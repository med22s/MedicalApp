package com.example.medicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MedicApp.db";
    private static final int DATABASE_VERSION = 1;


    // Doctors DB:

    private static final String TABLE_NAME = "Doctors";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FIRSTNAME = "first_name";
    private static final String COLUMN_LASTNAME = "last_name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_TELEPHONE = "tele";
    private static final String COLUMN_PERTELEPHONE = "per_tele";
    private static final String COLUMN_SPECIALITY = "speciality";

    // Appointments db

    private static final String TABLE_NAME2 = "Appointments";
    private static final String COLUMN_ID2 = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_REASON = "reason";
    private static final String COLUMN_DOC_ID = "doc_id";


    // Prescriptions db

    private static final String TABLE_NAME3 = "Prescriptions";
    private static final String COLUMN_ID3 = "_id";
    private static final String COLUMN_PHOTO = "photo";
    private static final String COLUMN_APPOINTMENT = "appointment";
    private static final String COLUMN_OBSERVATION = "observation";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Doctors db

        String query1 = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_TELEPHONE +" TEXT, " +
                COLUMN_PERTELEPHONE +" TEXT, " +
                COLUMN_SPECIALITY +" TEXT);";

        db.execSQL(query1);

        // Appointments db

        String query2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_REASON + " TEXT, " +
                COLUMN_DOC_ID + " TEXT REFERENCES Doctors(_id) ON DELETE CASCADE);";
        db.execSQL(query2);

        // Prescriptions db

        String query3 = "CREATE TABLE " + TABLE_NAME3 +
                " (" + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PHOTO + " BLOB, " +
                COLUMN_APPOINTMENT + " TEXT REFERENCES Appointments(_id) ON DELETE CASCADE, " +
                COLUMN_OBSERVATION + " TEXT);";
        db.execSQL(query3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    void addDoctor(String firstName, String lastName, String address,
                   String tele,String per_tele,String speciality){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRSTNAME, firstName);
        cv.put(COLUMN_LASTNAME, lastName);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_TELEPHONE, tele);
        cv.put(COLUMN_PERTELEPHONE, per_tele);
        cv.put(COLUMN_SPECIALITY, speciality);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(String table_name){
        String query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateData(String row_id, String firstName, String lastName, String address,String tele,
                    String per_tele,String speciality){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRSTNAME, firstName);
        cv.put(COLUMN_LASTNAME, lastName);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_TELEPHONE, tele);
        cv.put(COLUMN_PERTELEPHONE, per_tele);
        cv.put(COLUMN_SPECIALITY, speciality);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


    void DeleteData(String row_id,String table_name){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(table_name,"_id=?",new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteAllData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name);
    }


    void addAppointment(String date, String reason, String doc_id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REASON, reason);
        cv.put(COLUMN_DOC_ID, doc_id);

        long result = db.insert(TABLE_NAME2,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }




    void updateData2(String row_id,String date, String reason, String doc_id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REASON, reason);
        cv.put(COLUMN_DOC_ID, doc_id);

        long result = db.update(TABLE_NAME2, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }



    void updateData3(String row_id,String date, String reason, String doc_id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REASON, reason);
        cv.put(COLUMN_DOC_ID, doc_id);

        long result = db.update(TABLE_NAME2, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


}
