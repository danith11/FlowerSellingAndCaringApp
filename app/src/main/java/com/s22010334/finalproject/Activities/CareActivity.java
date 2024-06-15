package com.s22010334.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.ActivityCareBinding;

public class CareActivity extends AppCompatActivity {

    ActivityCareBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_care);
        binding = ActivityCareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.backtoHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}