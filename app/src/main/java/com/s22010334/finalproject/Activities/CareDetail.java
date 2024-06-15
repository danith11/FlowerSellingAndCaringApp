package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Domain.CareDomain;
import com.s22010334.finalproject.R;

public class CareDetail extends AppCompatActivity {

    TextView plantName, plantInstruction;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_detail);

        plantName = findViewById(R.id.plant_name);
        plantInstruction = findViewById(R.id.plant_care);

        name = getIntent().getStringExtra("name");
        if (name == null) {
            plantName.setText("No plant name provided");
            plantInstruction.setText("No plant care information available");
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Care");

        ref.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("CareDetail", "Snapshot: " + snapshot.toString());
                    CareDomain careDomain = snapshot.getValue(CareDomain.class);
                    if (careDomain != null) {
                        Log.d("CareDetail", "CareDomain: " + careDomain.getCare());
                        plantName.setText(careDomain.getName());
                        plantInstruction.setText(careDomain.getCare());
                    } else {
                        Log.d("CareDetail", "CareDomain is null");
                        plantName.setText("No data found");
                        plantInstruction.setText("No data found");
                    }
                } else {
                    Log.d("CareDetail", "Snapshot does not exist");
                    plantName.setText("No data found");
                    plantInstruction.setText("No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                plantName.setText("Failed to retrieve data");
                plantInstruction.setText("Failed to retrieve data");
            }
        });
    }
}

