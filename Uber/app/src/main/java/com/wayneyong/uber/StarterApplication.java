package com.wayneyong.uber;

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

        //Enable Local Database
        Parse.enableLocalDatastore(this);

        //passWord: Fq6KEw1D4ozk, username: user
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("2gW9DA3irwkm")
                .server("http://52.221.212.219/parse/")
                .build()
        );
//        http://52.221.212.219

//        ParseUser.logOut();

//        ParseObject object = new ParseObject("Test");
//        object.put("testSting", "My second tweet tweet ");
//        object.put("username", "wayne");

//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("ParseResult", "Successful!");
//                } else {
//                    Log.i("ParseResult", "Failed" + ex.toString());
//                    ex.printStackTrace();
//                }
//            }
//        });


//        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
