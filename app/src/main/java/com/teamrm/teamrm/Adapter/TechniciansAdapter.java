package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.Type.Technician;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

/**
 * Created by r00t on 01/01/2017.
 */

public class TechniciansAdapter extends RecyclerView.Adapter<TechniciansAdapter.TechnicianViewHolder> {

    Context tContext;
    ArrayList<Technician> techniciansList = new ArrayList<>();

    public TechniciansAdapter(Context context){
        this.tContext = context;
    }

    @Override
    public TechniciansAdapter.TechnicianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //TODO:add more types
        return viewType;
    }

    public void onBindViewHolder(TechniciansAdapter.TechnicianViewHolder holder, int position) {
        Technician technician = techniciansList.get(position);
        holder.userName.setText(technician.getUserNameString());
        holder.email.setText(technician.getUserEmail());
        holder.phone.setText(technician.getUserPhone());
        holder.calendarName.setText(technician.getTechCalendarID());
        holder.color.setBackgroundColor(parseColor(technician.getTechColor()));
        holder.shifts.setText(technician.getTechShifts());

    }

    @Override
    public int getItemCount() {
        return (null != techniciansList ? techniciansList.size() : 0);
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder {

        protected View view;
        protected String userID, calendarID;
        protected TextView userName, email, phone, calendarName, shifts, address;
        protected View color;

        public TechnicianViewHolder(View view, int viewNum){
            super(view);

        }
    }

}
