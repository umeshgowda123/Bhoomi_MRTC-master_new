package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.MPD_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.model.MPD_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.MPD_TABLE;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MutationPendencyDetails extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {


    private MaterialBetterSpinner sp_ped_district;
    private MaterialBetterSpinner sp_ped_taluk;
    private MaterialBetterSpinner sp_ped_hobli;
    private MaterialBetterSpinner sp_ped_village;


    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

    private int pdistrict_id;
    private int ptaluk_id;
    private int phobli_id;
    private int pvillage_id;

    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    PariharaIndividualReportInteface apiInterface;

    private Button btn_ped_submit;

    String res;
    private List<MPD_RES_Data> MPD_RES_Data;

    ArrayAdapter<String> defaultArrayAdapter;
    private String language;

    Call<BHOOMI_API_Response> call;

    String tokenType, accessToken;
    private RtcViewInfoBackGroundTaskFragment mTaskFragment;

    int AppType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation_pendency_details);

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

        sp_ped_district =  findViewById(R.id.sp_ped_district);
        sp_ped_taluk =  findViewById(R.id.sp_ped_taluk);
        sp_ped_hobli =  findViewById(R.id.sp_ped_hobli);
        sp_ped_village =  findViewById(R.id.sp_ped_village);

        btn_ped_submit =  findViewById(R.id.btn_ped_submit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Intent i = getIntent();
        AppType = i.getIntExtra("AppType", 0);

        defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_ped_district.setAdapter(defaultArrayAdapter);
        sp_ped_taluk.setAdapter(defaultArrayAdapter);
        sp_ped_hobli.setAdapter(defaultArrayAdapter);
        sp_ped_village.setAdapter(defaultArrayAdapter);


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
                        sp_ped_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        onClickAction();
        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }

        progressDialog = new ProgressDialog(MutationPendencyDetails.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);

//        if (mTaskFragment.isTaskExecuting) {
//            progressBar = findViewById(R.id.progress_circular);
//            if (progressBar != null)
//                progressBar.setVisibility(View.VISIBLE);
//        }
//        dataBaseHelper =
//                Room.databaseBuilder(getApplicationContext(),
//                        DataBaseHelper.class, getString(R.string.db_name)).build();
//        Observable<String> stringObservable;
//        stringObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMaintenanceStatus(4));
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
//                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MutationPendencyDetails.this).create();
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

    private void onClickAction() {

        sp_ped_district.setOnItemClickListener((parent, view, position, id) -> {
            sp_ped_taluk.setText("");
            sp_ped_hobli.setText("");
            sp_ped_village.setText("");
            sp_ped_taluk.setAdapter(defaultArrayAdapter);
            sp_ped_hobli.setAdapter(defaultArrayAdapter);
            sp_ped_village.setAdapter(defaultArrayAdapter);

            pdistrict_id = districtData.get(position).getVLM_DST_ID();
            Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(pdistrict_id)) : dataBaseHelper.daoAccess().getTalukByDistrictIdKannada(String.valueOf(pdistrict_id)));
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
                            sp_ped_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });

        sp_ped_taluk.setOnItemClickListener((parent, view, position, id) -> {
            sp_ped_hobli.setText("");
            sp_ped_village.setText("");
            sp_ped_hobli.setAdapter(defaultArrayAdapter);
            sp_ped_village.setAdapter(defaultArrayAdapter);

            ptaluk_id = talukData.get(position).getVLM_TLK_ID();
            Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(ptaluk_id), String.valueOf(pdistrict_id)) : dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictIdKannada(String.valueOf(ptaluk_id), String.valueOf(pdistrict_id)));
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
                            sp_ped_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_ped_hobli.setOnItemClickListener((parent, view, position, id) -> {
            sp_ped_village.setText("");
            sp_ped_village.setAdapter(defaultArrayAdapter);

            phobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(phobli_id), String.valueOf(ptaluk_id), String.valueOf(pdistrict_id)) : dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String.valueOf(phobli_id), String.valueOf(ptaluk_id), String.valueOf(pdistrict_id)));
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
                            sp_ped_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_ped_village.setOnItemClickListener((parent, view, position, id) -> pvillage_id = villageData.get(position).getVLM_VLG_ID());

        btn_ped_submit.setOnClickListener(v -> {
            String districtName = sp_ped_district.getText().toString().trim();
            String talukName = sp_ped_taluk.getText().toString().trim();
            String hobliName = sp_ped_hobli.getText().toString().trim();
            String villageName = sp_ped_village.getText().toString().trim();

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = sp_ped_district;
                status = true;
                sp_ped_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = sp_ped_taluk;
                status = true;
                sp_ped_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = sp_ped_hobli;
                status = true;
                sp_ped_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = sp_ped_village;
                status = true;
                sp_ped_village.setError(getString(R.string.village_err));
            }
            if (status) {
                focus.requestFocus();
            } else {

                if (isNetworkAvailable()) {


                    //----------------------------------LOCAl DB DATA PRESENT CHECK-----------------------------------------------------------
                    Observable<List<? extends MPD_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMPD_RES(pdistrict_id,ptaluk_id,phobli_id,pvillage_id));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends MPD_RES_Interface>>() {

                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull List<? extends MPD_RES_Interface> mpd_res_interfaces_List) {

                                    MPD_RES_Data = (List<MPD_RES_Data>) mpd_res_interfaces_List;
                                    if (mpd_res_interfaces_List.size()!=0) {
                                        for (int i = 0; i <= mpd_res_interfaces_List.size()-1; i++) {

                                            String MPD_RES = MPD_RES_Data.get(0).getMDP_RES();
                                            if (MPD_RES.equals("<NewDataSet />")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MutationPendencyDetails.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.no_data_found_for_this_record))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);

                                            }else {
                                                Intent intent = new Intent(MutationPendencyDetails.this, ShowMutationPendencyDetails.class);
                                                intent.putExtra("ped_response_data", MPD_RES);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                    else {
                                        progressDialog.show();
                                        mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));
//                                        Get_MutationPendencyDetailsResponse();
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                    //---------------------------------------------------------------------------------------------

                } else {
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

    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(MutationPendencyDetails.this).create();
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

    public List<MPD_TABLE> loadData() {
//        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<MPD_TABLE> mpd_tables_arr = new ArrayList<>();

        try {
            MPD_TABLE mpd_table = new MPD_TABLE();
            mpd_table.setMPD_DST_ID(pdistrict_id);
            mpd_table.setMPD_TLK_ID(ptaluk_id);
            mpd_table.setMPD_HBL_ID(phobli_id);
            mpd_table.setMPD_VLG_ID(pvillage_id);
            mpd_table.setMPD_RES(res);
            mpd_tables_arr.add(mpd_table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mpd_tables_arr;
    }


    public void createMPDData(final List<MPD_TABLE> MPD_List) {
        Observable<Long[]> insertMPDData = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMPDData(MPD_List));
        insertMPDData
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

                    }
                });
    }

    private void deleteAllResponse(List<MPD_TABLE> MPD_List) {

        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResponse());
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
                        createMPDData(MPD_List);
                    }
                });
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
    protected void onStop() {
        super.onStop();
        if(call != null && call.isExecuted()) {
            call.cancel();
        }
    }

    //______________________________________________________________________IMPLEMENTED METHODS____________________________________________________

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
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        accessToken = AccessToken;
        tokenType = TokenType;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MutationPendencyDetails.this, R.style.MyDialogTheme);
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
                ClsAppLgs objClsAppLgs = new ClsAppLgs();
                objClsAppLgs.setAppID(1);
                objClsAppLgs.setAppType(AppType);
                objClsAppLgs.setIPAddress("");

                mTaskFragment.startBackgroundTask_AppLgs(objClsAppLgs, getString(R.string.rest_service_url), tokenType, accessToken);

//                JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
//                mTaskFragment.startBackgroundTask_GetDetails_VilWise(jsonObject, getString(R.string.rest_service_url), tokenType, accessToken);

            } catch (Exception e){
                e.printStackTrace();
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
        progressDialog.show();
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {
        Get_MutationPendencyDetailsResponse(tokenType, accessToken);
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {
        Get_MutationPendencyDetailsResponse(tokenType, accessToken);
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

    public void Get_MutationPendencyDetailsResponse(String token_type, String token){

        apiInterface = AuthorizationClient.getClient(getResources().getString(R.string.rest_service_url),token_type,token).create(PariharaIndividualReportInteface.class);
        call = apiInterface.getMutationPendingDetails(pdistrict_id, ptaluk_id, phobli_id, pvillage_id);
        call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {

                if (response.isSuccessful()) {
                    BHOOMI_API_Response result = response.body();
                    progressDialog.dismiss();

                    assert result != null;
                    res = result.getBhoomI_API_Response();

                    if (res.equals("<NewDataSet />")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MutationPendencyDetails.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.no_data_found_for_this_record))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {

                        //---------DB INSERT-------
                        Observable<Integer> noOfRows;
                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsMPDTbl());
                        noOfRows
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {


                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull Integer integer) {
                                        List<MPD_TABLE> MPD_List = loadData();
                                        if (integer < 6) {
                                            createMPDData(MPD_List);
                                        } else {
                                            deleteAllResponse(MPD_List);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(MutationPendencyDetails.this, ShowMutationPendencyDetails.class);
                                        intent.putExtra("ped_response_data", result.getBhoomI_API_Response());
                                        startActivity(intent);
                                    }
                                });
                        //---------------------------------------------------------------------------------------------

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.menu_item_history)
        {
            Intent intent = new Intent(MutationPendencyDetails.this, Serach_History.class);
            intent.putExtra("APPType", AppType);
            startActivity(intent);

        }
        return  super.onOptionsItemSelected(item);
    }
}