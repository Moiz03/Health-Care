package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.DL.DataService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;
import com.example.healthcare.UI.R;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    EditText email,password, name, phone;
    Button signup;
    Spinner spinner_speciality,spinner_city;
    String user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getIntent().getStringExtra("user");

        if(user.compareToIgnoreCase("patient")==0)
        {
            setContentView(R.layout.signup_patient);
            setUpPatientSignUp();
        }
        else
        {
            setUpDoctorSignUp();
        }


    }
    void setUpPatientSignUp()
    {
        email = findViewById(R.id.email_signup_patient);
        password = findViewById(R.id.password_signup_patient);
        name = findViewById(R.id.name_signup_patient);
        phone = findViewById(R.id.phone_signup_patient);
        signup = findViewById(R.id.button_signup_patient);

        DataBackService dataService = GetBL.getBL();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.length()>0 && password.length()>0 && phone.length()>0 && name.length() >0)
                {
                    Patient patient = new Patient();
                    patient.email=email.getText().toString();
                    patient.name=name.getText().toString();
                    patient.phone=phone.getText().toString();
                    patient.password=password.getText().toString();
                    patient.age = 0;
                    dataService.addPatient(patient);
                    Intent intent = new Intent(SignUp.this,SignIn.class);
                    intent.putExtra("user","patient");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Required fields are empty.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void setUpDoctorSignUp()
    {
        setContentView(R.layout.signup_doctor);
        email = findViewById(R.id.email_signup_doctor);
        password = findViewById(R.id.password_signup_doctor);
        name = findViewById(R.id.name_signup_doctor);
        phone = findViewById(R.id.phone_signup_doctor);
        signup = findViewById(R.id.button_signup_doctor);
        spinner_speciality = findViewById(R.id.spinner_doctor_type);
        spinner_city = findViewById(R.id.spinner_doctor_city);

        DataService dataService = new DataService(new Observer() {
            @Override
            public void updatePatient(ArrayList<Patient> patients) {

            }

            @Override
            public void updateDoctors(ArrayList<Doctor> doctors) {

            }

            @Override
            public void updateAppointments(ArrayList<Appointment> appointments) {

            }

            @Override
            public void updateAvailabilities(ArrayList<Doctor> availabilities) {

            }

            @Override
            public void updateAdmin(ArrayList<User> admins) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.length()>0 && password.length()>0 && phone.length()>0 && name.length() >0)
                {
                    Doctor doctor = new Doctor();
                    doctor.email=email.getText().toString();
                    doctor.name=name.getText().toString();
                    doctor.password=password.getText().toString();
                    doctor.city = spinner_city.getSelectedItem().toString();
                    doctor.phone = phone.getText().toString();
                    doctor.speciality = spinner_speciality.getSelectedItem().toString();
                    //doctor.available_start_time = doctor.available_end_time = doctor.available_day =" ";
                    dataService.addDoctor(doctor);
//                    Intent intent = new Intent(SignUp.this,SignIn.class);
//                    intent.putExtra("user","doctor");
//                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Required fields are empty.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
