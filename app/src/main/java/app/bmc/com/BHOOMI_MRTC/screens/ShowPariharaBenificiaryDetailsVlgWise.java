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
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.VillageWiseBenificaryAdapter;
import app.bmc.com.BHOOMI_MRTC.model.BenificaryDataVlgWise;
import app.bmc.com.BHOOMI_MRTC.model.ThreadSafeSingletonClass;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowPariharaBenificiaryDetailsVlgWise extends AppCompatActivity {


    private RecyclerView rvBenificaryDetailsVlgWise;

    private VillageWiseBenificaryAdapter cadapter;

    private List<BenificaryDataVlgWise> myBenificaryList = new ArrayList<>();


    private List<BenificaryDataVlgWise> rowsArrayList = new ArrayList<>();

    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parihara_benificiary_details_vlg_wise);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvBenificaryDetailsVlgWise =  findViewById(R.id.rvBenificaryDetailsVlgWise);

     //   phraBenificaryDataResponse = (String) getIntent().getStringExtra("response_data");

        String phraBenificaryDataResponse = ThreadSafeSingletonClass.getInstance().getResponse();


        showData(phraBenificaryDataResponse);
        Log.d("RES : ", phraBenificaryDataResponse);

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
            Log.d("Exception", Objects.requireNonNull(ex.getMessage()));
        }

        if (myBenificaryList.size() > 0) {
            populateData();
            cadapter = new VillageWiseBenificaryAdapter(rowsArrayList, ShowPariharaBenificiaryDetailsVlgWise.this);
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
        Log.d("myBenificaryList",myBenificaryList.size()+"");
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
//                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        rowsArrayList.add(null);
        cadapter.notifyItemInserted(rowsArrayList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
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
        }, 2000);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
