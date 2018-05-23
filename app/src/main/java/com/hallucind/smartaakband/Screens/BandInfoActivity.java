package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hallucind.smartaakband.R;

public class BandInfoActivity extends AppCompatActivity {
    private int id;
    private String name;
    private int battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_info);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getInt("id");
            name = getIntent().getExtras().getString("name");
            battery = getIntent().getExtras().getInt("battery");

            setBandName(name);
            setBandBattery(battery);
        }
    }

    // Visar det valda namnet som rubrik
    private void setBandName(String name) {
        TextView bandNameTextView = findViewById(R.id.bandInfoName);
        bandNameTextView.setText(name);
    }

    // Visar batteritiden
    private void setBandBattery(int battery) {
        String batteryText = battery + "%";
        TextView bandInfoBatteryTextView = findViewById(R.id.bandInfoBattery);
        bandInfoBatteryTextView.setText(batteryText);
    }

    // Initialiserar tillbaka-knappen
    public void backButtonClick(View view) {
        Intent intent = new Intent(BandInfoActivity.this, BandsActivity.class);
        startActivity(intent);
        BandInfoActivity.this.finish();
    }

    // Initialiserar "Byt namn"-knappen
    public void changeName(View view) {
        Intent intent = new Intent(BandInfoActivity.this, NewNameActivity.class);

        intent.putExtra("id", id);
        intent.putExtra("name", name);

        startActivity(intent);
        BandInfoActivity.this.finish();
    }

    // Initialiserar "Koppla bort"-knappen, startar aktivitet för bekräftelse
    public void disconnect(View view) {
        Intent intent = new Intent(BandInfoActivity.this, ConfirmDisconnection.class);

        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("battery", battery);

        startActivity(intent);
        BandInfoActivity.this.finish();
    }

    // Initialiserar enhetens tillbaka-knapp
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            backButtonClick(null);
            return true;
        }
        return false;
    }
}
