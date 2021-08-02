package com.wayneyong.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewRequestsActivity extends AppCompatActivity {

    ListView requestListView;
    ArrayList<String> requests = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    //location cannot be passed in intent directly
    ArrayList<Double> requestLatitudes = new ArrayList<Double>();
    ArrayList<Double> requestLongitudes = new ArrayList<Double>();

    ArrayList<String> usernames = new ArrayList<String>();

    LocationManager locationManager;
    LocationListener locationListener;


    public void updateListView(Location location) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

        query.whereNear("location", geoPointLocation); //find objects near driver location
        query.whereDoesNotExist("driverUsername"); //we don't want to show request that is taken by driver
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    requests.clear();
                    requestLongitudes.clear();
                    requestLatitudes.clear();

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            ParseGeoPoint requestLocation = (ParseGeoPoint) object.get("location");

                            if (requestLocation != null) {

                                Double distanceInKM = geoPointLocation.distanceInKilometersTo((ParseGeoPoint) object.get("location"));
                                //convert to decimal places
                                Double distanceOneDP = (double) Math.round(distanceInKM * 10) / 10;

                                requests.add(distanceOneDP.toString() + " km");

                                requestLatitudes.add(requestLocation.getLatitude());
                                requestLongitudes.add(requestLocation.getLongitude());
                                usernames.add(object.getString("username"));
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        requests.add("No nearby active requests...");
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    updateListView(lastKnownLocation);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        setTitle("Nearby Requests");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requests);
        requests.clear();
        requests.add("Getting nearby requests...");
        requestListView = (ListView) findViewById(R.id.requestListView);
        requestListView.setAdapter(arrayAdapter);

        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewRequestsActivity.this, "Clicking on " + position, Toast.LENGTH_SHORT).show();

                //permission granted
                if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(ViewRequestsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (requestLatitudes.size() > position && requestLongitudes.size() > position && usernames.size() > position && lastKnownLocation != null) {
                        Intent intent = new Intent(getApplicationContext(), DriverLocationActivity.class);
                        intent.putExtra("requestLatitude", requestLatitudes.get(position));
                        intent.putExtra("requestLongitude", requestLongitudes.get(position));
                        intent.putExtra("driverLatitude", lastKnownLocation.getLatitude());
                        intent.putExtra("driverLongitude", lastKnownLocation.getLongitude());
                        intent.putExtra("username", usernames.get(position));

                        Log.e("IntentsExtras", "Passing Intents extras to Driver Location Activity:" + "requestLatitude:" + requestLatitudes.get(position) + " requestLongitude:" + requestLongitudes.get(position) + " driverLatitude:" + lastKnownLocation.getLatitude() + " driverLongitude:" + lastKnownLocation.getLongitude());

                        startActivity(intent);
                    }
                }
            }
        });


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateListView(location);

                //update in the parse server to let rider know where driver is
                ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(), location.getLongitude()));
                ParseUser.getCurrentUser().saveInBackground();
            }
        };

        //Permission checking,
        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {
            //permission not granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null) {
                    updateListView(lastKnownLocation);
                }
            }
        }
    }
}