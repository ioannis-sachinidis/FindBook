package com.example.findbook;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public LocationClass location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final String booktile = getIntent ().getExtras ().getString ("booktitle");
        final String locationName = getIntent ().getExtras ().getString ("location");
        location = new LocationService ().getLocationFromAPI (locationName);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        float lng = location.getLng ();
        float lat = location.getLat ();
        String address = location.getAddress ();


        LatLng addr = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(addr).title(address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(addr));
    }
}