package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.s22010334.finalproject.R;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private Button verifyBtn;
    private TextView resendText;
    private ProgressBar progressBar;
    private String verificationId;
    private String phoneNumber;
    private boolean resendEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        resendText = findViewById(R.id.resend_text);
        verifyBtn = findViewById(R.id.otp_verify_btn);
        progressBar = findViewById(R.id.otp_progressBar);

        phoneNumber = getIntent().getStringExtra("mobile");
        verificationId = getIntent().getStringExtra("verificationId");

        //Checking the number is null or not
        if (phoneNumber != null){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            signInWithCredential(phoneAuthCredential);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(OtpVerificationActivity.this,"Verification failed: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            verificationId = newVerificationId;
                            // Optional: Update UI to indicate OTP sent (not shown)
                        }
                    }
            );
        }

        setupOTPInputs();

        //Verify Button
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp1.getText().toString().trim().isEmpty()
                        || otp2.getText().toString().trim().isEmpty()
                        || otp3.getText().toString().trim().isEmpty()
                        || otp4.getText().toString().trim().isEmpty()
                        || otp5.getText().toString().trim().isEmpty()
                        || otp6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpVerificationActivity.this, "Please enter valid code", Toast.LENGTH_LONG).show();
                    return;
                }
                String code =
                        otp1.getText().toString() +
                                otp2.getText().toString() +
                                otp3.getText().toString() +
                                otp4.getText().toString() +
                                otp5.getText().toString() +
                                otp6.getText().toString();

                if (verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    signInWithCredential(phoneAuthCredential);
                }
            }
        });

        //Resend the code
        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificationId != null){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpVerificationActivity.this,"OTP code resent", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(OtpVerificationActivity.this, "Verification successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OtpVerificationActivity.this, ResetPasswordActivity.class));
                        }else {
                            progressBar.setVisibility(View.GONE);
                            verifyBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(OtpVerificationActivity.this, "Invalid code!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setupOTPInputs() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}