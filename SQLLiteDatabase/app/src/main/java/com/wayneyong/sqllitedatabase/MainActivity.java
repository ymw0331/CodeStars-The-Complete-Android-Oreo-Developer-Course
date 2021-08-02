package com.wayneyong.sqllitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {


        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
//            SQLiteDatabase eventDatabase = this.openOrCreateDatabase("Event", MODE_PRIVATE, null);


        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VCHAR, age INT(3))");
//            eventDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (event VCHAR, year DATE)");

        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 29)");
        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Wayne', 24)");

//            eventDatabase.execSQL("INSERT INTO events (event, year) VALUES ('WW1', 1901)");

        Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE age < 25", null);
//            Cursor c = eventDatabase.rawQuery("SELECT * FROM events", null);

        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");

//            int eventIndex = c.getColumnIndex("event");
//            int yearIndex = c.getColumnIndex("year");

            c.moveToFirst();

            while (c != null) {
                Log.i("name", c.getString(nameIndex));
                Log.i("age", c.getString(ageIndex));

                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}