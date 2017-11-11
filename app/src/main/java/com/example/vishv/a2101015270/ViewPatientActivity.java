package com.example.vishv.a2101015270;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPatientActivity extends Activity {

    private TextView firstname;
    private TextView lastname;
    private TextView department;
    private TextView room;
    private TextView doctor;
    private ListView listView;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int patientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);
        final Bundle bundle = getIntent().getExtras();
        firstname = findViewById(R.id.view_patient_firstname_txt);
        lastname = findViewById(R.id.view_patient_lastname_txt);
        department = findViewById(R.id.view_patient_department_txt);
        room = findViewById(R.id.view_patient_room_txt);
        doctor = findViewById(R.id.view_patient_doctor_txt);
        listView = findViewById(R.id.view_patient_test_lv);
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
            assert bundle != null;
            cursor = db.rawQuery("select _id, bpl from test where patientId ==" + bundle.getLong("patientId"), null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"bpl"},
                    new int[]{android.R.id.text1},//map the contents of NAME col to text in ListView
                    0);
            listView.setAdapter(listAdapter);
        }catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewTestActivity.class);
                intent.putExtra("testId", l);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });

        patientId = (int) bundle.getLong("patientId");
        firstname.setText(findPatient(patientId, "firstname"));
        lastname.setText(findPatient(patientId, "lastname"));
        department.setText(findPatient(patientId, "department"));
        room.setText(findPatient(patientId, "room"));
        doctor.setText(findDoctor(patientId, "doctorId"));
    }

    private String findPatient(int patientId, String col){
        String data = null;
        String findQuery = "select " + col + " from patient where _id == " + patientId ;
        Cursor c = db.rawQuery(findQuery, null);
        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex(col));
                // do what ever you want here
            }while(c.moveToNext());
        }
        c.close();
        return data;
    }

    private String findDoctor(int patientId, String col){
        String doctorId = findPatient(patientId, col);
        String data = null;
        String findQuery = "select firstname from doctor where doctorId == " + doctorId ;
        Cursor c = db.rawQuery(findQuery, null);
        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("firstname"));
                // do what ever you want here
            }while(c.moveToNext());
        }
        c.close();
        return data;
    }
}
