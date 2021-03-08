package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;

import app.bmc.com.BHOOMI_MRTC.adapters.LandConversionBasedOnUserIDAdapter;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Interface;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;

import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversionBasedOnUserId extends AppCompatActivity {
    List<Afdvt_ReqSts_BasedOnAfdvtIdTable> AfdvtBasedOnUserIdData;
    RecyclerView rvUserId;
    TextView tvUserId;
    private ProgressDialog progressDialog;
    DataBaseHelper dataBaseHelper;
    private List<LandConversion_Interface> LandConversion_Data;
    String User_res;

    Call<BHOOMI_API_Response> call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_conversion_based_on_user_id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rvUserId = findViewById(R.id.rvUserId);
        tvUserId = findViewById(R.id.tvUserId);

        String userId = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("USER_ID");
        String tokenType = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("tokenType");
        String accessToken = (String) Objects.requireNonNull(getIntent().getExtras()).getSerializable("accessToken");
        Log.d("token", tokenType+"   "+accessToken);

        if (userId!= null) {

            dataBaseHelper =
                    Room.databaseBuilder(getApplicationContext(),
                            DataBaseHelper.class, getString(R.string.db_name)).build();
            Observable<List<? extends LandConversion_Interface>> LandConversionObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getUSER_RES(userId));
            LandConversionObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends LandConversion_Interface>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<? extends LandConversion_Interface> landConversion_interfaces_list) {

                            LandConversion_Data = (List<LandConversion_Interface>) landConversion_interfaces_list;
                            if (landConversion_interfaces_list.size()!=0) {
                                for (int i = 0; i <= landConversion_interfaces_list.size()-1; i++) {

                                    String userid_res = LandConversion_Data.get(0).getUSER_RES();

                                    if(userid_res == null || userid_res.equals("") || userid_res.contains("INVALID")) {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                        builder.setTitle(getString(R.string.status))
                                                .setMessage(getString(R.string.invalid_user_id))
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                                                    dialog.cancel();
                                                    finish();
                                                });
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    }else if(userid_res.contains("Details not found")) {
                                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                        builder.setTitle(getString(R.string.status))
                                                .setMessage(getString(R.string.details_not_found))
                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                .setCancelable(false)
                                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                        final android.app.AlertDialog alert = builder.create();
                                        alert.show();
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                    }
                                    else {
                                        try {
                                            JSONArray jsonArray = new JSONArray(userid_res);
                                            Gson gson = new Gson();
                                            AfdvtBasedOnUserIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {
                                            }.getType());
                                            if (AfdvtBasedOnUserIdData.size() == 0) {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.no_data_found_for_this_record))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            } else {
                                                LandConversionBasedOnUserIDAdapter adapter = new LandConversionBasedOnUserIDAdapter(AfdvtBasedOnUserIdData, LandConversionBasedOnUserId.this);
                                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                                rvUserId.setLayoutManager(mLayoutManager);
                                                rvUserId.setItemAnimator(new DefaultItemAnimator());
                                                rvUserId.setAdapter(adapter);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                            else {
                                progressDialog = new ProgressDialog(LandConversionBasedOnUserId.this);
                                progressDialog.setMessage(getString(R.string.please_wait));
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                PariharaIndividualReportInteface apiInterface = AuthorizationClient.getClient(getString(R.string.rest_service_url), tokenType, accessToken).create(PariharaIndividualReportInteface.class);
                                call = apiInterface.getLandConversionBasedOnUserID(userId);
                                call.enqueue(new Callback<BHOOMI_API_Response>() {
                                    @Override
                                    public void onResponse(@NotNull Call<BHOOMI_API_Response>  call, @NotNull Response<BHOOMI_API_Response> response)
                                    {

                                        if(response.isSuccessful())
                                        {
                                            BHOOMI_API_Response result = response.body();
                                            assert result != null;
                                            User_res = result.getBhoomI_API_Response();

                                            progressDialog.dismiss();
                                            if(User_res == null || User_res.equals("") || User_res.contains("INVALID")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.invalid_user_id))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            }else if(User_res.contains("Details not found")) {
                                                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                                builder.setTitle(getString(R.string.status))
                                                        .setMessage(getString(R.string.details_not_found))
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                final android.app.AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            }
                                            else {
                                                try {
                                                    JSONArray jsonArray = new JSONArray(User_res);
                                                    Gson gson = new Gson();
                                                    AfdvtBasedOnUserIdData = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {
                                                    }.getType());


                                                    if (AfdvtBasedOnUserIdData.size() == 0) {
                                                        final AlertDialog.Builder builder = new AlertDialog.Builder(LandConversionBasedOnUserId.this, R.style.MyDialogTheme);
                                                        builder.setTitle(getString(R.string.status))
                                                                .setMessage(getString(R.string.no_data_found_for_this_record))
                                                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                                                .setCancelable(false)
                                                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                        final AlertDialog alert = builder.create();
                                                        alert.show();
                                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                    } else {
                                                        LandConversionBasedOnUserIDAdapter adapter = new LandConversionBasedOnUserIDAdapter(AfdvtBasedOnUserIdData,LandConversionBasedOnUserId.this);
                                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                                        rvUserId.setLayoutManager(mLayoutManager);
                                                        rvUserId.setItemAnimator(new DefaultItemAnimator());
                                                        rvUserId.setAdapter(adapter);
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call<BHOOMI_API_Response> call, @NotNull Throwable t) {
                                        call.cancel();
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (call != null && call.isExecuted()){
            call.cancel();
        }
    }
}
