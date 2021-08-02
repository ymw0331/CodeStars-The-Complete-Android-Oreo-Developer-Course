package com.wayneyong.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser user = new ParseUser();

//        user.setUsername("nick");
//        user.setPassword("myPass");
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    //ok
//                    Log.i("Sign Up", "OK");
//                }else
//                {
//                    e.printStackTrace();
//                }
//            }
//        });

//        ParseUser.logInInBackground("nick", "myass", new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (user != null) {
//                    Log.i("Success", "We logged in");
//                } else {
//                    Log.e("Error", e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        });

        //check if sign ein

        ParseUser.logOut();
        if (ParseUser.getCurrentUser() != null) {
            Log.i("Signed In", ParseUser.getCurrentUser().getUsername());

        } else {
            Log.e("not luck", "cannot sign in");
        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}


//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//        query.whereEqualTo("username", "Wayne");
//        query.setLimit(1);
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    if (objects.size() > 0) {
//                        for (ParseObject object : objects) {
//                            Log.i("username", object.getString("username"));
//                            Log.i("score", Integer.toString(object.getInt("score")));
//                        }
//                    }
//                }
//            }
//        });
/*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

        query.whereGreaterThan("score", 45);
        query.setLimit(1);


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject score : objects) {
                            score.put("score", score.getInt("score") + 20);
                            score.saveInBackground();
                            Log.i("Score updated", String.valueOf(score.getInt("score")));

                        }
                    }
                }
            }
        });


       /*
        ParseObject score = new ParseObject("Score"); //equivalent to table name
        score.put("username", "Sean");  //column
        score.put("score", 65);
        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("Success", "We save the score");
                } else {
                    e.printStackTrace();
                }
            }
        });*/

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.getInBackground("CUI5LysnJw", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
//                    object.getString("username");
                    object.put("score", 85); //update score number
                    object.saveInBackground();

                    Log.i("username", object.getString("username"));
                    Log.i("score", Integer.toString(object.getInt("score")));

                } else {
                    e.printStackTrace();
                }
            }
        });
         */

    /*
        ParseObject tweet = new ParseObject("Tweet"); //equivalent to table name
        tweet.put("username", "MingWei");  //column
        tweet.put("tweet", "My first tweet on Bitnami!");
        tweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("Success", "Tweet Saved!");
                } else {
                    e.printStackTrace();
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.getInBackground("A1bfG65sFc", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null && object != null) {
                    object.getString("username");
                    object.put("tweet", "Just updating my tweet bro!"); //update score number
                    object.saveInBackground();

                    Log.i("username", object.getString("username"));
                    Log.i("tweet", object.getString("tweet"));

                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}*/