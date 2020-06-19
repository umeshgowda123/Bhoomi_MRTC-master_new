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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.adapters.LandWiseBenificaryAdapter;
import app.bmc.com.bhoomi.adapters.VillageWiseBenificaryAdapter;
import app.bmc.com.bhoomi.model.BenificaryDataLandWise;
import app.bmc.com.bhoomi.model.BenificaryDataVlgWise;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowPariharaBenificiaryDetailsLandWise extends AppCompatActivity {


    private RecyclerView rvBenificaryDetailsLandWise;

    private String phraBenificaryLandDataResponse;

    private LandWiseBenificaryAdapter cadapter;

    private List<BenificaryDataLandWise> myBenificaryLandList = new ArrayList<>();

    private List<BenificaryDataLandWise> rowsArrayList = new ArrayList<>();

    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parihara_benificiary_details_land_wise);

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

        rvBenificaryDetailsLandWise =  findViewById(R.id.rvBenificaryDetailsLandWise);

        phraBenificaryLandDataResponse = (String) getIntent().getStringExtra("response_data");

        showData(phraBenificaryLandDataResponse);
    }

    private void showData(String phraBenificaryLandDataResponse) {
        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(phraBenificaryLandDataResponse.replace("\\r\\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);
            JSONObject rtc = responseObject.getJSONObject("NewDataSet");
            JSONArray  tableEntries = rtc.getJSONArray("Table");


            if(formatted.contains("Table"))
            {
                Type listType = new TypeToken<List<BenificaryDataLandWise>>() {
                }.getType();
                myBenificaryLandList = new Gson().fromJson(tableEntries.toString(), listType);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        if (myBenificaryLandList.size() > 0) {

           // populateData();
            cadapter = new LandWiseBenificaryAdapter(myBenificaryLandList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvBenificaryDetailsLandWise.setLayoutManager(mLayoutManager);
            rvBenificaryDetailsLandWise.setItemAnimator(new DefaultItemAnimator());
            rvBenificaryDetailsLandWise.setAdapter(cadapter);
          //  initScrollListener();

        }else
        {
            Intent intent  = new Intent(ShowPariharaBenificiaryDetailsLandWise.this,CommonErrorActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


  /*  private void populateData() {
        int i = 0;
        while (i < 3) {
            rowsArrayList.add(myBenificaryLandList.get(i));
            i++;
        }
    }

    private void initScrollListener() {
        rvBenificaryDetailsLandWise.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }*/

   /* private void loadMore() {
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
                    rowsArrayList.add(myBenificaryLandList.get(currentSize));
                    currentSize++;
                }

                cadapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }*/
}
