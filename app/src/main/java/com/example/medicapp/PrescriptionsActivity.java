package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PrescriptionsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriptions);

        recyclerView=findViewById(R.id.recyclerView3);
        floatingActionButton=findViewById(R.id.floatingActionButton3);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrescriptionsActivity.this,AddPrescription.class);
                startActivity(intent);
            }
        });


    }
}