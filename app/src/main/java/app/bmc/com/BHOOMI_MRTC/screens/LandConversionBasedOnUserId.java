package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.BankPaymentDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.CommercialBankAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnAffidavitAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnUserIDAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.PacsLoanDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.PacsPaymentDetailsAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.model.BankLoanTableData;
import app.bmc.com.BHOOMI_MRTC.model.BankPaymentTableData;
import app.bmc.com.BHOOMI_MRTC.model.PacsLoanTableData;
import app.bmc.com.BHOOMI_MRTC.model.PacsPaymentTableData;

public class LandConversionBasedOnUserId extends AppCompatActivity {
    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> AfdvtBasedOnUserIdData;
    RecyclerView rvUserId;
    TextView tvUserId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion_based_on_user_id);

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

        rvUserId = findViewById(R.id.rvUserId);
        tvUserId = findViewById(R.id.tvUserId);

        String USER_ID = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("USER_ID");
        String USERID_ResponseData = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("USERID_ResponseData");
        Log.d("LandConversionUserId : ", USER_ID+" &&&&&"+USERID_ResponseData);

        if(USERID_ResponseData != null) {
            try {

                JSONArray jsonArray = new JSONArray(USERID_ResponseData);
                Log.d("jsonArray", String.valueOf(jsonArray));
                Gson gson = new Gson();
                AfdvtBasedOnUserIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {}.getType());
                Log.d("SIZESUS", AfdvtBasedOnUserIdData.size() + "");

                if (AfdvtBasedOnUserIdData.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                    builder.setTitle("STATUS")
                            .setMessage("No Data Found For this Record")
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    AfdvtBasedOnUserIdData.size();
                    tvUserId.setVisibility(View.VISIBLE);
                    rvUserId.setVisibility(View.VISIBLE);
                    Log.d("AfdvtBasedOnUserIdData",AfdvtBasedOnUserIdData.size()+"");
                    LandConversionBasedOnUserIDAdapter adapter = new LandConversionBasedOnUserIDAdapter(AfdvtBasedOnUserIdData,this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvUserId.setLayoutManager(mLayoutManager);
                    rvUserId.setItemAnimator(new DefaultItemAnimator());
                    rvUserId.setAdapter(adapter);
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
