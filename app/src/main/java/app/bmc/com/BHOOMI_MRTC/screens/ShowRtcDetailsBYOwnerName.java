package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowRtcOwnerReportAdapter;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;

public class ShowRtcDetailsBYOwnerName extends AppCompatActivity {

    private LinearLayout lv_show_details;
    private ListView lv_OwnerDetails;

    ArrayList<RTCByOwnerNameResponse> rtcByOwnerNameResponseList;
    ListView listView;
    private static ShowRtcOwnerReportAdapter adapter;

    private  String response_result, distId, talkId, hblId, village_id;
    ArrayList<String> distId_array = new ArrayList<>(), talkId_array = new ArrayList<>(), hblId_array = new ArrayList<>(), village_id_array = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rtc_details_byowner_name);

//        lv_show_details  = findViewById(R.id.lv_show_details);
        lv_OwnerDetails =  findViewById(R.id.lv_OwnerDetails);

        intent = getIntent();
        rtcByOwnerNameResponseList= new ArrayList<>();



        if(intent != null)
        {
            response_result = intent.getStringExtra("response_data");

            distId = intent.getStringExtra("distId");
            talkId = intent.getStringExtra("talkId");
            hblId = intent.getStringExtra("hblId");
            village_id = intent.getStringExtra("village_id");

            Log.d("distId",distId);
            Log.d("talkId",talkId);
            Log.d("hblId",hblId);
            Log.d("village_id",village_id);

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(response_result);
                Log.d("jsonArray_", "str: "+jsonArray);

                Type listType = new TypeToken<ArrayList<RTCByOwnerNameResponse>>() {
                }.getType();
                rtcByOwnerNameResponseList = new Gson().fromJson(jsonArray.toString(), listType);
                Log.d("rtcByOwnerName",""+rtcByOwnerNameResponseList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//        dataModels.add(new DataModel("Raj Shekhar ABCDEFGH", "12", "*","13"));
//        dataModels.add(new DataModel("Ankur ABCDEFGH", "12", "*","13"));
//        dataModels.add(new DataModel("Amir Khan ABCDEFGH", "12", "*","13"));
//        dataModels.add(new DataModel("Sunil ABCDEFGH", "12", "*","13"));
//        dataModels.add(new DataModel("Nishchita", "12", "*","13"));
//        dataModels.add(new DataModel("Anand Pandey ABCDEFGH", "12", "*","13"));
//        dataModels.add(new DataModel("Pawan Kumar", "12", "*","13"));
//        dataModels.add(new DataModel("Shivu owner", "12", "*","13"));
//        dataModels.add(new DataModel("Ashik", "12", "*","13"));
//        dataModels.add(new DataModel("Darshan", "12", "*","13"));
//        dataModels.add(new DataModel("Muthu Kumar", "12", "*","13"));

        distId_array.clear();
        talkId_array.clear();
        hblId_array.clear();
        village_id_array.clear();

        for (int i=1;i<=rtcByOwnerNameResponseList.size();i++) {
            distId_array.add(distId);
            talkId_array.add(talkId);
            hblId_array.add(hblId);
            village_id_array.add(village_id);
        }

        Log.d("rtcByOwner_array", ""+rtcByOwnerNameResponseList.size());
        Log.d("distId_array", ""+distId_array.size());
        Log.d("talkId_array", ""+talkId_array.size());
        Log.d("hblId_array", ""+hblId_array.size());
        Log.d("village_id_array", ""+village_id_array.size());

        adapter= new ShowRtcOwnerReportAdapter(rtcByOwnerNameResponseList,getApplicationContext(), distId_array, talkId_array, hblId_array, village_id_array);
//        adapter= new ShowRtcOwnerReportAdapter(rtcByOwnerNameResponseList,getApplicationContext());

        lv_OwnerDetails.setAdapter(adapter);



    }
}