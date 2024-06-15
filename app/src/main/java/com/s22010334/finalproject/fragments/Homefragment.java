package com.s22010334.finalproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Adapter.PlantAdapter;
import com.s22010334.finalproject.Domain.PlantDomain;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.FragmentHomefragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class Homefragment extends Fragment {
    private FragmentHomefragmentBinding binding;
    private Context mContext;
    private RecyclerView recyclerView;
    private PlantAdapter plantAdapter;
    private List<PlantDomain> plantList;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        binding = FragmentHomefragmentBinding.inflate(inflater,container,false);
//        return binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        recyclerView = view.findViewById(R.id.recycleViewPopular);
        progressBar = view.findViewById(R.id.progressBarPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        plantList = new ArrayList<>();
        plantAdapter = new PlantAdapter(plantList, getContext());
        recyclerView.setAdapter(plantAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("HomeFragment","onDataChange");
                plantList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlantDomain plant = snapshot.getValue(PlantDomain.class);
                    if (plant != null) {
                        plant.setId(snapshot.getKey()); // Set the ID from the snapshot key
                        plantList.add(plant);
                    }
                }
                plantAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HomeFragment","onCancelled");
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }

}