package com.example.vishv.a2101015270;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HospitalDB";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  doctor ("
                + "doctorId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT," +
                "username TEXT," +
                "password TEXT);");

        db.execSQL("CREATE TABLE  patient ("
                + "patientId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT,"
                + "doctorId INTEGER,"
                + "FOREIGN KEY(doctorId) REFERENCES doctor(doctorId));");

        db.execSQL("CREATE TABLE  test ("
                + "testId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT," +
                "patientId INTEGER," +
                "bpl TEXT," +
                "bph TEXT," +
                "temperature TEXT," +
                "FOREIGN KEY(patientId) REFERENCES patient(patientId));");

        db.execSQL("CREATE TABLE  nurse ("
                + "nurseId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "firstname TEXT, "
                + "lastname TEXT, "
                + "department TEXT);");

        insertData(db, "Vishvajit", "Kher", "GBC", "vishvajit79", "Vishu@9033");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS doctor");
        onCreate(db);
    }

    public void insertData(SQLiteDatabase db, String fname, String lname, String department, String username, String password){
        ContentValues insert = new ContentValues();
        insert.put("firstname", fname);
        insert.put("lastname", lname);
        insert.put("department", department);
        insert.put("username", username);
        insert.put("password", password);
        db.insert("doctor", null, insert);
    }

}
