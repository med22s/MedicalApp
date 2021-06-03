package com.example.medicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicinesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    MyDatabaseHelper myDB;
    ArrayList<String> medecines_ids, medecines_names,medecines_description,medecines_prescriptions;
    ArrayList<byte[]> medecines_photos;
    CustomAdapter4 customAdapter;

    ImageView empty_imageview;
    TextView no_data;



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Medicines?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MedicinesActivity.this);
                myDB.deleteAllData("Medicines");

                Intent intent = new Intent(MedicinesActivity.this, MedicinesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);


        recyclerView=findViewById(R.id.recyclerView4);
        floatingActionButton=findViewById(R.id.floatingActionButton4);

        myDB=new MyDatabaseHelper(MedicinesActivity.this);

        empty_imageview=findViewById(R.id.empty_imageview4);
        no_data=findViewById(R.id.no_data4);

        medecines_ids=new ArrayList<>(); medecines_photos=new ArrayList<byte[]>();
        medecines_description=new ArrayList<>();medecines_prescriptions=new ArrayList<>();
        medecines_names=new ArrayList<>();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MedicinesActivity.this, AddMedicine.class);
                startActivity(intent);
            }
        });

        storeDataInArrays();


        customAdapter = new CustomAdapter4(MedicinesActivity.this,this,
                medecines_ids, medecines_names,
                medecines_photos,
                medecines_description,medecines_prescriptions);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicinesActivity.this));



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    private void storeDataInArrays() {

        Cursor cursor = myDB.readAllData("Medicines");
        if(cursor.getCount() <= 0 ){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                medecines_ids.add(cursor.getString(0));
                medecines_names.add(cursor.getString(1));
                medecines_photos.add(cursor.getBlob(2));
                medecines_description.add(cursor.getString(3));
                medecines_prescriptions.add(cursor.getString(4));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }





}






