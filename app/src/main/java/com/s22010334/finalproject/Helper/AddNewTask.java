package com.s22010334.finalproject.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s22010334.finalproject.Activities.ExhibitionActivity;
import com.s22010334.finalproject.Activities.LoginActivity;
import com.s22010334.finalproject.Activities.MainActivity;
import com.s22010334.finalproject.Activities.ProfileActivity;
import com.s22010334.finalproject.Activities.SignUpActivity;
import com.s22010334.finalproject.Activities.UploadActivity;
import com.s22010334.finalproject.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private EditText editTextPlace ,editTextdate , editTextTime;
    private Button addBtn;
    TextView textDate;
    private FirebaseFirestore firestore;
    private Context context;
    private String textdate ="";
    private String textDay="";
    FirebaseDatabase database;
    DatabaseReference myRef;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextPlace = view.findViewById(R.id.addPlace);
        textDate = view.findViewById(R.id.addDate);
        editTextTime = view.findViewById(R.id.addTime);
        addBtn = view.findViewById(R.id.Add);

        firestore = FirebaseFirestore.getInstance();
        editTextPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    addBtn.setEnabled(false);
                    addBtn.setBackgroundColor(Color.GRAY);
                }else{
                    addBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int MONTH = calendar.get(Calendar.MONTH);
                int YEAR = calendar.get(Calendar.YEAR);
                int DAY = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        textDate.setText(dayOfMonth + "/"+month +"/"+year);
                        textdate = dayOfMonth + "/"+month + "/"+year;
                    }
                },YEAR,MONTH,DAY);
                datePickerDialog.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("NeedToApproved");

                String place = editTextPlace.getText().toString();
                String date = textDate.getText().toString();
                String time = editTextTime.getText().toString();

//                AddingClass addingClass = new AddingClass(place,date , time);
//                myRef.child(place).setValue(addingClass);
//
//                Toast.makeText(context, "Event Added Succesfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context,ExhibitionActivity.class);
//                startActivity(intent);

                Map<String,Object> taskMap = new HashMap<>();

                taskMap.put("place", place);
                taskMap.put("date", date);
                taskMap.put("time", time);
                taskMap.put("status",0);

                AddingClass addingClass = new AddingClass(place,date , time);
                myRef.child(place).setValue(addingClass);

                Toast.makeText(context, "Event Added Succesfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ExhibitionActivity.class);
                startActivity(intent);
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity =getActivity();
        if (activity instanceof OnDialogCloserListner){
            ((OnDialogCloserListner)activity).onDialogClose(dialog);
        }
    }
}
