package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ViewRTCByOwnerNameNewAdapter;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;

import static java.util.Comparator.comparing;

public class ShowRtcDetailsBYOwnerName extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {

    private ListView lv_OwnerDetails;
    SearchView searchView;
    ArrayList<RTCByOwnerNameResponse> rtcByOwnerNameResponseList;
    private ViewRTCByOwnerNameNewAdapter adapter;

    private RtcViewInfoBackGroundTaskFragment mTaskFragment;
    private ProgressBar progressBar;

    String distId, talkId, hblId, village_id;
    ArrayList<String> distId_array = new ArrayList<>(), talkId_array = new ArrayList<>(), hblId_array = new ArrayList<>(), village_id_array = new ArrayList<>();

    Intent intent;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rtc_details_byowner_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        lv_OwnerDetails = findViewById(R.id.lv_OwnerDetails);
//        etSearch = findViewById(R.id.etSearch);
        searchView = findViewById(R.id.s);
        searchView.setIconified(false);
        searchView.onActionViewExpanded();

        progressBar = findViewById(R.id.progress_circular);

        intent = getIntent();
        rtcByOwnerNameResponseList = new ArrayList<>();

        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }
        if (mTaskFragment.isTaskExecuting) {
            progressBar = findViewById(R.id.progress_circular);
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

        if (intent != null) {

            distId = intent.getStringExtra("distId");
            talkId = intent.getStringExtra("talkId");
            hblId = intent.getStringExtra("hblId");
            village_id = intent.getStringExtra("village_id");

            input = "{" +
                    "\"pDistrictCode\": \""+distId+"\"," +
                    "\"pTalukCode\": \""+talkId+"\"," +
                    "\"pHobliCode\":\""+hblId+"\"," +
                    "\"pVillageCode\": \""+village_id+"\"" +
                    "}";


            if (isNetworkAvailable()){
                try {
                    mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));

                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPreExecute1() {

    }

    @Override
    public void onPostResponseSuccess1(String data) {

    }

    @Override
    public void onPostResponseError_FORHISSA(String data, int count) {

    }

    @Override
    public void onPreExecute2() {

    }

    @Override
    public void onPostResponseSuccess2(String data) {

    }

    @Override
    public void onPostResponseError_Task2(String data, int count) {

    }

    @Override
    public void onPreExecute3() {

    }

    @Override
    public void onPostResponseSuccess3(String data) {

    }

    @Override
    public void onPostResponseError_Task3(String data) {

    }

    @Override
    public void onPreExecute4() {

    }

    @Override
    public void onPostResponseSuccess4(String data) {

    }

    @Override
    public void onPostResponseError_Task4(String data) {

    }

    @Override
    public void onPostResponseSuccessCultivator(String gettcDataResult) {

    }

    @Override
    public void onPostResponseErrorCultivator(String errorResponse, int count) {

    }

    @Override
    public void onPreExecute5() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPostResponseSuccess_GetDetails_VilWise(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Log.d("data", ""+data);
        if (data == null || data.equals("[]") || data.equals("") || data.contains("500 | JSON ERROR")){
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ShowRtcDetailsBYOwnerName.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.no_data_found_for_this_vill_pls_cont_support_team_for))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                        dialog.cancel();
                        onBackPressed();
                    });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else if (data.contains("is busy")){
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ShowRtcDetailsBYOwnerName.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.server_is_busy_please_try_again_later))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(data);

                Type listType = new TypeToken<ArrayList<RTCByOwnerNameResponse>>() {
                }.getType();
                rtcByOwnerNameResponseList = new Gson().fromJson(jsonArray.toString(), listType);
                distId_array.clear();
                talkId_array.clear();
                hblId_array.clear();
                village_id_array.clear();

                for (int i=1;i<=rtcByOwnerNameResponseList.size();i++) {
                    //Log.d("rtcByOwnerNameResList", ""+rtcByOwnerNameResponseList.get(i));
                    distId_array.add(distId);
                    talkId_array.add(talkId);
                    hblId_array.add(hblId);
                    village_id_array.add(village_id);
                }

                Collections.sort(rtcByOwnerNameResponseList, comparing(RTCByOwnerNameResponse::getSurvey_no));

                adapter= new ViewRTCByOwnerNameNewAdapter(rtcByOwnerNameResponseList,getApplicationContext(), distId_array, talkId_array, hblId_array, village_id_array);

                lv_OwnerDetails.setAdapter(adapter);

            } catch (Throwable e) {
                e.printStackTrace();
                String throwab = e.toString();
                if (throwab.contains("Failure from system")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ShowRtcDetailsBYOwnerName.this, R.style.MyDialogTheme);
                    builder.setTitle("Alert")
                            .setMessage("Transaction contains Too Large Data.")
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> onBackPressed());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    Log.d("Exception_Success",e.getLocalizedMessage()+"");
                    Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onPostResponseError_GetDetails_VilWise(String data) {

        Log.d("DATA ERR", data+"");

        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowRtcDetailsBYOwnerName.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.status))
                .setMessage(data)
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

    @Override
    public void onPreExecuteToken() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ShowRtcDetailsBYOwnerName.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.something_went_wrong_pls_try_again))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
            mTaskFragment.startBackgroundTask_GetDetails_VilWise(jsonObject, getString(R.string.rest_service_url),TokenType, AccessToken);
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        Toast.makeText(this, ""+errorResponse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreExecuteGetBhoomiLandId() {

    }

    @Override
    public void onPostResponseSuccessGetBhoomiLandID(String data) {

    }

    @Override
    public void onPostResponseError_BhoomiLandID(String data) {

    }

    @Override
    public void onPreExecute_AppLgs() {
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {
        Log.d("AppLgsRes", ""+data);
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {
        Log.d("AppLgsRes", ""+data);
    }

    @Override
    public void onPreExecute_GetSurnocNo() {

    }

    @Override
    public void onPostResponseSuccess_GetSurnocNo(String data) {

    }

    @Override
    public void onPostResponseError_GetSurnocNo(String data) {

    }

    @Override
    public void onPreExecute_GetHissaNo() {

    }

    @Override
    public void onPostResponseSuccess_GetHissaNo(String data) {

    }

    @Override
    public void onPostResponseError_GetHissaNo(String data) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTaskFragment.terminateExecutionOfGetDetails_VillageWise_JSONResponse();
    }
}