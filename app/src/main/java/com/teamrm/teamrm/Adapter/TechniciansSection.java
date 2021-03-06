package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by r00t on 01/01/2017.
 */

public class TechniciansSection extends StatelessSection {

    Context tContext;
    RecyclerView scrollContainer;

    public TechniciansSection(Context context, RecyclerView _scrollContainer){
        super(R.layout.technician_enrollment_section_header, R.layout.technician_expand_card);
        this.tContext = context;
        this.scrollContainer = _scrollContainer;
    }

    @Override
    public int getContentItemsTotal() {
        return Technician.getTechnicianList().size();
    }



    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new TechnicianViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TechnicianViewHolder techHolder = (TechnicianViewHolder) holder;
        Technician technician = Technician.getTechnicianList().get(position);

        techHolder.technicianNameRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                techHolder.expandableLayout.toggle();
                rotateButtonOnToggle(techHolder.expandBtn);
                /*if (techHolder.expandableLayout.isExpanded()){
                    scrollToView(position);
                }*/
            }
        });

        techHolder.userName.setText(technician.getUserNameString() == null ? "" : technician.getUserNameString());
        techHolder.userEmail.setText(technician.getUserEmail() == null ? "" : technician.getUserEmail());
        techHolder.userPhone.setText(technician.getUserPhone() == null ? "" : technician.getUserPhone());
        techHolder.techAssignedRegions.setText(technician.getTechAssignedRegions() == null ? "" : technician.getTechAssignedRegions());
        techHolder.techShifts.setText(technician.getTechAssignedShifts() == null ? "" : technician.getTechAssignedShifts());
        techHolder.techColorView.setCardBackgroundColor(technician.getTechColor().length()<3 ? Color.RED : Color.parseColor(technician.getTechColor()));
        techHolder.userAddress.setText(technician.getUserAddress() == null ? "" : technician.getUserAddress());
    }

   /* public void onBindViewHolder(TechniciansSection.TechnicianViewHolder holder, int position) {
        int _position;
        _position = position;

        if (_position == techniciansList.size()-1){
            CardView.MarginLayoutParams mlp = (CardView.MarginLayoutParams)holder.cardContainer.getLayoutParams();
            mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, convertDpToPx(120)); //add 120dp margin after last card
        }
    }*/

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder headerHolder) {
        TechnicianHeaderHolder technicianHeaderHolder = (TechnicianHeaderHolder) headerHolder;
        technicianHeaderHolder.headerString.setText("רשימת טכנאים");

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new TechnicianHeaderHolder(view);
    }

    class TechnicianHeaderHolder extends RecyclerView.ViewHolder{
        TextView headerString;
        TechnicianHeaderHolder(View view){
            super(view);
            headerString = (TextView) view.findViewById(R.id.headerTitle);
        }
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder {

        private ExpandableLayout expandableLayout;

        private ImageView expandBtn;

        private View technicianNameRow;

        protected View view;
        //protected String userID, calendarID;
        protected TextView userName, userEmail, userPhone, techShifts, techAssignedRegions, userAddress;
        protected CardView techColorView;
        protected CardView cardContainer;

        public TechnicianViewHolder(View view){
            super(view);



            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandableLayout);
            expandBtn = (ImageView) view.findViewById(R.id.expandButton);

            this.technicianNameRow = view.findViewById(R.id.technicianNameRow);

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

    void rotateButtonOnToggle(ImageView expandBtn) {
        expandBtn.animate().rotation(expandBtn.getRotation() - 180f).setInterpolator(new LinearInterpolator()).start();
    }

    private void scrollToView(final int position) {

        //Log.d(":::Focusing", v.toString());
        scrollContainer.post(new Runnable() {
            @Override
            public void run() {
                Log.d(":::TechSection", "Smooth scrolling to " + position);
                scrollContainer.getLayoutManager().smoothScrollToPosition(scrollContainer, null, position);
            }
        });
    }
}
