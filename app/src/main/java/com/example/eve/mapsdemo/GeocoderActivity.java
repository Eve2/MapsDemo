package com.example.eve.mapsdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class GeocoderActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public Button geobutton, toRx;
    public Location mLastLocation;
    public AddressResultReceiver mResultReceiver;
    public GoogleApiClient googleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geobutton = (Button) findViewById(R.id.geocoder);
        toRx = (Button) findViewById(R.id.toRx);

        geobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntentService();
            }
        });

        toRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      Intent i = new Intent(GeocoderActivity.this, RxValidatorActivity.class);
           //     startActivity(i);
            }
        });

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

    public void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

            }
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);

        if (mLastLocation != null) {
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available,
                        Toast.LENGTH_LONG).show();
                return;
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            Toast.makeText(GeocoderActivity.this, mAddressOutput, Toast.LENGTH_LONG).show();

            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(GeocoderActivity.this, getString(R.string.address_found), Toast.LENGTH_LONG).show();
            }
        }
    }
}
