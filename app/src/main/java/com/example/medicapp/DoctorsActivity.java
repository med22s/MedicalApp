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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.ArrayList;

public class DoctorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyDatabaseHelper myDB;
    ArrayList<String> doc_id, doc_firstName, doc_lastName, doc_address,doc_tele,doc_teleper,doc_speciality;
    CustomAdapter customAdapter;

    ImageView empty_imageview;
    TextView no_data;


    @Override
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);


        recyclerView=findViewById(R.id.recyclerView);
        floatingActionButton=findViewById(R.id.floatingActionButton);


        empty_imageview=findViewById(R.id.empty_imageview);
        no_data=findViewById(R.id.no_data);




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

        customAdapter = new CustomAdapter(DoctorsActivity.this,this, doc_id, doc_firstName,
                doc_lastName,
                doc_address,doc_tele,doc_teleper,doc_speciality);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorsActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }



    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Doctors?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(DoctorsActivity.this);
                myDB.deleteAllData("Doctors");

                Intent intent = new Intent(DoctorsActivity.this, DoctorsActivity.class);
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


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData("Doctors");
        if(cursor.getCount() <= 0 ){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
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
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

}