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

public class LoanWaiverReportForCommercialFramerWise extends AppCompatActivity {


    private MaterialBetterSpinner spinner_district;
    private MaterialBetterSpinner spinner_taluk;
    private MaterialBetterSpinner spinner_hobli;
    private MaterialBetterSpinner spinner_village;
    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;
    private Button btn_submit;
    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    private int district_id;
    private int taluk_id;
    private int hobli_id;
    private int village_id;

    PariharaIndividualReportInteface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_waiver_report_for_commercial_framer_wise);

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

        spinner_district = findViewById(R.id.spinner_district);
        spinner_taluk = findViewById(R.id.spinner_taluk);
        spinner_hobli = findViewById(R.id.spinner_hobli);
        spinner_village = findViewById(R.id.spinner_village);
        btn_submit = findViewById(R.id.btn_submit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();


        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        spinner_district.setAdapter(defaultArrayAdapter);
        spinner_taluk.setAdapter(defaultArrayAdapter);
        spinner_hobli.setAdapter(defaultArrayAdapter);
        spinner_village.setAdapter(defaultArrayAdapter);

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
                        spinner_district.setAdapter(districtArrayAdapter);
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

        spinner_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_taluk.setText("");
                spinner_hobli.setText("");
                spinner_village.setText("");
                district_id = districtData.get(position).getVLM_DST_ID();
                Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(new Callable<List<? extends TalukModelInterface>>() {

                    @Override
                    public List<? extends TalukModelInterface> call() {
                        return dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id));
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
                                ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<TalukModelInterface>(LoanWaiverReportForCommercialFramerWise.this,
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


            }
        });


        spinner_taluk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_hobli.setText("");
                spinner_village.setText("");
                taluk_id = talukData.get(position).getVLM_TLK_ID();
                Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends HobliModelInterface>>() {

                    @Override
                    public List<? extends HobliModelInterface> call() {
                        return dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id));
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
                                ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<HobliModelInterface>(LoanWaiverReportForCommercialFramerWise.this,
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

            }
        });

        spinner_hobli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_village.setText("");
                hobli_id = hobliData.get(position).getVLM_HBL_ID();
                Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends VillageModelInterface>>() {

                    @Override
                    public List<? extends VillageModelInterface> call() {
                        return dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id));
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
                                ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<VillageModelInterface>(LoanWaiverReportForCommercialFramerWise.this,
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

            }
        });

        spinner_village.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                village_id = villageData.get(position).getVLM_VLG_ID();

            }
        });




        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String districtName = spinner_district.getText().toString().trim();
                String talukName = spinner_taluk.getText().toString().trim();
                String hobliName = spinner_hobli.getText().toString().trim();
                String villageName = spinner_village.getText().toString().trim();

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
                }
                if (status) {
                    focus.requestFocus();
                } else {

                    if (isNetworkAvailable()) {
                        progressDialog = new ProgressDialog(LoanWaiverReportForCommercialFramerWise.this);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getLoanWaiverReportFramerWise(Constants.REPORT_SERVICE_USER_NAME,
                                Constants.REPORT_SERVICE_PASSWORD, district_id, taluk_id, hobli_id, village_id);
                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                if (response.isSuccessful()) {
                                    PariharaIndividualDetailsResponse result = response.body();
                                    progressDialog.dismiss();

                                    spinner_district.setText("");
                                    spinner_taluk.setText("");
                                    spinner_hobli.setText("");
                                    spinner_village.setText("");

                                    String s = result.getGetLoanWaiverReportBANK_FarmerwiseResult();
                                    Log.d("RESPONSE_DATA", s);
                                    if (s.equals("<NewDataSet />")) {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoanWaiverReportForCommercialFramerWise.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Report Found For this Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        Intent intent = new Intent(LoanWaiverReportForCommercialFramerWise.this, ShowLoanWaiverReportFarmerWise.class);
                                        intent.putExtra("farmer_response_data", result.getGetLoanWaiverReportBANK_FarmerwiseResult());
                                        startActivity(intent);
                                    }
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
        AlertDialog alertDialog = new AlertDialog.Builder(LoanWaiverReportForCommercialFramerWise.this).create();
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
