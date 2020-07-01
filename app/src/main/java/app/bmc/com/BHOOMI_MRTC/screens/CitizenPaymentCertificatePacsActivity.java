package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
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
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.APIInterface;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentCertiFsdResult;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentCertiRasonResult;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentCertiUidResult;
import app.bmc.com.BHOOMI_MRTC.retrofit.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitizenPaymentCertificatePacsActivity extends AppCompatActivity {


    private RadioGroup rgPaymentCertificate;
    private RadioButton rbPayCertAadhar;
    private RadioButton rbPayCertRasanCard;
    private RadioButton rbPayCertFSDId;


    private EditText etPaymentCertAaadhar;
    private String aadharNumber;
    private String paymentRasanCardNumber;
    private String paymentFsdId;

    APIInterface apiInterface;

    private Button btnFetchDetails;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_payment_certificate_pacs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rgPaymentCertificate = findViewById(R.id.rgPaymentCertificate);
        rbPayCertAadhar = findViewById(R.id.rbPayCertAadhar);
        rbPayCertRasanCard = findViewById(R.id.rbPayCertRasanCard);
        rbPayCertFSDId = findViewById(R.id.rbPayCertFSDId);

        etPaymentCertAaadhar = findViewById(R.id.etPaymentCertAaadhar);
        btnFetchDetails = findViewById(R.id.btnFetchDetails);

        rbPayCertAadhar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etPaymentCertAaadhar.setVisibility(View.VISIBLE);
                    etPaymentCertAaadhar.setHint(R.string.clws_aadhar_edittext);
                    etPaymentCertAaadhar.setText("");
                    etPaymentCertAaadhar.setInputType(InputType.TYPE_CLASS_NUMBER);
                    rbPayCertRasanCard.setChecked(false);
                    rbPayCertFSDId.setChecked(false);
                }
            }
        });

        rbPayCertRasanCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etPaymentCertAaadhar.setText("");
                    etPaymentCertAaadhar.setVisibility(View.VISIBLE);
                    etPaymentCertAaadhar.setHint(R.string.clws_ration_edittext);
                    etPaymentCertAaadhar.setInputType(InputType.TYPE_CLASS_TEXT);
                    etPaymentCertAaadhar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                    rbPayCertAadhar.setChecked(false);
                    rbPayCertFSDId.setChecked(false);
                }
            }
        });

        rbPayCertFSDId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    etPaymentCertAaadhar.setVisibility(View.VISIBLE);
                    etPaymentCertAaadhar.setText("");
                    etPaymentCertAaadhar.setHint(R.string.clws_fsd_edit_text);
                    etPaymentCertAaadhar.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etPaymentCertAaadhar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                    rbPayCertAadhar.setChecked(false);
                    rbPayCertRasanCard.setChecked(false);
                }
            }
        });

        btnFetchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(CitizenPaymentCertificatePacsActivity.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (isNetworkAvailable()){
                    if (rbPayCertAadhar.isChecked()) {
                        aadharNumber = etPaymentCertAaadhar.getText().toString().trim();
                        if (aadharNumber.length() == 12) {
                            apiInterface = APIClient.getClient().create(APIInterface.class);
                            Call<PacsPaymentCertiUidResult> call = apiInterface.getPacsCertificationPaymentDetails(aadharNumber);
                            call.enqueue(new Callback<PacsPaymentCertiUidResult>() {
                                @Override
                                public void onResponse(Call<PacsPaymentCertiUidResult> call, Response<PacsPaymentCertiUidResult> response) {
                                    if(response.isSuccessful())
                                    {
                                        PacsPaymentCertiUidResult result = response.body();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(CitizenPaymentCertificatePacsActivity.this, PaymentCertifiacteDetails.class);
                                        intent.putExtra("PACS_RESPONSE_DATA", result.getGetPayMentCertificateForPacsByAadharNumberResult());
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onFailure(Call<PacsPaymentCertiUidResult> call, Throwable t) {
                                    call.cancel();
                                }
                            });
                        }
                        else {
//                            Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Aadhar Number should be 12 digit ", Toast.LENGTH_SHORT).show();

                            //---------------------SUSMITA------------------------
                            progressDialog.dismiss();
                            if(aadharNumber.isEmpty()) {
                                Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Please Enter The Aadhar Number", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Aadhar Number should be 12 digit ", Toast.LENGTH_SHORT).show();
                            }
                            //-----------------------------------------------------
                        }
                    } else if (rbPayCertRasanCard.isChecked()) {
                        paymentRasanCardNumber = etPaymentCertAaadhar.getText().toString().trim();
                        if (!paymentRasanCardNumber.isEmpty() && paymentRasanCardNumber.length()>5) {


                            apiInterface = APIClient.getClient().create(APIInterface.class);
                            Call<PacsPaymentCertiRasonResult> call = apiInterface.getPacsCertificationPaymentByRasonCard(paymentRasanCardNumber);
                            call.enqueue(new Callback<PacsPaymentCertiRasonResult>() {
                                @Override
                                public void onResponse(Call<PacsPaymentCertiRasonResult> call, Response<PacsPaymentCertiRasonResult> response) {
                                    if(response.isSuccessful())
                                    {
                                        PacsPaymentCertiRasonResult result = response.body();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(CitizenPaymentCertificatePacsActivity.this, PaymentCertifiacteDetails.class);
                                        intent.putExtra("PACS_RESPONSE_DATA", result.getGetPayMentCertificateForPacsByRationCardNumberResult());
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<PacsPaymentCertiRasonResult> call, Throwable t) {
                                    call.cancel();
                                }
                            });
                        }
                        else {
                            //Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Rasan Card Number should be 8 digit", Toast.LENGTH_SHORT).show();
                            //---------------------SUSMITA------------------------
                            progressDialog.dismiss();
                            if (paymentRasanCardNumber.isEmpty()){
                                Toast.makeText(CitizenPaymentCertificatePacsActivity.this, getString(R.string.please_enter_rationcard_num), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CitizenPaymentCertificatePacsActivity.this, getString(R.string.invalid_rationcard_num), Toast.LENGTH_SHORT).show();
                            }
                            //----------------------------------------------------
                        }
                    } else if (rbPayCertFSDId.isChecked()) {
                        paymentFsdId = etPaymentCertAaadhar.getText().toString().trim();
                        if (!paymentFsdId.isEmpty()) {

                            apiInterface = APIClient.getClient().create(APIInterface.class);
                            Call<PacsPaymentCertiFsdResult> call = apiInterface.getPacsCertificationPaymentByFsdid(paymentFsdId);
                            call.enqueue(new Callback<PacsPaymentCertiFsdResult>() {
                                @Override
                                public void onResponse(Call<PacsPaymentCertiFsdResult> call, Response<PacsPaymentCertiFsdResult> response) {
                                    if(response.isSuccessful())
                                    {
                                        PacsPaymentCertiFsdResult result = response.body();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(CitizenPaymentCertificatePacsActivity.this, PaymentCertifiacteDetails.class);
                                        intent.putExtra("PACS_RESPONSE_DATA", result.getGetPayMentCertificateForPacsByCustomerIDNewResult());
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onFailure(Call<PacsPaymentCertiFsdResult> call, Throwable t) {
                                    call.cancel();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();//---------------------SUSMITA------------------------
                            Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Please Enter FSD ID ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //---------------------SUSMITA------------------------
                        progressDialog.dismiss();
                        Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
                        //----------------------------------------------------
//                        Toast.makeText(CitizenPaymentCertificatePacsActivity.this, "Please Enter THE Aadhar Number", Toast.LENGTH_SHORT).show();
                    }
                }else {
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
        AlertDialog alertDialog = new AlertDialog.Builder(CitizenPaymentCertificatePacsActivity.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", (dialog, which) -> {
        });
        alertDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
