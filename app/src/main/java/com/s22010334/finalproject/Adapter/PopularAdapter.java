//package com.s22010334.finalproject.Adapter;
//
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//import com.s22010334.finalproject.Activities.ExhibitionActivity;
//import com.s22010334.finalproject.Activities.MainActivity;
//import com.s22010334.finalproject.Domain.itemDomain;
//import com.s22010334.finalproject.Helper.DataClass;
//import com.s22010334.finalproject.Model.PopularModel;
//import com.s22010334.finalproject.Model.eventModel;
//import com.s22010334.finalproject.databinding.ViewholderPopListBinding;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder>{
//
//    private List<PopularModel> popularList;
//    private MainActivity activity;
//    private FirebaseDatabase database;
//
//    public PopularAdapter (MainActivity mainActivity,List<eventModel> eventList){
//        this.popularList = popularList;
//        activity = mainActivity;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PopularAdapter.MyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return popularList.size();
//    }
//}
//
////
////    Context context;
////    ArrayList<itemDomain> items;
////
//
////    public PopularAdapter(Context context, ArrayList<itemDomain> items) {
////        this.context = context;
////        this.items = items;
////    }
////
////    @NonNull
////    @Override
////   public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View v = LayoutInflater.from(context).inflate(R.layout.viewholder_pop_list,parent,false);
////        return new ViewHolder(v);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
////        itemDomain itemDomain = items.get(position);
////        holder.title.setText(itemDomain.getTitle());
////        holder.description.setText(itemDomain.getDescription());
////    }
////
////    @Override
////    public int getItemCount() {
////        return 0;
////    }
////
////    public static class Viewholder extends RecyclerView.ViewHolder {
////        TextView title,description,price;
////        public Viewholder(@NonNull View itemView) {
////            super(itemView);
////            title = itemView.findViewById(R.id.textViewtitle);
////            description =itemView.findViewById(R.id.textViewdescription);
////            price = itemView.findViewById(R.id.textViewprice);
////        }
////    }
//
////    ArrayList<itemDomain> items;
////    Context context;
////    public PopularAdapter(Context context, ArrayList<itemDomain> adArrayList){
////        this.context = context;
////        this.adArrayList = adArrayList;
////        this.filterList = adArrayList;
////
////        firebaseAuth = FirebaseAuth.getInstance();
////    }
//////    public PopularAdapter(ConstraintLayout items) {
//////        this.items = items;
//////    }
////
////    @NonNull
////    @Override
////    public PopularAdapter  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        context= parent.getContext();
////        ViewholderPopListBinding binding = ViewholderPopListBinding.inflate(LayoutInflater.from(context),parent,false);
////        //View v = LayoutInflater.from(context).inflate(R.layout.viewholder_pop_list,parent,false);
////        //return new Viewholder(binding);
////        return new PopularAdapter(binding.getRoot());
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
////
////        String name = DataClass.getName();
////        String price = DataClass.getPrice();
////        String description = DataClass.getDescription();
////        String imageURL = DataClass.getImageURL();
////
////        loadFirstImage(modelAd, holder);
////
////        holder.name.setText(title);
////        holder.price.setText(description);
////        holder.description.setText(quantity + " Available");
////
//////        holder.imageURL.setText(foodType);
//////        holder.date.setText(formattedDate);
////
//////        holder.binding.textViewtitle.setText(items.get(position).getTitle());
//////        holder.binding.textViewRatings.setText("("+items.get(position).getRating()+")");
//////        holder.binding.textViewprice.setText("Rs"+items.get(position).getPrice());
//////        holder.binding.ratingBar.setRating((float) items.get(position).getRating());
//////        RequestOptions requestOptions = new RequestOptions();
//////        requestOptions = requestOptions.transform(new CenterCrop());
//////
//////        Glide.with(context).load(items.get(position).getPicUrl().get(0))
//////                .apply(requestOptions)
//////                .into(holder.binding.imageView);
//////
//////        holder.itemView.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////
//////            }
//////        });
////
////    }
////
////    @Override
////    public int getItemCount() {
////        return items.size();
////    }
////
////    public class Viewholder extends RecyclerView.ViewHolder{
////        ViewholderPopListBinding binding;
////        public Viewholder(ConstraintLayout binding) {
////            super(binding.getRoot());
////            this.binding = binding;
////        }
////    }


package com.s22010334.finalproject.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s22010334.finalproject.Activities.MainActivity;
import com.s22010334.finalproject.Model.PopularModel;
import com.s22010334.finalproject.Model.eventModel;
import com.s22010334.finalproject.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {

    private List<PopularModel> popularList;
    private MainActivity activity;
    FirebaseDatabase database;

    public PopularAdapter(MainActivity mainActivity,List<PopularModel> popularList){
        this.popularList = popularList;
        activity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.viewholder_pop_list,parent,false);
        database = FirebaseDatabase.getInstance();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PopularModel popularModel = popularList.get(position);
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView PopListImage;
        TextView name,price,description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageUrl = itemView.findViewById(R.id.imageViewPopList)
            //PopListImage = itemView.findViewById(R.id.imageViewPopList);
            name = itemView.findViewById(R.id.textViewtitle);
            price = itemView.findViewById(R.id.textViewprice);
            //description = itemView.findViewById(R.id.textViewdescription);
        }
    }
}

