package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.s22010334.finalproject.R;

import java.util.concurrent.TimeUnit;

public class ForgetPassActivity extends AppCompatActivity {

    private Button sendCodeBtn;
    private EditText forgotPwdMobile;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        sendCodeBtn = findViewById(R.id.send_code_button);
        forgotPwdMobile = findViewById(R.id.mobile_for_forgot_password);
        progressBar = findViewById(R.id.forgot_progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = forgotPwdMobile.getText().toString().trim();

                if (phone.isEmpty()){
                    Toast.makeText(ForgetPassActivity.this , "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                sendCodeBtn.setVisibility(View.INVISIBLE);

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(phone)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(ForgetPassActivity.this)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        sendCodeBtn.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(getApplicationContext(), OtpVerificationActivity.class);
                                        intent.putExtra("mobile", forgotPwdMobile.getText().toString());
                                        intent.putExtra("verificationId",verificationId);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        sendCodeBtn.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        sendCodeBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(ForgetPassActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });
    }
}
