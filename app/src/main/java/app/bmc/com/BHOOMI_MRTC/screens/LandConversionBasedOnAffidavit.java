package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import java.util.List;
import java.util.Objects;
import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnAffidavitAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;

public class LandConversionBasedOnAffidavit extends AppCompatActivity {

    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> AfdvtBasedOnAfdvtIdData;
    RecyclerView rvAffidavit;
    TextView tvAffidavit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion_based_on_affidavit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvAffidavit = findViewById(R.id.rvAffidavit);
        tvAffidavit = findViewById(R.id.tvAffidavit);

        String AFFIDAVIT_Id = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("AFFIDAVIT_ID");
        String AFFIDAVIT_ResponseData = (String) getIntent().getExtras().getSerializable("AFFIDAVIT_ResponseData");
        Log.d("LandConversionAffidavit", AFFIDAVIT_Id+" &&&&&"+AFFIDAVIT_ResponseData);

        if(AFFIDAVIT_ResponseData != null) {
            try {

                JSONArray jsonArray = new JSONArray(AFFIDAVIT_ResponseData);
                Log.d("jsonArray", String.valueOf(jsonArray));

                Gson gson = new Gson();
                AfdvtBasedOnAfdvtIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {}.getType());
                Log.d("SIZESUS", AfdvtBasedOnAfdvtIdData.size() + "");

                if (AfdvtBasedOnAfdvtIdData.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnAffidavit.this, R.style.MyDialogTheme);
                    builder.setTitle("STATUS")
                            .setMessage("No Data Found For this Record")
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    AfdvtBasedOnAfdvtIdData.size();
                    tvAffidavit.setVisibility(View.VISIBLE);
                    rvAffidavit.setVisibility(View.VISIBLE);
                    Log.d("AfdvtBasedOnAfdvtIdData",AfdvtBasedOnAfdvtIdData.size()+"");
                    LandConversionBasedOnAffidavitAdapter adapter = new LandConversionBasedOnAffidavitAdapter(AfdvtBasedOnAfdvtIdData,this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvAffidavit.setLayoutManager(mLayoutManager);
                    rvAffidavit.setItemAnimator(new DefaultItemAnimator());
                    rvAffidavit.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ExceptionSUS", e + "");
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
