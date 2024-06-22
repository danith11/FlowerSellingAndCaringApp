
// package com.s22010334.finalproject.Activities;

// import androidx.annotation.NonNull;
// import androidx.appcompat.app.AppCompatActivity;

// import android.annotation.SuppressLint;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;
// import android.view.View;
// import android.widget.Button;
// import android.widget.ImageView;
// import android.widget.TextView;
// import android.widget.Toast;

// import com.bumptech.glide.Glide;
// import com.google.firebase.database.DataSnapshot;
// import com.google.firebase.database.DatabaseError;
// import com.google.firebase.database.DatabaseReference;
// import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.database.ValueEventListener;
// import com.s22010334.finalproject.Domain.CartDomain;
// import com.s22010334.finalproject.Domain.PlantDomain;
// import com.s22010334.finalproject.R;

// public class DetailActivity extends AppCompatActivity {

//     private ImageView plantImage;
//     private TextView plantName, plantPrice, plantDescription, plantAddress, plantPhone, plantQuantity;
//     private Button buttonAddToCart;
//     private DatabaseReference databaseReference;
//     String ID;

//     @SuppressLint("MissingInflatedId")
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_detail);

//         // Initialize views
//         plantImage = findViewById(R.id.PlantImage);
//         plantName = findViewById(R.id.PlantName);
//         plantPrice = findViewById(R.id.PlantPrice);
//         plantDescription = findViewById(R.id.PlantDescription);
//         plantAddress = findViewById(R.id.Address);
//         plantPhone = findViewById(R.id.phoneNumber);
//         plantQuantity = findViewById(R.id.Quantity);
//         buttonAddToCart = findViewById(R.id.buttonAddToCart);

//         // Get product ID from Intent
//         Intent intent = getIntent();
//         if (intent == null) {
//             Log.e("DetailActivity", "Intent is null");
//             Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         }

//         ID = intent.getStringExtra("pID");
//         if (ID == null || ID.isEmpty()) {
//             Log.e("DetailActivity", "Product ID is missing or empty");
//             Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show();
//             finish();
//             return;
//         } else {
//             Log.d("DetailActivity", "Product ID: " + ID);
//         }

//         DatabaseReference ref = FirebaseDatabase.getInstance().getReference("uploads");

//         ref.child(ID).addValueEventListener(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 Log.d("DetailActivity", "DataSnapshot received: " + snapshot.toString());
//                 if (snapshot.exists()) {
//                     PlantDomain plantDomain = snapshot.getValue(PlantDomain.class);
//                     if (plantDomain != null) {
//                         Log.d("DetailActivity", "PlantDomain: " + plantDomain.getName());

//                         // Set plant details
//                         plantName.setText(plantDomain.getName());
//                         plantPrice.setText("Rs."+plantDomain.getPrice()+".00");
//                         plantDescription.setText(plantDomain.getDescription());
//                         plantAddress.setText(plantDomain.getAddress());
//                         //plantPhone.setText(plantDomain.getPhoneNumber());
//                         plantPhone.setText(String.valueOf(plantDomain.getPhoneNumber()));
//                         plantQuantity.setText(plantDomain.getQuantity()+" left");

//                         // Log image URL
//                         String imageUrl = plantDomain.getImageUrl();
//                         if (imageUrl != null && !imageUrl.isEmpty()) {
//                             Log.d("DetailActivity", "Loading image URL: " + imageUrl);

//                             // Check if plantImage is not null before using it with Glide
//                             if (plantImage != null) {
//                                 Glide.with(DetailActivity.this)
//                                         .load(plantDomain.getImageUrl())
//                                         .placeholder(R.drawable.ic_loading) // Optional placeholder image
//                                         .error(R.drawable.ic_warning) // Error image in case of failure
//                                         .into(plantImage);
//                             } else {
//                                 Log.e("DetailActivity", "plantImage ImageView is null");
//                             }
//                         } else {
//                             Log.e("DetailActivity", "Image URL is null or empty, setting default image");
//                             plantImage.setImageResource(R.drawable.ic_warning); // Set a default image
//                         }
//                     } else {
//                         Log.e("DetailActivity", "PlantDomain is null");
//                         setDefaultPlantDetails();
//                     }
//                 } else {
//                     Log.e("DetailActivity", "Snapshot does not exist");
//                     setDefaultPlantDetails();
//                 }
//             }

//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//                 Log.e("DetailActivity", "onCancelled: " + error.getMessage());
//                 setDefaultPlantDetails();
//             }
//         });

//         buttonAddToCart.setOnClickListener(v -> {
//             CartDomain cartItem = new CartDomain();
//             String cartItemId = CartManager.getInstance().getCartReference().push().getKey();
//             if (cartItemId != null) {
//                 cartItem.setId(cartItemId);
//                 cartItem.setUploadId(ID);
//                 cartItem.setName(plantName.getText().toString());
//                 //cartItem.setPrice(plantPrice.getText().toString().replace("Rs.", "").replace(".00", ""));
//                 double price = Double.parseDouble(plantPrice.getText().toString().replace("Rs.", "").replace(".00", ""));
//                 cartItem.setPrice(price);
//                 cartItem.setQuantity(1);
//                 cartItem.setImageUrl(""); // Set the image URL properly if you have it

//                 CartManager.getInstance().addCartItem(cartItem);
//                 Toast.makeText(DetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
//             } else {
//                 Toast.makeText(DetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
//             }
//         });
//     }

//     private void setDefaultPlantDetails() {
//         plantName.setText("Default Name");
//         plantPrice.setText("Rs.0.00");
//         plantDescription.setText("Default Description");
//         plantAddress.setText("Default Address");
//         plantPhone.setText("Default Phone Number");
//         plantQuantity.setText("0 left");
//         plantImage.setImageResource(R.drawable.ic_warning);
//     }
// }

package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Domain.CartDomain;
import com.s22010334.finalproject.Domain.PlantDomain;
import com.s22010334.finalproject.R;

public class DetailActivity extends AppCompatActivity {

    private ImageView plantImage;
    private TextView plantName, plantPrice, plantDescription, plantAddress, plantPhone, plantQuantity;
    private Button buttonAddToCart;
    private ImageView phoneIcon; 
    private DatabaseReference databaseReference;
    private String ID;
    private String plantImageUrl; 
    private int currentQuantity; 

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize views
        plantImage = findViewById(R.id.PlantImage);
        plantName = findViewById(R.id.PlantName);
        plantPrice = findViewById(R.id.PlantPrice);
        plantDescription = findViewById(R.id.PlantDescription);
        plantAddress = findViewById(R.id.Address);
        plantPhone = findViewById(R.id.phoneNumber);
        plantQuantity = findViewById(R.id.Quantity);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);
        phoneIcon = findViewById(R.id.phoneIcon); 
        
        // Set click listener for the phone call 
        phoneIcon.setOnClickListener(v -> {
            String phoneNumber = plantPhone.getText().toString().replace("Contact Number - ", "").trim();
            if (!phoneNumber.isEmpty()) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            } else {
                Toast.makeText(DetailActivity.this, "Phone number is not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Get product ID from Intent
        Intent intent = getIntent();
        if (intent == null) {
            Log.e("DetailActivity", "Intent is null");
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ID = intent.getStringExtra("pID");
        if (ID == null || ID.isEmpty()) {
            Log.e("DetailActivity", "Product ID is missing or empty");
            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            Log.d("DetailActivity", "Product ID: " + ID);
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("uploads");

        ref.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("DetailActivity", "DataSnapshot received: " + snapshot.toString());
                if (snapshot.exists()) {
                    PlantDomain plantDomain = snapshot.getValue(PlantDomain.class);
                    if (plantDomain != null) {
                        Log.d("DetailActivity", "PlantDomain: " + plantDomain.getName());

                        // Set plant details
                        plantName.setText(plantDomain.getName());
                        plantPrice.setText("Rs." + plantDomain.getPrice());
                        plantDescription.setText(plantDomain.getDescription());
                        plantAddress.setText(plantDomain.getAddress());
                        plantPhone.setText("Contact Number - " + plantDomain.getPhoneNumber());
                        currentQuantity = plantDomain.getQuantity();
                        plantQuantity.setText(currentQuantity + " left");

                        // Get and log image URL
                        plantImageUrl = plantDomain.getImageUrl();
                        if (plantImageUrl != null && !plantImageUrl.isEmpty()) {
                            Log.d("DetailActivity", "Loading image URL: " + plantImageUrl);

                            // Check if plantImage is not null before using it with Glide
                            if (isActivityValid()) {
                                Glide.with(DetailActivity.this)
                                        .load(plantImageUrl)
                                        .placeholder(R.drawable.ic_loading) // Optional placeholder image
                                        .error(R.drawable.ic_warning) // Error image in case of failure
                                        .into(plantImage);
                            } else {
                                Log.e("DetailActivity", "plantImage ImageView is null");
                            }
                        } else {
                            Log.e("DetailActivity", "Image URL is null or empty, setting default image");
                            plantImage.setImageResource(R.drawable.ic_warning); // Set a default image
                        }
                    } else {
                        Log.e("DetailActivity", "PlantDomain is null");
                        setDefaultPlantDetails();
                    }
                } else {
                    Log.e("DetailActivity", "Snapshot does not exist");
                    setDefaultPlantDetails();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DetailActivity", "onCancelled: " + error.getMessage());
                setDefaultPlantDetails();
            }
        });

        // Add to cart
        buttonAddToCart.setOnClickListener(v -> {
            if (currentQuantity > 0) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String uid = user.getUid();
                    CartDomain cartItem = new CartDomain();
                    DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("carts").child(uid);
                    String cartItemId = cartReference.push().getKey();
                    if (cartItemId != null) {
                        cartItem.setId(cartItemId);
                        cartItem.setUploadId(ID);
                        cartItem.setName(plantName.getText().toString());
                        double price = Double.parseDouble(plantPrice.getText().toString().replace("Rs.", "").replace(".00", ""));
                        cartItem.setPrice(price);
                        cartItem.setQuantity(1);
                        cartItem.setImageUrl(plantImageUrl); // Use the stored image URL

                        cartReference.child(cartItemId).setValue(cartItem);
                        adjustUploadItemQuantity(ID, -1); // Decrease the quantity in uploads
                        currentQuantity -= 1; // Update the local current quantity
                        plantQuantity.setText(currentQuantity + " left"); // Update the UI
                        Toast.makeText(DetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "User is not authenticated", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetailActivity.this, "Not enough stock available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adjustUploadItemQuantity(String uploadId, int adjustment) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child(uploadId).child("quantity");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int currentQuantity = snapshot.getValue(Integer.class);
                    int newQuantity = currentQuantity + adjustment;
                    if (newQuantity >= 0) {
                        databaseReference.setValue(newQuantity);
                    } else {
                        Log.e("DetailActivity", "Quantity adjustment would result in negative value");
                    }
                } else {
                    Log.e("DetailActivity", "Snapshot does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DetailActivity", "onCancelled: " + error.getMessage());
            }
        });
    }

    private void setDefaultPlantDetails() {
        plantName.setText("Default Name");
        plantPrice.setText("Rs.0.00");
        plantDescription.setText("Default Description");
        plantAddress.setText("Default Address");
        plantPhone.setText("Default Phone Number");
        plantQuantity.setText("0 left");
        plantImage.setImageResource(R.drawable.ic_warning);
    }

    private boolean isActivityValid() {
        return !isFinishing() && !isDestroyed();
    }
}

