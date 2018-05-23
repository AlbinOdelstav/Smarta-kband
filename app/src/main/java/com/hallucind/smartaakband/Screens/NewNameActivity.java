package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hallucind.smartaakband.Band.Band;
import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.Utils.Prefs;

import java.util.Collections;
import java.util.Random;

/**
 * Created by albin on 2018-04-22.
 */

public class NewNameActivity extends AppCompatActivity {
    private int id;
    private boolean editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_name);

        // Kollar om användaren vill ändra namnet på ett redan kopplat namn eller om ett nytt band ska kopplas
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getInt("id");
            setNameText(getIntent().getExtras().getString("name"));
            editName = true;
        } else {
            editName = false;
        }

        initializeOkButton();
        initializeCancelButton();
    }

    // Visar det valda namnet att ändra på som en hint (om användaren vill byta namn)
    private void setNameText(String name) {
        EditText nameText = findViewById(R.id.newName);
        nameText.setHint(name);
    }

    // Hämtar och returnerar namnet som användaren skrivit in
    private String getNameText() {
        EditText nameText = findViewById(R.id.newName);
        return nameText.getText().toString();
    }

    // Lägger till ett band
    private void addBand() {
        Band band = new Band(getNameText(), generateColor());
        BandHandler.bands.add(band);
        Collections.swap(BandHandler.bands, BandHandler.bands.size() - 1, BandHandler.bands.size() - 2);

    }

    // Genererar hue
    private float generateColor() {
        Random random = new Random();
        return (random.nextInt(18) + 1) * 20;
    }

    // Initialiserar OK-knappen, lägger till ett namn i en lista vid tryck
    private void initializeOkButton() {
        TextView okView = findViewById(R.id.confirmNewName);

        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Kollar om användaren vill ändra namnet på ett redan kopplat namn eller om ett nytt band ska kopplas
                if (!editName) {
                    if (!getNameText().isEmpty()) {
                        addBand();
                    } else {
                        Toast.makeText(NewNameActivity.this, "Skriv ett namn", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    if (!getNameText().isEmpty()) {
                        BandHandler.bands.get(id).setName(getNameText());
                    }
                }

                // sarar det kopplade bandet
                BandHandler.saveBands(NewNameActivity.this);
                Intent intent = new Intent(NewNameActivity.this, BandsActivity.class);
                startActivity(intent);
                NewNameActivity.this.finish();
            }
        });
    }

    // "Ångra"-knapp
    private void initializeCancelButton() {
        TextView okView = findViewById(R.id.cancelNewName);

        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewNameActivity.this, BandsActivity.class);
                startActivity(intent);
                NewNameActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            initializeCancelButton();
        }
        return false;
    }
}