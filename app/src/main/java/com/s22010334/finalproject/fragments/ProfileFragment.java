package com.s22010334.finalproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.s22010334.finalproject.Activities.EditAccountInfo;
import com.s22010334.finalproject.Activities.ExhibitionActivity;
import com.s22010334.finalproject.Activities.LoginActivity;
import com.s22010334.finalproject.Activities.Map3Activity;
import com.s22010334.finalproject.Activities.UploadActivity;
import com.s22010334.finalproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    }

    public ProfileFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        //Logout
        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccLogout();
            }
        });

        // Navigate to upload items
        binding.textViewProfileSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UploadActivity.class));
            }
        });

        // Navigate to exhibition
        binding.textViewProfileExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ExhibitionActivity.class));
            }
        });

        // Navigate to edit account
        binding.textViewProfileAccountinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EditAccountInfo.class));
            }
        });

        // Navigate to map
        binding.textViewProfileStoremap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Map3Activity.class));
            }
        });
    }

    //Logout function
    public void AccLogout(){
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}