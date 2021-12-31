package com.example.healthcare.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.UI.R;

import java.util.Calendar;

public class BookAppointment extends AppCompatActivity implements View.OnClickListener{

    String email;
    Spinner speciality, city;
    Fragment_Search available_doctors;
    TextView txtDate;
    private int mYear, mMonth, mDay;
    AdapterView.OnItemSelectedListener listener;
    ImageView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        available_doctors = new Fragment_Search();
        available_doctors.setEmail(email);

        getSupportFragmentManager().beginTransaction().replace(R.id.searched_doctors,available_doctors).commit();

        txtDate = findViewById(R.id.date_select);
        txtDate.setOnClickListener(this);
        calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        speciality = findViewById(R.id.doctor_speciality);

        city = findViewById(R.id.doctor_city);

        setListeners();;

    }

    private void setListeners()
    {
        listener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i > 0)
                {
                    available_doctors.searchData(adapterView.getItemAtPosition(i).toString());

                }
                else{
                    available_doctors.searchData("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        speciality.setOnItemSelectedListener(listener);
        city.setOnItemSelectedListener(listener);

        txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                available_doctors.searchData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void onClick(View view) {
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
}