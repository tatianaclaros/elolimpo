package com.example.webf1movil1704;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class activity5 extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);


        Button iniciovolver = findViewById(R.id.iniciovolver);
        Button volvercon = findViewById(R.id.volvercon);


        iniciovolver.setOnClickListener(v -> {

            Intent intent = new Intent(activity5.this, MainActivity.class);
            startActivity(intent);
        });


        volvercon.setOnClickListener(v -> {

            Intent intent = new Intent(activity5.this, activity4.class);
            startActivity(intent);
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng Bogota = new LatLng(4.514748337789864, -74.11555928535337);
        mMap.addMarker(new MarkerOptions().position(Bogota).title("Cebtro Comercial santa librada, local 111"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bogota, 15));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }
}



