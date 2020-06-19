package app.bmc.com.bhoomi.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.bmc.com.bhoomi.R;

public class DetailsNotPresentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_not_present);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(DetailsNotPresentActivity.this, ClwsStatus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
