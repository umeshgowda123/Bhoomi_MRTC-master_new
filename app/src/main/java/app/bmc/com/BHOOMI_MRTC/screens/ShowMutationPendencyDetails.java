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
import app.bmc.com.BHOOMI_MRTC.adapters.MutulalDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.MutualPendacyData;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowMutationPendencyDetails extends AppCompatActivity {


    String pending_data_response;

    private List<MutualPendacyData> myPendingDataList = new ArrayList<>();

    MutulalDetailsAdapter cadapter;

    private RecyclerView rvMutationPendency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mutation_pendency_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvMutationPendency =  findViewById(R.id.rvMutationPendency);

        pending_data_response  = getIntent().getStringExtra("ped_response_data");

        showData(pending_data_response);

    }


    private void showData(String pendingResponseData) {

        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(pendingResponseData.replace("\\r\\n", "").trim()).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");

            JSONObject responseObject = new JSONObject(formatted);
            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");
            JSONArray mutationEntries = null;

            if(formatted.contains("Table"))
            {
                String form = String.valueOf(mutationDetails);
                form = form.replace("{\"Table\":{", "{\"Table\":[{");
                form = form.replace("}}", "}]}");
                mutationDetails =  new JSONObject(form);

                mutationEntries = mutationDetails.getJSONArray("Table");
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
