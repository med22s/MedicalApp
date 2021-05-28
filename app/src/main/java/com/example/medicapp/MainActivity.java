package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView DocsCard,AppointCard,ListCard,MedicsCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DocsCard=findViewById(R.id.docs);
        AppointCard=findViewById(R.id.appoint);
        ListCard=findViewById(R.id.list);
        MedicsCard=findViewById(R.id.medics);


        DocsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DoctorsActivity.class);
                startActivity(intent);
            }
        });

    }
}