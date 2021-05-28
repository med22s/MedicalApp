package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    EditText txtFirstName,txtLastName,txtAddress,txtTele,txtTelePerso;
    Button btnAdd;
    String Speciality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        txtFirstName=findViewById(R.id.txtFirstName);
        txtLastName=findViewById(R.id.txtLastName);
        txtAddress=findViewById(R.id.txtAddress);
        txtTele=findViewById(R.id.txtPhone);
        txtTelePerso=findViewById(R.id.txtPhone2);
        btnAdd=findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddDoctor.this);
                myDB.addDoctor(txtFirstName.getText().toString().trim(),
                        txtLastName.getText().toString().trim(),
                        txtAddress.getText().toString().trim(),
                        txtTele.getText().toString().trim(),
                        txtTelePerso.getText().toString().trim(),
                        Speciality
                        );
            }
        });


        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.specialities,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Speciality=String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}