package com.s22010334.finalproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.s22010334.finalproject.Activities.CareDetail;
import com.s22010334.finalproject.Domain.CareDomain;
import com.s22010334.finalproject.R;

import java.util.List;

public class CareAdapter extends RecyclerView.Adapter<CareAdapter.FlowerViewHolder> {

    private List<CareDomain> flowerList;
    private Context context;

    public static class FlowerViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;

        public FlowerViewHolder(View v) {
            super(v);
            nameView = v.findViewById(R.id.flower_name);
        }
    }

    public CareAdapter(List<CareDomain> flowers, Context context) {
        Log.d("CareAdapter","Contructer");
        this.flowerList = flowers;
        this.context = context;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_care, parent, false);
        return new FlowerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        CareDomain flower = flowerList.get(position);
        Log.d("CareAdapter","Set Flowers name ");
        holder.nameView.setText(flower.getName());

        Log.d("CareAdapter","onClickListner for Item");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Instructions for " + flower.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CareDetail.class);
                intent.putExtra("name", flower.getName());
                intent.putExtra("care", flower.getCare());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }
}


