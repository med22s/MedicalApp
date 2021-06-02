package com.example.medicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPrescription extends AppCompatActivity {

    ImageView pickImag;
    MyDatabaseHelper myDB;
    EditText txtObservation;
    Spinner spinner;
    Button btnAdd;
    byte[] image = null;
    String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        pickImag=findViewById(R.id.pickImage);
        myDB=new MyDatabaseHelper(AddPrescription.this);
        spinner=findViewById(R.id.spinner2);
        txtObservation=findViewById(R.id.txtObservation);
        btnAdd=findViewById(R.id.btnAdd3);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                resize(bitmap);

                myDB.addPrescription(image,appId, txtObservation.getText().toString());
            }
        });


        // make an adapter from the cursor
        String[] from = new String[] {"_id"};

        // cursor

        Cursor c=myDB.readAllData("Appointments");

        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca =
                new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to,0);

        // set layout for activated adapter
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(sca);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                appId=String.valueOf(id);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
                Bitmap decodeStream = getInputStream(inputStream);
                resize(decodeStream);




            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }
        }
    }

    Bitmap getInputStream(InputStream inputStream){
        return  BitmapFactory.decodeStream(inputStream);
    }


    void resize(Bitmap decodeStream){
        decodeStream=getResizedBitmap(decodeStream,350,350);
        pickImag.setImageBitmap(decodeStream);

        image = getBytes(decodeStream);
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



    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}