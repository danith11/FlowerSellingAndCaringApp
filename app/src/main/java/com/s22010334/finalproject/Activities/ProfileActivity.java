package com.s22010334.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.s22010334.finalproject.R;

public class ProfileActivity extends AppCompatActivity {
    TextView sell,goToExhibition,goToStoreMap ,gotoUpload , profileEdit;
    Button logoutButton;
    ImageView backtoHome;
    FirebaseAuth auth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sell = findViewById(R.id.textViewProfileSell);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutButton= findViewById(R.id.buttonLogOut);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        goToExhibition = findViewById(R.id.textViewProfileExhibition);
        goToStoreMap = findViewById(R.id.textViewProfileStoremap);
        gotoUpload = findViewById(R.id.textViewProfileSell);
        backtoHome = findViewById(R.id.imageView2);
        profileEdit = findViewById(R.id.textViewProfileAccountinfo);
        //checking the user == null
        if (user == null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        //Logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Again back to Home from Profile
        backtoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditAccountInfo.class);
                startActivity(intent);
                finish();
            }
        });
        //Go to sell
        gotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Go to store map
        goToStoreMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Map3Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //Go to exhibition
        goToExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ExhibitionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}