package app.bmc.com.BHOOMI_MRTC.screens;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import app.bmc.com.BHOOMI_MRTC.R;

public class Endorsement_ReportWebView extends AppCompatActivity {
    String REQ_ID;
    WebView webViewEndorsement_Report;
    private boolean mbURLLoaded = false;
    private String resultUrl;
    String baseUrl;
    private ProgressDialog progressBar;

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
                        (dialog, which) -> result.confirm()).setCancelable(false).create().show();
                return true;
            }

        });

        loadURL(baseUrl, REQ_ID);

    }

    private void loadURL(String baseUrl, String REQ_ID) {

        if (!mbURLLoaded)

            resultUrl = baseUrl + REQ_ID ;

        if (REQ_ID != null) {
            progressBar = ProgressDialog.show(Endorsement_ReportWebView.this,
                    getString(R.string.loading), getString(R.string.please_wait_do_not_interrupt));

            webViewEndorsement_Report.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(Endorsement_ReportWebView.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Endorsement_ReportWebView.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.status))
                            .setMessage(description)
                            .setIcon(R.drawable.ic_notifications_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final android.app.AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                }
            });
            webViewEndorsement_Report.loadUrl(resultUrl);
        }

    }
}
