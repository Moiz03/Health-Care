package com.example.healthcare.Models;

import java.util.UUID;

public class Appointment {
    public String date, start_time,end_time, patient_email, doctor_email;
    public String id = UUID.randomUUID().toString();
}
