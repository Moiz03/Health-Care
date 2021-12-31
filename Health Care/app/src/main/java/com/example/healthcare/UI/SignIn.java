package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.UI.R;

public class SignIn extends AppCompatActivity {

    EditText email, password;
    Button signIn;
    TextView register;
    DataBackService dataBackService = GetBL.getBL();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String user = getIntent().getStringExtra("user");
        if(user.compareToIgnoreCase("patient")==0)
        {
            setContentView(R.layout.signin_patient);
            register = findViewById(R.id.signup_patient);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignIn.this, SignUp.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            });
        }
        else
        {
            setContentView(R.layout.signin);

        }
        email = findViewById(R.id.email_signin);
        password = findViewById(R.id.password_signin);

        signIn = findViewById(R.id.button_signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.compareToIgnoreCase("patient")==0)
                {
                    Log.w("SIgn In","Success");
                    if(dataBackService.signInPatient(email.getText().toString(),password.getText().toString()))
                    {
                        //sign in successful
                        Log.w("SIgn In","Success");
                        Intent intent = new Intent(SignIn.this, SignedInPatient.class);
                        intent.putExtra("email",email.getText().toString());
                        startActivity(intent);
                    }
                }
                else if(user.compareToIgnoreCase("doctor")==0)
                {
                    if(dataBackService.signInDoctor(email.getText().toString(),password.getText().toString()))
                    {
                        Intent intent = new Intent(SignIn.this, ViewAppointments.class);
                        intent.putExtra("user","doctor");
                        intent.putExtra("email",email.getText().toString());
                        startActivity(intent);
                    }

                }
                else
                {
                    if(dataBackService.signInAdmin(email.getText().toString(),password.getText().toString()))
                    {
                        Log.w("SIgn In","Success");
                        Intent intent = new Intent(SignIn.this, SignedInAdmin.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
