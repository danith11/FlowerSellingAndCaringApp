package com.s22010334.finalproject.Activities;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.s22010334.finalproject.databinding.ActivitySignupBinding;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextUsername, editTextMobileNumber;
    Button signupBtn, backtologinBtn;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ActivitySignupBinding binding;
    FirebaseUser firebaseUser;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//        mAuth = FirebaseAuth.getInstance();
//        editTextEmail = findViewById(R.id.signUpEmail);
//        editTextPassword = findViewById(R.id.signupPassword);
//        editTextUsername = findViewById(R.id.signupUsername);
//        editTextMobileNumber = findViewById(R.id.signupMNumber);
//        signupBtn = findViewById(R.id.signUpBtn);
//        backtologinBtn = findViewById(R.id.backtoLoginBtn);
//        mAuth = FirebaseAuth.getInstance();
//
//        //SignUp Button Implementation
//        signupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //database Reference
//                database = FirebaseDatabase.getInstance();
//                myRef = database.getReference("Users");
//
//                String name = editTextUsername.getText().toString().trim();
//                String email = editTextEmail.getText().toString().trim();
//                String mobile = editTextMobileNumber.getText().toString().trim();
//                String password = editTextPassword.getText().toString().trim();
//
//                //Validation
//                if (TextUtils.isEmpty(name)){
//                    editTextUsername.setError("Username is Required!");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(email)){
//                    editTextEmail.setError("Email is Required!");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(mobile)){
//                    editTextMobileNumber.setError("Mobile Number is Required!");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)){
//                    editTextPassword.setError("Password is Required!");
//                    return;
//                }
//                if (password.length() < 8){
//                    editTextPassword.setError("Password must be >= 8 Characters");
//                    return;
//                }
//
//                //Firebase Auth
//                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//
//                            Toast.makeText(SignUpActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                        }else {
//                            Toast.makeText(SignUpActivity.this,"Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                HelperClass helperClass = new HelperClass(name,email,mobile,password);
//                myRef.child(name).setValue(helperClass);
//            }
//        });
//
//        backtologinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextEmail = binding.signUpEmail;
        editTextPassword = binding.signupPassword;
        editTextUsername = binding.signupUsername;
        editTextMobileNumber = binding.signupMNumber;
        signupBtn = binding.signUpBtn;

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                firebaseUser = mAuth.getCurrentUser();

                String name = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String mobile = editTextMobileNumber.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    editTextUsername.setError("Username is Required!");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email is Required!");
                    return;
                }

                if (TextUtils.isEmpty(mobile)) {
                    editTextMobileNumber.setError("Mobile Number is Required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is Required!");
                    return;
                }
                if (password.length() < 8) {
                    editTextPassword.setError("Password must be >= 8 Characters");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        //myRef = database.getReference("Users");
                        myRef = FirebaseDatabase.getInstance().getReference("Users");
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("Email", editTextEmail.getText().toString());
                        userInfo.put("Name", editTextUsername.getText().toString());
                        userInfo.put("Phone Number", editTextMobileNumber.getText().toString());
                        userInfo.put("Password", editTextPassword.getText().toString());
//                        userInfo.put("isuser", "1");

                        myRef.push().setValue(userInfo);

                        Toast.makeText(SignUpActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    }
                });
            }
        });

    }
}