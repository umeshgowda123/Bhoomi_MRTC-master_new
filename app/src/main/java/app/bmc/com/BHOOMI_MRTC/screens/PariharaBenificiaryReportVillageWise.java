package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.interfaces.CalamityInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.DistrictModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.FinancialYearInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.HobliModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.SeasonInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.TalukModelInterface;
import app.bmc.com.BHOOMI_MRTC.interfaces.VillageModelInterface;
import app.bmc.com.BHOOMI_MRTC.model.CalamityDetails;
import app.bmc.com.BHOOMI_MRTC.model.PariharaIndividualDetailsResponse;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.ThreadSafeSingletonClass;
import app.bmc.com.BHOOMI_MRTC.model.YearDetails;
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

public class PariharaBenificiaryReportVillageWise extends AppCompatActivity {

    private MaterialBetterSpinner spinner_district;
    private MaterialBetterSpinner spinner_taluk;
    private MaterialBetterSpinner spinner_hobli;
    private MaterialBetterSpinner spinner_village;
    private MaterialBetterSpinner spinner_financial_year;
    private MaterialBetterSpinner spinner_season;
    private MaterialBetterSpinner spinner_calamity;
    private List<DistrictModelInterface> districtData;
    private List<TalukModelInterface> talukData;
    private List<HobliModelInterface> hobliData;
    private List<VillageModelInterface> villageData;

    private List<FinancialYearInterface> financialYearData;
    private List<SeasonInterface> seasonData;
    private List<CalamityInterface> calamityData;

    private String financial_year;
    private String season;
    private String calamity;

    private int district_id;
    private int taluk_id;
    private int hobli_id;
    private int village_id;

    private int year_id;
    private int season_id;
    private int calamity_id;

    private DataBaseHelper dataBaseHelper;

    private ProgressDialog progressDialog;

    PariharaIndividualReportInteface apiInterface;

    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parihara_benificiary_report_village_wise);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        spinner_district = findViewById(R.id.spinner_district);
        spinner_taluk = findViewById(R.id.spinner_taluk);
        spinner_hobli = findViewById(R.id.spinner_hobli);
        spinner_village = findViewById(R.id.spinner_village);
        spinner_financial_year = findViewById(R.id.spinner_financial_year);
        spinner_season = findViewById(R.id.spinner_season);
        spinner_calamity = findViewById(R.id.spinner_calamity);


        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        loadCalamityMasterData();

        btn_submit = findViewById(R.id.btn_submit);

        ArrayAdapter<String> defaultArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, new String[]{});
        spinner_district.setAdapter(defaultArrayAdapter);
        spinner_taluk.setAdapter(defaultArrayAdapter);
        spinner_hobli.setAdapter(defaultArrayAdapter);
        spinner_village.setAdapter(defaultArrayAdapter);
        spinner_financial_year.setAdapter(defaultArrayAdapter);
        spinner_season.setAdapter(defaultArrayAdapter);
        spinner_calamity.setAdapter(defaultArrayAdapter);

        Observable<List<? extends DistrictModelInterface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDistinctDistricts());
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
                        ArrayAdapter<DistrictModelInterface> districtArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, districtData);
                        spinner_district.setAdapter(districtArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        onClickAction();

    }

    private void loadCalamityMasterData() {

        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumberOfRowsFromCalamityMaster());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == 0) {
                            List<CalamityDetails> calmity_List = loadDataFromCsv();
                            createMasterData(calmity_List);

                        }else
                        {
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

    }

    private void onClickAction() {

        spinner_district.setOnItemClickListener((parent, view, position, id) -> {
            spinner_taluk.setText("");
            spinner_hobli.setText("");
            spinner_village.setText("");
            spinner_financial_year.setText("");
            spinner_season.setText("");
            spinner_calamity.setText("");
            district_id = districtData.get(position).getVLM_DST_ID();
            Observable<List<? extends TalukModelInterface>> talukDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getTalukByDistrictId(String.valueOf(district_id)));
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
                            ArrayAdapter<TalukModelInterface> talukArrayAdapter = new ArrayAdapter<>(PariharaBenificiaryReportVillageWise.this,
                                    android.R.layout.simple_list_item_single_choice, talukData);
                            spinner_taluk.setAdapter(talukArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        });


        spinner_taluk.setOnItemClickListener((parent, view, position, id) -> {
            spinner_hobli.setText("");
            spinner_village.setText("");
            spinner_financial_year.setText("");
            spinner_season.setText("");
            spinner_calamity.setText("");
            taluk_id = talukData.get(position).getVLM_TLK_ID();
            Observable<List<? extends HobliModelInterface>> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getHobliByTalukId_and_DistrictId(String.valueOf(taluk_id), String.valueOf(district_id)));
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
                            ArrayAdapter<HobliModelInterface> hobliArrayAdapter = new ArrayAdapter<>(PariharaBenificiaryReportVillageWise.this,
                                    android.R.layout.simple_list_item_single_choice, hobliData);
                            spinner_hobli.setAdapter(hobliArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        spinner_hobli.setOnItemClickListener((parent, view, position, id) -> {
            spinner_village.setText("");
            spinner_financial_year.setText("");
            spinner_season.setText("");
            spinner_calamity.setText("");
            hobli_id = hobliData.get(position).getVLM_HBL_ID();
            Observable<List<? extends VillageModelInterface>> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getVillageByHobliId_and_TalukId_and_DistrictId(String.valueOf(hobli_id), String.valueOf(taluk_id), String.valueOf(district_id)));
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
                            ArrayAdapter<VillageModelInterface> villageArrayAdapter = new ArrayAdapter<>(PariharaBenificiaryReportVillageWise.this,
                                    android.R.layout.simple_list_item_single_choice, villageData);
                            spinner_village.setAdapter(villageArrayAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });

        spinner_village.setOnItemClickListener((parent, view, position, id) -> {
            spinner_financial_year.setText("");
            spinner_season.setText("");
            spinner_calamity.setText("");
            village_id = villageData.get(position).getVLM_VLG_ID();
            loadFinancialYearList();
        });


        spinner_financial_year.setOnItemClickListener((parent, view, position, id) -> {
            spinner_season.setText("");
            spinner_calamity.setText("");
            year_id =  financialYearData.get(position).getCode();
            financial_year = spinner_financial_year.getText().toString().trim();
            loadSeasonYearList();

        });

        spinner_season.setOnItemClickListener((parent, view, position, id) -> {
            spinner_calamity.setText("");
            season_id = seasonData.get(position).getMSTSEASON_VAL();
            season = spinner_season.getText().toString().trim();
            loadCalamityList();

        });

        spinner_calamity.setOnItemClickListener((parent, view, position, id) -> {

            calamity = spinner_calamity.getText().toString().trim();
            calamity_id =  calamityData.get(position).getMSTCTYPE_ID();

        });


        btn_submit.setOnClickListener(v -> {
            String districtName = spinner_district.getText().toString().trim();
            String talukName = spinner_taluk.getText().toString().trim();
            String hobliName = spinner_hobli.getText().toString().trim();
            String villageName = spinner_village.getText().toString().trim();
            String financialYear = spinner_financial_year.getText().toString().trim();
            String season = spinner_season.getText().toString().trim();
            String calamity = spinner_calamity.getText().toString().trim();

            View focus = null;
            boolean status = false;
            if (TextUtils.isEmpty(districtName)) {
                focus = spinner_district;
                status = true;
                spinner_district.setError(getString(R.string.district_err));
            } else if (TextUtils.isEmpty(talukName)) {
                focus = spinner_taluk;
                status = true;
                spinner_taluk.setError(getString(R.string.taluk_err));
            } else if (TextUtils.isEmpty(hobliName)) {
                focus = spinner_hobli;
                status = true;
                spinner_hobli.setError(getString(R.string.hobli_err));
            } else if (TextUtils.isEmpty(villageName)) {
                focus = spinner_village;
                status = true;
                spinner_village.setError(getString(R.string.village_err));
            } else if (TextUtils.isEmpty(financialYear)) {
                focus = spinner_financial_year;
                status = true;
                spinner_financial_year.setError(getString(R.string.financial_year_error));
            } else if (TextUtils.isEmpty(season)) {
                focus = spinner_season;
                status = true;
                spinner_season.setError(getString(R.string.season_error));
            } else if (TextUtils.isEmpty(calamity)) {
                focus = spinner_calamity;
                status = true;
                spinner_calamity.setError(getString(R.string.calamity_error));
            }
            if (status) {
                focus.requestFocus();
            } else {

                if (isNetworkAvailable()) {
                    progressDialog = new ProgressDialog(PariharaBenificiaryReportVillageWise.this);
                    progressDialog.setMessage("Please Wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
                    Call<PariharaIndividualDetailsResponse> call = apiInterface.getVillageWisePariharaDetails(Constants.REPORT_SERVICE_USER_NAME,
                            Constants.REPORT_SERVICE_PASSWORD, district_id, taluk_id, hobli_id, village_id, year_id, season_id, calamity_id);
                    call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                        @Override
                        public void onResponse(Call<PariharaIndividualDetailsResponse> call, Response<PariharaIndividualDetailsResponse> response) {

                            if (response.isSuccessful()) {
                                PariharaIndividualDetailsResponse result = response.body();
                                progressDialog.dismiss();

                                spinner_district.setText("");
                                spinner_taluk.setText("");
                                spinner_hobli.setText("");
                                spinner_village.setText("");
                                spinner_financial_year.setText("");
                                spinner_season.setText("");
                                spinner_calamity.setText("");

                                String s = result.getGetPariharaBeneficiaryResult();
                                if (s.equals("<NewDataSet />")) {
                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PariharaBenificiaryReportVillageWise.this, R.style.MyDialogTheme);
                                    builder.setTitle("STATUS")
                                            .setMessage("No Report Found For this Record")
                                            .setIcon(R.drawable.ic_notifications_black_24dp)
                                            .setCancelable(false)
                                            .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                                    final android.app.AlertDialog alert = builder.create();
                                    alert.show();
                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                                } else {
                                    ThreadSafeSingletonClass.getInstance().setResponse(result.getGetPariharaBeneficiaryResult());
                                    Intent intent = new Intent(PariharaBenificiaryReportVillageWise.this, ShowPariharaBenificiaryDetailsVlgWise.class);
                                    //  intent.putExtra("response_data", result.getGetPariharaBeneficiaryResult());
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PariharaIndividualDetailsResponse> call, Throwable t) {
                            call.cancel();
                            progressDialog.dismiss();
                        }
                    });

                } else {
                    selfDestruct();
                }
            }
        });

    }

    private void loadCalamityList() {

        Observable<List<? extends CalamityInterface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDistinctCalamity());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends CalamityInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends CalamityInterface> mst_calamity_List) {

                        calamityData = (List<CalamityInterface>) mst_calamity_List;
                        ArrayAdapter<CalamityInterface> seasonArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, calamityData);
                        spinner_calamity.setAdapter(seasonArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadSeasonYearList() {

        Observable<List<? extends SeasonInterface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDistinctSeasons());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends SeasonInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends SeasonInterface> mst_season_List) {

                        seasonData = (List<SeasonInterface>) mst_season_List;
                        ArrayAdapter<SeasonInterface> seasonArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, seasonData);
                        spinner_season.setAdapter(seasonArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadFinancialYearList() {

        Observable<List<? extends FinancialYearInterface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDistinctYears());
        districtDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<? extends FinancialYearInterface>>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<? extends FinancialYearInterface> mst_yearList) {

                        financialYearData = (List<FinancialYearInterface>) mst_yearList;
                        ArrayAdapter<FinancialYearInterface> yearArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_list_item_single_choice, financialYearData);
                        spinner_financial_year.setAdapter(yearArrayAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
        AlertDialog alertDialog = new AlertDialog.Builder(PariharaBenificiaryReportVillageWise.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Please Enable Internet Connection");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "OK", (dialog, which) -> {

        });
        alertDialog.show();
    }


    public List<CalamityDetails> loadDataFromCsv() {
        List<CalamityDetails> cal_list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String mLine;


            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open("calamity_master.csv"), "UTF-8"));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String data[] = mLine.split(",");
                    CalamityDetails cal_type = new CalamityDetails();
                    cal_type.setMSTCTYPE_ID(Integer.valueOf(data[0]));
                    cal_type.setMSTCTYPE_VAL(Integer.valueOf(data[1]));
                    cal_type.setMSTCTYPE_DESC(data[2]);
                    cal_list.add(cal_type);


                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cal_list;
    }

    public void createMasterData(final List<CalamityDetails> calamity_List) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMasterCalamityData(calamity_List));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        List<SeasonDetails> season_list = loadSeasonDataFromCsv();
                        createSeasonMasterData(season_list);
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

    public List<SeasonDetails> loadSeasonDataFromCsv() {
        List<SeasonDetails> season_list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String mLine;


            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open("season_master.csv"), "UTF-8"));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String data[] = mLine.split(",");
                    SeasonDetails season_type = new SeasonDetails();
                    season_type.setMSTSEASON_ID(Integer.valueOf(data[0]));
                    season_type.setMSTSEASON_VAL(Integer.valueOf(data[1]));
                    season_type.setMSTSEASON_DESC(data[2]);
                    season_list.add(season_type);


                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return season_list;
    }

    public void createSeasonMasterData(final List<SeasonDetails> season_List) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMasterSeasonData(season_List));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        List<YearDetails> year_list = loadYearDataFromCsv();
                        createYearMasterData(year_list);
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

    public List<YearDetails> loadYearDataFromCsv() {
        List<YearDetails> year_list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String mLine;


            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open("year_master.csv"), "UTF-8"));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String data[] = mLine.split(",");
                    YearDetails year_type = new YearDetails();
                    year_type.setYear(data[0]);
                    year_type.setCode(Integer.valueOf(data[1]));
                    year_list.add(year_type);

                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return year_list;
    }

    public void createYearMasterData(final List<YearDetails> year_List) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertYearSeasonData(year_List));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
