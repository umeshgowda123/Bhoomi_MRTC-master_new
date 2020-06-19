package app.bmc.com.bhoomi.screens;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.api.PariharaIndividualReportInteface;
import app.bmc.com.bhoomi.model.PariharaIndividualDetailsResponse;
import app.bmc.com.bhoomi.retrofit.PariharaIndividualreportClient;
import app.bmc.com.bhoomi.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClwsStatus extends AppCompatActivity {

    private RadioGroup rgForSelection;
    private RadioButton rb_Aadhar;
    private RadioButton rb_RasanCard;

    private EditText etAaadhar;
    private String aadharNumber;
    private String rationcard;

    private Button btnFetchReports;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clws_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        etAaadhar = findViewById(R.id.etAaadhar);
        btnFetchReports = findViewById(R.id.btnFetchReports);
        rgForSelection = findViewById(R.id.rgForSelection);
        rb_Aadhar = findViewById(R.id.rb_Aadhar);
        rb_RasanCard = findViewById(R.id.rb_RasanCard);


        rb_Aadhar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etAaadhar.setVisibility(View.VISIBLE);
                    etAaadhar.setEnabled(true);
                    etAaadhar.setHint(R.string.clws_aadhar_edittext);
                    etAaadhar.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etAaadhar.setText("");
                    rb_RasanCard.setChecked(false);
                }
            }
        });

        rb_RasanCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etAaadhar.setVisibility(View.VISIBLE);
                    etAaadhar.setEnabled(true);
                    etAaadhar.setHint(R.string.clws_ration_edittext);
                    etAaadhar.setInputType(InputType.TYPE_CLASS_TEXT);
                    etAaadhar.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                    etAaadhar.setText("");
                    rb_Aadhar.setChecked(false);
                }
            }
        });

        btnFetchReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    //-------------------------SUSMITA-------------------
                    if (rb_Aadhar.isChecked() || rb_RasanCard.isChecked()) {
                    //--------------------------------------------------
                        if (rb_Aadhar.isChecked()) {
                            aadharNumber = etAaadhar.getText().toString().trim();
                            if (!aadharNumber.isEmpty() && aadharNumber.length() == 12) {

//                                MyAsyncTasks myAsyncTasks = new MyAsyncTasks(aadharNumber);
//                                myAsyncTasks.execute();

                                progressDialog = new ProgressDialog(ClwsStatus.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/SERVICE4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                Call<PariharaIndividualDetailsResponse> call = apiInterface.getCLWSStatusByAaadharNumber(Constants.REPORT_SERVICE_USER_NAME,
                                        Constants.REPORT_SERVICE_PASSWORD,aadharNumber);
                                call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                        if(response.isSuccessful())
                                        {
                                            PariharaIndividualDetailsResponse result = response.body();
                                            assert result != null;
                                            String res = result.getGetDownloadCLWSSTATUS_AadhaarResult();
                                            Log.d("CLWSSTATUS_Aadhaar", ""+res);

                                            XmlToJson xmlToJson = new XmlToJson.Builder(res.replace("\\r\\n", "").trim()).build();
                                            String formatted = xmlToJson.toFormattedString().replace("\n", "");
                                            Log.d("CLWSSTATUS_formatted", ""+formatted);

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(ClwsStatus.this, ClwsStatusDetails.class);
                                            intent.putExtra("Aadhaar_ResponseData", formatted);
                                            intent.putExtra("UID", aadharNumber);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                                        call.cancel();
                                        progressDialog.dismiss();
                                    }
                                });


                            } else {
                                //-------------------------SUSMITA-------------------
                                if (aadharNumber.isEmpty()) {
                                    Toast.makeText(ClwsStatus.this, "Please Enter Aadhar Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ClwsStatus.this, "Aadhar Number should be 12 digit ", Toast.LENGTH_SHORT).show();
                                }
                                //--------------------------------------------------
//                            Toast.makeText(ClwsStatus.this, "Aadhar Number should be 12 digit ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            rationcard = etAaadhar.getText().toString().trim();
                            if (!rationcard.isEmpty() && rationcard.length() > 5) {

//                                RationCardAsyncTasks mytasks = new RationCardAsyncTasks(rationcard);
//                                mytasks.execute();

                                progressDialog = new ProgressDialog(ClwsStatus.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/SERVICE4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                Call<PariharaIndividualDetailsResponse> call = apiInterface.getCLWSStatusByRationCard(Constants.REPORT_SERVICE_USER_NAME,
                                        Constants.REPORT_SERVICE_PASSWORD,rationcard);
                                call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                        if(response.isSuccessful())
                                        {
                                            PariharaIndividualDetailsResponse result = response.body();
                                            assert result != null;
                                            String res = result.getGetDownloadCLWSSTATUS_RationCardResult();
                                            Log.d("CLWSSTATUS_RationCard", ""+res);

                                            XmlToJson xmlToJson = new XmlToJson.Builder(res.replace("\\r\\n", "").trim()).build();
                                            String formatted = xmlToJson.toFormattedString().replace("\n", "");
                                            Log.d("CLWSSTATUS_formatted", ""+formatted);

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(ClwsStatus.this, ClwsStatusRationCardDetails.class);
                                            intent.putExtra("Ration_ResponseData", formatted);
                                            intent.putExtra("RATION_CARD",rationcard);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                                        call.cancel();
                                        progressDialog.dismiss();
                                    }
                                });

                            } else {
                                //-------------------------SUSMITA-------------------
                                if (rationcard.isEmpty()) {
                                    Toast.makeText(ClwsStatus.this, "Please Enter RationCard Num.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ClwsStatus.this, "Invalid RationCard Num.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    //-------------------------SUSMITA-------------------
                    }else {
                        Toast.makeText(ClwsStatus.this, "Please Select Any Radio Button", Toast.LENGTH_SHORT).show();
                    }
                    //--------------------------------------------------
                }else
                {
                    selfDestruct();
                }
            }
        });
    }

    // Individual Lonne Report First Network Call
    private class MyAsyncTasks extends AsyncTask<String, String, String> {

        private String aadhaarnumber;

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 60000;
        public static final int CONNECTION_TIMEOUT = 60000;


        public MyAsyncTasks(String aadharnumber) {
            this.aadhaarnumber = aadharNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(ClwsStatus.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            // implement API in background and store the response in current variable
            String inputLine;
            String response;
            String format = aadhaarnumber;
            //   String apiUrl = "http://192.168.0.139/LoaneeReportService/Service1.svc/GetAllDetailsByAadhaarNumber/" + format;
            //   String outerapiUrl = "http://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByAadhaarNumber/" + format;

            String outerapiUrl = Constants.outerapiUrl + format;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(outerapiUrl);
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                response = stringBuilder.toString();
            }catch(IOException e){

                e.printStackTrace();
                response = null;

            }
            return response;
        }


        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            progressDialog.dismiss();
            if (data != null || !data.isEmpty()) {
//            if (data != "") {  // SUSMITA
                Log.d("Datatopass",data);
                Intent intent = new Intent(ClwsStatus.this, ClwsStatusDetails.class);
                intent.putExtra("Aadhar_ResponseData", data);
                intent.putExtra("UID", aadharNumber);
                startActivity(intent);

            }else
            {
                Toast.makeText(ClwsStatus.this, "Network Problem.AN.Please try later", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private class RationCardAsyncTasks extends AsyncTask<String, String, String> {

        private String rationcardnumber;

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


        public RationCardAsyncTasks(String rationcardnumber) {
            this.rationcardnumber = rationcardnumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(ClwsStatus.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            // implement API in background and store the response in current variable
            String inputLine;
            String response;
            String format = rationcardnumber;

            //  String outerapiUrl = "http://218.248.32.25/LoaneeReportService/Service1.svc/GetAllDetailsByRationCardNumber/" + format;
            // String outerapiUrl = "http://parihara.karnataka.gov.in/clwstest/LoaneeReportService/Service1.svc/GetAllDetailsByRationCardNumber/" + format;

            String apiUrlByRcNo = Constants.apiUrlByRcNo + format;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(apiUrlByRcNo);
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                response = stringBuilder.toString();
            }catch(IOException e){
                e.printStackTrace();
                response = null;
                Log.d("response : ",response+" "+e.toString());
            }
            return response;
        }


        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
//            Log.d("Datatopass",data);
            progressDialog.dismiss();
            if (data != null || !data.isEmpty()) {
//            if (data != "") { // SUSMITA
                Log.d("DATA",data);
                Intent intent = new Intent(ClwsStatus.this, ClwsStatusRationCardDetails.class);
                intent.putExtra("Ration_ResponseData", data);
                intent.putExtra("RATION_CARD",rationcard);
                startActivity(intent);
            }
            //SUSMITA
            else
            {
                Toast.makeText(ClwsStatus.this, "Network Problem.RC.Please try later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(ClwsStatus.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            } });
        alertDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
