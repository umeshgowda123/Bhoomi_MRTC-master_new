package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.CommonCertificatePaymentAdapter;
import app.bmc.com.BHOOMI_MRTC.model.PacsCertificateAadharTableData;

public class PaymentCertifiacteDetails extends AppCompatActivity {

    private TextView tvPPD;
    private String pacsResponsedata;

    private List<PacsCertificateAadharTableData> pCertificateDetailslist= new ArrayList<>();
    private RecyclerView rvCBLWPaymentLoneeDetails;
    private CommonCertificatePaymentAdapter cCertificatePaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_certifiacte_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        pacsResponsedata = (String) getIntent().getStringExtra("PACS_RESPONSE_DATA");

        tvPPD = findViewById(R.id.tvPPD);
        rvCBLWPaymentLoneeDetails = findViewById(R.id.rvCBLWPaymentLoneeDetails);

        if (pacsResponsedata != null) {
            try {
                JSONArray jsonarray = new JSONArray(pacsResponsedata);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String trn_custid = jsonobject.getString("TRN_CUSTID");
                    String trn_bnkid = jsonobject.getString("TRN_BNKID");
                    String trn_loonenme = jsonobject.getString("TRN_LOONENME");
                    String trn_rcno = jsonobject.getString("TRN_RCNO");
                    String fmly_distcde = jsonobject.getString("FMLY_DISTCDE");
                    String fmly_distnme = jsonobject.getString("FMLY_DISTNME");
                    String fmly_tlkcde = jsonobject.getString("FMLY_TLKCDE");
                    String fmly_tlknme = jsonobject.getString("FMLY_TLKNME");
                    String dcc_bnk_nme_en = jsonobject.getString("DCC_BNK_NME_EN");
                    String pacs_bnk_brnch_cde = jsonobject.getString("PACS_BNK_BRNCH_CDE");
                    String pacs_bnk_brnch_nme = jsonobject.getString("PACS_BNK_BRNCH_NME");
                    String pcs_pur = jsonobject.getString("PCS_PUR");
                    String pcs_liab_10072018 = jsonobject.getString("PCS_LIAB_10072018");
                    String sts_desc = jsonobject.getString("STS_DESC");
                    String otrn_shareno = jsonobject.getString("OTRN_SHARENO");
                    String releasedAmount = jsonobject.getString("ReleasedAmount");
                    String releasedDate = jsonobject.getString("ReleasedDate");
                    String paymentPhase = jsonobject.getString("PaymentPhase");
                    String paymentBatch = jsonobject.getString("PaymentBatch");
                    String paymentStatus = jsonobject.getString("PaymentStatus");
                    String paymentRemarks = jsonobject.getString("PaymentRemarks");
                    String custFather_guardian = jsonobject.getString("CustFather_Guardian");
                    PacsCertificateAadharTableData data = new PacsCertificateAadharTableData();
                    data.setTrn_custid(trn_custid);
                    data.setTrn_bnkid(trn_bnkid);
                    data.setTrn_loonenme(trn_loonenme);
                    data.setTrn_rcno(trn_rcno);
                    data.setFmly_distcde(fmly_distcde);
                    data.setFmly_distnme(fmly_distnme);
                    data.setFmly_tlkcde(fmly_tlkcde);
                    data.setFmly_tlknme(fmly_tlknme);
                    data.setDcc_bnk_nme_en(dcc_bnk_nme_en);
                    data.setPacs_bnk_brnch_cde(pacs_bnk_brnch_cde);
                    data.setPacs_bnk_brnch_nme(pacs_bnk_brnch_nme);
                    data.setPcs_pur(pcs_pur);
                    data.setPcs_liab_10072018(pcs_liab_10072018);
                    data.setSts_desc(sts_desc);
                    data.setOtrn_shareno(otrn_shareno);
                    data.setReleasedAmount(releasedAmount);
                    data.setReleasedDate(releasedDate);
                    data.setPaymentPhase(paymentPhase);
                    data.setPaymentBatch(paymentBatch);
                    data.setPaymentStatus(paymentStatus);
                    data.setPaymentRemarks(paymentRemarks);
                    data.setCustFather_guardian(custFather_guardian);
                    pCertificateDetailslist.add(data);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(pCertificateDetailslist.size() ==0 && pCertificateDetailslist.isEmpty())
        {
            Intent intent = new Intent(PaymentCertifiacteDetails.this, PacsCertificateDataNotPresent.class);
            startActivity(intent);
        }else {
            if (pCertificateDetailslist != null && pCertificateDetailslist.size() != 0) {
                tvPPD.setVisibility(View.VISIBLE);
                rvCBLWPaymentLoneeDetails.setVisibility(View.VISIBLE);
                cCertificatePaymentAdapter = new CommonCertificatePaymentAdapter(pCertificateDetailslist, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvCBLWPaymentLoneeDetails.setLayoutManager(mLayoutManager);
                rvCBLWPaymentLoneeDetails.setItemAnimator(new DefaultItemAnimator());
                rvCBLWPaymentLoneeDetails.setAdapter(cCertificatePaymentAdapter);
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
