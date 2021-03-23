package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcXmlverificationBackGroundTask;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.RTCV_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.model.RTCXML_InputParameter_Class;
import app.bmc.com.BHOOMI_MRTC.model.RTC_VERIFICATION_TABLE;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RtcVerification extends AppCompatActivity implements RtcXmlverificationBackGroundTask.BackgroundCallBackRtcXmlVerification, RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {
    private Button getRtcDataBtn;
    private Button clearReferenceNoBtn;
    private EditText referenceNumber;
    private RtcXmlverificationBackGroundTask mTaskFragment;
    private RtcViewInfoBackGroundTaskFragment mTaskFragment_2;
    private ProgressBar progressBar;

    private DataBaseHelper dataBaseHelper;
    String referenceNo;
    String responseData;
    List<RTCV_RES_Interface> RTCV_data;

    String accessToken, tokenType;

    RTCXML_InputParameter_Class rtcxml_inputParameter_class;
    int AppType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_verification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        progressBar = findViewById(R.id.progress_circular);
        getRtcDataBtn = findViewById(R.id.btn_get_rtc_data);
        clearReferenceNoBtn = findViewById(R.id.btn_clear_reference_no);
        referenceNumber = findViewById(R.id.etRefNum);

        onButtonClickActions();

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Intent i = getIntent();
        AppType = i.getIntExtra("AppType", 0);

        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcXmlverificationBackGroundTask) fm.findFragmentByTag(RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcXmlverificationBackGroundTask();
            fm.beginTransaction().add(mTaskFragment, RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT).commit();
        }
        if (mTaskFragment.isTaskExecuting) {

            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        mTaskFragment_2 = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment_2 == null) {
            mTaskFragment_2 = new RtcViewInfoBackGroundTaskFragment();
            fragmentManager.beginTransaction().add(mTaskFragment_2, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }
//        if (mTaskFragment_2.isTaskExecuting) {
//            progressBar = findViewById(R.id.progress_circular);
//            if (progressBar != null)
//                progressBar.setVisibility(View.VISIBLE);
//        }

//        dataBaseHelper =
//                Room.databaseBuilder(getApplicationContext(),
//                        DataBaseHelper.class, getString(R.string.db_name)).build();
//        Observable<String> stringObservable;
//        stringObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMaintenanceStatus(2));
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
//                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(RtcVerification.this).create();
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

    }

    private void onButtonClickActions() {
        getRtcDataBtn.setOnClickListener(v -> {
            referenceNo = referenceNumber.getText().toString().trim();
            View focus = null;
            boolean status = false;

            if (TextUtils.isEmpty(referenceNo)) {
                focus = referenceNumber;
                status = true;
                referenceNumber.setError("Enter Reference Number");
            }
            if (status) {
                focus.requestFocus();
            } else {
                if (isNetworkAvailable()) {

                    String passcode = getString(R.string.passcode);
                    String saltkey = getString(R.string.saltkey);
//                    String values1;
//                    values1 = "{" + "\"pReferenceNo\":" + referenceNo + ","
//                            + "\"pPasscode\":" + passcode + ","
//                            + "\"pSaltkey\":\"" + saltkey + "\""
//                            + "}";
//                    jsonObject = new JsonParser().parse(values1).getAsJsonObject();
                    rtcxml_inputParameter_class = new RTCXML_InputParameter_Class();
                    rtcxml_inputParameter_class.setpReferenceNo(referenceNo);
                    rtcxml_inputParameter_class.setpPasscode(passcode);
                    rtcxml_inputParameter_class.setpSaltkey(saltkey);


                    //---------------------------------------------------------------------------

                    Observable<List<? extends RTCV_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getREFF_RES(referenceNo));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends RTCV_RES_Interface>>() {

                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull List<? extends RTCV_RES_Interface> rtcv_res_interfaces_list) {
//                                    progressBar.setVisibility(View.GONE);

                                    RTCV_data = (List<RTCV_RES_Interface>) rtcv_res_interfaces_list;
                                    if (rtcv_res_interfaces_list.size() != 0) {
                                        for (int i = 0; i <= rtcv_res_interfaces_list.size()-1; i++) {

                                            String Response = RTCV_data.get(0).getREFF_RES();
                                            try {

                                                if (Response.contains("No Information Found")||Response.contains("SqlException")) {
                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(RtcVerification.this, R.style.MyDialogTheme);
                                                    builder.setTitle(getString(R.string.status))
                                                            .setMessage(getString(R.string.no_information_found))
                                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                                            .setCancelable(false)
                                                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                    final AlertDialog alert = builder.create();
                                                    alert.show();
                                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                }else {
                                                    Intent intent = new Intent(RtcVerification.this, RtcVerificationData.class);
                                                    intent.putExtra("xml_data", Response);
                                                    startActivity(intent);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
//                                        progressBar.setVisibility(View.VISIBLE);
//                                        mTaskFragment.startBackgroundTask(jsonObject);
                                        InputMethodManager inputManager = (InputMethodManager) RtcVerification.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                        assert inputManager != null;
                                        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(RtcVerification.this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        mTaskFragment_2.startBackgroundTask_GenerateToken(getString(R.string.url_token));


                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                    //---------------------------------------------------------------------------
//                    mTaskFragment.startBackgroundTask(jsonObject);
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });
        clearReferenceNoBtn.setOnClickListener(v -> referenceNumber.setText(""));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPreExecute1() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess1(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        responseData = data;
        Log.d("DATA RtcVerification", data+"");
        if (responseData.contains("No Information Found")||responseData.contains("SqlException")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(RtcVerification.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_information_found))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
        else {

            Observable<Integer> noOfRows;
            noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsRTCV());
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Integer integer) {
                            List<RTC_VERIFICATION_TABLE> rtc_verification_tableList = loadData();
                            if (integer < 6) {
                                createRTCVTABLE_Data(rtc_verification_tableList, data);
                            } else {
                                deleteResponseByID(rtc_verification_tableList, data);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
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
    public void onPostResponseError(String data) {
        Log.d("ERRORMSG",""+data);
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPreExecuteToken() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        accessToken = AccessToken;
        tokenType = TokenType;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RtcVerification.this, R.style.MyDialogTheme);
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
                progressBar.setVisibility(View.VISIBLE);
                ClsAppLgs objClsAppLgs = new ClsAppLgs();
                objClsAppLgs.setAppID(1);
                objClsAppLgs.setAppType(AppType);
                objClsAppLgs.setIPAddress("");

                mTaskFragment_2.startBackgroundTask_AppLgs(objClsAppLgs, getString(R.string.rest_service_url), tokenType, accessToken);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        Log.d("ERR_msg", errorResponse+"");
        Toast.makeText(this, errorResponse+"", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Authorization has been denied for this request.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPreExecuteGetBhoomiLandId() {

    }

    @Override
    public void onPostResponseSuccessGetBhoomiLandID(String data) {

    }

    @Override
    public void onPostResponseError_BhoomiLandID(String data) {

    }

    @Override
    public void onPreExecute_AppLgs() {
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {
        Log.d("AppLgsRes", ""+data);
        mTaskFragment.startBackgroundTask(rtcxml_inputParameter_class, tokenType, accessToken);
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {
        Log.d("AppLgsRes", ""+data);
        mTaskFragment.startBackgroundTask(rtcxml_inputParameter_class, tokenType, accessToken);
    }

    @Override
    public void onPreExecute_GetSurnocNo() {

    }

    @Override
    public void onPostResponseSuccess_GetSurnocNo(String data) {

    }

    @Override
    public void onPostResponseError_GetSurnocNo(String data) {

    }

    @Override
    public void onPreExecute_GetHissaNo() {

    }

    @Override
    public void onPostResponseSuccess_GetHissaNo(String data) {

    }

    @Override
    public void onPostResponseError_GetHissaNo(String data) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //______________________________________________________________________DB____________________________________________________

    public List<RTC_VERIFICATION_TABLE> loadData() {
//        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<RTC_VERIFICATION_TABLE> rtc_verification_tables_arr = new ArrayList<>();
        try {
            RTC_VERIFICATION_TABLE rtc_verification_table = new RTC_VERIFICATION_TABLE();
            rtc_verification_table.setREF_NO(referenceNo);
            rtc_verification_table.setREFF_RES(responseData);
            rtc_verification_tables_arr.add(rtc_verification_table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtc_verification_tables_arr;
    }


    public void createRTCVTABLE_Data(final List<RTC_VERIFICATION_TABLE> rtc_verification_tables, String data) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertRTCVerificationData(rtc_verification_tables));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long[] longs) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(RtcVerification.this, RtcVerificationData.class);
                        intent.putExtra("xml_data", data);
                        startActivity(intent);
//                        Toast.makeText(RtcVerification.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteResponseByID(final List<RTC_VERIFICATION_TABLE> rtc_verification_tableList, String data) {

        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllRTCVResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        createRTCVTABLE_Data(rtc_verification_tableList, data);
                    }
                });

    }

}
