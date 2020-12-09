package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ViewRTCByOwnerNameNewAdapter;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

import static java.util.Comparator.comparing;

public class ShowRtcDetailsBYOwnerName extends AppCompatActivity {

    private ListView lv_OwnerDetails;
//    EditText etSearch;

    SearchView searchView;
    ArrayList<RTCByOwnerNameResponse> rtcByOwnerNameResponseList;
//    private ShowRtcOwnerReportAdapter adapter;
    private ViewRTCByOwnerNameNewAdapter adapter;
    ProgressDialog progressDialog;
    String SOAP_ACTION2 = "http://tempuri.org/GetDetails_VillageWise_JSON";
    public final String OPERATION_NAME2 = "GetDetails_VillageWise_JSON";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ADDRESS2 = "https://parihara.karnataka.gov.in/service16/WS_BHOOMI.asmx";

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    String distId, talkId, hblId, village_id;
    ArrayList<String> distId_array = new ArrayList<>(), talkId_array = new ArrayList<>(), hblId_array = new ArrayList<>(), village_id_array = new ArrayList<>();

    Intent intent;

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


        intent = getIntent();
        rtcByOwnerNameResponseList = new ArrayList<>();

        if (intent != null) {

            distId = intent.getStringExtra("distId");
            talkId = intent.getStringExtra("talkId");
            hblId = intent.getStringExtra("hblId");
            village_id = intent.getStringExtra("village_id");


            if (isNetworkAvailable()){
                new GetAllOwnerDetailsBasedOnDTHVId(distId,talkId,hblId,village_id).execute();
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
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("CharSequence", ""+s);
//                adapter.getFilter().filter(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class GetAllOwnerDetailsBasedOnDTHVId extends AsyncTask<String, Integer, String> {
        String TAG = getClass().getSimpleName();

        String distId, talkId, hblId, vlgId;


        public GetAllOwnerDetailsBasedOnDTHVId(String dId, String tId, String hId, String vId) {
            this.distId = dId;
            this.talkId = tId;
            this.hblId = hId;
            this.vlgId = vId;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ShowRtcDetailsBYOwnerName.this);
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2, OPERATION_NAME2);
            request.addProperty("pstrUserName", Constants.USER_NAME);
            request.addProperty("pStrPassword", Constants.USER_PWD);
            request.addProperty("pDistrictCode", distId);
            request.addProperty("pTalukCode", talkId);
            request.addProperty("pHobliCode", hblId);
            request.addProperty("pVillageCode", vlgId);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS2);

            try {

                androidHttpTransport.call(SOAP_ACTION2, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                resultFromServer = String.valueOf(resultString);

                JSONArray jsonArray = new JSONArray(resultFromServer);

//              Type listType = new TypeToken<ArrayList<RTCByOwnerNameResponse>>() {
//              }.getType();
//              rtcByOwnerNameResponsesList = new Gson().fromJson(jsonArray.toString(), listType);

            } catch (IOException | XmlPullParserException | JSONException | ClassCastException e) {
                e.printStackTrace();
                //Toast.makeText(ShowRtcDetailsBYOwnerName.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }


            return resultFromServer;
        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                progressDialog.dismiss();
                Toast.makeText(ShowRtcDetailsBYOwnerName.this, "Error", Toast.LENGTH_SHORT).show();
            } else if (result.contains("500 | JSON ERROR")){
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
            } else {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(resultFromServer);

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
                    progressDialog.dismiss();
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
                        Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}