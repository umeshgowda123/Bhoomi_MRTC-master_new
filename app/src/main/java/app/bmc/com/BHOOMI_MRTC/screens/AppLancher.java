package app.bmc.com.BHOOMI_MRTC.screens;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppLancher extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lancher);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
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
                            createMasterData(mst_vlmList);
                        } else {
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(AppLancher.this, BhoomiHomePage.class);
                                startActivity(intent);
                                finish();
                            }, 1000);

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
                    mst_vlm.setVLM_CIR_ID(Integer.parseInt(data[4]));
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

    public void createMasterData(final List<MST_VLM> mst_vlmList) {
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
                        Intent intent = new Intent(AppLancher.this, BhoomiHomePage.class);
                        startActivity(intent);
                        finish();
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
