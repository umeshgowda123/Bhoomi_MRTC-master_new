package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcXmlverificationBackGroundTask;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.RTCV_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.model.RTC_VERIFICATION_TABLE;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class  for reprajents UI and RTc Verification  service applications
 */
public class RtcVerification extends AppCompatActivity implements RtcXmlverificationBackGroundTask.BackgroundCallBackRtcXmlVerification {
    private Button getRtcDataBtn;
    private Button clearReferenceNoBtn;
    private EditText referenceNumber;
    private RtcXmlverificationBackGroundTask mTaskFragment;
    private ProgressBar progressBar;

    private DataBaseHelper dataBaseHelper;
    String referenceNo;
    String responseData;
    List<RTCV_RES_Interface> RTCV_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_verification);

//        AlertDialog alertDialog = new AlertDialog.Builder(RtcVerification.this).create();
//        // alertDialog.setTitle("Reset...");
//        alertDialog.setMessage("This Service is Still Under Maintenance");
//        alertDialog.setCancelable(false);
//        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", (dialog, which) -> onBackPressed());
//
//        alertDialog.show();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        getRtcDataBtn = findViewById(R.id.btn_get_rtc_data);
        clearReferenceNoBtn = findViewById(R.id.btn_clear_reference_no);
        referenceNumber = findViewById(R.id.etRefNum);
        onButtonClickActions();
        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcXmlverificationBackGroundTask) fm.findFragmentByTag(RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcXmlverificationBackGroundTask();
            fm.beginTransaction().add(mTaskFragment, RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT).commit();
        }
        if (mTaskFragment.isTaskExecuting) {
            progressBar = findViewById(R.id.progress_circular);
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void onButtonClickActions() {
        getRtcDataBtn.setOnClickListener(v -> {
            View focus = null;
            boolean status = false;
            referenceNo = referenceNumber.getText().toString().trim();

            String passcode = getString(R.string.passcode);
            String saltkey = getString(R.string.saltkey);
            String values1;
            values1 = "{" + "\"pReferenceNo\":" + referenceNo + ","
                          + "\"pPasscode\":" + passcode + ","
                          + "\"pSaltkey\":\"" + saltkey + "\""
                    + "}";
            JsonObject jsonObject = new JsonParser().parse(values1).getAsJsonObject();
            Log.d("jsonOBJECT", String.valueOf(jsonObject));


            if (TextUtils.isEmpty(referenceNo)) {
                focus = referenceNumber;
                status = true;
                referenceNumber.setError("Enter Reference Number");
            }
            if (status) {
                focus.requestFocus();
            } else {
                if (isNetworkAvailable()) {

                    //---------------------------------------------------------------------------
                    dataBaseHelper =
                            Room.databaseBuilder(getApplicationContext(),
                                    DataBaseHelper.class, getString(R.string.db_name)).build();
                    Observable<List<? extends RTCV_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getREFF_RES(referenceNo));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends RTCV_RES_Interface>>() {

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends RTCV_RES_Interface> rtcv_res_interfaces_list) {
//                                    progressBar.setVisibility(View.GONE);
                                    Log.d("rlr_res_interfaces_list", rtcv_res_interfaces_list.size() + "");

                                    RTCV_data = (List<RTCV_RES_Interface>) rtcv_res_interfaces_list;
                                    if (rtcv_res_interfaces_list.size() != 0) {
                                        Log.d("CHECK", "Fetching from local");
                                        for (int i = 0; i <= rtcv_res_interfaces_list.size()-1; i++) {

                                            String Response = RTCV_data.get(0).getREFF_RES();
                                            try {
//                                                JsonObject json = new Gson().fromJson(Response, JsonObject.class);
//                                                Log.d("CHECK", json.toString());
                                                Intent intent = new Intent(RtcVerification.this, RtcVerificationData.class);
                                                intent.putExtra("xml_data", Response);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    } else {
                                        mTaskFragment.startBackgroundTask(jsonObject);

                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

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
        Log.d("DATA",data);
        //---------DB INSERT-------

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows;
        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsRTCV());
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
                            List<RTC_VERIFICATION_TABLE> rtc_verification_tableList = loadData();
                            createRTCVTABLE_Data(rtc_verification_tableList);
                        } else {
                            Log.d("intValueELSE",integer+"");
                            deleteByID(0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //---------------------------------------------------------------------------------------------
        if (responseData.contains("No Information Found")||responseData.contains("Incorrect syntax near the keyword")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(RtcVerification.this, R.style.MyDialogTheme);
            builder.setTitle("Information Status")
                    .setMessage("No Information Found")
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
        else {

            Intent intent = new Intent(RtcVerification.this, RtcVerificationData.class);
            intent.putExtra("xml_data", data);
            startActivity(intent);
        }
    }

    @Override
    public void onPostResponseError(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
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
            Log.d("Exception", e + "");
        }

        return rtc_verification_tables_arr;
    }


    public void createRTCVTABLE_Data(final List<RTC_VERIFICATION_TABLE> rtc_verification_tables) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertRTCVerificationData(rtc_verification_tables));
        insertMasterObservable
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
//                        Toast.makeText(RtcVerification.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteByID(final int id) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteByIdRTCV(id));
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
                        deleteResponseByID();

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

    private void deleteResponseByID() {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllRTCVResponse());
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
