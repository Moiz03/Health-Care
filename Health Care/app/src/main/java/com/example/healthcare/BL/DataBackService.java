package com.example.healthcare.BL;

import android.util.Log;

import com.example.healthcare.DL.DataService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.BookedAppointments;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;

import java.util.ArrayList;

public class DataBackService implements Observer{
    static DataService dataService ;
    static ArrayList<Doctor> doctors;
    static ArrayList<Doctor> availability;
    static ArrayList<Patient> patients;
    static ArrayList<Appointment> appointments;
    static ArrayList<Doctor> appointment_docs = new ArrayList<>();
    static ArrayList<Patient> appointment_patients = new ArrayList<>();
    static ArrayList<User> admin = new ArrayList<>();
    private Observer observer;

    public DataBackService(Observer observer) {
        if(dataService==null)
        {
            dataService = new DataService(this);
        }
        doctors = dataService.getDoctors();
        patients=dataService.getPatients();
        availability = dataService.getAllAvailabilities();
        appointments = dataService.getAppointments();
        this.observer=observer;
    }

    public ArrayList<Doctor> allDoctorData()
    {
        Log.w("DBS","REada all doctors");
        ArrayList<Doctor> allDoctorsData = new ArrayList<>();
        for(Doctor available: availability)
        {
            for (Doctor doc:doctors
            ) {
                if(available.email.compareTo(doc.email)==0)
                {
                    Doctor doctor = new Doctor();
                    doctor.email = available.email;
                    doctor.speciality = doc.speciality;
                    doctor.city = doc.city;
                    doctor.name = doc.name;
                    doctor.available_day=available.available_day;
                    doctor.available_start_time=available.available_start_time;
                    doctor.available_end_time=available.available_end_time;
                    allDoctorsData.add(doctor);

                }
            }
        }
        Log.w("DBS","REada all doctors "+availability.size());
        return allDoctorsData;
    }

    public boolean addAppointment(Appointment appointment)
    {
        return dataService.addAppointment(appointment);
    }

    public boolean addPatient(Patient patient)
    {
        return dataService.addPatient(patient);
    }
    public ArrayList<Doctor> getPatientAppointments(String email)
    {
        appointment_docs.clear();
        Log.w("INSIDE ",appointments.size()+"");
        for (Appointment appointment: appointments
        ) {

            if(appointment.patient_email.compareTo(email)==0)
            {
                for (Doctor doc: doctors
                ) {
                    if(appointment.doctor_email.compareTo(doc.email)==0)
                    {
                        doc.available_day=appointment.date;
                        doc.available_end_time=appointment.end_time;
                        doc.available_start_time=appointment.start_time;
                        appointment_docs.add(doc);
                    }
                }
            }

        }
        return appointment_docs;
    }
    public boolean removeAvailability(Doctor doctor)
    {
        return dataService.removeAvailability(doctor);
    }
    public ArrayList<Patient> getDoctorAppointments(String email)
    {
        appointment_patients.clear();
        Log.w("INSIDE ",appointments.size()+"");
        ArrayList<Patient> patients = dataService.getPatients();
        for (Appointment appointment: appointments
        ) {
            if(appointment.doctor_email.compareTo(email)==0)
            {
                for (Patient patient: patients
                ) {
                    if(appointment.patient_email.compareTo(patient.email)==0)
                    {
                        appointment_patients.add(patient);
                    }
                }
            }

        }
        return appointment_patients;
    }
    public boolean signInPatient(String email,String password)
    {
        ArrayList<Patient> patients = dataService.getPatients();
        for (Patient patient:patients
        ) {
            if(patient.email.compareTo(email)==0)
            {
                if(patient.password.compareTo(password)==0)
                {
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }
    public boolean signInDoctor(String email,String password)
    {
        //ArrayList<Doctor> doctors = dataService.getDoctors();
        Log.w("Doctor:","check");
        for (Doctor doctor:doctors
        ) {
            if(doctor.email.compareTo(email)==0)
            {
                if(doctor.password.compareTo(password)==0)
                {
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    public boolean signInAdmin(String email,String password)
    {
        ArrayList<User> admins = dataService.getAdmin();
        for (User admin:admins
        ) {
            if(admin.email.compareTo(email)==0)
            {
                if(admin.password.compareTo(password)==0)
                {
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }

    public ArrayList<BookedAppointments> getBookedAppointments(String email)
    {
        ArrayList<BookedAppointments> appointments_return = new ArrayList<>();
        for (Appointment appointment: appointments
        ) {
            if(appointment.doctor_email.compareTo(email)==0)
            {
                for (Patient patient: patients
                ) {
                    if(appointment.patient_email.compareTo(patient.email)==0)
                    {
                        BookedAppointments bookedAppointments = new BookedAppointments();
                        bookedAppointments.appointment = appointment;
                        bookedAppointments.patient = patient;

                        appointments_return.add(bookedAppointments);
                    }
                }
            }

        }
        return appointments_return;
    }
    @Override
    public void updatePatient(ArrayList<Patient> patients) {
        this.patients=patients;
        observer.updatePatient(patients);
    }

    @Override
    public void updateDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
        observer.updateDoctors(allDoctorData());
    }

    @Override
    public void updateAppointments(ArrayList<Appointment> appointments) {
        Log.w("DBS", "Got "+ appointments.size());
        this.appointments = appointments;
        observer.updateAppointments(appointments);
    }

    @Override
    public void updateAvailabilities(ArrayList<Doctor> availabilities) {
        this.availability = availabilities;
        Log.w("Removed:","BL");
        observer.updateAvailabilities(availabilities);
        observer.updateDoctors(allDoctorData());
    }

    @Override
    public void updateAdmin(ArrayList<User> admins) {
        this.admin = admins;
        observer.updateAdmin(admins);
    }
}