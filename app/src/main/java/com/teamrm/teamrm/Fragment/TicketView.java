package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener, FireBaseAble {

    CardView userDetailCard, approval, cancel, btnProfile;
    RelativeLayout userDetailOpen;
    RelativeLayout ticketDetailClose;
    RelativeLayout ticketDetailOpen;
    TextView userName, userProfile, txtCancel, endTimeTxt, txtProduct, txtCategory, txtRegion,
        txtProductEx, txtCategoryEx, txtRegionEx, txtAddressEx, txtPhoneEx, descriptionShort, descriptionLong,
        userNameEx, mailUserEx, regionUserEx, addressUserEx, phoneUserEx;
    ImageView img1, img2;
    private Ticket ticket;
    static  String ticketID, timeFormated;
    static Long bundleEndTime;
    Calendar endTime;
    private Users userProfileObj ;
    Fragment stateActionButtons;
    UtlAlarmManager utlAlarmManager;
    Button btnRight;

    public TicketView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toast.makeText(getContext(), UserSingleton.getInstance().getUserEmail(), Toast.LENGTH_SHORT).show();
        userProfileObj = UserSingleton.getInstance();
        Bundle bundle = this.getArguments();
        utlAlarmManager = new UtlAlarmManager(getContext());
        if (bundle != null) {
            if (!bundle.getString("ticketID", "error").equals("error")) {
                String ticketId = bundle.getString("ticketID", "error");
                Log.w("TICKET_ID Bundle:  ", ticketId);
                ticketID = ticketId;


                if (bundle.getLong("time", 0) != 0) {
                    Log.w("TICKET_ID Bundle:  ", bundle.getLong("time", 0)+"");

                    bundleEndTime = bundle.getLong("time", 0);
                    endTime = Calendar.getInstance();
                    endTime.setTime(new Date(bundleEndTime));
                    SimpleDateFormat format1;
                    if(endTime.get(Calendar.HOUR_OF_DAY)>0)
                        format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    else
                        format1 = new SimpleDateFormat("dd-MM-yyyy");
                    timeFormated = format1.format(endTime.getTime());
                    Log.w(" Bundle_timeFormated:  ", timeFormated);

                    UtlFirebase.updateTicket(ticketID, "endTime", endTime.getTime());
                }
            }

        }

        /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.btnFragment, new StateActionButtons(), null);
        ft.disallowAddToBackStack();
        ft.commit();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        stateActionButtons = new StateActionButtons();
        setListeners(view);
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        getActivity().setTitle(getContext().getResources().getString(R.string.ticket_new));
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
        ((HomeScreen) getActivity()).btnaddTicketGon();


        ((TextView) view.findViewById(R.id.statusTxt)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.ticketNum)).setTypeface(SEMI_BOLD);
        UtlFirebase.getTicketByKey(ticketID, this);
        initUserCard();
        return view;
    }

    private void initUserCard() {
        userNameEx.setText(userProfileObj.getUserNameString());
        mailUserEx.setText(userProfileObj.getUserEmail());
        regionUserEx.setText(userProfileObj.getUserRegion());
        addressUserEx.setText(userProfileObj.getUserAddress());
        phoneUserEx.setText(userProfileObj.getUserPhone());
    }

    @Override
    public void onClick(View view) {
        // Toast.makeText(this.getContext(), view.getId()+"", Toast.LENGTH_SHORT).show();

        if (view.getId() == userDetailCard.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.VISIBLE);
        } else if (view.getId() == userDetailOpen.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.GONE);
        } else if (view.getId() == ticketDetailClose.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.VISIBLE);
        } else if (view.getId() == ticketDetailOpen.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.GONE);
        } else if (view.getId() == userProfile.getId()) {
            Toast.makeText(getContext(), "USER PROFILE " + ticket.clientName, Toast.LENGTH_SHORT).show();


        } else if (view.getId() == btnProfile.getId())
        {
            Toast.makeText(getContext(), UserSingleton.getInstance().getUserNameString(), Toast.LENGTH_SHORT).show();
        }else if (view.getId() == approval.getId()) {

            Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            switch (ticket.state)
            {
                case ProductID.STATE_A00: {
                    ticket.changeState(ProductID.STATE_A01,ticket);
                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_A01:{
                    ticket.changeState(ProductID.STATE_B01,ticket);
                    UtlFirebase.changeStatus(ticket.ticketId,2);
                    //ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_A02CN:
                {
                    if(timeFormated!=null)
                    {
                        if(!(ticket.repeatSendCounter>3)) {
                            ticket.incCounter();
                            ticket.changeState(ProductID.STATE_A03,ticket);
                            ticket.getStateObj().setView(this.getView());
                            break;
                        }else
                        {
                            ticket.changeState(ProductID.STATE_E02,ticket);
                            ticket.incInitialization();
                            break;
                        }
                    }
                }
                case ProductID.STATE_A03:
                {
                        ticket.changeState(ProductID.STATE_B01,ticket);
                        ticket.getStateObj().setView(this.getView());
                        break;
                }
                case ProductID.STATE_B01:
                {
                    ticket.changeState(ProductID.STATE_B02,ticket);
                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_B03:
                {
                    if(ticket.getTicketDone()) {
                        ticket.changeState(ProductID.STATE_C01,ticket);
                        ticket.getStateObj().setView(this.getView());
                        break;
                    }else
                    {
                        ticket.changeState(ProductID.STATE_E06,ticket);
                        ticket.getStateObj().setView(this.getView());
                        break;
                    }
                }
                case ProductID.STATE_C01:
                {
                        ticket.changeState(ProductID.STATE_C02,ticket);
                        ticket.getStateObj().setView(this.getView());
                        break;
                }
                case ProductID.STATE_C02:
                {
                        ticket.changeState(ProductID.STATE_Z00,ticket);
                        break;
                }
                case ProductID.STATE_E03:
                {
                    ticket.changeState(ProductID.STATE_E02,ticket);
                    break;
                }
            }
        } else if (view.getId() == endTimeTxt.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("ticketID",ticketID);

            FragmentTransaction fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                    .beginTransaction();
            CalendarView calendarView = new CalendarView();
            calendarView.setArguments(bundle);
            fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentManager.replace(R.id.container_body,  calendarView).addToBackStack("NEW_TICKET").commit();
            ((HomeScreen) getActivity()).setTitle("יומן");
        }
        else if(view.getId() == cancel.getId())
        {
            switch (ticket.state)
            {
                case ProductID.STATE_A00: {
                    ticket.changeState(ProductID.STATE_E00,ticket);
                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_A01:{
                    ticket.changeState(ProductID.STATE_E01,ticket);
                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_A03:
                {
                    ticket.changeState(ProductID.STATE_E02,ticket);
                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_E03:
                {
                    ticket.changeState(ProductID.STATE_E04,ticket);
                    ticket.endTime.setTime(ticket.endTime.getTime()+14000);
                    ticket.setAlarm(utlAlarmManager.setAlarm(ticket.endTime,TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION,ticketID));

                    ticket.getStateObj().setView(this.getView());
                    break;
                }
                case ProductID.STATE_C01:
                {
                        ticket.changeState(ProductID.STATE_E07,ticket);
                        ticket.getStateObj().setView(this.getView());
                        break;
                }
                case ProductID.STATE_C02:
                {
                    ticket.changeState(ProductID.STATE_Z00,ticket);
                    break;
                }
            }
        }
    }

    private void setListeners(View view) {
        userDetailCard = (CardView) view.findViewById(R.id.userDetailCard);
        userDetailOpen = (RelativeLayout) view.findViewById(R.id.name);
        ticketDetailClose = (RelativeLayout) view.findViewById(R.id.ticketHeading);
        ticketDetailOpen = (RelativeLayout) view.findViewById(R.id.ticketHeadingExpand);
        userName = (TextView) view.findViewById(R.id.userNameCard);
        userProfile = (TextView) view.findViewById(R.id.userProfileLabel);
        btnProfile = (CardView)view.findViewById(R.id.btnProfile); 
        approval = (CardView) view.findViewById(R.id.ok);
        cancel = (CardView) view.findViewById(R.id.cancel);
        txtCancel = (TextView) view.findViewById(R.id.txtCancel);
        endTimeTxt = (TextView) view.findViewById(R.id.dateTimeChange);
        img1 = (ImageView)view.findViewById(R.id.photo1);
        img2 = (ImageView)view.findViewById(R.id.photo2);
        txtProduct = (TextView)view.findViewById(R.id.productText);
        txtCategory = (TextView)view.findViewById(R.id.categoryText);
        txtRegion = (TextView)view.findViewById(R.id.regionText);
        txtProductEx = (TextView)view.findViewById(R.id.productTextEx);
        txtCategoryEx = (TextView)view.findViewById(R.id.categoryTextEx);
        txtRegionEx = (TextView)view.findViewById(R.id.regionTextEx);
        txtAddressEx  = (TextView)view.findViewById(R.id.addressTextEx);
        txtPhoneEx  = (TextView)view.findViewById(R.id.phoneTextEx);
        descriptionShort  = (TextView)view.findViewById(R.id.descriptionShortEx);
        descriptionLong = (TextView)view.findViewById(R.id.descriptionLongEx);
        userNameEx = (TextView)view.findViewById(R.id.userNameCard);
        mailUserEx = (TextView)view.findViewById(R.id.mailAdd);
        regionUserEx = (TextView)view.findViewById(R.id.locationAdd);
        addressUserEx = (TextView)view.findViewById(R.id.locationText);
        phoneUserEx = (TextView)view.findViewById(R.id.phoneText);
        //btnRight = (Button)view.findViewById(R.id.btnRight);

        endTimeTxt.setOnClickListener(this);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        ticketDetailClose.setOnClickListener(this);
        ticketDetailOpen.setOnClickListener(this);
        userProfile.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        approval.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //btnRight.setOnClickListener(this);
    }

    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket = ticket;
        initializeTicket();
    }

    private void initializeTicket() {
        userName.setText(ticket.clientName);
        txtProduct.setText(ticket.product);
        txtCategory.setText(ticket.classification);
        txtRegion.setText(ticket.area);
        txtProductEx.setText(ticket.product);
        txtCategoryEx.setText(ticket.classification);
        txtRegionEx.setText(ticket.area);
        txtAddressEx.setText(ticket.address);
        txtPhoneEx.setText(ticket.phone);
        descriptionShort.setText(ticket.desShort);
        descriptionLong.setText(ticket.desLong);

        if(!ticket.ticketImage1.equals("error"))
        {
            img1.setImageBitmap(UtlImage.string2bitmap(ticket.ticketImage1));
        }
        if(!ticket.ticketImage2.equals("error"))
        {
            img2.setImageBitmap(UtlImage.string2bitmap(ticket.ticketImage2));
        }
        if(ticket.endTime!=null)
            endTimeTxt.setText(date2String(ticket.endTime));

    }

    private String date2String(Date date)
    {
        SimpleDateFormat format1;
        if(date.getHours()> 0)
            format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        else
            format1 = new SimpleDateFormat("dd-MM-yyyy");

        String timeFormated = format1.format(date);
        return timeFormated;
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

