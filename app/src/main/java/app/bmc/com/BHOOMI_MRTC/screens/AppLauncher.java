package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper.MIGRATION_1_2;
import static app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper.MIGRATION_2_3;

public class AppLauncher extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    Date date;
    SimpleDateFormat df;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lancher);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        date = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        currentDate = df.format(date);
        Log.d("currentDate", ""+currentDate);

        dataBaseHelper = Room.databaseBuilder(AppLauncher.this, DataBaseHelper.class, getString(R.string.db_name))
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();

        Observable<Integer> noOfRows;
        noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumberOfRows());
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
                            List<MST_VLM> mst_vlmList = loadDataFromCsv();
                            createMasterData(mst_vlmList, currentDate);
                        }
                        else {
                            verifyUPDDate(currentDate);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.d("1st", e.getLocalizedMessage()+"");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public List<MST_VLM> loadDataFromCsv() {
        List<MST_VLM> mst_vlms = new ArrayList<>();
        BufferedReader reader = null;

        try {
            String mLine;


            reader = new BufferedReader(
                    new InputStreamReader(getApplicationContext().getAssets().open("MasterData.csv"), StandardCharsets.UTF_8));

            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i > 0) {
                    String[] data = mLine.split(",");
                    MST_VLM mst_vlm = new MST_VLM();
                    mst_vlm.setVLM_ID(Integer.parseInt(data[0]));
                    mst_vlm.setVLM_DST_ID(Integer.parseInt(data[1]));
                    mst_vlm.setVLM_TLK_ID(Integer.parseInt(data[2]));
                    mst_vlm.setVLM_HBL_ID(Integer.parseInt(data[3]));
                    mst_vlm.setVLM_CIR_ID(data[4]);
                    mst_vlm.setVLM_VLG_ID(Integer.parseInt(data[5]));
                    mst_vlm.setVLM_DKN_NM(data[6]);
                    mst_vlm.setVLM_DST_NM(data[7]);
                    mst_vlm.setVLM_TLK_NM(data[8]);
                    mst_vlm.setVLM_TKN_NM(data[9]);
                    mst_vlm.setVLM_HKN_NM(data[10]);
                    mst_vlm.setVLM_HBL_NM(data[11]);
                    mst_vlm.setVLM_CIR_NM(data[12]);
                    mst_vlm.setVLM_CKN_NM(data[13]);
                    mst_vlm.setVLM_VKN_NM(data[15]);
                    mst_vlm.setVLM_VLG_NM(data[16]);
                    mst_vlm.setVLM_TUNIT("");
                    mst_vlm.setVLM_FURBAN("");
                    mst_vlms.add(mst_vlm);


                }
                i++;
            }
            //  addMstVlmItems(mst_vlms);
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
        return mst_vlms;
    }

    public void verifyUPDDate(String currentDate){
        Observable<Integer> getCountUpdatedTbl;
        getCountUpdatedTbl = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getCountOfUpdDate());
        getCountUpdatedTbl
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if(integer == 0){
                            createUPDATE_DateTbl(currentDate, true);
                        } else {
                            checkUPD_Date(currentDate);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createUPDATE_DateTbl(String str_date, boolean clearData){
        Observable<Long> insertUpdDateTbl = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertUpdatedDate(1, str_date));
        insertUpdDateTbl
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long longs) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        new Handler().postDelayed(() -> {
                            Intent intent = new Intent(AppLauncher.this, BhoomiHomePage.class);
                            intent.putExtra("clearData", clearData);
                            startActivity(intent);
                            finish();
                        }, 1000);
                    }
                });
    }

    public void checkUPD_Date(String curDate){
        Observable<String> insertUpdDateTbl = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getUPD_DATE());
        insertUpdDateTbl
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s!=null){
//                            try {
//                                Date currentDate = df.parse(curDate);
//                                Log.d("CurrentDate", ""+currentDate);
//                                Date DBDate = df.parse(s);
//                                Log.d("DBDate", ""+DBDate);
//                                assert currentDate != null;
//                                boolean compareDate = currentDate.after(DBDate);
//                                Log.d("compareDate", ""+compareDate);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                            boolean compareDateString = curDate.equals(s);
                            Log.d("compareDateString", ""+compareDateString);
                            if (compareDateString){
                                new Handler().postDelayed(() -> {
                                    Intent intent = new Intent(AppLauncher.this, BhoomiHomePage.class);
                                    intent.putExtra("clearData", false);
                                    startActivity(intent);
                                    finish();
                                }, 1000);
                            } else {
                                deleteUpdDateTable(curDate);
                            }
                        } else {
                            deleteUpdDateTable(curDate);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteUpdDateTable(String currentDate){
        Log.d("deleteUpdDateTable", "Deleted");
        Observable<Integer> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteUpdDateTable());
        insertMasterObservable
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

                    }

                    @Override
                    public void onComplete() {
                        createUPDATE_DateTbl(currentDate, true);
                    }
                });
    }

    public void createMasterData(final List<MST_VLM> mst_vlmList, String currentDate) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMasterData(mst_vlmList));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.d("Status_AppLau", "enter3 CreatedMasterData");
                        verifyUPDDate(currentDate);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.d("2nd", e.getLocalizedMessage()+"");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
