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


package com.s22010334.finalproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Activities.CartManager;
import com.s22010334.finalproject.Domain.CartDomain;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.fragments.CartFragment;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartDomain> cartItemList;
    private CartFragment cartFragment;

    public CartAdapter(Context context, List<CartDomain> cartItemList, CartFragment cartFragment) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.cartFragment = cartFragment;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartDomain cartItem = cartItemList.get(position);
        holder.plantName.setText(cartItem.getName());
        holder.plantPrice.setText("Rs." + cartItem.getPrice() + ".00");
        holder.plantQuantity.setText(String.valueOf(cartItem.getQuantity()));

        String imageUrl = cartItem.getImageUrl();
        Log.d("CartAdapter", "Image URL: " + imageUrl); // Log the image URL for debugging

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_warning)
                .into(holder.plantImage);

        long currentTime = System.currentTimeMillis();
        long timeSinceAdded = currentTime - cartItem.getTimestamp();
        long oneHourInMillis = 60 * 60 * 1000;

        boolean canUpdateQuantity = timeSinceAdded <= oneHourInMillis;

        holder.buttonRemove.setOnClickListener(v -> {
            String cartItemId = cartItem.getId();
            if (cartItemId != null) {
                int currentQuantity = cartItem.getQuantity();

                // Remove the cart item
                CartManager.getInstance().removeCartItem(cartItemId);
                cartItemList.remove(position);
                notifyItemRemoved(position);
                cartFragment.calculateTotalPrice(cartItemList);

                // Add the removed quantity back to the uploads quantity
                CartManager.getInstance().adjustUploadItemQuantity(cartItem.getUploadId(), currentQuantity);
            } else {
                Log.e("CartAdapter", "Cart item ID is null");
            }
        });

        holder.buttonIncrease.setEnabled(canUpdateQuantity);
        holder.buttonDecrease.setEnabled(canUpdateQuantity);

        holder.buttonIncrease.setOnClickListener(v -> {
            if (canUpdateQuantity) {
                getAvailableQuantity(cartItem.getUploadId(), availableQuantity -> {
                    int currentCartQuantity = cartItem.getQuantity();
                    int totalAvailable = availableQuantity + currentCartQuantity;
                    int newQuantity = currentCartQuantity + 1;
                    if (newQuantity <= totalAvailable) {
                        cartItem.setQuantity(newQuantity);
                        holder.plantQuantity.setText(String.valueOf(newQuantity));
                        CartManager.getInstance().updateCartItemQuantity(cartItem.getId(), newQuantity);
                        CartManager.getInstance().adjustUploadItemQuantity(cartItem.getUploadId(), -1); // Decrease the quantity in uploads
                        cartFragment.calculateTotalPrice(cartItemList);
                    } else {
                        Toast.makeText(context, "Cannot exceed available quantity", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "You can only update the quantity within 1 hour of adding the item to the cart", Toast.LENGTH_SHORT).show();
            }
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            if (canUpdateQuantity) {
                if (cartItem.getQuantity() > 1) {
                    int newQuantity = cartItem.getQuantity() - 1;
                    cartItem.setQuantity(newQuantity);
                    holder.plantQuantity.setText(String.valueOf(newQuantity));
                    CartManager.getInstance().updateCartItemQuantity(cartItem.getId(), newQuantity);
                    CartManager.getInstance().adjustUploadItemQuantity(cartItem.getUploadId(), 1); // Increase the quantity in uploads
                    cartFragment.calculateTotalPrice(cartItemList);
                } else {
                    Toast.makeText(context, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "You can only update the quantity within 1 hour of adding the item to the cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    private void getAvailableQuantity(String uploadId, OnQuantityRetrievedListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child(uploadId).child("quantity");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int availableQuantity = snapshot.getValue(Integer.class);
                    listener.onQuantityRetrieved(availableQuantity);
                } else {
                    listener.onQuantityRetrieved(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CartAdapter", "Error retrieving available quantity", error.toException());
                listener.onQuantityRetrieved(0);
            }
        });
    }

    public interface OnQuantityRetrievedListener {
        void onQuantityRetrieved(int availableQuantity);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView plantName, plantPrice, plantQuantity;
        ImageView plantImage;
        AppCompatButton buttonIncrease, buttonDecrease, buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            plantName = itemView.findViewById(R.id.cart_plant_name);
            plantPrice = itemView.findViewById(R.id.cart_plant_price);
            plantQuantity = itemView.findViewById(R.id.cart_plant_quantity);
            plantImage = itemView.findViewById(R.id.cart_plant_image);
            buttonIncrease = itemView.findViewById(R.id.quantity_increase_button);
            buttonDecrease = itemView.findViewById(R.id.quantity_decrease_button);
            buttonRemove = itemView.findViewById(R.id.cart_remove_button);
        }
    }
}



