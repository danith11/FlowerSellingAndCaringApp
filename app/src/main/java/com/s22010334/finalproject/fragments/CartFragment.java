//package com.s22010334.finalproject.fragments;
//
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import com.s22010334.finalproject.Activities.CartManager;
//import com.s22010334.finalproject.Domain.CartDomain;
//import com.s22010334.finalproject.R;
//import com.s22010334.finalproject.Adapter.CartAdapter;
//import java.util.List;
//
//public class CartFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private CartAdapter cartAdapter;
//    private TextView totalPrice;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//
//        recyclerView = view.findViewById(R.id.cart_recycler_view);
//        totalPrice = view.findViewById(R.id.total_price);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<CartDomain> cartItems = CartManager.getInstance().getCartItems();
//        cartAdapter = new CartAdapter(getContext(), cartItems,this);
//        recyclerView.setAdapter(cartAdapter);
//
//        calculateTotalPrice(cartItems);
//
//        return view;
//    }
//
//    public void calculateTotalPrice(List<CartDomain> cartItems) {
//        double total = 0;
//        for (CartDomain item : cartItems) {
//            double itemPrice = Double.parseDouble(item.getPrice()); // Assuming getPrice() returns a string
//           total += item.getQuantity() * itemPrice;
//        }
//        totalPrice.setText("Total Price: Rs." + total + ".00");
//    }
//    //    public void calculateTotalPrice() {
////        double total = 0;
////        for (CartDomain cartItem : cartItemList) {
////            double itemPrice = Double.parseDouble(cartItem.getPrice()); // Assuming getPrice() returns a string
////            total += cartItem.getQuantity() * itemPrice;
////        }
////        totalPrice.setText("Total Price: $" + total);
////    }
//}
package com.s22010334.finalproject.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.s22010334.finalproject.Adapter.CartAdapter;
import com.s22010334.finalproject.Domain.CartDomain;
import com.s22010334.finalproject.Activities.CartManager;
import com.s22010334.finalproject.R;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;

    public CartFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        totalPriceTextView = view.findViewById(R.id.total_price);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CartManager.getInstance().fetchCartItems(new CartManager.CartItemsFetchListener() {
            @Override
            public void onCartItemsFetched(List<CartDomain> cartItems) {
                cartAdapter = new CartAdapter(getContext(), cartItems, CartFragment.this);
                recyclerView.setAdapter(cartAdapter);
                calculateTotalPrice(cartItems);
            }
        });

        return view;
    }

    public void calculateTotalPrice(List<CartDomain> cartItems) {
        double total = 0;
        for (CartDomain item : cartItems) {
            double itemPrice = item.getPrice(); // Assuming getPrice() returns a string
           total += item.getQuantity() * itemPrice;
        }
        totalPriceTextView.setText("Total Price: Rs." + total + ".00");
    }
}




