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

public class UpdateAppointment extends AppCompatActivity {

    EditText txtDate,txtReason;
    Spinner spinner;
    Button btnUpdate,btnDelete;

    String id,date,reason,docId;

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

                myDB.updateData2(id,date,reason,docId);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });



        Cursor c=myDB.readAllData();


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
                Toast.makeText(UpdateAppointment.this,String.valueOf(position),Toast.LENGTH_LONG).show();
                docId=String.valueOf(id);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        getAndSetIntentData(spinner);













    }




    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Appointment ?");
        builder.setMessage("Are you sure you want to delete this appointment ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateAppointment.this);
                myDB.DeleteData2(id);
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
                Toast.makeText(UpdateAppointment.this,String.valueOf(docIdPosition),Toast.LENGTH_LONG).show();
                spinner.setSelection(docIdPosition);
            }

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }



}