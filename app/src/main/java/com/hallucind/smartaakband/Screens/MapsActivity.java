package com.hallucind.smartaakband.Screens;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.R;
import com.hallucind.smartaakband.RecyclerListAdapter;
import com.hallucind.smartaakband.RecyclerListAdapterHorizontal;
import com.hallucind.smartaakband.Settings;
import com.hallucind.smartaakband.Utils.Prefs;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private GoogleMap mMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initRecyclerView();
        getLocationPermission();
        initDeviceLocation();

        if (Settings.showMapInformationBox) {
            showPopup();
            Settings.showMapInformationBox = false;
        }
    }

    // Hämtar enhetens position
    private void initDeviceLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    getDeviceLocation(location);
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
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    getDeviceLocation(location);
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
            });
        }
    }

    // Sätter ut en marker på kartan
    private void getDeviceLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
       // Geocoder geocoder = new Geocoder(getApplicationContext());

    //    try {
          //  List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
         //   String str = addressList.get(0).getLocality() + " , ";
        //    mMap.addMarker(new MarkerOptions().position(latLng).title(str));
           mMap.addMarker(new MarkerOptions()
                   .position(latLng)
                   .title("Din plats")
                   // .icon(BitmapDescriptorFactory.defaultMarker(100))
                    );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
      //  } catch (IOException e) {
     //       e.printStackTrace();
     //   }
    }

    // Skapar lista med de kopplade banden
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.nameList);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerListAdapterHorizontal adapter = new RecyclerListAdapterHorizontal(BandHandler.bands);
        recyclerView.setAdapter(adapter);
    }

    // Kollar om användaren tillåter appen att använda enhetens GPS (för demonstration)
    private void getLocationPermission() {
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // Visar kartan
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    // Kollar om användaren tillåter appen att använda enhetens GPS (för demonstration)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    initMap();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    // Initialisear tillbaka-knappen
    public void backButtonClick(View view) {
        Intent intent = new Intent(MapsActivity.this, BandsActivity.class);
        startActivity(intent);
        MapsActivity.this.finish();
    }

    // Visar ett popup-fönster som informerar hur man får information de kopplade banden
    private void showPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_information);

        final TextView informationText = dialog.findViewById(R.id.informationText);
        final CheckBox checkBox = dialog.findViewById(R.id.informationCheckBox);
        final TextView okBtn = dialog.findViewById(R.id.informationOkBtn);


        informationText.setText(getResources().getIdentifier("map_information", "string", getPackageName()));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Prefs.saveToPrefs(MapsActivity.this, "showMapInformationBox", false);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MapsActivity.this, BandsActivity.class);
            startActivity(intent);
            MapsActivity.this.finish();
            return true;
        }
        return false;
    }
}