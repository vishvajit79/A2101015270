package com.example.vishv.a2101015270;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class RegisterActivity extends Activity{

    private SQLiteDatabase db;
    private Spinner spinner;
    private EditText firstname;
    private EditText lastname;
    private EditText department;
    private EditText username;
    private EditText password;
    private Button button;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getWritableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        databaseHelper = new DatabaseHelper(this);
        spinner = findViewById(R.id.register_spinner);
        firstname = findViewById(R.id.register_firstnane_txt);
        lastname = findViewById(R.id.register_lastname_txt);
        department = findViewById(R.id.register_department_txt);
        username = findViewById(R.id.register_username_txt);
        password = findViewById(R.id.register_password_txt);
        button = findViewById(R.id.register_register_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                if(Objects.equals(spinner.getSelectedItem().toString(), "Doctor")){
                    databaseHelper.addNewDoctor(db, firstname.getText().toString(), lastname.getText().toString(), department.getText().toString(), username.getText().toString(), password.getText().toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Doctor account added", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                }
                else if(Objects.equals(spinner.getSelectedItem().toString(), "Nurse")){
                    databaseHelper.addNewNurse(db, firstname.getText().toString(), lastname.getText().toString(), department.getText().toString(), username.getText().toString(), password.getText().toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Nurse account added", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error while saving data", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

    }
}
