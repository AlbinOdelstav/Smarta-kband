package com.hallucind.smartaakband.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.hallucind.smartaakband.R;

/**
 * Created by albin on 2018-04-26.
 */

public class ConnectingActivity extends AppCompatActivity implements Runnable {

    TextView connectingTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);

        connectingTextView = findViewById(R.id.connectingTextView);

        simulateConnection();
    }

    private void simulateConnection() {
        Thread thread = new Thread(this);
        thread.start();
    }

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

    private void setText(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectingTextView.setText(value);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}