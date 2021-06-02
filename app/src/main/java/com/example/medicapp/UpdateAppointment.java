package com.example.medicapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateAppointment extends AppCompatActivity  {

    EditText txtDate,txtReason;
    Spinner spinner;
    Button btnUpdate,btnDelete;

    String id,date,reason,docId,Doctor;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);


        txtDate=findViewById(R.id.txtDate_2);
        txtReason=findViewById(R.id.txtReason_2);
        spinner=findViewById(R.id.appointmentSpinner_2);
        btnUpdate=findViewById(R.id.btnUpdate_2);
        btnDelete=findViewById(R.id.btnDelete_2);

        myDB=new MyDatabaseHelper(UpdateAppointment.this);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = txtDate.getText().toString().trim();
                reason = txtReason.getText().toString().trim();

                myDB.updateData2(id,date,reason,Doctor);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });



        ArrayList<String> spinnerArray=fillSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Doctor=String.valueOf(parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getAndSetIntentData(spinner);


/*
        // make an adapter from the cursor
        String[] from = new String[] {"_id"};

        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca =
                new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to,0);

        // set layout for activated adapter
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(sca);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                docId=String.valueOf(id);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });


*/



    }







    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Appointment ?");
        builder.setMessage("Are you sure you want to delete this appointment ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateAppointment.this);
                myDB.DeleteData(id,"Appointments");
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


    ArrayList<String> fillSpinner(){
        Cursor cursor=myDB.readAllData("Doctors");
        // you need to have a list of data that you want the spinner to display
        ArrayList<String> spinnerArray =  new ArrayList<>();

        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                spinnerArray.add(cursor.getString(0));
            }
        }

        return spinnerArray;
    }



    void getAndSetIntentData( Spinner spinner){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("reason") && getIntent().hasExtra("docId")
        ){

            id = getIntent().getStringExtra("id");
            date = getIntent().getStringExtra("date");
            reason = getIntent().getStringExtra("reason");
            docId = getIntent().getStringExtra("docId");
            int docIdPosition=getIntent().getIntExtra("docIdPosition",0);


            txtDate.setText(date);
            txtReason.setText(reason);



            // set the spinner selected value from the db

            if(docId!=null){

               spinner.setSelection(((ArrayAdapter<String>)spinner.getAdapter()).getPosition(docId));
            }

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }



}