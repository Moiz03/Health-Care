
package com.example.healthcare.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.DL.DataService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;
import com.example.healthcare.UI.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAvailability extends AppCompatActivity implements
        View.OnClickListener {

    Button add;
    EditText txtDate, txtTimeStart , txtTimeEnd, email;
    private int mYear, mMonth, mDay, startHour, startMinute , endHour,endMinute;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_availability);

        txtDate=(EditText)findViewById(R.id.date_availability_doctor);
        txtTimeStart =(EditText)findViewById(R.id.time_st_availability_doctor);
        txtTimeEnd = (EditText)findViewById(R.id.time_end_add_availability_doctor);

        txtDate.setOnClickListener(this);
        txtTimeStart.setOnClickListener(this);
        txtTimeEnd.setOnClickListener(this);

        add = findViewById(R.id.button_add_availability_doctor);
        add.setOnClickListener(this);
        email= findViewById(R.id.email_availability_doctor);
    }

    @Override
    public void onClick(View v) {

        if (v == txtDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == txtTimeStart) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            startHour = c.get(Calendar.HOUR_OF_DAY);
            startMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeStart.setText(hourOfDay + ":" + minute);
                        }
                    }, startHour, startMinute, false);
            timePickerDialog.show();
        }
        if (v == txtTimeEnd) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            endHour = c.get(Calendar.HOUR_OF_DAY);
            endMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimeEnd.setText(hourOfDay + ":" + minute);
                        }
                    }, endHour, endMinute, false);
            timePickerDialog.show();
        }


        if(v == add)
        {
            if(email.length() > 0)
            {
                Doctor doctor = new Doctor();
                doctor.email = email.getText().toString();
                doctor.available_day = txtDate.getText().toString();
                doctor.available_start_time = txtTimeStart.getText().toString();
                doctor.available_end_time = txtTimeEnd.getText().toString();
                Log.w("DL_upd","UI");
                dataService.addDoctorAvailability(doctor);
            }

        }

    }
}
