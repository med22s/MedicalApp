package com.example.medicapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {


    private Context context;
    private Activity activity;
    private ArrayList appointment_ids, appointment_dates, appointment_reasons,appointment_docs;

    CustomAdapter2(Activity activity,Context context,
                   ArrayList appointment_ids, ArrayList appointment_dates, ArrayList appointment_reasons,
                  ArrayList appointment_docs){

        this.context = context;
        this.activity=activity;
        this.appointment_ids = appointment_ids;
        this.appointment_dates = appointment_dates;
        this.appointment_reasons = appointment_reasons;
        this.appointment_docs = appointment_docs;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.appointment_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.txtId.setText(String.valueOf(appointment_ids.get(position)));
        holder.txtDate.setText(String.valueOf((appointment_dates.get(position))));
        holder.txtDoctor.setText(String.valueOf(appointment_docs.get(position)));
        holder.txtReason.setText(String.valueOf(appointment_reasons.get(position)));

        holder.mainLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAppointment.class);
                intent.putExtra("id", String.valueOf(appointment_ids.get(position)));
                intent.putExtra("date", String.valueOf(appointment_dates.get(position)));
                intent.putExtra("reason", String.valueOf(appointment_reasons.get(position)));
                intent.putExtra("docId", String.valueOf(appointment_docs.get(position)));
                intent.putExtra("docIdPosition", Integer.valueOf(appointment_docs.get(position).toString())-1);


                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointment_ids.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtReason, txtDoctor, txtId;
        LinearLayout mainLayout2;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtReason = itemView.findViewById(R.id.txtReason);
            txtDoctor = itemView.findViewById(R.id.txtDoctor);
            mainLayout2=itemView.findViewById(R.id.mainLayout2);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout2.setAnimation(translate_anim);
        }
    }


}
