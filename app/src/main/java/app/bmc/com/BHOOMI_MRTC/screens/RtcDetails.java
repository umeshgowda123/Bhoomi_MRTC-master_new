package app.bmc.com.BHOOMI_MRTC.screens;


import android.app.AlertDialog;
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
import java.util.Objects;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.database.DataBaseHelper;
import app.bmc.com.BHOOMI_MRTC.fragments.CultivatorDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.LandDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.OwnerDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.interfaces.VR_RES_Interface;
import app.bmc.com.BHOOMI_MRTC.model.ClsAppLgs;
import app.bmc.com.BHOOMI_MRTC.model.ClsReqLandID;
import app.bmc.com.BHOOMI_MRTC.model.Staticinfopahani;
import app.bmc.com.BHOOMI_MRTC.model.VR_INFO;
import app.bmc.com.BHOOMI_MRTC.model.Villagedetails;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RtcDetails extends AppCompatActivity implements RtcViewInfoBackGroundTaskFragment.BackgroundCallBackRtcViewInfo{


    private Villagedetails villagedetails = new Villagedetails();
    private Staticinfopahani staticinfopahani = new Staticinfopahani();
    RtcViewInfoBackGroundTaskFragment mTaskFragment;
    private ProgressBar progressBar;
    String distId, talkId, hblId, villId;
    String land_no, survey, RTC, hissa_str, surveyNo,surnoc;
    public String owner;



    String LandDetailsFragment;
    String survey_new;
    String OwnerDetailsFragment;
    String CultivatorDetailsFragment;
    String accessToken, tokenType;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DataBaseHelper dataBaseHelper;
    String formattedLand,formattedCult;

    private List<VR_RES_Interface> VR_RES_Data;

//    String input;
    ClsReqLandID clsReqLandID;

    int AppType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        surnoc = i.getStringExtra("surnoc");
        hissa_str = i.getStringExtra("hissa_str");
        RTC = i.getStringExtra("RTC");
        AppType = i.getIntExtra("AppType", 0);

        dataBaseHelper =
                Room.databaseBuilder(getApplicationContext(),
                        DataBaseHelper.class, getString(R.string.db_name)).build();

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

        clsReqLandID = new ClsReqLandID();
        clsReqLandID.setBhm_dist_code(Integer.parseInt(distId));
        clsReqLandID.setBhm_taluk_code(Integer.parseInt(talkId));
        clsReqLandID.setBhm_hobli_code(Integer.parseInt(hblId));
        clsReqLandID.setVillage_code(Integer.parseInt(villId));
        clsReqLandID.setLand_code(Integer.parseInt(land_no));
//        input = "{" +
//                "\"Bhm_dist_code\": \""+distId+"\"," +
//                "\"Bhm_taluk_code\": \""+talkId+"\"," +
//                "\"Bhm_hobli_code\":\""+hblId+"\"," +
//                "\"village_code\": \""+villId+"\"," +
//                "\"land_code\": \""+land_no+"\"" +
//                "}";

        if (RTC.equals("RTC")) {
            if (distId != null && talkId != null && hblId != null && villId != null && land_no != null) {
                if (isNetworkAvailable()) {
                    Observable<List<? extends VR_RES_Interface>> districtDataObservable = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getVR_RES(distId,talkId,hblId,villId,surveyNo,hissa_str));
                    districtDataObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<? extends VR_RES_Interface>>() {

                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull List<? extends VR_RES_Interface> vrdistidList) {


                                    VR_RES_Data = (List<VR_RES_Interface>) vrdistidList;
                                    if (vrdistidList.size()!=0) {
                                        for (int i = 0; i <= vrdistidList.size(); i++) {

                                            String LansOwner = VR_RES_Data.get(0).getVR_LAND_OWNER_RES();
                                            String Cult = VR_RES_Data.get(0).getVR_CULT_RES();

                                            try {
                                                Object object;

                                                JSONObject obj = new JSONObject(LansOwner);
                                                JSONObject rtc = obj.getJSONObject("rtc");

                                                JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");

                                                object = ownerdetails.get("jointowners");

                                                JSONArray jointOwners = new JSONArray();

                                                if (object instanceof JSONObject) {
                                                    jointOwners.put(object);

                                                } else {
                                                    jointOwners = (JSONArray) object;

                                                }
                                                LandDetailsFragment = rtc.toString();
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

                                                JSONObject cultivatordetails = rtcCult.getJSONObject("cultivatordetails");

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
                                        if (progressBar != null) {
                                            progressBar.setVisibility(View.VISIBLE);
                                        }
                                        try {
                                            mTaskFragment.startBackgroundTask_GenerateToken(getString(R.string.url_token));

                                        } catch (Exception e){
                                            Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                } else
                    Toast.makeText(getApplicationContext(), R.string.internet_not_available, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong_pls_try_again), Toast.LENGTH_LONG).show();
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
    public void onPostResponseError_Task2(String data, int count) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.contains("CertPathValidatorException")){
            Toast.makeText(getApplicationContext(), ""+data, Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(RtcDetails.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.server_is_busy_please_try_again_later))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                        dialog.cancel();
                        finish();
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPreExecute2() {
        if (progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess2(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (data.contains("No value for rtc") || data.contains("Please try again later")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RtcDetails.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(data+"")
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                        dialog.cancel();
                        finish();
                    });
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }else {
            XmlToJson xmlToJson = new XmlToJson.Builder(data.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();
            // convert to a JSONObject
            //JSONObject jsonObject = xmlToJson.toJson();
            Object object;

            // convert to a formatted Json String
            formattedLand = xmlToJson.toFormattedString().replace("\n", "");
            try {
                JSONObject obj = new JSONObject(formattedLand);
                JSONObject rtc = obj.getJSONObject("rtc");

                JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");

                object = ownerdetails.get("jointowners");

                JSONArray jointOwners = new JSONArray();

                if (object instanceof JSONObject) {
                    jointOwners.put(object);

                } else {
                    jointOwners = (JSONArray) object;

                }
                LandDetailsFragment = rtc.toString();
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
                try {
//                    JsonObject jsonObject1 = new JsonParser().parse(input).getAsJsonObject();
                    mTaskFragment.startBackgroundTaskCultivatorData(clsReqLandID, getString(R.string.rest_service_url), tokenType, accessToken);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                survey_new = survey;
                OwnerDetailsFragment = jointOwners.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPreExecute3() {

    }

    @Override
    public void onPostResponseSuccess3(String data) {

    }

    @Override
    public void onPostResponseError_Task3(String data) {

    }

    @Override
    public void onPostResponseSuccess4(String data) {

    }

    @Override
    public void onPostResponseError_Task4(String data) {

    }

    @Override
    public void onPreExecute4() {

    }

    @Override
    public void onPostResponseErrorCultivator(String errorResponse, int count) {
//        if (progressBar != null)
//            progressBar.setVisibility(View.GONE);
////        Toast.makeText(getApplicationContext(), errorResponse, Toast.LENGTH_LONG).show();
//        mTaskFragment.startBackgroundTaskCultivatorData(distId, talkId, hblId, villId, land_no, getString(R.string.rest_service_url));
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        if (errorResponse.contains("CertPathValidatorException")){
            Toast.makeText(getApplicationContext(), ""+errorResponse, Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(RtcDetails.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(""+errorResponse)
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        }
    }

    @Override
    public void onPreExecute5() {

    }

    @Override
    public void onPostResponseSuccess_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPostResponseError_GetDetails_VilWise(String data) {

    }

    @Override
    public void onPreExecuteToken() {
        progressBar = findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccessGetToken(String TokenType, String AccessToken) {
        tokenType = TokenType;
        accessToken = AccessToken;
        if (AccessToken == null || AccessToken.equals("") || AccessToken.contains("INVALID")||TokenType == null || TokenType.equals("") || TokenType.contains("INVALID")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RtcDetails.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.status))
                    .setMessage(getString(R.string.something_went_wrong_pls_try_again))
                    .setIcon(R.drawable.ic_notifications_black_24dp)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
            final android.app.AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        } else {
            try {

                ClsAppLgs objClsAppLgs = new ClsAppLgs();
                objClsAppLgs.setAppID(1);
                objClsAppLgs.setAppType(AppType);
                objClsAppLgs.setIPAddress("");

                mTaskFragment.startBackgroundTask_AppLgs(objClsAppLgs, getString(R.string.rest_service_url), tokenType, accessToken);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPostResponseError_Token(String errorResponse) {
        Toast.makeText(this, ""+errorResponse, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPreExecuteGetBhoomiLandId() {

    }

    @Override
    public void onPostResponseSuccessGetBhoomiLandID(String data) {

    }

    @Override
    public void onPostResponseError_BhoomiLandID(String data) {

    }

    @Override
    public void onPreExecute_AppLgs() {
        if (progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess_AppLgs(String data) {
        Log.d("AppLgsResSuc", ""+data);
//        JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
        mTaskFragment.startBackgroundTask2(clsReqLandID, getString(R.string.rest_service_url),tokenType, accessToken);
    }

    @Override
    public void onPostResponseError_AppLgs(String data) {
        Log.d("AppLgsResErr", ""+data);
//        JsonObject jsonObject = new JsonParser().parse(input).getAsJsonObject();
        mTaskFragment.startBackgroundTask2(clsReqLandID, getString(R.string.rest_service_url),tokenType, accessToken);
    }

    @Override
    public void onPreExecute_GetSurnocNo() {

    }

    @Override
    public void onPostResponseSuccess_GetSurnocNo(String data) {

    }

    @Override
    public void onPostResponseError_GetSurnocNo(String data) {

    }

    @Override
    public void onPreExecute_GetHissaNo() {

    }

    @Override
    public void onPostResponseSuccess_GetHissaNo(String data) {

    }

    @Override
    public void onPostResponseError_GetHissaNo(String data) {

    }

    @Override
    public void onPostResponseSuccessCultivator(String gettcDataResult) {
        if (progressBar!=null)
            progressBar.setVisibility(View.GONE);
        XmlToJson xmlToJson = new XmlToJson.Builder(gettcDataResult.replace("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>", "").trim()).build();

        // convert to a formatted Json String
        formattedCult = xmlToJson.toFormattedString().replace("\n", "");
        try {
                JSONObject obj = new JSONObject(formattedCult);
                JSONObject rtc = obj.getJSONObject("rtc");

            JSONObject cultivatordetails = rtc.getJSONObject("cultivatordetails");

            CultivatorDetailsFragment = cultivatordetails.toString();

            viewPager = findViewById(R.id.viewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

            //---------DB INSERT-------

            Observable<Integer> noOfRows;
            noOfRows = Observable.fromCallable(() -> dataBaseHelper.daoAccess().getNumOfRows());
            noOfRows
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {


                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Integer integer) {
                            List<VR_INFO> vr_info_List = loadData();
                            if (integer < 6) {
                                createVRTCData(vr_info_List);
                            } else {
                                deleteResponseByID(vr_info_List);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
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
    public void onPostResponseError_FORHISSA(String data, int count) {

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
        List<VR_INFO> vr_info_arr = new ArrayList<>();
        try {
            VR_INFO vr_info = new VR_INFO();
            vr_info.setVR_DST_ID(Integer.parseInt(distId));
            vr_info.setVR_TLK_ID(Integer.parseInt(talkId));
            vr_info.setVR_HBL_ID(Integer.parseInt(hblId));
            vr_info.setVR_VLG_ID(Integer.parseInt(villId));
            vr_info.setVR_LAND_NO(Integer.parseInt(land_no));
            vr_info.setVR_SNO(surveyNo);
            vr_info.setVR_surnoc(surnoc);
            vr_info.setVR_HISSA_NM(hissa_str);
            vr_info.setVR_LAND_OWNER_RES(formattedLand);
            vr_info.setVR_CULT_RES(formattedCult);
            vr_info_arr.add(vr_info);

        } catch (Exception e) {
            e.printStackTrace();
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
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long[] longs) {
//                        Intent intent = new Intent(ViewRtcInformation.this, BhoomiHomePage.class);
//                        startActivity(intent);
//                        finish();
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

    private void deleteResponseByID(List<VR_INFO> vr_info_List) {

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
                        createVRTCData(vr_info_List);
                    }
                });

    }


    @Override
    protected void onStop() {
        super.onStop();
        mTaskFragment.terminateExecutionOfBackgroundTask2();
        mTaskFragment.terminateExecutionOfGetCultivatorResponse();
    }
}
