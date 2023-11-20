package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nomad.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity {
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            LatLng marker = new LatLng(0, 0);
            googleMap.addMarker(new MarkerOptions().position(marker).title("Markeeer"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        });

    }
}