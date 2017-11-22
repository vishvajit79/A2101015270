package com.example.vishv.a2101015270;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DashboardActivity extends Activity {

    private TextView textView;
    private ListView listView;
    private Button createNewPatientBtn;
    private Button createNewTestBtn;
    private SQLiteDatabase db;
    private Cursor cursor;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final Bundle bundle = getIntent().getExtras();
        textView = findViewById(R.id.dashboard_welcome_txt);
        assert bundle != null;
        textView.setText("Welcome, " + bundle.getString("username").toLowerCase());
        createNewPatientBtn = findViewById(R.id.dashboard_create_patient_btn);
        createNewTestBtn = findViewById(R.id.dashbaord_create_new_test_btn);

        final ListView listView = findViewById(R.id.list);
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
            cursor = db.rawQuery("select _id, firstname from patient", null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"firstname"},
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
                Intent intent = new Intent(view.getContext(), ViewPatientActivity.class);
                intent.putExtra("patientId", l);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });

        createNewPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddPatientActivity.class);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });


        createNewTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHelper.checkPatient()){
                Intent intent = new Intent(getApplicationContext(), AddTestActivity.class);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
                }
                else if(!databaseHelper.checkPatient()){
                    Toast.makeText(getApplicationContext(), "You need to add patient first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
