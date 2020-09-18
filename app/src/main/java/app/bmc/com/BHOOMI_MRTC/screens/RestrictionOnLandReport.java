package app.bmc.com.BHOOMI_MRTC.screens;

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.RestrictionOnLandReportAdapter;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.RLR_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.model.RLR_RES_Data;
import app.bmc.com.BHOOMI_MRTC.model.R_LAND_REPORT_TABLE;
import app.bmc.com.BHOOMI_MRTC.model.RestrictionOnLandReportTable;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestrictionOnLandReport extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {
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
    RtcViewInfoBackGroundTaskFragment mTaskFragment;
    private ProgressBar progressBar;

    private DataBaseHelper dataBaseHelper;
    String strValueOfJSONArrayResponse;
    List<RLR_RES_Interface> RLR_RES_Data;

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

        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }

        progressBar = findViewById(R.id.progress_circular);
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
        }
        if (isNetworkAvailable()){
            String input = "{" +
                    "\"DISTRICT_CODE\": \""+district_id+"\"," +
                    "\"TALUK_CODE\": \""+taluk_id+"\"," +
                    "\"HOBLI_CODE\":\""+hobli_id+"\"," +
                    "\"VILLAGE_CODE\": \""+village_id+"\"," +
                    "\"SURVEY_NUMBER\": \""+surveyNo+"\"," +
                    "\"SURNOC\": \""+suroc+"\"," +
                    "\"HISSA\": \""+hissa+"\"" +
                    "}";
            try
            {
                dataBaseHelper =
                        Room.databaseBuilder(getApplicationContext(),
                                DataBaseHelper.class, getString(R.string.db_name)).build();
                Observable<List<? extends RLR_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getRLR_RES(district_id,
                        taluk_id,hobli_id,village_id,surveyNo,suroc,hissa));
                districtDataObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<? extends RLR_RES_Interface>>() {

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<? extends RLR_RES_Interface> rlr_res_interfaces_list) {
                                progressBar.setVisibility(View.GONE);
                                Log.d("rlr_res_interfaces_list",rlr_res_interfaces_list.size()+"");

                                RLR_RES_Data = (List<RLR_RES_Interface>) rlr_res_interfaces_list;
//                                RLR_RES_Data = (List<RLR_RES_Interface>) rlr_res_interfaces_list;
                                if (rlr_res_interfaces_list.size()!=0) {
                                    Log.d("CHECK","Fetching from local");
                                    for (int i = 0; i <= rlr_res_interfaces_list.size(); i++) {

                                        String Response = RLR_RES_Data.get(0).getRLR_RES();
                                        Log.d("CHECK",Response);
                                        try {
                                            Gson gson = new Gson();
                                            restrictionOnLandReportTableList = gson.fromJson(Response, new TypeToken<List<RestrictionOnLandReportTable>>() {
                                            }.getType());

                                            if (restrictionOnLandReportTableList.size() == 0) {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(RestrictionOnLandReport.this, R.style.MyDialogTheme);
                                                builder.setTitle("STATUS")
                                                        .setMessage("No Data Found For this Record")
                                                        .setIcon(R.drawable.ic_notifications_black_24dp)
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", (dialog, id) -> {
                                                            dialog.cancel();
                                                            finish();
                                                        });
                                                final AlertDialog alert = builder.create();
                                                alert.show();
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                            } else {
                                                restrictionOnLandReportTableList.size();
                                                RestrictionOnLandReportAdapter adapter = new RestrictionOnLandReportAdapter(restrictionOnLandReportTableList);
                                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                                rvRestrictionReport.setLayoutManager(mLayoutManager);
                                                rvRestrictionReport.setItemAnimator(new DefaultItemAnimator());
                                                rvRestrictionReport.setAdapter(adapter);
                                            }
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                else {
                                    JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
                                    progressBar.setVisibility(View.VISIBLE);
                                    mTaskFragment.startBackgroundTask4(jsonObject, getString(R.string.server_report_url));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });



            }catch (Exception ex) {
                ex.printStackTrace();
            }
            //new GetRestrictionOnLandReport(district_id,taluk_id,hobli_id,village_id,surveyNo,suroc,hissa).execute();
        }else {
            Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPreExecute1() {

    }

    @Override
    public void onPostResponseSuccess1(String data) {

    }

    @Override
    public void onPreExecute2() {

    }

    @Override
    public void onPostResponseSuccess2(String data) {

    }

    @Override
    public void onPreExecute3() {

    }

    @Override
    public void onPostResponseSuccess3(String data) {

    }

    @Override
    public void onPreExecute4() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseErrorCultivator(String errorResponse) {

    }

    @Override
    public void onPostResponseSuccessCultivator(String gettcDataResult) {

    }

    @Override
    public void onPostResponseSuccess4(String data) {
        progressBar.setVisibility(View.GONE);
        if (data==null || data.equals("")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(RestrictionOnLandReport.this, R.style.MyDialogTheme);
            builder.setTitle("STATUS")
                    .setMessage("No Data Found For this Record")
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.cancel();
                        finish();
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            XmlToJson xmlToJson = new XmlToJson.Builder(data).build();
            String formatted = xmlToJson.toFormattedString();
            try {
                JSONObject obj = new JSONObject(formatted.replace("\n", ""));
                JSONObject OwnerDetails_Obj = obj.getJSONObject("OwnerDetails");
                OwnerDetails_Obj = OwnerDetails_Obj.getJSONObject("OWNERS");
                formatted = OwnerDetails_Obj.toString();
                formatted = formatted.replace("\"JOINT_OWNERS\":{", "\"JOINT_OWNERS\":[{");
                formatted = formatted.replace("}}", "}]}");
                OwnerDetails_Obj = new JSONObject(formatted);
//            OwnerDetails_Obj = OwnerDetails_Obj.getJSONObject("JOINT_OWNERS");
//            Log.d("JOINT_OWNERS", "str: " + OwnerDetails_Obj);
                JSONArray JOINT_OWNERS_jsonArray = OwnerDetails_Obj.getJSONArray("JOINT_OWNERS");

                String strJsonArray = JOINT_OWNERS_jsonArray.toString();
                strJsonArray = strJsonArray.replace("{\"OWNER\":", "");
                strJsonArray = strJsonArray.replace("},\"EXTENT\"", ",\"EXTENT\"");

                JSONArray final_JsonArray = new JSONArray(strJsonArray);

                Log.d("RESPONSE : ", String.valueOf(final_JsonArray));

                strValueOfJSONArrayResponse = String.valueOf(final_JsonArray);
                //---------DB INSERT-------

                dataBaseHelper =
                        Room.databaseBuilder(getApplicationContext(),
                                DataBaseHelper.class, getString(R.string.db_name)).build();
                Observable<Integer> noOfRows;
                noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsRLR());
                noOfRows
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.d("intValue",integer+"");

                                if (integer < 6) {
                                    Log.d("intValueIN",integer+"");
                                    List<R_LAND_REPORT_TABLE> RLR_list = loadData();
                                    createRLRTABLE_Data(RLR_list);
                                } else {
                                    Log.d("intValueELSE",integer+"");
                                    deleteByID(0);
//                                    deleteResponseByID();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });


                //---------------------------------------------------------------------------------------------

                Gson gson = new Gson();
                restrictionOnLandReportTableList = gson.fromJson(String.valueOf(final_JsonArray), new TypeToken<List<RestrictionOnLandReportTable>>() {
                }.getType());

                if (restrictionOnLandReportTableList.size() == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RestrictionOnLandReport.this, R.style.MyDialogTheme);
                    builder.setTitle("STATUS")
                            .setMessage("No Data Found For this Record")
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> {
                                dialog.cancel();
                                finish();
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                } else {
                    restrictionOnLandReportTableList.size();
                    RestrictionOnLandReportAdapter adapter = new RestrictionOnLandReportAdapter(restrictionOnLandReportTableList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rvRestrictionReport.setLayoutManager(mLayoutManager);
                    rvRestrictionReport.setItemAnimator(new DefaultItemAnimator());
                    rvRestrictionReport.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), Constants.ERR_MSG, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPostResponseError(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
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
            request.addProperty("DeptID", "WS_BMC_ID");
            request.addProperty("DeptPassPhase", " fa16722b-36e0-40d1-805a-9026d600ab1d");
            request.addProperty("Bhm_dist_code", distId);
            request.addProperty("Bhm_taluk_code", talkId);
            request.addProperty("Bhm_hobli_code", hblId);
            request.addProperty("Bhm_village_code", vlgId);
            request.addProperty("Bhm_survey_no", surveyNO);
            request.addProperty("Bhm_surnoc", surcoNo);
            request.addProperty("Bhm_Hissa", hissaNo);

//            request.addProperty("DeptID", "WS_BMC_ID");
//            request.addProperty("DeptPassPhase", " fa16722b-36e0-40d1-805a-9026d600ab1d");
//            request.addProperty("Bhm_dist_code", "20");
//            request.addProperty("Bhm_taluk_code", "2");
//            request.addProperty("Bhm_hobli_code", "16");
//            request.addProperty("Bhm_village_code", "36");
//            request.addProperty("Bhm_survey_no", "1");
//            request.addProperty("Bhm_surnoc", "*");
//            request.addProperty("Bhm_Hissa", "1");


            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS2);

            try {
                androidHttpTransport.call(SOAP_ACTION2,envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                resultFromServer = String.valueOf(resultString);


            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            return resultFromServer;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    progressDialog.dismiss();
                    XmlToJson xmlToJson = new XmlToJson.Builder(result.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
                    // convert to a JSONObject
                    JSONObject response = xmlToJson.toJson();
                    assert response != null;
                    JSONObject OwnerDetails_Obj = response.getJSONObject("OwnerDetails");
                    JSONObject OWNERS_Obj = OwnerDetails_Obj.getJSONObject("OWNERS");
                    JSONArray JOINT_OWNERS_jsonArray = OWNERS_Obj.getJSONArray("JOINT_OWNERS");

                    String strJsonArray = JOINT_OWNERS_jsonArray.toString();
                    strJsonArray = strJsonArray.replace("{\"OWNER\":","");
                    strJsonArray = strJsonArray.replace("},\"EXTENT\"",",\"EXTENT\"");

                    JSONArray final_JsonArray = new JSONArray(strJsonArray);

                        Gson gson = new Gson();
                        restrictionOnLandReportTableList = gson.fromJson(String.valueOf(final_JsonArray), new TypeToken<List<RestrictionOnLandReportTable>>() {
                        }.getType());

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
                        RestrictionOnLandReportAdapter adapter = new RestrictionOnLandReportAdapter(restrictionOnLandReportTableList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvRestrictionReport.setLayoutManager(mLayoutManager);
                        rvRestrictionReport.setItemAnimator(new DefaultItemAnimator());
                        rvRestrictionReport.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Result Null", Toast.LENGTH_SHORT).show();
                finish();
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

    //______________________________________________________________________DB____________________________________________________

    public List<R_LAND_REPORT_TABLE> loadData() {
        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<R_LAND_REPORT_TABLE> r_land_report_tables_arr = new ArrayList<>();
        try {
            R_LAND_REPORT_TABLE land_report_table = new R_LAND_REPORT_TABLE();
            land_report_table.setRLR_DST_ID(district_id);
            land_report_table.setRLR_TLK_ID(taluk_id);
            land_report_table.setRLR_HBL_ID(hobli_id);
            land_report_table.setRLR_VLG_ID(village_id);
            land_report_table.setRLR_SNO(surveyNo);
            land_report_table.setRLR_SUROC(suroc);
            land_report_table.setRLR_HISSA(hissa);
            land_report_table.setRLR_RES(strValueOfJSONArrayResponse);
            r_land_report_tables_arr.add(land_report_table);

        } catch (Exception e) {
            Log.d("Exception", e + "");
        }

        return r_land_report_tables_arr;
    }


    public void createRLRTABLE_Data(final List<R_LAND_REPORT_TABLE> r_land_report_tables_list) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertRestrictionOnLandReportData(r_land_report_tables_list));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
//                        Intent intent = new Intent(ViewRtcInformation.this, BhoomiHomePage.class);
//                        startActivity(intent);
//                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void deleteByID(final int id) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteByIdRLR(id));
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.i("delete", integer + "");
                        deleteResponseByID();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void deleteResponseByID() {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllRLRResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.i("delete", integer + "");


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
