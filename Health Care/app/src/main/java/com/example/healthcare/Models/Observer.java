package com.example.healthcare.Models;

import java.util.ArrayList;

public interface Observer {
    void updatePatient(ArrayList<Patient> patients);
    void updateDoctors(ArrayList<Doctor> doctors);
    void updateAppointments(ArrayList<Appointment> appointments);
    void updateAvailabilities(ArrayList<Doctor> availabilities);
    void updateAdmin(ArrayList<User> admins);
}
