package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;

import java.util.ArrayList;

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
        int _position;
        _position = position;
        Technician technician = techniciansList.get(position);
        holder.userName.setText(technician.getUserNameString());
        holder.userEmail.setText(technician.getUserEmail());
        holder.userPhone.setText(technician.getUserPhone());
        holder.techAssignedRegions.setText(technician.getTechAssignedRegions());
        holder.techShifts.setText(technician.getTechAssignedShifts());
        holder.techColorView.setCardBackgroundColor(Color.parseColor(technician.getTechColor()));
        holder.userAddress.setText(technician.getUserAddress());
        if (_position == techniciansList.size()-1){
            CardView.MarginLayoutParams mlp = (CardView.MarginLayoutParams)holder.cardContainer.getLayoutParams();
            mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, convertDpToPx(120)); //add 120dp margin after last card
        }
    }

    @Override
    public int getItemCount() {
        return (null != techniciansList ? techniciansList.size() : 0);
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder {

        protected View view;
        //protected String userID, calendarID;
        protected TextView userName, userEmail, userPhone, techShifts, techAssignedRegions, userAddress;
        protected CardView techColorView;
        protected CardView cardContainer;

        public TechnicianViewHolder(View view){
            super(view);
            this.userName = (TextView)view.findViewById(R.id.technicianNameText);
            this.userEmail = (TextView)view.findViewById(R.id.technicianMailText);
            this.userPhone = (TextView)view.findViewById(R.id.technicianPhoneText);
            this.techShifts = (TextView)view.findViewById(R.id.technicianShiftsText);
            this.techAssignedRegions = (TextView)view.findViewById(R.id.technicianAssignedRegionsText);
            this.userAddress = (TextView)view.findViewById(R.id.technicianAddressText);
            this.techColorView = (CardView)view.findViewById(R.id.technicianColorView);
            this.cardContainer = (CardView)view.findViewById(R.id.technicianCard);
        }
    }

    private int convertDpToPx(int dp) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
        return Math.round(pixels);
    }
}
