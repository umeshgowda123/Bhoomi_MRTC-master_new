package app.bmc.com.bhoomi.screens;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.api.APIInterface;
import app.bmc.com.bhoomi.api.PariharaIndividualReportInteface;
import app.bmc.com.bhoomi.model.BankPaymentCertiUidResult;
import app.bmc.com.bhoomi.model.PariharaIndividualDetailsResponse;
import app.bmc.com.bhoomi.retrofit.APIClient;
import app.bmc.com.bhoomi.retrofit.PariharaIndividualreportClient;
import app.bmc.com.bhoomi.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PariharaDetailsIndividual extends AppCompatActivity {


    private MaterialBetterSpinner sp_year;
    private MaterialBetterSpinner sp_calamity_type;
    private EditText etAadharNumber;

    private Button btn_GetDetails;

    private String financial_year;
    private String calamity_type;

    private ProgressDialog progress_circular;

    PariharaIndividualReportInteface apiInterface;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parihara_details_individual);

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

        sp_year =  findViewById(R.id.sp_year);
        sp_calamity_type =  findViewById(R.id.sp_calamity_type);
        etAadharNumber =  findViewById(R.id.etAadharNumber);

        btn_GetDetails =  findViewById(R.id.btn_GetDetails);

        String financialYear[] = getResources().getStringArray(R.array.select_financial_year);
        String calamityType[] = getResources().getStringArray(R.array.select_calamity_type);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, financialYear);
        sp_year.setAdapter(defaultArrayAdapter);


        ArrayAdapter<String> calamityTypeDefaultAdapter =  new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,calamityType);
        sp_calamity_type.setAdapter(calamityTypeDefaultAdapter);


        sp_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                financial_year =  sp_year.getText().toString().trim();

            }
        });

        sp_calamity_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                calamity_type =  sp_year.getText().toString().trim();

            }
        });



        btn_GetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String financialYear = sp_year.getText().toString().trim();
                String calamityType = sp_calamity_type.getText().toString().trim();
                String adhaarNumber = etAadharNumber.getText().toString().trim();

                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(financialYear)) {
                    focus = sp_year;
                    status = true;
                    sp_year.setError(getString(R.string.year_err));
                } else if (TextUtils.isEmpty(calamityType)) {
                    focus = sp_calamity_type;
                    status = true;
                    sp_calamity_type.setError(getString(R.string.calamity_err));
                } else if (TextUtils.isEmpty(adhaarNumber)) {
                    focus = etAadharNumber;
                    status = true;
                    etAadharNumber.setError(getString(R.string.aadhaar_err));
                }
                //------------------SUSMITA--------------------------
                else if (etAadharNumber.length() != 12){
                    focus = etAadharNumber;
                    status = true;
                    etAadharNumber.setError("Aadhar Number should be 12 Digit");
                }
                //---------------------------------------------------
                if (status) {
                    focus.requestFocus();
                }else
                {
                    if (isNetworkAvailable()) {

                        progressDialog = new ProgressDialog(PariharaDetailsIndividual.this);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                        Call<PariharaIndividualDetailsResponse> call = apiInterface.getPariharaPaymentDetails(Constants.REPORT_SERVICE_USER_NAME,
                                Constants.REPORT_SERVICE_PASSWORD,adhaarNumber,calamityType,financialYear);
                        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                if(response.isSuccessful())
                                {
                                    PariharaIndividualDetailsResponse result = response.body();
                                    progressDialog.dismiss();
                                    assert result != null;
                                    String str = result.getGetPariharaPaymentDetailsResult();
                                    Log.d("Result_1",""+str);
                                    if (str.contains("No Details Found for This Record")){
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PariharaDetailsIndividual.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Details Found for This Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        Intent intent = new Intent(PariharaDetailsIndividual.this, ShowIndividualPariharaDetailsReport.class);
                                        intent.putExtra("response_data", result.getGetPariharaPaymentDetailsResult());
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

                    }else
                    {
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
        AlertDialog alertDialog = new AlertDialog.Builder(PariharaDetailsIndividual.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            } });
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
