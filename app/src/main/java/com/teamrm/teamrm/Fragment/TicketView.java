package com.teamrm.teamrm.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Interfaces.ClientCallback;
import com.teamrm.teamrm.Interfaces.CompanyCallback;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Client;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketView extends Fragment implements View.OnClickListener, FireBaseAble {

    public static final String FRAGMENT_TRANSACTION = "TicketView";
    public static final int PERMISSION_PHONE_REQUEST_CODE = 1050;
    CardView userDetailCard, btnProfile;
    View btnApproval, btnCancel;
    private RelativeLayout userDetailOpen, ticketDetailClose, ticketDetailOpen;
    TextView txtCancel, userNameClose, userProfile, userAreaCardOpen, userNameCardOpen, dateTimeChange, userNameCardClose, dateTimeOpen, ticketNumber, ticketStatus, productTicketDetailsCardClosed, categoryTicketDetailsCardClosed, regionTicketDetailsCardClosed, categoryTicketDetailsCardOpen, regionTicketDetailsCardOpen, addressTicketDetailsCardOpen, phoneTicketDetailsCardOpen, descriptionShortTicketDetailsCardOpen, descriptionLongTicketDetailsCardOpen, userMailCardOpen, userAddCardOpen, userPhoneCardOpen, productTicketDetailsCardOpen;
    private ImageView img1, img2;
    public static ImageView mailBtn, locationButton, phoneButton, navigateToAddressTicketDetailsCardOpen, callNumberTicketDetailsCardOpen;
    private static ProgressDialog mProgress;
    private Ticket ticket;
    static String ticketID, timeFormated;
    static Long bundleEndTime;
    Calendar endTime;
    private Users userProfileObj;
    Fragment stateActionButtons;
    UtlAlarmManager utlAlarmManager;
    private Object userDitaile;

    public TicketView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Bundle bundle = this.getArguments();

        mProgress = new ProgressDialog(getContext());
        mProgress.setMessage("מוריד קריאה...");
        mProgress.setCanceledOnTouchOutside(false);


        utlAlarmManager = new UtlAlarmManager(getContext());
        if (bundle != null) {
            Log.d("TICKET_ID Bundle:  ", bundle.getString("ticketID", "error"));


            if (!bundle.getString("ticketID", "error").equals("error")) {
                String ticketId = bundle.getString("ticketID", "error");
                Log.d("TICKET_ID Bundle:  ", ticketId);
                ticketID = ticketId;

                if (Ticket.getTickeyById(ticketId) != null) {
                    this.ticket = Ticket.getTickeyById(ticketId);

                } else
                    UtlFirebase.getTicketByKey(ticketID, this);

            }

        }
        Log.d("lifeCycel", "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        //alternateRowColor(view, R.id.rowSet1);


        mProgress.show();
        stateActionButtons = new StateActionButtons();

        setListeners(view);
        if (this.ticket != null) {
            initializeUserDitile();
        }
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        getActivity().setTitle(getContext().getResources().getString(R.string.ticket_new));
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);


        ((TextView) view.findViewById(R.id.ticketStatus)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.ticketNumber)).setTypeface(SEMI_BOLD);

        Log.d("lifeCycel", "onCreateView: ");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //initializeUserDitile();
        UtlFirebase.getTicketByKey(ticketID, this);
        Log.d("lifeCycel", "onStart: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("lifeCycel", "onAttach: ");

    }

    @Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("lifeCycel", "onActivityCreated: ");

    }


    @Override
    public void onClick(View view) {
        // Toast.makeText(this.getContext(), view.getId()+"", Toast.LENGTH_SHORT).show();

        if (view.getId() == userDetailCard.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.VISIBLE);
            //alternateRowColor(view, R.id.rowSet2);
        } else if (view.getId() == navigateToAddressTicketDetailsCardOpen.getId()) {
            openGpsDialog(ticket.getTicketAddress());
        } else if (view.getId() == callNumberTicketDetailsCardOpen.getId()) {
            openPhoneDialog(ticket.getTicketPhone());

        } else if (view.getId() == mailBtn.getId()) {
            openMailDialog();

        } else if (view.getId() == locationButton.getId()) {
            openGpsDialog();

        } else if (view.getId() == phoneButton.getId()) {
            openPhoneDialog();

        } else if (view.getId() == userDetailOpen.getId()) {
            this.getView().findViewById(R.id.userDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.userDetailsOpen).setVisibility(View.GONE);
        } else if (view.getId() == ticketDetailClose.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.GONE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.VISIBLE);
            //alternateRowColor(view, R.id.rowSet1);
        } else if (view.getId() == ticketDetailOpen.getId()) {
            this.getView().findViewById(R.id.ticketDetails).setVisibility(View.VISIBLE);
            this.getView().findViewById(R.id.ticketDetailsOpen).setVisibility(View.GONE);
            //alternateRowColor(view, R.id.rowSet1);
        } else if (view.getId() == userProfile.getId()) {
            Toast.makeText(getContext(), "USER PROFILE " + ticket.getClientNameString(), Toast.LENGTH_SHORT).show();

        } else if (view.getId() == btnProfile.getId()) {
            Toast.makeText(getContext(), UserSingleton.getInstance().getUserNameString(), Toast.LENGTH_SHORT).show();
        } else if (view.getId() == btnApproval.getId()) {
            new NiceToast(getContext(), "Clicked approve", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
            /*switch (ticket.getTicketStateString()) {
                case TicketStateStringable.STATE_A00: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_A01, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_B01, ticket);
                    //ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A02CN: {
                    if (timeFormated != null) {
                        if (!(ticket.getRepeatSendCounter() > 3)) {
                            ticket.incCounter();
                            ticket.updateTicketStateString(TicketStateStringable.STATE_A03, ticket);
                            ticket.getTicketStateObj().setView(this.getView());
                            break;
                        } else {
                            ticket.updateTicketStateString(TicketStateStringable.STATE_E02, ticket);
                            ticket.incInitialization();
                            break;
                        }
                    }
                }
                case TicketStateStringable.STATE_A03: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_B01, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_B01: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_B02, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_B03: {
                    if (ticket.getTicketDone()) {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_C01, ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                    } else {
                        ticket.updateTicketStateString(TicketStateStringable.STATE_E06, ticket);
                        ticket.getTicketStateObj().setView(this.getView());
                        break;
                    }
                }
                case TicketStateStringable.STATE_C01: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_C02, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C02: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_Z00, ticket);
                    break;
                }
                case TicketStateStringable.STATE_E03: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E02, ticket);
                    break;
                }
            }*/
        } else if (view.getId() == dateTimeChange.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("ticketID", ticketID);

            FragmentTransaction fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                    .beginTransaction();
            CalendarView calendarView = new CalendarView();
            calendarView.setArguments(bundle);
            fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentManager.replace(R.id.container_body, calendarView).addToBackStack(CalendarView.FRAGMENT_TRANSACTION).commit();
            (getActivity()).setTitle("יומן");
        }
        else if(view.getId() == img1.getId())
        {
            Bitmap bitmapOrg = ((BitmapDrawable) img1.getDrawable()).getBitmap();
            if(null != bitmapOrg)
            {
                try {
                    FullScreenImage fullScreenImage = new FullScreenImage();
                    Bundle ticketData = new Bundle();
                    ticketData.putString("ticketId", ticketID);
                    ticketData.putInt("imgClicked", 1);
                    fullScreenImage.setArguments(ticketData);
                    fullScreenImage.setBitmap(bitmapOrg);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                    ft.add(R.id.container_body, fullScreenImage, FullScreenImage.FRAGMENT_TRANSACTION);
                    ft.addToBackStack(FullScreenImage.FRAGMENT_TRANSACTION);
                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(view.getId() == img2.getId())
        {
            Bitmap bitmapOrg = ((BitmapDrawable) img2.getDrawable()).getBitmap();
            if(null != bitmapOrg)
            {
                try {
                    FullScreenImage fullScreenImage = new FullScreenImage();
                    Bundle ticketData = new Bundle();
                    ticketData.putString("ticketId", ticketID);
                    ticketData.putInt("imgClicked", 2);
                    fullScreenImage.setArguments(ticketData);
                    fullScreenImage.setBitmap(bitmapOrg);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                    ft.add(R.id.container_body, fullScreenImage, FullScreenImage.FRAGMENT_TRANSACTION);
                    ft.addToBackStack(FullScreenImage.FRAGMENT_TRANSACTION);
                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (view.getId() == btnCancel.getId()) {
            new NiceToast(getContext(), "Clicked cancel", NiceToast.NICETOAST_WARNING, Toast.LENGTH_SHORT).show();
            /*switch (ticket.getTicketStateString()) {
                case TicketStateStringable.STATE_A00: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E00, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E01, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A03: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E02, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_E03: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E04, ticket);
                    // ticket.getTicketCloseDateTime().setTime(ticket.getTicketCloseDateTime().getTime()+14000);
                    // ticket.setAlarm(utlAlarmManager.setAlarm(ticket.getTicketCloseDateTime(),TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION,ticketID));

                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C01: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E07, ticket);
                    ticket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C02: {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_Z00, ticket);
                    break;
                }
            }*/
        }
    }

    public void openPhoneDialog(String phone) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_REQUEST_CODE);
            Log.d("openPhoneDialog", ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) + "");
            return;
        } else {

            getActivity().startActivity(callIntent);
        }
    }

    private void openGpsDialog(String addres) {

        Address formataddres = null;
        String title = "בחר תוכנת ניווט";
        formataddres = getLocationFromAddress(addres);
        if (formataddres != null) {
            double longitude = formataddres.getLongitude();
            double latitude = formataddres.getLatitude();

            String link = "geo:" + latitude + "," + longitude;
            Intent navigateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            Intent chooser = Intent.createChooser(navigateIntent, title);
            if (navigateIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(chooser);
            }
        } else {
            new MaterialDialog.Builder(getContext())
                    .title("לא הוזנה כתובת לתקלה זו!")
                    .titleColor(Color.BLACK)
                    .positiveText("הבנתי")
                    .backgroundColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .show();
        }
    }

    public void openPhoneDialog() {

        String phone = null;

        if (userDitaile instanceof Client)
            phone = ((Client) userDitaile).getUserPhone();
        else
            phone = ((Company) userDitaile).getCompanyPhone();


        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_REQUEST_CODE);
            Log.d("openPhoneDialog", ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) + "");
            return;
        } else {

            getActivity().startActivity(callIntent);
        }
    }

    private void openGpsDialog() {

        Address addres;
        String title = "בחר לנווט עם...";
        if (getLocationFromAddress(ticket.getTicketAddress()) != null) {
            if (userDitaile instanceof Client)
                addres = getLocationFromAddress(((Client) userDitaile).getUserAddress());
            else
                addres = getLocationFromAddress(((Company) userDitaile).getCompanyAddress());
            double longitude = addres.getLongitude();
            double latitude = addres.getLatitude();

            String link = "geo:" + latitude + "," + longitude;
            Intent navigateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            Intent chooser = Intent.createChooser(navigateIntent, title);
            if (navigateIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(chooser);
            }
        } else {
            new MaterialDialog.Builder(getContext())
                    .title("לא קיימת כתובת למשתמש זה")
                    .titleColor(Color.BLACK)
                    .positiveText("הבנתי")
                    .backgroundColor(Color.WHITE)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .show();
        }
    }

    private void openMailDialog() {
        String email;
        if (userDitaile instanceof Client)
            email = ((Client) userDitaile).getUserEmail();
        else
            email = ((Company) userDitaile).getCompanyName();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?to=" + email);
        intent.setData(data);
        startActivity(intent);
    }

    public Address getLocationFromAddress(String strAddress) {

        if (strAddress.length() == 0)
            return null;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(strAddress, 1);
        } catch (IOException e) {
            Log.d("getLocationFromAddress", e.getMessage());
        }
        Address address = addresses.get(0);

        return address;
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
        btnProfile = (CardView) view.findViewById(R.id.btnProfile);
        btnApproval = view.findViewById(R.id.btnOk);
        btnCancel = view.findViewById(R.id.btnCancel);
        //txtCancel = (TextView) view.findViewById(R.id.txtCancel);

        //user card Close
        userNameCardClose = (TextView) view.findViewById(R.id.userNameCardClose);

        //user card open
        userNameCardOpen = (TextView) view.findViewById(R.id.userNameCardOpen);
        userMailCardOpen = (TextView) view.findViewById(R.id.userMailCardOpen);
        userAddCardOpen = (TextView) view.findViewById(R.id.userAddCardOpen);
        userPhoneCardOpen = (TextView) view.findViewById(R.id.userPhoneCardOpen);
        userAreaCardOpen = (TextView) view.findViewById(R.id.userAreaCardOpen);

        //ticket card Close
        productTicketDetailsCardClosed = (TextView) view.findViewById(R.id.productTicketDetailsCardClosed);
        categoryTicketDetailsCardClosed = (TextView) view.findViewById(R.id.categoryTicketDetailsCardClosed);
        regionTicketDetailsCardClosed = (TextView) view.findViewById(R.id.regionTicketDetailsCardClosed);

        //ticket card open
        productTicketDetailsCardOpen = (TextView) view.findViewById(R.id.productTicketDetailsCardOpen);
        categoryTicketDetailsCardOpen = (TextView) view.findViewById(R.id.categoryTicketDetailsCardOpen);
        regionTicketDetailsCardOpen = (TextView) view.findViewById(R.id.regionTicketDetailsCardOpen);
        addressTicketDetailsCardOpen = (TextView) view.findViewById(R.id.addressTicketDetailsCardOpen);
        phoneTicketDetailsCardOpen = (TextView) view.findViewById(R.id.phoneTicketDetailsCardOpen);
        descriptionShortTicketDetailsCardOpen = (TextView) view.findViewById(R.id.descriptionShortTicketDetailsCardOpen);
        descriptionLongTicketDetailsCardOpen = (TextView) view.findViewById(R.id.descriptionLongTicketDetailsCardOpen);

        navigateToAddressTicketDetailsCardOpen = (ImageView) view.findViewById(R.id.navigateToAddress);
        callNumberTicketDetailsCardOpen = (ImageView) view.findViewById(R.id.callNumber);

        //ticket img
        img1 = (ImageView) view.findViewById(R.id.photo1);
        img2 = (ImageView) view.findViewById(R.id.photo2);

        mailBtn = (ImageView) view.findViewById(R.id.mailButton);
        locationButton = (ImageView) view.findViewById(R.id.locationButton);
        phoneButton = (ImageView) view.findViewById(R.id.phoneButton);

        navigateToAddressTicketDetailsCardOpen.setOnClickListener(this);
        callNumberTicketDetailsCardOpen.setOnClickListener(this);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        dateTimeChange.setOnClickListener(this);
        userDetailCard.setOnClickListener(this);
        userDetailOpen.setOnClickListener(this);
        ticketDetailClose.setOnClickListener(this);
        ticketDetailOpen.setOnClickListener(this);
        userProfile.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnApproval.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        mailBtn.setOnClickListener(this);
        locationButton.setOnClickListener(this);
        phoneButton.setOnClickListener(this);
    }

    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket = ticket;
        initializeUserDitile();


    }

    @Override
    public void onResume() {
        super.onResume();
        initializeUserDitile();
        Log.d("onResume", "onResume: ");
    }

    private void initializeUserDitile() {
        if (UserSingleton.getInstance().isUserIsAdmin()) {
            UtlFirebase.getClientByID(ticket.getClientID(), new ClientCallback() {
                @Override
                public void clientCallback(Client client) {

                    userDitaile = client;
                    initializeTicket();

                }
            });
        } else {
            UtlFirebase.getCompanyByID(ticket.getCompanyID(), new CompanyCallback() {
                @Override
                public void companyCallback(Company company) {

                    userDitaile = company;
                    initializeTicket();

                }
            });
        }

    }

    private void initializeTicket() {


        ticketNumber.setText("מספר קריאה " + ticket.getTicketNumber());

        Log.d("initializeTicket", ticket.getTicketAssignedDateTime());

        //if(ticket.getTicketAssignedDateTime()!=null&&ticket.getTicketAssignedDateTime().length()!=0)
        dateTimeOpen.setText((ticket.getTicketOpenDateTime().split("-"))[1]);

        if (userDitaile instanceof Client) {

            userNameCardClose.setText(((Users) userDitaile).getUserNameString());
            userNameCardOpen.setText(((Users) userDitaile).getUserNameString());
            userAddCardOpen.setText(((Users) userDitaile).getUserAddress());
            userMailCardOpen.setText(((Users) userDitaile).getUserEmail());
            userPhoneCardOpen.setText(((Users) userDitaile).getUserPhone());
            userAreaCardOpen.setText(((Users) userDitaile).getUserNameString());//TODO ADD FILED AREA TO USER


        } else {
            userNameCardClose.setText(((Company) userDitaile).getCompanyName());
            userNameCardOpen.setText(((Company) userDitaile).getCompanyName());
            userAddCardOpen.setText(((Company) userDitaile).getCompanyAddress());
            userMailCardOpen.setText(((Company) userDitaile).getCompanyName());//TODO ADD FILED mail IN Company
            userPhoneCardOpen.setText(((Company) userDitaile).getCompanyPhone());
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

        if (!ticket.getTicketImage1().equals("error")) {
            UtlFirebase.downloadFile(ticket.getTicketID() + "/pic1.jpg", img1);
        }
        if (!ticket.getTicketImage2().equals("error")) {
            UtlFirebase.downloadFile(ticket.getTicketID() + "/pic2.jpg", img2);
        }
        if (ticket.getTicketAssignedDateTime().length() > 0)
            dateTimeChange.setText(ticket.getTicketAssignedDateTime());

        mProgress.hide();
    }

    private String date2String(Date date) {
        SimpleDateFormat format1;
        if (date.getHours() > 0)
            format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        else
            format1 = new SimpleDateFormat("dd/MM/yyyy");

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

    private void alternateRowColor(View view, int rowSetID) {
        try {
            RowSetLayout rowSetLayout = (RowSetLayout) view.findViewById(rowSetID);
            rowSetLayout.AlternateRowsBackground(rowSetLayout, R.color.listRow_alt, RowSetLayout.ALTER_EVEN_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

