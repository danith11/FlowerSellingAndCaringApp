package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.s22010334.finalproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity {

//    GoogleMap map;
//    SupportMapFragment supportMapFragment;
//    FusedLocationProviderClient fusedLocationClient;
//    double currentLat = 0 , currentLong = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragmentContainerViewMap);
//
//        String placetype = "flower_shop";
//        String placename = "FLOWER_SHOP";
//        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"+
//                "?location+" + currentLat + ","+currentLong +
//                "&radius=5000" +
//                "&types+" + placetype +
//                "&sensor=true"+
//                "&key" +getResources().getString(R.string.API_KEy);
//
//        new PlaceTask().execute(url);
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (ActivityCompat.checkSelfPermission(MapActivity.this
//                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            //if permission is grabnted then call this method
//            getCurrentLocation();
//        }
//        else{
//            //Permission not granted
//            ActivityCompat.requestPermissions(MapActivity.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//        }
//
//    }
//
//    private void getCurrentLocation() {
//        //Innitialize task location
//        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationClient.getLastLocation(); //10.29
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                //When success
//                if (location != null){
//                    // When location is not null
//                    currentLat = location.getLatitude();
//                    currentLong = location.getLongitude();
//
//                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(@NonNull GoogleMap googleMap) {
//                            //When map is ready
//                            map= googleMap;
//                            //Zooming the current location
//                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(currentLat,currentLong),10
//                            ));
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//        @SuppressLint("MissingSuperCall")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 44){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                //When permission granted
//                getCurrentLocation();
//            }
//        }
//    }
//
//    private class PlaceTask extends AsyncTask<String , Integer , String > {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String data = null;
//            //Initialize data
//            try {
//                data = downloadURl(strings[0]);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            //Execute the parsar task
//            new ParsarTask().execute(s);
//        }
//    }
//
//    private String downloadURl(String string) throws IOException {
//        URL url = new URL(string);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
//        InputStream stream = connection.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        StringBuilder builder = new StringBuilder();
//        String line = "";
//        while ((line = reader.readLine()) != null){
//            builder.append(line);
//        }
//        String data = builder.toString();
//        reader.close();
//        return data;
//    }
//
//    private class ParsarTask extends AsyncTask<String , Integer, List<HashMap<String,String>>> {
//
//        @Override
//        protected List<HashMap<String, String>> doInBackground(String... strings) {
//            //Create Json parsar class
//            JsonPsrsar jsonPsrsar = new JsonPsrsar();
//            List <HashMap<String,String>> mapList = null;
//            JSONObject object = null;
//            try {
//                 object = new JSONObject(strings[0]);
//                 mapList = jsonPsrsar.parseresult(object);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//            return mapList;
//        }
//
//        @Override
//        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
//            map.clear();
//            for (int i= 0 ;i< hashMaps.size();i++){
//                HashMap<String,String > hashMapList = hashMaps.get(i);
//                double lat = Double.parseDouble(hashMapList.get("lat"));
//                double lng = Double.parseDouble(hashMapList.get("lng"));
//                String name = hashMapList.get("name");
//                LatLng latLng = new LatLng(lat,lng);
//                MarkerOptions  options = new MarkerOptions();
//                options.position(latLng);
//                options.title(name);
//                map.addMarker(options);
//            }
//        }
//    }
}