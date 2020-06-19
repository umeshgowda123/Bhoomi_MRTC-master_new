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
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;
import java.util.concurrent.Callable;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.api.PariharaIndividualReportInteface;
import app.bmc.com.bhoomi.database.DataBaseHelper;
import app.bmc.com.bhoomi.interfaces.CalamityInterface;
import app.bmc.com.bhoomi.interfaces.FinancialYearInterface;
import app.bmc.com.bhoomi.interfaces.SeasonInterface;
import app.bmc.com.bhoomi.model.CalamityDetails;
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

public class PariharaBenificiaryReportLandWise extends AppCompatActivity {


    private MaterialBetterSpinner sp_financial_year;
    private MaterialBetterSpinner sp_season;
    private MaterialBetterSpinner sp_calamity;
    private EditText etLAadharNumber;
    private Button btnFetchLandDetails;

    PariharaIndividualReportInteface apiInterface;

    private List<FinancialYearInterface> financialYearData;
    private List<SeasonInterface> seasonData;
    private List<CalamityInterface> calamityData;

    private String financial_year;
    private String season;
    private String calamity;

    private int year_id;
    private int season_id;
    private int calamity_id;

    private String aadhar_no;

    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parihara_benificiary_report_land_wise);

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

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();


        sp_financial_year = findViewById(R.id.sp_financial_year);
        sp_season = findViewById(R.id.sp_season);
        sp_calamity = findViewById(R.id.sp_calamity);

        btnFetchLandDetails = findViewById(R.id.btnFetchLandDetails);
        etLAadharNumber = findViewById(R.id.etLAadharNumber);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_financial_year.setAdapter(defaultArrayAdapter);
        sp_season.setAdapter(defaultArrayAdapter);
        sp_calamity.setAdapter(defaultArrayAdapter);

        loadFinancialYearList();


        sp_financial_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_season.setText("");
                sp_calamity.setText("");
                etLAadharNumber.setText("");
                year_id = financialYearData.get(position).getCode();
                financial_year = sp_financial_year.getText().toString().trim();
                loadSeasonYearList();

            }
        });


        sp_season.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_calamity.setText("");
                season_id = seasonData.get(position).getMSTSEASON_VAL();
                season = sp_season.getText().toString().trim();
                loadCalamityList();

            }
        });

        sp_calamity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                calamity = sp_calamity.getText().toString().trim();
                calamity_id = calamityData.get(position).getMSTCTYPE_ID();

            }
        });


        btnFetchLandDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String financialYear = sp_financial_year.getText().toString().trim();
                String season = sp_season.getText().toString().trim();
                String calamity = sp_calamity.getText().toString().trim();
                aadhar_no = etLAadharNumber.getText().toString().trim();

                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(financialYear)) {
                    focus = sp_financial_year;
                    status = true;
                    sp_financial_year.setError(getString(R.string.financial_year_error));
                } else if (TextUtils.isEmpty(season)) {
                    focus = sp_season;
                    status = true;
                    sp_season.setError(getString(R.string.season_error));
                } else if (TextUtils.isEmpty(calamity)) {
                    focus = sp_calamity;
                    status = true;
                    sp_calamity.setError(getString(R.string.calamity_error));
                }
//                else if (TextUtils.isEmpty(aadhar_no) && aadhar_no.length() != 12)
                else if (TextUtils.isEmpty(aadhar_no))
                {
                    focus = etLAadharNumber;
                    status = true;
                    etLAadharNumber.setError(getString(R.string.aadhaar_number_err));
                }
                //------------------SUSMITA--------------------------
                else if (aadhar_no.length() != 12){
                    focus = etLAadharNumber;
                    status = true;
                    etLAadharNumber.setError("Aadhar Number should be 12 Digit");
                }
                //---------------------------------------------------
                if (status) {
                    focus.requestFocus();
                } else {

                    if (isNetworkAvailable()) {
                        progressDialog = new ProgressDialog(PariharaBenificiaryReportLandWise.this);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getBenificiaryDetailsLandWise(Constants.REPORT_SERVICE_USER_NAME,
                                Constants.REPORT_SERVICE_PASSWORD, aadhar_no, year_id, season_id, calamity_id);
                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                if (response.isSuccessful()) {
                                    PariharaIndividualDetailsResponse result = response.body();
                                    progressDialog.dismiss();

                                    sp_financial_year.setText("");
                                    sp_season.setText("");
                                    sp_calamity.setText("");
                                    etLAadharNumber.setText("");
                                    String s = result.getGetPariharaBeneficiaryPaymentDetailsResult();
                                    Log.d("RESPONSE_DATA", s);

                                    if(s.contains("DistrictName"))
                                    {
                                        Intent intent = new Intent(PariharaBenificiaryReportLandWise.this, ShowPariharaBenificiaryDetailsLandWise.class);
                                        intent.putExtra("response_data", result.getGetPariharaBeneficiaryPaymentDetailsResult());
                                        startActivity(intent);
                                    }else
                                    {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PariharaBenificiaryReportLandWise.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Data Found")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
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

    private void loadFinancialYearList() {

        Observable<List<? extends FinancialYearInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends FinancialYearInterface>>() {

            @Override
            public List<? extends FinancialYearInterface> call() {
                return dataBaseHelper.daoAccess().getDistinctYears();
            }
        });
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends FinancialYearInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends FinancialYearInterface> mst_yearList) {

                        financialYearData = (List<FinancialYearInterface>) mst_yearList;
                        ArrayAdapter<FinancialYearInterface> yearArrayAdapter = new ArrayAdapter<FinancialYearInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, financialYearData);
                        sp_financial_year.setAdapter(yearArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadSeasonYearList() {

        Observable<List<? extends SeasonInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends SeasonInterface>>() {

            @Override
            public List<? extends SeasonInterface> call() {
                return dataBaseHelper.daoAccess().getDistinctSeasons();
            }
        });
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends SeasonInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends SeasonInterface> mst_season_List) {

                        seasonData = (List<SeasonInterface>) mst_season_List;
                        ArrayAdapter<SeasonInterface> seasonArrayAdapter = new ArrayAdapter<SeasonInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, seasonData);
                        sp_season.setAdapter(seasonArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void loadCalamityList() {

        Observable<List<? extends CalamityInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends CalamityInterface>>() {

            @Override
            public List<? extends CalamityInterface> call() {
                return dataBaseHelper.daoAccess().getDistinctCalamity();
            }
        });
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends CalamityInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends CalamityInterface> mst_calamity_List) {

                        calamityData = (List<CalamityInterface>) mst_calamity_List;
                        ArrayAdapter<CalamityInterface> seasonArrayAdapter = new ArrayAdapter<CalamityInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, calamityData);
                        sp_calamity.setAdapter(seasonArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
        AlertDialog alertDialog = new AlertDialog.Builder(PariharaBenificiaryReportLandWise.this).create();
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
