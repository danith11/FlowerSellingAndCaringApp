package com.s22010334.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Adapter.EventAdapter;
import com.s22010334.finalproject.Adapter.PopularAdapter;
import com.s22010334.finalproject.Model.PopularModel;
import com.s22010334.finalproject.Model.eventModel;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.ActivityMainBinding;
import com.s22010334.finalproject.Domain.itemDomain;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    public ActivityMainBinding binding;
    private FirebaseDatabase database;
    PopularAdapter adapter;
    List<PopularModel> mList;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        recyclerView= findViewById(R.id.recycleViewPopular);

        showData();
    }
    
    private void showData(){
        mList= new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Images");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    PopularModel popularModel = ds.getValue(PopularModel.class);

                    mList.add(popularModel);
                }
                adapter = new PopularAdapter(MainActivity.this, mList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onclickCare(){
        Intent intent = new Intent(getApplicationContext(),CareActivity.class);
        startActivity(intent);
        finish();
    }

    public void onclickProfile(View v) {
        Intent i = new Intent();
        i.setClass(this, ProfileActivity.class);
        startActivity(i);
    }

}