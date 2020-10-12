package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class ShowMutationSummeryReport extends AppCompatActivity {


    private String htmlResponseData;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mutation_summery_report);

        webView = findViewById(R.id.webView);
        htmlResponseData = (String) getIntent().getStringExtra("html_response_data");

        showData(htmlResponseData);

    }


    private void showData(String htmlResponseData) {

        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);

            if (htmlResponseData != null) {
                webView.requestFocus();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadDataWithBaseURL("file:///android_asset/", htmlResponseData, "text/html", "UTF-8", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
