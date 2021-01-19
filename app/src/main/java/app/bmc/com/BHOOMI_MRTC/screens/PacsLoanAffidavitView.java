package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.DialogInterface;
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

public class PacsLoanAffidavitView extends AppCompatActivity {

    private WebView webViewPacsAffidavit = null;

    private boolean mbURLLoaded = false;

    private String affidavitUrl;

    private String customerId;
    private String bankId;
    private String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacs_loan_affidavit_view);

        webViewPacsAffidavit = findViewById(R.id.webViewPacsAffidavit);

        customerId = getIntent().getStringExtra("Cust_ID");
        bankId = getIntent().getStringExtra("Bank_ID");
        appId = getIntent().getStringExtra("APP_ID");


        initializeWebView();
    }


    public void initializeWebView()
    {
        WebSettings webSettings = webViewPacsAffidavit.getSettings();
        webViewPacsAffidavit.setClickable(true);
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
        webViewPacsAffidavit.setWebChromeClient(new WebChromeClient());
        webViewPacsAffidavit.setWebChromeClient(new WebChromeClient() {
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


    private void loadURL()
    {
        affidavitUrl = "https://clws.karnataka.gov.in/clws/pacs/pacsAffidavite/AFDVT_PRINT_PACS.aspx?" + "rp_CoustmerBankID=" + bankId + "&rp_CLW_APPGUID=" + appId + "&rp_CoustmerID=" + customerId + "&rp_CLW_UserID=" + "";
//        affidavitUrl = "https://clws.karnataka.gov.in/clws/pacs/pacsAffidavite/AFDVT_PRINT_PACS.aspx?" + "rp_CoustmerBankID=" + bankId + "&rp_CLW_APPGUID=" + appId + "&rp_CoustmerID=" + customerId + "&rp_CLW_UserID=" + "";//SUSMITA  4:26.P.M.
        if(bankId != null && appId!=null &&  customerId!= null)
        {
            webViewPacsAffidavit.setWebViewClient(new WebViewClient(){
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(PacsLoanAffidavitView.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                    handler.proceed(); // Ignore SSL certificate errors
                }
            });
            webViewPacsAffidavit.loadUrl(affidavitUrl);
        }
    }
}
