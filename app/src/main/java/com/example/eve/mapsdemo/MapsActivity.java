package com.example.eve.mapsdemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng rjt = new LatLng(41.917153, -88.2676359);
        mMap.addMarker(new MarkerOptions().position(rjt).title("CURRENT LOCATION"));

        LatLng blue = new LatLng(41.917, -88.2676);
        mMap.addMarker(new MarkerOptions()
                .position(blue)
                .title("blue")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng image = new LatLng(41.917, -88.27);
        mMap.addMarker(new MarkerOptions()
                .position(image)
                .title("image")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.wink_marker)));

        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(blue, image));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rjt));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }
}
