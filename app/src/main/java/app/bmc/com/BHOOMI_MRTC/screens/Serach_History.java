package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.io.File;
import java.util.List;
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowHistoryDataAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowLandConFinalHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowLandConHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowMPDHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowMSHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowMSVHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowRLandHistoryAdapter;
import app.bmc.com.BHOOMI_MRTC.adapters.ShowRTCXMLAdapter;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.model.DTHVName;
import app.bmc.com.BHOOMI_MRTC.model.LandConFinal_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.LandCon_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.MPD_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.MSV_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.MS_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.RLand_Data_History;
import app.bmc.com.BHOOMI_MRTC.model.RTCV_Data_history;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO_HISTORY;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Serach_History extends AppCompatActivity {

    private ListView historyList, historyList1;
    private LinearLayout l1;
    private TableLayout tableLayout;
    DataBaseHelper dataBaseHelper;
    TextView tvdist, tvtlk, tvhlb, tvvill, tvhissa, tvsurvaynum, tvref, tvrefdata, tvlandConAffID, tvlandCOnUserID;
    TextView tvLandConFinalRequesID;
    TextView nodata;


    private ShowHistoryDataAdapter adapter;
    private ShowRTCXMLAdapter adapter1;
    private ShowMPDHistoryAdapter adapter2;
    private ShowMSHistoryAdapter adapter3;
    private ShowMSVHistoryAdapter adapter4;
    private ShowRLandHistoryAdapter adapter5;
    private ShowLandConHistoryAdapter adapter6;
    private ShowLandConFinalHistoryAdapter adapter7;


    private List<DTHVName> DTHVName_Data;
    private List<VR_INFO_HISTORY> HistoryName;
    private List<MPD_Data_History> MPD_data_history;
    private List<MS_Data_History> MS_data_history;
    private List<MSV_Data_History> MSV_data_history;
    private List<RLand_Data_History> RLand_data_history;
    private List<LandConFinal_Data_History> LandConFinal_data_history;

    int APPType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dataBaseHelper = Room.databaseBuilder(getApplicationContext(),
                DataBaseHelper.class, getString(R.string.db_name)).build();

        historyList = findViewById(R.id.lv_show_history);
        historyList1 = findViewById(R.id.lv_show_history1);
        tableLayout = findViewById(R.id.tbl2);
        l1 = findViewById(R.id.lv_show_details1);


        tvdist = findViewById(R.id.tvdstNAME);
        tvtlk = findViewById(R.id.tvtlkNAME);
        tvhlb = findViewById(R.id.tvhlbNAME);
        tvvill = findViewById(R.id.tvvllNAME);
        tvsurvaynum = findViewById(R.id.tvsryNo);
        tvhissa = findViewById(R.id.tvhissaNo);
        tvref = findViewById(R.id.tvrtcxml_reff);
        tvrefdata = findViewById(R.id.tvRTC_reffData);

        nodata = findViewById(R.id.nodata);

        tvlandConAffID = findViewById(R.id.tvLandConAffID);
        tvlandCOnUserID = findViewById(R.id.tvLandConUserID_1);
        tvLandConFinalRequesID = findViewById(R.id.tvLandConFinalRequesID);


        Intent intent = getIntent();
        APPType = intent.getIntExtra("APPType", APPType);

        if (APPType == 1) {

            Observable<List<VR_INFO_HISTORY>> historydataName = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHNameByName());
            historydataName
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<VR_INFO_HISTORY>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<VR_INFO_HISTORY> historyData) {
                            Log.d("TAG", "onNext: " + historyData.size());

                            if (historyData.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (historyData.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvdist.setVisibility(View.VISIBLE);
                                tvtlk.setVisibility(View.VISIBLE);
                                tvhlb.setVisibility(View.VISIBLE);
                                tvvill.setVisibility(View.VISIBLE);
                                tvsurvaynum.setVisibility(View.VISIBLE);


                                HistoryName = (List<VR_INFO_HISTORY>) historyData;
                                int i;
                                for (i = 0; i < HistoryName.size(); i++) {
                                    int distID = HistoryName.get(i).getVR_DST_ID();
                                    int talukID = HistoryName.get(i).getVR_TLK_ID();
                                    int hobliID = HistoryName.get(i).getVR_HBL_ID();
                                    int villID = HistoryName.get(i).getVR_VLG_ID();

                                    Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID);

                                    Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                    int finalI = i;
                                    historydataobservable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<DTHVName>>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull List<DTHVName> dthvData) {
                                                    Log.d("TAG", "onNext: " + dthvData.size());

                                                    DTHVName_Data = (List<DTHVName>) dthvData;
                                                    for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                        String distName = DTHVName_Data.get(j).getDistName();
                                                        String talName = DTHVName_Data.get(j).getTalukName();
                                                        String HobliName = DTHVName_Data.get(j).getHobliName();
                                                        String VillName = DTHVName_Data.get(j).getVillageName();
                                                        Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                        historyData.get(finalI).setHistory_DistName(distName);
                                                        historyData.get(finalI).setHistory_TalukName(talName);
                                                        historyData.get(finalI).setHistory_HobliName(HobliName);
                                                        historyData.get(finalI).setHistory_VillageName(VillName);
                                                        adapter = new ShowHistoryDataAdapter(historyData, getApplicationContext());
                                                        historyList.setAdapter(adapter);


                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (APPType == 2) {

            Observable<List<RTCV_Data_history>> rtcxmlDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getRTCREFF_RES());
            rtcxmlDataObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<RTCV_Data_history>>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<RTCV_Data_history> rtc_detail) {
                            Log.d("TAG", "onNext: " + rtc_detail.size());

                            if (rtc_detail.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (rtc_detail.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvref.setVisibility(View.VISIBLE);
                                adapter1 = new ShowRTCXMLAdapter(rtc_detail, getApplicationContext());
                                historyList.setAdapter(adapter1);
                                Log.i("select the DATA", " " + historyList);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (APPType == 4) {


            Observable<List<MPD_Data_History>> MPDhistorydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMPD_History());
            MPDhistorydata
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MPD_Data_History>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<MPD_Data_History> MPDhistoryData) {
                            Log.d("TAG", "onNext: " + MPDhistoryData.size());

                            if (MPDhistoryData.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (MPDhistoryData.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvdist.setVisibility(View.VISIBLE);
                                tvtlk.setVisibility(View.VISIBLE);
                                tvhlb.setVisibility(View.VISIBLE);
                                tvvill.setVisibility(View.VISIBLE);
                                historyList.setVisibility(View.VISIBLE);

                                MPD_data_history = (List<MPD_Data_History>) MPDhistoryData;
                                int i;
                                for (i = 0; i < MPD_data_history.size(); i++) {
                                    int distID = MPD_data_history.get(i).getMPD_DST_ID();
                                    int talukID = MPD_data_history.get(i).getMPD_TLK_ID();
                                    int hobliID = MPD_data_history.get(i).getMPD_HBL_ID();
                                    int villID = MPD_data_history.get(i).getMPD_VLG_ID();

                                    Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID);

                                    Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                    int finalI = i;
                                    historydataobservable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<DTHVName>>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull List<DTHVName> dthvData) {
                                                    Log.d("TAG", "onNext: " + dthvData.size());


                                                    DTHVName_Data = (List<DTHVName>) dthvData;
                                                    for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                        String distName = DTHVName_Data.get(j).getDistName();
                                                        String talName = DTHVName_Data.get(j).getTalukName();
                                                        String HobliName = DTHVName_Data.get(j).getHobliName();
                                                        String VillName = DTHVName_Data.get(j).getVillageName();
                                                        Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                        MPDhistoryData.get(finalI).setMPD_DistName(distName);
                                                        MPDhistoryData.get(finalI).setMPD_TalukName(talName);
                                                        MPDhistoryData.get(finalI).setMPD_HobliName(HobliName);
                                                        MPDhistoryData.get(finalI).setMPD_VillageName(VillName);
                                                        adapter2 = new ShowMPDHistoryAdapter(MPDhistoryData, getApplicationContext());
                                                        historyList.setAdapter(adapter2);
                                                    }

                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (APPType == 5) {


            Observable<List<MS_Data_History>> MShistorydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMS_History());
            MShistorydata
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MS_Data_History>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<MS_Data_History> MShistory) {
                            Log.d("TAG", "onNext: " + MShistory.size());

                            if (MShistory.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (MShistory.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvdist.setVisibility(View.VISIBLE);
                                tvtlk.setVisibility(View.VISIBLE);
                                tvhlb.setVisibility(View.VISIBLE);
                                tvvill.setVisibility(View.VISIBLE);
                                tvsurvaynum.setVisibility(View.VISIBLE);

                                MS_data_history = (List<MS_Data_History>) MShistory;
                                int i;
                                for (i = 0; i < MS_data_history.size(); i++) {
                                    int distID = MS_data_history.get(i).getMSR_DST_ID();
                                    int talukID = MS_data_history.get(i).getMSR_TLK_ID();
                                    int hobliID = MS_data_history.get(i).getMSR_HBL_ID();
                                    int villID = MS_data_history.get(i).getMSR_VLG_ID();
                                    String survayNo = MS_data_history.get(i).getMSR_SNO();

                                    Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID + ", " + survayNo);

                                    Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                    int finalI = i;
                                    historydataobservable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<DTHVName>>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull List<DTHVName> dthvData) {
                                                    Log.d("TAG", "onNext: " + dthvData.size());


                                                    DTHVName_Data = (List<DTHVName>) dthvData;
                                                    for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                        String distName = DTHVName_Data.get(j).getDistName();
                                                        String talName = DTHVName_Data.get(j).getTalukName();
                                                        String HobliName = DTHVName_Data.get(j).getHobliName();
                                                        String VillName = DTHVName_Data.get(j).getVillageName();
                                                        Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                        MShistory.get(finalI).setMS_DistName(distName);
                                                        MShistory.get(finalI).setMS_TalukName(talName);
                                                        MShistory.get(finalI).setMS_HobliName(HobliName);
                                                        MShistory.get(finalI).setMS_VillageName(VillName);
                                                        MShistory.get(finalI).setMSR_SNO(survayNo);
                                                        adapter3 = new ShowMSHistoryAdapter(MShistory, getApplicationContext());
                                                        historyList.setAdapter(adapter3);

                                                    }
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (APPType == 6) {

            Observable<List<MSV_Data_History>> MSVhistorydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getMSV_History());
            MSVhistorydata
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MSV_Data_History>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<MSV_Data_History> MSVhistory) {
                            Log.d("TAG", "onNext: " + MSVhistory.size());

                            if (MSVhistory.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (MSVhistory.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvdist.setVisibility(View.VISIBLE);
                                tvtlk.setVisibility(View.VISIBLE);
                                tvhlb.setVisibility(View.VISIBLE);
                                tvvill.setVisibility(View.VISIBLE);
                                tvsurvaynum.setVisibility(View.VISIBLE);
                            }

                            MSV_data_history = (List<MSV_Data_History>) MSVhistory;
                            int i;
                            for (i = 0; i < MSV_data_history.size(); i++) {
                                int distID = MSV_data_history.get(i).getVMS_DST_ID();
                                int talukID = MSV_data_history.get(i).getVMS_TLK_ID();
                                int hobliID = MSV_data_history.get(i).getVMS_HBL_ID();
                                int villID = MSV_data_history.get(i).getVMS_VLG_ID();
                                String hissa = MSV_data_history.get(i).getVMS_HISSA();
                                String survayNo = MSV_data_history.get(i).getVMS_SURVEY_NO();
                                String sarnoc = MSV_data_history.get(i).getVMS_SERNOC();


                                Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID + ", " + survayNo + ", " + hissa);

                                Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                int finalI = i;
                                historydataobservable
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<DTHVName>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull List<DTHVName> dthvData) {
                                                Log.d("TAG", "onNext: " + dthvData.size());
                                                DTHVName_Data = (List<DTHVName>) dthvData;


                                                for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                    String distName = DTHVName_Data.get(j).getDistName();
                                                    String talName = DTHVName_Data.get(j).getTalukName();
                                                    String HobliName = DTHVName_Data.get(j).getHobliName();
                                                    String VillName = DTHVName_Data.get(j).getVillageName();
                                                    Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                    MSVhistory.get(finalI).setMSV_DistName(distName);
                                                    MSVhistory.get(finalI).setMSV_TalukName(talName);
                                                    MSVhistory.get(finalI).setMSV_HobliName(HobliName);
                                                    MSVhistory.get(finalI).setMSV_VillageName(VillName);
                                                    MSVhistory.get(finalI).setVMS_SURVEY_NO(survayNo);
                                                    MSVhistory.get(finalI).setVMS_HISSA(hissa);
                                                    MSVhistory.get(finalI).setVMS_SERNOC(sarnoc);
                                                    adapter4 = new ShowMSVHistoryAdapter(MSVhistory, getApplicationContext());
                                                    historyList.setAdapter(adapter4);
                                                }
                                            }


                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else if (APPType == 7) {


            Observable<List<RLand_Data_History>> RLandhistorydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getRLand_History());
            RLandhistorydata
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<RLand_Data_History>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<RLand_Data_History> RLandhistory) {
                            Log.d("TAG", "onNext: " + RLandhistory.size());

                            if (RLandhistory.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (RLandhistory.size() > 0) {

                                nodata.setVisibility(View.GONE);
                                tvdist.setVisibility(View.VISIBLE);
                                tvtlk.setVisibility(View.VISIBLE);
                                tvhlb.setVisibility(View.VISIBLE);
                                tvvill.setVisibility(View.VISIBLE);
                                tvsurvaynum.setVisibility(View.VISIBLE);


                                RLand_data_history = (List<RLand_Data_History>) RLandhistory;
                                int i;
                                for (i = 0; i < RLand_data_history.size(); i++) {
                                    int distID = RLand_data_history.get(i).getRLR_DST_ID();
                                    int talukID = RLand_data_history.get(i).getRLR_TLK_ID();
                                    int hobliID = RLand_data_history.get(i).getRLR_HBL_ID();
                                    int villID = RLand_data_history.get(i).getRLR_VLG_ID();


                                    Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID);

                                    Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                    int finalI = i;
                                    historydataobservable
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<DTHVName>>() {
                                                @Override
                                                public void onSubscribe(@NonNull Disposable d) {

                                                }

                                                @Override
                                                public void onNext(@NonNull List<DTHVName> dthvData) {
                                                    Log.d("TAG", "onNext: " + dthvData.size());
                                                    DTHVName_Data = (List<DTHVName>) dthvData;


                                                    for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                        String distName = DTHVName_Data.get(j).getDistName();
                                                        String talName = DTHVName_Data.get(j).getTalukName();
                                                        String HobliName = DTHVName_Data.get(j).getHobliName();
                                                        String VillName = DTHVName_Data.get(j).getVillageName();
                                                        Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                        RLandhistory.get(finalI).setRLand_DistName(distName);
                                                        RLandhistory.get(finalI).setRLand_TalukName(talName);
                                                        RLandhistory.get(finalI).setRLand_HobliName(HobliName);
                                                        RLandhistory.get(finalI).setRLand_VillageName(VillName);
                                                        adapter5 = new ShowRLandHistoryAdapter(RLandhistory, getApplicationContext());
                                                        historyList.setAdapter(adapter5);
                                                    }
                                                }


                                                @Override
                                                public void onError(@NonNull Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (APPType == 8) {

            Observable<List<LandCon_Data_History>> tablesize_landCon = Observable.fromCallable(() -> dataBaseHelper.daoAccess().gettablesize_landConverion());
            tablesize_landCon
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<LandCon_Data_History>>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<LandCon_Data_History> table_size) {
                            Log.d("TAG", "onNext: " + table_size.size());

                            if (table_size.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (table_size.size() > 0) {

                                Observable<List<LandCon_Data_History>> LandCon_historydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getLandCon_Aff_History());
                                LandCon_historydata
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<LandCon_Data_History>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull List<LandCon_Data_History> LandCon_history) {
                                                Log.d("TAG", "onNext: " + LandCon_history.size());


                                                if (LandCon_history.size()<0){
                                                    tableLayout.setVisibility(View.GONE);
                                                    tvlandCOnUserID.setVisibility(View.GONE);
                                                    historyList1.setVisibility(View.GONE);
                                                    nodata.setVisibility(View.GONE);
                                                } else if (LandCon_history.size()>0){
                                                    l1.setVisibility(View.VISIBLE);
                                                    tvlandConAffID.setVisibility(View.VISIBLE);

                                                    adapter6 = new ShowLandConHistoryAdapter(LandCon_history, getApplicationContext());
                                                    historyList.setAdapter(adapter6);
                                                    Log.i("select the DATA", " " + historyList);

                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                                Observable<List<LandCon_Data_History>> LandConUser_historydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getLandCon_user_History());
                                LandConUser_historydata
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<LandCon_Data_History>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull List<LandCon_Data_History> LandCon_history) {
                                                Log.d("TAG", "onNext: " + LandCon_history.size());


                                                if (LandCon_history.size()<0){
                                                    tvlandConAffID.setVisibility(View.GONE);
                                                    nodata.setVisibility(View.GONE);
                                                } else if (LandCon_history.size()>0){
                                                    l1.setVisibility(View.VISIBLE);
                                                    tableLayout.setVisibility(View.VISIBLE);
                                                    tvlandCOnUserID.setVisibility(View.VISIBLE);
                                                    historyList1.setVisibility(View.VISIBLE);

                                                    adapter6 = new ShowLandConHistoryAdapter(LandCon_history, getApplicationContext());
                                                    historyList1.setAdapter(adapter6);
                                                    Log.i("select the DATA", " " + historyList1);
                                                }



                                            }
                                            //}

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });


                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (APPType == 9) {


            Observable<List<LandConFinal_Data_History>> tablesize = Observable.fromCallable(() -> dataBaseHelper.daoAccess().gettablesize());
            tablesize
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<LandConFinal_Data_History>>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<LandConFinal_Data_History> table_sise) {
                            Log.d("TAG", "onNext: " + table_sise.size());

                            if (table_sise.size() <= 0) {
                                nodata.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                historyList.setVisibility(View.GONE);

                            } else if (table_sise.size() > 0) {

                                Observable<List<LandConFinal_Data_History>> LandConFinal_historydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getLandConFinal_survayNum_History());
                                LandConFinal_historydata
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<LandConFinal_Data_History>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull List<LandConFinal_Data_History> LandConFinal_history) {
                                                Log.d("TAG", "onNext: " + LandConFinal_history.size());

                                                if (LandConFinal_history.size() <= 0) {
                                                    l1.setVisibility(View.GONE);
                                                    historyList1.setVisibility(View.GONE);
                                                    tvLandConFinalRequesID.setVisibility(View.GONE);
                                                    nodata.setVisibility(View.GONE);
                                                } else if (LandConFinal_history.size() > 0) {

                                                    tvdist.setVisibility(View.VISIBLE);
                                                    tvtlk.setVisibility(View.VISIBLE);
                                                    tvhlb.setVisibility(View.VISIBLE);
                                                    tvvill.setVisibility(View.VISIBLE);
                                                    tvsurvaynum.setVisibility(View.VISIBLE);

                                                LandConFinal_data_history = (List<LandConFinal_Data_History>) LandConFinal_history;
                                                int i;
                                                for (i = 0; i < LandConFinal_data_history.size(); i++) {
                                                    int distID = LandConFinal_data_history.get(i).getDST_ID();
                                                    int talukID = LandConFinal_data_history.get(i).getTLK_ID();
                                                    int hobliID = LandConFinal_data_history.get(i).getHBL_ID();
                                                    int villID = LandConFinal_data_history.get(i).getVLG_ID();
                                                    String survayNo = LandConFinal_data_history.get(i).getS_NO();

                                                    String SNO_RES = LandConFinal_data_history.get(i).getSNO_RES();

                                                    Log.d("history_name", "" + distID + ", " + talukID + ", " + hobliID + ", " + villID);

                                                    Observable<List<DTHVName>> historydataobservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getDTHVNamesByCodes(distID, talukID, hobliID, villID));
                                                    int finalI = i;
                                                    historydataobservable
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Observer<List<DTHVName>>() {
                                                                @Override
                                                                public void onSubscribe(@NonNull Disposable d) {

                                                                }

                                                                @Override
                                                                public void onNext(@NonNull List<DTHVName> dthvData) {
                                                                    Log.d("TAG", "onNext: " + dthvData.size());


                                                                    DTHVName_Data = (List<DTHVName>) dthvData;
                                                                    if (dthvData.size() > 0) {
                                                                        for (int j = 0; j < DTHVName_Data.size(); j++) {
                                                                            String distName = DTHVName_Data.get(j).getDistName();
                                                                            String talName = DTHVName_Data.get(j).getTalukName();
                                                                            String HobliName = DTHVName_Data.get(j).getHobliName();
                                                                            String VillName = DTHVName_Data.get(j).getVillageName();
                                                                            Log.d("Val", "" + distName + ", " + talName + ", " + HobliName + ", " + VillName);

                                                                            LandConFinal_data_history.get(finalI).setLandFinal_DistName(distName);
                                                                            LandConFinal_data_history.get(finalI).setLandFinal_TalukName(talName);
                                                                            LandConFinal_data_history.get(finalI).setLandFinal_HobliName(HobliName);
                                                                            LandConFinal_data_history.get(finalI).setLandFinal_VillageName(VillName);
                                                                            LandConFinal_data_history.get(finalI).setS_NO(survayNo);

                                                                            LandConFinal_data_history.get(finalI).setSNO_RES(SNO_RES);

                                                                            adapter7 = new ShowLandConFinalHistoryAdapter(LandConFinal_data_history, getApplicationContext());
                                                                            historyList.setAdapter(adapter7);
                                                                        }
                                                                    }
                                                                }


                                                                @Override
                                                                public void onError(@NonNull Throwable e) {

                                                                }

                                                                @Override
                                                                public void onComplete() {

                                                                }
                                                            });
                                                }
                                            }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                                Observable<List<LandConFinal_Data_History>> LandConFinalRes_historydata = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getLandConFinal_RequestId_History());
                                LandConFinalRes_historydata
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<LandConFinal_Data_History>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull List<LandConFinal_Data_History> LandConFinal_history) {
                                                Log.d("TAG", "onNext: " + LandConFinal_history.size());

                                                if (LandConFinal_history.size() <= 0) {
                                                    tvdist.setVisibility(View.GONE);
                                                    tvtlk.setVisibility(View.GONE);
                                                    tvhlb.setVisibility(View.GONE);
                                                    tvvill.setVisibility(View.GONE);
                                                    tvsurvaynum.setVisibility(View.GONE);
                                                    nodata.setVisibility(View.GONE);
                                                    historyList.setVisibility(View.GONE);
                                                } else if (LandConFinal_history.size() > 0) {

                                                    l1.setVisibility(View.VISIBLE);
                                                    historyList1.setVisibility(View.VISIBLE);
                                                    tvLandConFinalRequesID.setVisibility(View.VISIBLE);

                                                    LandConFinal_data_history = (List<LandConFinal_Data_History>) LandConFinal_history;
                                                    int i;

                                                    for (i = 0; i < LandConFinal_data_history.size(); i++) {


                                                        String REQUEST_ID = LandConFinal_data_history.get(i).getREQUEST_ID();
                                                        String REQUEST_ID_RES = LandConFinal_data_history.get(i).getREQUEST_ID_RES();
                                                        LandConFinal_data_history.get(i).setREQUEST_ID(REQUEST_ID);
                                                        LandConFinal_data_history.get(i).setREQUEST_ID_RES(REQUEST_ID_RES);

                                                        adapter7 = new ShowLandConFinalHistoryAdapter(LandConFinal_data_history, getApplicationContext());
                                                        historyList1.setAdapter(adapter7);
                                                        Log.i("select the DATA", " " + historyList1);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });


                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}