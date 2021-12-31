package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.UI.R;

public class SignedInAdmin extends AppCompatActivity {
    String email;
    ImageButton add_doc,add_avail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_siggned_in);

        add_doc = findViewById(R.id.add_doctor);
        add_avail = findViewById(R.id.add_availability);

        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignedInAdmin.this,SignUp.class);
                intent.putExtra("user","doctor");
                startActivity(intent);
            }
        });

        add_avail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignedInAdmin.this, AddAvailability.class);
                startActivity(intent);
            }
        });
    }
}
