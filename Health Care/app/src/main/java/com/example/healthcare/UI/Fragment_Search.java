package com.example.healthcare.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;
import com.example.healthcare.UI.R;

import java.util.ArrayList;

public class Fragment_Search extends Fragment implements Observer
{
    private ArrayList<Doctor> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapterDoctor mAdapter;
    DataBackService dbs = new DataBackService(this);
    private String email;
    View view;
    RecyclerViewAdapterDoctor.AppointmentBookedListner listner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.appointment_doctor,container,false);
        data = dbs.allDoctorData();
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        listner = new RecyclerViewAdapterDoctor.AppointmentBookedListner() {
            @Override
            public void update() {
                Log.w("LISTNER","I was listening");
                mAdapter.dataRefresher(dbs.allDoctorData());
                Intent intent = new Intent(getContext(), ViewAppointments.class);
                intent.putExtra("email",email);
                intent.putExtra("user","patient");
                startActivity(intent);

            }
        };
        if(email!=null) {
            mAdapter = new RecyclerViewAdapterDoctor(getContext(), data, email, true,listner);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }
    void searchData(String search)
    {
        mAdapter.getFilter().filter(search);
    }

    public void setData(ArrayList<Doctor> data) {
        this.data = data;
        mAdapter.dataRefresher(data);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void updatePatient(ArrayList<Patient> patients) {

    }

    @Override
    public void updateDoctors(ArrayList<Doctor> doctors) {
        mAdapter.dataRefresher(doctors);
    }

    @Override
    public void updateAppointments(ArrayList<Appointment> appointments) {

    }

    @Override
    public void updateAvailabilities(ArrayList<Doctor> availabilities) {
        mAdapter.dataRefresher(dbs.allDoctorData());
    }

    @Override
    public void updateAdmin(ArrayList<User> admins) {

    }

}
