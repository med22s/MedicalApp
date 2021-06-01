package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAppointment extends AppCompatActivity  {

    EditText txtDate,txtReason;
    Spinner spinner;
    ArrayList<String> doc_id, doc_firstName, doc_lastName;
    MyDatabaseHelper myDB;
    Button btnAdd;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);



        txtDate=findViewById(R.id.txtDate);
        txtReason=findViewById(R.id.txtReason);
        spinner=findViewById(R.id.appointmentSpinner);

        btnAdd=findViewById(R.id.btnAdd2);

        myDB=new MyDatabaseHelper(this);

        doc_id = new ArrayList<>();
        doc_firstName = new ArrayList<>();
        doc_lastName = new ArrayList<>();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.addAppointment(txtDate.getText().toString().trim(),txtReason.getText().toString().trim()
                ,docId.trim());
            }
        });

        Cursor c=storeDataInArrays();


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

    }



    Cursor storeDataInArrays(){
        Cursor cursor = myDB.readAllData("Doctors");
        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                doc_id.add(cursor.getString(0));
                doc_firstName.add(cursor.getString(1));
                doc_lastName.add(cursor.getString(2));

            }
        }

        return cursor;
    }



}