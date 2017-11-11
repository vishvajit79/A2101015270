package com.example.vishv.a2101015270;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddPatientActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private SQLiteDatabase sdb;
    private EditText firstname;
    private EditText lastname;
    private EditText department;
    private EditText room;
    private Button button;
    private long doctorId;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        final Bundle bundle = getIntent().getExtras();
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            sdb = databaseHelper.getWritableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        spinner = findViewById(R.id.add_patient_doctor_spn);
        firstname = findViewById(R.id.add_patient_firstname_txt);
        lastname = findViewById(R.id.add_patient_lastname_txt);
        department = findViewById(R.id.add_patient_department_txt);
        room = findViewById(R.id.add_patient_room_txt);
        button = findViewById(R.id.add_patient_create_btn);
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Loading spinner data from database
        loadSpinnerData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateTextEdits())
                    return;
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                db.addPatient(sdb, firstname.getText().toString(), lastname.getText().toString(), department.getText().toString(), room.getText().toString(), ((int) doctorId));
                assert bundle != null;
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        doctorId = adapterView.getSelectedItemPosition();
        doctorId = doctorId + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadSpinnerData() {
        // Spinner Drop down elements
        List<String> doctorList = db.getAllDoctors();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorList);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private boolean validateTextEdits() {
        if (isEmptyString(firstname.getText().toString())) {
            firstname.setError("Required");
            return false;
        }
        if (isEmptyString(lastname.getText().toString())) {
            lastname.setError("Required");
            return false;
        }
        if (isEmptyString(department.getText().toString())) {
            department.setError("Required");
            return false;
        }
        if (isEmptyString(room.getText().toString())) {
            room.setError("Required");
            return false;
        }

        return true;
    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}
