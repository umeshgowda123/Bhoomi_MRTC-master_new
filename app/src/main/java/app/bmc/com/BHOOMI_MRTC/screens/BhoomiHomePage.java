package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.room.Room;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
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
    LinearLayout layout_mutation_pendency;
    LinearLayout layout_mut_summery;
    LinearLayout layout_mutation_status;
    LinearLayout view_land_conversion, download_Conversion_order;
    LinearLayout layout_restriction_land;

    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    LinearLayout linearLayout_bhoomi;
    private DataBaseHelper dataBaseHelper;
    boolean clearData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        String language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bhoomi1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        progressDialog = new ProgressDialog(BhoomiHomePage.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);

        checkForAppUpdate();

//        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(this);
//        appUpdateChecker.checkForUpdate(false);
        linearLayout_bhoomi = findViewById(R.id.linearLayout_bhoomi);
        layout_viewRtc =  findViewById(R.id.layout_viewRtc);
        layout_rtc_verify =  findViewById(R.id.layout_rtc_verify);
        layout_view_rtc_ownerName =  findViewById(R.id.layout_view_rtc_ownerName);
        layout_restriction_land = findViewById(R.id.layout_restriction_land);
        layout_mut_summery =  findViewById(R.id.layout_mut_summery);
        layout_mutation_pendency =  findViewById(R.id.layout_mutation_pendency);
        layout_mutation_status =  findViewById(R.id.layout_mutation_status);
        view_land_conversion = findViewById(R.id.view_land_conversion);
        download_Conversion_order = findViewById(R.id.download_Conversion_order);

        Intent i = getIntent();
        clearData = i.getBooleanExtra("clearData", false);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

        if (clearData){
            progressDialog.show();
            deleteViewRTCInformationTbl();
        }

        loadCalamityMasterData();
        //createMaintenanceTbl();

        layout_viewRtc.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this, ViewRtcInformation.class);
            startActivity(intent);
        });

        layout_rtc_verify.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this, RtcVerification.class);
            startActivity(intent);
        });


        layout_view_rtc_ownerName.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this, ViewRtcInformationByOwnerName.class);
            startActivity(intent);
        });

        layout_mutation_pendency.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this,MutationPendencyDetails.class);
            startActivity(intent);
        });


        layout_mut_summery.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this,ViewMutationSummeryReport.class);
            startActivity(intent);
        });



        layout_mutation_status.setOnClickListener(v -> {

            Intent intent = new Intent(BhoomiHomePage.this,ViewMutationStatusInformation.class);
            startActivity(intent);
        });


        layout_restriction_land.setOnClickListener(v -> {
            Intent in = new Intent(BhoomiHomePage.this,RestrictionOnLand.class);
            startActivity(in);
        });


        view_land_conversion.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this,LandConversion.class);
            startActivity(intent);
        });

        download_Conversion_order.setOnClickListener(v -> {
            Intent intent = new Intent(BhoomiHomePage.this, Download_Conversion_order.class);
            startActivity(intent);
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
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(BhoomiHomePage.this, Language.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    new InputStreamReader(getApplicationContext().getAssets().open("calamity_master.csv"), StandardCharsets.UTF_8));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String[] data = mLine.split(",");
                    CalamityDetails cal_type = new CalamityDetails();
                    cal_type.setMSTCTYPE_ID(Integer.parseInt(data[0]));
                    cal_type.setMSTCTYPE_VAL(Integer.parseInt(data[1]));
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
                    new InputStreamReader(getApplicationContext().getAssets().open("season_master.csv"), StandardCharsets.UTF_8));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String[] data = mLine.split(",");
                    SeasonDetails season_type = new SeasonDetails();
                    season_type.setMSTSEASON_ID(Integer.parseInt(data[0]));
                    season_type.setMSTSEASON_VAL(Integer.parseInt(data[1]));
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
                    new InputStreamReader(getApplicationContext().getAssets().open("year_master.csv"), StandardCharsets.UTF_8));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String[] data = mLine.split(",");
                    YearDetails year_type = new YearDetails();
                    year_type.setYear(data[0]);
                    year_type.setCode(Integer.parseInt(data[1]));
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

    private void deleteViewRTCInformationTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResponseRow());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteRtcVerificationTbl();
                    }
                });
    }

    private void deleteRtcVerificationTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllRTCVResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteMutationPendDetailsTbl();
                    }
                });
    }

    private void deleteMutationPendDetailsTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteMutationSummaryReportTbl();
                    }
                });
    }

    private void deleteMutationSummaryReportTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteViewMutationStatusTbl();
                    }
                });
    }

    private void deleteViewMutationStatusTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteAllVMSResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteRestrictionOnLandTbl();
                    }
                });
    }

    private void deleteRestrictionOnLandTbl(){
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


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteLandConReqStatusTbl();
                    }
                });
    }

    private void deleteLandConReqStatusTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteLandConversionResponse());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        deleteLandConFinalOrderTbl();
                    }
                });
    }

    private void deleteLandConFinalOrderTbl(){
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteLandConversion_Final_Order_Response());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                // If the update is cancelled or fails,
                // you can request to start the update again.
                unregisterInstallStateUpdListener();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }


    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = installState -> {
            // Show module progress, log state, or install the update.
            if (installState.installStatus() == InstallStatus.DOWNLOADED)
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                popupSnackbarForCompleteUpdateAndUnregister();
        };

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
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
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            //FLEXIBLE:
            // If the update is downloaded but not installed,
            // notify the user to complete the update.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdateAndUnregister();
            }
            //Toast.makeText(getApplicationContext(), "UpdateAvailability: "+appUpdateInfo.updateAvailability(), Toast.LENGTH_SHORT).show();
            //IMMEDIATE:
            if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                startAppUpdateImmediate(appUpdateInfo);
            }
        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
