package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.sql.Time;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener, FireBaseAble {

    CardView userDetailCard, approval, cancel;
    RelativeLayout userDetailOpen;
    RelativeLayout ticketDetailClose;
    RelativeLayout ticketDetailOpen;
    TextView userName, userProfile, txtCancel,endTimeTxt;
    Ticket ticket;
    String ticketID;
    static String endTime;
    
    
    public TicketView() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            if(bundle.getString("ticketID", "error")!="error") {
                String ticketId = bundle.getString("ticketID", "error");
                Log.w("TICKET_ID:  ", ticketId);
                this.ticketID = ticketId;
            }
            else if (bundle.getString("time", "error")!="error")
            {
                endTime = bundle.getString("time", "error");
            }
            if(endTimeTxt!=null&&ticketID!=null)
            {
                UtlFirebase.getTicketByKey(ticketID,this);
            }
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

        getActivity().setTitle(getContext().getResources().getString(R.string.new_ticket));
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
        ((HomeScreen)getActivity()).btnaddTicketGon();


        ((TextView)view.findViewById(R.id.statusTxt)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView)view.findViewById(R.id.ticketNum)).setTypeface(SEMI_BOLD);

        if(ticketID!=null)

      // txtCancel.setText(MainActivity.userStatus.equals("User")?"בטל":"דחה");
        if (endTime!=null)
            endTimeTxt.setText(endTime);
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
        else if(view.getId()==cancel.getId())
        {
            UtlFirebase.changeState(ticketID,
                    MainActivity.userStatus.equals("User")?ProductID.STATE_E00:ProductID.STATE_E01);
        }
        else if(view.getId()==approval.getId())
        {
            //UtlFirebase.stateListener("Admin",MainActivity.email,"NULL");
        }
        else if(view.getId()==endTimeTxt.getId())
        {
            ((HomeScreen)getActivity()).onDrawerItemSelected(view,2);
            ((HomeScreen)getActivity()).setTitle("יומן");
        }
    }
    private void setListeners(View view)
    {
        userDetailCard = (CardView)view.findViewById(R.id.userDetailCard);
        userDetailOpen = (RelativeLayout) view.findViewById(R.id.name);
        ticketDetailClose = (RelativeLayout)view.findViewById(R.id.ticketHeading);
        ticketDetailOpen = (RelativeLayout) view.findViewById(R.id.ticketHeadingExpand);
        userName = (TextView)view.findViewById(R.id.userNameCard);
        userProfile = (TextView)view.findViewById(R.id.userProfile);
        approval = (CardView)view.findViewById(R.id.ok);
        cancel = (CardView)view.findViewById(R.id.cancel);
        txtCancel = (TextView)view.findViewById(R.id.txtCancel);
        endTimeTxt = (TextView)view.findViewById(R.id.dateTimeChange);
        endTimeTxt.setOnClickListener(this);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        ticketDetailClose.setOnClickListener(this);
        ticketDetailOpen.setOnClickListener(this);
        userProfile.setOnClickListener(this);
        approval.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket=ticket;
        userName.setText(ticket.customerName);

        if(ticket.state != ProductID.STATE_A03)
        {
        UtlFirebase.changeState(ticket.ticketId, ProductID.STATE_A03);
        ticket.state = ProductID.STATE_A03;
        }
    }

    @Override
    public void resultUser(Users user) {

    }

    @Override
    public void resultList(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }
}

