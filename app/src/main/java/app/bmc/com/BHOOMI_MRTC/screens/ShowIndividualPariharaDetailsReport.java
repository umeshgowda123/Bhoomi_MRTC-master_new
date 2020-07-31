package app.bmc.com.BHOOMI_MRTC.screens;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.IndividualPariharaDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.IndividualPariharaPayementAdapter;
import app.bmc.com.BHOOMI_MRTC.model.PariharaEntry;
import app.bmc.com.BHOOMI_MRTC.model.PaymentDetail;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowIndividualPariharaDetailsReport extends AppCompatActivity {

    private String pariharaqResponsedata;

    private RecyclerView rvPariharaDetails;
    private RecyclerView rvPaymentDetails;

    private IndividualPariharaDetailsAdapter cadapter;
    private IndividualPariharaPayementAdapter cpaymentAdapter;

    private List<PariharaEntry> myPariharaList = new ArrayList<>();
    private List<PaymentDetail> myPaymentList = new ArrayList<>();

    String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_individual_parihara_details_report);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvPariharaDetails = findViewById(R.id.rvPariharaDetails);
        rvPaymentDetails = findViewById(R.id.rvPaymentDetails);

        pariharaqResponsedata = getIntent().getStringExtra("response_data");

        showData(pariharaqResponsedata);
    }

    private void showData(String pariharaqResponsedata) {
        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(pariharaqResponsedata.replace("\\r\\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);
            JSONObject rtc = responseObject.getJSONObject("NewDataSet");
            JSONArray pariharaEntries = null;
            JSONArray paymentEntries = null;

            if(formatted.contains("PariharaEntries"))
            {
                String form = String.valueOf(rtc);
                form = form.replace("{\"PariharaEntries\":{", "{\"PariharaEntries\":[{");
                form = form.replace("}}", "}]}");
                rtc =  new JSONObject(form);

                pariharaEntries = rtc.getJSONArray("PariharaEntries");
                Type listType = new TypeToken<List<PariharaEntry>>() {
                }.getType();
                myPariharaList = new Gson().fromJson(pariharaEntries.toString(), listType);
            }if(formatted.contains("PaymentDetails"))
            {
                String form1 = String.valueOf(rtc);
                form1 = form1.replace("{\"PaymentDetails\":{", "{\"PaymentDetails\":[{");
                form1 = form1.replace("}}", "}]}");
                rtc =  new JSONObject(form1);

                paymentEntries = rtc.getJSONArray("PaymentDetails");
                Type paymentlistType = new TypeToken<List<PaymentDetail>>() {
                }.getType();
                myPaymentList = new Gson().fromJson(paymentEntries.toString(), paymentlistType);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myPariharaList.size() > 0) {
            cadapter = new IndividualPariharaDetailsAdapter(myPariharaList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvPariharaDetails.setLayoutManager(mLayoutManager);
            rvPariharaDetails.setItemAnimator(new DefaultItemAnimator());
            rvPariharaDetails.setAdapter(cadapter);

        }
        if (myPariharaList.size() > 0 && myPaymentList.size() > 0) {
            cpaymentAdapter = new IndividualPariharaPayementAdapter(myPariharaList, myPaymentList, this);
            RecyclerView.LayoutManager pmLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvPaymentDetails.setLayoutManager(pmLayoutManager);
            rvPaymentDetails.setItemAnimator(new DefaultItemAnimator());
            rvPaymentDetails.setAdapter(cpaymentAdapter);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
