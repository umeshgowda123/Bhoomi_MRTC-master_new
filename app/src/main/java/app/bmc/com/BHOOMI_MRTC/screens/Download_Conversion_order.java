package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;
import java.util.concurrent.Callable;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.retrofit.PariharaIndividualreportClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Download_Conversion_order extends AppCompatActivity {

    RadioGroup rgForSelection;
    RadioButton rbRequestID, rbSurveyNo;
    EditText etRequestID;
    Button btnDownloadOrder;
    String strSelected;
    LinearLayout linearLayout_survey;
    MaterialBetterSpinner sp_sum_district;
    MaterialBetterSpinner sp_sum_taluk;
    MaterialBetterSpinner sp_sum_hobli;
    MaterialBetterSpinner sp_sum_village;
    EditText etSurveyNumber;

    DataBaseHelper dataBaseHelper;

    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

    private  String surveyNumber, requestID;

    private int district_id;
    private int taluk_id;
    private int hobli_id;
    private int village_id;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_conversion_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        rgForSelection = findViewById(R.id.rgForSelection);
        rbRequestID = findViewById(R.id.rbRequestID);
        rbSurveyNo = findViewById(R.id.rbSurveyNo);
        etRequestID = findViewById(R.id.etRequestID);
        btnDownloadOrder = findViewById(R.id.btnDownloadOrder);
        linearLayout_survey = findViewById(R.id.linearLayout_survey);
        sp_sum_district = findViewById(R.id.sp_sum_district);
        sp_sum_taluk = findViewById(R.id.sp_sum_taluk);
        sp_sum_hobli = findViewById(R.id.sp_sum_hobli);
        sp_sum_village  =  findViewById(R.id.sp_sum_village);
        etSurveyNumber = findViewById(R.id.etSurveyNumber);

        dataBaseHelper = Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        strSelected = getString(R.string.request_id);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_sum_district.setAdapter(defaultArrayAdapter);
        sp_sum_taluk.setAdapter(defaultArrayAdapter);
        sp_sum_hobli.setAdapter(defaultArrayAdapter);
        sp_sum_village.setAdapter(defaultArrayAdapter);

        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(new Callable<List<? extends DistrictModelInterface>>() {

            @Override
            public List<? extends DistrictModelInterface> call() {
                return dataBaseHelper.daoAccess().getDistinctDistricts();
            }
        });
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends DistrictModelInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends DistrictModelInterface> mst_vlmList) {

                        districtData = (List<DistrictModelInterface>) mst_vlmList;
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<DistrictModelInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_sum_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        rgForSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbRequestID) {
                    strSelected = getString(R.string.request_id);
                    etRequestID.setVisibility(View.VISIBLE);
                    linearLayout_survey.setVisibility(View.GONE);

                    sp_sum_district.setText("");
                    sp_sum_taluk.setText("");
                    sp_sum_hobli.setText("");
                    sp_sum_village.setText("");
                    etSurveyNumber.setText("");
                    etSurveyNumber.setError(null);

                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                } else if(checkedId == R.id.rbSurveyNo) {
                    strSelected = getString(R.string.survey_no_wise);
                    etRequestID.setVisibility(View.GONE);
                    linearLayout_survey.setVisibility(View.VISIBLE);

                    etRequestID.setText("");
                    etRequestID.setError(null);

                    imm.hideSoftInputFromWindow(etRequestID.getWindowToken(), 0);
                    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                Log.d("strSelected", ""+strSelected);
            }
        });

        sp_sum_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_sum_taluk.setText("");
                sp_sum_hobli.setText("");
                sp_sum_village.setText("");
                district_id = districtData.get(position).getVLM_DST_ID();

                Log.d("district_id",""+district_id);
                Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(new Callable<List<? extends TalukModelInterface>>() {

                    @Override
                    public List<? extends TalukModelInterface> call() {
                        return dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id));
                    }
                });
                talukDataObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<? extends TalukModelInterface>>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<? extends TalukModelInterface> talukDataList) {
                                talukData = (List<TalukModelInterface>) talukDataList;
                                ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<TalukModelInterface>(Download_Conversion_order.this,
                                        android.R.layout.simple_list_item_single_choice, talukData);
                                sp_sum_taluk.setAdapter(talukArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


            }
        });

        sp_sum_taluk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_sum_hobli.setText("");
                sp_sum_village.setText("");
                taluk_id = talukData.get(position).getVLM_TLK_ID();
                Log.d("taluk_id",""+taluk_id);
                Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends HobliModelInterface>>() {

                    @Override
                    public List<? extends HobliModelInterface> call() {
                        return dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id));
                    }
                });
                noOfRows
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<? extends HobliModelInterface>>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<? extends HobliModelInterface> hobliDataList) {
                                hobliData = (List<HobliModelInterface>) hobliDataList;
                                ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<HobliModelInterface>(Download_Conversion_order.this,
                                        android.R.layout.simple_list_item_single_choice, hobliData);
                                sp_sum_hobli.setAdapter(hobliArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });

        sp_sum_hobli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sp_sum_village.setText("");
                hobli_id = hobliData.get(position).getVLM_HBL_ID();
                Log.d("hobli_id",""+hobli_id);
                Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(new Callable<List<? extends VillageModelInterface>>() {

                    @Override
                    public List<? extends VillageModelInterface> call() {
                        return dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id));
                    }
                });
                noOfRows
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<? extends VillageModelInterface>>() {


                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<? extends VillageModelInterface> villageDataList) {
                                villageData = (List<VillageModelInterface>) villageDataList;
                                ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<VillageModelInterface>(Download_Conversion_order.this,
                                        android.R.layout.simple_list_item_single_choice, villageData);
                                sp_sum_village.setAdapter(villageArrayAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });

        sp_sum_village.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                village_id = villageData.get(position).getVLM_VLG_ID();
                Log.d("village_id",""+village_id);
            }
        });

        btnDownloadOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    View focus = null;
                    boolean status = false;
                    if (strSelected.equals(getString(R.string.survey_no_wise))) {
                        String districtName = sp_sum_district.getText().toString().trim();
                        String talukName = sp_sum_taluk.getText().toString().trim();
                        String hobliName = sp_sum_hobli.getText().toString().trim();
                        String villageName = sp_sum_village.getText().toString().trim();
                        surveyNumber = etSurveyNumber.getText().toString().trim();
                        if (TextUtils.isEmpty(districtName)) {
                            focus = sp_sum_district;
                            status = true;
                            sp_sum_district.setError(getString(R.string.district_err));
                        } else if (TextUtils.isEmpty(talukName)) {
                            focus = sp_sum_taluk;
                            status = true;
                            sp_sum_taluk.setError(getString(R.string.taluk_err));
                        } else if (TextUtils.isEmpty(hobliName)) {
                            focus = sp_sum_hobli;
                            status = true;
                            sp_sum_hobli.setError(getString(R.string.hobli_err));
                        } else if (TextUtils.isEmpty(villageName)) {
                            focus = sp_sum_village;
                            status = true;
                            sp_sum_village.setError(getString(R.string.village_err));
                        } else if (TextUtils.isEmpty(surveyNumber)) {
                            focus = etSurveyNumber;
                            status = true;
                            etSurveyNumber.setError(getString(R.string.survey_no_err));
                        }
                        if (status) {
                            focus.requestFocus();
                        } else {

                            if (isNetworkAvailable()) {
                                progressDialog = new ProgressDialog(Download_Conversion_order.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();


                            } else {
                                selfDestruct();
                            }
                        }
                    } else if (strSelected.equals(getString(R.string.request_id))) {
                        requestID = etRequestID.getText().toString().trim();
                        if (TextUtils.isEmpty(requestID)) {
                            etRequestID.setError(getString(R.string.requestid_err));
                            etRequestID.requestFocus();
                        } else {
                            if (isNetworkAvailable()) {

                                progressDialog = new ProgressDialog(Download_Conversion_order.this);
                                progressDialog.setMessage("Please Wait");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                PariharaIndividualReportInteface apiInterface = PariharaIndividualreportClient.getClient("https://clws.karnataka.gov.in/Service4/BHOOMI/").create(PariharaIndividualReportInteface.class);
                                Call<PariharaIndividualDetailsResponse> call = apiInterface.getLandConversionFinalOrders_BasedOnReqId(Constants.REPORT_SERVICE_USER_NAME,
                                        Constants.REPORT_SERVICE_PASSWORD, requestID);
                                call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                                    @Override
                                    public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                                        if (response.isSuccessful()) {
                                            PariharaIndividualDetailsResponse result = response.body();
                                            assert result != null;
                                            String res = result.getGet_Afdvt_ReqSts_BasedOnAfdvtIdResult();
                                            Log.d("ResponseBasedOnReqID", "" + res);

                                            progressDialog.dismiss();

//                                        Intent intent = new Intent(Download_Conversion_order.this, LandConversionBasedOnAffidavit.class);
//                                        intent.putExtra("AFFIDAVIT_ResponseData", res);
//                                        intent.putExtra("AFFIDAVIT_ID", affidavitID);
//                                        Log.d("put : ", res+" & "+affidavitID);
//                                        startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                                        call.cancel();
                                        progressDialog.dismiss();
                                    }
                                });

                            } else {
                                if (savedInstanceState.isEmpty()) {
                                    Toast.makeText(Download_Conversion_order.this, "Please Enter Affidavit Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Download_Conversion_order.this, "Invalid Affidavit Number", Toast.LENGTH_SHORT).show();
                                }
                            }

                    }
                    } else {
                        selfDestruct();
                    }

                }

        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(Download_Conversion_order.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
