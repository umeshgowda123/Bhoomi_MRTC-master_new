package app.bmc.com.BHOOMI_MRTC.screens;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class Endorsement_ReportWebView extends AppCompatActivity {
    String REQ_ID;
    WebView webViewEndorsement_Report;
    private boolean mbURLLoaded = false;
    private String resultUrl;
    String baseUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endorsement__report_web_view);

        baseUrl = getIntent().getStringExtra("baseUrl");
        REQ_ID = getIntent().getStringExtra("REQ_ID");

        webViewEndorsement_Report = findViewById(R.id.webViewEndorsement_Report);

        initializeWebView(baseUrl, REQ_ID);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initializeWebView(String baseUrl, String REQ_ID) {
        WebSettings webSettings = webViewEndorsement_Report.getSettings();
        webViewEndorsement_Report.setClickable(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


        /*
         * Setup the Web Chrome client. Right now, we don't have anything in this class, but we set
         * it up because we need the JS to have the ability to show alerts
         */
        webViewEndorsement_Report.setWebChromeClient(new WebChromeClient());
        webViewEndorsement_Report.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final android.webkit.JsResult result) {
                new AlertDialog.Builder(view.getContext()).setTitle("JS Dialog")
                        .setMessage(message).setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();
                return true;
            }

        });

        loadURL(baseUrl, REQ_ID);

    }

    private void loadURL(String baseUrl, String REQ_ID) {

        if (!mbURLLoaded)

            resultUrl = baseUrl + REQ_ID ;
        Log.d("resultUrl",""+resultUrl);

        if (REQ_ID != null) {
            webViewEndorsement_Report.loadUrl(resultUrl);
        }

    }
}
