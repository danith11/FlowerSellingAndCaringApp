package com.s22010334.finalproject.Activities;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.s22010334.finalproject.Helper.Fetchdata;
//import com.s22010334.finalproject.R;
//import com.s22010334.finalproject.databinding.ActivityMapBinding;
//import com.s22010334.finalproject.databinding.ActivityMaps2Binding;
//
//public class Maps2Activity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    ActivityMaps2Binding binding;
//    //private ActivityMapBinding binding;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int Request_code = 101;
//    private SupportMapFragment supportMapFragment;
//    private double lat , lng ;
//    Button findBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_maps2);
//        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        //findBtn = findViewById(R.id.buttonMap);
//        findBtn = binding.buttonMap;
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
//
//        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        supportMapFragment.getMapAsync(this);
//
//        findBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringBuilder stringBuilder = new StringBuilder("http://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//                stringBuilder.append("Location" + lat+"'"+ lng);
//                stringBuilder.append("&radius = 1000");
//                stringBuilder.append("&types=flowershop");
//                stringBuilder.append("&sensor=true");
//                stringBuilder.append("&key"+getResources().getString(R.string.API_KEy));
//
//                String url = stringBuilder.toString();
//                Object datafetch[] = new Object[2];
//                datafetch[0] = mMap;
//                datafetch[1] = url;
//
//                Fetchdata fetchdata = new Fetchdata();
//                fetchdata.execute(datafetch);
//
//            }
//        });
//    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        mMap =googleMap;
//        getCurrentLocation();
//    }
//
//    public void getCurrentLocation(){
//        if (ActivityCompat.checkSelfPermission(this
//                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//        && ActivityCompat.checkSelfPermission(this
//                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
//            return;
//        }
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(60000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setFastestInterval(5000);
//        LocationCallback locationCallback = new LocationCallback(){
//            //To recieve the notification
//
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                Toast.makeText(getApplicationContext(), "Location Result is "+locationResult, Toast.LENGTH_SHORT).show();
//
//                if (locationResult == null){
//                    Toast.makeText(getApplicationContext(), "Current Location is null ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                for (Location location:locationResult.getLocations()){
//                    if (location != null){
//                        Toast.makeText(getApplicationContext(), "Current Location is "+location.getLongitude(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        };
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location!= null){
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//                    LatLng latLng = new LatLng(lat,lng);
//                    mMap.addMarker(new MarkerOptions().position(latLng).title("current location"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
//                }
//            }
//        });
//    }
//
//    //TO get the current location
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (Request_code){
//            case Request_code:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    getCurrentLocation();
//                }
//        }
//    }
//}