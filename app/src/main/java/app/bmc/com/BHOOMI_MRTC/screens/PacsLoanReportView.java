package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.bmc.com.BHOOMI_MRTC.R;

public class PacsLoanReportView extends AppCompatActivity {

    private WebView webViewPacsReport = null;

    private boolean mbURLLoaded = false;

    private String reportUrl;

    private String customerId;
    private String bankId;
    private String appId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacs_loan_report_view);

        webViewPacsReport = findViewById(R.id.webViewPacsReport);

        customerId = getIntent().getStringExtra("Cust_ID");
        bankId = getIntent().getStringExtra("Bank_ID");
        appId = getIntent().getStringExtra("APP_ID");


        initializeWebView();
    }

    public void initializeWebView()
    {
        WebSettings webSettings = webViewPacsReport.getSettings();
        webViewPacsReport.setClickable(true);
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
        webViewPacsReport.setWebChromeClient(new WebChromeClient());
        webViewPacsReport.setWebChromeClient(new WebChromeClient() {
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
            reportUrl = "https://clws.karnataka.gov.in/clws/pacs/pacsAffidavite/ACK_PACS.aspx?" + "rp_CoustmerBankID="  + bankId + "&rp_CLW_APPGUID=" + appId + "&rp_CoustmerID=" + customerId;
        if(bankId != null && appId!=null &&  customerId!= null)
        {
            webViewPacsReport.loadUrl(reportUrl);
        }


    }
}
