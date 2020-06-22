package app.bmc.com.bhoomi.screens;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.bhoomi.R;

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

            resultUrl = "https://clws.karnataka.gov.in/clws/Bank/Affidavite/AFDVT_PRINT_BANK.aspx?" + "rp_CoustmerBankID=" + bankIdValue + "&rp_CLW_APPGUID=" + appValue + "&rp_CoustmerID=" + custIdValue + "&rp_CLW_UserID=" + "";
            Log.d("resultUrl",""+resultUrl);

            if (appValue != null && custIdValue!=null &&  bankIdValue!= null) {
                webViewBankLoanAffidavit.loadUrl(resultUrl);
            }

    }
}
