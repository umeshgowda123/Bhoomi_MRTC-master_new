package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.LandConversion_Final_Order_Interface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.BHOOMI_API_Response;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.model.LandConversion_Final_Order_TABLE;
import app.bmc.com.BHOOMI_MRTC.retrofit.AuthorizationClient;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandConversionFinalOrder extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo {

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

    private String district_name = Integer.toString(district_id);
    private String taluk_name = Integer.toString(taluk_id);
    private String hobli_name = Integer.toString(hobli_id);
    private String village_name = Integer.toString(village_id);

    int i=10;
    String s=Integer.toString(i);//Now it will return "10"

    private ProgressDialog progressDialog;

    String ReqID_RES, SNO_RES;
    List<LandConversion_Final_Order_Interface> LCFO_DATA;
    private String language;

    PariharaIndividualReportInteface apiInterface;
    Call<BHOOMI_API_Response> call;

    String tokenType, accessToken;

    int AppType;

    private RtcViewInfoBackGroundTaskFragment mTaskFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_conversion_order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);

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

        Intent i = getIntent();
        AppType = i.getIntExtra("AppType", 0);

        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }

        progressDialog = new ProgressDialog(LandConversionFinalOrder.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);

//        dataBaseHelper =
//                Room.databaseBuilder(getApplicationContext(),
//                        DataBaseHelper.class, getString(R.string.db_name)).build();
//        Observable<String> stringObservable;
//        stringObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMaintenanceStatus(9));
//        stringObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String str) {
//                        if (str.equals("false")){
//                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Download_Conversion_order.this).create();
//                            alertDialog.setTitle(getString(R.string.status));
//                            alertDialog.setMessage(getString(R.string.this_service_is_under_maintenance));
//                            alertDialog.setCancelable(false);
//                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,getString(R.string.ok), (dialog, which) -> onBackPressed());
//                            alertDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        dataBaseHelper = Room.databaseBuilder(getApplicationContext(),
                DataBaseHelper.class, getString(R.string.db_name)).build();

        strSelected = getString(R.string.request_id);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_sum_district.setAdapter(defaultArrayAdapter);
        sp_sum_taluk.setAdapter(defaultArrayAdapter);
        sp_sum_hobli.setAdapter(defaultArrayAdapter);
        sp_sum_village.setAdapter(defaultArrayAdapter);

        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getDistinctDistricts() : dataBaseHelper.daoAccess().getDistinctDistrictsKannada());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends DistrictModelInterface>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<? extends DistrictModelInterface> mst_vlmList) {

                        districtData = (List<DistrictModelInterface>) mst_vlmList;
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<DistrictModelInterface>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_sum_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        rgForSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

//                    district_id= Integer.parseInt(null);
//                    taluk_id= Integer.parseInt(null);
//                    hobli_id= Integer.parseInt(null);
//                    village_id= Integer.parseInt(null);



                    district_name=null;
                    taluk_name = null;
                    hobli_name = null;
                    village_name  = null;
                    SNO_RES=null;
                    surveyNumber=null;


                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                } else if(checkedId == R.id.rbSurveyNo) {
                    strSelected = getString(R.string.survey_no_wise);
                    etRequestID.setVisibility(View.GONE);
                    linearLayout_survey.setVisibility(View.VISIBLE);

                    etRequestID.setText("");
                    etRequestID.setError(null);

                    requestID=null;
                    ReqID_RES=null;

                    imm.hideSoftInputFromWindow(etRequestID.getWindowToken(), 0);
                    //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

        sp_sum_district.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_taluk.setText("");
            sp_sum_hobli.setText("");
            sp_sum_village.setText("");
            sp_sum_taluk.setAdapter(defaultArrayAdapter);
            sp_sum_hobli.setAdapter(defaultArrayAdapter);
            sp_sum_village.setAdapter(defaultArrayAdapter);
            etSurveyNumber.setText("");
            district_id = districtData.get(position).getVLM_DST_ID();

            Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id)) : dataBaseHelper.daoAccess().getTalukByDistrictIdKannada(String.valueOf(district_id)));
            talukDataObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends TalukModelInterface>>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<? extends TalukModelInterface> talukDataList) {
                            talukData = (List<TalukModelInterface>) talukDataList;
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(LandConversionFinalOrder.this,
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            sp_sum_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });

        sp_sum_taluk.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_hobli.setText("");
            sp_sum_village.setText("");
            sp_sum_hobli.setAdapter(defaultArrayAdapter);
            sp_sum_village.setAdapter(defaultArrayAdapter);
            etSurveyNumber.setText("");
            taluk_id = talukData.get(position).getVLM_TLK_ID();
            Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictIdKannada(String.valueOf(taluk_id), String.valueOf(district_id)));
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends HobliModelInterface>>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<? extends HobliModelInterface> hobliDataList) {
                            hobliData = (List<HobliModelInterface>) hobliDataList;
                            ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(LandConversionFinalOrder.this,
                                    android.R.layout.simple_list_item_single_choice, hobliData);
                            sp_sum_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_sum_hobli.setOnItemClickListener((parent, view, position, id) -> {
            sp_sum_village.setText("");
            sp_sum_village.setAdapter(defaultArrayAdapter);
            etSurveyNumber.setText("");
            hobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> language.equalsIgnoreCase(Constants.LANGUAGE_EN) ? dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)) : dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictIdKannada(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)));
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<? extends VillageModelInterface>>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<? extends VillageModelInterface> villageDataList) {
                            villageData = (List<VillageModelInterface>) villageDataList;
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(LandConversionFinalOrder.this,
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            sp_sum_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        sp_sum_village.setOnItemClickListener((parent, view, position, id) -> village_id = villageData.get(position).getVLM_VLG_ID());

        btnDownloadOrder.setOnClickListener(v -> {

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
                            String dID = String.valueOf(district_id);
                            String tID = String.valueOf(taluk_id);
                            String hID = String.valueOf(hobli_id);
                            String vID = String.valueOf(village_id);

                        //----------------------------------LOCAl DB DATA PRESENT CHECK-----------------------------------------------------------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<List<? extends LandConversion_Final_Order_Interface>> DataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getSNO_RES(dID,tID,hID,vID,surveyNumber));
                        DataObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<List<? extends LandConversion_Final_Order_Interface>>() {

                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull List<? extends LandConversion_Final_Order_Interface> nterfaces_List) {

                                        LCFO_DATA = (List<LandConversion_Final_Order_Interface>) nterfaces_List;
                                        if (nterfaces_List.size()!=0) {
                                            for (int i = 0; i <= nterfaces_List.size()-1; i++) {

                                                String sno_RES = LCFO_DATA.get(0).getSNO_RES();
                                                if(sno_RES == null || sno_RES.equals("") || sno_RES.equals("[{\"Result\":\"Details not found\"}]")) {
                                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionFinalOrder.this, R.style.MyDialogTheme);
                                                    builder.setTitle(getString(R.string.status))
                                                            .setMessage(getString(R.string.no_data_found_for_this_record))
                                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                                            .setCancelable(false)
                                                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                    final android.app.AlertDialog alert = builder.create();
                                                    alert.show();
                                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                } else {

                                                    Intent intent = new Intent(LandConversionFinalOrder.this, ConversionFinalOrders_BasedOnReq_ID.class);
                                                    intent.putExtra("LandConversionFinalOrders", "" + sno_RES);
                                                    startActivity(intent);
                                                }
                                            }
                                        } else {
                                            progressDialog.show();
                                            mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        //---------------------------------------------------------------------------------------------

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

                        //----------------------------------LOCAl DB DATA PRESENT CHECK-----------------------------------------------------------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<List<? extends LandConversion_Final_Order_Interface>> DataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getREQUEST_ID_RES(requestID));
                        DataObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<List<? extends LandConversion_Final_Order_Interface>>() {

                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull List<? extends LandConversion_Final_Order_Interface> nterfaces_List) {


                                        LCFO_DATA = (List<LandConversion_Final_Order_Interface>) nterfaces_List;
                                        if (nterfaces_List.size()!=0) {
                                            for (int i = 0; i <= nterfaces_List.size()-1; i++) {


                                                String req_RES = LCFO_DATA.get(0).getREQUEST_ID_RES();
                                                if(req_RES == null || req_RES.equals("") || req_RES.equals("[{\"Result\":\"Details not found\"}]")) {
                                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionFinalOrder.this, R.style.MyDialogTheme);
                                                    builder.setTitle(getString(R.string.status))
                                                            .setMessage(getString(R.string.no_data_found_for_this_record))
                                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                                            .setCancelable(false)
                                                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                                                    final android.app.AlertDialog alert = builder.create();
                                                    alert.show();
                                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                                } else {
                                                    Intent intent = new Intent(LandConversionFinalOrder.this, ConversionFinalOrders_BasedOnReq_ID.class);
                                                    intent.putExtra("LandConversionFinalOrders", "" + req_RES);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                        else {
                                            progressDialog.show();
                                            mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        //---------------------------------------------------------------------------------------------

                    } else {
                        assert savedInstanceState != null;
                        if (savedInstanceState.isEmpty()) {
                            Toast.makeText(LandConversionFinalOrder.this, "Please Enter Affidavit Number", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LandConversionFinalOrder.this, "Invalid Affidavit Number", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            } else {
                selfDestruct();
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


    public void selfDestruct() {
        AlertDialog alertDialog = new AlertDialog.Builder(LandConversionFinalOrder.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage(getString(R.string.please_enable_internet_connection));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.ok), (dialog, which) -> {

        });
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.menu_item_history)
        {
            Intent intent = new Intent(LandConversionFinalOrder.this, Serach_History.class);
            intent.putExtra("APPType", AppType);
            startActivity(intent);

        }
        return  super.onOptionsItemSelected(item);
    }

    //______________________________________________________________________DB____________________________________________________

    public List<LandConversion_Final_Order_TABLE> loadData() {
//        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<LandConversion_Final_Order_TABLE> tables_arr = new ArrayList<>();

        try {
            LandConversion_Final_Order_TABLE table = new LandConversion_Final_Order_TABLE();
            table.setREQUEST_ID(requestID);
            table.setREQUEST_ID_RES(ReqID_RES);
            table.setDST_ID(district_id+"");
            table.setTLK_ID(taluk_id+"");
            table.setHBL_ID(hobli_id+"");
            table.setVLG_ID(village_id+"");
            table.setS_NO(surveyNumber);
            table.setSNO_RES(SNO_RES);
            tables_arr.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables_arr;
    }


    public void createLandConversion_Final_Order_TABLEData(final List<LandConversion_Final_Order_TABLE> LandConversion_Final_Order_TABLE_List) {
        Observable<Long[]> insertLandConversion_Final_Order_TABLEData = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertLandConversion_Final_Order_Data(LandConversion_Final_Order_TABLE_List));
        insertLandConversion_Final_Order_TABLEData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long[] longs) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void deleteAllResponse(List<LandConversion_Final_Order_TABLE> LCFO_List) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteLandConversion_Final_Order_Response());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        createLandConversion_Final_Order_TABLEData(LCFO_List);
                    }
                });
    }

    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(call != null && call.isExecuted()) {
            call.cancel();
        }
    }


    public void ReqID_Response(String token_type, String token){

        apiInterface = AuthorizationClient.getClient(getString(R.string.rest_service_url),token_type,token).create(PariharaIndividualReportInteface.class);
        call = apiInterface.getLandConversionFinalOrders_BasedOnReqId(requestID);
        call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {

                if (response.isSuccessful()) {
                    BHOOMI_API_Response result = response.body();
                    assert result != null;
                    ReqID_RES = result.getBhoomI_API_Response();

                    progressDialog.dismiss();
                    if(ReqID_RES == null || ReqID_RES.equals("") || ReqID_RES.equals("[{\"Result\":\"Details not found\"}]")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionFinalOrder.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.no_data_found_for_this_record))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {

                        //---------DB INSERT-------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<Integer> noOfRows;
                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsLandConversion_Final_Order_Tbl());
                        noOfRows
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {


                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull Integer integer) {
                                        List<LandConversion_Final_Order_TABLE> LCFO_List = loadData();
                                        if (integer < 6) {
                                            createLandConversion_Final_Order_TABLEData(LCFO_List);
                                        } else {
                                            deleteAllResponse(LCFO_List);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(LandConversionFinalOrder.this, ConversionFinalOrders_BasedOnReq_ID.class);
                                        intent.putExtra("LandConversionFinalOrders", "" + ReqID_RES);
                                        startActivity(intent);
                                    }
                                });
                        //---------------------------------------------------------------------------------------------

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    public void SNO_Response(String token_type, String token){

        apiInterface = AuthorizationClient.getClient(getString(R.string.rest_service_url),token_type,token).create(PariharaIndividualReportInteface.class);
        call = apiInterface.getLandConversionFinalOrders_BasedOnSurveyNo(district_id, taluk_id, hobli_id, village_id, surveyNumber);
        call.enqueue(new Callback<BHOOMI_API_Response>() {
            @Override
            public void onResponse(@NonNull Call<BHOOMI_API_Response> call, @NonNull Response<BHOOMI_API_Response> response) {

                if (response.isSuccessful()) {
                    BHOOMI_API_Response result = response.body();
                    assert result != null;
                    SNO_RES = result.getBhoomI_API_Response();

                    progressDialog.dismiss();
                    if(SNO_RES == null || SNO_RES.equals("") || SNO_RES.contains("Details not found")) {
                        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionFinalOrder.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.status))
                                .setMessage(getString(R.string.no_data_found_for_this_record))
                                .setIcon(R.drawable.ic_notifications_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final android.app.AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else {


                        //---------DB INSERT-------
                        dataBaseHelper =
                                Room.databaseBuilder(getApplicationContext(),
                                        DataBaseHelper.class, getString(R.string.db_name)).build();
                        Observable<Integer> noOfRows;
                        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRowsLandConversion_Final_Order_Tbl());
                        noOfRows
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {


                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull Integer integer) {
                                        List<LandConversion_Final_Order_TABLE> LCFO_List = loadData();
                                        if (integer < 6) {
                                            createLandConversion_Final_Order_TABLEData(LCFO_List);
                                        } else {
                                            deleteAllResponse(LCFO_List);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(LandConversionFinalOrder.this, ConversionFinalOrders_BasedOnReq_ID.class);
                                        intent.putExtra("LandConversionFinalOrders", "" + SNO_RES);
                                        startActivity(intent);
                                    }
                                });
                        //---------------------------------------------------------------------------------------------
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BHOOMI_API_Response> call, @NonNull Throwable t) {
                call.cancel();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    }

    @Override
    public void onPostResponseSuccess_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPostResponseError_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPreExecuteToken() {
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        accessToken = AccessToken;
        tokenType = TokenType;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LandConversionFinalOrder.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.something_went_wrong_pls_try_again))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            try {
                ClsAppLgs objClsAppLgs = new ClsAppLgs();
                objClsAppLgs.setAppID(1);
                objClsAppLgs.setAppType(AppType);
                objClsAppLgs.setIPAddress("");

                mTaskFragment.startBackgroundTask_AppLgs(objClsAppLgs, getString(R.string.rest_service_url), tokenType, accessToken);

//                JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
//                mTaskFragment.startBackgroundTask_GetDetails_VilWise(jsonObject, getString(R.string.rest_service_url), tokenType, accessToken);

            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        progressDialog.dismiss();
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
        progressDialog.show();
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {
        if (strSelected.equals(getString(R.string.request_id))) {
            ReqID_Response(tokenType, accessToken);
        }else if (strSelected.equals(getString(R.string.survey_no_wise))){
            SNO_Response(tokenType, accessToken);
        }
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {
        if (strSelected.equals(getString(R.string.request_id))) {
            ReqID_Response(tokenType, accessToken);
        }else if (strSelected.equals(getString(R.string.survey_no_wise))){
            SNO_Response(tokenType, accessToken);
        }
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
}
