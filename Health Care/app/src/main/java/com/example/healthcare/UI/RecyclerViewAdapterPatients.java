package com.example.healthcare.UI;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.Models.BookedAppointments;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.UI.R;

import java.util.ArrayList;

import android.net.Uri;

public class RecyclerViewAdapterPatients extends RecyclerView.Adapter<RecyclerViewAdapterPatients.ViewHolder>
{

    private ArrayList<Patient> data;
    private Context context;
    public String location;
    private ArrayList<BookedAppointments> appointments;
    private String user_email;
    DataBackService dataBackService = GetBL.getBL();
    public RecyclerViewAdapterPatients(Context context, ArrayList<Patient> data,String user_email)
    {
        this.context = context;
        this.data = new ArrayList<Patient>();
        this.data = data;
        this.user_email = user_email;
        this.appointments = new ArrayList<>();
        this.appointments = dataBackService.getBookedAppointments(user_email);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_appointment_patient, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(appointments.get(position).patient.name);
        String str = Integer.toString(appointments.get(position).patient.age);
        holder.age.setText(new StringBuilder().append("Age: ").append(str).toString());

        holder.data.setText(String.format("Date : %s", appointments.get(position).appointment.date));
        holder.time.setText(String.format("Timings : %s - %s", appointments.get(position).appointment.start_time, appointments.get(position).appointment.end_time));

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.setData(Uri.parse("tel:" + appointments.get(position).patient.phone));
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, age ,data , time;
        ImageView call;
        RelativeLayout parent_layout;
        public ViewHolder(View itemView)
        {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.recycleview_appointment_patient_item_layout);
            name = itemView.findViewById(R.id.recycleview_appointment_patient_name);
            age = itemView.findViewById(R.id.recycleview_appointment_patient_age);
            call = itemView.findViewById(R.id.recycleview_appointment_patient_phone_no);
            data = itemView.findViewById(R.id.recycleview_appointment_patient_date);
            time = itemView.findViewById(R.id.recycleview_appointment_patient_time);
        }


    }

    public void dataRefresher(ArrayList<Patient> patients)
    {
        this.data.clear();
        this.data.addAll(patients);
        this.appointments = dataBackService.getBookedAppointments(user_email);
        this.notifyDataSetChanged();
    }

    public void wew(String str)
    {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

}

