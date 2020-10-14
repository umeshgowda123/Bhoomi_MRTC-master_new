package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.adapters.ExpandListAdapter;
import app.bmc.com.BHOOMI_MRTC.util.Constants;


/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class  for reprajents UI and Bhoomi applications
 */
public class Bhoomi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        String language = sp.getString(Constants.LANGUAGE, "en");
        setLocale(language);
        setContentView(R.layout.activity_bhoomi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // DBManager dbManager=new DBManager(Bhoomi.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        enableExpandableList();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    private void enableExpandableList() {
        ArrayList<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<String>> listDataChild = new HashMap<>();
        ExpandableListView expListView;
        expListView = findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        ExpandListAdapter listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            // Toast.makeText(getApplicationContext(),
            // "Group Clicked " + listDataHeader.get(groupPosition),
            // Toast.LENGTH_SHORT).show();
            return false;
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(groupPosition -> {

        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(groupPosition -> {


        });

        // ListView on child click listener
        expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            // TODO Auto-generated method stub
            // Temporary code:
//if(Constants.SERVICE1.equalsIgnoreCase(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
            if (childPosition == 0) {
                Intent intent = new Intent(Bhoomi.this, ViewRtcInformation.class);
                startActivity(intent);
            } else if(childPosition == 1)
            {
                Intent intent = new Intent(Bhoomi.this, ViewRtcInformationByOwnerName.class);
                startActivity(intent);
            } else if (childPosition == 2) {
                Intent intent = new Intent(Bhoomi.this, RtcVerification.class);
                startActivity(intent);
            }

//}
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       /* // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_rtc_information) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {
        listDataHeader.add(getString(R.string.services));
        List<String> services = new ArrayList<>();
        services.add(getString(R.string.viewrtcinformation));
        services.add(getString(R.string.viewrtcinformationByOwnerName));
        services.add(getString(R.string.rtc_verification));
        listDataChild.put(listDataHeader.get(0), services);

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
            Intent intent = new Intent(Bhoomi.this, Language.class);
            startActivity(intent);
            // finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
