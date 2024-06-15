package com.s22010334.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.s22010334.finalproject.Activities.DetailActivity;
import com.s22010334.finalproject.Domain.PlantDomain;
import com.s22010334.finalproject.Domain.ProductDomain;
import com.s22010334.finalproject.R;
import com.s22010334.finalproject.fragments.Homefragment;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<PlantDomain> plantList;
    private Context context;

    public PlantAdapter(List<PlantDomain> plantList, Context context) {
        this.plantList = plantList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Log.d("PlantAdapter","Holder setting");
        PlantDomain plant = plantList.get(position);
        holder.name.setText(plant.getName());
        holder.price.setText("Rs." + plant.getPrice() + ".00");
        holder.quantity.setText(plant.getQuantity() + " left");

        // Load image using Glide with placeholder and error handling
        Glide.with(context)
                .load(plant.getImageUrl())
                .placeholder(R.drawable.ic_loading) // replace with your placeholder image
                .error(R.drawable.ic_warning) // replace with your error image
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PlantAdapter", "Item clicked, ID: " + plant.getId());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pID", plant.getId());
                context.startActivity(intent);
            }
        });
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.PlantName);
            price = itemView.findViewById(R.id.PlantPrice);
            quantity = itemView.findViewById(R.id.plantQuantity);
            image = itemView.findViewById(R.id.PlantImage);
        }
    }
}

