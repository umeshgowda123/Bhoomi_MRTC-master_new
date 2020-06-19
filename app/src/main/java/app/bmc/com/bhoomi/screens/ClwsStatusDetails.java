package app.bmc.com.bhoomi.screens;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.BankPaymentDetailsAdapter;
import app.bmc.com.bhoomi.adapters.CommercialBankAdapter;
import app.bmc.com.bhoomi.adapters.PacsLoanDetailsAdapter;
import app.bmc.com.bhoomi.adapters.PacsPaymentDetailsAdapter;
import app.bmc.com.bhoomi.model.BankLoanTableData;
import app.bmc.com.bhoomi.model.BankPaymentTableData;
import app.bmc.com.bhoomi.model.PacsLoanTableData;
import app.bmc.com.bhoomi.model.PacsPaymentTableData;

public class ClwsStatusDetails extends AppCompatActivity {

    // Recycle View For CommercialBank
    //  private List<BankLoanDetailsTable> bankLoanDetailsTables;
    private List<BankLoanTableData> bankloanadata;

    //Recycle View For Bank Payment Details
    private List<BankPaymentTableData> paymentList;

    //Recycle View For PACS Loan Details
    private List<PacsLoanTableData> pacsLoanList;

    // Recycle View For PACS Payment Details
    private List<PacsPaymentTableData> pacsPaymentList;

    // private String aadharResponsedata = "{\"GetAllDetailsByAadhaarNumberResult\":\"{\\\"BankLoanDetailsAadharNumber\\\": {\\\"Table\\\":[{\\\"TRN_CUSTID\\\":143,\\\"BCUSTID\\\":\\\"09214915\\\",\\\"TRN_BNKID\\\":378,\\\"OTRN_BNKID\\\":378,\\\"FMLY_DISTCDE\\\":2,\\\"FMLY_DISTNME\\\":\\\"Bagalkote\\\",\\\"FMLY_TLKCDE\\\":3,\\\"FMLY_TLKNME\\\":\\\"Mudhol\\\",\\\"TRN_RCNO\\\":\\\"CBPR00114583\\\",\\\"OTRN_LOANEENME\\\":\\\"HM MUNIKRISHNAPPA  MUNEGOWDA                                                                        \\\",\\\"TRN_STS\\\":500,\\\"STATYS_DESC\\\":\\\"All Verification Fail\\\",\\\"BNK_NME_EN\\\":\\\"PRAGATHI KRISHNA GRAMIN BANK\\\",\\\"BNK_BRNCH_CDE\\\":378,\\\"BNK_BRNCH_NME\\\":\\\"OOJEIN\\\",\\\"OTRN_LOANCAT\\\":\\\"Regular\\\",\\\"ACNO\\\":\\\"049113100007286\\\",\\\"LIABAmount\\\":\\\"250102\\\"},{\\\"TRN_CUSTID\\\":143,\\\"BCUSTID\\\":\\\"09214915\\\",\\\"TRN_BNKID\\\":378,\\\"OTRN_BNKID\\\":378,\\\"FMLY_DISTCDE\\\":21,\\\"FMLY_DISTNME\\\":\\\"Bangalore Rural\\\",\\\"FMLY_TLKCDE\\\":2,\\\"FMLY_TLKNME\\\":\\\"Doddaballapura\\\",\\\"TRN_RCNO\\\":\\\"CBPR00114583\\\",\\\"OTRN_LOANEENME\\\":\\\"HM MUNIKRISHNAPPA  MUNEGOWDA                                                                        \\\",\\\"TRN_STS\\\":500,\\\"STATYS_DESC\\\":\\\"All Verification Fail\\\",\\\"BNK_NME_EN\\\":\\\"PRAGATHI KRISHNA GRAMIN BANK\\\",\\\"BNK_BRNCH_CDE\\\":378,\\\"BNK_BRNCH_NME\\\":\\\"OOJEIN\\\",\\\"OTRN_LOANCAT\\\":\\\"Regular\\\",\\\"ACNO\\\":\\\"049113100007286\\\",\\\"LIABAmount\\\":\\\"250102\\\"}]},\\\"BankPaymentDetailsAadharNumber\\\": {\\\"Table\\\":[]},\\\"PacsLoanDetailsAadharNumber\\\": {\\\"Table\\\":[{\\\"TRN_CUSTID\\\":5,\\\"TRN_BNKID\\\":165,\\\"TRN_LOONENME\\\":\\\"Rakesh Test\\\",\\\"TRN_RCNO\\\":\\\"crn14161179\\\",\\\"FMLY_DISTCDE\\\":1508,\\\"FMLY_DISTNME\\\":\\\"CHAMARAJANAGARA\\\",\\\"FMLY_TLKCDE\\\":1508001,\\\"FMLY_TLKNME\\\":\\\"CHAMARAJANAGAR\\\",\\\"BNK_NME_EN\\\":\\\"Bellary DCCB\\\",\\\"BNK_BRNCH_CDE\\\":165,\\\"BNK_BRNCH_NME\\\":\\\"ರೂಪನಗುಡಿ ಪ್ರಾಥಮಿಕ ಕೃಷಿ ಸಹಕಾರ ಸಂಘ ನಿಯಮಿತ.  \\\",\\\"PCS_PUR\\\":\\\"Crop loan\\\",\\\"PCS_LIAB_10072018\\\":10000.0,\\\"STS_DESC\\\":\\\"Application Sent for DCC Approval\\\",\\\"OTRN_SHARENO\\\":\\\"1234\\\"},{\\\"TRN_CUSTID\\\":4605,\\\"TRN_BNKID\\\":165,\\\"TRN_LOONENME\\\":\\\"Munish Test\\\",\\\"TRN_RCNO\\\":\\\"crn14161179\\\",\\\"FMLY_DISTCDE\\\":1508,\\\"FMLY_DISTNME\\\":\\\"CHAMARAJANAGARA\\\",\\\"FMLY_TLKCDE\\\":1508001,\\\"FMLY_TLKNME\\\":\\\"CHAMARAJANAGAR\\\",\\\"BNK_NME_EN\\\":\\\"Bellary DCCB\\\",\\\"BNK_BRNCH_CDE\\\":165,\\\"BNK_BRNCH_NME\\\":\\\"ರೂಪನಗುಡಿ ಪ್ರಾಥಮಿಕ ಕೃಷಿ ಸಹಕಾರ ಸಂಘ ನಿಯಮಿತ.  \\\",\\\"PCS_PUR\\\":\\\"Crop loan\\\",\\\"PCS_LIAB_10072018\\\":10000.0,\\\"STS_DESC\\\":\\\"Application Sent for DCC Approval\\\",\\\"OTRN_SHARENO\\\":\\\"1234\\\"},{\\\"TRN_CUSTID\\\":6444,\\\"TRN_BNKID\\\":165,\\\"TRN_LOONENME\\\":\\\"Munidh trdt\\\",\\\"TRN_RCNO\\\":\\\"crn14161179\\\",\\\"FMLY_DISTCDE\\\":1508,\\\"FMLY_DISTNME\\\":\\\"CHAMARAJANAGARA\\\",\\\"FMLY_TLKCDE\\\":1508001,\\\"FMLY_TLKNME\\\":\\\"CHAMARAJANAGAR\\\",\\\"BNK_NME_EN\\\":\\\"Bellary DCCB\\\",\\\"BNK_BRNCH_CDE\\\":165,\\\"BNK_BRNCH_NME\\\":\\\"ರೂಪನಗುಡಿ ಪ್ರಾಥಮಿಕ ಕೃಷಿ ಸಹಕಾರ ಸಂಘ ನಿಯಮಿತ.  \\\",\\\"PCS_PUR\\\":\\\"Crop loan\\\",\\\"PCS_LIAB_10072018\\\":10000.0,\\\"STS_DESC\\\":\\\"Application Sent for DCC Approval\\\",\\\"OTRN_SHARENO\\\":\\\"1234\\\"}]},\\\"PacsPaymentDetailsAadharNumber\\\": {\\\"Table\\\":[]}}\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clws_status_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Log.d("Entered","Entered");
        TextView tvCBLD = findViewById(R.id.tvCBLD);
        TextView tvBPD = findViewById(R.id.tvBPD);
        TextView tvPLD = findViewById(R.id.tvPLD);
        TextView tvPPD = findViewById(R.id.tvPPD);
        RecyclerView rvCommercialBank = findViewById(R.id.rvCommercialBank);
        RecyclerView rvBankPaymentDetails = findViewById(R.id.rvBankPaymentDetails);
        RecyclerView rvPacsLoanDetails = findViewById(R.id.rvPacsLoanDetails);
        RecyclerView rvPacsPaymentDetails = findViewById(R.id.rvPacsPaymentDetails);


        String aadharno = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("UID");
        String aadharResponsedata = (String) getIntent().getExtras().getSerializable("Aadhaar_ResponseData");

        Log.d("aadharno", ""+aadharno);
        Log.d("aadharResponsedata", ""+aadharResponsedata);

        if(aadharResponsedata != null) {
            try {

                JSONObject object1 = new JSONObject(aadharResponsedata);
                Log.d("aadharResponsedata", String.valueOf(object1));

                JSONObject object = new JSONObject(aadharResponsedata);

                Log.d("aadharResponsedata", String.valueOf(object));

                String getalldetailsObject = object.getString("NewDataSet");
                JSONObject objdata = new JSONObject(getalldetailsObject);
                String bankLoanDetailsAadharNumber = objdata.getString("CitizenBankDetails");
                String bankPaymentDetailsAadharNumber = objdata.getString("CitizenBankPaymentDetails");
                String pacsLoanDetailsAadharNumber = objdata.getString("CitizenPacsDetails");
                String pacsPaymentDetailsAadharNumber = objdata.getString("CitizenPacsPaymentDetails");
                Log.d("bankLoanDetails",bankLoanDetailsAadharNumber);
                Log.d("bankPaymentDetails",bankPaymentDetailsAadharNumber);
                Log.d("pacsLoanDetails",pacsLoanDetailsAadharNumber);
                Log.d("pacsPaymentDetails",pacsPaymentDetailsAadharNumber);

                // Data For BankLoanDetailsAadharNumber
                JSONObject bankloanTabledata = new JSONObject(bankLoanDetailsAadharNumber);
                Log.d("bankLoanDetails", String.valueOf(bankloanTabledata));

                String form = String.valueOf(bankloanTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);

                Gson gson = new Gson();
                bankloanadata = gson.fromJson(form, new TypeToken<List<BankLoanTableData>>() {}.getType());
                Log.d("SIZESUS", bankloanadata.size() + "");
                // Data For BankPaymentDetailsAadhaarNumber
                JSONObject bankPaymentTabledata = new JSONObject(bankPaymentDetailsAadharNumber);

                form = String.valueOf(bankPaymentTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);

                paymentList = gson.fromJson(form, new TypeToken<List<BankPaymentTableData>>() {}.getType());
                Log.d("SIZESUS", paymentList.size() + "");
                // Data For PacsLoanDetails AadhaarNumber
                JSONObject pacsloanTabledata = new JSONObject(pacsLoanDetailsAadharNumber);

                form = String.valueOf(pacsloanTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);

                pacsLoanList = gson.fromJson(form, new TypeToken<List<PacsLoanTableData>>() {}.getType());
                Log.d("SIZESUS", pacsLoanList.size() + "");
                // Data For PacsPaymentDetails AadharNumber
                JSONObject pacsPaymentTabledata = new JSONObject(pacsPaymentDetailsAadharNumber);

                form = String.valueOf(pacsPaymentTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);

                pacsPaymentList = gson.fromJson(form, new TypeToken<List<PacsPaymentTableData>>() {}.getType());
                Log.d("SIZESUS", pacsPaymentList.size() + "");

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ExceptionSUS", e + "");
            }

            if (bankloanadata.size() == 0 && paymentList.size() == 0 && pacsLoanList.size() == 0 && pacsPaymentList.size() == 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ClwsStatusDetails.this, R.style.MyDialogTheme);
                builder.setTitle("STATUS")
                        .setMessage("No Data Found For this Input")
                        .setIcon(R.drawable.ic_notifications_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                final AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
            } else {
                if (bankloanadata.size() != 0) {
                    tvCBLD.setVisibility(View.VISIBLE);
                    rvCommercialBank.setVisibility(View.VISIBLE);
                    CommercialBankAdapter cadapter = new CommercialBankAdapter(bankloanadata, this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvCommercialBank.setLayoutManager(mLayoutManager);
                    rvCommercialBank.setItemAnimator(new DefaultItemAnimator());
                    rvCommercialBank.setAdapter(cadapter);
                }

                if (paymentList != null && paymentList.size() != 0) {
                    tvBPD.setVisibility(View.VISIBLE);
                    rvBankPaymentDetails.setVisibility(View.VISIBLE);
                    BankPaymentDetailsAdapter bankPaymentadapter = new BankPaymentDetailsAdapter(paymentList, this);
                    RecyclerView.LayoutManager bPaymentLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvBankPaymentDetails.setLayoutManager(bPaymentLayoutManager);
                    rvBankPaymentDetails.setItemAnimator(new DefaultItemAnimator());
                    rvBankPaymentDetails.setAdapter(bankPaymentadapter);
                }


                if (pacsLoanList != null && pacsLoanList.size() != 0) {
                    tvPLD.setVisibility(View.VISIBLE);
                    rvPacsLoanDetails.setVisibility(View.VISIBLE);
                    PacsLoanDetailsAdapter pacloanDetailsadapter = new PacsLoanDetailsAdapter(pacsLoanList, this);
                    RecyclerView.LayoutManager pacsLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvPacsLoanDetails.setLayoutManager(pacsLayoutManager);
                    rvPacsLoanDetails.setItemAnimator(new DefaultItemAnimator());
                    rvPacsLoanDetails.setAdapter(pacloanDetailsadapter);
                }

                if (pacsPaymentList != null && pacsPaymentList.size() != 0) {
                    tvPPD.setVisibility(View.VISIBLE);
                    rvPacsPaymentDetails.setVisibility(View.VISIBLE);
                    PacsPaymentDetailsAdapter pacPaymentDetailsadapter = new PacsPaymentDetailsAdapter(pacsPaymentList, this);
                    RecyclerView.LayoutManager pacsPaymentLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvPacsPaymentDetails.setLayoutManager(pacsPaymentLayoutManager);
                    rvPacsPaymentDetails.setItemAnimator(new DefaultItemAnimator());
                    rvPacsPaymentDetails.setAdapter(pacPaymentDetailsadapter);
                }

            }

        }
        else {
            Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
        }
}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
