package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
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
import app.bmc.com.BHOOMI_MRTC.adapters.SkatchAdapter;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.model.PhodySketch;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sketch extends AppCompatActivity {
    String etAppId, sp_AppType;
    private ProgressDialog progressDialog;
    List<PhodySketch> PhodySketch;
    RecyclerView rvSketch;
    String res;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch);

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

        etAppId = getIntent().getStringExtra("etAppId");
        sp_AppType = getIntent().getStringExtra("sp_AppType");

        rvSketch = findViewById(R.id.rvSketch);

        progressDialog = new ProgressDialog(Sketch.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/SERVICE4/BHOOMI/").create(PariharaIndividualReportInteface.class);
        Call<PariharaIndividualDetailsResponse> call = apiInterface.getSketchDetailsDetailsBasedOnAppNo(Constants.REPORT_SERVICE_USER_NAME,
                Constants.REPORT_SERVICE_PASSWORD, etAppId, sp_AppType);
        call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Response<PariharaIndividualDetailsResponse> response) {

                if (response.isSuccessful()) {
                    PariharaIndividualDetailsResponse result = response.body();
                    assert result != null;
                    res = result.getGetSketchDetailsDetailsBasedOnAppNoResult();

                    progressDialog.dismiss();
                    if (res==null || res.contains("Details not found")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Sketch.this, R.style.MyDialogTheme);
                        builder.setTitle("STATUS")
                                .setMessage("No Data Found For this Record")
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else  {
                        try {
                            JSONArray jsonArray = new JSONArray(res);

                            Gson gson = new Gson();
                            PhodySketch = gson.fromJson(String.valueOf(jsonArray),new TypeToken<List<PhodySketch>>(){}.getType());


                            if (PhodySketch.size() == 0) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(Sketch.this, R.style.MyDialogTheme);
                                builder.setTitle("STATUS")
                                        .setMessage("No Data Found For this Record")
                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                        .setCancelable(false)
                                        .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                final AlertDialog alert = builder.create();
                                alert.show();
                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                            } else {

                                SkatchAdapter adapter = new SkatchAdapter(PhodySketch);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                rvSketch.setLayoutManager(mLayoutManager);
                                rvSketch.setItemAnimator(new DefaultItemAnimator());
                                rvSketch.setAdapter(adapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
