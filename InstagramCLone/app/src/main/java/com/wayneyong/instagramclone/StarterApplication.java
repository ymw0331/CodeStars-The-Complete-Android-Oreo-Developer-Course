package com.wayneyong.instagramclone;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //passWord: Fq6KEw1D4ozk, username: user
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                // if defined
                .clientKey("Fq6KEw1D4ozk")
//                .server("http://localhost:1337/parse/")
                .server("http://18.141.143.138/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "123");
//        object.put("myString", "rob");
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
