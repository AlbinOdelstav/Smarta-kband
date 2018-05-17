package com.hallucind.smartaakband.Screens;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.RecyclerListFragment;
import com.hallucind.smartaakband.Settings;
import com.hallucind.smartaakband.Utils.Prefs;

/**
 * Created by albin on 2018-04-22.
 */

public class BandsActivity extends AppCompatActivity {
    RecyclerListFragment fragment;
    private boolean keyDownWasPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bands);

        keyDownWasPressed = false;

        if (savedInstanceState == null) {
            fragment = new RecyclerListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        }

        if (Settings.showBandInformationBox) {
            showPopup();
            Settings.showBandInformationBox = false;
        }
        initializeMapBtn();
    }

    // Gör knappen för kartan tryckbar
    private void initializeMapBtn() {
        ImageButton newBandView = findViewById(R.id.mapBtn);

        newBandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BandsActivity.this, MapsActivity.class);
                startActivity(intent);
                BandsActivity.this.finish();
            }
        });
    }

    // Visar ett popup-fönster som informerar hur man får information de kopplade banden
    private void showPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_information);

        final TextView informationText = dialog.findViewById(R.id.informationText);
        final CheckBox checkBox = dialog.findViewById(R.id.informationCheckBox);
        final TextView okBtn = dialog.findViewById(R.id.informationOkBtn);

        informationText.setText(getResources().getIdentifier("band_information", "string", getPackageName()));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Prefs.saveToPrefs(BandsActivity.this, "showBandInformationBox", false);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {

            if (!keyDownWasPressed) {
                keyDownWasPressed = true;
                Toast.makeText(this, "Tryck igen för att stänga ner appen.", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            return true;
        }
        return false;
    }
}