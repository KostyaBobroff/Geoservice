package com.example.costa.geoservice;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    LocationManager location;
    LocationRequest mLocationRequest;

    TextView mLatitudeText, mLongitudeText;
    private Location mLastLocation;

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle connectionHint) {
        startLocationUpdates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mLatitudeText = (TextView) findViewById(R.id.textView3);
        mLongitudeText = (TextView) findViewById(R.id.textView2);
//        isConnect(this);
//        CheckLocation(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
        }
    }
    public static boolean isConnect(Context ctx) {

        final ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Toast.makeText(ctx,"Передача данных включена",Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(ctx,"Передача данных выключена.Пожалуйста включите ее",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void CheckLocation(Context context){
       // location.isProviderEnabled()
        location = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        if(location.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getApplicationContext(),"GPS включен",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(),"GPS не включен.Пожалуйста включите GPS!",Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void onConnected(Bundle connectionHint) {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            if (mLastLocation != null) {
//                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//               // mLongitudeText.setText("https://www.google.ru/maps/"+String.valueOf(mLastLocation.getLatitude())+","+String.valueOf(mLastLocation.getLongitude()));
//            }
//
//    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(getApplicationContext(),"Позиция изменилась",Toast.LENGTH_LONG).show();
    }
}
