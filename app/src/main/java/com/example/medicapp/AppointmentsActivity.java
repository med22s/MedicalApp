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

public class AppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    MyDatabaseHelper myDB;
    ArrayList<String> appointment_ids, appointment_dates, appointment_reasons,appointment_docs;
    CustomAdapter2 customAdapter;

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
        builder.setMessage("Are you sure you want to delete all Appointments?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AppointmentsActivity.this);
                myDB.deleteAllData2();

                Intent intent = new Intent(AppointmentsActivity.this, AppointmentsActivity.class);
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

            setContentView(R.layout.activity_appointments);



            recyclerView=findViewById(R.id.recyclerView2);
            floatingActionButton=findViewById(R.id.floatingActionButton2);



            myDB=new MyDatabaseHelper(AppointmentsActivity.this);
            appointment_ids=new ArrayList<>(); appointment_dates=new ArrayList<>();
            appointment_reasons=new ArrayList<>();appointment_docs=new ArrayList<>();

            empty_imageview=findViewById(R.id.empty_imageview2);
            no_data=findViewById(R.id.no_data2);


            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(AppointmentsActivity.this,AddAppointment.class);
                    startActivity(intent);
                }
            });


            storeDataInArrays();

            customAdapter = new CustomAdapter2(AppointmentsActivity.this,this,
                    appointment_ids, appointment_dates,
                    appointment_reasons,
                    appointment_docs);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentsActivity.this));





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    private void storeDataInArrays() {


        Cursor cursor = myDB.readAllData2();
        if(cursor.getCount() <= 0 ){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                appointment_ids.add(cursor.getString(0));
                appointment_dates.add(cursor.getString(1));
                appointment_reasons.add(cursor.getString(2));
                appointment_docs.add(cursor.getString(3));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

}
