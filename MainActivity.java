package com.map.parkingmgt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends Activity implements LocationListener {

    private Button buttonsearch;
    private TextView display;
    private TextView calc;
    private LocationManager locationManager;
    private String provider;
    private double userLat;
    private double userLon;
    private double parklat;
    private double parklon;
    private int counterUser = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        display = (TextView) findViewById(R.id.DisplayGPS);


        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define the criteria how to select the location provider -> use default
        Criteria criteria = new Criteria();

        // criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }
        else {

            display.setText("Location not available");
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        //  int lat = (int) (location.getLatitude());
        //  int lng = (int) (location.getLongitude());
        // String s = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude() + "\n\n";
        //  display.setText(s);

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    public void OnClick(View v) {

        buttonsearch = (Button) v;
        ((Button) v).setText("Clicked");

        Firebase ref = new Firebase("https://parking-7e89d.firebaseio.com");

        //Adding values
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

        userLat = (location.getLatitude() * 10000d) / 10000d;
        userLon = (location.getLongitude() * 10000d) / 10000d;
        DecimalFormat f = new DecimalFormat("##.00000");

        userLat = Double.parseDouble(f.format(userLat));
        userLon = Double.parseDouble(f.format(userLon));

        GPS coord = new GPS();
        coord.setLat(userLat);
        coord.setLon(userLon);

        //save to database
        ref.getRoot().child("Driver Location").child("Driver" + Integer.toString(counterUser)).setValue(coord);
        counterUser++;

        //retrieve data from database
        ref = new Firebase("https://parking-7e89d.firebaseio.com/Parking Location/Zone 1/Phoenix");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                parklat = (dataSnapshot.child("Latitude").getValue(Double.class) * 10000d) / 10000d;
                parklon = (dataSnapshot.child("Longitude").getValue(Double.class) * 10000d) / 10000d;


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
/**
        Location loc1 =  locationManager.getLastKnownLocation(provider);// new Location("loc1");
        loc1.setLatitude(userLat);
        loc1.setLongitude(userLon);

        Location loc2 = new Location("loc2");
        loc2.setLatitude(parklat);
        loc2.setLongitude(parklon);

         float distanceInM = (loc1.distanceTo(loc2));
*/
        double rad = 6372.8; // In kilometers
        double dLat = Math.toRadians(userLat - parklat);
        double dLon = Math.toRadians(userLon - parklon);
        userLat = Math.toRadians(userLat);
        parklat = Math.toRadians(parklat);

        double a = (Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2)) * (Math.cos(userLat)) * (Math.cos(parklat));
        double c = 2 * Math.asin(Math.sqrt(a));
        double distanceInKm =  rad * c ;//haversine(userLat,userLon,parklat,parklon);

        if (distanceInKm >= 6000){
            String m = "try again"; //in km
            calc = (TextView) findViewById(R.id.calc);
            calc.setText(m);
        }
        else {
            String m = "Distance in km: " + String.format("%.5f", (distanceInKm)); //in km
            calc = (TextView) findViewById(R.id.calc);
            calc.setText(m);
        }

        /**   float[] results = new float[1];
         Location.distanceBetween(userLat,userLon,parklat,parklon, results);
         float distanceInMeters = results[0];
         double distanceInKm = 10/8;//distanceInMeters / 1000.0f;
         String m = "Distance in km: " + distanceInKm; //in km
         calc = (TextView) findViewById(R.id.calc);
         calc.setText(m);*/

        if (distanceInKm <= 15 && distanceInKm >= 0) {
            ref = new Firebase("https://parking-7e89d.firebaseio.com/Parking Location/Zone 1");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    HashMap<String, String> map = dataSnapshot.getValue(HashMap.class);

                    for (String k : map.keySet()) {

                        display.setText(k);


                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } else
            display.setText("No nearby parking available!!");



    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.map.parkingmgt/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.map.parkingmgt/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




}


