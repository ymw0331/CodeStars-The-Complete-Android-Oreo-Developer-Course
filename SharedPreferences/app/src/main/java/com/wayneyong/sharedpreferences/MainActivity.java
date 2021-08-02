package com.wayneyong.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //information only keep to itself, private
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.wayneyong.sharedpreferences", Context.MODE_PRIVATE);


        ArrayList<String> friends = new ArrayList<>();
        friends.add("Fido");
        friends.add("Sarah");
        friends.add("John");

        try {

            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();
//            ObjectSerializer.serialize(friends);

            //change to something like machine understandable strings
            Log.i("friends", ObjectSerializer.serialize(friends));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("newFriends", newFriends.toString());

//        sharedPreferences.edit().putString("username", "wayne").apply();
//        String username = sharedPreferences.getString("username", "");
//        Log.i("UserName", username);


    }
}