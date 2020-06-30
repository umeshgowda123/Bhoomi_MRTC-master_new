package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MojiniRequestStatus extends AppCompatActivity {
    private RadioButton rb_App_Status;
    private RadioButton rb_Allotment_Details;

    private EditText etRadioText;
    private String app_status;
    private String allotment_details;

    Button btnFetchReports;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mojini_request_satus);

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

        etRadioText = findViewById(R.id.etText);
        btnFetchReports = findViewById(R.id.btnSubmit);
        rb_App_Status = findViewById(R.id.rb_App_Status);
        rb_Allotment_Details = findViewById(R.id.rb_Allotment_Details);

        rb_App_Status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etRadioText.setVisibility(View.VISIBLE);
                etRadioText.setHint(R.string.enter_application_number);
                etRadioText.setInputType(InputType.TYPE_CLASS_NUMBER);
                etRadioText.setText("");
                rb_Allotment_Details.setChecked(false);
            }
        });

        rb_Allotment_Details.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etRadioText.setVisibility(View.VISIBLE);
                etRadioText.setEnabled(true);
                etRadioText.setHint(R.string.enter_application_number);
                etRadioText.setInputType(InputType.TYPE_CLASS_NUMBER);
                etRadioText.setText("");
                rb_App_Status.setChecked(false);
            }
        });
        btnFetchReports.setOnClickListener(v -> {

            if (isNetworkAvailable()) {
                if (rb_App_Status.isChecked() || rb_Allotment_Details.isChecked()) {
                    if (rb_App_Status.isChecked()) {
                        app_status = etRadioText.getText().toString().trim();
                        if (!app_status.isEmpty()) {

                            progressDialog = new ProgressDialog(MojiniRequestStatus.this);
                            progressDialog.setMessage("Please Wait");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                            Call<PariharaIndividualDetailsResponse> call = apiInterface.getApplicationStatusBasedonAppNo(Constants.REPORT_SERVICE_USER_NAME,
                                    Constants.REPORT_SERVICE_PASSWORD,app_status);
                            call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                @Override
                                public void onResponse(@NotNull Call<PariharaIndividualDetailsResponse>  call, @NotNull Response<PariharaIndividualDetailsResponse> response) {

                                    if(response.isSuccessful())
                                    {
                                        PariharaIndividualDetailsResponse result = response.body();
                                        assert result != null;
                                        String res = result.getGetApplicationStatusBasedonAppNoResult();
                                        Log.d("app_Status_ResponseData", ""+res);

                                        progressDialog.dismiss();
                                        if(res == null || res.contains("Details not found")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MojiniRequestStatus.this, R.style.MyDialogTheme);
                                            builder.setTitle("STATUS")
                                                    .setMessage("No Data Found For this Record")
                                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                            final android.app.AlertDialog alert = builder.create();
                                            alert.show();
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                        } else {
                                            Intent intent = new Intent(MojiniRequestStatus.this, Mojini_Application_Status_BasedOnAppNo.class);
                                            intent.putExtra("app_Status_ResponseData", res);
                                            startActivity(intent);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Throwable t) {
                                    call.cancel();
                                    progressDialog.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(MojiniRequestStatus.this, "Please Enter Application Number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        allotment_details = etRadioText.getText().toString().trim();
                        if (!allotment_details.isEmpty()) {
                            progressDialog = new ProgressDialog(MojiniRequestStatus.this);
                            progressDialog.setMessage("Please Wait");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                            Call<PariharaIndividualDetailsResponse> call = apiInterface.getAllotmentDetailsBasedOnAppNo(Constants.REPORT_SERVICE_USER_NAME,
                                    Constants.REPORT_SERVICE_PASSWORD,allotment_details);
                            call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                @Override
                                public void onResponse(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Response<PariharaIndividualDetailsResponse> response) {

                                    if(response.isSuccessful())
                                    {
                                        PariharaIndividualDetailsResponse result = response.body();
                                        assert result != null;
                                        String res = result.getGetAllotmentDetailsBasedOnAppNoResult();
                                        Log.d("allotment_ResponseData", ""+res);

                                        progressDialog.dismiss();
                                        if(res == null || res.contains("[{Details not found")) {
                                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MojiniRequestStatus.this, R.style.MyDialogTheme);
                                            builder.setTitle("STATUS")
                                                    .setMessage("No Data Found For this Record")
                                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                            final android.app.AlertDialog alert = builder.create();
                                            alert.show();
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                        } else {
                                            Intent intent = new Intent(MojiniRequestStatus.this, Mojini_Allotment_Details_BasedOnAppNo.class);
                                            intent.putExtra("allotment_ResponseData", res);
                                            startActivity(intent);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure( @NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Throwable t) {
                                    call.cancel();
                                    progressDialog.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(MojiniRequestStatus.this, "Please enter Application Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MojiniRequestStatus.this, "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                selfDestruct();
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
        AlertDialog alertDialog = new AlertDialog.Builder(MojiniRequestStatus.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", (dialog, which) -> progressDialog.dismiss());
        alertDialog.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
