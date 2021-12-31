package com.example.healthcare.UI;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.healthcare.BL.DataBackService;
import com.example.healthcare.Models.Appointment;
import com.example.healthcare.Models.Doctor;
import com.example.healthcare.Models.Observer;
import com.example.healthcare.Models.Patient;
import com.example.healthcare.Models.User;
import com.example.healthcare.UI.R;

import java.util.ArrayList;

public class RecyclerViewAdapterDoctor extends RecyclerView.Adapter<RecyclerViewAdapterDoctor.ViewHolder> implements Filterable {

    private ArrayList<Doctor> data;
    private ArrayList<Doctor> filteredList;
    private Context context;
    public String location;

    private String user_email;
    Boolean adding = false;
    DataBackService dataBackService = new DataBackService(new Observer() {
        @Override
        public void updatePatient(ArrayList<Patient> patients) {

        }

        @Override
        public void updateDoctors(ArrayList<Doctor> doctors) {
            Log.w("IN THE ADAPTER","dnsnf doc update");
            dataRefresher(dataBackService.allDoctorData());
        }

        @Override
        public void updateAppointments(ArrayList<Appointment> appointments) {

        }

        @Override
        public void updateAvailabilities(ArrayList<Doctor> availabilities) {
            Log.w("IN THE ADAPTER ","dnsnf");
            dataRefresher(dataBackService.allDoctorData());
        }

        @Override
        public void updateAdmin(ArrayList<User> admins) {

        }
    });

    AppointmentBookedListner listner;

    public RecyclerViewAdapterDoctor(Context context, ArrayList<Doctor> data, String email, Boolean adding, AppointmentBookedListner listner)
    {
        user_email = email;
        this.context = context;
        this.data = new ArrayList<Doctor>();
        this.data = data;
        this.filteredList = new ArrayList<Doctor>(data);
        this.adding = adding;

        this.listner = listner;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_appointment_doctor, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(filteredList.get(position).name);
        holder.start_time.setText(filteredList.get(position).available_start_time);
        holder.speciality.setText(filteredList.get(position).speciality);
        holder.date.setText(filteredList.get(position).available_day);
        holder.end_time.setText(filteredList.get(position).available_end_time);
        holder.city.setText(filteredList.get(position).city);

        if(!adding)
        {
            holder.add.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Doctor doctor = filteredList.get(position);
                    Appointment appointment = new Appointment();
                    appointment.doctor_email =doctor.email;
                    appointment.patient_email=user_email;
                    appointment.date = doctor.available_day;
                    appointment.start_time = doctor.available_start_time;
                    appointment.end_time = doctor.available_end_time;

                    dataBackService.addAppointment(appointment);
                    Log.w("Appoint","Yes");
                    dataBackService.removeAvailability(doctor);
                    //dataRefresher(dataBackService.allDoctorData());

                    if(listner!=null)
                    {
                        listner.update();
                    }
                    //dataBackService.removeAvailability(filteredList.remove(position));
//                    if()
//                    {
//
//                        Toast.makeText(context, "Appointment Added",Toast.LENGTH_SHORT);
//                    }
//                    else
//                    {
//                        Toast.makeText(context, "Could not book appointment",Toast.LENGTH_SHORT);
//                    }
                    //dataRefresher(dataBackService.allDoctorData());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return search_filter;
    }
    private final Filter search_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Doctor> filter_list = new ArrayList<>();

            if (charSequence.length() > 0)
            {
                for(int i=0;i<data.size();i++)
                {
                    if(data.get(i).city.toLowerCase().contains(charSequence.toString().toLowerCase())
                            ||data.get(i).available_day.toLowerCase().contains(charSequence.toString().toLowerCase())
                            ||data.get(i).speciality.toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filter_list.add(data.get(i));
                    }
                }
            }
            else if(charSequence.toString().equals(""))
            {
                filter_list=data;
            }

            FilterResults results = new FilterResults();
            results.count = filter_list.size();
            results.values = filter_list;

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filteredList = (ArrayList<Doctor>) filterResults.values;
            notifyDataSetChanged();
        }

    };


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView start_time,end_time;
        TextView speciality;
        TextView date;
        TextView city,phone;
        ImageView add;
        RelativeLayout parent_layout;
        public ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.recycleview_appointment_doctor_name);
            start_time = itemView.findViewById(R.id.recycleview_appointment_doctor_starttime);
            end_time = itemView.findViewById(R.id.recycleview_appointment_doctor_endtime);
            city= itemView.findViewById(R.id.recycleview_appointment_doctor_city);
            speciality = itemView.findViewById(R.id.recycleview_appointment_doctor_speciality);
            date = itemView.findViewById(R.id.recycleview_appointment_doctor_date);
            add = itemView.findViewById(R.id.recycleview_appointment_doctor_add);

            parent_layout = itemView.findViewById(R.id.recycleview_appointment_doctor_item_layout);
        }


    }

    public void dataRefresher(ArrayList<Doctor> doctors)
    {
        this.data.clear();
        this.data.addAll(doctors);
        this.filteredList.clear();
        this.filteredList.addAll(doctors);
        this.notifyDataSetChanged();
    }

    public void wew(String str)
    {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public interface AppointmentBookedListner{
        void update();
    }
}

