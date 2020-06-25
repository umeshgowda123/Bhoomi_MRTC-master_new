package app.bmc.com.BHOOMI_MRTC.screens;

import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
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

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.LoanWaiverPacsWiseDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.PacsWaiverBankResponseData;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowLoanWaiverPacsReportBankWise extends AppCompatActivity {

    private String pacsResponseData;

    private List<PacsWaiverBankResponseData> mypacsDataList = new ArrayList<>();

    private List<PacsWaiverBankResponseData> rowsArrayList  = new ArrayList<>();

    private RecyclerView rvPacsLoanWaiverBankReport;

    private LoanWaiverPacsWiseDetailsAdapter cadapter;

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_waiver_pacs_report_bank_wise);

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

        rvPacsLoanWaiverBankReport =  findViewById(R.id.rvPacsLoanWaiverBankReport);
        pacsResponseData = (String) getIntent().getStringExtra("pacs_response_data");

        showData(pacsResponseData);
    }



    private void showData(String pacsResponseData) {

        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(pacsResponseData.replace("\r\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");

            JSONObject responseObject = new JSONObject(formatted);

            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");

            JSONArray mutationEntries = null;

            if(formatted.contains("Table"))
            {
                String form = String.valueOf(mutationDetails);
                form = form.replace("{\"Table\":{", "{\"Table\":[{");
                Log.d("form_1",""+form);
                form = form.replace("}}", "}]}");
                Log.d("form_2",""+form);
                mutationDetails =  new JSONObject(form);
                Log.d("mutationDetails",""+mutationDetails);

                mutationEntries = mutationDetails.getJSONArray("Table");
                Type listType = new TypeToken<List<PacsWaiverBankResponseData>>() {
                }.getType();
                mypacsDataList = new Gson().fromJson(mutationEntries.toString(), listType);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        if (mypacsDataList.size() > 0) {

            populateData();
            cadapter = new LoanWaiverPacsWiseDetailsAdapter(rowsArrayList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvPacsLoanWaiverBankReport.setLayoutManager(mLayoutManager);
            rvPacsLoanWaiverBankReport.setItemAnimator(new DefaultItemAnimator());
            rvPacsLoanWaiverBankReport.setAdapter(cadapter);
            initScrollListener();

        }else {
            Toast.makeText(ShowLoanWaiverPacsReportBankWise.this, "No Report Found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateData() {
        int i = 0;
//        while (i < 10) {
        while (i < mypacsDataList.size()) { //---SUSMITA
            rowsArrayList.add(mypacsDataList.get(i));
            i++;
        }
    }

    private void initScrollListener() {
        rvPacsLoanWaiverBankReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        loadMore(); // ----COMMENTED BY SUSMITA
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
                Log.d("CHECKING","rowsArrayList : "+rowsArrayList.size()+"");
                int scrollPosition = rowsArrayList.size();
                Log.d("CHECKING","scrollPosition : "+scrollPosition+"");
                cadapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                Log.d("CHECKING","currentSize : "+currentSize+"");
                int nextLimit = currentSize + 10;
                Log.d("CHECKING","nextLimit : "+nextLimit+"");

                while (currentSize - 1 < nextLimit) {
                    Log.d("CHECKING","Entered");
                    rowsArrayList.add(mypacsDataList.get(currentSize));
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
