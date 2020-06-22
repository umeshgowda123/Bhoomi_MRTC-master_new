package app.bmc.com.bhoomi.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import app.bmc.com.bhoomi.R;

public class Download_Conversion_order extends AppCompatActivity {

    RadioGroup rgForSelection;
    RadioButton rbRequestID, rbSurveyNo;
    EditText etRequestID;
    Button btnDownloadOrder;
    String strSelected;
    LinearLayout linearLayout_survey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_conversion_order);

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

        rgForSelection = findViewById(R.id.rgForSelection);
        rbRequestID = findViewById(R.id.rbRequestID);
        rbSurveyNo = findViewById(R.id.rbSurveyNo);
        etRequestID = findViewById(R.id.etRequestID);
        btnDownloadOrder = findViewById(R.id.btnDownloadOrder);
        linearLayout_survey = findViewById(R.id.linearLayout_survey);

        rgForSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbRequestID) {
                    strSelected = getString(R.string.request_id);
                    etRequestID.setVisibility(View.VISIBLE);
                    linearLayout_survey.setVisibility(View.GONE);
                } else if(checkedId == R.id.rbSurveyNo) {
                    strSelected = getString(R.string.survey_no_wise);
                    etRequestID.setVisibility(View.GONE);
                    linearLayout_survey.setVisibility(View.VISIBLE);
                }
                Log.d("strSelected", ""+strSelected);
            }
        });

        btnDownloadOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
