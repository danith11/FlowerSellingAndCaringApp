package com.s22010334.finalproject.fragments;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Adapter.CareAdapter;
import com.s22010334.finalproject.Domain.CareDomain;;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.FragmentCareBinding;

import java.util.ArrayList;
import java.util.List;

public class CareFragment extends Fragment {

    private Context mContext;
    FragmentCareBinding binding;
    private CareAdapter careAdapter;
    private RecyclerView recyclerView;
    private List<CareDomain> careDomainList;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_care, container, false);

        recyclerView = view.findViewById(R.id.recycleViewCare);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        careDomainList = new ArrayList<>();
        careAdapter= new CareAdapter(careDomainList, getContext());
        recyclerView.setAdapter(careAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Care");
        Log.d("HomeFragment","Refering Care Data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("CareFragment","onDataChange(DataSnapShot)");
                careDomainList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CareDomain plant = snapshot.getValue(CareDomain.class);
                    careDomainList.add(plant);
                }
                careAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("CareFragment","onCancelled");
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}