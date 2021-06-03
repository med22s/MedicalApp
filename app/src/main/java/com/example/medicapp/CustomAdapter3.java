package com.example.medicapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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



    CustomAdapter3(Activity activity,Context context,
                   ArrayList prescription_ids, ArrayList prescription_imgs, ArrayList prescription_observations,
                   ArrayList prescription_appointments){

        this.context = context;
        this.activity=activity;
        this.prescription_ids = prescription_ids;
        this.prescription_imgs = prescription_imgs;
        this.prescription_observations = prescription_observations;
        this.prescription_appointments = prescription_appointments;
    }



    @NonNull
    @NotNull
    @Override
    public CustomAdapter3.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prescription_row, parent, false);
        return new CustomAdapter3.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomAdapter3.MyViewHolder holder, int position) {
        holder.txtObservation.setText(String.valueOf(prescription_observations.get(position)));
        holder.txtAppointment.setText(String.valueOf(prescription_appointments.get(position)));


        Bitmap bitmap = BitmapFactory.decodeByteArray(((byte[])prescription_imgs.get(position)),
                0, ((byte[])prescription_imgs.get(position)).length);
        holder.medecineImage.setImageBitmap(bitmap);


        holder.mainLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatePrescription.class);

                intent.putExtra("id", String.valueOf(prescription_ids.get(position)));
                intent.putExtra("image", ((byte[])prescription_imgs.get(position)));
                intent.putExtra("observation", String.valueOf(prescription_observations.get(position)));
                intent.putExtra("appointment", String.valueOf(prescription_appointments.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescription_ids.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtObservation;
        TextView txtAppointment;
        ImageView medecineImage;
        LinearLayout mainLayout3;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObservation = itemView.findViewById(R.id.txtName);
            medecineImage = itemView.findViewById(R.id.medecineImage);
            txtAppointment = itemView.findViewById(R.id.txtPrescription4);

            mainLayout3=itemView.findViewById(R.id.mainLayout3);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout3.setAnimation(translate_anim);
        }
    }

}
