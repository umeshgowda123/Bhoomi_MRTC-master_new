package app.bmc.com.bhoomi.screens;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.LoanWaiverBankWiseDetailsAdapter;
import app.bmc.com.bhoomi.adapters.MutulalDetailsAdapter;
import app.bmc.com.bhoomi.model.LoanWaiverBankResponseData;
import app.bmc.com.bhoomi.model.MutualPendacyData;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowLoanWaiverReportBankWise extends AppCompatActivity {

    private String banKResponseData;

    private List<LoanWaiverBankResponseData> myBankDataList = new ArrayList<>();

    private RecyclerView rvLoanWaiverBankReport;

    private LoanWaiverBankWiseDetailsAdapter cadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_waiver_report_bank_wise);

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
        rvLoanWaiverBankReport =  findViewById(R.id.rvLoanWaiverBankReport);
        banKResponseData = (String) getIntent().getStringExtra("bank_response_data");

        showData(banKResponseData);

    }


    private void showData(String bankResponseData) {

        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(bankResponseData.replace("\r\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");

            JSONObject responseObject = new JSONObject(formatted);

            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");

            JSONArray responseData =  mutationDetails.getJSONArray("Table");

            JSONArray mutationEntries = null;

            if(formatted.contains("Table"))
            {
                mutationEntries = mutationDetails.getJSONArray("Table");
                Type listType = new TypeToken<List<LoanWaiverBankResponseData>>() {
                }.getType();
                myBankDataList = new Gson().fromJson(mutationEntries.toString(), listType);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myBankDataList.size() > 0) {
            cadapter = new LoanWaiverBankWiseDetailsAdapter(myBankDataList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvLoanWaiverBankReport.setLayoutManager(mLayoutManager);
            rvLoanWaiverBankReport.setItemAnimator(new DefaultItemAnimator());
            rvLoanWaiverBankReport.setAdapter(cadapter);

        }else
        {
            Toast.makeText(ShowLoanWaiverReportBankWise.this, "No Data Found!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
