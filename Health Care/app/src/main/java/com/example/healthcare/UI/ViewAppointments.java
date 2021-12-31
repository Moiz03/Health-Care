package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.UI.R;

import java.util.ArrayList;

public class ViewAppointments extends AppCompatActivity {

    DataBackService dataBackService = GetBL.getBL();
    private RecyclerView recyclerView;
    String email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String user = getIntent().getStringExtra("user");
        email= getIntent().getStringExtra("email");

        if(user.compareToIgnoreCase("patient")==0)
        {
            setContentView(R.layout.appointment_doctor);
            ArrayList<Doctor> appointment_docs = dataBackService.getPatientAppointments(email);
            recyclerView = findViewById(R.id.recycle_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerViewAdapterDoctor mAdapter = new RecyclerViewAdapterDoctor(this, appointment_docs, email,false,null);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            setContentView(R.layout.appointment_patient);
            ArrayList<Patient> appointment_patients = dataBackService.getDoctorAppointments(email);

            recyclerView = findViewById(R.id.recycle_view);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerViewAdapterPatients mAdapter = new RecyclerViewAdapterPatients(this, appointment_patients,email);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewAppointments.this,SignedInPatient.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
