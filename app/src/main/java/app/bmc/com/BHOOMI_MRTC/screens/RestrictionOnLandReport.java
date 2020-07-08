package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.RestrictionOnLandReportAdapter;
import app.bmc.com.BHOOMI_MRTC.model.RTCByOwnerNameResponse;
import app.bmc.com.BHOOMI_MRTC.model.RestrictionOnLandReportTable;

public class RestrictionOnLandReport extends AppCompatActivity {
    RecyclerView rvRestrictionReport;
    List<RestrictionOnLandReportTable> restrictionOnLandReportTableList;

    String district_id, taluk_id, hobli_id, village_id, surveyNo, suroc, hissa;

    ProgressDialog progressDialog;

    public final String OPERATION_NAME2 = "get_rtc_OwnerDetails";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ACTION2 = "http://tempuri.org/get_rtc_OwnerDetails";
    String SOAP_ADDRESS2 = "https://landrecords.karnataka.gov.in/service46/WS_RTC_Details.asmx";
    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    private RestrictionOnLandReportAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restriction_on_land_report);

        rvRestrictionReport = findViewById(R.id.rvRestrictionReport);

        Intent intent = getIntent();
        if (intent != null) {
            district_id = intent.getStringExtra("d_id");
            taluk_id = intent.getStringExtra("t_id");
            hobli_id = intent.getStringExtra("h_id");
            village_id = intent.getStringExtra("v_id");
            surveyNo = intent.getStringExtra("s_No");
            suroc = intent.getStringExtra("s_c");
            hissa = intent.getStringExtra("hi_no");
            Log.d("INTENT : ", district_id+" "+taluk_id+" "+hobli_id+" "+village_id+" "+surveyNo
                    +" "+suroc+" "+hissa);
        }
        if (isNetworkAvailable()){
            new GetRestrictionOnLandReport(district_id,taluk_id,hobli_id,village_id,surveyNo,suroc,hissa).execute();
        }else {
            Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
        }
    }

    class GetRestrictionOnLandReport extends AsyncTask<String, Integer, String> {
        String TAG = getClass().getSimpleName();

        String distId, talkId, hblId, vlgId, surveyNO, surcoNo, hissaNo;


        public GetRestrictionOnLandReport(String dId, String tId, String hId, String vId, String survey_No,
                                          String surco_No, String hissa_No) {
            this.distId = dId;
            this.talkId = tId;
            this.hblId = hId;
            this.vlgId = vId;
            this.surveyNO = survey_No;
            this.surcoNo = surco_No;
            this.hissaNo = hissa_No;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RestrictionOnLandReport.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2, OPERATION_NAME2);
            request.addProperty("DeptID", "WS_BMC_ID");
            request.addProperty("DeptPassPhase", " fa16722b-36e0-40d1-805a-9026d600ab1d");
            request.addProperty("Bhm_dist_code", "20");
            request.addProperty("Bhm_taluk_code", "2");
            request.addProperty("Bhm_hobli_code", "16");
            request.addProperty("Bhm_village_code", "36");
            request.addProperty("Bhm_survey_no", "1");
            request.addProperty("Bhm_surnoc", "1");
            request.addProperty("Bhm_Hissa", "2");

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

            } catch (IOException | XmlPullParserException | JSONException e) {
                e.printStackTrace();
            }

            return resultFromServer;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG + " onPostExecute", "" + result);

            if (result == null) {
                progressDialog.dismiss();
                Toast.makeText(RestrictionOnLandReport.this, "Error here", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(resultFromServer);
                    Log.d("jsonArray_", "str: " + jsonArray);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
