package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.BranchNameModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.ClsLoanWaiverReportPacs_Branchwise;
import app.bmc.com.BHOOMI_MRTC.model.PacsBankMasterData;
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

public class LoanWaiverReportForPacsBranchWsie extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private MaterialBetterSpinner sp_pacs_dist;
    private MaterialBetterSpinner sp_pacs_bank;
  //  private MaterialBetterSpinner csp_pacs_branch;
    private AutoCompleteTextView etPacsBranchName;
    private int district_id;
    private String dist_name;
    private String bank_name;
    private String branch_name;
    private int branch_code;
    private Button btnShowPacsDetails;
    private List<DistrictModelInterface> districtData;

    private List<BranchNameModelInterface> branchData;

    PariharaIndividualReportInteface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_waiver_report_for_pacs_branch_wsie);

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


        sp_pacs_dist = findViewById(R.id.sp_pacs_dist);
        sp_pacs_bank = findViewById(R.id.sp_pacs_bank);
     //   sp_pacs_branch =  findViewById(R.id.sp_pacs_branch);
        etPacsBranchName = findViewById(R.id.etPacsBranchName);
        btnShowPacsDetails =  findViewById(R.id.btnShowPacsDetails);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_pacs_dist.setAdapter(defaultArrayAdapter);
        sp_pacs_bank.setAdapter(defaultArrayAdapter);
        //sp_pacs_branch.setAdapter(defaultArrayAdapter);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        loadBankMasterData();

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
                        sp_pacs_dist.setAdapter(districtArrayAdapter);
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadBankMasterData() {

        Observable<Integer> noOfRows = Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() {
                return dataBaseHelper.daoAccess().getNumberOfROwsFromBankMaster();
            }
        });
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == 0) {
                            List<PacsBankMasterData> bank_master_list = loadDataFromCsv();
                            createMasterData(bank_master_list);

                        } else {
                            Log.i("Loaded", "Already Loaded");

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
    }

    private void getBankDetailsList(int district_id) {
        dataBaseHelper = Room.databaseBuilder(getApplicationContext(),
                DataBaseHelper.class, getString(R.string.db_name)).build();

        Observable<List<String>> noOfRows = Observable.fromCallable(new Callable<List<String>>() {

            @Override
            public List<String> call() {
                return dataBaseHelper.daoAccess().getPacsBankNames(district_id);

            }
        });
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> bankmasterDataDetails) {

                        ArrayList<String> clist =  new ArrayList<>();

                        if (bankmasterDataDetails.size() > 0) {
                            for (int i = 0; i < bankmasterDataDetails.size(); i++) {
                                bank_name = bankmasterDataDetails.get(i).toString();
                                clist.add(bank_name);
                            }
                        }


                        ArrayAdapter<String> bankArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, clist);
                        sp_pacs_bank.setAdapter(bankArrayAdapter);
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

    public List<PacsBankMasterData> loadDataFromCsv() {
        List<PacsBankMasterData> bank_list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String mLine;


            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open("bank_master.csv"), "UTF-8"));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String data[] = mLine.split(",");
                    PacsBankMasterData bankData = new PacsBankMasterData();
                    bankData.setBNK_ID(Integer.valueOf(data[0]));
                    bankData.setBNK_BHM_DC_CDE(Integer.valueOf(data[1]));
                    bankData.setBNK_BHM_DC_NME(data[2]);
                    bankData.setBNK_BHM_TLK_CDE(Integer.valueOf(data[3]));
                    bankData.setBNK_BHM_TLK_NME(data[4]);
                    bankData.setBNK_NME_EN(data[5]);
                    bankData.setBNK_BRNCH_CDE(data[6]);
                    bankData.setBnk_Brnch_Nme_Eng(data[7]);
                    bank_list.add(bankData);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bank_list;
    }

    public void createMasterData(final List<PacsBankMasterData> bankMasterList) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(new Callable<Long[]>() {

            @Override
            public Long[] call() {
                return dataBaseHelper.daoAccess().insertPacsBankMasterData(bankMasterList);
            }
        });
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.i("Inserted", longs + " ");


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


    public void getBranchNameDetails(int district_id, String bank_name)
    {
        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Observable<List<? extends BranchNameModelInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends BranchNameModelInterface>>() {

            @Override
            public List<? extends BranchNameModelInterface> call() {
                return dataBaseHelper.daoAccess().getPacsBranchNameList(district_id, bank_name);
            }
        });
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends BranchNameModelInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends BranchNameModelInterface> branch_list) {

                        branchData = (List<BranchNameModelInterface>) branch_list;
                        ArrayAdapter<BranchNameModelInterface> branchArrayAdapter = new ArrayAdapter<BranchNameModelInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, branchData);
                     //   sp_pacs_branch.setAdapter(branchArrayAdapter);

                        etPacsBranchName.setThreshold(1);
                        etPacsBranchName.setAdapter(branchArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onClickAction() {

        sp_pacs_dist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                district_id = districtData.get(position).getVLM_DST_ID();
                Log.d("district_id",""+district_id);
                getBankDetailsList(district_id);
                sp_pacs_bank.setText("");
            }
        });


        sp_pacs_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bank_name =  sp_pacs_bank.getText().toString().trim();
                Log.d("bank_name",""+bank_name);
                getBranchNameDetails(district_id, bank_name);
            }
        });

        etPacsBranchName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etPacsBranchName.showDropDown();
                return false;
            }
        });

        etPacsBranchName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                branch_name =  etPacsBranchName.getText().toString().trim();
                branch_code = branchData.get(position).getBNK_BRNCH_CDE();
                Log.d("branch_name",""+branch_name);
                Log.d("branch_code",""+branch_code);
            }
        });


        btnShowPacsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  d_name =  sp_pacs_dist.getText().toString().trim();
                String  b_name = sp_pacs_bank.getText().toString().trim();
                String bran_name =  etPacsBranchName.getText().toString().trim();
                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(d_name)) {
                    focus = sp_pacs_dist;
                    status = true;
                    sp_pacs_dist.setError(getString(R.string.district_err));
                } else if (TextUtils.isEmpty(b_name)) {
                    focus = sp_pacs_bank;
                    status = true;
                    sp_pacs_bank.setError(getString(R.string.bnk_error));
                }else if (TextUtils.isEmpty(bran_name)) {
                    focus = etPacsBranchName;
                    status = true;
                    etPacsBranchName.setError(getString(R.string.bank_branch_err_name));
                }
                if (status) {
                    focus.requestFocus();
                } else {
                    if (isNetworkAvailable()) {

                        Log.d("bank_name",""+bank_name);
                        if (bank_name.equals("Karnataka State Co.oparative Agriculture & Rural Development Bank Ltd")){
                            bank_name = "Karnataka State Co.oparative Agriculture & Rural Development Bank Ltd.,";
                            Log.d("bank_name",""+bank_name);
                        }

                        ClsLoanWaiverReportPacs_Branchwise cobj =  new ClsLoanWaiverReportPacs_Branchwise();
                        cobj.setDISTRICT_CODE(district_id);
                        cobj.setBANK_NAME(bank_name);
                        cobj.setBRANCH_CODE(String.valueOf(branch_code));
                        getLoanReportDataFromBankDetails(cobj);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void getLoanReportDataFromBankDetails(ClsLoanWaiverReportPacs_Branchwise clsLoanWaiverReportBANK_branchwise) {
        final ProgressDialog progressDialog = new ProgressDialog(LoanWaiverReportForPacsBranchWsie.this);
        progressDialog.setMessage(getString(R.string.req_msg));
        progressDialog.setCancelable(false);
        progressDialog.show();
        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
        Call<PariharaIndividualDetailsResponse> call = apiInterface.getLoanWaiverReportPACS_Branchwise(Constants.REPORT_SERVICE_USER_NAME,
                Constants.REPORT_SERVICE_PASSWORD,clsLoanWaiverReportBANK_branchwise);
        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    sp_pacs_dist.setText("");
                    sp_pacs_bank.setText("");
                    etPacsBranchName.setText("");
                    PariharaIndividualDetailsResponse result = response.body();
                    String s = result.getGetLoanWaiverReportPACS_BranchwiseResult();
                    Log.d("response_data", s);

                    if (s.equals("<NewDataSet />")) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoanWaiverReportForPacsBranchWsie.this, R.style.MyDialogTheme);
                        builder.setTitle("STATUS")
                                .setMessage("No Report Found For this Record")
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {
                        Intent intent = new Intent(LoanWaiverReportForPacsBranchWsie.this, ShowLoanWaiverPacsReportBranchWise.class);
                        intent.putExtra("pacs_branch_response_data", result.getGetLoanWaiverReportPACS_BranchwiseResult());
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
