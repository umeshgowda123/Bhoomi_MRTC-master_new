package app.bmc.com.BHOOMI_MRTC.screens;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andexert.library.RippleView;

import app.bmc.com.BHOOMI_MRTC.R;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        final RippleView rippleClwsHome = (RippleView) findViewById(R.id.rippleClwsHome);
        rippleClwsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, BhoomiHomePage.class);
                startActivity(intent);

            }
        });


        final RippleView rippleForReport = (RippleView) findViewById(R.id.rippleForReport);
        rippleForReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this, ClwsStatus.class);
                startActivity(intent);
            }
        });

        final RippleView rippleForPacs = (RippleView) findViewById(R.id.rippleForPacs);
        rippleForPacs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashBoardActivity.this, CitizenPaymentCertificatePacsActivity.class);
                startActivity(intent);
            }
        });

        final RippleView rippleForBanks = (RippleView) findViewById(R.id.rippleForBanks);
        rippleForBanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashBoardActivity.this, CitizenPaymentCertificateBanksActivity.class);
                startActivity(intent);
            }
        });
    }
}
