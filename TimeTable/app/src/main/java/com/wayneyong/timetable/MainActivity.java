package com.wayneyong.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar timesTableSeekbar = findViewById(R.id.timesTableSeekBar);
        final ListView timesTableListView = findViewById(R.id.timeTableListView);

        timesTableSeekbar.setMax(20);
        timesTableSeekbar.setProgress(10); //starting point

        timesTableSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTableNumber;

                if (progress < min) {
                    timesTableNumber = min;
                    timesTableSeekbar.setProgress(min);
                } else {
                    timesTableNumber = progress;
                }
                Log.i("Seekbar Value", Integer.toString(timesTableNumber));
                ArrayList<String> timesTableContent = new ArrayList<String>();

                for (int i = 1; i <= 100; i++) {
                    timesTableContent.add(Integer.toString(i * timesTableNumber));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, timesTableContent);
                timesTableListView.setAdapter(arrayAdapter);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}