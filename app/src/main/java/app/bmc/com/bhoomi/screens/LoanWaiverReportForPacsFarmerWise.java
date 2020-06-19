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
import app.bmc.com.bhoomi.model.PacsThreadSafeSingletonFarmerWiseClass;
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

public class LoanWaiverReportForPacsFarmerWise extends AppCompatActivity {

    private MaterialBetterSpinner sp_pacsdist;
    private MaterialBetterSpinner sp_pacstlk;
    private MaterialBetterSpinner sp_pacs_hbl;
    private MaterialBetterSpinner sp_pacs_vlg;
    private Button btn_pacs_submit;

    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

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
        setContentView(R.layout.activity_loan_waiver_report_for_pacs_farmer_wise);

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

        sp_pacsdist =  findViewById(R.id.sp_pacsdist);
        sp_pacstlk =  findViewById(R.id.sp_pacstlk);
        sp_pacs_hbl =  findViewById(R.id.sp_pacs_hbl);
        sp_pacs_vlg =  findViewById(R.id.sp_pacs_vlg);
        btn_pacs_submit =  findViewById(R.id.btn_pacs_submit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_pacsdist.setAdapter(defaultArrayAdapter);
        sp_pacstlk.setAdapter(defaultArrayAdapter);
        sp_pacs_hbl.setAdapter(defaultArrayAdapter);
        sp_pacs_vlg.setAdapter(defaultArrayAdapter);

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
                        sp_pacsdist.setAdapter(districtArrayAdapter);
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

        sp_pacsdist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_pacstlk.setText("");
                sp_pacs_hbl.setText("");
                sp_pacs_vlg.setText("");
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
                                ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<TalukModelInterface>(LoanWaiverReportForPacsFarmerWise.this,
                                        android.R.layout.simple_list_item_single_choice, talukData);
                                sp_pacstlk.setAdapter(talukArrayAdapter);
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


        sp_pacstlk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_pacs_hbl.setText("");
                sp_pacs_vlg.setText("");
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
                                ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<HobliModelInterface>(LoanWaiverReportForPacsFarmerWise.this,
                                        android.R.layout.simple_list_item_single_choice, hobliData);
                                sp_pacs_hbl.setAdapter(hobliArrayAdapter);
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

        sp_pacs_hbl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_pacs_vlg.setText("");
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
                                ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<VillageModelInterface>(LoanWaiverReportForPacsFarmerWise.this,
                                        android.R.layout.simple_list_item_single_choice, villageData);
                                sp_pacs_vlg.setAdapter(villageArrayAdapter);
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

        sp_pacs_vlg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                village_id = villageData.get(position).getVLM_VLG_ID();

            }
        });

        btn_pacs_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String districtName = sp_pacsdist.getText().toString().trim();
                String talukName = sp_pacstlk.getText().toString().trim();
                String hobliName = sp_pacs_hbl.getText().toString().trim();
                String villageName = sp_pacs_vlg.getText().toString().trim();

                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(districtName)) {
                    focus = sp_pacsdist;
                    status = true;
                    sp_pacsdist.setError(getString(R.string.district_err));
                } else if (TextUtils.isEmpty(talukName)) {
                    focus = sp_pacstlk;
                    status = true;
                    sp_pacstlk.setError(getString(R.string.taluk_err));
                } else if (TextUtils.isEmpty(hobliName)) {
                    focus = sp_pacs_hbl;
                    status = true;
                    sp_pacs_hbl.setError(getString(R.string.hobli_err));
                } else if (TextUtils.isEmpty(villageName)) {
                    focus = sp_pacs_vlg;
                    status = true;
                    sp_pacs_vlg.setError(getString(R.string.village_err));
                }
                if (status) {
                    focus.requestFocus();
                } else {

                    if (isNetworkAvailable()) {
                        progressDialog = new ProgressDialog(LoanWaiverReportForPacsFarmerWise.this);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getPacsLoanWaiverReportFramerWise(Constants.REPORT_SERVICE_USER_NAME,
                                Constants.REPORT_SERVICE_PASSWORD, district_id, taluk_id, hobli_id, village_id);
                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                if (response.isSuccessful()) {
                                    PariharaIndividualDetailsResponse result = response.body();
                                    progressDialog.dismiss();

                                    sp_pacsdist.setText("");
                                    sp_pacstlk.setText("");
                                    sp_pacs_hbl.setText("");
                                    sp_pacs_vlg.setText("");
                                    String s = result.getGetLoanWaiverReportPACS_FarmerwiseResult();
                                    Log.d("RESPONSE_DATA",s);

                                    if (s.equals("<NewDataSet />")) {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoanWaiverReportForPacsFarmerWise.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Report Found For this Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        PacsThreadSafeSingletonFarmerWiseClass.getInstance().setResponse(result.getGetLoanWaiverReportPACS_FarmerwiseResult());
                                        Intent intent = new Intent(LoanWaiverReportForPacsFarmerWise.this, ShowLoanWaiverReportPacsFarmerWise.class);
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
        AlertDialog alertDialog = new AlertDialog.Builder(LoanWaiverReportForPacsFarmerWise.this).create();
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
