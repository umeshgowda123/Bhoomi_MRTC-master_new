package app.bmc.com.BHOOMI_MRTC.screens;

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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import app.bmc.com.BHOOMI_MRTC.model.Hissa_Response;
import app.bmc.com.BHOOMI_MRTC.model.R_LAND_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestrictionOnLand extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo{

    MaterialBetterSpinner spinner_district;
    MaterialBetterSpinner spinner_taluk;
    MaterialBetterSpinner spinner_hobli;
    MaterialBetterSpinner spinner_village;
    EditText edittext_survey;
    Button btn_go;
    MaterialBetterSpinner spinner_hissa;
    Button btn_go_hissa;
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
    List<Hissa_Response> hissa_responseList;
    String surveyNo;
    RtcViewInfoBackGroundTaskFragment mTaskFragment;
    ProgressBar progressBar;
    String language;
    DataBaseHelper dataBaseHelper;
    TextView tvSetTite;
    ArrayAdapter<String> defaultArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restriction_on_land);

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
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);

        //dbManager = new DBManager(ViewRtcInformation.this);
        spinner_district = findViewById(R.id.spinner_district);
        spinner_taluk = findViewById(R.id.spinner_taluk);
        spinner_hobli = findViewById(R.id.spinner_hobli);
        spinner_village = findViewById(R.id.spinner_village);
        spinner_hissa = findViewById(R.id.spinner_hissa);
        edittext_survey = findViewById(R.id.edittext_survey);
        btn_go = findViewById(R.id.btn_go);
        btn_go_hissa = findViewById(R.id.btn_go_hissa);
        tvSetTite = findViewById(R.id.tvSetTite);
        defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        spinner_taluk.setAdapter(defaultArrayAdapter);
        spinner_hobli.setAdapter(defaultArrayAdapter);
        spinner_village.setAdapter(defaultArrayAdapter);
        spinner_hissa.setAdapter(defaultArrayAdapter);
        spinner_district.setAdapter(defaultArrayAdapter);


        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getDistinctDistricts() : dataBaseHelper.daoAccess().getDistinctDistrictsKannada());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends DistrictModelInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends DistrictModelInterface> mst_vlmList) {

                        districtData = (List<DistrictModelInterface>) mst_vlmList;
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        spinner_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

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

        spinner_district.setOnItemClickListener((parent, view, position, id) -> {
            spinner_taluk.setText("");
            spinner_hobli.setText("");
            spinner_village.setText("");
            spinner_hissa.setText("");
            spinner_taluk.setAdapter(defaultArrayAdapter);
            spinner_hobli.setAdapter(defaultArrayAdapter);
            spinner_village.setAdapter(defaultArrayAdapter);
            spinner_hissa.setAdapter(defaultArrayAdapter);
            edittext_survey.setText("");

            district_id = districtData.get(position).getVLM_DST_ID();
            Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id)) : dataBaseHelper.daoAccess().getTalukByDistrictIdKannada(String.valueOf(district_id)));
            talukDataObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends TalukModelInterface>>() {


                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends TalukModelInterface> talukDataList) {
                            talukData = (List<TalukModelInterface>) talukDataList;
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(RestrictionOnLand.this,
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            spinner_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });


        spinner_taluk.setOnItemClickListener((parent, view, position, id) -> {
            spinner_hobli.setText("");
            spinner_village.setText("");
            spinner_hissa.setText("");
            spinner_hobli.setAdapter(defaultArrayAdapter);
            spinner_village.setAdapter(defaultArrayAdapter);
            spinner_hissa.setAdapter(defaultArrayAdapter);
            edittext_survey.setText("");

            taluk_id = talukData.get(position).getVLM_TLK_ID();
            Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictIdKannada(String.valueOf(taluk_id), String.valueOf(district_id)));
            noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends HobliModelInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends HobliModelInterface> hobliDataList) {
                        hobliData = (List<HobliModelInterface>) hobliDataList;
                        ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(RestrictionOnLand.this,
                                android.R.layout.simple_list_item_single_choice, hobliData);
                        spinner_hobli.setAdapter(hobliArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        });

        spinner_hobli.setOnItemClickListener((parent, view, position, id) -> {
            spinner_village.setText("");
            spinner_hissa.setText("");
            spinner_village.setAdapter(defaultArrayAdapter);
            spinner_hissa.setAdapter(defaultArrayAdapter);
            edittext_survey.setText("");

            hobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)));
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends VillageModelInterface>>() {


                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends VillageModelInterface> villageDataList) {
                            villageData = (List<VillageModelInterface>) villageDataList;
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(RestrictionOnLand.this,
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            spinner_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
        spinner_village.setOnItemClickListener((parent, view, position, id) -> {
            spinner_hissa.setText("");
            spinner_hissa.setAdapter(defaultArrayAdapter);

            village_id = villageData.get(position).getVLM_VLG_ID();
        });

        spinner_hissa.setOnItemClickListener((parent, view, position, id) -> {
            land_no = hissa_responseList.get(position).getLand_code();
            hissa = hissa_responseList.get(position).getHissa_no();
            suroc = hissa_responseList.get(position).getSurnoc();
        }
        );
        btn_go.setOnClickListener(v -> {
            String districtName = spinner_district.getText().toString().trim();
            String talukName = spinner_taluk.getText().toString().trim();
            String hobliName = spinner_hobli.getText().toString().trim();
            String villageName = spinner_village.getText().toString().trim();
            surveyNo = edittext_survey.getText().toString().trim();

            String input = "{" +
                    "\"Bhm_dist_code\": \""+district_id+"\"," +
                    "\"Bhm_taluk_code\": \""+taluk_id+"\"," +
                    "\"Bhm_hobli_code\":\""+hobli_id+"\"," +
                    "\"village_code\": \""+village_id+"\"," +
                    "\"survey_no\": \""+surveyNo+"\"" +
                    "}";

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = spinner_district;
                status = true;
                spinner_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = spinner_taluk;
                status = true;
                spinner_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = spinner_hobli;
                status = true;
                spinner_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = spinner_village;
                status = true;
                spinner_village.setError(getString(R.string.village_err));
            } else if (TextUtils.isEmpty(surveyNo)) {
                focus = edittext_survey;
                status = true;
                edittext_survey.setError(getString(R.string.survey_no_err));
            }
            if (status) {
                focus.requestFocus();
            } else {
//                surveyNo = Integer.parseInt(edittext_survey.getText().toString().trim());
                if (isNetworkAvailable()){
                    try {
                        JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
                        mTaskFragment.startBackgroundTask1(jsonObject, getString(R.string.rest_service_url));
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();

            }
        });
        btn_go_hissa.setOnClickListener(v -> {
            String districtName = spinner_district.getText().toString().trim();
            String talukName = spinner_taluk.getText().toString().trim();
            String hobliName = spinner_hobli.getText().toString().trim();
            String villageName = spinner_village.getText().toString().trim();
            String surveyno = edittext_survey.getText().toString().trim();
            String hissa = spinner_hissa.getText().toString().trim();
            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = spinner_district;
                status = true;
                spinner_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = spinner_taluk;
                status = true;
                spinner_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = spinner_hobli;
                status = true;
                spinner_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = spinner_village;
                status = true;
                spinner_village.setError(getString(R.string.village_err));
            } else if (TextUtils.isEmpty(surveyno)) {
                focus = edittext_survey;
                status = true;
                edittext_survey.setError(getString(R.string.survey_no_err));
            }else if (TextUtils.isEmpty(hissa)) {
                focus = spinner_hissa;
                status = true;
                spinner_hissa.setError(getString(R.string.hissa_err));
            }
            if (status) {
                focus.requestFocus();
            } else {
                Intent intent = new Intent(RestrictionOnLand.this, RestrictionOnLandReport.class);
                intent.putExtra("d_id", district_id + "");
                intent.putExtra("t_id", taluk_id + "");
                intent.putExtra("h_id", hobli_id + "");
                intent.putExtra("v_id", village_id + "");
                intent.putExtra("s_No", surveyNo + "");
                intent.putExtra("s_c", suroc + "");
                intent.putExtra("hi_no", hissa + "");
                startActivity(intent);
            }
        });
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

        if (TextUtils.isEmpty(data) || data.contains("No Data Found")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RestrictionOnLand.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_survey_no))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                        dialog.cancel();
                        edittext_survey.setText("");
                    });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }else {

            //progressDoalog.dismiss();
            XmlToJson xmlToJson = new XmlToJson.Builder(data).build();


            // convert to a formatted Json String
            String formatted = xmlToJson.toFormattedString();
            Object object;
            try {
                JSONObject obj = new JSONObject(formatted.replace("\n", ""));
                JSONObject documentElement = obj.getJSONObject("DocumentElement");
                object = documentElement.get("DS_RTC");
                JSONArray ds_rtc = new JSONArray();
                if (object instanceof JSONObject) {
                    ds_rtc.put(object);
                } else {
                    ds_rtc = (JSONArray) object;
                }
                Type listType = new TypeToken<ArrayList<Hissa_Response>>() {
                }.getType();
                hissa_responseList = new Gson().fromJson(ds_rtc.toString(), listType);
                ArrayAdapter<Hissa_Response> villageArrayAdapter = new ArrayAdapter<>(RestrictionOnLand.this,
                        android.R.layout.simple_list_item_single_choice, hissa_responseList);
                spinner_hissa.setAdapter(villageArrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
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
    public void onPostResponseSuccess4(String data) {

    }

    @Override
    public void onPostResponseError_Task4(String data) {

    }

    @Override
    public void onPreExecute4() {

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
    public void onPostResponseError_FORHISSA(String data, int count) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.contains("CertPathValidatorException")){
            Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_SHORT).show();
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RestrictionOnLand.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage("Server is busy, Please try after sometime")
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPostResponseError_Task2(String data, int count) {
//        if (progressBar != null)
//            progressBar.setVisibility(View.GONE);
//        mTaskFragment.startBackgroundTask1(district_id, taluk_id, hobli_id, village_id, surveyNo, getString(R.string.rest_service_url));
    }

    @Override
    public void onPreExecute2() {

    }

    @Override
    public void onPostResponseSuccess2(String data) {

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(RestrictionOnLand.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage(getString(R.string.please_enable_internet_connection));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.ok), (dialog, which) -> {

        });
        alertDialog.show();
    }

    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTaskFragment.terminateExecutionOfBackgroundTask1();
    }
}
