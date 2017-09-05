package com.example.eve.mapsdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    public GoogleMap mMap;
    public Button nextbutton;
//    double lat1, lng1;
    public LocationManager lm;
    double lat1 = 41.9171;
    double lng1 = -88.265;
//    String provider;
//    Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 //       provider = lm.getBestProvider(new Criteria(), false);
 //       location = lm.getLastKnownLocation(provider);

        nextbutton = (Button) findViewById(R.id.nextbutton);

        checkPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, GeocoderActivity.class);
                startActivity(i);
            }
        });

/*
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lat1 = location.getLatitude();
                            lng1 = location.getLongitude();
                        }
                    }
                });
*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkPermission();

        LatLng rjt = new LatLng(41.917153, -88.2676359);
        mMap.addMarker(new MarkerOptions().position(rjt).title("RJT"));

        LatLng blue = new LatLng(41.92, -88.2676);
        mMap.addMarker(new MarkerOptions()
                .position(blue)
                .title("blue")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng image = new LatLng(41.917, -88.27);
        mMap.addMarker(new MarkerOptions()
                .position(image)
                .title("image")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.wink_marker)));

        LatLng current = new LatLng(lat1, lng1);
        mMap.addMarker(new MarkerOptions()
                .position(current)
                .title("current")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));


        mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(blue, image));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rjt));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }



    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
        }

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
