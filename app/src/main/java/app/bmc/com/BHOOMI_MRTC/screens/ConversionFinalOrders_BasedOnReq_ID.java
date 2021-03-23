package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
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
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionFinalOrdersAdapter;
import app.bmc.com.BHOOMI_MRTC.model.GetLandConversionFinalOrders_Table;

public class ConversionFinalOrders_BasedOnReq_ID extends AppCompatActivity {

    RecyclerView rvReqID;
    List<GetLandConversionFinalOrders_Table> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversionfinalorders_basedonreq_id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvReqID = findViewById(R.id.rvReqID);

        String LandConversionFinalOrders = getIntent().getStringExtra("LandConversionFinalOrders");

        if (LandConversionFinalOrders!=null){

            try {
                JSONArray jsonArray = new JSONArray(LandConversionFinalOrders);

                Gson gson = new Gson();
                list = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<GetLandConversionFinalOrders_Table>>() {
                }.getType());

                if (list.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConversionFinalOrders_BasedOnReq_ID.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.status))
                            .setMessage(getString(R.string.no_data_found_for_this_record))
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    list.size();
                    LandConversionFinalOrdersAdapter adapter = new LandConversionFinalOrdersAdapter(list, ConversionFinalOrders_BasedOnReq_ID.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvReqID.setLayoutManager(mLayoutManager);
                    rvReqID.setItemAnimator(new DefaultItemAnimator());
                    rvReqID.setAdapter(adapter);
                }
            } catch (JSONException e){
                e.printStackTrace();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ConversionFinalOrders_BasedOnReq_ID.this, R.style.MyDialogTheme);
                builder.setTitle(getString(R.string.status))
                        .setMessage(getString(R.string.something_went_wrong_pls_try_again))
                        .setIcon(R.drawable.ic_notifications_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                            dialog.cancel();
                            finish();
                        });
                final AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
