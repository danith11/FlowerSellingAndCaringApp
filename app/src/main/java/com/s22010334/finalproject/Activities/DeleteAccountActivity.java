// package com.s22010334.finalproject.Activities;

// import androidx.annotation.NonNull;
// import androidx.appcompat.app.AppCompatActivity;

// import android.app.ProgressDialog;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.View;
// import android.widget.Toast;

// import com.google.android.gms.tasks.OnFailureListener;
// import com.google.android.gms.tasks.OnSuccessListener;
// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.database.DataSnapshot;
// import com.google.firebase.database.DatabaseError;
// import com.google.firebase.database.DatabaseReference;
// import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.database.ValueEventListener;
// import com.s22010334.finalproject.R;
// import com.s22010334.finalproject.databinding.ActivityDeleteAccountBinding;

// public class DeleteAccountActivity extends AppCompatActivity {

//     private ActivityDeleteAccountBinding binding;
//     private static final String TAG = "DELETE_ACCOUNT_TAG";
//     private ProgressDialog progressDialog;
//     private FirebaseAuth firebaseAuth;
//     private FirebaseUser firebaseUser;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);

//         binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
//         setContentView(binding.getRoot());

//         progressDialog = new ProgressDialog(this);
//         progressDialog.setTitle("Please wait...");
//         progressDialog.setCanceledOnTouchOutside(false);

//         firebaseAuth = FirebaseAuth.getInstance();
//         firebaseUser = firebaseAuth.getCurrentUser();

//         binding.deleteConfirmBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 deleteAccount();
//             }
//         });
//     }

//     private void deleteAccount(){
//         Log.d(TAG,"deleteAccount: ");

//         String myUid = firebaseAuth.getUid();

//         progressDialog.setMessage("Deleting User Account");
//         progressDialog.show();

//         firebaseUser.delete()
//                 .addOnSuccessListener(new OnSuccessListener<Void>() {
//                     @Override
//                     public void onSuccess(Void unused) {
//                         Log.d(TAG,"onSuccess: Account deleted");

//                         progressDialog.setMessage("Deleting User Ads");

//                         DatabaseReference refUserAds = FirebaseDatabase.getInstance().getReference("Users");
//                         refUserAds.orderByChild("uid").equalTo(myUid)
//                                 .addListenerForSingleValueEvent(new ValueEventListener() {
//                                     @Override
//                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                         for (DataSnapshot ds: snapshot.getChildren()){
//                                             ds.getRef().removeValue();
//                                         }

//                                         progressDialog.setMessage("Deleting User Data");

//                                         DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("Users");
//                                         refUsers.child(myUid)
//                                                 .removeValue()
//                                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                     @Override
//                                                     public void onSuccess(Void unused) {
//                                                         Log.d(TAG,"onSuccess: User data deleted...");
//                                                         Toast.makeText(DeleteAccountActivity.this, "Deleted....!", Toast.LENGTH_SHORT).show();
//                                                         startGetStartActivity();
//                                                     }
//                                                 })
//                                                 .addOnFailureListener(new OnFailureListener() {
//                                                     @Override
//                                                     public void onFailure(@NonNull Exception e) {
//                                                         Log.e(TAG,"onFailure: ",e);
//                                                         progressDialog.dismiss();
//                                                         Toast.makeText(DeleteAccountActivity.this, "failed to delete ,"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                         startGetStartActivity();
//                                                     }
//                                                 });
//                                     }

//                                     @Override
//                                     public void onCancelled(@NonNull DatabaseError error) {

//                                     }
//                                 });
//                     }
//                 })
//                 .addOnFailureListener(new OnFailureListener() {
//                     @Override
//                     public void onFailure(@NonNull Exception e) {
//                         Log.d(TAG,"onFailure: ", e);
//                         progressDialog.dismiss();
//                         Toast.makeText(DeleteAccountActivity.this, "Failed to delete the account "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                     }
//                 });
//     }

//     private void startGetStartActivity(){
//         Log.d(TAG, "startGetStartActivity: ");

//         startActivity(new Intent(this, StartActivity.class));
//         finishAffinity();
//     }
// }

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.databinding.ActivityDeleteAccountBinding;

public class DeleteAccountActivity extends AppCompatActivity {

    private ActivityDeleteAccountBinding binding;
    private static final String TAG = "DELETE_ACCOUNT_TAG";
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        binding.deleteConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void deleteAccount() {
        Log.d(TAG, "deleteAccount: ");

        String myUid = firebaseAuth.getUid();

        progressDialog.setMessage("Deleting User Account");
        progressDialog.show();

        // Delete the user's uploads first
        deleteUploads(myUid);
    }

    private void deleteUploads(String myUid) {
        DatabaseReference refUserUploads = FirebaseDatabase.getInstance().getReference("uploads");
        refUserUploads.orderByChild("userId").equalTo(myUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ds.getRef().removeValue();
                        }
                        Log.d(TAG, "onSuccess: User uploads deleted...");
                        // Now delete the user account
                        deleteUserAccount(myUid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                        progressDialog.dismiss();
                        Toast.makeText(DeleteAccountActivity.this, "Failed to delete uploads: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteUserAccount(String myUid) {
        firebaseUser.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Account deleted");

                        progressDialog.setMessage("Deleting User Data");

                        DatabaseReference refUsers = FirebaseDatabase.getInstance().getReference("Users");
                        refUsers.child(myUid)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: User data deleted...");
                                        Toast.makeText(DeleteAccountActivity.this, "Deleted....!", Toast.LENGTH_SHORT).show();
                                        startGetStartActivity();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "onFailure: ", e);
                                        progressDialog.dismiss();
                                        Toast.makeText(DeleteAccountActivity.this, "Failed to delete, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        startGetStartActivity();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ", e);
                        progressDialog.dismiss();
                        Toast.makeText(DeleteAccountActivity.this, "Failed to delete the account " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startGetStartActivity() {
        Log.d(TAG, "startGetStartActivity: ");

        startActivity(new Intent(this, StartActivity.class));
        finishAffinity();
    }
}
