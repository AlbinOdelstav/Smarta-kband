package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.R;

public class ConfirmDisconnection extends AppCompatActivity {
    private int id;
    private String name;
    private int battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_disconnection);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getInt("id");
            name = getIntent().getExtras().getString("name");
            battery = getIntent().getExtras().getInt("battery");

            setConfirmationText(name);
        }
    }

    // Ber användaren bekräfta om det valda bandet ska kopplas bort
    private void setConfirmationText(String name) {
        String text = "Är du säker på att du vill koppla bort " + name + "?";
        TextView bandNameTextView = findViewById(R.id.connectingTextView);
        bandNameTextView.setText(text);
    }

    // Kopplar bort bandet och sparar den uppdaterade listan
    public void confirmDisconnection(View view) {
        BandHandler.bands.remove(id);
        BandHandler.saveBands(this);

        Intent intent = new Intent(ConfirmDisconnection.this, BandsActivity.class);
        startActivity(intent);
        ConfirmDisconnection.this.finish();
    }

    // Nej-knapp om användaren ångrar sig
    public void cancelDisconnection(View view) {
        Intent intent = new Intent(ConfirmDisconnection.this, BandsActivity.class);
        startActivity(intent);
        ConfirmDisconnection.this.finish();
    }
}