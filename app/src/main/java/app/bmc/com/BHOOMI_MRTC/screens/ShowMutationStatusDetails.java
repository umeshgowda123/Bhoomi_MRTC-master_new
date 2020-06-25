package app.bmc.com.BHOOMI_MRTC.screens;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
import app.bmc.com.BHOOMI_MRTC.adapters.MutationStatusAdapter;
import app.bmc.com.BHOOMI_MRTC.model.MutationStatusData;

public class ShowMutationStatusDetails extends AppCompatActivity {

    private List<MutationStatusData> myPendingDataList = new ArrayList<>();

    private RecyclerView rvMutationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mutation_status_report);

        rvMutationStatus =  findViewById(R.id.rvMutationStatus);

        String status_data_response = getIntent().getStringExtra("status_response_data");

        showData(status_data_response);
    }

    private void showData(String status_data_response) {

        try {

//            XmlToJson xmlToJson = new XmlToJson.Builder(status_data_response.replace("\\r\\n", "").trim()).build();
//            Log.d("xmlToJson_Formatted", ""+xmlToJson.toFormattedString());
//            String formatted = xmlToJson.toFormattedString().replace("\n", "");
//            Log.d("formatted",""+formatted);
//
//            JSONObject responseObject = new JSONObject(formatted);
//            Log.d("responseObject",""+responseObject);
//            JSONObject mutationDetails =  responseObject.getJSONObject("NewDataSet");
//            Log.d("mutation_NewDataSet",""+mutationDetails);
//            JSONArray mutationEntries = null;

            JSONObject obj1 = new JSONObject(status_data_response);
            Log.d("mutationStatusObj1", String.valueOf(obj1));

            String form = String.valueOf(obj1);
            form = form.replace("{\"MutationStatus\":{", "{\"MutationStatus\":[{");
            Log.d("form_1",""+form);
            form = form.replace("}}", "}]}");
            Log.d("form_2",""+form);

            JSONObject obj = new JSONObject(form);
            Log.d("mutationStatusObj", String.valueOf(obj));

            JSONArray tableEntries;

            if(status_data_response.contains("MutationStatus"))
            {
                //tableEntries = new JSONArray(form);
                tableEntries = obj.getJSONArray("MutationStatus");
                Type listType = new TypeToken<List<MutationStatusData>>() {
                }.getType();
                myPendingDataList = new Gson().fromJson(tableEntries.toString(), listType);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        if (myPendingDataList.size() > 0) {
            MutationStatusAdapter cadapter = new MutationStatusAdapter(myPendingDataList, ShowMutationStatusDetails.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvMutationStatus.setLayoutManager(mLayoutManager);
            rvMutationStatus.setItemAnimator(new DefaultItemAnimator());
            rvMutationStatus.setAdapter(cadapter);

        }else
        {
            Toast.makeText(getApplicationContext(), "No Data Found!", Toast.LENGTH_SHORT).show();
        }

    }
}
