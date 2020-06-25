package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class CommercialBankLoanReportDocActivity extends AppCompatActivity {

    private WebView webViewBankLoanReport = null;

    private boolean mbURLLoaded = false;

    private String baseUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/ACK_BANK.aspx?";

    private String appValue;
    private String custIdValue;
    private String bankIdValue;

    private String resultUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_bank_loan_report_doc);

        webViewBankLoanReport = findViewById(R.id.webViewBankLoanReport);

        appValue = getIntent().getStringExtra("APP_ID");
        custIdValue = getIntent().getStringExtra("Cust_ID");
        bankIdValue = getIntent().getStringExtra("Bank_ID");

        initializeWebView();
    }

    public void initializeWebView() {
        WebSettings webSettings = webViewBankLoanReport.getSettings();
        webViewBankLoanReport.setClickable(true);
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
        webViewBankLoanReport.setWebChromeClient(new WebChromeClient());
        webViewBankLoanReport.setWebChromeClient(new WebChromeClient() {
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
            resultUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/ACK_BANK.aspx?" + "rp_CoustmerBankID=" + bankIdValue + "&rp_CLW_APPGUID=" + appValue + "&rp_CoustmerID=" + custIdValue;
            Log.d("resultUrl",""+resultUrl);
            if (appValue != null && custIdValue!=null &&  bankIdValue!= null) {
                webViewBankLoanReport.loadUrl(resultUrl);
            }

    }
}
