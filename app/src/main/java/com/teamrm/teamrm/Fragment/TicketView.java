package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener, FireBaseAble {

    CardView userDetailCard;
    RelativeLayout userDetailOpen;
    RelativeLayout ticketDetailClose;
    RelativeLayout ticketDetailOpen;
    TextView userName, userProfile;
    Ticket ticket;
    String ticketID;
    
    
    public TicketView() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            String ticketId = bundle.getString("ticketID", "error");
            Log.w("TICKET_ID: ",ticketId);
            this.ticketID = ticketId;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_ticket, container, false);
        setListeners(view);
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        ((TextView)view.findViewById(R.id.statusTxt)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.ticketNum)).setTypeface(SEMI_BOLD);
        userName = (TextView)view.findViewById(R.id.userNameCard);
        userProfile = (TextView)view.findViewById(R.id.userProfile);

        userProfile.setOnClickListener(this);
        UtlFirebase.getTicketByKey(ticketID,this);

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
        else if(view.getId()== ticketDetailClose.getId())
        {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.VISIBLE);
        }
        else if(view.getId()== ticketDetailOpen.getId())
        {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.GONE);
        }
        else if(view.getId()==userProfile.getId())
        {
            Toast.makeText(getContext(), "USER PROFILE "+ticket.customerName, Toast.LENGTH_SHORT).show();
        }
    }
    private void setListeners(View view)
    {
        userDetailCard = (CardView)view.findViewById(R.id.userDetailCard);
        userDetailOpen = (RelativeLayout) view.findViewById(R.id.name);
        ticketDetailClose = (RelativeLayout)view.findViewById(R.id.ticketHeading);
        ticketDetailOpen = (RelativeLayout) view.findViewById(R.id.ticketHeadingExpand);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        ticketDetailClose.setOnClickListener(this);
        ticketDetailOpen.setOnClickListener(this);
    }

    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket=ticket;
        userName.setText(ticket.customerName);
    }

    @Override
    public void resultList(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }
}

