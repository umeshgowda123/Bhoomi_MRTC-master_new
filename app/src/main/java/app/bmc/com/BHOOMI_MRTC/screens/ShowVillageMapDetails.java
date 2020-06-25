package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import app.bmc.com.BHOOMI_MRTC.R;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class ShowVillageMapDetails extends AppCompatActivity {

    private String mapResponseData;

    private TextView tvMDistrictName;
    private TextView tvMTalukName;
    private TextView tvMHobliName;
    private TextView tvMVillageName;
    private ImageView imgPdfMap;

    private String pdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_village_map_details);

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

        tvMDistrictName = findViewById(R.id.tvMDistrictName);
        tvMTalukName = findViewById(R.id.tvMTalukName);
        tvMHobliName = findViewById(R.id.tvMHobliName);
        tvMVillageName = findViewById(R.id.tvMVillageName);
        imgPdfMap =  findViewById(R.id.imgPdfMap);


        mapResponseData = (String) getIntent().getStringExtra("map_response_data");

        showData(mapResponseData);
    }

    private void showData(String mapResponseData) {

        try {

            XmlToJson xmlToJson = new XmlToJson.Builder(mapResponseData).build();
            String formatted = xmlToJson.toFormattedString().replace("\n", "");
            JSONObject responseObject = new JSONObject(formatted);

            JSONObject mapDetails =  responseObject.getJSONObject("MAPDETAILS");

            Log.d("DATA",mapDetails.toString());

            String districtName =  mapDetails.getString("DISTRICT_NAME");
            String talukName =  mapDetails.getString("TALUKA_NAME");
            String hobliName =  mapDetails.getString("HOBLI_NAME");
            String villageName =  mapDetails.getString("VILLAGE_NAME");
            pdfUrl = mapDetails.getString("DOC_URL");


            if(pdfUrl != null)
            {
                imgPdfMap.setClickable(true);
            }

            if(districtName != null && talukName != null && hobliName != null && villageName != null)
            {
                tvMDistrictName.setText(districtName);
                tvMTalukName.setText(talukName);
                tvMHobliName.setText(hobliName);
                tvMVillageName.setText(villageName);
            }


            imgPdfMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ShowVillageMapDetails.this,ViewMapInPdfActivity.class);
                    intent.putExtra("MAP_PDF_URL",pdfUrl);
                    startActivity(intent);
                }
            });


        } catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
