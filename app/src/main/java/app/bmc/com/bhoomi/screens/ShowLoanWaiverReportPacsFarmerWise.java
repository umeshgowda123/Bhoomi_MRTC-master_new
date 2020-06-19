package app.bmc.com.bhoomi.screens;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.LoanWaiverFarmerWiseDetailsAdapter;
import app.bmc.com.bhoomi.adapters.LoanWaiverPacsFarmerWiseDetailsAdapter;
import app.bmc.com.bhoomi.model.LoanWaiverFramerWiseResponseData;
import app.bmc.com.bhoomi.model.LoanWaiverPACSFramerWiseResponseData;
import app.bmc.com.bhoomi.model.PacsThreadSafeSingletonFarmerWiseClass;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowLoanWaiverReportPacsFarmerWise extends AppCompatActivity {

    private RecyclerView rvLoanWaiverPacsFarmerReport;
    private List<LoanWaiverPACSFramerWiseResponseData> myBankFarmerDataList = new ArrayList<>();

    private List<LoanWaiverPACSFramerWiseResponseData> rowsArrayList = new ArrayList<>();
    private String  pacsfarmerDataResponse;

    private LoanWaiverPacsFarmerWiseDetailsAdapter cadapter;

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_waiver_report_pacs_farmer_wise);

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

        rvLoanWaiverPacsFarmerReport =  findViewById(R.id.rvLoanWaiverPacsFarmerReport);
        pacsfarmerDataResponse = PacsThreadSafeSingletonFarmerWiseClass.getInstance().getResponse();
        showData(pacsfarmerDataResponse);

    }

    private void showData(String pacsFarmerResponsedata) {
        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(pacsFarmerResponsedata.replace("\\r\\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);
            JSONObject rtc = responseObject.getJSONObject("NewDataSet");
            JSONArray tableEntries = null;
            JSONArray paymentEntries = null;

            if(formatted.contains("Table"))
            {
                tableEntries = rtc.getJSONArray("Table");
                Type listType = new TypeToken<List<LoanWaiverPACSFramerWiseResponseData>>() {
                }.getType();
                myBankFarmerDataList = new Gson().fromJson(tableEntries.toString(), listType);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myBankFarmerDataList.size() > 0) {

            populateData();
            cadapter = new LoanWaiverPacsFarmerWiseDetailsAdapter(rowsArrayList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvLoanWaiverPacsFarmerReport.setLayoutManager(mLayoutManager);
            rvLoanWaiverPacsFarmerReport.setItemAnimator(new DefaultItemAnimator());
            rvLoanWaiverPacsFarmerReport.setAdapter(cadapter);
            initScrollListener();

        }else
        {
            Intent intent  = new Intent(ShowLoanWaiverReportPacsFarmerWise.this,CommonErrorActivity.class);
            startActivity(intent);
        }
    }

    private void populateData() {
        int i = 0;
        while (i < myBankFarmerDataList.size()) {
            rowsArrayList.add(myBankFarmerDataList.get(i));
            i++;
        }
    }

    private void initScrollListener() {
        rvLoanWaiverPacsFarmerReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        //bottom of list!
//                        loadMore(); ---- MODIFIED BY SUSMITA
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        rowsArrayList.add(null);
        cadapter.notifyItemInserted(rowsArrayList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsArrayList.remove(rowsArrayList.size() - 1);
                int scrollPosition = rowsArrayList.size();
                cadapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    rowsArrayList.add(myBankFarmerDataList.get(currentSize));
                    currentSize++;
                }

                cadapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
