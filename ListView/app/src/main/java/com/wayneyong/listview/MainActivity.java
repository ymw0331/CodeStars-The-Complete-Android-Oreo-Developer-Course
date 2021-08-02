package com.wayneyong.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);
        ArrayList<String> myFamily = new ArrayList<String>(asList("Mark", "Jane", "Sussy", "Jan"));

//        myFamily.add("John");
//        myFamily.add("Fido");
//        myFamily.add("Nick");
//        myFamily.add("Sarah");
//        myFamily.add("John");
//        myFamily.add("Fido");
//        myFamily.add("Nick");
//        myFamily.add("Sarah");
//        myFamily.add("John");
//        myFamily.add("Fido");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myFamily);

        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Person Selected: ", myFamily.get(position));
                Toast.makeText(MainActivity.this, "person selected: " + myFamily.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}