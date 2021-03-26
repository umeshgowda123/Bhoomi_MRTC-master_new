package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.model.ClsKnowID_Get_Surnoc_Hissa;
import app.bmc.com.BHOOMI_MRTC.model.ClsReqLandID;

import app.bmc.com.BHOOMI_MRTC.model.Hissa_Separate_Response;
import app.bmc.com.BHOOMI_MRTC.model.Surnoc_Response;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Know_Your_LandID extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo{

    MaterialBetterSpinner sp_landid_district;
    MaterialBetterSpinner sp_landid_taluk;
    MaterialBetterSpinner sp_landid_hobli;
    MaterialBetterSpinner sp_landid_village;
    EditText et_landid_surveyno;
    Button btn_landid_go;
    MaterialBetterSpinner sp_surnc;
    MaterialBetterSpinner sp_landid_hissa;
    Button btn_landid_fetch;
    List<DistrictModelInterface> districtData;
    List<TalukModelInterface> talukData;
    List<HobliModelInterface> hobliData;
    List<VillageModelInterface> villageData;
    int district_id;
    int taluk_id;
    int hobli_id;
    int village_id;
    int land_no;
    String hissa;
    String suroc;

    List<Surnoc_Response> surnoc_responseList;
    List<Hissa_Separate_Response> hissa_responseList;

    String surveyNo;
    RtcViewInfoBackGroundTaskFragment mTaskFragment;
    ProgressBar progressBar;
    String language;
    DataBaseHelper dataBaseHelper;
    TextView tvSetTite;
    ArrayAdapter<String> defaultArrayAdapter;
    ClsKnowID_Get_Surnoc_Hissa clsKnowID_get_hissa;
    ClsKnowID_Get_Surnoc_Hissa clsKnowID_get_surnoc;

    String accessToken, tokenType;

    int AppType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_your_land_id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);

        sp_landid_district = findViewById(R.id.sp_landid_district);
        sp_landid_taluk = findViewById(R.id.sp_landid_taluk);
        sp_landid_hobli = findViewById(R.id.sp_landid_hobli);
        sp_landid_village = findViewById(R.id.sp_landid_village);
        sp_surnc = findViewById(R.id.sp_surnc);
        sp_landid_hissa = findViewById(R.id.sp_landid_hissa);
        et_landid_surveyno = findViewById(R.id.et_landid_surveyno);
        btn_landid_go = findViewById(R.id.btn_landid_go);
        btn_landid_fetch = findViewById(R.id.btn_landid_fetch);
        tvSetTite = findViewById(R.id.tvSetTite);
        defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_landid_taluk.setAdapter(defaultArrayAdapter);
        sp_landid_hobli.setAdapter(defaultArrayAdapter);
        sp_landid_village.setAdapter(defaultArrayAdapter);
        sp_surnc.setAdapter(defaultArrayAdapter);
        sp_landid_hissa.setAdapter(defaultArrayAdapter);
        sp_landid_district.setAdapter(defaultArrayAdapter);


        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Intent i = getIntent();
        AppType = i.getIntExtra("AppType", 0);

        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getDistinctDistricts() : dataBaseHelper.daoAccess().getDistinctDistrictsKannada());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends DistrictModelInterface>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<? extends DistrictModelInterface> mst_vlmList) {

                        districtData = (List<DistrictModelInterface>) mst_vlmList;
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_landid_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
        if (mTaskFragment.isTaskExecuting) {
            progressBar = findViewById(R.id.progress_circular);
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }
        onClickAction();
    }

    private void onClickAction() {

        sp_landid_district.setOnItemClickListener((parent, view, position, id) -> {
        sp_landid_taluk.setText("");
        sp_landid_hobli.setText("");
        sp_landid_village.setText("");
        sp_surnc.setText("");
        sp_landid_hissa.setText("");
        sp_landid_taluk.setAdapter(defaultArrayAdapter);
        sp_landid_hobli.setAdapter(defaultArrayAdapter);
        sp_landid_village.setAdapter(defaultArrayAdapter);
        sp_surnc.setAdapter(defaultArrayAdapter);
        sp_landid_hissa.setAdapter(defaultArrayAdapter);
        et_landid_surveyno.setText("");

        district_id = districtData.get(position).getVLM_DST_ID();
        Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id)) : dataBaseHelper.daoAccess().getTalukByDistrictIdKannada(String.valueOf(district_id)));
        talukDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends TalukModelInterface>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<? extends TalukModelInterface> talukDataList) {
                        talukData = (List<TalukModelInterface>) talukDataList;
                        ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, talukData);
                        sp_landid_taluk.setAdapter(talukArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        });

        sp_landid_taluk.setOnItemClickListener((parent, view, position, id) -> {
            sp_landid_hobli.setText("");
            sp_landid_village.setText("");
            sp_surnc.setText("");
            sp_landid_hissa.setText("");
            sp_landid_hobli.setAdapter(defaultArrayAdapter);
            sp_landid_village.setAdapter(defaultArrayAdapter);
            sp_surnc.setAdapter(defaultArrayAdapter);
            sp_landid_hissa.setAdapter(defaultArrayAdapter);
            et_landid_surveyno.setText("");


            taluk_id = talukData.get(position).getVLM_TLK_ID();
        Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictIdKannada(String.valueOf(taluk_id), String.valueOf(district_id)));
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends HobliModelInterface>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<? extends HobliModelInterface> hobliDataList) {
                        hobliData = (List<HobliModelInterface>) hobliDataList;
                        ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, hobliData);
                        sp_landid_hobli.setAdapter(hobliArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        });

        sp_landid_hobli.setOnItemClickListener((parent, view, position, id) -> {
            sp_landid_village.setText("");
            sp_surnc.setText("");
            sp_landid_hissa.setText("");
            sp_landid_village.setAdapter(defaultArrayAdapter);
            sp_surnc.setAdapter(defaultArrayAdapter);
            sp_landid_hissa.setAdapter(defaultArrayAdapter);
            et_landid_surveyno.setText("");


            hobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)));
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends VillageModelInterface>>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<? extends VillageModelInterface> villageDataList) {
                            villageData = (List<VillageModelInterface>) villageDataList;
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            sp_landid_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_landid_village.setOnItemClickListener((parent, view, position, id) -> {
            sp_surnc.setText("");
            sp_landid_hissa.setText("");
            sp_surnc.setAdapter(defaultArrayAdapter);
            sp_landid_hissa.setAdapter(defaultArrayAdapter);
            village_id = villageData.get(position).getVLM_VLG_ID();
        });

        sp_surnc.setOnItemClickListener((parent, view, position, id) -> {
            sp_landid_hissa.setText("");
            sp_landid_hissa.setAdapter(defaultArrayAdapter);
            suroc = surnoc_responseList.get(position).getSurnoc();

            clsKnowID_get_hissa = new ClsKnowID_Get_Surnoc_Hissa();
            clsKnowID_get_hissa.setpCensus_dist_code(String.valueOf(district_id));
            clsKnowID_get_hissa.setpCensus_Taluk_Code(String.valueOf(taluk_id));
            clsKnowID_get_hissa.setpHoblicode(String.valueOf(hobli_id));
            clsKnowID_get_hissa.setpVillagecode(String.valueOf(village_id));
            clsKnowID_get_hissa.setpSurvey_no(surveyNo);
            clsKnowID_get_hissa.setpSurnoc(suroc);

            mTaskFragment.startBackgroundTask_GetHissaNo(clsKnowID_get_hissa, getString(R.string.rest_service_url),tokenType, accessToken);

        });

        sp_landid_hissa.setOnItemClickListener((parent, view, position, id) -> {
            land_no = hissa_responseList.get(position).getLand_code();
            hissa = hissa_responseList.get(position).getHissa_no();
        });



        btn_landid_go.setOnClickListener(v -> {
            String districtName = sp_landid_district.getText().toString().trim();
            String talukName = sp_landid_taluk.getText().toString().trim();
            String hobliName = sp_landid_hobli.getText().toString().trim();
            String villageName = sp_landid_village.getText().toString().trim();
            surveyNo = et_landid_surveyno.getText().toString().trim();

            clsKnowID_get_surnoc = new ClsKnowID_Get_Surnoc_Hissa();
            clsKnowID_get_surnoc.setpCensus_dist_code(String.valueOf(district_id));
            clsKnowID_get_surnoc.setpCensus_Taluk_Code(String.valueOf(taluk_id));
            clsKnowID_get_surnoc.setpHoblicode(String.valueOf(hobli_id));
            clsKnowID_get_surnoc.setpVillagecode(String.valueOf(village_id));
            clsKnowID_get_surnoc.setpSurvey_no(surveyNo);

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = sp_landid_district;
                status = true;
                sp_landid_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = sp_landid_taluk;
                status = true;
                sp_landid_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = sp_landid_hobli;
                status = true;
                sp_landid_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = sp_landid_village;
                status = true;
                sp_landid_village.setError(getString(R.string.village_err));
            } else if (TextUtils.isEmpty(surveyNo)) {
                focus = et_landid_surveyno;
                status = true;
                et_landid_surveyno.setError(getString(R.string.survey_no_err));
            }
            if (status) {
                focus.requestFocus();
            } else {
                if (isNetworkAvailable()){
                    try {
                        sp_surnc.setText("");
                        sp_landid_hissa.setText("");
                        sp_surnc.setAdapter(defaultArrayAdapter);
                        sp_landid_hissa.setAdapter(defaultArrayAdapter);
                        mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();

            }
        });
        btn_landid_fetch.setOnClickListener(v -> {
            String districtName = sp_landid_district.getText().toString().trim();
            String talukName = sp_landid_taluk.getText().toString().trim();
            String hobliName = sp_landid_hobli.getText().toString().trim();
            String villageName = sp_landid_village.getText().toString().trim();
            surveyNo = et_landid_surveyno.getText().toString().trim();

            String surnoc = sp_surnc.getText().toString().trim();
            String hissa = sp_landid_hissa.getText().toString().trim();
            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = sp_landid_district;
                status = true;
                sp_landid_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = sp_landid_taluk;
                status = true;
                sp_landid_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = sp_landid_hobli;
                status = true;
                sp_landid_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = sp_landid_village;
                status = true;
                sp_landid_village.setError(getString(R.string.village_err));
            } else if (TextUtils.isEmpty(surveyNo)) {
                focus = et_landid_surveyno;
                status = true;
                et_landid_surveyno.setError(getString(R.string.survey_no_err));
            }else if (TextUtils.isEmpty(surnoc)) {
                focus = sp_surnc;
                status = true;
                sp_surnc.setError(getString(R.string.surnoc_err));
            }else if (TextUtils.isEmpty(hissa)) {
                focus = sp_landid_hissa;
                status = true;
                sp_landid_hissa.setError(getString(R.string.hissa_err));
            }
            if (status) {
                focus.requestFocus();
            } else {
                if (isNetworkAvailable()) {
                    try {
                        ClsAppLgs objClsAppLgs = new ClsAppLgs();
                        objClsAppLgs.setAppID(1);
                        objClsAppLgs.setAppType(AppType);
                        objClsAppLgs.setIPAddress("");

                        mTaskFragment.startBackgroundTask_AppLgs(objClsAppLgs, getString(R.string.rest_service_url), tokenType, accessToken);
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    selfDestruct();
                }
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

    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(Know_Your_LandID.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage(getString(R.string.please_enable_internet_connection));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.ok), (dialog, which) -> {

        });
        alertDialog.show();
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
    public void onPreExecuteToken() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken)
    {
        accessToken = AccessToken;
        tokenType = TokenType;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
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
                mTaskFragment.startBackgroundTask_GetSurnocNo(clsKnowID_get_surnoc, getString(R.string.rest_service_url), TokenType, AccessToken);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Toast.makeText(this, ""+errorResponse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreExecuteGetBhoomiLandId() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccessGetBhoomiLandID(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.equalsIgnoreCase("") || data.contains("Details Not Found")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_record))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else if(data.matches("[0-9]+") && data.length()==12) {
            Intent i = new Intent(Know_Your_LandID.this, ViewBhoomiLandID.class);
            i.putExtra("bhoomiLandID", ""+data);
            startActivity(i);
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    //.setMessage(getString(R.string.something_went_wrong_pls_try_again))
                    .setMessage(data)
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPostResponseError_BhoomiLandID(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.contains("CertPathValidatorException")){
            Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_SHORT).show();
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(""+data)
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPreExecute_AppLgs() {
        if (progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {

        ClsReqLandID objClsReqLandID = new ClsReqLandID();
        objClsReqLandID.setBhm_dist_code(district_id);
        objClsReqLandID.setBhm_taluk_code(taluk_id);
        objClsReqLandID.setBhm_hobli_code(hobli_id);
        objClsReqLandID.setBhm_village_code(village_id);
        objClsReqLandID.setBhm_land_code(land_no);

        mTaskFragment.startBackgroundTask_GetBhoomiLandID(objClsReqLandID, getString(R.string.rest_service_url), tokenType, accessToken);
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {

        ClsReqLandID objClsReqLandID = new ClsReqLandID();
        objClsReqLandID.setBhm_dist_code(district_id);
        objClsReqLandID.setBhm_taluk_code(taluk_id);
        objClsReqLandID.setBhm_hobli_code(hobli_id);
        objClsReqLandID.setBhm_village_code(village_id);
        objClsReqLandID.setBhm_land_code(land_no);

        mTaskFragment.startBackgroundTask_GetBhoomiLandID(objClsReqLandID, getString(R.string.rest_service_url), tokenType, accessToken);
    }

    @Override
    public void onPreExecute_GetSurnocNo() {
        progressBar  = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess_GetSurnocNo(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (TextUtils.isEmpty(data) || data.contains("No Data Found")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_survey_no))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                        dialog.cancel();
                        et_landid_surveyno.setText("");
                    });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else if (data.contains("is busy")){
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.server_is_busy_please_try_again_later))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(data);

                Type listType = new TypeToken<ArrayList<Surnoc_Response>>() {
                }.getType();
                surnoc_responseList = new Gson().fromJson(jsonArray.toString(), listType);
                ArrayAdapter<Surnoc_Response> arrayAdapter = new ArrayAdapter<>(Know_Your_LandID.this,
                        android.R.layout.simple_list_item_single_choice, surnoc_responseList);
                sp_surnc.setAdapter(arrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPostResponseError_GetSurnocNo(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.contains("CertPathValidatorException")){
            Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_SHORT).show();
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.server_is_busy_please_try_again_later))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPreExecute_GetHissaNo() {
        progressBar  = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess_GetHissaNo(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (TextUtils.isEmpty(data) || data.contains("No Data Found")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_survey_no))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else if (data.contains("is busy")){
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Know_Your_LandID.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.server_is_busy_please_try_again_later))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(data);

                Type listType = new TypeToken<ArrayList<Hissa_Separate_Response>>() {
                }.getType();
                hissa_responseList = new Gson().fromJson(jsonArray.toString(), listType);
                ArrayAdapter<Hissa_Separate_Response> arrayAdapter = new ArrayAdapter<>(Know_Your_LandID.this,
                        android.R.layout.simple_list_item_single_choice, hissa_responseList);
                sp_landid_hissa.setAdapter(arrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPostResponseError_GetHissaNo(String data) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTaskFragment.terminateExecutionOfBackTaskGetBhoomiLandID();
        mTaskFragment.terminateExecutionOf_GetSurnocNo();
        mTaskFragment.terminateExecutionOf_GetHissaNo();
    }
}