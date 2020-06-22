package app.bmc.com.bhoomi.screens;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.database.DataBaseHelper;
import app.bmc.com.bhoomi.model.CalamityDetails;
import app.bmc.com.bhoomi.model.SeasonDetails;
import app.bmc.com.bhoomi.model.YearDetails;
import app.bmc.com.bhoomi.util.Constants;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BhoomiHomePage extends AppCompatActivity {

    private LinearLayout layout_viewRtc;
    private LinearLayout layout_rtc_verify;
    private LinearLayout layout_view_rtc_ownerName;
    private LinearLayout layout_clws_status;
    private LinearLayout layout_certificate_pacs;
    private LinearLayout layout_certificate_banks;
    private LinearLayout layout_parihara_individual;
    private LinearLayout layout_parihara_benificary;
    private LinearLayout layout_benificiary_land_report;
    private LinearLayout layout_download_villageMap;
    private LinearLayout layout_mutation_pendency;
    private LinearLayout layout_loan_w_branch_wise;

    private LinearLayout layout_mut_summery;

    private LinearLayout layout_loan_w_bank;

    private LinearLayout layout_mutation_status;
    private LinearLayout layout_loan_b_layout;
    private LinearLayout layout_loan_w_farmer_wise;

    private LinearLayout layout_pacs_bank_wise;
    private LinearLayout layout_pacs_report_branchwise;
    private LinearLayout layout_pacs_farmer_wise;

    private LinearLayout layout_view_phody_sketch;
    private LinearLayout view_mojini_req_status;
    LinearLayout view_land_conversion, download_Conversion_order;

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


        layout_viewRtc =  findViewById(R.id.layout_viewRtc);
        layout_rtc_verify =  findViewById(R.id.layout_rtc_verify);
        layout_view_rtc_ownerName =  findViewById(R.id.layout_view_rtc_ownerName);

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
                String url = "http://202.138.101.171/service18";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        view_mojini_req_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://202.138.101.171/service19/report/Allotdetails";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        view_land_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://landrecords.karnataka.gov.in/service99/";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
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
                // finish();
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

}
