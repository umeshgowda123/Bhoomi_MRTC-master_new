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
import app.bmc.com.BHOOMI_MRTC.adapters.LoanWaiverPacsBranchWiseDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.LoanWaiverPacsBranchResponseData;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowLoanWaiverPacsReportBranchWise extends AppCompatActivity {


    private RecyclerView rvLoanWaiverPacsBranchReport;

    private List<LoanWaiverPacsBranchResponseData> myBankFarmerDataList = new ArrayList<>();

    private List<LoanWaiverPacsBranchResponseData> rowsArrayList  = new ArrayList<>();
    private String  branchPacsDataResponse;

    private LoanWaiverPacsBranchWiseDetailsAdapter cadapter;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loan_waiver_pacs_report_branch_wise);

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

        rvLoanWaiverPacsBranchReport =  findViewById(R.id.rvLoanWaiverPacsBranchReport);

        branchPacsDataResponse = getIntent().getStringExtra("pacs_branch_response_data");
        showData(branchPacsDataResponse);
    }


    private void showData(String branchPacsDataResponse) {
        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(branchPacsDataResponse.replace("\\r\\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);
            JSONObject rtc1 = responseObject.getJSONObject("NewDataSet");
            String form = rtc1.toString();
            form = form.replace("{\"Table\":{", "{\"Table\":[{");
            form = form.replace("}}", "}]}");
            JSONObject rtc = new JSONObject(form);
            JSONArray tableEntries = null;

            if(formatted.contains("Table"))
            {
                tableEntries = rtc.getJSONArray("Table");
                Type listType = new TypeToken<List<LoanWaiverPacsBranchResponseData>>() {
                }.getType();
                myBankFarmerDataList = new Gson().fromJson(tableEntries.toString(), listType);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myBankFarmerDataList.size() > 0) {

            populateData();
            cadapter = new LoanWaiverPacsBranchWiseDetailsAdapter(rowsArrayList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvLoanWaiverPacsBranchReport.setLayoutManager(mLayoutManager);
            rvLoanWaiverPacsBranchReport.setItemAnimator(new DefaultItemAnimator());
            rvLoanWaiverPacsBranchReport.setAdapter(cadapter);
            initScrollListener();

        }else
        {
            Toast.makeText(getApplicationContext(), "No Report Found", Toast.LENGTH_SHORT).show();

        }

    }

    private void populateData() {
        int i = 0;
//        while (i < 10) {
        while (i < myBankFarmerDataList.size()){// ----SUSMITA
            rowsArrayList.add(myBankFarmerDataList.get(i));
            i++;
        }
    }

    private void initScrollListener() {
        rvLoanWaiverPacsBranchReport.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        loadMore(); //-----COMMENTED BY SUSMITA
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
