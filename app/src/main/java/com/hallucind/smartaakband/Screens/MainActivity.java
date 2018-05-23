package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.Barcode.BarcodeActivity;
import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.Settings;
import com.hallucind.smartaakband.Utils.Prefs;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSettings();
        BandHandler.loadBands(this);
        bandCheck();
        initBtn();

    }

    // kollar om det redan finns kopplade band, då skippas välkomstaktiviteten
    private void bandCheck() {
        if (BandHandler.bands.size() > 1) {
            Intent intent = new Intent(MainActivity.this, BandsActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    // initialiserar knappen som tar användaren vidare till att koppla ett band
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