package com.example.medicapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class UpdatePrescription extends AppCompatActivity {

    EditText txtObservation2;
    Spinner spinner2_2;
    Button btnUpdate3_3,btnDelete3_3;
    ImageView pickImage2;

    String id,observation,appointment,selectedAppoint;
    byte[] image;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_prescription);

        txtObservation2=findViewById(R.id.txtObservation2);
        spinner2_2=findViewById(R.id.spinner2_2);
        btnUpdate3_3=findViewById(R.id.btnUpdate3_3);
        btnDelete3_3=findViewById(R.id.btnDelete3_3);
        pickImage2=findViewById(R.id.pickImage2);

        myDB=new MyDatabaseHelper(this);


        btnUpdate3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                observation = txtObservation2.getText().toString().trim();

                BitmapDrawable drawable = (BitmapDrawable) pickImage2.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                image = getBytes(bitmap);


                myDB.updateData3(id,image,selectedAppoint,observation);
            }
        });


        btnDelete3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


        ArrayList<String> spinnerArray=fillSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2_2.setAdapter(adapter);


        spinner2_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAppoint=String.valueOf(parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getAndSetIntentData(spinner2_2);




    }




    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Prescription ?");
        builder.setMessage("Are you sure you want to delete this prescription ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdatePrescription.this);
                myDB.DeleteData(id,"Prescriptions");
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
        Cursor cursor=myDB.readAllData("Appointments");
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
        if(getIntent().hasExtra("id") && getIntent().hasExtra("image") &&
                getIntent().hasExtra("observation") && getIntent().hasExtra("appointment")
        ){

            id = getIntent().getStringExtra("id");
            image = getIntent().getByteArrayExtra("image");
            observation = getIntent().getStringExtra("observation");
            appointment = getIntent().getStringExtra("appointment");



            txtObservation2.setText(observation);

            // set the image


            Bitmap decodeStream= BitmapFactory.decodeByteArray(image,
                    0, image.length);

            decodeStream=getResizedBitmap(decodeStream,350,350);
            pickImage2.setImageBitmap(decodeStream);


            // set the spinner selected value from the db

            if(appointment!=null){

                spinner.setSelection(((ArrayAdapter<String>)spinner.getAdapter()).getPosition(appointment));
            }

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }



    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }



    public void openGalleries(View view) {

        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                decodeStream=getResizedBitmap(decodeStream,350,350);
                pickImage2.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);


            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }
        }
    }


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }




}