package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.MPD_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.MPD_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.MPD_TABLE;
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

public class MutationPendencyDetails extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation_pendency_details);

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

        sp_ped_district =  findViewById(R.id.sp_ped_district);
        sp_ped_taluk =  findViewById(R.id.sp_ped_taluk);
        sp_ped_hobli =  findViewById(R.id.sp_ped_hobli);
        sp_ped_village =  findViewById(R.id.sp_ped_village);

        btn_ped_submit =  findViewById(R.id.btn_ped_submit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

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
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends DistrictModelInterface> mst_vlmList) {

                        districtData = (List<DistrictModelInterface>) mst_vlmList;
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_ped_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        onClickAction();

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<String> stringObservable;
        stringObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMaintenanceStatus(4));
        stringObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String str) {
                        Log.d("valStr", ""+str);
                        if (str.equals("false")){
                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MutationPendencyDetails.this).create();
                            alertDialog.setTitle(getString(R.string.status));
                            alertDialog.setMessage(getString(R.string.this_service_is_under_maintenance));
                            alertDialog.setCancelable(false);
                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.ok), (dialog, which) -> onBackPressed());
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends TalukModelInterface> talukDataList) {
                            talukData = (List<TalukModelInterface>) talukDataList;
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            sp_ped_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

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
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends HobliModelInterface> hobliDataList) {
                            hobliData = (List<HobliModelInterface>) hobliDataList;
                            ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_single_choice, hobliData);
                            sp_ped_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

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
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends VillageModelInterface> villageDataList) {
                            villageData = (List<VillageModelInterface>) villageDataList;
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            sp_ped_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

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
                    dataBaseHelper =
                            Room.databaseBuilder(getApplicationContext(),
                                    DataBaseHelper.class, getString(R.string.db_name)).build();
                    Observable<List<? extends MPD_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMPD_RES(pdistrict_id,ptaluk_id,phobli_id,pvillage_id));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends MPD_RES_Interface>>() {

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends MPD_RES_Interface> mpd_res_interfaces_List) {

                                    Log.d("mpd_res_interfaces",mpd_res_interfaces_List.size()+"");

                                    MPD_RES_Data = (List<MPD_RES_Data>) mpd_res_interfaces_List;
                                    if (mpd_res_interfaces_List.size()!=0) {
                                        Log.d("CHECK","Fetching from local");
                                        for (int i = 0; i <= mpd_res_interfaces_List.size()-1; i++) {

                                            String MPD_RES = MPD_RES_Data.get(0).getMDP_RES();
                                            Log.d("MPD_RES", MPD_RES + "");
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
                                        progressDialog = new ProgressDialog(MutationPendencyDetails.this);
                                        progressDialog.setMessage(getString(R.string.please_wait));
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getMutationPendingDetails(Constants.REPORT_SERVICE_USER_NAME,
                                                Constants.REPORT_SERVICE_PASSWORD, pdistrict_id, ptaluk_id, phobli_id, pvillage_id);
                                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                            @Override
                                            public void onResponse(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Response<PariharaIndividualDetailsResponse> response) {

                                                if (response.isSuccessful()) {
                                                    PariharaIndividualDetailsResponse result = response.body();
                                                    progressDialog.dismiss();

                                                    assert result != null;
                                                    res = result.getGetMutationPendencyDetailsResult();

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
                                                        dataBaseHelper =
                                                                Room.databaseBuilder(getApplicationContext(),
                                                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                                                        Observable<Integer> noOfRows;
                                                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsMPDTbl());
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
                                                                            List<MPD_TABLE> MPD_List = loadData();
                                                                            createMPDData(MPD_List);


                                                                        } else {
                                                                            Log.d("intValueELSE", integer + "");
                                                                            deleteByID(0);
//                                                                            deleteByID(MPD_RES_Data.size()-1);
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
                                                                        Intent intent = new Intent(MutationPendencyDetails.this, ShowMutationPendencyDetails.class);
                                                                        intent.putExtra("ped_response_data", result.getGetMutationPendencyDetailsResult());
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                        //---------------------------------------------------------------------------------------------

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable t) {
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
            Log.d("Exception", e + "");
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
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteByIdMPD(id));
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
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResponse());
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