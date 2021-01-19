package app.bmc.com.BHOOMI_MRTC.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import app.bmc.com.BHOOMI_MRTC.R;

public class CommercialBankLoanAffidavitDoc extends AppCompatActivity {

    private WebView webViewBankLoanAffidavit = null;

    private boolean mbURLLoaded = false;

    private String baseUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/AFDVT_PRINT_BANK.aspx?";
//    private String baseUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/AFDVT_PRINT_BANK.aspx?"; //SUSMITA  4:24.P.M.

    private String appValue;
    private String custIdValue;
    private String bankIdValue;

    private String resultUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_bank_loan_affidavit_doc);

        webViewBankLoanAffidavit = findViewById(R.id.webViewBankLoanAffidavit);

        appValue = getIntent().getStringExtra("APP_ID");
        custIdValue = getIntent().getStringExtra("Cust_ID");
        bankIdValue = getIntent().getStringExtra("Bank_ID");

        initializeWebView();
    }

    public void initializeWebView() {
        WebSettings webSettings = webViewBankLoanAffidavit.getSettings();
        webViewBankLoanAffidavit.setClickable(true);
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
        webViewBankLoanAffidavit.setWebChromeClient(new WebChromeClient());
        webViewBankLoanAffidavit.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final android.webkit.JsResult result) {
                new AlertDialog.Builder(view.getContext()).setTitle("JS Dialog")
                        .setMessage(message).setPositiveButton(android.R.string.ok,
                        (dialog, which) -> result.confirm()).setCancelable(false).create().show();
                return true;
            }

        });

        loadURL();

    }

    private void loadURL() {
        if (!mbURLLoaded)

            resultUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/AFDVT_PRINT_BANK.aspx?" + "rp_CoustmerBankID=" + bankIdValue + "&rp_CLW_APPGUID=" + appValue + "&rp_CoustmerID=" + custIdValue + "&rp_CLW_UserID=" + "";
            if (appValue != null && custIdValue!=null &&  bankIdValue!= null) {
                webViewBankLoanAffidavit.setWebViewClient(new WebViewClient(){
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(CommercialBankLoanAffidavitDoc.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                        handler.proceed(); // Ignore SSL certificate errors
                    }
                });
                webViewBankLoanAffidavit.loadUrl(resultUrl);
            }

    }
}
