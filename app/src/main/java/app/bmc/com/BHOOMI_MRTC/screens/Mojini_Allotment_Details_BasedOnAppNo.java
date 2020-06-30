package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.MojiniAllotmentDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.MojiniAllotementDetailsTable;

public class Mojini_Allotment_Details_BasedOnAppNo extends AppCompatActivity {

    List<MojiniAllotementDetailsTable> allotementDetailsTableList;
    RecyclerView rvAllotmentDetail;
    String allotment_ResponseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mojini_allotment_details_basedonappno);

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

        rvAllotmentDetail = findViewById(R.id.rvAllotmentDetail);

        allotment_ResponseData = getIntent().getStringExtra("allotment_ResponseData");
        Log.d("res",allotment_ResponseData+"");

        if(allotment_ResponseData != null) {
            try {

                JSONArray jsonArray = new JSONArray(allotment_ResponseData);
                Log.d("jsonArray", String.valueOf(jsonArray));

                Gson gson = new Gson();
                allotementDetailsTableList = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<MojiniAllotementDetailsTable>>() {}.getType());
                Log.d("SIZESUS", allotementDetailsTableList.size() + "");

                if (allotementDetailsTableList.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Mojini_Allotment_Details_BasedOnAppNo.this, R.style.MyDialogTheme);
                    builder.setTitle("STATUS")
                            .setMessage("No Data Found For this Record")
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    allotementDetailsTableList.size();
                    Log.d("allotementDetailslist",allotementDetailsTableList.size()+"");
                    MojiniAllotmentDetailsAdapter adapter = new MojiniAllotmentDetailsAdapter(allotementDetailsTableList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvAllotmentDetail.setLayoutManager(mLayoutManager);
                    rvAllotmentDetail.setItemAnimator(new DefaultItemAnimator());
                    rvAllotmentDetail.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ExceptionSUS", e + "");
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Details Found", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
