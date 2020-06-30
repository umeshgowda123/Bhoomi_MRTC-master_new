package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.model.ClsLoanWaiverReportPacs_Branchwise;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MojiniPhodySketch extends AppCompatActivity {

    private MaterialBetterSpinner sp_app_type;
    private AutoCompleteTextView etAppID;
    private Button btnShowSketch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mojini_phody_sketch);

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

        sp_app_type = findViewById(R.id.sp_app_type);
        etAppID = findViewById(R.id.etAppID);
        btnShowSketch = findViewById(R.id.btnShowSketch);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{"11E/Phodi/Alienation", "Hadbasthu", "E-Swathu"});
        sp_app_type.setAdapter(defaultArrayAdapter);

        btnShowSketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etAppId = etAppID.getText().toString().trim();
                String sp_AppType = sp_app_type.getText().toString().trim();
                View focus = null;
                boolean status = false;
                if (TextUtils.isEmpty(etAppId)) {
                    focus = etAppID;
                    status = true;
                    etAppID.setError("Please Enter Application ID");
                } else if (TextUtils.isEmpty(sp_AppType)) {
                    focus = sp_app_type;
                    status = true;
                    sp_app_type.setError("Please Select Application Type");
                }
                if (status) {
                    focus.requestFocus();
                } else {
                    if (isNetworkAvailable()) {
                        if (sp_AppType.equals("11E/Phodi/Alienation")){
                            sp_AppType = "1";
                        }else if (sp_AppType.equals("Hadbasthu")){
                            sp_AppType = "2";
                        }else {
                            sp_AppType = "3";
                        }
                        Intent intent = new Intent(MojiniPhodySketch.this, Sketch.class);
                        intent.putExtra("etAppId", etAppId+"");
                        intent.putExtra("sp_AppType", sp_AppType+"");
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
