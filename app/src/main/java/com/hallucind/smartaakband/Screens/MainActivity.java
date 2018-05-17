package com.hallucind.smartaakband.Screens;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.Barcode.BarcodeActivity;
import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.Settings;
import com.hallucind.smartaakband.Utils.Prefs;

/**
 * Created by albin on 2018-04-15.
 */

public class MainActivity extends AppCompatActivity{

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1080);
        }

        if(isServiceOK()) {
            loadSettings();
            BandHandler.loadBands(this);

            // om det redan finns kopplade band skippas välkomstaktiviteten
            if (BandHandler.bands.size() > 1) {
                Intent intent = new Intent(MainActivity.this, BandsActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
            initBtn();
        }
    }

    // kontrollerar att google play services fungerar, så kartan kan visas
    public boolean isServiceOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        return false;
    }

    private void initBtn() {
        Button btn = findViewById(R.id.connectFirstBandBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarcodeActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    // kontrollerar om användaren har valt att aldrig mer visa informationfönstret om banden
    private void loadSettings() {
        Settings.showBandInformationBox = Prefs.getBoolean(this, "showBandInformationBox");
        Settings.showMapInformationBox = Prefs.getBoolean(this, "showMapInformationBox");
    }
}