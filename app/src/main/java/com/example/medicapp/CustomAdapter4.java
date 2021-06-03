package com.example.medicapp;

import android.app.Activity;
import android.content.Context;
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

public class CustomAdapter4 extends RecyclerView.Adapter<CustomAdapter4.MyViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList medecines_ids, medecines_names, medecines_photos,medecines_description,medecines_prescriptions;


    @NonNull
    @NotNull
    @Override
    public CustomAdapter4.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medicines_row, parent, false);
        return new CustomAdapter4.MyViewHolder(view);
    }

    CustomAdapter4(Activity activity,Context context,
                   ArrayList medecines_ids, ArrayList medecines_names, ArrayList medecines_photos,
                   ArrayList medecines_description,ArrayList medecines_prescriptions){

        this.context = context;
        this.activity=activity;
        this.medecines_ids = medecines_ids;
        this.medecines_names = medecines_names;
        this.medecines_photos = medecines_photos;
        this.medecines_description = medecines_description;
        this.medecines_prescriptions = medecines_prescriptions;
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomAdapter4.MyViewHolder holder, int position) {

        holder.txtMedicine4.setText(String.valueOf(medecines_names.get(position)));
        holder.txtPrescription4.setText(String.valueOf(medecines_prescriptions.get(position)));
        holder.txtDescription4.setText(String.valueOf(medecines_description.get(position)));


        Bitmap bitmap = BitmapFactory.decodeByteArray(((byte[])medecines_photos.get(position)),
                0, ((byte[])medecines_photos.get(position)).length);
        holder.medecineImage4.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return medecines_ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtMedicine4;
        TextView txtDescription4;
        TextView txtPrescription4;
        ImageView medecineImage4;
        LinearLayout mainLayout4;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedicine4 = itemView.findViewById(R.id.txtMedicine4);
            medecineImage4 = itemView.findViewById(R.id.medecineImage4);
            txtDescription4 = itemView.findViewById(R.id.txtDescription4);
            txtPrescription4 = itemView.findViewById(R.id.txtPrescription4);

            mainLayout4=itemView.findViewById(R.id.mainLayout4);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout4.setAnimation(translate_anim);
        }


    }
}
