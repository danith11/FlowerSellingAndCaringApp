package com.s22010334.finalproject.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.s22010334.finalproject.databinding.ActivityEditAccountInfoBinding;

import java.util.HashMap;


public class EditAccountInfo extends AppCompatActivity {

    private ActivityEditAccountInfoBinding binding;
    private static final String TAG = "PROFILE_EDIT_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditAccountInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadMyInfo();

        binding.saveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();

            }
        });

        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDeleteActivity();
            }
        });
    }

    private String name = "";
    private String email = "";
    private String phone = "";
    private String password = "";
    private void validateData(){
        name = binding.editUserName.getText().toString().trim();
        email = binding.editEmail.getText().toString().trim();
        phone = binding.editPhoneNumber.getText().toString().trim();
        password = binding.editPassword.getText().toString().trim();
        updateProfileDb();
    }

    private void updateProfileDb(){

        progressDialog.setMessage("Updating user info...");
        progressDialog.show();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("email", ""+ email);
        hashMap.put("mobile", ""+ phone);
        hashMap.put("name", ""+ name);
        hashMap.put("password", ""+ password);


        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"onSuccess: Info updated");
                        progressDialog.dismiss();
                        Toast.makeText(EditAccountInfo.this, "Profile Updated Succesfully", Toast.LENGTH_SHORT).show();
                        moveHomeActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure: ",e);
                        progressDialog.dismiss();

                        Toast.makeText(EditAccountInfo.this, "Failed to update, "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void moveHomeActivity(){
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void moveDeleteActivity(){
        startActivity(new Intent(this, DeleteAccountActivity.class));
    }

    //Load users deatils to editText hint
    private void loadMyInfo(){
        Log.d(TAG,"LoadMyInfo");

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name = ""+ snapshot.child("name").getValue();
                        String email = ""+ snapshot.child("email").getValue();
                        String phone = ""+ snapshot.child("mobile").getValue();
                        String password = ""+ snapshot.child("password").getValue();

                        binding.editUserName.setText(name);
                        binding.editEmail.setText(email);
                        binding.editPhoneNumber.setText(phone);
                        binding.editPassword.setText(password);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}