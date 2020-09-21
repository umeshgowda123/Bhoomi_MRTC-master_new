package app.bmc.com.BHOOMI_MRTC.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.fragments.CultivatorDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.LandDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.OwnerDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.interfaces.VR_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.model.Staticinfopahani;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO;
import app.bmc.com.BHOOMI_MRTC.model.Villagedetails;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class  for reprajents UI and RTc Detaisl service applications
 */
public class RtcDetails extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo{


    private Villagedetails villagedetails = new Villagedetails();
    private Staticinfopahani staticinfopahani = new Staticinfopahani();
    RtcViewInfoBackGroundTaskFragment mTaskFragment;
    private ProgressBar progressBar;
    String distId, talkId, hblId, villId;
    String land_no, survey, RTC, hissa_str, surveyNo;
    public String owner;

    String LandDetailsFragment;
    String survey_new;
    String OwnerDetailsFragment;
    String CultivatorDetailsFragment;


    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DataBaseHelper dataBaseHelper;
    String formattedLand,formattedCult;

    private List<VR_RES_Interface> VR_RES_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


        Intent i = getIntent();
        distId = i.getStringExtra("distId");
        talkId = i.getStringExtra("talkId");
        hblId = i.getStringExtra("hblId");
        villId = i.getStringExtra("villId");
        land_no = i.getStringExtra("land_code");
        survey = i.getStringExtra("survey");
        surveyNo = i.getStringExtra("surveyNo");
        hissa_str = i.getStringExtra("hissa_str");
        RTC = i.getStringExtra("RTC");

        FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcViewInfoBackGroundTaskFragment) fm.findFragmentByTag(RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcViewInfoBackGroundTaskFragment();
            fm.beginTransaction().add(mTaskFragment, RtcViewInfoBackGroundTaskFragment.TAG_HEADLESS_FRAGMENT).commit();
        }
        progressBar = findViewById(R.id.progress_circular);
        if (mTaskFragment.isTaskExecuting) {
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

        if (RTC.equals("RTC")) {
            if (distId != null && talkId != null && hblId != null && villId != null && land_no != null) {
                if (isNetworkAvailable()) {
                    dataBaseHelper =
                            Room.databaseBuilder(getApplicationContext(),
                                    DataBaseHelper.class, getString(R.string.db_name)).build();
                    Observable<List<? extends VR_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getVR_RES(distId,talkId,hblId,villId,surveyNo,hissa_str));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends VR_RES_Interface>>() {

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<? extends VR_RES_Interface> vrdistidList) {

                                    Log.d("vrdistidList",vrdistidList+"");

                                    VR_RES_Data = (List<VR_RES_Interface>) vrdistidList;
                                    if (vrdistidList.size()!=0) {
                                        Log.d("CHECK","Fetching from local");
                                        for (int i = 0; i <= vrdistidList.size(); i++) {

                                            String LansOwner = VR_RES_Data.get(0).getVR_LAND_OWNER_RES();
                                            String Cult = VR_RES_Data.get(0).getVR_CULT_RES();

                                            Log.d("LandOwner", LansOwner);
                                            Log.d("Cult", Cult);
                                            try {
                                                Object object = new Object();

                                                JSONObject obj = new JSONObject(LansOwner);
                                                JSONObject rtc = obj.getJSONObject("rtc");
                                                Log.d("rtc", rtc+"");

                                                JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");

                                                object = ownerdetails.get("jointowners");

                                                JSONArray jointOwners = new JSONArray();
                                                Log.d("jointOwners",jointOwners+"");

                                                if (object instanceof JSONObject) {
                                                    jointOwners.put(object);

                                                } else {
                                                    jointOwners = (JSONArray) object;

                                                }
                                                LandDetailsFragment = rtc.toString();
                                                Log.d("LandDetailsFragment", LandDetailsFragment+"");
                                                Gson gson = new Gson();
                                                JSONObject object1 = new JSONObject(LandDetailsFragment);
                                                if (object1.has("villagedetails")) {
                                                    Object villagedetailsObject = object1.get("villagedetails");
                                                    if (villagedetailsObject instanceof JSONObject) {
                                                        JSONObject villageJsonObj = (JSONObject) villagedetailsObject;
                                                        villagedetails = gson.fromJson(villageJsonObj.toString(), Villagedetails.class);
                                                    } else if (villagedetailsObject instanceof String) {
                                                        villagedetails = new Villagedetails();
                                                    }
                                                }
                                                if (object1.has("staticinfopahani")) {
                                                    Object staticinfopahaniObject = object1.get("staticinfopahani");
                                                    if (staticinfopahaniObject instanceof JSONObject) {
                                                        JSONObject staticinfopahaniObj = (JSONObject) staticinfopahaniObject;
                                                        staticinfopahani = gson.fromJson(staticinfopahaniObj.toString(), Staticinfopahani.class);

                                                    } else if (staticinfopahaniObject instanceof String) {
                                                        staticinfopahani = new Staticinfopahani();
                                                    }
                                                }

                                                JSONObject ObjectCult = new JSONObject(Cult);
                                                JSONObject rtcCult = ObjectCult.getJSONObject("rtc");
                                                Log.d("rtcCult", rtcCult+"");

                                                JSONObject cultivatordetails = rtcCult.getJSONObject("cultivatordetails");
                                                Log.d("cultivatordetails", cultivatordetails+"");

                                                CultivatorDetailsFragment = cultivatordetails.toString();
                                                survey_new = survey;
                                                OwnerDetailsFragment = jointOwners.toString();
                                                viewPager = findViewById(R.id.viewPager);
                                                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                                                viewPager.setAdapter(viewPagerAdapter);
                                                tabLayout = findViewById(R.id.tabs);
                                                tabLayout.setupWithViewPager(viewPager);
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    else {
                                        progressBar.setVisibility(View.VISIBLE);
                                        mTaskFragment.startBackgroundTask2(distId, talkId, hblId, villId, land_no, getString(R.string.rtc_view_info_url));
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else
                    Toast.makeText(getApplicationContext(), "Internet not available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong Please Try Again", Toast.LENGTH_LONG).show();
            }
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPreExecute1() {

    }

    @Override
    public void onPostResponseSuccess1(String data) {

    }

    @Override
    public void onPostResponseError(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
//        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
        mTaskFragment.startBackgroundTask2(distId, talkId, hblId, villId, land_no, getString(R.string.rtc_view_info_url_parihara));

    }

    @Override
    public void onPreExecute2() {
        if (progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPostResponseSuccess2(String data) {
        if (progressBar!=null)
            progressBar.setVisibility(View.GONE);
        XmlToJson xmlToJson = new XmlToJson.Builder(data.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
        // convert to a JSONObject
        JSONObject jsonObject = xmlToJson.toJson();
        Object object = new Object();

        // convert to a formatted Json String
        formattedLand = xmlToJson.toFormattedString().replace("\n", "");
        try {
            JSONObject obj = new JSONObject(formattedLand);
            JSONObject rtc = obj.getJSONObject("rtc");
            Log.d("rtc", rtc+"");

            JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");

            object = ownerdetails.get("jointowners");

            JSONArray jointOwners = new JSONArray();
            Log.d("jointOwners",jointOwners+"");

            if (object instanceof JSONObject) {
                jointOwners.put(object);

            } else {
                jointOwners = (JSONArray) object;

            }
            LandDetailsFragment = rtc.toString();
            Log.d("LandDetailsFragment", LandDetailsFragment+"");
            Gson gson = new Gson();
            JSONObject object1 = new JSONObject(LandDetailsFragment);
            if (object1.has("villagedetails")) {
                Object villagedetailsObject = object1.get("villagedetails");
                if (villagedetailsObject instanceof JSONObject) {
                    JSONObject villageJsonObj = (JSONObject) villagedetailsObject;
                    villagedetails = gson.fromJson(villageJsonObj.toString(), Villagedetails.class);
                } else if (villagedetailsObject instanceof String) {
                    villagedetails = new Villagedetails();
                }
            }
            if (object1.has("staticinfopahani")) {
                Object staticinfopahaniObject = object1.get("staticinfopahani");
                if (staticinfopahaniObject instanceof JSONObject) {
                    JSONObject staticinfopahaniObj = (JSONObject) staticinfopahaniObject;
                    staticinfopahani = gson.fromJson(staticinfopahaniObj.toString(), Staticinfopahani.class);

                } else if (staticinfopahaniObject instanceof String) {
                    staticinfopahani = new Staticinfopahani();
                }
            }
            mTaskFragment.startBackgroundTaskCultivatorData(distId, talkId, hblId, villId, land_no, getString(R.string.rtc_view_info_url));

            survey_new = survey;
            OwnerDetailsFragment = jointOwners.toString();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreExecute3() {

    }

    @Override
    public void onPostResponseSuccess3(String data) {

    }

    @Override
    public void onPostResponseSuccess4(String data) {

    }

    @Override
    public void onPreExecute4() {

    }

    @Override
    public void onPostResponseErrorCultivator(String errorResponse) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
//        Toast.makeText(getApplicationContext(), errorResponse, Toast.LENGTH_LONG).show();
        mTaskFragment.startBackgroundTaskCultivatorData(distId, talkId, hblId, villId, land_no, getString(R.string.rtc_view_info_url_parihara));

    }

    @SuppressLint("CheckResult")
    @Override
    public void onPostResponseSuccessCultivator(String gettcDataResult) {
        if (progressBar!=null)
            progressBar.setVisibility(View.GONE);
        XmlToJson xmlToJson = new XmlToJson.Builder(gettcDataResult.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
        // convert to a JSONObject
        JSONObject jsonObject = xmlToJson.toJson();
        Object object = new Object();

        // convert to a formatted Json String
        formattedCult = xmlToJson.toFormattedString().replace("\n", "");
        Log.d("formattedCult : ",""+formattedCult);
        try {
                JSONObject obj = new JSONObject(formattedCult);
                JSONObject rtc = obj.getJSONObject("rtc");
                Log.d("rtc", rtc+"");

            JSONObject cultivatordetails = rtc.getJSONObject("cultivatordetails");
            Log.d("cultivatordetails", cultivatordetails+"");

            CultivatorDetailsFragment = cultivatordetails.toString();

            viewPager = findViewById(R.id.viewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

            //---------DB INSERT-------

            dataBaseHelper =
                    Room.databaseBuilder(getApplicationContext(),
                            DataBaseHelper.class, getString(R.string.db_name)).build();
            Observable<Integer> noOfRows;
            noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRows());
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {


                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            Log.d("intValue",integer+"");

                            if (integer < 6) {
                                Log.d("intValueIN",integer+"");
                                List<VR_INFO> vr_info_List = loadData();
                                createVRTCData(vr_info_List);
                            } else {
                                Log.d("intValueELSE",integer+"");
                                deleteByID(0);
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


            //---------------------------------------------------------------------------------------------


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
       /* outState.putString(GAME_STATE_KEY, mGameState);
        outState.putString(TEXT_VIEW_KEY, mTextView.getText());
*/
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //  mTextView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                String data = LandDetailsFragment;
                fragment = new LandDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LandDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", survey_new);
                bundle.putString("Valid", staticinfopahani.getValidfrom().replace("Valid from", ""));
                fragment.setArguments(bundle);

            } else if (position == 1) {
                String data = OwnerDetailsFragment;

                fragment = new OwnerDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("OwnerDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", survey_new);
                bundle.putString("Valid", staticinfopahani.getValidfrom().replace("Valid from", ""));

                fragment.setArguments(bundle);
            }
            else if (position == 2) {
                String data = CultivatorDetailsFragment;
                Log.d("CultivatorDetails",CultivatorDetailsFragment+"");
                fragment = new CultivatorDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CultivatorDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", survey_new);
                bundle.putString("Valid", staticinfopahani.getValidfrom().replace("Valid from", ""));

                fragment.setArguments(bundle);
            }

            assert fragment != null;
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = getString(R.string.land_details);
            } else if (position == 1) {
                title = getString(R.string.owner_details);
            }
            else if (position == 2) {
                title = getString(R.string.cultivator_details);
            }

            return title;
        }
    }

    //______________________________________________________________________DB____________________________________________________

    public List<VR_INFO> loadData() {
//        Toast.makeText(this, "LoadData", Toast.LENGTH_SHORT).show();
        List<VR_INFO> vr_info_arr = new ArrayList<>();
        try {
            VR_INFO vr_info = new VR_INFO();
            vr_info.setVR_DST_ID(Integer.parseInt(distId));
            vr_info.setVR_TLK_ID(Integer.parseInt(talkId));
            vr_info.setVR_HBL_ID(Integer.parseInt(hblId));
            vr_info.setVR_VLG_ID(Integer.parseInt(villId));
            vr_info.setVR_LAND_NO(Integer.parseInt(land_no));
            vr_info.setVR_SNO(surveyNo);
            vr_info.setVR_HISSA_NM(hissa_str);
            vr_info.setVR_LAND_OWNER_RES(formattedLand);
            vr_info.setVR_CULT_RES(formattedCult);
            vr_info_arr.add(vr_info);

        } catch (Exception e) {
            Log.d("Exception", e + "");
        }
//        vr_info.setVR_DST_ID(1);
//        vr_info.setVR_TLK_ID(3);
//        vr_info.setVR_HBL_ID(4);
//        vr_info.setVR_VLG_ID(5);
//        vr_info.setVR_LAND_NO(1);
//        vr_info.setVR_SNO("land_no");
//        vr_info.setVR_HISSA_NM("survey");
//        vr_info.setVR_LAND_OWNER_RES("formattedLand");
//        vr_info.setVR_CULT_RES("formattedCult");
//        vr_info_arr.add(vr_info);

        return vr_info_arr;
    }


        public void createVRTCData(final List<VR_INFO> vr_info_List) {
        Observable<Long[]> insertMasterObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().insertViewRTCInfoData(vr_info_List));
        insertMasterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long[]>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long[] longs) {
//                        Intent intent = new Intent(ViewRtcInformation.this, BhoomiHomePage.class);
//                        startActivity(intent);
//                        finish();
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

    private void deleteByID(final int id) {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
        Observable<Integer> noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().deleteById(id));
        noOfRows
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        Log.i("delete", integer + "");
                        deleteResponseByID();

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

    private void deleteResponseByID() {

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();
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

                        Log.i("delete", integer + "");


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
