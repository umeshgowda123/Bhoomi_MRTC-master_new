package app.bmc.com.BHOOMI_MRTC.screens;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.RestrictionOnLandReportAdapter;
import app.bmc.com.BHOOMI_MRTC.model.Afdvt_ReqSts_BasedOnAfdvtIdTable;
import app.bmc.com.BHOOMI_MRTC.model.RestrictionOnLandReportTable;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restriction_on_land_report);

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

    private class GetRestrictionOnLandReport extends AsyncTask<String, Integer, String> {
        String TAG = getClass().getSimpleName();

        String distId, talkId, hblId, vlgId, surveyNO, surcoNo, hissaNo;

        private GetRestrictionOnLandReport(String dId, String tId, String hId, String vId, String survey_No,
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
//            request.addProperty("DeptID", "WS_BMC_ID");
//            request.addProperty("DeptPassPhase", " fa16722b-36e0-40d1-805a-9026d600ab1d");
//            request.addProperty("Bhm_dist_code", distId);
//            request.addProperty("Bhm_taluk_code", talkId);
//            request.addProperty("Bhm_hobli_code", hblId);
//            request.addProperty("Bhm_village_code", vlgId);
//            request.addProperty("Bhm_survey_no", surveyNO);
//            request.addProperty("Bhm_surnoc", surcoNo);
//            request.addProperty("Bhm_Hissa", hissaNo);

            request.addProperty("DeptID", "WS_BMC_ID");
            request.addProperty("DeptPassPhase", " fa16722b-36e0-40d1-805a-9026d600ab1d");
            request.addProperty("Bhm_dist_code", "20");
            request.addProperty("Bhm_taluk_code", "2");
            request.addProperty("Bhm_hobli_code", "16");
            request.addProperty("Bhm_village_code", "36");
            request.addProperty("Bhm_survey_no", "1");
            request.addProperty("Bhm_surnoc", "*");
            request.addProperty("Bhm_Hissa", "1");

            Log.d("request", ": " + request);

            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS2);
            Log.d("URL", "URL: " + SOAP_ADDRESS2);

            try {

                androidHttpTransport.call(SOAP_ACTION2,envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                resultFromServer = String.valueOf(resultString);

                Log.i("Resultttt", resultFromServer);
                XmlToJson xmlToJson = new XmlToJson.Builder(resultFromServer.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
                // convert to a JSONObject
                JSONObject output = xmlToJson.toJson();
                assert output != null;
                String s = (String) output.remove("{\"OwnerDetails\":{\"VILLAGE\":{\"NAME\":\"ದೊಡ್ಡನಾಗಮಂಗಲ\",\"CODE\":\"36\"},\"DISTRICT\":{\"NAME\":\"ಬೆ೦ಗಳೂರು ನಗರ\",\"CODE\":\"20\"},\"TALUKA\":{\"NAME\":\"ಬೆ೦ಗಳೂರು - ದಕ್ಷಿಣ\",\"CODE\":\"2\"},\"HOBLI\":{\"NAME\":\"ಬೇಗೂರು - 2\",\"CODE\":\"16\"},\"SURVEY_DETAILS\":{\"SurveyNumber\":\"1 \\/ * \\/ 2\"},\"OWNERS\":{");
                assert s != null;
                String str = s.replace("}}}","");
                Log.d("jsonObj", "str: " + str);

                JSONArray jsonArray = output.getJSONArray("JOINT_OWNERS");

                Log.d("jsonArray_", "str: " + jsonArray);

            } catch (IOException | XmlPullParserException | JSONException e) {
                e.printStackTrace();
            }
            return resultFromServer;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG + " onPostExecute", "" + result);

            if (result != null) {

                try {
                    progressDialog.dismiss();
                    JSONArray jsonArray = new JSONArray(result);
                    Log.d("jsonArray", String.valueOf(jsonArray));

                    Gson gson = new Gson();
                    restrictionOnLandReportTableList = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Afdvt_ReqSts_BasedOnAfdvtIdTable>>() {}.getType());
                    Log.d("SIZESUS", restrictionOnLandReportTableList.size() + "");

                    if (restrictionOnLandReportTableList.size() == 0) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(RestrictionOnLandReport.this, R.style.MyDialogTheme);
                        builder.setTitle("STATUS")
                                .setMessage("No Data Found For this Record")
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {
                        restrictionOnLandReportTableList.size();
                        Log.d("List",restrictionOnLandReportTableList.size()+"");
                        RestrictionOnLandReportAdapter adapter = new RestrictionOnLandReportAdapter(restrictionOnLandReportTableList,RestrictionOnLandReport.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRestrictionReport.setLayoutManager(mLayoutManager);
                        rvRestrictionReport.setItemAnimator(new DefaultItemAnimator());
                        rvRestrictionReport.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ExceptionSUS", e + "");
                }
            }else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Result Null", Toast.LENGTH_SHORT).show();
            }
        }
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
