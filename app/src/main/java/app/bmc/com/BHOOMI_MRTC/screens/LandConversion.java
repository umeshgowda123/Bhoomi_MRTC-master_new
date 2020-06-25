package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversion extends AppCompatActivity {

    private RadioButton rb_AffidavitID;
    private RadioButton rb_UserID;

    private EditText etRadioText;
    private String affidavitID;
    private String userID;

    private Button btnFetchReports;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        etRadioText = findViewById(R.id.etRadioText);
        btnFetchReports = findViewById(R.id.btnFetchReports);
        rb_AffidavitID = findViewById(R.id.rb_AffidavitID);
        rb_UserID = findViewById(R.id.rb_UserID);

        rb_AffidavitID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etRadioText.setVisibility(View.VISIBLE);
                    etRadioText.setHint(R.string.enter_affidavit_id);
                    etRadioText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etRadioText.setText("");
                    rb_UserID.setChecked(false);
                }
            }
        });
//
        rb_UserID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etRadioText.setVisibility(View.VISIBLE);
                    etRadioText.setEnabled(true);
                    etRadioText.setHint(R.string.enter_user_ID);
                    etRadioText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etRadioText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                    etRadioText.setText("");
                    rb_AffidavitID.setChecked(false);
                }
            }
        });

        btnFetchReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    if (rb_AffidavitID.isChecked() || rb_UserID.isChecked()) {
                        if (rb_AffidavitID.isChecked()) {
                            affidavitID = etRadioText.getText().toString().trim();
                            if (!affidavitID.isEmpty()) {

                                progressDialog = new ProgressDialog(LandConversion.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionBasedOnAffidavitID(Constants.REPORT_SERVICE_USER_NAME,
                                        Constants.REPORT_SERVICE_PASSWORD,affidavitID);
                                call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                        if(response.isSuccessful())
                                        {
                                            PariharaIndividualDetailsResponse result = response.body();
                                            assert result != null;
                                            String res = result.getGet_Afdvt_ReqSts_BasedOnAfdvtIdResult();
                                            Log.d("AFFIDAVIT_ResponseData", ""+res);

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(LandConversion.this, LandConversionBasedOnAffidavit.class);
                                            intent.putExtra("AFFIDAVIT_ResponseData", res);
                                            intent.putExtra("AFFIDAVIT_ID", affidavitID);
//                                            Log.d("put : ", res+" & "+affidavitID);
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
                                if (affidavitID.isEmpty()) {
                                    Toast.makeText(LandConversion.this, "Please Enter Affidavit Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LandConversion.this, "Invalid Affidavit Number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            userID = etRadioText.getText().toString().trim();
                            if (!userID.isEmpty()) {
                                progressDialog = new ProgressDialog(LandConversion.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();


                                PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionBasedOnUserID(Constants.REPORT_SERVICE_USER_NAME,
                                        Constants.REPORT_SERVICE_PASSWORD,userID);
                                call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                        if(response.isSuccessful())
                                        {
                                            PariharaIndividualDetailsResponse result = response.body();
                                            assert result != null;
                                            String res = result.getGet_Afdvt_ReqSts_BasedOnUserIdResult();
                                            Log.d("USERID_ResponseData", ""+res);

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(LandConversion.this, LandConversionBasedOnUserId.class);
                                            intent.putExtra("USERID_ResponseData", res);
                                            intent.putExtra("USER_ID", userID);
                                            Log.d("put : ", res+" & "+userID);
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
                                if (userID.isEmpty()) {
                                    Toast.makeText(LandConversion.this, "Pleae enter User ID", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LandConversion.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(LandConversion.this, "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
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
        AlertDialog alertDialog = new AlertDialog.Builder(LandConversion.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            } });
        alertDialog.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
