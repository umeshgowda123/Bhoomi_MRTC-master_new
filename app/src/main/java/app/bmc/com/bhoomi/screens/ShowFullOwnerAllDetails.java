package app.bmc.com.bhoomi.screens;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import app.bmc.com.bhoomi.R;
import app.bmc.com.bhoomi.fragments.CultivatorDetailsFragment;
import app.bmc.com.bhoomi.fragments.LandDetailsFragment;
import app.bmc.com.bhoomi.fragments.OwnerDetailsFragment;
import app.bmc.com.bhoomi.model.Staticinfopahani;
import app.bmc.com.bhoomi.model.Villagedetails;

public class ShowFullOwnerAllDetails extends AppCompatActivity {

    private Villagedetails villagedetails = new Villagedetails();
    private Staticinfopahani staticinfopahani = new Staticinfopahani();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_owner_all_details);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String land_code = getIntent().getStringExtra("land_code");
        String main_owner_no = getIntent().getStringExtra("main_owner_no");
        String owner_no = getIntent().getStringExtra("owner_no");
        String owner = getIntent().getStringExtra("owner");
        String surnoc = getIntent().getStringExtra("surnoc");
        String hissa_no = getIntent().getStringExtra("hissa_no");
        Log.d("DATA : ", land_code+" "+main_owner_no+" "+owner_no+" "+owner+" "+surnoc+" "+hissa_no);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager = (ViewPager) findViewById(R.id.owner_viewPager);// MODIFIED BY SUSMITA
//        ViewPagerAdapter viewPagerAdapter = new ShowFullOwnerAllDetails.ViewPagerAdapter(getSupportFragmentManager());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout = (TabLayout) findViewById(R.id.tabsByOwner);// MODIFIED BY SUSMITA
        tabLayout.setupWithViewPager(viewPager);

    }




    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                String data = getIntent().getStringExtra("LandDetailsFragment");
                fragment = new LandDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LandDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", getIntent().getStringExtra("survey"));
                bundle.putString("Valid", staticinfopahani.getValidfrom().toString().replace("Valid from", ""));
                fragment.setArguments(bundle);

            } else if (position == 1) {
                String data = getIntent().getStringExtra("OwnerDetailsFragment");

                fragment = new OwnerDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("OwnerDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", getIntent().getStringExtra("survey"));
                bundle.putString("Valid", staticinfopahani.getValidfrom().toString().replace("Valid from", ""));

                fragment.setArguments(bundle);
            } else if (position == 2) {
                String data = getIntent().getStringExtra("CultivatorDetailsFragment");
                fragment = new CultivatorDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CultivatorDetailsFragment", data);
                bundle.putString("Taluk", villagedetails.getTalukname());
                bundle.putString("Hobli", villagedetails.getHobliname());
                bundle.putString("Village", villagedetails.getVillagename());
                bundle.putString("Survey", getIntent().getStringExtra("survey"));
                bundle.putString("Valid", staticinfopahani.getValidfrom().toString().replace("Valid from", ""));

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}