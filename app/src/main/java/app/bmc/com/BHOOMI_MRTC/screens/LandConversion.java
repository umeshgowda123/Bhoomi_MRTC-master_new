package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Interface;
import app.bmc.com.BHOOMI_MRTC.model.LandConversion_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversion extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {

    private RadioButton rb_AffidavitID;
    private RadioButton rb_UserID;

    private EditText etRadioText;
    private String affidavitID;
    private String userID;

    Button btnFetchReports;
    private ProgressDialog progressDialog;
    String Affidavit_res, User_res;

    private DataBaseHelper dataBaseHelper;
    private List<LandConversion_Interface> LandConversion_Data;
    Call<PariharaIndividualDetailsResponse> call;
    PariharaIndividualReportInteface apiInterface;

    String tokenType, accessToken;

    private RtcViewInfoBackGroundTaskFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        etRadioText = findViewById(R.id.etRadioText);
        btnFetchReports = findViewById(R.id.btnSubmit);
        rb_AffidavitID = findViewById(R.id.rb_AffidavitID);
        rb_UserID = findViewById(R.id.rb_UserID);


//        dataBaseHelper =
//                Room.databaseBuilder(getApplicationContext(),
//                        DataBaseHelper.class, getString(R.string.db_name)).build();
//        Observable<String> stringObservable;
//        stringObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMaintenanceStatus(8));
//        stringObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String str) {
//                        if (str.equals("false")){
//                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(LandConversion.this).create();
//                            alertDialog.setTitle(getString(R.string.status));
//                            alertDialog.setMessage(getString(R.string.this_service_is_under_maintenance));
//                            alertDialog.setCancelable(false);
//                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.ok), (dialog, which) -> onBackPressed());
//                            alertDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        rb_AffidavitID.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etRadioText.setVisibility(View.VISIBLE);
                etRadioText.setHint(R.string.enter_affidavit_id);
                etRadioText.setInputType(InputType.TYPE_CLASS_NUMBER);
                etRadioText.setText("");
                rb_UserID.setChecked(false);
            }
        });

        rb_UserID.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etRadioText.setVisibility(View.VISIBLE);
                etRadioText.setEnabled(true);
                etRadioText.setHint(R.string.enter_user_ID);
                etRadioText.setInputType(InputType.TYPE_CLASS_TEXT);
                etRadioText.setText("");
                rb_AffidavitID.setChecked(false);
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }
        btnFetchReports.setOnClickListener(v -> {

        if (isNetworkAvailable()) {
            if (rb_AffidavitID.isChecked() || rb_UserID.isChecked()) {
                if (rb_AffidavitID.isChecked()) {
                    affidavitID = etRadioText.getText().toString().trim();
                    if (TextUtils.isEmpty(affidavitID)) {
                        etRadioText.setError(getString(R.string.enter_affidavit_id));
                        etRadioText.requestFocus();
                    }else {
                        if (!affidavitID.isEmpty()) {
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<List<? extends LandConversion_Interface>> LandConversionObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getAFFIDAVIT_RES(affidavitID));
                        LandConversionObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends LandConversion_Interface>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends LandConversion_Interface> landConversion_interfaces_list) {

                                    LandConversion_Data = (List<LandConversion_Interface>) landConversion_interfaces_list;
                                    if (landConversion_interfaces_list.size() != 0) {
                                        for (int i = 0; i <= landConversion_interfaces_list.size() - 1; i++) {
                                            String affidavit_res = LandConversion_Data.get(0).getAFFIDAVIT_RES();
                                            if (affidavit_res == null || affidavit_res.equals("") || affidavit_res.contains("INVALID")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.invalid_affidavit_id))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            } else if (affidavit_res.contains("Details not found")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.details_not_found))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            } else {
                                                Intent intent = new Intent(LandConversion.this, LandConversionBasedOnAffidavit.class);
                                                intent.putExtra("AFFIDAVIT_ResponseData", affidavit_res);
                                                intent.putExtra("AFFIDAVIT_ID", affidavitID);
                                                startActivity(intent);
                                            }
                                        }
                                    } else {
                                        mTaskFragment.startBackgroundTask_GenerateToken();
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                        }
                    }
                } else {
                    userID = etRadioText.getText().toString().trim();
                    if (TextUtils.isEmpty(userID)) {
                        etRadioText.setError(getString(R.string.enter_user_ID));
                        etRadioText.requestFocus();
                    } else {

                    if (!userID.isEmpty()) {

                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<List<? extends LandConversion_Interface>> LandConversionObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getUSER_RES(userID));
                        LandConversionObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends LandConversion_Interface>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends LandConversion_Interface> landConversion_interfaces_list) {

                                    LandConversion_Data = (List<LandConversion_Interface>) landConversion_interfaces_list;
                                    if (landConversion_interfaces_list.size()!=0) {
                                        for (int i = 0; i <= landConversion_interfaces_list.size()-1; i++) {

                                        String userid_res = LandConversion_Data.get(0).getUSER_RES();
                                        if( userid_res == null || userid_res.equals("") || userid_res.contains("INVALID")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                            builder.setTitle(getString(R.string.status))
                                                    .setMessage(getString(R.string.invalid_user_id))
                                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                                    .setCancelable(false)
                                                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                            final android.app.AlertDialog alert = builder.create();
                                            alert.show();
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                        }else if(userid_res.contains("Details not found")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                            builder.setTitle(getString(R.string.status))
                                                    .setMessage(getString(R.string.details_not_found))
                                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                            final android.app.AlertDialog alert = builder.create();
                                            alert.show();
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                        } else {

                                            Intent intent = new Intent(LandConversion.this, LandConversionBasedOnUserId.class);
                                            intent.putExtra("USER_ID", userID);
                                            intent.putExtra("tokenType", tokenType);
                                            intent.putExtra("accessToken", accessToken);
                                            startActivity(intent);
                                        }
                                        }
                                    }
                                    else {
                                        mTaskFragment.startBackgroundTask_GenerateToken();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                        }
                    }
                }
            } else {
                Toast.makeText(LandConversion.this, "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            selfDestruct();
        }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(LandConversion.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage(getString(R.string.please_enable_internet_connection));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.ok), (dialog, which) -> {

        });
        alertDialog.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //______________________________________________________________________DB____________________________________________________

    public List<LandConversion_TABLE> loadData() {
        List<LandConversion_TABLE> LandConversion_tables_arr = new ArrayList<>();
        try {
            LandConversion_TABLE LandConversion_table = new LandConversion_TABLE();
            LandConversion_table.setAFFIDAVIT_ID(affidavitID+"");
            LandConversion_table.setAFFIDAVIT_RES(Affidavit_res+"");
            LandConversion_table.setUSER_ID(userID+"");
            LandConversion_table.setUSER_RES(User_res+"");
            LandConversion_tables_arr.add(LandConversion_table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return LandConversion_tables_arr;
    }

    public void createLandConversion_Data(final List<LandConversion_TABLE> LandConversion_List) {
        Observable<Long[]> insertMPDData = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertLandConversionData(LandConversion_List));
        insertMPDData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Long[]>() {


                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long[] longs) {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onComplete() {

                }
            });
    }

    private void deleteAllResponse(List<LandConversion_TABLE> LandConversion_list) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteLandConversionResponse());
        noOfRows
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {


                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer integer) {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                    createLandConversion_Data(LandConversion_list);
                }
            });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(call != null && call.isExecuted()) {
            call.cancel();
        }
    }



    @Override
    public void onPreExecute1() {

    }

    @Override
    public void onPostResponseSuccess1(String data) {

    }

    @Override
    public void onPostResponseError_FORHISSA(String data, int count) {

    }

    @Override
    public void onPreExecute2() {

    }

    @Override
    public void onPostResponseSuccess2(String data) {

    }

    @Override
    public void onPostResponseError_Task2(String data, int count) {

    }

    @Override
    public void onPreExecute3() {

    }

    @Override
    public void onPostResponseSuccess3(String data) {

    }

    @Override
    public void onPostResponseError_Task3(String data) {

    }

    @Override
    public void onPreExecute4() {

    }

    @Override
    public void onPostResponseSuccess4(String data) {

    }

    @Override
    public void onPostResponseError_Task4(String data) {

    }

    @Override
    public void onPostResponseSuccessCultivator(String gettcDataResult) {

    }

    @Override
    public void onPostResponseErrorCultivator(String errorResponse, int count) {

    }

    @Override
    public void onPreExecute5() {

    }

    @Override
    public void onPostResponseSuccess_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPostResponseError_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        accessToken = AccessToken;
        tokenType = TokenType;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.something_went_wrong_pls_try_again))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            try {
                if (rb_AffidavitID.isChecked()) {
                    LandConversionBasedOnAffidavitIDResponse(tokenType, accessToken);
                }else {
                    LandConversionBasedOnUserIDResponse(tokenType, accessToken);
                }
//                JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
//                mTaskFragment.startBackgroundTask_GetDetails_VilWise(jsonObject, getString(R.string.rest_service_url), tokenType, accessToken);

            } catch (Exception e){
                Log.d("ExcepSuccessGetToken",e.getLocalizedMessage()+"");
                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        Log.d("ERR_msg", errorResponse+"");
        Toast.makeText(this, ""+errorResponse, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Authorization has been denied for this request.", Toast.LENGTH_SHORT).show();
    }

    public void LandConversionBasedOnAffidavitIDResponse(String token_type, String token){
        progressDialog = new ProgressDialog(LandConversion.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiInterface = AuthorizationClient.getClient(getString(R.string.rest_service_url),token_type,token).create(PariharaIndividualReportInteface.class);
        call = apiInterface.getLandConversionBasedOnAffidavitID(affidavitID);
        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Response<PariharaIndividualDetailsResponse> response) {

                if (response.isSuccessful()) {
                    PariharaIndividualDetailsResponse result = response.body();
                    assert result != null;
                    Affidavit_res = result.getGet_Afdvt_ReqSts_BasedOnAfdvtIdResult();

                    progressDialog.dismiss();
                    if (Affidavit_res == null || Affidavit_res.equals("") || Affidavit_res.contains("INVALID")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.invalid_affidavit_id))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else if (Affidavit_res.contains("Details not found")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.details_not_found))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {


                        //---------DB INSERT-------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<Integer> noOfRows;
                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsLandConversionTbl());
                        noOfRows
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {


                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Integer integer) {
                                        List<LandConversion_TABLE> LandConversion_list = loadData();
                                        if (integer < 6) {
                                            createLandConversion_Data(LandConversion_list);
                                        } else {
                                            deleteAllResponse(LandConversion_list);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(LandConversion.this, LandConversionBasedOnAffidavit.class);
                                        intent.putExtra("AFFIDAVIT_ResponseData", Affidavit_res);
                                        intent.putExtra("AFFIDAVIT_ID", affidavitID);
                                        startActivity(intent);
                                    }
                                });
                        //---------------------------------------------------------------------------------------------

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void LandConversionBasedOnUserIDResponse(String token_type, String token){
        progressDialog = new ProgressDialog(LandConversion.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        apiInterface = AuthorizationClient.getClient(getString(R.string.rest_service_url),token_type,token).create(PariharaIndividualReportInteface.class);
        call = apiInterface.getLandConversionBasedOnUserID(userID);
        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PariharaIndividualDetailsResponse>  call, @NotNull Response<PariharaIndividualDetailsResponse> response)
            {
                if(response.isSuccessful())
                {
                    PariharaIndividualDetailsResponse result = response.body();
                    assert result != null;
                    User_res = result.getGet_Afdvt_ReqSts_BasedOnUserIdResult();

                    progressDialog.dismiss();
                    if(User_res == null || User_res.equals("") || User_res.contains("INVALID")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.invalid_user_id))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    }else if(User_res.contains("Details not found")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.details_not_found))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {


                        //---------DB INSERT-------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<Integer> noOfRows;
                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsLandConversionTbl());
                        noOfRows
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {


                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Integer integer) {
                                        List<LandConversion_TABLE> LandConversion_list = loadData();
                                        if (integer < 6) {
                                            createLandConversion_Data(LandConversion_list);
                                        } else {
                                            deleteAllResponse(LandConversion_list);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(LandConversion.this, LandConversionBasedOnUserId.class);
//                                                                                intent.putExtra("UserID_ResponseData", User_res);
                                        intent.putExtra("USER_ID", userID);
                                        startActivity(intent);
                                    }
                                });
                        //---------------------------------------------------------------------------------------------

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
