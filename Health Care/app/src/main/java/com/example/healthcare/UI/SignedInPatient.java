package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.UI.R;

public class SignedInPatient extends AppCompatActivity {
    String email;
    ImageButton add,view;
    protected void onCreate(Bundle savedInstanceState) {
        email = getIntent().getStringExtra("email");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_signned_in);

        add = findViewById(R.id.add_appointment);
        view = findViewById(R.id.view_appointment);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignedInPatient.this,BookAppointment.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignedInPatient.this, ViewAppointments.class);
                intent.putExtra("email",email);
                intent.putExtra("user","patient");
                startActivity(intent);
            }
        });
    }
}
