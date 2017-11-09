package com.example.vishv.a2101015270;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private TextView textView;
    private ListView listView;
    private Button button;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final Bundle bundle = getIntent().getExtras();
        textView = findViewById(R.id.dashboard_welcome_txt);
        assert bundle != null;
        textView.setText("Welcome, " + bundle.getString("username").toUpperCase());
        button = findViewById(R.id.dashboard_create_patient_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddPatientActivity.class);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });
    }
}
