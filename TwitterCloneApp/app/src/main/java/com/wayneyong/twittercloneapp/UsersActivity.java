package com.wayneyong.twittercloneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.tweet_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.tweet:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Send a Tweet");
                EditText tweetEditText = new EditText(this);

                builder.setView(tweetEditText);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Info", tweetEditText.getText().toString());
                        //Log the tweet to  parse
                        ParseObject tweet = new ParseObject("Tweet");
                        tweet.put("tweet", tweetEditText.getText().toString());
                        tweet.put("username", ParseUser.getCurrentUser().getUsername());

                        tweet.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(UsersActivity.this, "Tweet Sent!", Toast.LENGTH_SHORT).show();
                                    Log.e("Tweet", "Tweet Sent!");
                                } else {
                                    Toast.makeText(UsersActivity.this, "Tweet Failed ;(", Toast.LENGTH_SHORT).show();
                                    Log.e("Tweet", e.getMessage());
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Info", "I dont want to tweet");
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;

            case R.id.viewFeed:
                Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                ParseUser.logOut();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        setTitle("User List");

//        users.add("nick");
//        users.add("sarah");

        ListView listView = findViewById(R.id.listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkedTextView = (CheckedTextView) view;

                if (checkedTextView.isChecked()) {
                    Log.e("Info", "Checked!");
                    ParseUser.getCurrentUser().add("isFollowing", users.get(position)); //let parse know this user is followed

                } else {
                    Log.e("Info", "Not Checked!");
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    List tempUsers = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", tempUsers);
                }

                ParseUser.getCurrentUser().saveInBackground();
            }

        });


        //Query
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername()); //dont get our own account

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        users.add(user.getUsername());
                    }
                    adapter.notifyDataSetChanged(); //nortify after downloaded all list

                    //check if current user following any
                    for (String username : users) {
                        if (ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {
                            listView.setItemChecked(users.indexOf(username), true);
                        }
                    }
                }
            }
        });
    }
}