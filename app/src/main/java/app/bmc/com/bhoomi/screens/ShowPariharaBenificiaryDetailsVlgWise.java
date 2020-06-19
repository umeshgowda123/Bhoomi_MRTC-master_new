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

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.IndividualPariharaDetailsAdapter;
import app.bmc.com.bhoomi.adapters.IndividualPariharaPayementAdapter;
import app.bmc.com.bhoomi.adapters.VillageWiseBenificaryAdapter;
import app.bmc.com.bhoomi.model.BenificaryDataVlgWise;
import app.bmc.com.bhoomi.model.PariharaEntry;
import app.bmc.com.bhoomi.model.PaymentDetail;
import app.bmc.com.bhoomi.model.ThreadSafeSingletonClass;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowPariharaBenificiaryDetailsVlgWise extends AppCompatActivity {


    private RecyclerView rvBenificaryDetailsVlgWise;

    private String  phraBenificaryDataResponse;

    private VillageWiseBenificaryAdapter cadapter;

    private List<BenificaryDataVlgWise> myBenificaryList = new ArrayList<>();


    private List<BenificaryDataVlgWise> rowsArrayList = new ArrayList<>();

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parihara_benificiary_details_vlg_wise);

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

        rvBenificaryDetailsVlgWise =  findViewById(R.id.rvBenificaryDetailsVlgWise);

     //   phraBenificaryDataResponse = (String) getIntent().getStringExtra("response_data");

        phraBenificaryDataResponse =  ThreadSafeSingletonClass.getInstance().getResponse();


        showData(phraBenificaryDataResponse);

    }



    private void showData(String benificaryResponsedata) {
        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(benificaryResponsedata.replace("\r\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);
            JSONObject rtc = responseObject.getJSONObject("NewDataSet");
            JSONArray tableEntries;

            if(formatted.contains("Table"))
            {
                String form = String.valueOf(rtc);
                form = form.replace("{\"Table\":{", "{\"Table\":[{");
                Log.d("form_1",""+form);
                form = form.replace("}}", "}]}");
                Log.d("form_2",""+form);
                rtc =  new JSONObject(form);
                Log.d("rtc",""+rtc);

                tableEntries = rtc.getJSONArray("Table");
                Type listType = new TypeToken<List<BenificaryDataVlgWise>>() {
                }.getType();
                myBenificaryList = new Gson().fromJson(tableEntries.toString(), listType);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myBenificaryList.size() > 0) {

            populateData();
            cadapter = new VillageWiseBenificaryAdapter(rowsArrayList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvBenificaryDetailsVlgWise.setLayoutManager(mLayoutManager);
            rvBenificaryDetailsVlgWise.setItemAnimator(new DefaultItemAnimator());
            rvBenificaryDetailsVlgWise.setAdapter(cadapter);
            initScrollListener();

        }else
        {
            Toast.makeText(getApplicationContext(), "No Data Found!", Toast.LENGTH_SHORT).show();

        }

    }


    private void populateData() {
        int i = 0;
        while (i < myBenificaryList.size()) { //----SUSMITA
            rowsArrayList.add(myBenificaryList.get(i));
            i++;
        }
    }

    private void initScrollListener() {
        rvBenificaryDetailsVlgWise.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                        loadMore();
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
                    rowsArrayList.add(myBenificaryList.get(currentSize));
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
