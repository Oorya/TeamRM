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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener, FireBaseAble {

    public static final String FRAGMENT_TRANSACTION = "TicketView";
    CardView userDetailCard, approval, cancel, btnProfile;
    RelativeLayout userDetailOpen;
    RelativeLayout ticketDetailClose;
    RelativeLayout ticketDetailOpen;
    TextView userNameClose, userProfile,userNameCardOpen, txtCancel, dateTimeChange,userNameCardClose,dateTimeOpen,ticketNumber,ticketStatus
            ,productTicketDetailsCardClosed,categoryTicketDetailsCardClosed,regionTicketDetailsCardClosed
            ,categoryTicketDetailsCardOpen,regionTicketDetailsCardOpen,addressTicketDetailsCardOpen,phoneTicketDetailsCardOpen
            ,descriptionShortTicketDetailsCardOpen,descriptionLongTicketDetailsCardOpen,userMailCardOpen,userAddCardOpen,userPhoneCardOpen
            ,productTicketDetailsCardOpen;
    public static ImageView img1, img2;
    private Ticket ticket;
    static  String ticketID, timeFormated;
    static Long bundleEndTime;
    Calendar endTime;
    private Users userProfileObj ;
    Fragment stateActionButtons;
    UtlAlarmManager utlAlarmManager;

    public TicketView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       // Toast.makeText(getContext(), UserSingleton.getInstance().getUserEmail(), Toast.LENGTH_SHORT).show();
        userProfileObj = UserSingleton.getInstance();
        Bundle bundle = this.getArguments();
        utlAlarmManager = new UtlAlarmManager(getContext());
        if (bundle != null) {
            if (!bundle.getString("ticketID", "error").equals("error")) {
                String ticketId = bundle.getString("ticketID", "error");
                Log.w("TICKET_ID Bundle:  ", ticketId);
                ticketID = ticketId;


                if (bundle.getLong("ticketOpenDateTime", 0) != 0) {
                    Log.w("TICKET_ID Bundle:  ", bundle.getLong("ticketOpenDateTime", 0)+"");

                    bundleEndTime = bundle.getLong("ticketOpenDateTime", 0);
                    endTime = Calendar.getInstance();
                    endTime.setTime(new Date(bundleEndTime));
                    SimpleDateFormat format1;
                    if(endTime.get(Calendar.HOUR_OF_DAY)>0)
                        format1 = new SimpleDateFormat("HH:mm:ss - dd-MM-yyyy");
                    else
                        format1 = new SimpleDateFormat("dd-MM-yyyy");
                    timeFormated = format1.format(endTime.getTime());
                    Log.w(" Bundle_timeFormated:  ", timeFormated);
                    Map<String, String> updates = new HashMap<>();
                    updates.put("ticketCloseDateTime",timeFormated);
                    UtlFirebase.updateTicket(ticketID,(HashMap<String, String>) updates);
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        alternateRowColor(view, R.id.rowSet1);

        stateActionButtons = new StateActionButtons();
        setListeners(view);
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        getActivity().setTitle(getContext().getResources().getString(R.string.ticket_new));
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);


        ((TextView) view.findViewById(R.id.ticketStatus)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.ticketNumber)).setTypeface(SEMI_BOLD);
        UtlFirebase.getTicketByKey(ticketID, this);

        return view;
    }

    @Override
    public void onClick(View view) {
        // Toast.makeText(this.getContext(), view.getId()+"", Toast.LENGTH_SHORT).show();

        if (view.getId() == userDetailCard.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.VISIBLE);
            alternateRowColor(view, R.id.rowSet2);
        } else if (view.getId() == userDetailOpen.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.GONE);
        } else if (view.getId() == ticketDetailClose.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.VISIBLE);
            alternateRowColor(view, R.id.rowSet1);
        } else if (view.getId() == ticketDetailOpen.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.GONE);
            alternateRowColor(view, R.id.rowSet1);
        } else if (view.getId() == userProfile.getId()) {
            Toast.makeText(getContext(), "USER PROFILE " + ticket.getClientNameString(), Toast.LENGTH_SHORT).show();


        } else if (view.getId() == btnProfile.getId())
        {
            Toast.makeText(getContext(), UserSingleton.getInstance().getUserNameString(), Toast.LENGTH_SHORT).show();
        }else if (view.getId() == approval.getId()) {

            Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            switch (ticket.getTicketStateString())
            {
                case TicketStateStringable.STATE_A00: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_A01,ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01:{
                    ticket.updateTicketStateString(TicketStateStringable.STATE_B01,ticket);
                    //ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A02CN:
                {
                    if(timeFormated!=null)
                    {
                        if(!(ticket.getRepeatSendCounter()>3)) {
                            ticket.incCounter();
                            ticket.updateTicketStateString(TicketStateStringable.STATE_A03,ticket);
                            ticket.getTicketStateObj().setView(this.getView());
                            break;
                        }else
                        {
                            ticket.updateTicketStateString(TicketStateStringable.STATE_E02,ticket);
                            ticket.incInitialization();
                            break;
                        }
                    }
                }
                case TicketStateStringable.STATE_A03:
                {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_B01,ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                }
                case TicketStateStringable.STATE_B01:
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_B02,ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_B03:
                {
                    if(ticket.getTicketDone()) {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_C01,ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                    }else
                    {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_E06,ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                    }
                }
                case TicketStateStringable.STATE_C01:
                {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_C02,ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                }
                case TicketStateStringable.STATE_C02:
                {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_Z00,ticket);
                        break;
                }
                case TicketStateStringable.STATE_E03:
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E02,ticket);
                    break;
                }
            }
        } else if (view.getId() == dateTimeChange.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("ticketID",ticketID);

            FragmentTransaction fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                    .beginTransaction();
            CalendarView calendarView = new CalendarView();
            calendarView.setArguments(bundle);
            fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentManager.replace(R.id.container_body,  calendarView).addToBackStack(CalendarView.FRAGMENT_TRANSACTION).commit();
            ((HomeScreen) getActivity()).setTitle("יומן");
        }
        else if(view.getId() == cancel.getId())
        {
            switch (ticket.getTicketStateString())
            {
                case TicketStateStringable.STATE_A00: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E00,ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01:{
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E01,ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A03:
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E02,ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_E03:
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E04,ticket);
                   // ticket.getTicketCloseDateTime().setTime(ticket.getTicketCloseDateTime().getTime()+14000);
                   // ticket.setAlarm(utlAlarmManager.setAlarm(ticket.getTicketCloseDateTime(),TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION,ticketID));

                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C01:
                {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_E07,ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                }
                case TicketStateStringable.STATE_C02:
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_Z00,ticket);
                    break;
                }
            }
        }
    }

    private void setListeners(View view) {

        //ticket header
        ticketNumber = (TextView) view.findViewById(R.id.ticketNumber);
        ticketStatus = (TextView) view.findViewById(R.id.ticketStatus);
        dateTimeChange = (TextView) view.findViewById(R.id.dateTimeChange);
        dateTimeOpen = (TextView) view.findViewById(R.id.dateTimeOpen);

        //ticket card
        userDetailCard = (CardView) view.findViewById(R.id.userDetailCard);
        userDetailOpen = (RelativeLayout) view.findViewById(R.id.name);
        ticketDetailClose = (RelativeLayout) view.findViewById(R.id.ticketHeading);
        ticketDetailOpen = (RelativeLayout) view.findViewById(R.id.ticketHeadingExpand);

        //ticket profile buttons
        userProfile = (TextView) view.findViewById(R.id.userProfileLabel);
        btnProfile = (CardView)view.findViewById(R.id.btnProfile);
        approval = (CardView) view.findViewById(R.id.ok);
        cancel = (CardView) view.findViewById(R.id.cancel);
        txtCancel = (TextView) view.findViewById(R.id.txtCancel);

        //user card Close
        userNameCardClose = (TextView) view.findViewById(R.id.userNameCardClose);

        //user card open
        userNameCardOpen = (TextView)view.findViewById(R.id.userNameCardOpen);
        userMailCardOpen = (TextView)view.findViewById(R.id.userMailCardOpen);
        userAddCardOpen = (TextView)view.findViewById(R.id.userAddCardOpen);
        userPhoneCardOpen = (TextView)view.findViewById(R.id.userPhoneCardOpen);

        //ticket card Close
        productTicketDetailsCardClosed = (TextView)view.findViewById(R.id.productTicketDetailsCardClosed);
        categoryTicketDetailsCardClosed = (TextView)view.findViewById(R.id.categoryTicketDetailsCardClosed);
        regionTicketDetailsCardClosed = (TextView)view.findViewById(R.id.regionTicketDetailsCardClosed);

       //ticket card open
        productTicketDetailsCardOpen = (TextView)view.findViewById(R.id.productTicketDetailsCardClosed);
        categoryTicketDetailsCardOpen = (TextView)view.findViewById(R.id.categoryTicketDetailsCardOpen);
        regionTicketDetailsCardOpen = (TextView)view.findViewById(R.id.regionTicketDetailsCardOpen);
        addressTicketDetailsCardOpen  = (TextView)view.findViewById(R.id.addressTicketDetailsCardOpen);
        phoneTicketDetailsCardOpen  = (TextView)view.findViewById(R.id.phoneTicketDetailsCardOpen);
        descriptionShortTicketDetailsCardOpen  = (TextView)view.findViewById(R.id.descriptionShortTicketDetailsCardOpen);
        descriptionLongTicketDetailsCardOpen = (TextView)view.findViewById(R.id.descriptionLongTicketDetailsCardOpen);


        //ticket img
        img1 = (ImageView)view.findViewById(R.id.photo1);
        img2 = (ImageView)view.findViewById(R.id.photo2);


        dateTimeChange.setOnClickListener(this);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        ticketDetailClose.setOnClickListener(this);
        ticketDetailOpen.setOnClickListener(this);
        userProfile.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        approval.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket = ticket;
        initializeTicket();

    }

    private void initializeTicket() {

        if(UserSingleton.getInstance().isUserIsAdmin()) {
            userNameCardClose.setText(ticket.getClientNameString());
            userNameCardOpen.setText(ticket.getClientNameString());
            userAddCardOpen.setText(ticket.getClientEmail());//TODO ADD FILED IN TICKET CLIENT ADD
            userMailCardOpen.setText(ticket.getClientEmail());
            userPhoneCardOpen.setText(ticket.getClientEmail());//TODO ADD CLIENT PHONE NUMBER FILED
        }else
            {
                userNameCardClose.setText(ticket.getCompanyName());
                userNameCardOpen.setText(ticket.getCompanyName());
                userAddCardOpen.setText(ticket.getCompanyID());//TODO ADD FILED  add IN Company
                userMailCardOpen.setText(ticket.getCompanyID());//TODO ADD FILED mail IN Company
                userPhoneCardOpen.setText(ticket.getCompanyID());//TODO ADD FILED PHONE NUMBER IN Company
            }

        categoryTicketDetailsCardClosed.setText(ticket.getCategoryName());
        categoryTicketDetailsCardOpen.setText(ticket.getCategoryName());
        regionTicketDetailsCardClosed.setText(ticket.getRegionName());
        regionTicketDetailsCardOpen.setText(ticket.getRegionName());
        productTicketDetailsCardClosed.setText(ticket.getProductName());
        productTicketDetailsCardOpen.setText(ticket.getProductName());
        addressTicketDetailsCardOpen.setText(ticket.getTicketAddress());
        phoneTicketDetailsCardOpen.setText(ticket.getTicketPhone());
        descriptionShortTicketDetailsCardOpen.setText(ticket.getDescriptionShort());
        descriptionLongTicketDetailsCardOpen.setText(ticket.getDescriptionLong());

        if(!ticket.getTicketImage1().equals("error"))
        {
            UtlFirebase.downloadFile(ticket.getTicketID()+"/pic1.jpg",1);
        }
        if(!ticket.getTicketImage2().equals("error"))
        {
            UtlFirebase.downloadFile(ticket.getTicketID()+"/pic2.jpg",2);
        }
        if(ticket.getTicketCloseDateTime() !="")
            dateTimeChange.setText(ticket.getTicketCloseDateTime());

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
    public void ticketListCallback(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }

    @Override
    public void companyListCallback(List<Company> companies) {

    }

    @Override
    public void productListCallback(List<Product> products) {

    }

    @Override
    public void categoryListCallback(List<Category> categories) {

    }

    @Override
    public void regionListCallback(List<Region> regions) {

    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {

    }

    private void alternateRowColor(View view, int rowSetID){
        try {
            RowSetLayout rowSetLayout = (RowSetLayout)view.findViewById(rowSetID);
            rowSetLayout.AlternateRowsBackground(rowSetLayout, R.color.listRow_alt, RowSetLayout.ALTER_EVEN_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

