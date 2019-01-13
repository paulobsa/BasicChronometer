package com.paulobsa.basicchronometer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class BasicChronometerActivity extends AppCompatActivity {

    Button btnStartStop;
    Button btnRestart;
    Chronometer mChronometer;
    private long chronometerActualTime = 0;
    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_chronometer);

        btnStartStop = findViewById(R.id.btnStartStop);
        mChronometer = findViewById(R.id.chronometer);
        btnRestart = findViewById(R.id.btnRestart);
        setupChronometer();

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopCronometer();
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartChronometer();
            }
        });
    }

    private void restartChronometer() {
        chronometerActualTime = 0;
        btnRestart.setVisibility(View.GONE);
        started = false;
        mChronometer.setText("00:00:00");
    }

    private void setupChronometer() {
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int)(time / 3600000);
                int m = (int)(time - h*3600000)/ 60000;
                int s = (int)(time - h*3600000- m*60000)/ 1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.setText("00:00:00");
    }

    private void startStopCronometer() {
        if (started) {
            chronometerActualTime = mChronometer.getBase() - SystemClock.elapsedRealtime();
            mChronometer.stop();
            btnStartStop.setText(R.string.start);
            btnRestart.setVisibility(View.VISIBLE);
            started = false;
        } else {
            mChronometer.setBase(SystemClock.elapsedRealtime() + chronometerActualTime);
            mChronometer.start();
            btnStartStop.setText(R.string.stop);
            btnRestart.setVisibility(View.GONE);
            started = true;
        }
    }

}
