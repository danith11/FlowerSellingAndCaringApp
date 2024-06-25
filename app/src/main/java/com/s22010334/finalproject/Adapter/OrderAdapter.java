package com.s22010334.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s22010334.finalproject.Activities.SellerOrdersActivity;
import com.s22010334.finalproject.Domain.CartDomain;
import com.s22010334.finalproject.Domain.OrderDomain;
import com.s22010334.finalproject.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<OrderDomain> orders;
    private SellerOrdersActivity activity;

    public OrderAdapter(Context context, List<OrderDomain> orders, SellerOrdersActivity activity) {
        this.context = context;
        this.orders = orders;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDomain order = orders.get(position);
        holder.orderIdTextView.setText("Order ID: " + order.getOrderId());
        holder.statusTextView.setText("Status: " + order.getStatus());

        if (!order.getItems().isEmpty()) {
            CartDomain firstItem = order.getItems().get(0);
            holder.plantNameTextView.setText("Plant Name: " + firstItem.getName());
            holder.quantityTextView.setText("Quantity: " + firstItem.getQuantity());
        }

        holder.customerNameTextView.setText("Customer Name: " + order.getCustomerName());
        holder.customerPhoneTextView.setText("Customer Phone: " + order.getCustomerPhone());

        if ("accepted".equals(order.getStatus())) {
            holder.acceptButton.setVisibility(View.GONE);
        } else {
            holder.acceptButton.setVisibility(View.VISIBLE);
        }

        holder.acceptButton.setOnClickListener(v -> {
            if (!order.getStatus().equals("accepted")) {
                activity.acceptOrder(order);
            } else {
                Toast.makeText(context, "Order already accepted.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener to dial the phone number
        holder.customerPhoneTextView.setOnClickListener(v -> {
            String phoneNumber = order.getCustomerPhone();
            Log.d("OrderAdapter", "Attempting to dial phone number: " + phoneNumber);

            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                String formattedNumber = "tel:" + phoneNumber.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(formattedNumber));
                Log.d("OrderAdapter", "Formatted phone number: " + formattedNumber);

                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No application can handle this request.", Toast.LENGTH_SHORT).show();
                    Log.e("OrderAdapter", "No application can handle the ACTION_DIAL intent.");
                }
            } else {
                Toast.makeText(context, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                Log.e("OrderAdapter", "Invalid phone number: " + phoneNumber);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView statusTextView;
        TextView plantNameTextView;
        TextView quantityTextView;
        TextView customerNameTextView;
        TextView customerPhoneTextView;
        Button acceptButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id_text_view);
            statusTextView = itemView.findViewById(R.id.status_text_view);
            plantNameTextView = itemView.findViewById(R.id.plant_name_text_view);
            quantityTextView = itemView.findViewById(R.id.quantity_text_view);
            customerNameTextView = itemView.findViewById(R.id.customer_name_text_view);
            customerPhoneTextView = itemView.findViewById(R.id.customer_phone_text_view);
            acceptButton = itemView.findViewById(R.id.accept_button);
        }
    }
}
