package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.APIInterface;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentCertiFsdResult;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentCertiRasonResult;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentCertiUidResult;
import app.bmc.com.BHOOMI_MRTC.retrofit.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitizenPaymentCertificateBanksActivity extends AppCompatActivity {

    RadioGroup rgBankCertificate;

    private RadioButton rbBankCertAadhar;
    private RadioButton rbBankCertRasanCard;
    private RadioButton rbBankCertFSDId;


    private EditText etBankCertAaadhar;
    private String aadharNumber;
    private String  bankRasanCardNumber;
    private String  bankFsdId;

    APIInterface apiInterface;

    Button btnBankFetchDetails;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_payment_certificate_banks);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rgBankCertificate = findViewById(R.id.rgBankCertificate);
        rbBankCertAadhar = findViewById(R.id.rbBankCertAadhar);
        rbBankCertRasanCard = findViewById(R.id.rbBankCertRasanCard);
        rbBankCertFSDId = findViewById(R.id.rbBankCertFSDId);

        etBankCertAaadhar = findViewById(R.id.etBankCertAaadhar);
        btnBankFetchDetails = findViewById(R.id.btnBankFetchDetails);

        rbBankCertAadhar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                etBankCertAaadhar.setVisibility(View.VISIBLE);
                etBankCertAaadhar.setText("");
                etBankCertAaadhar.setHint(R.string.clws_aadhar_edittext);
                etBankCertAaadhar.setInputType(InputType.TYPE_CLASS_NUMBER);
                rbBankCertRasanCard.setChecked(false);
                rbBankCertFSDId.setChecked(false);
            }
        });

        rbBankCertRasanCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                etBankCertAaadhar.setText("");
                etBankCertAaadhar.setVisibility(View.VISIBLE);
                etBankCertAaadhar.setHint(R.string.clws_ration_edittext);
                etBankCertAaadhar.setInputType(InputType.TYPE_CLASS_TEXT);
                etBankCertAaadhar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                rbBankCertAadhar.setChecked(false);
                rbBankCertFSDId.setChecked(false);
            }
        });

        rbBankCertFSDId.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                etBankCertAaadhar.setVisibility(View.VISIBLE);
                etBankCertAaadhar.setEnabled(true);
                etBankCertAaadhar.setText("");
                etBankCertAaadhar.setHint(R.string.clws_fsd_edit_text);
                etBankCertAaadhar.setInputType(InputType.TYPE_CLASS_NUMBER);
                etBankCertAaadhar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                rbBankCertAadhar.setChecked(false);
                rbBankCertRasanCard.setChecked(false);
            }
        });

        btnBankFetchDetails.setOnClickListener(v -> {

            progressDialog = new ProgressDialog(CitizenPaymentCertificateBanksActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (isNetworkAvailable()) {
                if (rbBankCertAadhar.isChecked()) {
                    aadharNumber = etBankCertAaadhar.getText().toString().trim();
//                        if (!aadharNumber.isEmpty()) {
                    if (aadharNumber.length() == 12) {// SUSMITA
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<BankPaymentCertiUidResult> call = apiInterface.getBankCertificationPaymentDetails(aadharNumber);
                        call.enqueue(new Callback<BankPaymentCertiUidResult>() {
                            @Override
                            public void onResponse(@NonNull Call<BankPaymentCertiUidResult> call, @NonNull Response<BankPaymentCertiUidResult> response) {

                                if(response.isSuccessful())
                                {

                                    BankPaymentCertiUidResult result = response.body();
                                    progressDialog.dismiss();
                                    assert result != null;
                                    String res = result.getGetPayMentCertificateForBankByAadharNumberResult();
                                    Log.d("Result_1","res: "+res);
                                    if (res.equals("[]")){
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CitizenPaymentCertificateBanksActivity.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Details Found for This Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        Intent intent = new Intent(CitizenPaymentCertificateBanksActivity.this, PaymentBankCertificateDetails.class);
                                        intent.putExtra("response_data", result.getGetPayMentCertificateForBankByAadharNumberResult());
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<BankPaymentCertiUidResult> call, @NonNull Throwable t) {
                                call.cancel();
                            }
                        });
                    }
                    else {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Please Enter The Aadhar Number ", Toast.LENGTH_SHORT).show();

                        //---------------------SUSMITA------------------------
                        progressDialog.dismiss();
                        if(aadharNumber.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please Enter The Aadhar Number", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Aadhar Number should be 12 digit ", Toast.LENGTH_SHORT).show();
                        }
                        //-----------------------------------------------------
                    }
                }
                else if (rbBankCertRasanCard.isChecked()) {
                    bankRasanCardNumber = etBankCertAaadhar.getText().toString().trim();
                    if (!bankRasanCardNumber.isEmpty() && bankRasanCardNumber.length()>5) {

                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<BankPaymentCertiRasonResult> call = apiInterface.getBankCertificationPaymentByRasonCard(bankRasanCardNumber);
                        call.enqueue(new Callback<BankPaymentCertiRasonResult>() {
                            @Override
                            public void onResponse(@NonNull Call<BankPaymentCertiRasonResult> call, @NonNull Response<BankPaymentCertiRasonResult> response) {
                                if(response.isSuccessful())
                                {
                                    BankPaymentCertiRasonResult result = response.body();
                                    progressDialog.dismiss();
                                    assert result != null;
                                    String res = result.getGetPayMentCertificateForBankByRationCardNumberResult();
                                    Log.d("Result_1","res: "+res);
                                    if (res.equals("[]")){
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CitizenPaymentCertificateBanksActivity.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Details Found for This Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        Intent intent = new Intent(CitizenPaymentCertificateBanksActivity.this, PaymentBankCertificateDetails.class);
                                        intent.putExtra("response_data", result.getGetPayMentCertificateForBankByRationCardNumberResult());
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<BankPaymentCertiRasonResult> call, @NonNull Throwable t) {
                                call.cancel();
                            }
                        });
                    }
                    else {
//                            Toast.makeText(CitizenPaymentCertificateBanksActivity.this, "Please Enter Rasan Card Number ", Toast.LENGTH_SHORT).show();
                        //---------------------SUSMITA------------------------
                        progressDialog.dismiss();
                        if (bankRasanCardNumber.isEmpty()){
                            Toast.makeText(getApplicationContext(), getString(R.string.please_enter_rationcard_num), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), getString(R.string.invalid_rationcard_num), Toast.LENGTH_SHORT).show();
                        }
                        //----------------------------------------------------
                    }
                } else if (rbBankCertFSDId.isChecked()) {
                    bankFsdId = etBankCertAaadhar.getText().toString().trim();
                    if (!bankFsdId.isEmpty()) {

                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<BankPaymentCertiFsdResult> call = apiInterface.getBankCertificationPaymentByFsdid(bankFsdId);
                        call.enqueue(new Callback<BankPaymentCertiFsdResult>() {
                            @Override
                            public void onResponse(@NonNull Call<BankPaymentCertiFsdResult> call, @NonNull Response<BankPaymentCertiFsdResult> response) {
                                if(response.isSuccessful())
                                {

                                    BankPaymentCertiFsdResult result = response.body();
                                    progressDialog.dismiss();
                                    assert result != null;
                                    String res = result.getGetPayMentCertificateForBankByFsdIdNumberResult();
                                    Log.d("Result_1","res: "+res);
                                    if (res.equals("[]")){
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CitizenPaymentCertificateBanksActivity.this, R.style.MyDialogTheme);
                                        builder.setTitle("STATUS")
                                                .setMessage("No Details Found for This Record")
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    } else {
                                        Intent intent = new Intent(CitizenPaymentCertificateBanksActivity.this, PaymentBankCertificateDetails.class);
                                        intent.putExtra("PACS_Request_Parameter", bankFsdId);
                                        intent.putExtra("response_data", result.getGetPayMentCertificateForBankByFsdIdNumberResult());
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<BankPaymentCertiFsdResult> call, @NonNull Throwable t) {
                                call.cancel();
                                progressDialog.dismiss();
                            }
                        });

                    } else {
                        progressDialog.dismiss();//-------SUSMITA--------
                        Toast.makeText(CitizenPaymentCertificateBanksActivity.this, "Please Enter FSD ID ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //---------------------SUSMITA------------------------
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
                    //----------------------------------------------------
//                        Toast.makeText(CitizenPaymentCertificateBanksActivity.this, "Please Enter The Aadhar Number", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                selfDestruct();
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
        AlertDialog alertDialog = new AlertDialog.Builder(CitizenPaymentCertificateBanksActivity.this).create();
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
