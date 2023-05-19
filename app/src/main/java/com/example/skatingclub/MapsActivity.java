package com.example.skatingclub;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.skatingclub.databinding.ActivityMapsBinding;
import java.util.Objects;

public class MapsActivity extends DrawerBaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.skatingclub.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Карта");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng goldenPuck = new LatLng(53.184547126126596, 45.06522101250114);
        mMap.addMarker(new MarkerOptions().position(goldenPuck).title("Золотая шайба, ледово-спортивный комплекс. Открыто: до 22:00"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(goldenPuck));

        LatLng majorLeague = new LatLng(53.184547126126596, 45.06522101250114);
        mMap.addMarker(new MarkerOptions().position(majorLeague).title("\"Высшая лига\", ул. Московская, 37, 5 этаж. Открыто: до 21:00"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(majorLeague));

        LatLng sportsPalace = new LatLng(53.19048864239309, 44.99854863183193);
        mMap.addMarker(new MarkerOptions().position(sportsPalace).title("Дворец спорта \"Рубин\", ул. Революционная, 9. Открыто: до 22:00"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sportsPalace));
    }
}