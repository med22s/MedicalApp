package com.example.medicapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private ArrayList doc_id, doc_firstName, doc_lastName, doc_address,doc_tele,doc_teleper,doc_speciality;

    CustomAdapter(Context context, ArrayList doc_id, ArrayList doc_firstName, ArrayList doc_lastName,
                  ArrayList doc_address,ArrayList doc_tele,ArrayList doc_teleper,ArrayList doc_speciality){

        this.context = context;
        this.doc_id = doc_id;
        this.doc_firstName = doc_firstName;
        this.doc_lastName = doc_lastName;
        this.doc_address = doc_address;
        this.doc_tele = doc_tele;
        this.doc_teleper = doc_teleper;
        this.doc_speciality = doc_speciality;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doc_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.doc_id_txt.setText(String.valueOf(doc_id.get(position)));
        holder.doc_fullName_txt.setText(String.valueOf(doc_firstName.get(position))+" "+String.valueOf(doc_lastName.get(position)));
        holder.doc_phone_txt.setText(String.valueOf(doc_tele.get(position)));
        holder.doc_speciality_txt.setText(String.valueOf(doc_speciality.get(position)));

    }

    @Override
    public int getItemCount() {
        return doc_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView doc_id_txt, doc_fullName_txt, doc_phone_txt, doc_speciality_txt;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doc_id_txt = itemView.findViewById(R.id.doc_id_txt);
            doc_fullName_txt = itemView.findViewById(R.id.doc_fullName_txt);
            doc_phone_txt = itemView.findViewById(R.id.doc_phone_txt);
            doc_speciality_txt = itemView.findViewById(R.id.doc_speciality_txt);
        }

    }


}