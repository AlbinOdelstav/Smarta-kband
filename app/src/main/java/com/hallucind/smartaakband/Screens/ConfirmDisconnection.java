package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.R;

/**
 * Created by albin on 2018-05-04.
 */

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

            setBandName(name);
        }
    }

    private void setBandName(String name) {
        String text = "Är du säker på att du vill koppla bort " + name + "?";
        TextView bandNameTextView = findViewById(R.id.connectingTextView);
        bandNameTextView.setText(text);
    }

    public void confirmDisconnection(View view) {
        BandHandler.bands.remove(id);
        BandHandler.saveBands(this);

        Intent intent = new Intent(ConfirmDisconnection.this, BandsActivity.class);
        startActivity(intent);
        ConfirmDisconnection.this.finish();
    }

    public void cancelDisconnection(View view) {
        Intent intent = new Intent(ConfirmDisconnection.this, BandsActivity.class);
        startActivity(intent);
        ConfirmDisconnection.this.finish();
    }
}