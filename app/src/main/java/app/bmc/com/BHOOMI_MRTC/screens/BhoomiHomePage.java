package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.model.AppUpdateChecker;
import app.bmc.com.BHOOMI_MRTC.model.CalamityDetails;
import app.bmc.com.BHOOMI_MRTC.model.SeasonDetails;
import app.bmc.com.BHOOMI_MRTC.model.YearDetails;
import app.bmc.com.BHOOMI_MRTC.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BhoomiHomePage extends AppCompatActivity {

    LinearLayout layout_viewRtc;
    LinearLayout layout_rtc_verify;
    LinearLayout layout_view_rtc_ownerName;
    LinearLayout layout_clws_status;
    LinearLayout layout_certificate_pacs;
    LinearLayout layout_certificate_banks;
    LinearLayout layout_parihara_individual;
    LinearLayout layout_parihara_benificary;
    LinearLayout layout_benificiary_land_report;
    LinearLayout layout_download_villageMap;
    LinearLayout layout_mutation_pendency;
    LinearLayout layout_loan_w_branch_wise;

    LinearLayout layout_mut_summery;

    LinearLayout layout_loan_w_bank;

    LinearLayout layout_mutation_status;
    LinearLayout layout_loan_b_layout;
    LinearLayout layout_loan_w_farmer_wise;

    LinearLayout layout_pacs_bank_wise;
    LinearLayout layout_pacs_report_branchwise;
    LinearLayout layout_pacs_farmer_wise;

    LinearLayout layout_view_phody_sketch;
    LinearLayout view_mojini_req_status;
    LinearLayout view_land_conversion, download_Conversion_order;
    LinearLayout layout_restriction_land;

    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    LinearLayout linearLayout_bhoomi;

    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        String language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bhoomi1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        checkForAppUpdate();

//        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this);
//        appUpdateChecker.checkForUpdate(false);
        linearLayout_bhoomi = findViewById(R.id.linearLayout_bhoomi);
        layout_viewRtc =  findViewById(R.id.layout_viewRtc);
        layout_rtc_verify =  findViewById(R.id.layout_rtc_verify);
        layout_view_rtc_ownerName =  findViewById(R.id.layout_view_rtc_ownerName);
        layout_restriction_land = findViewById(R.id.layout_restriction_land);

        layout_clws_status =  findViewById(R.id.layout_clws_status);
        layout_certificate_pacs =  findViewById(R.id.layout_certificate_pacs);
        layout_certificate_banks =  findViewById(R.id.layout_certificate_banks);

        layout_parihara_individual =  findViewById(R.id.layout_parihara_individual);
        layout_parihara_benificary =  findViewById(R.id.layout_parihara_benificary);
        layout_benificiary_land_report =  findViewById(R.id.layout_benificiary_land_report);
        layout_download_villageMap =  findViewById(R.id.layout_download_villageMap);

        layout_mutation_pendency =  findViewById(R.id.layout_mutation_pendency);
        layout_loan_w_branch_wise =  findViewById(R.id.layout_loan_w_branch_wise);

        layout_mut_summery =  findViewById(R.id.layout_mut_summery);
        layout_loan_w_bank =  findViewById(R.id.layout_loan_w_bank);

        layout_mutation_status =  findViewById(R.id.layout_mutation_status);
        layout_loan_w_farmer_wise =  findViewById(R.id.layout_loan_w_farmer_wise);

        layout_pacs_bank_wise = findViewById(R.id.layout_pacs_bank_wise);
        layout_pacs_report_branchwise = findViewById(R.id.layout_pacs_report_branchwise);
        layout_pacs_farmer_wise = findViewById(R.id.layout_pacs_farmer_wise);

        layout_view_phody_sketch =  findViewById(R.id.layout_view_phody_sketch);
        view_mojini_req_status =  findViewById(R.id.view_mojini_req_status);

        view_land_conversion = findViewById(R.id.view_land_conversion);
        download_Conversion_order = findViewById(R.id.download_Conversion_order);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        loadCalamityMasterData();


        layout_viewRtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this, ViewRtcInformation.class);
                startActivity(intent);
            }
        });

        layout_rtc_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this, RtcVerification.class);
                startActivity(intent);
            }
        });


        layout_view_rtc_ownerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this, ViewRtcInformationByOwnerName.class);
                startActivity(intent);
            }
        });

        layout_restriction_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(BhoomiHomePage.this,RestrictionOnLand.class);
                startActivity(in);
            }
        });

        layout_clws_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BhoomiHomePage.this, ClwsStatus.class);
                startActivity(intent);
            }
        });

        layout_certificate_pacs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,CitizenPaymentCertificatePacsActivity.class);
                startActivity(intent);
            }
        });



        layout_certificate_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,CitizenPaymentCertificateBanksActivity.class);
                startActivity(intent);
            }
        });



        layout_parihara_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,PariharaDetailsIndividual.class);
                startActivity(intent);
            }
        });

        layout_parihara_benificary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,PariharaBenificiaryReportVillageWise.class);
                startActivity(intent);
            }
        });

        layout_benificiary_land_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,PariharaBenificiaryReportLandWise.class);
                startActivity(intent);
            }
        });


        layout_download_villageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(BhoomiHomePage.this,DownloadVillageMap.class);
                startActivity(intent);

            }
        });


        layout_mutation_pendency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this,MutationPendencyDetails.class);
                startActivity(intent);
            }
        });


        layout_mut_summery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this,ViewMutationSummeryReport.class);
                startActivity(intent);
            }
        });



        layout_mutation_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BhoomiHomePage.this,ViewMutationStatusInformation.class);
                startActivity(intent);
            }
        });


        layout_loan_w_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BhoomiHomePage.this, LoanWaiverReportForCommercialBankWise.class);
                startActivity(intent);
            }
        });


        layout_loan_w_farmer_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BhoomiHomePage.this, LoanWaiverReportForCommercialFramerWise.class);
                startActivity(intent);
            }
        });

        layout_loan_w_branch_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BhoomiHomePage.this,LoanWaiverReportForCommercialBranchWise.class);
                startActivity(intent);
            }
        });

        layout_pacs_bank_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this, LoanWaiverReportForPacsBankWise.class);
                startActivity(intent);
            }
        });

        layout_pacs_report_branchwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this,LoanWaiverReportForPacsBranchWsie.class);
                startActivity(intent);
            }
        });

        layout_pacs_farmer_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this,LoanWaiverReportForPacsFarmerWise.class);
                startActivity(intent);
            }
        });



        layout_view_phody_sketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "http://202.138.101.171/service18";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                Intent intent = new Intent(BhoomiHomePage.this,MojiniPhodySketch.class);
                startActivity(intent);
            }
        });


        view_mojini_req_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BhoomiHomePage.this, MojiniRequestStatus.class);
                startActivity(i);
            }
        });

        view_land_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this,LandConversion.class);
                startActivity(intent);
            }
        });

        download_Conversion_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BhoomiHomePage.this, Download_Conversion_order.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bhoomi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(BhoomiHomePage.this, Language.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    private void loadCalamityMasterData() {

        Observable<Integer> noOfRows = Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() {
                return dataBaseHelper.daoAccess().getNumberOfRowsFromCalamityMaster();
            }
        });
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
                            Log.i("Loaded","Already Loaded");
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
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(new Callable<Long[]>() {

            @Override
            public Long[] call() {
                return dataBaseHelper.daoAccess().insertMasterCalamityData(calamity_List);
            }
        });
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.i("Inserted", longs + " ");
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
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(new Callable<Long[]>() {

            @Override
            public Long[] call() {
                return dataBaseHelper.daoAccess().insertMasterSeasonData(season_List);
            }
        });
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.i("Inserted", longs + " ");
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
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(new Callable<Long[]>() {

            @Override
            public Long[] call() {
                return dataBaseHelper.daoAccess().insertYearSeasonData(year_List);
            }
        });
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.i("Inserted", longs + " ");

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
    protected void onResume() {
        super.onResume();
        Log.d("APP_UPDATE", "onResume Entered");
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("APP_UPDATE", "onActivityResult Entered");
        switch (requestCode) {
            case REQ_CODE_VERSION_UPDATE:
                if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                    Log.d("APP_UPDATE", "Update flow failed! Result code: " + resultCode);
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    unregisterInstallStateUpdListener();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("APP_UPDATE", " onDestroy Entered");
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }


    private void checkForAppUpdate() {
        Log.d("APP_UPDATE", "checkForAppUpdate Entered");
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                Log.d("APP_UPDATE", ""+installState);
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d("APP_UPDATE", "appUpdateInfo"+appUpdateInfo);
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d("APP_UPDATE", "appUpdateInfo_updateAvailability"+appUpdateInfo.updateAvailability());
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    Log.d("APP_UPDATE", "appUpdateInfo_FLEXIBLE"+appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE));
                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
                    Log.d("APP_UPDATE", "appUpdateInfo_IMMEDIATE"+appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE));
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        Log.d("APP_UPDATE", "startAppUpdateIm Entered");
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        Log.d("APP_UPDATE", "startAppUpdateFl Entered");
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Log.d("APP_UPDATE", "popupSnackbarForC Entered");
        Snackbar snackbar = Snackbar.make(linearLayout_bhoomi, "Update Downloaded", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Restart", view -> appUpdateManager.completeUpdate());
        snackbar.show();

        //Toast.makeText(getApplicationContext(), "Update Downloaded", Toast.LENGTH_SHORT).show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        Log.d("APP_UPDATE", "checkNewAppVersionState Entered");
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            Log.d("APP_UPDATE", "appUpdateInfo_Ver"+appUpdateInfo);
            //FLEXIBLE:
            // If the update is downloaded but not installed,
            // notify the user to complete the update.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.d("APP_UPDATE", "appUpdateInfo_Ver"+appUpdateInfo.installStatus());
                popupSnackbarForCompleteUpdateAndUnregister();
            }
            //Toast.makeText(getApplicationContext(), "UpdateAvailability: "+appUpdateInfo.updateAvailability(), Toast.LENGTH_SHORT).show();
            //IMMEDIATE:
            if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                Log.d("APP_UPDATE", "appUpdateInfo_Ver"+appUpdateInfo.updateAvailability());
                startAppUpdateImmediate(appUpdateInfo);
            }
        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        Log.d("APP_UPDATE", "unregisterInstall Entered");
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }
}
