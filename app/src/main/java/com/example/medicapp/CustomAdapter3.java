package com.example.medicapp;

import android.app.Activity;
import android.content.Context;
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

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.MyViewHolder>  {


    private Context context;
    private Activity activity;
    private ArrayList prescription_ids, prescription_imgs, prescription_observations,prescription_appointments;

    @NonNull
    @NotNull
    @Override
    public CustomAdapter3.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomAdapter3.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
