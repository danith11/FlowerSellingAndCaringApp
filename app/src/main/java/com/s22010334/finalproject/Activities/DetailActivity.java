//package com.s22010334.finalproject.Activities;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.s22010334.finalproject.Domain.CareDomain;
//import com.s22010334.finalproject.Domain.CartDomain;
//import com.s22010334.finalproject.Domain.PlantDomain;
//import com.s22010334.finalproject.Domain.ProductDomain;
//import com.s22010334.finalproject.R;
//
//public class DetailActivity extends AppCompatActivity {
//
//    private ImageView plantImage;
//    private TextView plantName, plantPrice, plantDescription, plantAddress, plantPhone, plantQuantity;
//    private Button buttonAddToCart;
//    private DatabaseReference databaseReference;
//    String ID;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        // Initialize views
//        plantImage = findViewById(R.id.PlantImage);
//        plantName = findViewById(R.id.PlantName);
//        plantPrice = findViewById(R.id.PlantPrice);
//        plantDescription = findViewById(R.id.PlantDescription);
//        plantAddress = findViewById(R.id.Address);
//        plantPhone = findViewById(R.id.phoneNumber);
//        plantQuantity = findViewById(R.id.Quantity);
//        buttonAddToCart = findViewById(R.id.buttonAddToCart);
//
//        // Get product ID from Intent
//        Intent intent = getIntent();
//        if (intent == null) {
//            Log.e("DetailActivity", "Intent is null");
//            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        ID = intent.getStringExtra("pID");
//        if (ID == null || ID.isEmpty()) {
//            Log.e("DetailActivity", "Product ID is missing or empty");
//            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        } else {
//            Log.d("DetailActivity", "Product ID: " + ID);
//        }
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("uploads");
//
//        ref.child(ID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("DetailActivity", "DataSnapshot received: " + snapshot.toString());
//                if (snapshot.exists()) {
//                    PlantDomain plantDomain = snapshot.getValue(PlantDomain.class);
//                    if (plantDomain != null) {
//                        Log.d("DetailActivity", "PlantDomain: " + plantDomain.getName());
//
//                        // Set plant details
//                        plantName.setText(plantDomain.getName());
//                        plantPrice.setText("Rs."+plantDomain.getPrice()+".00");
//                        plantDescription.setText(plantDomain.getDescription());
//                        plantAddress.setText(plantDomain.getAddress());
//                        //plantPhone.setText(plantDomain.getPhoneNumber());
//                        plantPhone.setText(String.valueOf(plantDomain.getPhoneNumber()));
//                        plantQuantity.setText(plantDomain.getQuantity()+" left");
//
//                        // Log image URL
//                        String imageUrl = plantDomain.getImageUrl();
//                        if (imageUrl != null && !imageUrl.isEmpty()) {
//                            Log.d("DetailActivity", "Loading image URL: " + imageUrl);
//
//                            // Check if plantImage is not null before using it with Glide
//                            if (plantImage != null) {
//                                Glide.with(DetailActivity.this)
//                                        .load(plantDomain.getImageUrl())
//                                        .placeholder(R.drawable.ic_loading) // Optional placeholder image
//                                        .error(R.drawable.ic_warning) // Error image in case of failure
//                                        .into(plantImage);
//                            } else {
//                                Log.e("DetailActivity", "plantImage ImageView is null");
//                            }
//                        } else {
//                            Log.e("DetailActivity", "Image URL is null or empty, setting default image");
//                            plantImage.setImageResource(R.drawable.ic_warning); // Set a default image
//                        }
//                    } else {
//                        Log.e("DetailActivity", "PlantDomain is null");
//                        setDefaultPlantDetails();
//                    }
//                } else {
//                    Log.e("DetailActivity", "Snapshot does not exist");
//                    setDefaultPlantDetails();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("DetailActivity", "onCancelled: " + error.getMessage());
//                setDefaultPlantDetails();
//            }
//        });
//
////        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
////                StartActivity(intent);
////                finish();
////            }
////        });
//
//        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("DetailActivity","Getting current product details");
//                // Assuming you have a method to get current product details
//                String productId = ID; // Example: get product ID from intent or member variable
//                String productName = plantName.getText().toString(); // Example: get product name
//                double productPrice = Double.parseDouble(plantPrice.getText().toString().replace("Rs.","").replace(".00","").trim()); // Example: get product price
//                int productQuantity = Integer.parseInt(plantQuantity.getText().toString().replace(" left","").trim()); // Example: get product quantity
//
//                // Create a new CartItem object
//                CartDomain cartItem = new CartDomain();
//                Log.d("Detail Activity","Seting to the cart");
//                cartItem.setId(productId);
//                cartItem.setName(productName);
//                cartItem.setPrice(String.valueOf(productPrice));
//                cartItem.setQuantity(productQuantity);
//
//                // Add the item to the cart (you may need to handle cart management in a singleton or ViewModel)
//                // For simplicity, let's assume you have a singleton class CartManager
//                CartManager.getInstance().addCartItem(cartItem);
//
//                // Optionally, show a toast or message indicating item added to cart
//                Toast.makeText(DetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void setDefaultPlantDetails() {
//        plantName.setText("No data found");
//        plantPrice.setText("No data found");
//        plantQuantity.setText("No data found");
//        plantPhone.setText("No data found");
//        plantDescription.setText("No data found");
//        plantAddress.setText("No data found");
//        plantImage.setImageResource(R.drawable.ic_warning); // Set a default image
//    }
//
//}

package com.s22010334.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    private DatabaseReference databaseReference;
    String ID;

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
                        plantPrice.setText("Rs."+plantDomain.getPrice()+".00");
                        plantDescription.setText(plantDomain.getDescription());
                        plantAddress.setText(plantDomain.getAddress());
                        //plantPhone.setText(plantDomain.getPhoneNumber());
                        plantPhone.setText(String.valueOf(plantDomain.getPhoneNumber()));
                        plantQuantity.setText(plantDomain.getQuantity()+" left");

                        // Log image URL
                        String imageUrl = plantDomain.getImageUrl();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Log.d("DetailActivity", "Loading image URL: " + imageUrl);

                            // Check if plantImage is not null before using it with Glide
                            if (plantImage != null) {
                                Glide.with(DetailActivity.this)
                                        .load(plantDomain.getImageUrl())
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

        buttonAddToCart.setOnClickListener(v -> {
            CartDomain cartItem = new CartDomain();
            String cartItemId = CartManager.getInstance().getCartReference().push().getKey();
            if (cartItemId != null) {
                cartItem.setId(cartItemId);
                cartItem.setUploadId(ID);
                cartItem.setName(plantName.getText().toString());
                //cartItem.setPrice(plantPrice.getText().toString().replace("Rs.", "").replace(".00", ""));
                double price = Double.parseDouble(plantPrice.getText().toString().replace("Rs.", "").replace(".00", ""));
                cartItem.setPrice(price);
                cartItem.setQuantity(1);
                cartItem.setImageUrl(""); // Set the image URL properly if you have it

                CartManager.getInstance().addCartItem(cartItem);
                Toast.makeText(DetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
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
}
