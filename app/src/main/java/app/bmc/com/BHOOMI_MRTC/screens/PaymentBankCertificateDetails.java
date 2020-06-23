package app.bmc.com.BHOOMI_MRTC.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.CommonBankCertificatePaymentAdapter;
import app.bmc.com.BHOOMI_MRTC.model.CommonBankPaymentCertiTableData;

public class PaymentBankCertificateDetails extends AppCompatActivity {

    private TextView tvBankPaymentCertificateDetails;
    private String bankResponsedata;

    private String commonId;

    private List<CommonBankPaymentCertiTableData> bankCertificateDetailslist = new ArrayList<>();
    private RecyclerView rvBankPaymentCertificateDetails;
    private CommonBankCertificatePaymentAdapter cBankCertificatePaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_bank_certificate_details);

        tvBankPaymentCertificateDetails = findViewById(R.id.tvBankPaymentCertificateDetails);
        rvBankPaymentCertificateDetails = findViewById(R.id.rvBankPaymentCertificateDetails);


        bankResponsedata = (String) getIntent().getStringExtra("response_data");
        commonId = getIntent().getStringExtra("PACS_Request_Parameter");

        if(bankResponsedata!= null)
        {
            try
            {
                JSONArray jsonarray = new JSONArray(bankResponsedata);
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
                    CommonBankPaymentCertiTableData data = new CommonBankPaymentCertiTableData();
                    data.setTRN_CUSTID(trn_custid);
                    data.setTRN_BNKID(trn_bnkid);
                    data.setTRN_LOONENME(trn_loonenme);
                    data.setTRN_RCNO(trn_rcno);
                    data.setFMLY_DISTCDE(fmly_distcde);
                    data.setFMLY_DISTNME(fmly_distnme);
                    data.setFMLY_TLKCDE(fmly_tlkcde);
                    data.setFMLY_TLKNME(fmly_tlknme);
                    data.setDCC_BNK_NME_EN(dcc_bnk_nme_en);
                    data.setPACS_BNK_BRNCH_CDE(pacs_bnk_brnch_cde);
                    data.setPACS_BNK_BRNCH_NME(pacs_bnk_brnch_nme);
                    data.setPCS_PUR(pcs_pur);
                    data.setPCS_LIAB_10072018(pcs_liab_10072018);
                    data.setSTS_DESC(sts_desc);
                    data.setOTRN_SHARENO(otrn_shareno);
                    data.setReleasedAmount(releasedAmount);
                    data.setReleasedDate(releasedDate);
                    data.setPaymentPhase(paymentPhase);
                    data.setPaymentBatch(paymentBatch);
                    data.setPaymentStatus(paymentStatus);
                    data.setPaymentRemarks(paymentRemarks);
                    data.setCustFather_Guardian(custFather_guardian);
                    bankCertificateDetailslist.add(data);
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        if(!bankCertificateDetailslist.isEmpty()) {
            cBankCertificatePaymentAdapter = new CommonBankCertificatePaymentAdapter(bankCertificateDetailslist,commonId,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvBankPaymentCertificateDetails.setLayoutManager(mLayoutManager);
            rvBankPaymentCertificateDetails.setItemAnimator(new DefaultItemAnimator());
            rvBankPaymentCertificateDetails.setAdapter(cBankCertificatePaymentAdapter);
        }else
        {
            Toast.makeText(getApplicationContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
        }
    }
}
