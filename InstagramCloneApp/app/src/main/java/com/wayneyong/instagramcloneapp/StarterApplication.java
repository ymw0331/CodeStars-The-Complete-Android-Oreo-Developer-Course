package com.wayneyong.instagramcloneapp;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //passWord: Fq6KEw1D4ozk, username: user
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("Fq6KEw1D4ozk")
                .server("http://18.141.143.138/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("Test1");
//        object.put("myNumber", "123");
//        object.put("myString", "wayne");
//
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("ParseResult", "Successful!");
//                } else {
//                    Log.i("ParseResult", "Failed" + ex.toString());
//                }
//            }
//        });


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }


}
