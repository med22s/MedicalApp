package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DoctorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyDatabaseHelper myDB;
    ArrayList<String> doc_id, doc_firstName, doc_lastName, doc_address,doc_tele,doc_teleper,doc_speciality;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);


        recyclerView=findViewById(R.id.recyclerView);
        floatingActionButton=findViewById(R.id.floatingActionButton);

        myDB = new MyDatabaseHelper(DoctorsActivity.this);
        doc_id = new ArrayList<>();
        doc_firstName = new ArrayList<>();
        doc_lastName = new ArrayList<>();
        doc_address = new ArrayList<>();
        doc_tele = new ArrayList<>();
        doc_teleper = new ArrayList<>();
        doc_speciality = new ArrayList<>();





        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorsActivity.this,AddDoctor.class);
                startActivity(intent);
            }
        });

        storeDataInArrays();


        customAdapter = new CustomAdapter(DoctorsActivity.this, doc_id, doc_firstName,
                doc_lastName,
                doc_address,doc_tele,doc_teleper,doc_speciality);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorsActivity.this));

    }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(DoctorsActivity.this,"No Data",Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                doc_id.add(cursor.getString(0));
                doc_firstName.add(cursor.getString(1));
                doc_lastName.add(cursor.getString(2));
                doc_address.add(cursor.getString(3));
                doc_tele.add(cursor.getString(4));
                doc_teleper.add(cursor.getString(5));
                doc_speciality.add(cursor.getString(6));
            }
        }
    }

}