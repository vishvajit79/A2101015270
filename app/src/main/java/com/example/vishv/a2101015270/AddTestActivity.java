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

public class AddTestActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText bpl;
    private EditText bph;
    private EditText temperature;
    private Spinner patientIdSpn;
    private Button button;
    private long patientId;
    private SQLiteDatabase sdb;
    DatabaseHelper db = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

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

        bpl = findViewById(R.id.add_test_bpl_txt);
        bph = findViewById(R.id.add_test_bph_txt);
        temperature = findViewById(R.id.add_test_temperature_txt);
        patientIdSpn = findViewById(R.id.add_test_patientId_sp);
        button = findViewById(R.id.add_test_add_test_btn);
        // Spinner click listener
        patientIdSpn.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        loadSpinnerData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateTextEdits())
                    return;
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                db.addTest(sdb, ((int) patientId), bpl.getText().toString(), bph.getText().toString(), temperature.getText().toString());
                assert bundle != null;
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });
    }

    private void loadSpinnerData() {
        // Spinner Drop down elements
        List<String> patientList = db.getAllPatient();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientList);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        patientIdSpn.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        patientId = adapterView.getSelectedItemPosition();
        patientId = patientId + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validateTextEdits() {
        if (isEmptyString(bph.getText().toString())) {
            bph.setError("Required");
            return false;
        }
        if (isEmptyString(bpl.getText().toString())) {
            bpl.setError("Required");
            return false;
        }
        if (isEmptyString(temperature.getText().toString())) {
            temperature.setError("Required");
            return false;
        }

        return true;
    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}
