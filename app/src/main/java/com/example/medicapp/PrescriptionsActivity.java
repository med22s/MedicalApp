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

public class PrescriptionsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    MyDatabaseHelper myDB;
    ArrayList<String> prescription_ids, prescription_observations,prescription_appointments;
    ArrayList<byte[]> prescription_imgs;
    CustomAdapter3 customAdapter;

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
        builder.setMessage("Are you sure you want to delete all Prescriptions?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(PrescriptionsActivity.this);
                myDB.deleteAllData("Prescriptions");

                Intent intent = new Intent(PrescriptionsActivity.this, PrescriptionsActivity.class);
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
        setContentView(R.layout.activity_prescriptions);

        recyclerView=findViewById(R.id.recyclerView3);
        floatingActionButton=findViewById(R.id.floatingActionButton3);

        myDB=new MyDatabaseHelper(PrescriptionsActivity.this);
        prescription_ids=new ArrayList<>(); prescription_imgs=new ArrayList<byte[]>();
        prescription_observations=new ArrayList<>();prescription_appointments=new ArrayList<>();

        empty_imageview=findViewById(R.id.empty_imageview3);
        no_data=findViewById(R.id.no_data3);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PrescriptionsActivity.this,AddPrescription.class);
                startActivity(intent);
            }
        });

        storeDataInArrays();



        customAdapter = new CustomAdapter3(PrescriptionsActivity.this,this,
                prescription_ids, prescription_imgs,
                prescription_observations,
                prescription_appointments);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionsActivity.this));





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }


    private void storeDataInArrays() {


        Cursor cursor = myDB.readAllData("Prescriptions");
        if(cursor.getCount() <= 0 ){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                prescription_ids.add(cursor.getString(0));
                prescription_imgs.add(cursor.getBlob(1));
                prescription_appointments.add(cursor.getString(2));
                prescription_observations.add(cursor.getString(3));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }





}