package app.bmc.com.BHOOMI_MRTC.screens;


import androidx.annotation.NonNull;
import androidx.room.Room;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;

import android.util.DisplayMetrics;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;

import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewRtcInformationByOwnerName extends AppCompatActivity {

    private MaterialBetterSpinner sp_district;
    private MaterialBetterSpinner sp_taluk;
    private MaterialBetterSpinner sp_hobli;
    private MaterialBetterSpinner sp_village;
    private Button btn_ViewDetails;
    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;
    private int district_id;
    private int taluk_id;
    private int hobli_id;
    private int village_id;
    boolean clearData;
    int AppType;

//    String SOAP_ACTION2 = "http://tempuri.org/GetDetails_VillageWise_JSON";
//    public final String OPERATION_NAME2 = "GetDetails_VillageWise_JSON";  //Method_name
//    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
//    String SOAP_ADDRESS2 = "https://parihara.karnataka.gov.in/service16/WS_BHOOMI.asmx";

    private String language;
    private DataBaseHelper dataBaseHelper;

    ArrayAdapter<String> defaultArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rtc_information_by_owner_name);

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

        sp_district = findViewById(R.id.sp_district);
        sp_taluk = findViewById(R.id.sp_taluk);
        sp_hobli = findViewById(R.id.sp_hobli);
        sp_village = findViewById(R.id.sp_village);
        btn_ViewDetails =  findViewById(R.id.btn_ViewDetails);

        defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        sp_district.setAdapter(defaultArrayAdapter);
        sp_taluk.setAdapter(defaultArrayAdapter);
        sp_hobli.setAdapter(defaultArrayAdapter);
        sp_village.setAdapter(defaultArrayAdapter);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        Intent i = getIntent();
        clearData = i.getBooleanExtra("clearData", false);
        AppType = i.getIntExtra("AppType", 0);

        if (clearData){
            deleteDBData();
        }

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
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        sp_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        onClickAction();

    }

    private void onClickAction() {

        sp_district.setOnItemClickListener((parent, view, position, id) -> {
            sp_taluk.setText("");
            sp_hobli.setText("");
            sp_village.setText("");
            sp_taluk.setAdapter(defaultArrayAdapter);
            sp_hobli.setAdapter(defaultArrayAdapter);
            sp_village.setAdapter(defaultArrayAdapter);

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
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(ViewRtcInformationByOwnerName.this,
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            sp_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });


        sp_taluk.setOnItemClickListener((parent, view, position, id) -> {
            sp_hobli.setText("");
            sp_village.setText("");
            sp_hobli.setAdapter(defaultArrayAdapter);
            sp_village.setAdapter(defaultArrayAdapter);

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
                            ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(ViewRtcInformationByOwnerName.this,
                                    android.R.layout.simple_list_item_single_choice, hobliData);
                            sp_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });

        sp_hobli.setOnItemClickListener((parent, view, position, id) -> {
            sp_village.setText("");
            sp_village.setAdapter(defaultArrayAdapter);
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
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(ViewRtcInformationByOwnerName.this,
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            sp_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
        sp_village.setOnItemClickListener((parent, view, position, id) -> village_id = villageData.get(position).getVLM_VLG_ID());



        btn_ViewDetails.setOnClickListener(v -> {
            String districtName = sp_district.getText().toString().trim();
            String talukName = sp_taluk.getText().toString().trim();
            String hobliName = sp_hobli.getText().toString().trim();
            String villageName = sp_village.getText().toString().trim();

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = sp_district;
                status = true;
                sp_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = sp_taluk;
                status = true;
                sp_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = sp_hobli;
                status = true;
                sp_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = sp_village;
                status = true;
                sp_village.setError(getString(R.string.village_err));
            }
            if (status) {
                focus.requestFocus();
            } else {

                if (isNetworkAvailable())
                {
                    Intent intent = new Intent(ViewRtcInformationByOwnerName.this, ShowRtcDetailsBYOwnerName.class);
                    intent.putExtra("distId", district_id + "");
                    intent.putExtra("talkId", taluk_id + "");
                    intent.putExtra("hblId", hobli_id + "");
                    intent.putExtra("village_id", village_id + "");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.internet_not_available, Toast.LENGTH_LONG).show();
                }
            }


        });

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id ==R.id.menu_item_history)
//        {
//            Intent intent = new Intent(ViewRtcInformationByOwnerName.this, Serach_History.class);
//            intent.putExtra("APPType", AppType);
//            startActivity(intent);
//
//        }
//        return  super.onOptionsItemSelected(item);
//    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void deleteDBData(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResponseRow());
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
                    }
                });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}