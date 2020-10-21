package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.api.PariharaIndividualReportInteface;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.model.MST_VLM;
import app.bmc.com.BHOOMI_MRTC.model.Maintenance_Flags;
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

public class AppLauncher extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    PariharaIndividualReportInteface apiInterface;
    List<Maintenance_Flags> maintenance_flagsList;

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
        Log.d("Status_AppLau", "enter1");

        dataBaseHelper = Room.databaseBuilder(AppLauncher.this,
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
                            Log.d("Status_AppLau", "enter2 Integer==0");
                        }
                        else {
                            Log.d("Status_AppLau", "enter2 Integer>0");
                            createMaintenanceTbl();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

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
                        Log.d("Status_AppLau", "enter3 CreatedMasterData");
                        createMaintenanceTbl();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createMaintenanceTbl(){
        Log.d("Status_AppLau", "enter4 CreateMaintenanceTbl");
        if (isNetworkAvailable()){
            apiInterface = PariharaIndividualreportClient.getClient(getResources().getString(R.string.server_report_url)).create(PariharaIndividualReportInteface.class);
            Call<PariharaIndividualDetailsResponse> call = apiInterface.FnGetServiceStatus(Constants.REPORT_SERVICE_USER_NAME,
                    Constants.REPORT_SERVICE_PASSWORD);
            call.enqueue(new Callback<PariharaIndividualDetailsResponse>() {
                @Override
                public void onResponse(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Response<PariharaIndividualDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("Status_AppLau", "enter4 Call<>");
                        PariharaIndividualDetailsResponse result = response.body();
                        assert result != null;
                        String res = result.getFnGetServiceStatusResult();
                        if (!TextUtils.isEmpty(res)){
                            try {
                                JSONArray jsonArray = new JSONArray(res);

                                Gson gson = new Gson();
                                maintenance_flagsList = gson.fromJson(String.valueOf(jsonArray), new TypeToken<List<Maintenance_Flags>>() {
                                }.getType());

                                deleteResponseByID(maintenance_flagsList);
                            } catch (JSONException e){
                                Log.d("Status_AppLau", "enter4 catch1");
                                Log.d("Status_AppLau", ""+e.getLocalizedMessage());
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PariharaIndividualDetailsResponse> call, @NonNull Throwable t) {
                    call.cancel();
                    Log.d("Status_AppLau", "enter4 onFailure1");
                    Log.d("Status_AppLau", ""+t.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Observable<Integer> countOfRows;
            countOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getCountMaintenance_Flags());
            countOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {


                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Integer integer) {

                            if (integer == 0) {
                                Log.d("Status_AppLau", "enter4 No Internet Integer==0");
                                AlertDialog alertDialog = new AlertDialog.Builder(AppLauncher.this).create();
                                alertDialog.setMessage(getString(R.string.please_enable_internet_connection));
                                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.ok), (dialog, which) -> {
                                    onBackPressed();
                                    dialog.dismiss();
                                });
                                alertDialog.show();
                            } else {
                                Log.d("Status_AppLau", "enter4 No Internet Integer>0");
                                Toast.makeText(getApplicationContext(), "Internet Connection not available", Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(() -> {
                                    Intent intent = new Intent(AppLauncher.this, BhoomiHomePage.class);
                                    startActivity(intent);
                                    finish();
                                }, 1000);

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            Toast.makeText(getApplicationContext(), "Internet Connection not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertDataMainFlags(List<Maintenance_Flags> maintenance_flagsList){
        Log.d("Status_AppLau", "enter6 insertDataMainFlags");
        Observable<Long[]> insertDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertMaintenance_Flags(maintenance_flagsList));
        insertDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
                        Log.d("Status_AppLau", "enter6 insertedDataMainFlags");
                        Intent intent = new Intent(AppLauncher.this, BhoomiHomePage.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void deleteResponseByID(List<Maintenance_Flags> maintenance_flagsList) {
        Log.d("Status_AppLau", "enter5 deleteResByID");
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteResMainFlags());
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("Status_AppLau", "enter5 deleteResByID");
                        insertDataMainFlags(maintenance_flagsList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
