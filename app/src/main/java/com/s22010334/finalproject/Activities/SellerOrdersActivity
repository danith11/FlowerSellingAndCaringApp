package com.s22010334.finalproject.Activities;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Adapter.OrderAdapter;
import com.s22010334.finalproject.Domain.CartDomain;
import com.s22010334.finalproject.Domain.OrderDomain;
import com.s22010334.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class SellerOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderDomain> ordersList;
    private DatabaseReference uploadsRef;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_orders);

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, ordersList, this);
        ordersRecyclerView.setAdapter(orderAdapter);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        uploadsRef = FirebaseDatabase.getInstance().getReference("uploads");

        fetchOrders();
    }

    private void fetchOrders() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderDomain order = orderSnapshot.getValue(OrderDomain.class);
                    if (order != null) {
                        for (CartDomain item : order.getItems()) {
                            String uploadId = item.getUploadId();
                            fetchUploaderUserId(uploadId, order);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SellerOrdersActivity", "Error fetching orders", error.toException());
            }
        });
    }

    private void fetchUploaderUserId(String uploadId, OrderDomain order) {
        uploadsRef.child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uploaderUserId = snapshot.child("userId").getValue(String.class);
                if (currentUserId.equals(uploaderUserId)) {
                    fetchCustomerDetails(order);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SellerOrdersActivity", "Error fetching uploader userId", error.toException());
            }
        });
    }

    private void fetchCustomerDetails(OrderDomain order) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(order.getUserId());

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String customerName = snapshot.child("name").getValue(String.class);
                String customerPhone = snapshot.child("phone").getValue(String.class);

                if (customerName != null && customerPhone != null) {
                    order.setCustomerName(customerName);
                    order.setCustomerPhone(customerPhone);
                }

                ordersList.add(order);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SellerOrdersActivity", "Error fetching customer details", error.toException());
            }
        });
    }

    public void acceptOrder(OrderDomain order) {
        Log.d("SellerOrdersActivity", "Order accepted: " + order.getOrderId());
    }
}
