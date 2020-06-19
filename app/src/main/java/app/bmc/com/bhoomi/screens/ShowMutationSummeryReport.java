package app.bmc.com.bhoomi.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.kxml2.wap.Wbxml;

import app.bmc.com.bhoomi.R;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

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
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false);

            if (htmlResponseData != null) {
                webView.requestFocus();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadDataWithBaseURL("", htmlResponseData, "text/html", "UTF-8", "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
