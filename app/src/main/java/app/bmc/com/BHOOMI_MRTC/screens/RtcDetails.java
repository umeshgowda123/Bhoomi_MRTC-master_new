package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcViewInfoBackGroundTaskFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.CultivatorDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.LandDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.fragments.OwnerDetailsFragment;
import app.bmc.com.BHOOMI_MRTC.model.Staticinfopahani;
import app.bmc.com.BHOOMI_MRTC.model.Villagedetails;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

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
    String land_no, survey, RTC;
    public String owner;

    String LandDetailsFragment;
    String survey_new;
    String OwnerDetailsFragment;
    String CultivatorDetailsFragment;


    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        RTC = i.getStringExtra("RTC");

        Log.d("survey_no",""+survey);
        Log.d("land_code_int",""+land_no);
        Log.d("distId1",""+distId);
        Log.d("talkId1",""+talkId);
        Log.d("hblId1",""+hblId);
        Log.d("villId1",""+villId);
        Log.d("hissa_no", ""+hblId);

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
                    progressBar.setVisibility(View.VISIBLE);
                    mTaskFragment.startBackgroundTask2(distId, talkId, hblId, villId, land_no);
                    Log.d("land_no", "" + land_no);
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
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
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
        String formatted = xmlToJson.toFormattedString().replace("\n", "");
        try {
            JSONObject obj = new JSONObject(formatted);
            JSONObject rtc = obj.getJSONObject("rtc");

            JSONObject ownerdetails = rtc.getJSONObject("ownerdetails");
            object = ownerdetails.get("jointowners");
            JSONArray jointOwners = new JSONArray();
            if (object instanceof JSONObject) {
                jointOwners.put((JSONObject) object);
            } else {
                jointOwners = (JSONArray) object;
            }
            JSONObject cultivatordetails = rtc.getJSONObject("cultivatordetails");

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
            survey_new = survey;
            OwnerDetailsFragment = jointOwners.toString();
            CultivatorDetailsFragment = cultivatordetails.toString();
            Log.d("LandDetailsFragment",""+LandDetailsFragment);
            Log.d("OwnerDetailsFragment",""+OwnerDetailsFragment);
            Log.d("CultivatorDetails",""+CultivatorDetailsFragment);

            viewPager = findViewById(R.id.viewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


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
    public void onSaveInstanceState(Bundle outState) {
       /* outState.putString(GAME_STATE_KEY, mGameState);
        outState.putString(TEXT_VIEW_KEY, mTextView.getText());
*/
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
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
            } else if (position == 2) {
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
            } else if (position == 2) {
                title = getString(R.string.cultivator_details);
            }

            return title;
        }
    }

}
