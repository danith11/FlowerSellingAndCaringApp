// package com.s22010334.finalproject.Adapter;

// import android.content.Context;
// import android.util.Log;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.ImageView;
// import android.widget.TextView;
// import android.widget.Toast;

// import androidx.annotation.NonNull;
// import androidx.appcompat.widget.AppCompatButton;
// import androidx.recyclerview.widget.RecyclerView;

// import com.bumptech.glide.Glide;
// import com.s22010334.finalproject.Activities.CartManager;
// import com.s22010334.finalproject.Domain.CartDomain;
// import com.s22010334.finalproject.R;
// import com.s22010334.finalproject.fragments.CartFragment;

// import java.util.List;

// public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
//     private Context context;
//     private List<CartDomain> cartItemList;
//     private CartFragment cartFragment;

//     public CartAdapter(Context context, List<CartDomain> cartItemList, CartFragment cartFragment) {
//         this.context = context;
//         this.cartItemList = cartItemList;
//         this.cartFragment = cartFragment;
//     }

//     @NonNull
//     @Override
//     public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//         View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
//         return new CartViewHolder(view);
//     }
//     @Override
//     public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//         CartDomain cartItem = cartItemList.get(position);
//         holder.plantName.setText(cartItem.getName());
//         holder.plantPrice.setText("Rs." + cartItem.getPrice() + ".00");
//         holder.plantQuantity.setText(String.valueOf(cartItem.getQuantity()));

//         Glide.with(context)
//                 .load(cartItem.getImageUrl())
//                 .placeholder(R.drawable.ic_loading)
//                 .error(R.drawable.ic_warning)
//                 .into(holder.plantImage);

//         holder.buttonRemove.setOnClickListener(v -> {
//             String cartItemId = cartItem.getId();
//             if (cartItemId != null) {
//                 CartManager.getInstance().removeCartItem(cartItemId);
//                 cartItemList.remove(position);
//                 notifyItemRemoved(position);
//                 cartFragment.calculateTotalPrice(cartItemList);
//             } else {
//                 // Log an error or show a message to the user
//                 Log.e("CartAdapter", "Cart item ID is null");
//             }
//         });

//         holder.buttonIncrease.setOnClickListener(v -> {
//             int quantity = cartItem.getQuantity() + 1;
//             cartItem.setQuantity(quantity);
//             holder.plantQuantity.setText(String.valueOf(quantity));
//             cartFragment.calculateTotalPrice(cartItemList);
//         });

//         holder.buttonDecrease.setOnClickListener(v -> {
//             if (cartItem.getQuantity() > 1) {
//                 int quantity = cartItem.getQuantity() - 1;
//                 cartItem.setQuantity(quantity);
//                 holder.plantQuantity.setText(String.valueOf(quantity));
//                 cartFragment.calculateTotalPrice(cartItemList);
//             }
//         });
//     }

//     @Override
//     public int getItemCount() {
//         return cartItemList.size();
//     }

//     public static class CartViewHolder extends RecyclerView.ViewHolder {
//         TextView plantName, plantPrice, plantQuantity;
//         ImageView plantImage;
//         AppCompatButton buttonIncrease, buttonDecrease, buttonRemove;

//         public CartViewHolder(@NonNull View itemView) {
//             super(itemView);
//             plantName = itemView.findViewById(R.id.cart_plant_name);
//             plantPrice = itemView.findViewById(R.id.cart_plant_price);
//             plantQuantity = itemView.findViewById(R.id.cart_plant_quantity);
//             plantImage = itemView.findViewById(R.id.cart_plant_image);
//             buttonIncrease = itemView.findViewById(R.id.quantity_increase_button);
//             buttonDecrease = itemView.findViewById(R.id.quantity_decrease_button);
//             buttonRemove = itemView.findViewById(R.id.cart_remove_button);
//         }
//     }
// }

package com.s22010334.finalproject.Activities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private DatabaseReference uploadsReference;
    private List<CartDomain> cartItems = new ArrayList<>();

    private CartManager() {
        uploadsReference = FirebaseDatabase.getInstance().getReference("uploads");
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    private DatabaseReference getUserCartReference() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            return FirebaseDatabase.getInstance().getReference("carts").child(uid);
        }
        return null;
    }

    public void addCartItem(CartDomain cartItem) {
        DatabaseReference cartReference = getUserCartReference();
        if (cartReference != null) {
            String cartItemId = cartReference.push().getKey();
            if (cartItemId != null) {
                Log.d("CartManager", "CartItem is not null and added to the cart");
                cartItem.setId(cartItemId);
                cartReference.child(cartItemId).setValue(cartItem);
            } else {
                Log.d("CartManager", "CartItem is null");
            }
        }
    }

    public void removeCartItem(String cartItemId) {
        DatabaseReference cartReference = getUserCartReference();
        if (cartReference != null) {
            cartReference.child(cartItemId).removeValue();
        }
    }

    public void updateCartItemQuantity(String cartItemId, int newQuantity) {
        DatabaseReference cartReference = getUserCartReference();
        if (cartReference != null) {
            cartReference.child(cartItemId).child("quantity").setValue(newQuantity);
        }
    }

    public void adjustUploadItemQuantity(String uploadId, int quantityChange) {
        uploadsReference.child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer currentQuantity = snapshot.child("quantity").getValue(Integer.class);
                    if (currentQuantity != null) {
                        int newQuantity = currentQuantity + quantityChange;
                        uploadsReference.child(uploadId).child("quantity").setValue(newQuantity);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CartManager", "Database error: " + error.getMessage());
            }
        });
    }

    public void fetchCartItems(final CartItemsFetchListener listener) {
        DatabaseReference cartReference = getUserCartReference();
        if (cartReference != null) {
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
    }

    public List<CartDomain> getCartItems() {
        return cartItems;
    }

    public interface CartItemsFetchListener {
        void onCartItemsFetched(List<CartDomain> cartItems);
    }
}


