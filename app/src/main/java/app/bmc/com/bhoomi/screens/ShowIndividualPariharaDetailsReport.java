package app.bmc.com.bhoomi.screens;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.IndividualPariharaDetailsAdapter;
import app.bmc.com.bhoomi.adapters.IndividualPariharaPayementAdapter;
import app.bmc.com.bhoomi.model.PariharaEntry;
import app.bmc.com.bhoomi.model.PaymentDetail;
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

        rvPariharaDetails = findViewById(R.id.rvPariharaDetails);
        rvPaymentDetails = findViewById(R.id.rvPaymentDetails);

        pariharaqResponsedata = (String) getIntent().getStringExtra("response_data");

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
                pariharaEntries = rtc.getJSONArray("PariharaEntries");
                Type listType = new TypeToken<List<PariharaEntry>>() {
                }.getType();
                myPariharaList = new Gson().fromJson(pariharaEntries.toString(), listType);
            }if(formatted.contains("PaymentDetails"))
            {
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
        if (myPaymentList.size() > 0) {
            cpaymentAdapter = new IndividualPariharaPayementAdapter(myPaymentList, this);
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
