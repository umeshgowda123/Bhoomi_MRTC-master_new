package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class PacsWebViewActivity extends AppCompatActivity {

    private WebView webViewPacsPaymentCertificate = null;

    private boolean mbURLLoaded = false;

    private String custIdValue;
    private String appIdValue;


    private String url;
    private String baseurl="https://clws.karnataka.gov.in/clws/payment/pacscertview/PacsReportBasedOnCustId.aspx?";
//    private String baseurl="https://clws.karnataka.gov.in/clws/payment/pacscertview/PacsReportBasedOnCustId.aspx?"; //SUSMITA  4:27.P.M.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacs_web_view);

        webViewPacsPaymentCertificate = findViewById(R.id.webViewPacsPaymentCertificate);

        custIdValue = getIntent().getStringExtra("Cust_ID");
        appIdValue = getIntent().getStringExtra("APP_ID");

        initializeWebView();
    }


    public void initializeWebView() {
        WebSettings webSettings = webViewPacsPaymentCertificate.getSettings();
        webViewPacsPaymentCertificate.setClickable(true);
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
        webViewPacsPaymentCertificate.setWebChromeClient(new WebChromeClient());
        webViewPacsPaymentCertificate.setWebChromeClient(new WebChromeClient() {
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

        loadURL();

    }

    private void loadURL() {
        if (!mbURLLoaded)
            if (custIdValue != null && appIdValue != null) {
                url = baseurl + "appId=" + appIdValue + "&CustID=" + custIdValue;
                webViewPacsPaymentCertificate.loadUrl(url);
            }


    }
}
