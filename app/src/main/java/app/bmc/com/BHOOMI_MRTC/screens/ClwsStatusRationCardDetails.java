package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.BankPaymentRationDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.CommercialBankRationAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.PacsLoanRationDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.PacsPaymentRationDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.BankLoanRationTableData;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentRationTableData;
import app.bmc.com.BHOOMI_MRTC.model.PacsLoanRationTableData;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentRationTableData;

public class ClwsStatusRationCardDetails extends AppCompatActivity {

    private TextView tvRcNoCBLD;
    private TextView tvRcNoBPD;
    private TextView tvRcNoPLD;
    private TextView tvRcNoPPD;


    private List<BankLoanRationTableData> bankloanrationdata;
    private RecyclerView rvRcNoCommercialBank;
    private CommercialBankRationAdapter rationadapter;

    private List<BankPaymentRationTableData> paymentRclist;
    private RecyclerView rvRcNoBankPaymentDetails;
    private BankPaymentRationDetailsAdapter bankpaymentrationadapter;

    private List<PacsLoanRationTableData> pacsloanRcNolist;
    private RecyclerView rvRcNoPacsLoanDetails;
    private PacsLoanRationDetailsAdapter pcloanrationAdapter;

    private List<PacsPaymentRationTableData> pacsRationPaymentList;
    private RecyclerView rvRcNoPacsPaymentDetails;
    private PacsPaymentRationDetailsAdapter pacspaymentrationAdapter;

    Bundle bundle;
    private String rationCardNo;
    private String rasonResponsedata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clws_status_ration_card_details);

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

        tvRcNoCBLD = findViewById(R.id.tvRcNoCBLD);
        tvRcNoBPD = findViewById(R.id.tvRcNoBPD);
        tvRcNoPLD = findViewById(R.id.tvRcNoPLD);
        tvRcNoPPD = findViewById(R.id.tvRcNoPPD);


        bundle = getIntent().getExtras();
        if(bundle != null)
        {
            rationCardNo = (String) bundle.getSerializable("RATION_CARD");
            rasonResponsedata = bundle.getString("Ration_ResponseData");
            Log.d("Ration_ResponseData",""+rasonResponsedata);

        }

        if(rasonResponsedata != null) {

            try {
                JSONObject object = new JSONObject(rasonResponsedata);
                String getalldetailsObject = object.getString("NewDataSet");
                JSONObject objdata = new JSONObject(getalldetailsObject);
                String bankLoanDetailsRationCardNumber = objdata.getString("CitizenBankDetails");
                String bankPaymentDetailsRationCardNumber = objdata.getString("CitizenBankPaymentDetails");
                String pacsLoanDetailsRationCardNumber = objdata.getString("CitizenPacsDetails");
                String pacsPaymentDetailsRationCardNumber = objdata.getString("CitizenPacsPaymentDetails");

                // Data For BankLoanDetailsRationCardNumber
                JSONObject bankloanTabledata = new JSONObject(bankLoanDetailsRationCardNumber);
                Log.d("bankLoanDetails", String.valueOf(bankloanTabledata));
                String form = String.valueOf(bankloanTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);

                Gson gson = new Gson();
                bankloanrationdata = gson.fromJson(form, new TypeToken<List<BankLoanRationTableData>>() {
                }.getType());

                // Data For BankPaymentDetailsRationCardNumber
                JSONObject bankPaymentTabledata = new JSONObject(bankPaymentDetailsRationCardNumber);
                Log.d("bankPaymentTabledata", String.valueOf(bankPaymentTabledata));
                form = String.valueOf(bankPaymentTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);
                paymentRclist = gson.fromJson(form, new TypeToken<List<BankPaymentRationTableData>>() {
                }.getType());

                // Data For PacsLoanDetails AadhaarNumber
                JSONObject pacsloanTabledata = new JSONObject(pacsLoanDetailsRationCardNumber);
                Log.d("pacsloanTabledata", String.valueOf(pacsloanTabledata));
                form = String.valueOf(pacsloanTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);
                pacsloanRcNolist = gson.fromJson(form, new TypeToken<List<PacsLoanRationTableData>>() {
                }.getType());


                // Data For PacsPaymentDetails AadharNumber
                JSONObject pacsPaymentTabledata = new JSONObject(pacsPaymentDetailsRationCardNumber);
                Log.d("pacsPaymentTabledata", String.valueOf(pacsPaymentTabledata));
                form = String.valueOf(pacsPaymentTabledata);
                form = form.replace("{", "[{");
                Log.d("form_1",""+form);
                form = form.replace("}", "}]");
                Log.d("form_2",""+form);
                pacsRationPaymentList = gson.fromJson(form, new TypeToken<List<PacsPaymentRationTableData>>() {
                }.getType());
                Log.d("SIZE", bankloanrationdata.size() + " " + paymentRclist.size() + " " + pacsloanRcNolist.size() + " " + pacsRationPaymentList.size());


            } catch (Exception e) {
                e.printStackTrace();
            }

                if (bankloanrationdata.size() == 0 && paymentRclist.size() == 0 && pacsloanRcNolist.size() == 0 && pacsRationPaymentList.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ClwsStatusRationCardDetails.this, R.style.MyDialogTheme);
                    builder.setTitle("STATUS")
                            .setMessage("No Data Found For this Record")
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    if (bankloanrationdata.size() != 0) {
                        tvRcNoCBLD.setVisibility(View.VISIBLE);
                        rvRcNoCommercialBank = findViewById(R.id.rvRcNoCommercialBank);
                        rvRcNoCommercialBank.setVisibility(View.VISIBLE);
                        rationadapter = new CommercialBankRationAdapter(bankloanrationdata, this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRcNoCommercialBank.setLayoutManager(mLayoutManager);
                        rvRcNoCommercialBank.setItemAnimator(new DefaultItemAnimator());
                        rvRcNoCommercialBank.setAdapter(rationadapter);
                    }
                    if (paymentRclist != null && paymentRclist.size() != 0) {
                        tvRcNoBPD.setVisibility(View.VISIBLE);
                        rvRcNoBankPaymentDetails = findViewById(R.id.rvRcNoBankPaymentDetails);
                        rvRcNoBankPaymentDetails.setVisibility(View.VISIBLE);
                        bankpaymentrationadapter = new BankPaymentRationDetailsAdapter(paymentRclist);
                        RecyclerView.LayoutManager bPaymentLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRcNoBankPaymentDetails.setLayoutManager(bPaymentLayoutManager);
                        rvRcNoBankPaymentDetails.setItemAnimator(new DefaultItemAnimator());
                        rvRcNoBankPaymentDetails.setAdapter(bankpaymentrationadapter);
                    }


                    if (pacsloanRcNolist != null && pacsloanRcNolist.size() != 0) {
                        tvRcNoPLD.setVisibility(View.VISIBLE);
                        rvRcNoPacsLoanDetails = findViewById(R.id.rvRcNoPacsLoanDetails);
                        rvRcNoPacsLoanDetails.setVisibility(View.VISIBLE);
                        pcloanrationAdapter = new PacsLoanRationDetailsAdapter(pacsloanRcNolist, this);
                        RecyclerView.LayoutManager pacsLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRcNoPacsLoanDetails.setLayoutManager(pacsLayoutManager);
                        rvRcNoPacsLoanDetails.setItemAnimator(new DefaultItemAnimator());
                        rvRcNoPacsLoanDetails.setAdapter(pcloanrationAdapter);
                    }

                    if (pacsRationPaymentList != null && pacsRationPaymentList.size() != 0) {
                        tvRcNoPPD.setVisibility(View.VISIBLE);
                        rvRcNoPacsPaymentDetails = findViewById(R.id.rvRcNoPacsPaymentDetails);
                        rvRcNoPacsPaymentDetails.setVisibility(View.VISIBLE);
                        pacspaymentrationAdapter = new PacsPaymentRationDetailsAdapter(pacsRationPaymentList);
                        RecyclerView.LayoutManager pacsPaymentLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRcNoPacsPaymentDetails.setLayoutManager(pacsPaymentLayoutManager);
                        rvRcNoPacsPaymentDetails.setItemAnimator(new DefaultItemAnimator());
                        rvRcNoPacsPaymentDetails.setAdapter(pacspaymentrationAdapter);
                    }

                }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
