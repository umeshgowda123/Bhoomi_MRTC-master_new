package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Interface;
import app.bmc.com.BHOOMI_MRTC.model.LandConversion_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversion extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        etRadioText = findViewById(R.id.etRadioText);
        btnFetchReports = findViewById(R.id.btnSubmit);
        rb_AffidavitID = findViewById(R.id.rb_AffidavitID);
        rb_UserID = findViewById(R.id.rb_UserID);

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
                                    Log.d("mpd_res_interfaces", landConversion_interfaces_list.size() + "");

                                    LandConversion_Data = (List<LandConversion_Interface>) landConversion_interfaces_list;
                                    if (landConversion_interfaces_list.size() != 0) {
                                        Log.d("CHECK", "Fetching from local");
                                        for (int i = 0; i <= landConversion_interfaces_list.size() - 1; i++) {
                                            String affidavit_res = LandConversion_Data.get(0).getAFFIDAVIT_RES();
                                            Log.d("affidavit_res", affidavit_res + "");
                                            if (affidavit_res == null || affidavit_res.equals("") || affidavit_res.contains("INVALID")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                builder.setTitle("STATUS")
                                                        .setMessage("Invalid Affidavit ID")
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            } else if (affidavit_res.contains("Details not found")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                builder.setTitle("STATUS")
                                                        .setMessage(getString(R.string.details_not_found))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
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
                                        progressDialog = new ProgressDialog(LandConversion.this);
                                        progressDialog.setMessage("Please Wait");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();

                                        PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionBasedOnAffidavitID(Constants.REPORT_SERVICE_USER_NAME,
                                                Constants.REPORT_SERVICE_PASSWORD, affidavitID);
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
                                                        builder.setTitle("STATUS")
                                                                .setMessage("Invalid Affidavit ID")
                                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                                        final android.app.AlertDialog alert = builder.create();
                                                        alert.show();
                                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                    } else if (Affidavit_res.contains("Details not found")) {
                                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                        builder.setTitle("STATUS")
                                                                .setMessage(getString(R.string.details_not_found))
                                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
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
                                                                        Log.d("intValue", integer + "");
                                                                        if (integer < 6) {
                                                                            Log.d("intValueIN", integer + "");
//                                                                                    if ()
                                                                            List<LandConversion_TABLE> LandConversion_list = loadData();
                                                                            createLandConversion_Data(LandConversion_list);


                                                                        } else {
                                                                            Log.d("intValueELSE", integer + "");
                                                                            deleteByID(0);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void onComplete() {
                                                                        progressDialog.dismiss();
                                                                        Log.d("CHECK", "Fetching From Server");

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
                                            }
                                        });
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
                                    Log.d("landConversion_res",landConversion_interfaces_list.size()+"");

                                    LandConversion_Data = (List<LandConversion_Interface>) landConversion_interfaces_list;
                                    if (landConversion_interfaces_list.size()!=0) {
                                        Log.d("CHECK","Fetching from local");
                                        for (int i = 0; i <= landConversion_interfaces_list.size()-1; i++) {

                                        String userid_res = LandConversion_Data.get(0).getUSER_RES();
                                        Log.d("userid_res",userid_res+"");
                                        if( userid_res == null || userid_res.equals("") || userid_res.contains("INVALID")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                            builder.setTitle("STATUS")
                                                    .setMessage("Invalid User ID")
                                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                            final android.app.AlertDialog alert = builder.create();
                                            alert.show();
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                        }else if(userid_res.contains("Details not found")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                            builder.setTitle("STATUS")
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
                                            startActivity(intent);
                                        }
                                        }
                                    }
                                    else {
                                        progressDialog = new ProgressDialog(LandConversion.this);
                                        progressDialog.setMessage("Please Wait");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();

                                        PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionBasedOnUserID(Constants.REPORT_SERVICE_USER_NAME,
                                                Constants.REPORT_SERVICE_PASSWORD,userID);
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
                                                    builder.setTitle("STATUS")
                                                            .setMessage("Invalid User ID")
                                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                                            .setCancelable(false)
                                                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                                    final android.app.AlertDialog alert = builder.create();
                                                    alert.show();
                                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                }else if(User_res.contains("Details not found")) {
                                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversion.this, R.style.MyDialogTheme);
                                                    builder.setTitle("STATUS")
                                                            .setMessage(getString(R.string.details_not_found))
                                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                                            .setCancelable(false)
                                                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
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
                                                                Log.d("intValue",integer+"");
                                                                if (integer < 6) {
                                                                    Log.d("intValueIN",integer+"");
                                                                    List<LandConversion_TABLE> LandConversion_list = loadData();
                                                                    createLandConversion_Data(LandConversion_list);


                                                                } else {
                                                                    Log.d("intValueELSE", integer + "");
                                                                    deleteByID(0);
                                                                }
                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {
                                                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                            }

                                                            @Override
                                                            public void onComplete() {
                                                                progressDialog.dismiss();
                                                                Log.d("CHECK","Fetching From Server");

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
                                            }
                                        });
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
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", (dialog, which) -> {

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
            Log.d("Exception", e + "");
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
    private void deleteByID(final int id) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteByIdLandConversion(id));
        noOfRows
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {


                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer integer) {

                    Log.i("delete", integer + "");
                    deleteAllResponse();

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

    private void deleteAllResponse() {

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

                    Log.i("delete", integer + "");


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
}
