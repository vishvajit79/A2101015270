package com.example.vishv.a2101015270;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HospitalDB";
    private static final int DB_VERSION = 1;


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDataBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS nurse");
        db.execSQL("DROP TABLE IF EXISTS patient");
        db.execSQL("DROP TABLE IF EXISTS doctor");

        updateMyDataBase(db);
    }

    private void updateMyDataBase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  doctor ("
                + "doctorId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT,"
                + "username TEXT,"
                + "password TEXT);");

        db.execSQL("CREATE TABLE  patient ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT,"
                + "room TEXT,"
                + "doctorId INTEGER);");

        db.execSQL("CREATE TABLE  test ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "patientId INTEGER,"
                + "bpl TEXT,"
                + "bph TEXT,"
                + "temperature TEXT);");

        db.execSQL("CREATE TABLE  nurse ("
                + "nurseId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT,"
                + "username TEXT,"
                + "password TEXT);");

//        addNewDoctor(db, "Vishvajit", "Kher", "Surgery", "vishvajit79", "123456");
    }

    public void addNewDoctor(SQLiteDatabase db, String fname, String lname, String department, String username, String password){
        ContentValues insert = new ContentValues();
        insert.put("firstname", fname);
        insert.put("lastname", lname);
        insert.put("department", department);
        insert.put("username", username);
        insert.put("password", password);
        db.insert("doctor", null, insert);
    }

    public void addNewNurse(SQLiteDatabase db, String fname, String lname, String department, String username, String password){
        ContentValues insert = new ContentValues();
        insert.put("firstname", fname);
        insert.put("lastname", lname);
        insert.put("department", department);
        insert.put("username", username);
        insert.put("password", password);
        db.insert("nurse", null, insert);
    }

    public void addPatient(SQLiteDatabase db, String fname, String lname, String department, String room, int id){
        ContentValues insert = new ContentValues();
        insert.put("firstname", fname);
        insert.put("lastname", lname);
        insert.put("department", department);
        insert.put("room", room);
        insert.put("doctorId", id);
        db.insert("patient", null, insert);
    }

    public void addTest(SQLiteDatabase db,  int id, String bpl, String bph, String temperature){
        ContentValues insert = new ContentValues();
        insert.put("patientId", id);
        insert.put("bpl", bpl);
        insert.put("bph", bph);
        insert.put("temperature", temperature);
        db.insert("test", null, insert);
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllDoctors(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT firstname FROM doctor" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public List<String> getAllPatient(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT firstname FROM patient" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}
