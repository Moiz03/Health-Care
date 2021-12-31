package com.example.healthcare.DL;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataService {
    static FirebaseDatabase database = null;
    static DatabaseReference dbReferenceDoctors = null;
    static DatabaseReference dbReferenceAppointments = null;
    static DatabaseReference dbReferencePatients = null;
    static DatabaseReference dbReferenceAdmin = null;
    static DatabaseReference dbAvailability = null;
    ArrayList<Doctor> allDoctors= new ArrayList<>();
    ArrayList<User> allAdmins= new ArrayList<>();
    ArrayList<Patient> allPatients= new ArrayList<>();
    ArrayList<Appointment> allAppointment= new ArrayList<>();
    ArrayList<Doctor> availabilities= new ArrayList<>();
    private Observer observer;
    Boolean flag = false;
    static int patients,doctors;

    DataService dataService = null;

    public DataService(Observer observer)
    {
        this.observer = observer;
        if(database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);

            dbReferenceDoctors = database.getReference().child("Doctors");
            dbReferenceAppointments = database.getReference().child("Appointments");
            dbReferencePatients = database.getReference().child("Patients");
            dbReferenceAdmin = database.getReference().child("Admin");
            dbAvailability = database.getReference().child("Availability");
            patients=0;
            doctors=0;
        }

    }
    public boolean addDoctor(Doctor newDoctor) {
        flag=false;

        dbReferenceDoctors.orderByChild("email").equalTo(newDoctor.email).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists() ){

                    flag=false;

                }
                else{
                    Log.w("DL","Came");
                    doctors++;
                    newDoctor.id = doctors;
                    dbReferenceDoctors.child(String.valueOf(newDoctor.id)).setValue(newDoctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                flag = true;
                            }
                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return flag;

    }
    public boolean addPatient(Patient newPatient) {
        flag=false;

        dbReferencePatients.orderByChild("email").equalTo(newPatient.email).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists() ){

                    flag=false;

                }
                else{
                    patients++;
                    newPatient.id = patients;
                    dbReferencePatients.child(String.valueOf(newPatient.id)).setValue(newPatient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                flag = true;
                            }
                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return flag;

    }
    public boolean addAppointment(Appointment newAppointment) {
        //flag=false;
        dbReferenceAppointments.child(newAppointment.id).setValue(newAppointment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    flag = true;
                }
            }
        });
        return flag;

    }
    public boolean addAdmin(User newAdmin) {
        flag=false;
        Log.w("ADMINNN","sdfdjshkjfhs");
        dbReferenceAdmin.orderByChild("email").equalTo(newAdmin.email).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists() ){

                    flag=false;

                }
                else{
                    Log.w("DL","Came");

                    dbReferenceDoctors.child(String.valueOf(newAdmin.id)).setValue(newAdmin).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Log.w("dfs","sdfsnf");
                                flag = true;
                            }
                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("dfs","cancelled");
            }
        });
        return flag;

    }
    public ArrayList<User> getAdmin()
    {
        dbReferenceAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    allAdmins.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.w("Fire Admin", "Came to read values "+ ds.getChildrenCount());
                        User user = ds.getValue(User.class);
                        //Log.w("firebaseDb", "Donors : "+ doctor.name);
                        allAdmins.add(user);
                    }
                    Log.w("fireBaseDb", "Updated values."+allAdmins.size());
                    observer.updateAdmin(allAdmins);
                }
                catch (Exception ex) {
                    // Log.e("firebaseDb", ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebaseDb", "Failed to read value.", error.toException());
            }
        });
        return allAdmins;
    }

    public ArrayList<Doctor> getDoctors()
    {
        dbReferenceDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    allDoctors.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // Log.w("firebaseDb", "Came to read values"+ ds.getChildrenCount());
                        Doctor doctor = ds.getValue(Doctor.class);
                        //Log.w("firebaseDb", "Donors : "+ doctor.name);
                        allDoctors.add(doctor);
                    }
                    Log.w("fireBaseDb", "Updated values."+allAdmins.size());
                    ArrayList<Doctor> doctorsData = new ArrayList<>();
                    doctorsData.addAll(allDoctors);
                    observer.updateDoctors(doctorsData);
                }
                catch (Exception ex) {
                    // Log.e("firebaseDb", ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebaseDb", "Failed to read value.", error.toException());
            }
        });
        return allDoctors;
    }
    public ArrayList<Patient> getPatients()
    {
        dbReferencePatients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    allPatients.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.w("firebaseDb", "Came to read values"+ ds.getChildrenCount());
                        Patient patient = ds.getValue(Patient.class);
                        Log.w("firebaseDb", "Patients : "+ patient.name);
                        allPatients.add(patient);
                    }
                    observer.updatePatient(allPatients);
                }
                catch (Exception ex) {
                    // Log.e("firebaseDb", ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebaseDb", "Failed to read value.", error.toException());
            }
        });
        return allPatients;
    }

    public ArrayList<Appointment> getAppointmentsOfDoctor(String doc_email)
    {
        dbReferenceAppointments.orderByChild("doctor_email").equalTo(doc_email).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists() ){

                    try {
                        allAppointment.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.w("firebaseDb", "Came to read values"+ ds.getChildrenCount());
                            Patient patient = ds.getValue(Patient.class);
                            allPatients.add(patient);
                        }
                        observer.updateAppointments(allAppointment);
                    }
                    catch (Exception ex) {
                        // Log.e("firebaseDb", ex.getMessage());
                        ex.printStackTrace();
                    }

                }
                else{
                    flag=false;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allAppointment;
    }
    public ArrayList<Appointment> getAppointmentsOfPatient(String pat_email)
    {
        dbReferenceAppointments.orderByChild("patient_email").equalTo(pat_email).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                if(dataSnapshot.exists() ){

                    try {
                        allAppointment.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.w("Appointments", "Came to read values "+ ds.getChildrenCount());
                            Appointment appointment = ds.getValue(Appointment.class);
                            allAppointment.add(appointment);
                        }
                        Log.w("Appointments", "Came to read values "+ allAppointment.size());
                        observer.updateAppointments(allAppointment);
                    }
                    catch (Exception ex) {
                        // Log.e("firebaseDb", ex.getMessage());
                        ex.printStackTrace();
                    }

                }
                else{
                    flag=false;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allAppointment;
    }

    public ArrayList<Appointment> getAppointments()
    {
        dbReferenceAppointments.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                try {
                    allAppointment.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Appointment appointment = ds.getValue(Appointment.class);
                        allAppointment.add(appointment);
                    }
                    // Log.w("Appointments", "Came to read values "+ allAppointment.size());
                    observer.updateAppointments(allAppointment);
                }
                catch (Exception ex) {
                    // Log.e("firebaseDb", ex.getMessage());
                    ex.printStackTrace();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return allAppointment;
    }
    public ArrayList<Doctor> getAllAvailabilities()
    {
        dbAvailability.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                try {
                    availabilities.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.w("Availability", "Changed");
                        Doctor doctor = ds.getValue(Doctor.class);
                        availabilities.add(doctor);
                    }
                    observer.updateAvailabilities(availabilities);
                }
                catch (Exception ex) {
                    // Log.e("firebaseDb", ex.getMessage());
                    ex.printStackTrace();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return availabilities;
    }
    public boolean addDoctorAvailability(Doctor doctor)
    {
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_day").setValue(doctor.available_day);
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_start_time").setValue(doctor.available_start_time);
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_end_time").setValue(doctor.available_end_time);

        dbAvailability.push().setValue(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    flag = true;
                }
            }
        });
        return flag;
    }
    public boolean updateDoctorAvailability(String email,String date)
    {
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_day").setValue(doctor.available_day);
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_start_time").setValue(doctor.available_start_time);
//        dbReferenceDoctors.child("Doctors").child(doctor.email).child("available_end_time").setValue(doctor.available_end_time);
        dbAvailability.orderByChild("email").equalTo(email).orderByChild("available_day").equalTo(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot ds:snapshot.getChildren()
                    ) {
                        Doctor available = ds.getValue(Doctor.class);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return flag;
    }

    public boolean removeAvailability(Doctor doctor)
    {
        Log.w("REMOVING","remove "+doctor.email);
        dbAvailability.orderByChild("email").equalTo(doctor.email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                        Doctor doctor1 = appleSnapshot.getValue(Doctor.class);
                        if(doctor1.available_day.compareTo(doctor.available_day)==0 && doctor1.available_start_time.compareTo(doctor.available_start_time)==0 &&
                                doctor1.available_end_time.compareTo(doctor.available_end_time)==0)
                        {
                            appleSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    flag = true;
                                    Log.w("REMOVING","DONE");
                                    //observer.updateAvailabilities(availabilities);
                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                flag = false;
            }
        });
        return flag;
    }
}
