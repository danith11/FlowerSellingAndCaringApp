package com.s22010334.finalproject.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.s22010334.finalproject.Activities.ExhibitionActivity;
import com.s22010334.finalproject.Model.eventModel;
import com.s22010334.finalproject.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder> {

    private List<eventModel> eventList;
    private ExhibitionActivity activity;
    private FirebaseDatabase database;

    public EventAdapter (ExhibitionActivity exhibitionActivity,List<eventModel> eventList){
        this.eventList= eventList;
        activity = exhibitionActivity;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_exhibition,parent,false);
        database = FirebaseDatabase.getInstance();
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        eventModel eventModel = eventList.get(position);
        holder.place.setText(eventModel.getPlace());
        holder.date.setText(eventModel.getDate());
        holder.time.setText(eventModel.getTime()+" Onwards");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView place,date,month,day,time;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            place = itemView.findViewById(R.id.EventPlace);
            date = itemView.findViewById(R.id.date);
            //month = itemView.findViewById(R.id.month);
            //day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
        }
    }
}
