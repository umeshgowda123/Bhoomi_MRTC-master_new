package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;

import java.util.Locale;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.util.Constants;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        String language = sp.getString(Constants.LANGUAGE, "en");
        RadioButton english_btn = findViewById(R.id.radio_btn_english);
        RadioButton kannada_btn = findViewById(R.id.radio_btn_kannada);
        if (language.equalsIgnoreCase(Constants.LANGUAGE_EN)) {
            english_btn.setChecked(true);
            kannada_btn.setChecked(false);
        } else if (language.equalsIgnoreCase(Constants.LANGUAGE_KN)) {
            english_btn.setChecked(false);
            kannada_btn.setChecked(true);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_btn_kannada:
                if (checked) {
                    SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                    sp.edit().putString(Constants.LANGUAGE, "kn").commit();
                    setLocale("kn");
                    break;
                }
            case R.id.radio_btn_english:
                if (checked) {
                    SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                    sp.edit().putString(Constants.LANGUAGE, "en").commit();
                    setLocale("en");
                    break;
                }
        }
    }

    public void setLocale(String localeName) {

        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(this, Bhoomi.class);
        startActivity(refresh);
        finish();

    }

    @Override
    public void onBackPressed() {
        // do something on back.
        finish();
    }


}
