package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import app.bmc.com.BHOOMI_MRTC.R;
import app.bmc.com.BHOOMI_MRTC.backgroundtasks.RtcXmlverificationBackGroundTask;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This class  for reprajents UI and RTc Verification  service applications
 */
public class RtcVerification extends AppCompatActivity implements RtcXmlverificationBackGroundTask.BackgroundCallBackRtcXmlVerification {
    private Button getRtcDataBtn;
    private Button clearReferenceNoBtn;
    private EditText referenceNumber;
    private RtcXmlverificationBackGroundTask mTaskFragment;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtc_verification);

        AlertDialog alertDialog = new AlertDialog.Builder(RtcVerification.this).create();
        // alertDialog.setTitle("Reset...");
        alertDialog.setMessage("This Service is Still Under Maintenance");
        alertDialog.setCancelable(false);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            } });

        alertDialog.show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        getRtcDataBtn = findViewById(R.id.btn_get_rtc_data);
        clearReferenceNoBtn = findViewById(R.id.btn_clear_reference_no);
        referenceNumber = findViewById(R.id.edittext_reference_number);
        onButtonClickActions();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mTaskFragment = (RtcXmlverificationBackGroundTask) fm.findFragmentByTag(RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new RtcXmlverificationBackGroundTask();
            fm.beginTransaction().add(mTaskFragment, RtcXmlverificationBackGroundTask.TAG_HEADLESS_FRAGMENT).commit();
        }
        if (mTaskFragment.isTaskExecuting) {
            progressBar = (ProgressBar) findViewById(R.id.progress_circular);
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void onButtonClickActions() {
        getRtcDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focus = null;
                boolean status = false;
                String referenceNo = referenceNumber.getText().toString().trim();
                if (TextUtils.isEmpty(referenceNo)) {
                    focus = referenceNumber;
                    status = true;
                    referenceNumber.setError("Enter Reference Number");
                }
                if (status) {
                    focus.requestFocus();
                } else {
                    if (isNetworkAvailable()) {
                        mTaskFragment.startBackgroundTask(referenceNo);
                    } else {
                        Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
        clearReferenceNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceNumber.setText("");
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPreExecute1() {
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostResponseSuccess1(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(RtcVerification.this, RtcVerificationData.class);
        intent.putExtra("xml_data", data);
        startActivity(intent);
    }

    @Override
    public void onPostResponseError(String data) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
