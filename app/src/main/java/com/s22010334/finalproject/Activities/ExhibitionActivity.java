package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Adapter.EventAdapter;
import com.s22010334.finalproject.Helper.AddNewTask;
import com.s22010334.finalproject.Model.eventModel;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.ActivityMapBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExhibitionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView addTask;
    FirebaseDatabase database;
    DatabaseReference reference;
    //ActivityMapBinding binding;
    EventAdapter adapter;
    List<eventModel> mList;
    FirebaseAuth firebaseAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition);
        addTask = findViewById(R.id.addTask);
        recyclerView = findViewById(R.id.taskRecycler);
        database = FirebaseDatabase.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExhibitionActivity.this));
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        showData();
    }

    private void showData() {
        mList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ExhibitionsApproved");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    eventModel eventModel = ds.getValue(eventModel.class);

                    mList.add(eventModel);
                }
                adapter = new EventAdapter(ExhibitionActivity.this, mList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
//    private void loadMyInfo(){
////        Log.d(TAG,"LoadMyInfo");
//
//        reference = FirebaseDatabase.getInstance().getReference("Exhibition");
//        reference.child("place")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        String place = ""+ snapshot.child("place").getValue();
//                        String date = ""+ snapshot.child("date").getValue();
//                        String time = ""+ snapshot.child("time").getValue();
//
//
//                        binding.setText(place);
//                        binding.editEmail.setText(date);
//                        binding.editPhoneNumber.setText(time);
//
//
//
//                        try {
//                            Glide.with(ExhibitionActivity.this)
//                                    .load(imageUrl)
//                                    .placeholder(R.drawable.man)
//                                    .into(binding.editProfilePhoto);
//
//                        }catch (Exception e){
////                            Log.e(TAG,"onDataChange: ",e);
//                            Toast.makeText(ExhibitionActivity.this, "No data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }
