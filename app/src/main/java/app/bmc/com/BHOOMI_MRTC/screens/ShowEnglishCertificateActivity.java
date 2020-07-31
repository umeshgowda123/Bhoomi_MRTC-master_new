package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class ShowEnglishCertificateActivity extends AppCompatActivity {

    private WebView webViewCrop = null;

    private boolean mbURLLoaded = false;

    private String elanguage;
    private String klanaguage;

    private String eurl;
    private String kandaurl;

    private String certificateRequestParamter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_english_certificate);


        webViewCrop = findViewById(R.id.webViewCrop);

        certificateRequestParamter = getIntent().getStringExtra("Request_Parameter");
        elanguage = getIntent().getStringExtra("Language_Parameter");
        klanaguage = getIntent().getStringExtra("Language_Kanada");

        initializeWebView();
    }

    public void initializeWebView() {
        WebSettings webSettings = webViewCrop.getSettings();
        webViewCrop.setClickable(true);
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
        webViewCrop.setWebChromeClient(new WebChromeClient());
        webViewCrop.setWebChromeClient(new WebChromeClient() {
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
            // webViewCrop.loadUrl("https://clws.karnataka.gov.in/clws/payment/bankcertificate/");

            if (elanguage != null) {
                eurl = "https://clws.karnataka.gov.in/clws/paydment/pacscertview/ReportBasedOnId.aspx?" + "language=" + elanguage + "&CustID=" + certificateRequestParamter;
//                eurl = "https://clws.karnataka.gov.in/clws/paydment/pacscertview/ReportBasedOnId.aspx?" + "language=" + elanguage + "&CustID=" + certificateRequestParamter;//SUSMITA  4:28.P.M.
                webViewCrop.loadUrl(eurl);
            }
        if (klanaguage != null) {
            kandaurl = "https://clws.karnataka.gov.in/clws/payment/pacscertview/ReportBasedOnId.aspx?" + "language=" + klanaguage + "&CustID=" + certificateRequestParamter;
//            kandaurl = "https://clws.karnataka.gov.in/clws/payment/pacscertview/ReportBasedOnId.aspx?" + "language=" + klanaguage + "&CustID=" + certificateRequestParamter;//SUSMITA  4:28.P.M.
            webViewCrop.loadUrl(kandaurl);
        }


    }
}
