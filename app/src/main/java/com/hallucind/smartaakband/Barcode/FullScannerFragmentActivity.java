package com.hallucind.smartaakband.Barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.Screens.BandInfoActivity;
import com.hallucind.smartaakband.Screens.BandsActivity;
import com.hallucind.smartaakband.Screens.ConnectingActivity;

public class FullScannerFragmentActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_full_scanner_fragment);
    }

    // låter användaren koppla ett band genom att trycka på skärmen (för demonstration)
    public void toConnectingActivity(View view) {
        Intent intent = new Intent(FullScannerFragmentActivity.this, ConnectingActivity.class);
        startActivity(intent);
        FullScannerFragmentActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(FullScannerFragmentActivity.this, BandsActivity.class);
            startActivity(intent);
            FullScannerFragmentActivity.this.finish();
            return true;
        }
        return false;
    }
}