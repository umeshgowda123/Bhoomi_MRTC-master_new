package app.bmc.com.bhoomi.screens;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;
import java.util.concurrent.Callable;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.api.PariharaIndividualReportInteface;
import app.bmc.com.bhoomi.database.DataBaseHelper;
import app.bmc.com.bhoomi.interfaces.DistrictModelInterface;
import app.bmc.com.bhoomi.interfaces.HobliModelInterface;
import app.bmc.com.bhoomi.interfaces.TalukModelInterface;
import app.bmc.com.bhoomi.interfaces.VillageModelInterface;
import app.bmc.com.bhoomi.model.PariharaIndividualDetailsResponse;
import app.bmc.com.bhoomi.retrofit.PariharaIndividualreportClient;
import app.bmc.com.bhoomi.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadVillageMap extends AppCompatActivity {

    private MaterialBetterSpinner sp_vlg_district;
    private MaterialBetterSpinner sp_vlg_taluk;
    private MaterialBetterSpinner sp_vlg_hobli;
    private MaterialBetterSpinner sp_vlg_village;


    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

    private int mdistrict_id;
    private int mtaluk_id;
    private int mhobli_id;
    private int mvillage_id;

    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    PariharaIndividualReportInteface apiInterface;

    private Button btn_submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_village_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        sp_vlg_district = findViewById(R.id.sp_vlg_district);
        sp_vlg_taluk = findViewById(R.id.sp_vlg_taluk);
        sp_vlg_hobli = findViewById(R.id.sp_vlg_hobli);
        sp_vlg_village = findViewById(R.id.sp_vlg_village);

        btn_submit =  findViewById(R.id.btn_submit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_vlg_district.setAdapter(defaultArrayAdapter);
        sp_vlg_taluk.setAdapter(defaultArrayAdapter);
        sp_vlg_hobli.setAdapter(defaultArrayAdapter);
        sp_vlg_village.setAdapter(defaultArrayAdapter);


        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends DistrictModelInterface>>() {

            @Override
            public List<? extends DistrictModelInterface> call() {
                return dataBaseHelper.daoAccess().getDistinctDistricts();
            }
        });
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
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<DistrictModelInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_vlg_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        onClickAction();
    }

    private void onClickAction() {

        sp_vlg_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_vlg_taluk.setText("");
                sp_vlg_hobli.setText("");
                sp_vlg_village.setText("");
                mdistrict_id = districtData.get(position).getVLM_DST_ID();
                Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(new Callable<List<? extends TalukModelInterface>>() {

                    @Override
                    public List<? extends TalukModelInterface> call() {
                        return dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(mdistrict_id));
                    }
                });
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
                                ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<TalukModelInterface>(getApplicationContext(),
                                        android.R.layout.simple_list_item_single_choice, talukData);
                                sp_vlg_taluk.setAdapter(talukArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        });


        sp_vlg_taluk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_vlg_hobli.setText("");
                sp_vlg_village.setText("");
                mtaluk_id = talukData.get(position).getVLM_TLK_ID();
                Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends HobliModelInterface>>() {

                    @Override
                    public List<? extends HobliModelInterface> call() {
                        return dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(mtaluk_id), String.valueOf(mdistrict_id));
                    }
                });
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
                                ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<HobliModelInterface>(getApplicationContext(),
                                        android.R.layout.simple_list_item_single_choice, hobliData);
                                sp_vlg_hobli.setAdapter(hobliArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });

        sp_vlg_hobli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_vlg_village.setText("");

                mhobli_id = hobliData.get(position).getVLM_HBL_ID();
                Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends VillageModelInterface>>() {

                    @Override
                    public List<? extends VillageModelInterface> call() {
                        return dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(mhobli_id), String.valueOf(mtaluk_id), String.valueOf(mdistrict_id));
                    }
                });
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
                                ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<VillageModelInterface>(getApplicationContext(),
                                        android.R.layout.simple_list_item_single_choice, villageData);
                                sp_vlg_village.setAdapter(villageArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });

        sp_vlg_village.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mvillage_id = villageData.get(position).getVLM_VLG_ID();

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String districtName = sp_vlg_district.getText().toString().trim();
                String talukName = sp_vlg_taluk.getText().toString().trim();
                String hobliName = sp_vlg_hobli.getText().toString().trim();
                String villageName = sp_vlg_village.getText().toString().trim();

                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(districtName)) {
                    focus = sp_vlg_district;
                    status = true;
                    sp_vlg_district.setError(getString(R.string.district_err));
                } else if (TextUtils.isEmpty(talukName)) {
                    focus = sp_vlg_taluk;
                    status = true;
                    sp_vlg_taluk.setError(getString(R.string.taluk_err));
                } else if (TextUtils.isEmpty(hobliName)) {
                    focus = sp_vlg_hobli;
                    status = true;
                    sp_vlg_hobli.setError(getString(R.string.hobli_err));
                } else if (TextUtils.isEmpty(villageName)) {
                    focus = sp_vlg_village;
                    status = true;
                    sp_vlg_village.setError(getString(R.string.village_err));
                }
                if (status) {
                    focus.requestFocus();
                } else {

                    if (isNetworkAvailable()) {
                        progressDialog = new ProgressDialog(DownloadVillageMap.this);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                        Call<PariharaIndividualDetailsResponse> call = apiInterface.downloadVillageMapDetails(Constants.REPORT_SERVICE_USER_NAME,
                                Constants.REPORT_SERVICE_PASSWORD, mdistrict_id, mtaluk_id, mhobli_id, mvillage_id);
                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                if (response.isSuccessful()) {
                                    PariharaIndividualDetailsResponse result = response.body();
                                    progressDialog.dismiss();

                                  /*  sp_vlg_district.setText("");
                                    sp_vlg_taluk.setText("");
                                    sp_vlg_hobli.setText("");
                                    sp_vlg_village.setText("");*/

                                    String s = result.getDownloadVillageMapResult();

                                    s =  s.replace("<Details","");
                                    Log.d("MAP_RESPONSE_DATA", s);
                                    s =  s.replace("</Details","");

                                    Log.d("MAP_RESPONSE_DATA", s);
                                    Intent intent = new Intent(DownloadVillageMap.this, ShowVillageMapDetails.class);
                                    intent.putExtra("map_response_data",s);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                                call.cancel();
                                progressDialog.dismiss();
                            }
                        });

                    } else {
                        selfDestruct();
                    }
                }
            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(DownloadVillageMap.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
