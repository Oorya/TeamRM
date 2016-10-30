package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener {

    CardView userDetailCard;
    RelativeLayout userDetailOpen;
    RelativeLayout tiketDetailclose;
    RelativeLayout tiketDetailOpen;
    
    
    public TicketView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_tiket, container, false);
        setListeners(view);
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        ((TextView)view.findViewById(R.id.statusTxt)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.ticketNum)).setTypeface(SEMI_BOLD);
        
        return view;   
    }


    @Override
    public void onClick(View view) {
       // Toast.makeText(this.getContext(), view.getId()+"", Toast.LENGTH_SHORT).show();
        
        if (view.getId()==userDetailCard.getId())
        {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.VISIBLE);
        }
        else if(view.getId()==userDetailOpen.getId()) 
        {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.GONE);
        }
        else if(view.getId()==tiketDetailclose.getId())
        {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.VISIBLE);
        }
        else if(view.getId()==tiketDetailOpen.getId())
        {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.GONE);
        }
    }
    private void setListeners(View view)
    {
        userDetailCard = (CardView)view.findViewById(R.id.userDetailCard);
        userDetailOpen = (RelativeLayout) view.findViewById(R.id.name);
        tiketDetailclose = (RelativeLayout)view.findViewById(R.id.line1);
        tiketDetailOpen = (RelativeLayout) view.findViewById(R.id.line1open);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        tiketDetailclose.setOnClickListener(this);
        tiketDetailOpen.setOnClickListener(this);
    }
}

