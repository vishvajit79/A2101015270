package com.example.vishv.a2101015270;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button addPatientBtn = findViewById(R.id.dashboard_create_patient_btn);
        Button viewPatientBtn = findViewById(R.id.view_patient_dashboard_btn);

        addPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddPatientActivity.class);
                startActivity(intent);
            }
        });
    }
}
