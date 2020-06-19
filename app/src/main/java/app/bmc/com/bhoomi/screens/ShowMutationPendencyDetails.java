package app.bmc.com.bhoomi.screens;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import app.bmc.com.bhoomi.adapters.MutulalDetailsAdapter;
import app.bmc.com.bhoomi.model.MutualPendacyData;
import app.bmc.com.bhoomi.model.PariharaEntry;
import app.bmc.com.bhoomi.model.PaymentDetail;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowMutationPendencyDetails extends AppCompatActivity {


    private String pending_data_response;

    private List<MutualPendacyData> myPendingDataList = new ArrayList<>();

    private MutulalDetailsAdapter cadapter;

    private RecyclerView rvMutationPendency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mutation_pendency_details);

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

        rvMutationPendency =  findViewById(R.id.rvMutationPendency);

        pending_data_response  = (String) getIntent().getStringExtra("ped_response_data");

        showData(pending_data_response);

    }


    private void showData(String pendingResponseData) {

        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(pendingResponseData.replace("\\r\\n", "").trim()).build();
            Log.d("xmlToJson_Formatted", ""+xmlToJson.toFormattedString());
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            Log.d("formatted",""+formatted);

            JSONObject responseObject = new JSONObject(formatted);
            Log.d("responseObject",""+responseObject);
            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");
            Log.d("mutation_NewDataSet",""+mutationDetails);
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
                Log.d("mutationEntries",""+mutationEntries);
                Type listType = new TypeToken<List<MutualPendacyData>>() {
                }.getType();
                myPendingDataList = new Gson().fromJson(mutationEntries.toString(), listType);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myPendingDataList.size() > 0) {
            cadapter = new MutulalDetailsAdapter(myPendingDataList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvMutationPendency.setLayoutManager(mLayoutManager);
            rvMutationPendency.setItemAnimator(new DefaultItemAnimator());
            rvMutationPendency.setAdapter(cadapter);

        }else
        {
            Toast.makeText(ShowMutationPendencyDetails.this, "No Data Found!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
