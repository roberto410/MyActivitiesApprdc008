package com.bignerdranch.android.MyActivitiesApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by rdc008 on 24/10/2017.
 *
 * This activity creates and displays the google map fragment
 */


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent;
    private Bundle extras;
    private String latitudeSting;
    private String longitudeString;
    private Double latitudeDouble;
    private Double longitudeDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        extras = intent.getExtras();
        latitudeSting = extras.getString("com.bignerdranch.android.MyActivitiesApp.map_lat");
        longitudeString = extras.getString("com.bignerdranch.android.MyActivitiesApp.map_long");
        latitudeDouble = Double.parseDouble(latitudeSting);
        longitudeDouble = Double.parseDouble(longitudeString);

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(latitudeDouble, longitudeDouble);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}