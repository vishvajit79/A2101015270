package com.example.vishv.a2101015270;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private TextView username;
    private TextView password;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        username = findViewById(R.id.login_username_txt);
        password = findViewById(R.id.login_password_txt);
        spinner = findViewById(R.id.login_spinner);

        Button loginBtn = findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean validUser = checkLogin(username.getText().toString(), password.getText().toString());

                if(validUser) {
                    Intent i = new Intent(view.getContext(), DashboardActivity.class);
                    i.putExtra("username", username.getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(view.getContext(), "Invalid login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkLogin(String username, String password) {
        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        String table = spinner.getSelectedItem().toString();
//        Cursor c = db.rawQuery("SELECT * FROM doctor WHERE " + username + " =? AND" + password + " =?", null);
        Cursor c = db.rawQuery("SELECT * FROM '" + table+ "' WHERE username = '" + username + "' AND password = '" + password+"'", null);

        if(c.getCount() <= 0) {
            c.close();
            db.close();
            return false;
        } else {
            c.close();
            db.close();
            return true;
        }
    }
}
