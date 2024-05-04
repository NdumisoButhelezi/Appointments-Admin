package com.example.appointments_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    private Button btnViewBookings;
    private Button btnRedirectToPatientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btnViewBookings = findViewById(R.id.btn_view_bookings);
        btnRedirectToPatientLogin = findViewById(R.id.btn_redirect_patient_login);

        btnViewBookings.setOnClickListener(v -> {
            // Navigate to view bookings
            // Placeholder: Replace this with your actual booking viewing activity or functionality
            Toast.makeText(this, "View Bookings Clicked!", Toast.LENGTH_SHORT).show();
        });

        btnRedirectToPatientLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        });
    }
}
