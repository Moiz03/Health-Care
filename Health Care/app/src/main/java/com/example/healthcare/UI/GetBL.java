package com.example.healthcare.UI;

import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;

import java.util.ArrayList;

public class GetBL {
    public static DataBackService getBL()
    {
        DataBackService dataBackService = new DataBackService(new Observer() {
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
        return dataBackService;
    }
}
