package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
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

public class LoanWaiverReportForPacsBankWise extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private MaterialBetterSpinner sp_pacs_district;
    private MaterialBetterSpinner sp_pacs_bank;
    private int district_id;
    private String dist_name;
    private String bank_name;
    private Button btnPacsBankDetails;
    private List<DistrictModelInterface> districtData;

    PariharaIndividualReportInteface apiInterface;

    ArrayList<String> clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_waiver_report_for_pacs_bank_wsie);

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

        sp_pacs_district = findViewById(R.id.sp_pacs_district);
        sp_pacs_bank = findViewById(R.id.sp_pacs_bank);
        btnPacsBankDetails =  findViewById(R.id.btnPacsBankDetails);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_pacs_district.setAdapter(defaultArrayAdapter);
        sp_pacs_bank.setAdapter(defaultArrayAdapter);

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
                        sp_pacs_district.setAdapter(districtArrayAdapter);
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

    private void loadBankMasterData() {

        Observable<Integer> noOfRows = Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() {
                return dataBaseHelper.daoAccess().getNumberOfROwsFromPacsBankMaster();
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

    public void getBankDetailsList(int district_id) {
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

                        clist =  new ArrayList<>();

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

    private void onClickAction() {

        sp_pacs_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                district_id = districtData.get(position).getVLM_DST_ID();
                getBankDetailsList(district_id);
                sp_pacs_bank.setText("");
            }
        });



        sp_pacs_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bank_name =  sp_pacs_bank.getText().toString().trim();
                Log.d("bank_name",""+bank_name);
                if (bank_name.equals("Karnataka State Co.oparative Agriculture & Rural Development Bank Ltd")){
                    bank_name = "Karnataka State Co.oparative Agriculture & Rural Development Bank Ltd.,";
                    Log.d("bank_name",""+bank_name);
                }
            }
        });

        btnPacsBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  d_name =  sp_pacs_district.getText().toString().trim();
                String  b_name = sp_pacs_bank.getText().toString().trim();
                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(d_name)) {
                    focus = sp_pacs_bank;
                    status = true;
                    sp_pacs_district.setError(getString(R.string.district_err));
                } else if (TextUtils.isEmpty(b_name)) {
                    focus = sp_pacs_bank;
                    status = true;
                    sp_pacs_bank.setError(getString(R.string.bnk_error));
                }
                if (status) {
                    focus.requestFocus();
                } else {
                    if (isNetworkAvailable()) {
                        Log.d("bank_name",""+bank_name);
                        String values1;
                        values1 = "{" + "\"DISTRICT_CODE\":" + district_id + ","
                                + "\"BANK_NAME\":\"" + bank_name + "\""
                                + "}";
                        try
                        {
                            JsonObject jsonObject = new JsonParser().parse(values1).getAsJsonObject();
                            Log.d("jsonObject", "" + jsonObject);
                            getLoanPacsReportDataFromBankDetails(jsonObject);
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
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


    private void getLoanPacsReportDataFromBankDetails(JsonObject jsonObject) {
        final ProgressDialog progressDialog = new ProgressDialog(LoanWaiverReportForPacsBankWise.this);
        progressDialog.setMessage(getString(R.string.req_msg));
        progressDialog.setCancelable(false);
        progressDialog.show();
        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
        Call<PariharaIndividualDetailsResponse> call = apiInterface.fnGetLoanWaiverPacsReportBankWise(Constants.REPORT_SERVICE_USER_NAME,
                Constants.REPORT_SERVICE_PASSWORD,jsonObject);
        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    sp_pacs_district.setText("");
                    sp_pacs_bank.setText("");
                    PariharaIndividualDetailsResponse result = response.body();
                    String s = result.getGetLoanWaiverReportPACS_BankwiseResult();
                    // Log.d("RESPONSE_DATA", s);
                    if (s.equals("<NewDataSet />")) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoanWaiverReportForPacsBankWise.this, R.style.MyDialogTheme);
                        builder.setTitle("STATUS")
                                .setMessage("No Report Found For this Record")
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {
                        Intent intent = new Intent(LoanWaiverReportForPacsBankWise.this, ShowLoanWaiverPacsReportBankWise.class);
                        intent.putExtra("pacs_response_data", result.getGetLoanWaiverReportPACS_BankwiseResult());
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
