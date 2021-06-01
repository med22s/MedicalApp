package com.example.medicapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtFirstName,txtLastName,txtAddress,txtTele,txtTelePerso;
    Button btnUpdate,btnDelete;
    String Speciality;

    String id;
    String firstName;
    String lastName;
    String address;
    String tele;
    String per_tele;
    String speciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        txtFirstName=findViewById(R.id.txtFirstName_2);
        txtLastName=findViewById(R.id.txtLastName_2);
        txtAddress=findViewById(R.id.txtAddress_2);
        txtTele=findViewById(R.id.txtPhone_2);
        txtTelePerso=findViewById(R.id.txtPhone2_2);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);


        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.specialities,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        getAndSetIntentData(adapter,spinner);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper db=new MyDatabaseHelper(UpdateActivity.this);


                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                address = txtAddress.getText().toString().trim();
                tele = txtTele.getText().toString().trim();
                per_tele = txtTelePerso.getText().toString().trim();

                db.updateData(id,firstName,lastName,address,tele,per_tele,Speciality);

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });


    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Doctor ?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.DeleteData(id,"Doctors");
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



    void getAndSetIntentData(ArrayAdapter<CharSequence> adapter,Spinner spinner){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("firstName") &&
                getIntent().hasExtra("lastName") && getIntent().hasExtra("address") &&
                getIntent().hasExtra("tele") &&
                getIntent().hasExtra("per_tele") &&
                getIntent().hasExtra("speciality")

        ){

            id = getIntent().getStringExtra("id");
            firstName = getIntent().getStringExtra("firstName");
            lastName = getIntent().getStringExtra("lastName");
            address = getIntent().getStringExtra("address");
            tele = getIntent().getStringExtra("tele");
            per_tele = getIntent().getStringExtra("per_tele");
            speciality = getIntent().getStringExtra("speciality");



            txtFirstName.setText(firstName);
            txtLastName.setText(lastName);
            txtAddress.setText(address);
            txtTele.setText(tele);
            txtTelePerso.setText(per_tele);


            // set the spinner selected value from the db

            if(speciality!=null){
                int spinnerPosition = adapter.getPosition(speciality);
                spinner.setSelection(spinnerPosition);
            }

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Speciality=String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}