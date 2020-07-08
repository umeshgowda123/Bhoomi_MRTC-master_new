package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowRtcOwnerReportAdapter;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

public class ShowRtcDetailsBYOwnerName extends AppCompatActivity {

    private LinearLayout lv_show_details;
    private ListView lv_OwnerDetails;

    ArrayList<RTCByOwnerNameResponse> rtcByOwnerNameResponseList;
    ListView listView;
    private static ShowRtcOwnerReportAdapter adapter;

    ProgressDialog progressDialog;
    String SOAP_ACTION2 = "http://tempuri.org/GetDetails_VillageWise_JSON";
    public final String OPERATION_NAME2 = "GetDetails_VillageWise_JSON";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ADDRESS2 = "https://parihara.karnataka.gov.in/service16/WS_BHOOMI.asmx";

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    private  String response_result, distId, talkId, hblId, village_id;
    ArrayList<String> distId_array = new ArrayList<>(), talkId_array = new ArrayList<>(), hblId_array = new ArrayList<>(), village_id_array = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rtc_details_byowner_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        lv_OwnerDetails = findViewById(R.id.lv_OwnerDetails);

        intent = getIntent();
        rtcByOwnerNameResponseList = new ArrayList<>();

        if (intent != null) {

            distId = intent.getStringExtra("distId");
            talkId = intent.getStringExtra("talkId");
            hblId = intent.getStringExtra("hblId");
            village_id = intent.getStringExtra("village_id");

            Log.d("distId", distId);
            Log.d("talkId", talkId);
            Log.d("hblId", hblId);
            Log.d("village_id", village_id);


            if (isNetworkAvailable()){
                new GetAllOwnerDetailsBasedOnDTHVId(distId,talkId,hblId,village_id).execute();
            }else {
                Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
            }
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
            progressDialog.setMessage("Please Wait");
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

            Log.d("request", ": " + request);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS2);
            Log.d("URL", "URL: " + SOAP_ADDRESS2);

            try {

                androidHttpTransport.call(SOAP_ACTION2, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                resultFromServer = String.valueOf(resultString);

                Log.i("Result", resultFromServer);

                JSONArray jsonArray = new JSONArray(resultFromServer);
                Log.d("jsonArray_", "str: " + jsonArray);

//              Type listType = new TypeToken<ArrayList<RTCByOwnerNameResponse>>() {
//              }.getType();
//              rtcByOwnerNameResponsesList = new Gson().fromJson(jsonArray.toString(), listType);

            } catch (IOException | XmlPullParserException | JSONException e) {
                e.printStackTrace();
            }


            return resultFromServer;
        }

        protected void onProgressUpdate(Integer... a) {
            super.onProgressUpdate(a);
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG + " onPostExecute", "" + result);

            if (result == null) {
                progressDialog.dismiss();
                Toast.makeText(ShowRtcDetailsBYOwnerName.this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(resultFromServer);
                    Log.d("jsonArray_", "str: " + jsonArray);

                    Type listType = new TypeToken<ArrayList<RTCByOwnerNameResponse>>() {
                    }.getType();
                    rtcByOwnerNameResponseList = new Gson().fromJson(jsonArray.toString(), listType);
                    Log.d("rtcByOwnerName", "" + rtcByOwnerNameResponseList);

                    distId_array.clear();
                    talkId_array.clear();
                    hblId_array.clear();
                    village_id_array.clear();

                    for (int i=1;i<=rtcByOwnerNameResponseList.size();i++) {
                        distId_array.add(distId);
                        talkId_array.add(talkId);
                        hblId_array.add(hblId);
                        village_id_array.add(village_id);
                    }

                    Log.d("rtcByOwner_array", ""+rtcByOwnerNameResponseList.size());
                    Log.d("distId_array", ""+distId_array.size());
                    Log.d("talkId_array", ""+talkId_array.size());
                    Log.d("hblId_array", ""+hblId_array.size());
                    Log.d("village_id_array", ""+village_id_array.size());

                    adapter= new ShowRtcOwnerReportAdapter(rtcByOwnerNameResponseList,getApplicationContext(), distId_array, talkId_array, hblId_array, village_id_array);

                    lv_OwnerDetails.setAdapter(adapter);

                } catch (Throwable e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    String throwab = e.toString();
                    Log.d("Throwable_e", "" + throwab);
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
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
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
}