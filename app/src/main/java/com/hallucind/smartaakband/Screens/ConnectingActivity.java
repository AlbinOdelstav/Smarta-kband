package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.hallucind.smartaakband.R;

public class ConnectingActivity extends AppCompatActivity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);

        Thread thread = new Thread(this);
        thread.start();
    }

    // Simulerar en förbindelse mellan ett band och appen
    @Override
    public void run() {
        int seconds = 0;
        String text = "Kopplar band";

        while (seconds < 3) {
            try {
                setText(text);
                Thread.sleep(500);
                text += ".";
                seconds++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(ConnectingActivity.this, NewNameActivity.class);
        startActivity(intent);
        ConnectingActivity.this.finish();
    }

    // Uppdaterar texten "Kopplar band" med punkter
    private void setText(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView connectingTextView = findViewById(R.id.connectingTextView);
                connectingTextView.setText(value);
            }
        });
    }
}