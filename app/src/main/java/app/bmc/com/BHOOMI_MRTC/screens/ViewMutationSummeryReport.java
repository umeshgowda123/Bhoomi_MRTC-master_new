package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.MSR_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.MSR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.MS_REPORT_TABLE;
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

public class ViewMutationSummeryReport extends AppCompatActivity {


    private MaterialBetterSpinner sp_sum_district;
    private MaterialBetterSpinner sp_sum_taluk;
    private MaterialBetterSpinner sp_sum_hobli;
    private MaterialBetterSpinner sp_sum_village;
    private EditText etSurveyNumber;
    private Button btnSumSubmit;

    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

    private  String surveyNumber;

    private int district_id;
    private int taluk_id;
    private int hobli_id;
    private int village_id;

    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    PariharaIndividualReportInteface apiInterface;

    String s;
    private List<MSR_RES_Data> MSR_RES_Data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mutation_summery_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        sp_sum_district = findViewById(R.id.sp_sum_district);
        sp_sum_taluk = findViewById(R.id.sp_sum_taluk);
        sp_sum_hobli = findViewById(R.id.sp_sum_hobli);
        sp_sum_village  =  findViewById(R.id.sp_sum_village);
        etSurveyNumber = findViewById(R.id.etSurveyNumber);
        btnSumSubmit =  findViewById(R.id.btnSumSubmit);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_sum_district.setAdapter(defaultArrayAdapter);
        sp_sum_taluk.setAdapter(defaultArrayAdapter);
        sp_sum_hobli.setAdapter(defaultArrayAdapter);
        sp_sum_village.setAdapter(defaultArrayAdapter);


        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDistinctDistricts());
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
                        sp_sum_district.setAdapter(districtArrayAdapter);
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

        sp_sum_district.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_taluk.setText("");
            sp_sum_hobli.setText("");
            sp_sum_village.setText("");
            district_id = districtData.get(position).getVLM_DST_ID();
            Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id)));
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
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(ViewMutationSummeryReport.this,
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            sp_sum_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });


        sp_sum_taluk.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_hobli.setText("");
            sp_sum_village.setText("");
            taluk_id = talukData.get(position).getVLM_TLK_ID();
            Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id)));
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
                            ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(ViewMutationSummeryReport.this,
                                    android.R.layout.simple_list_item_single_choice, hobliData);
                            sp_sum_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_sum_hobli.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_village.setText("");
            hobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)));
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
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(ViewMutationSummeryReport.this,
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            sp_sum_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_sum_village.setOnItemClickListener((parent, view, position, id) -> village_id = villageData.get(position).getVLM_VLG_ID());


        btnSumSubmit.setOnClickListener(v -> {
            String districtName = sp_sum_district.getText().toString().trim();
            String talukName = sp_sum_taluk.getText().toString().trim();
            String hobliName = sp_sum_hobli.getText().toString().trim();
            String villageName = sp_sum_village.getText().toString().trim();
            surveyNumber = etSurveyNumber.getText().toString().trim();

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = sp_sum_district;
                status = true;
                sp_sum_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = sp_sum_taluk;
                status = true;
                sp_sum_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = sp_sum_hobli;
                status = true;
                sp_sum_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = sp_sum_village;
                status = true;
                sp_sum_village.setError(getString(R.string.village_err));
            }else if(TextUtils.isEmpty(surveyNumber))
            {
                focus =  etSurveyNumber;
                status = true;
                etSurveyNumber.setError(getString(R.string.survey_no_err));
            }
            if (status) {
                focus.requestFocus();
            } else {

                if (isNetworkAvailable()) {
                    //----------------------------------LOCAl DB DATA PRESENT CHECK-----------------------------------------------------------
                    dataBaseHelper =
                            Room.databaseBuilder(getApplicationContext(),
                                    DataBaseHelper.class, getString(R.string.db_name)).build();
                    Observable<List<? extends MSR_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMSR_RES(district_id,taluk_id,hobli_id,village_id,surveyNumber));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends MSR_RES_Interface>>() {

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends MSR_RES_Interface> msr_res_interfaces_List) {

                                    Log.d("msr_res_interfaces",msr_res_interfaces_List.size()+"");

                                    MSR_RES_Data = (List<MSR_RES_Data>) msr_res_interfaces_List;
                                    if (msr_res_interfaces_List.size()!=0) {
                                        Log.d("CHECK","Fetching from local");
                                        for (int i = 0; i <= msr_res_interfaces_List.size()-1; i++) {

                                            String MSR_RES = MSR_RES_Data.get(0).getMSR_RES();
                                            Log.d("MSR_RES",MSR_RES+"");

                                            Intent intent = new Intent(ViewMutationSummeryReport.this, ShowMutationSummeryReport.class);
                                            intent.putExtra("html_response_data", MSR_RES);
                                            startActivity(intent);
                                        }
                                    }
                                    else {
                                        Log.d("MSR_RES","Fetching From Server");

                                        progressDialog = new ProgressDialog(ViewMutationSummeryReport.this);
                                        progressDialog.setMessage("Please Wait");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();
                                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getMutationSummeryReportDetails(Constants.REPORT_SERVICE_USER_NAME,
                                                Constants.REPORT_SERVICE_PASSWORD, district_id, taluk_id, hobli_id, village_id,Integer.parseInt(surveyNumber));
                                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                            @Override
                                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                                if (response.isSuccessful()) {
                                                    PariharaIndividualDetailsResponse result = response.body();
                                                    progressDialog.dismiss();

                                                    sp_sum_district.setText("");
                                                    sp_sum_taluk.setText("");
                                                    sp_sum_hobli.setText("");
                                                    sp_sum_village.setText("");
                                                    etSurveyNumber.setText("");
                                                    assert result != null;
                                                    s = result.getGetMutationSummaryReportResult();
                                                    if (s.equals("")) {
                                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ViewMutationSummeryReport.this, R.style.MyDialogTheme);
                                                        builder.setTitle("STATUS")
                                                                .setMessage("No Data Found")
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
                                                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsMSR());
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
                                                                            List<MS_REPORT_TABLE> MPD_List = loadData();
                                                                            createMSRTable_Data(MPD_List);

                                                                        } else {
                                                                            Log.d("intValueELSE", integer + "");
                                                                            deleteByID(0);
//                                                                          deleteByID(MPD_RES_Data.size()-1);
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {
                                                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void onComplete() {
                                                                        progressDialog.dismiss();
                                                                        Log.d("MPD_RES","Fetching From Server");
                                                                        Intent intent = new Intent(ViewMutationSummeryReport.this, ShowMutationSummeryReport.class);
                                                                        intent.putExtra("html_response_data", result.getGetMutationSummaryReportResult());
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                        //---------------------------------------------------------------------------------------------

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                                                call.cancel();
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
//
//                                        Intent intent = new Intent(ViewMutationSummeryReport.this, ShowMutationSummeryReport.class);
//                                        intent.putExtra("html_response_data", result.getGetMutationSummaryReportResult());
//                                        startActivity(intent);
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
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(ViewMutationSummeryReport.this).create();
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

    public List<MS_REPORT_TABLE> loadData() {
        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<MS_REPORT_TABLE> ms_report_tables_arr = new ArrayList<>();

        try {
            MS_REPORT_TABLE ms_report_table = new MS_REPORT_TABLE();
            ms_report_table.setMSR_DST_ID(district_id);
            ms_report_table.setMSR_TLK_ID(taluk_id);
            ms_report_table.setMSR_HBL_ID(hobli_id);
            ms_report_table.setMSR_VLG_ID(village_id);
            ms_report_table.setMSR_SNO(surveyNumber);
            ms_report_table.setMSR_RES(s);
            ms_report_tables_arr.add(ms_report_table);

        } catch (Exception e) {
            Log.d("Exception", e + "");
        }

        return ms_report_tables_arr;
    }
    public void createMSRTable_Data(final List<MS_REPORT_TABLE> MSR_Table) {
        Observable<Long[]> insertMPDData = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMSRTable_Data(MSR_Table));
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
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteByIdMSR(id));
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
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllResponse());
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
