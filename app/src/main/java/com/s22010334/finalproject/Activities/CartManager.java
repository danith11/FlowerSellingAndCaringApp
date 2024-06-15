//package com.s22010334.finalproject.Activities;
//
//import android.util.Log;
//import androidx.annotation.NonNull;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.s22010334.finalproject.Domain.CartDomain;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CartManager {
//    private static CartManager instance;
//    private DatabaseReference cartReference;
//    private List<CartDomain> cartItems = new ArrayList<>();
//
//    private CartManager() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        cartReference = database.getReference("cart");
//    }
//
//    public static CartManager getInstance() {
//        if (instance == null) {
//            instance = new CartManager();
//        }
//        return instance;
//    }
//
//    public DatabaseReference getCartReference() {
//        return cartReference;
//    }
//
//    public void addCartItem(CartDomain cartItem) {
//        String cartItemId = cartReference.push().getKey();
//        if (cartItemId != null) {
//            Log.d("CartManager","CartItem is not null an added to the cart");
//            cartItem.setId(cartItemId);
//            cartReference.child(cartItemId).setValue(cartItem);
//        }
//        else {
//            Log.d("CartManager","CartItem is null");
//        }
//    }
//
////public void addCartItem(CartDomain cartItem) {
////    String cartItemId = cartReference.push().getKey();
////    if (cartItemId != null) {
////        Log.d("CartManager","CartItem is not null an added to the cart");
////        cartItem.setId(cartItemId);
////        cartReference.child(cartItemId).setValue(cartItem);
////
////         //Decrease quantity in uploads database
////        DatabaseReference uploadsReference = FirebaseDatabase.getInstance().getReference("uploads");
////        uploadsReference.child(cartItem.getUploadId()).child("quantity")
////                .addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        if (snapshot.exists()) {
////                            int currentQuantity = snapshot.getValue(Integer.class);
////                            int newQuantity = currentQuantity - 1;
////                            updateUploadQuantity(cartItem.getUploadId(), newQuantity);
////                        }
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////                        Log.e("CartManager", "Failed to read quantity", error.toException());
////                    }
////                });
////    }
////    else{
////        Log.d("CartManager","CartItem is null");
////    }
//
//
//
//    public void removeCartItem(String cartItemId) {
//        cartReference.child(cartItemId).removeValue();
//    }
//
////    public void fetchCartItems(final CartItemsFetchListener listener) {
////        cartReference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                cartItems.clear();
////                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
////                    CartDomain cartItem = dataSnapshot.getValue(CartDomain.class);
////                    if (cartItem != null) {
////                        cartItems.add(cartItem);
////                    }
////                }
////                listener.onCartItemsFetched(cartItems);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Log.e("CartManager", "Database error: " + error.getMessage());
////            }
////        });
////    }
//
//    public void fetchCartItems(final CartItemsFetchListener listener) {
//        cartReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                cartItems.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    try {
//                        CartDomain cartItem = dataSnapshot.getValue(CartDomain.class);
//                        if (cartItem != null) {
//                            // Handle type conversion
//                            Object priceObj = dataSnapshot.child("price").getValue();
//                            if (priceObj instanceof String) {
//                                cartItem.setPrice(Double.parseDouble((String) priceObj));
//                            } else if (priceObj instanceof Double) {
//                                cartItem.setPrice((Double) priceObj);
//                            }
//                            cartItems.add(cartItem);
//                        }
//                    } catch (Exception e) {
//                        Log.e("CartManager", "Failed to convert data to CartDomain", e);
//                    }
//                }
//                listener.onCartItemsFetched(cartItems);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("CartManager", "Database error: " + error.getMessage());
//            }
//        });
//    }
//
////    public void updateUploadQuantity(String itemId, int quantity) {
////        DatabaseReference uploadsReference = FirebaseDatabase.getInstance().getReference("uploads");
////        uploadsReference.child(itemId).child("quantity").setValue(quantity)
////                .addOnSuccessListener(aVoid -> Log.d("CartManager", "Quantity updated successfully"))
////                .addOnFailureListener(e -> Log.e("CartManager", "Failed to update quantity", e));
////    }
//
//    public List<CartDomain> getCartItems() {
//        return cartItems;
//    }
//
//    public interface CartItemsFetchListener {
//        void onCartItemsFetched(List<CartDomain> cartItems);
//    }
//}
package com.s22010334.finalproject.Activities;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Domain.CartDomain;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private DatabaseReference cartReference;
    private List<CartDomain> cartItems = new ArrayList<>();

    private CartManager() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        cartReference = database.getReference("cart");
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public DatabaseReference getCartReference() {
        return cartReference;
    }

    public void addCartItem(CartDomain cartItem) {
        String cartItemId = cartReference.push().getKey();
        if (cartItemId != null) {
            Log.d("CartManager", "CartItem is not null and added to the cart");
            cartItem.setId(cartItemId);
            cartReference.child(cartItemId).setValue(cartItem);
        } else {
            Log.d("CartManager", "CartItem is null");
        }
    }

    public void removeCartItem(String cartItemId) {
        cartReference.child(cartItemId).removeValue();
    }

    public void fetchCartItems(final CartItemsFetchListener listener) {
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        CartDomain cartItem = dataSnapshot.getValue(CartDomain.class);
                        if (cartItem != null) {
                            // Handle type conversion
                            Object priceObj = dataSnapshot.child("price").getValue();
                            Object quantityObj = dataSnapshot.child("quantity").getValue();

                            if (priceObj instanceof String) {
                                cartItem.setPrice(Double.parseDouble((String) priceObj));
                            } else if (priceObj instanceof Double) {
                                cartItem.setPrice((Double) priceObj);
                            }

                            if (quantityObj instanceof String) {
                                cartItem.setQuantity(Integer.parseInt((String) quantityObj));
                            } else if (quantityObj instanceof Integer) {
                                cartItem.setQuantity((Integer) quantityObj);
                            }

                            cartItems.add(cartItem);
                        }
                    } catch (Exception e) {
                        Log.e("CartManager", "Failed to convert data to CartDomain", e);
                    }
                }
                listener.onCartItemsFetched(cartItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CartManager", "Database error: " + error.getMessage());
            }
        });
    }

    public List<CartDomain> getCartItems() {
        return cartItems;
    }

    public interface CartItemsFetchListener {
        void onCartItemsFetched(List<CartDomain> cartItems);
    }
}



