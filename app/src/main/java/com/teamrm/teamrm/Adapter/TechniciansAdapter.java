package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

/**
 * Created by r00t on 01/01/2017.
 */

public class TechniciansAdapter extends RecyclerView.Adapter<TechniciansAdapter.TechnicianViewHolder> {

    Context tContext;
    ArrayList<Technician> techniciansList = new ArrayList<>();

    public TechniciansAdapter(Context context, ArrayList<Technician> techniciansList){
        this.tContext = context;
        this.techniciansList = techniciansList;
    }

    @Override
    public TechniciansAdapter.TechnicianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.technician_expand_card, null);
        TechniciansAdapter.TechnicianViewHolder tHolder = new TechniciansAdapter.TechnicianViewHolder(view);
        return tHolder;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //TODO:add more types
        return viewType;
    }


    public void onBindViewHolder(TechniciansAdapter.TechnicianViewHolder holder, int position) {
        Technician technician = techniciansList.get(position);
        holder.userName.setText(technician.getUserNameString());
        holder.userEmail.setText(technician.getUserEmail());
        holder.userPhone.setText(technician.getUserPhone());
        holder.techCalendarName.setText(technician.getTechCalendarID());
        holder.techAssignedRegions.setText(technician.getTechAssignedRegions());
        holder.techShifts.setText(technician.getTechShifts());
        holder.techColorView.setCardBackgroundColor(Color.parseColor(technician.getTechColor()));
        holder.userAddress.setText(technician.getUserAddress());
    }

    @Override
    public int getItemCount() {
        return (null != techniciansList ? techniciansList.size() : 0);
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder {

        protected View view;
        //protected String userID, calendarID;
        protected TextView userName, userEmail, userPhone, techCalendarName, techShifts, techAssignedRegions, userAddress;
        protected CardView techColorView;

        public TechnicianViewHolder(View view){
            super(view);
            this.userName = (TextView)view.findViewById(R.id.technicianNameText);
            this.userEmail = (TextView)view.findViewById(R.id.technicianMailText);
            this.userPhone = (TextView)view.findViewById(R.id.technicianPhoneText);
            this.techCalendarName = (TextView)view.findViewById(R.id.technicianCalendarText);
            this.techShifts = (TextView)view.findViewById(R.id.technicianShiftsText);
            this.techAssignedRegions = (TextView)view.findViewById(R.id.technicianAssignedRegionsText);
            this.userAddress = (TextView)view.findViewById(R.id.technicianAddressText);
            this.techColorView = (CardView)view.findViewById(R.id.technicianColorView);
        }
    }

}
