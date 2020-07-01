package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnUserIDAdapter;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversionBasedOnUserId extends AppCompatActivity {
    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> AfdvtBasedOnUserIdData;
    RecyclerView rvUserId;
    TextView tvUserId;
    private ProgressDialog progressDialog;
    String res;
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

        String userId = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("USER_ID");
        Log.d("LandConversionUserId : ", userId);

        if (userId!= null || userId != "") {
            progressDialog = new ProgressDialog(LandConversionBasedOnUserId.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();


            PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
            Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionBasedOnUserID(Constants.REPORT_SERVICE_USER_NAME,
                    Constants.REPORT_SERVICE_PASSWORD,userId);
            call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                @Override
                public void onResponse(@NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Response<PariharaIndividualDetailsResponse> response) {

                    if(response.isSuccessful())
                    {
                        PariharaIndividualDetailsResponse result = response.body();
                        assert result != null;
                        res = result.getGet_Afdvt_ReqSts_BasedOnUserIdResult();
                        Log.d("USERID_ResponseData", ""+res);

                        progressDialog.dismiss();
                        if(res == null || res.contains("INVALID")) {
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                            builder.setTitle("STATUS")
                                    .setMessage("Invalid User ID")
                                    .setIcon(R.drawable.ic_notifications_black_24dp)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                            final android.app.AlertDialog alert = builder.create();
                            alert.show();
                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        }else {
                            try {
                                JSONArray jsonArray = new JSONArray(res);
                                Log.d("jsonArray", String.valueOf(jsonArray));
                                Gson gson = new Gson();
                                AfdvtBasedOnUserIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {
                                }.getType());
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
                                    Log.d("AfdvtBasedOnUserIdData", AfdvtBasedOnUserIdData.size() + "");
                                    LandConversionBasedOnUserIDAdapter adapter = new LandConversionBasedOnUserIDAdapter(AfdvtBasedOnUserIdData,LandConversionBasedOnUserId.this);
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
                }
                @Override
                public void onFailure( @NotNull Call<PariharaIndividualDetailsResponse> call, @NotNull Throwable t) {
                    call.cancel();
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
