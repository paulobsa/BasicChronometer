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
    Chronometer mChronometer;
    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_chronometer);

        btnStartStop = findViewById(R.id.btnStartStop);
        mChronometer = findViewById(R.id.chronometer);
        setupChronometer();

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopCronometer();
            }
        });
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
        });
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.setText("00:00:00");
    }

    private void startStopCronometer() {
        if (started) {
            mChronometer.stop();
            btnStartStop.setText(R.string.start);
            started = false;
        } else {
            mChronometer.start();
            btnStartStop.setText(R.string.stop);
            started = true;
        }
    }

}
