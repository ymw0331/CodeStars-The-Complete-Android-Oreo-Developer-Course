package com.wayneyong.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //every time it is called
                Log.i("Seconds Left!", String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //run when countDown timer finish
                Log.i("We are done", "No more countdown");
            }
        }.start();

      /*  //Handler runs the runnable
        Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.i("Hey its us", "A second has passed by");

                handler.postDelayed(this, 1000);

            }
        };
        handler.post(run);*/
    }

}