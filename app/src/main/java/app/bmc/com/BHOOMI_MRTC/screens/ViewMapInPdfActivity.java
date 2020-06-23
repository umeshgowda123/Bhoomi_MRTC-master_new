package app.bmc.com.BHOOMI_MRTC.screens;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import app.bmc.com.BHOOMI_MRTC.R;

public class ViewMapInPdfActivity extends AppCompatActivity {


    private WebView showMap;

    private String responsepdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map_in_pdf);

        showMap =  findViewById(R.id.showMap);


        responsepdfUrl = (String) getIntent().getStringExtra("MAP_PDF_URL");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);

        if(responsepdfUrl != null)
        {
            showMap.requestFocus();
            showMap.getSettings().setJavaScriptEnabled(true);

            String url = "http://docs.google.com/gview?embedded=true&url="+responsepdfUrl;
//            String url = "https://docs.google.com/gview?embedded=true&url="+responsepdfUrl;//SUSMITA  4:31.P.M.

            showMap.loadUrl(url);

        }else
        {
            showMap.setVisibility(View.GONE);
            Toast.makeText(ViewMapInPdfActivity.this, "No Map Available!", Toast.LENGTH_SHORT).show();
        }
    }
}
