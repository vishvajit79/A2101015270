package com.example.vishv.a2101015270;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTestActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private TextView bpl;
    private TextView bph;
    private TextView temperature;
    private int testId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        final Bundle bundle = getIntent().getExtras();
        bpl = findViewById(R.id.view_test_bpl_txt);
        bph = findViewById(R.id.view_test_bph_txt);
        temperature = findViewById(R.id.view_test_temperature_txt);
        try {
            SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
            db = databaseHelper.getReadableDatabase();
            Toast toast = Toast.makeText(this, "Database available", Toast.LENGTH_SHORT);
            toast.show();
        }catch(SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        testId = (int) bundle.getLong("testId");
        bpl.setText(findTest(testId, "bpl"));
        bph.setText(findTest(testId, "bph"));
        temperature.setText(findTest(testId, "temperature"));
    }

    private String findTest(int testId, String col){
        String data = null;
        String findQuery = "select " + col + " from test where _id == " + testId ;
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
}
