package com.wayneyong.twittercloneapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable Local Database
        Parse.enableLocalDatastore(this);

        //passWord: Fq6KEw1D4ozk, username: user
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("Fq6KEw1D4ozk")
                .server("http://18.141.143.138/parse/")
                .build()
        );

        ParseUser.logOut();

        ParseObject object = new ParseObject("TweetTweet");
        object.put("tweet", "My second tweet tweet ");
        object.put("username", "wayne");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException ex) {
                if (ex == null) {
                    Log.i("ParseResult", "Successful!");
                } else {
                    Log.i("ParseResult", "Failed" + ex.toString());
                }
            }
        });


        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
