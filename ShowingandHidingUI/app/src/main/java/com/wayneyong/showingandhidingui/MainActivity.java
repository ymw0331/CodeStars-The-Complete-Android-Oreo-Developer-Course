package com.wayneyong.showingandhidingui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewHello = findViewById(R.id.textViewHello);
        Button showBtn = findViewById(R.id.showButton);
        Button hideBtn = findViewById(R.id.hideButton);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHello.setVisibility(View.VISIBLE);
            }
        });

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHello.setVisibility(View.INVISIBLE);
            }
        });

    }
}