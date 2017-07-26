package com.teamrm.teamrm.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Interfaces.ClientCallback;
import com.teamrm.teamrm.Interfaces.CompanyCallback;
import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Client;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.RowViewLayout;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;

import net.cachapa.expandablelayout.ExpandableLayout;

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
public class TicketView extends Fragment {

    public static final String TAG = ":::TicketView";

    public static final String FRAGMENT_TRANSACTION = "TicketView";
    public static final int PERMISSION_PHONE_REQUEST_CODE = 1050;

    //action buttons
    private View btnApproval, btnCancel;

    //header
    private TextView ticketNumber, ticketStatus, dateTimeOpen, dateTimeAssigned, dateTimeClosed;
    private RelativeLayout dateTimeAssignedLayout, dateTimeClosedLayout;

    //User object
    private Client objUser;

    //user card
    private RowViewLayout userDetailsRow;
    private ExpandableLayout userDetailsCardExpandableLayout;
    private TextView userNameString, userEmail, userRegion, userAddress, userPhone;
    private CardView btnUserProfileOpen;
    private ImageView userProfileImage, btnUserEmailSend, btnUserAddressNavigate, btnUserPhoneCall, userExpandIcon, userLoadingIcon;
    private RowViewLayout btnUserExpandToggle;

    //Company object
    private Company objCompany;

    //Company card
    private RowViewLayout companyDetailsRow;
    private ExpandableLayout companyDetailsCardExpandableLayout;
    private TextView companyNameString, companyEmail, companyAddress, companyPhone;
    private CardView btnCompanyProfileOpen;
    private ImageView companyProfileImage, btnCompanyEmailSend, btnCompanyAddressNavigate, btnCompanyPhoneCall, companyExpandIcon, companyLoadingIcon;
    private RowViewLayout btnCompanyExpandToggle;

    //TicketLite object
    private TicketLite objTicketLite;

    //Ticket object
    private Ticket objTicket;


    //Ticket card
    private TextView ticketProduct, ticketCategory, ticketRegion, ticketAddress, ticketDescShort, ticketDescLong;
    private ImageView ticketImg1, ticketImg2;
    private ImageView btnTicketAddressNavigate, btnTicketPhoneCall, ticketExpandIcon, ticketLoadingIcon;
    private RowViewLayout btnTicketExpandToggle;
    private ExpandableLayout ticketExpandableLayout;

    static Long bundleEndTime;
    Calendar endTime;
    Fragment stateActionButtons;
    UtlAlarmManager utlAlarmManager;

    public TicketView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utlAlarmManager = new UtlAlarmManager(getContext());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            //Log.d("TICKET_ID Bundle:  ", bundle.getString("ticketID", "error"));
            objTicketLite = (TicketLite) bundle.getSerializable("mTicketLite");
        }
        Log.d(TAG, "Lifecycle:onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_new, container, false);

        getActivity().setTitle(getContext().getResources().getString(R.string.ticket_new));
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

        stateActionButtons = new StateActionButtons();

        //bind XML elements to the fragment
        bindItemsToView(view);

        //init elements from acquired TicketLite
        initItemsFromTicketLite(view);

        //init User/Company details and set listeners
        initUserCompanyItems(view);

        //init ticket details
        initTicketItems(view);

        /*
        //set typeface
        Typeface REGULAR = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-Regular.ttf");
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        ((TextView) view.findViewById(R.id.ticketStatus)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeChange)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.dateTimeOpen)).setTypeface(REGULAR);
        ((TextView) view.findViewById(R.id.ticketNumber)).setTypeface(SEMI_BOLD);
        */

        Log.d("lifeCycel", "onCreateView: ");

        return view;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        //initUserDetails();
        UtlFirebase.getTicketByKey(ticketID, this);
        Log.d("lifeCycel", "onStart: ");
    }*/

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("lifeCycel", "onAttach: ");

    }*/

    /*@Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("lifeCycel", "onActivityCreated: ");

    }
*/


    private void bindItemsToView(View view) {

        //header
        ticketNumber = (TextView) view.findViewById(R.id.ticketNumber);
        ticketStatus = (TextView) view.findViewById(R.id.ticketStatusString);
        dateTimeOpen = (TextView) view.findViewById(R.id.dateTimeOpen);
        dateTimeAssignedLayout = (RelativeLayout) view.findViewById(R.id.dateTimeAssignedLayout);
        dateTimeAssigned = (TextView) view.findViewById(R.id.dateTimeAssigned);
        dateTimeClosedLayout = (RelativeLayout) view.findViewById(R.id.dateTimeClosedLayout);
        dateTimeClosed = (TextView) view.findViewById(R.id.dateTimeClosed);

        //user details card elements
        userDetailsRow = (RowViewLayout) view.findViewById(R.id.row_userDetails);
        userDetailsCardExpandableLayout = (ExpandableLayout) view.findViewById(R.id.userCardExpandableLayout);
        userNameString = (TextView) view.findViewById(R.id.userNameString);
        userEmail = (TextView) view.findViewById(R.id.userEmailText);
        userRegion = (TextView) view.findViewById(R.id.userRegionText);
        userAddress = (TextView) view.findViewById(R.id.userAddressText);
        userPhone = (TextView) view.findViewById(R.id.userPhoneText);

        userProfileImage = (ImageView) view.findViewById(R.id.userProfileImage);
        userLoadingIcon = (ImageView) view.findViewById(R.id.userLoadingIcon);
        userExpandIcon = (ImageView) view.findViewById(R.id.userExpandIcon);
        btnUserEmailSend = (ImageView) view.findViewById(R.id.btnUserEmailSend);
        btnUserAddressNavigate = (ImageView) view.findViewById(R.id.btnUserAddressNavigate);
        btnUserPhoneCall = (ImageView) view.findViewById(R.id.btnUserPhoneCall);

        btnUserExpandToggle = (RowViewLayout) view.findViewById(R.id.userNameRow);

        btnUserProfileOpen = (CardView) view.findViewById(R.id.btnUserProfileOpen);

        //company details card elements
        companyDetailsRow = (RowViewLayout) view.findViewById(R.id.row_companyDetails);
        companyDetailsCardExpandableLayout = (ExpandableLayout) view.findViewById(R.id.companyCardExpandableLayout);

        companyNameString = (TextView) view.findViewById(R.id.companyNameString);
        companyEmail = (TextView) view.findViewById(R.id.companyEmailText);
        companyAddress = (TextView) view.findViewById(R.id.companyAddressText);
        companyPhone = (TextView) view.findViewById(R.id.companyPhoneText);

        companyProfileImage = (ImageView) view.findViewById(R.id.companyProfileImage);
        companyLoadingIcon = (ImageView) view.findViewById(R.id.companyLoadingIcon);
        companyExpandIcon = (ImageView) view.findViewById(R.id.companyExpandIcon);
        btnCompanyEmailSend = (ImageView) view.findViewById(R.id.btnCompanyEmailSend);
        btnCompanyAddressNavigate = (ImageView) view.findViewById(R.id.btnCompanyAddressNavigate);
        btnCompanyPhoneCall = (ImageView) view.findViewById(R.id.btnCompanyPhoneCall);

        btnCompanyExpandToggle = (RowViewLayout) view.findViewById(R.id.companyNameRow);


        //Ticket details card
        ticketProduct = (TextView) view.findViewById(R.id.ticketProductText);
        ticketCategory = (TextView) view.findViewById(R.id.ticketCategoryText);
        ticketRegion = (TextView) view.findViewById(R.id.ticketRegionText);
        ticketAddress = (TextView) view.findViewById(R.id.ticketAddressText);
        ticketDescShort = (TextView) view.findViewById(R.id.ticketDescriptionShortText);
        ticketDescLong = (TextView) view.findViewById(R.id.ticketDescriptionLongText);

        ticketImg1 = (ImageView) view.findViewById(R.id.ticketPhoto1);
        ticketImg2 = (ImageView) view.findViewById(R.id.ticketPhoto2);
        btnTicketPhoneCall = (ImageView) view.findViewById(R.id.btnTicketPhoneCall);
        btnTicketAddressNavigate = (ImageView) view.findViewById(R.id.btnTicketAddressNavigate);
        ticketLoadingIcon = (ImageView) view.findViewById(R.id.ticketLoadingIcon);
        ticketExpandIcon = (ImageView) view.findViewById(R.id.ticketExpandIcon);

        btnTicketExpandToggle = (RowViewLayout) view.findViewById(R.id.ticketHeaderRow);
        ticketExpandableLayout = (ExpandableLayout) view.findViewById(R.id.ticketExpandableLayout);

        //action buttons
        btnApproval = view.findViewById(R.id.btnOk);
        btnCancel = view.findViewById(R.id.btnCancel);


    }

    private void initItemsFromTicketLite(View view) {

        //header start
        ticketNumber.setText(getContext().getString(R.string.label_ticket_number, objTicketLite.getTicketNumber()));
        ticketStatus.setText(objTicketLite.getTicketStateString());//TODO: replace with icon and text
        dateTimeOpen.setText(objTicketLite.getTicketOpenDateTime());
        //show Assigned or Closed date time
        switch (objTicketLite.getTicketStateString()) {
            case ("Z00"):
                dateTimeAssignedLayout.setVisibility(View.GONE);
                if (null != objTicketLite.getTicketCloseDateTime()) {
                    dateTimeClosedLayout.setVisibility(View.VISIBLE);
                    dateTimeClosed.setText(objTicketLite.getTicketCloseDateTime());
                } else {
                    dateTimeClosedLayout.setVisibility(View.GONE);
                    Log.e(TAG, "Ticket is closed but no close date time defined");
                }
                break;

            //TODO: define more states if needed

            default:
                if (null != objTicketLite.getTicketAssignedDateTime()) {
                    dateTimeAssignedLayout.setVisibility(View.VISIBLE);
                    dateTimeAssigned.setText(objTicketLite.getTicketAssignedDateTime());
                } else {
                    dateTimeAssignedLayout.setVisibility(View.GONE);
                }
        }
        //header end
        // check user type and display Company or User card accordingly
        switch (UserSingleton.getLoadedUserType()) {
            case Users.STATUS_ADMIN:
            case Users.STATUS_TECH:
                //user details card elements
                companyDetailsRow.setVisibility(View.GONE);
                userDetailsRow.setVisibility(View.VISIBLE);
                userNameString.setText(objTicketLite.getClientNameString());
                userLoadingIcon.setVisibility(View.VISIBLE);
                userExpandIcon.setVisibility(View.GONE);
                rotateWaitingIcon(userLoadingIcon);
                break;
            case Users.STATUS_CLIENT:
                //company details card elements
                userDetailsRow.setVisibility(View.GONE);
                companyDetailsRow.setVisibility(View.VISIBLE);
                companyNameString.setText(objTicketLite.getCompanyName());
                companyExpandIcon.setVisibility(View.GONE);
                companyLoadingIcon.setVisibility(View.VISIBLE);
                rotateWaitingIcon(companyLoadingIcon);
                break;
        }

        //ticket card
        ticketProduct.setText(objTicketLite.getProductName());
        ticketCategory.setText(objTicketLite.getCategoryName());
        ticketRegion.setText(objTicketLite.getRegionName());
        ticketExpandIcon.setVisibility(View.GONE);
        ticketLoadingIcon.setVisibility(View.VISIBLE);
        rotateWaitingIcon(ticketLoadingIcon);
    }

    private void initUserCompanyItems(final View view) {
        switch (UserSingleton.getLoadedUserType()) {
            case Users.STATUS_ADMIN:
            case Users.STATUS_TECH:
                UtlFirebase.getClientByID(objTicketLite.getClientID(), new ClientCallback() {
                    @Override
                    public void clientCallback(Client client) {
                        objUser = client;
                        userNameString.setText(objUser.getUserNameString());
                        //TODO: set userProfileImage
                        userEmail.setText(objUser.getUserEmail());
                        if (objUser.getUserStatus().equals(Users.STATUS_CLIENT)) {
                            userRegion.setText(objUser.getClientRegion());
                            view.findViewById(R.id.userRegionRow).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.btnUserProfileOpen).setVisibility(View.VISIBLE);
                        } else {
                            view.findViewById(R.id.userRegionRow).setVisibility(View.GONE);
                            view.findViewById(R.id.btnUserProfileOpen).setVisibility(View.GONE);
                        }
                        //TODO: indicate that ticket was opened by Admin or Tech
                        userAddress.setText(objUser.getUserAddress());
                        userLoadingIcon.clearAnimation();
                        userLoadingIcon.setVisibility(View.GONE);
                        userExpandIcon.setVisibility(View.VISIBLE);

                        //set onClick
                        btnUserExpandToggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                userDetailsCardExpandableLayout.toggle();
                                rotateButtonOnToggle(userExpandIcon);
                            }
                        });
                        btnUserEmailSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openMailDialog(objUser.getUserEmail());
                            }
                        });
                        btnUserEmailSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openMailDialog(objUser.getUserEmail());
                            }
                        });
                        btnUserPhoneCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openPhoneDialog(objUser.getUserPhone());
                            }
                        });
                        btnUserAddressNavigate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGpsDialog(objUser.getUserAddress());
                            }
                        });
                    }
                });
                break;

            case Users.STATUS_CLIENT:
                if (!Company.getCompanyList().contains(new Company(objTicketLite.getCompanyID()))) {
                    UtlFirebase.getCompanyByID(objTicketLite.getCompanyID(), new CompanyCallback() {
                        @Override
                        public void companyCallback(Company company) {
                            objCompany = company;
                            initUserCompanyItems(view);
                        }
                    });
                } else {
                    objCompany = Company.getCompanyList().get(Company.getCompanyList().indexOf(new Company(objTicketLite.getCompanyID())));
                    companyNameString.setText(objCompany.getCompanyName());
                    companyEmail.setText(objCompany.getCompanyMail());
                    companyAddress.setText(objCompany.getCompanyAddress());
                    companyPhone.setText(objCompany.getCompanyPhone());

                    companyLoadingIcon.clearAnimation();
                    companyLoadingIcon.setVisibility(View.GONE);
                    companyExpandIcon.setVisibility(View.VISIBLE);

                    //set onClick
                    btnCompanyExpandToggle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            companyDetailsCardExpandableLayout.toggle();
                            rotateButtonOnToggle(companyExpandIcon);
                        }
                    });
                    btnCompanyEmailSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openMailDialog(objCompany.getCompanyMail());
                        }
                    });
                    btnCompanyPhoneCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openPhoneDialog(objCompany.getCompanyPhone());
                        }
                    });
                    btnCompanyAddressNavigate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openGpsDialog(objCompany.getCompanyAddress());
                        }
                    });
                }
                break;
        }

       /* //user details card actions
        btnUserEmailSend.setOnClickListener(this);
        btnUserAddressNavigate.setOnClickListener(this);
        btnUserPhoneCall.setOnClickListener(this);
        btnUserProfileOpen.setOnClickListener(this);

        //Ticket actions
        btnTicketAddressNavigate.setOnClickListener(this);
        btnTicketPhoneCall.setOnClickListener(this);
        ticketImg1.setOnClickListener(this);
        ticketImg2.setOnClickListener(this);
        dateTimeAssignedLayout.setOnClickListener(this);
        btnApproval.setOnClickListener(this);
        btnCancel.setOnClickListener(this);*/

    }

    private void initTicketItems(final View view) {
        if (!Ticket.getTicketList().contains(new Ticket(objTicketLite.getTicketID()))) {
            UtlFirebase.getTicketByKey(objTicketLite.getTicketID(), new FireBaseBooleanCallback() {
                @Override
                public void booleanCallback(boolean isTrue) {
                    if (isTrue) {
                        initTicketItems(view);
                    } else {
                        new NiceToast(getContext(), "Could not load ticket, please try again later", NiceToast.NICETOAST_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            objTicket = Ticket.getTicketById(objTicketLite.getTicketID());
            ticketProduct.setText(objTicket.getProductName());
            ticketCategory.setText(objTicket.getCategoryName());
            ticketRegion.setText(objTicket.getRegionName());
            ticketAddress.setText(objTicket.getTicketAddress());
            ticketDescShort.setText(objTicket.getDescriptionShort());
            if (null != objTicket.getDescriptionLong() && objTicket.getDescriptionLong().length() > 1) {
                view.findViewById(R.id.ticketDescriptionLongText).setVisibility(View.VISIBLE);
                ticketDescLong.setText(objTicket.getDescriptionLong());
            } else {
                view.findViewById(R.id.ticketDescriptionLongText).setVisibility(View.GONE);
            }
            if ((null == objTicket.getTicketImage1() || objTicket.getTicketImage1().equals("error")) &&
                    (null == objTicket.getTicketImage2() || objTicket.getTicketImage2().equals("error"))) {
                view.findViewById(R.id.ticketPhotosRow).setVisibility(View.GONE);
            } else if ((null != objTicket.getTicketImage1() || !objTicket.getTicketImage1().equals("error")) &&
                    (null == objTicket.getTicketImage2() || objTicket.getTicketImage2().equals("error"))) {
                view.findViewById(R.id.ticketPhotosRow).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto2).setVisibility(View.GONE);
                UtlFirebase.downloadFile(objTicket.getTicketID() + "/pic1.jpg", ticketImg1);
            } else if ((null != objTicket.getTicketImage2() || !objTicket.getTicketImage2().equals("error")) &&
                    (null == objTicket.getTicketImage1() || objTicket.getTicketImage1().equals("error"))) {
                view.findViewById(R.id.ticketPhotosRow).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto2).setVisibility(View.GONE);
                UtlFirebase.downloadFile(objTicket.getTicketID() + "/pic2.jpg", ticketImg2);
            } else if ((null != objTicket.getTicketImage1() || !objTicket.getTicketImage1().equals("error")) &&
                    (null != objTicket.getTicketImage2() || !objTicket.getTicketImage2().equals("error"))) {
                view.findViewById(R.id.ticketPhotosRow).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.ticketPhoto2).setVisibility(View.VISIBLE);
                UtlFirebase.downloadFile(objTicket.getTicketID() + "/pic1.jpg", ticketImg1);
                UtlFirebase.downloadFile(objTicket.getTicketID() + "/pic2.jpg", ticketImg2);
            }
            ticketLoadingIcon.clearAnimation();
            ticketLoadingIcon.setVisibility(View.GONE);
            ticketExpandIcon.setVisibility(View.VISIBLE);

            RowSetLayout ticketRowSet = (RowSetLayout) view.findViewById(R.id.ticketRowSet);
            ticketRowSet.alternateRowsBackground(R.color.red, RowSetLayout.ALTER_ODD_ROWS);

            //set onClick
            btnTicketExpandToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketExpandableLayout.toggle();
                    rotateButtonOnToggle(ticketExpandIcon);
                }
            });
            btnTicketAddressNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGpsDialog(objTicket.getTicketAddress());
                }
            });
        }
    }


    /* @Override
    public void onClick(View view) {
        // Toast.makeText(this.getContext(), view.getId()+"", Toast.LENGTH_SHORT).show();

     *//*   int viewID = view.getId();
        switch (viewID){
            case userDetailCard.getId():

        }*//*


        if (view.getId() == btnTicketAddressNavigate.getId()) {
            openGpsDialog(objTicket.getTicketAddress());
        } else if (view.getId() == btnTicketPhoneCall.getId()) {
            openPhoneDialog(objTicket.getTicketPhone());

        } else if (view.getId() == btnUserEmailSend.getId()) {
            openMailDialog();

        } else if (view.getId() == btnUserAddressNavigate.getId()) {
            openGpsDialog();

        } else if (view.getId() == btnUserPhoneCall.getId()) {
            openPhoneDialog();


        } else if (view.getId() == btnUserProfileOpen.getId()) {
            Toast.makeText(getContext(), UserSingleton.getInstance().getUserNameString(), Toast.LENGTH_SHORT).show();


        } else if (view.getId() == btnApproval.getId()) {
            new NiceToast(getContext(), "Clicked approve", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
            *//*switch (objTicket.getTicketStateString()) {
                case TicketStateStringable.STATE_A00: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_A01, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_B01, objTicket);
                    //objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A02CN: {
                    if (timeFormated != null) {
                        if (!(objTicket.getRepeatSendCounter() > 3)) {
                            objTicket.incCounter();
                            objTicket.updateTicketStateString(TicketStateStringable.STATE_A03, objTicket);
                            objTicket.getTicketStateObj().setView(this.getView());
                            break;
                        } else {
                            objTicket.updateTicketStateString(TicketStateStringable.STATE_E02, objTicket);
                            objTicket.incInitialization();
                            break;
                        }
                    }
                }
                case TicketStateStringable.STATE_A03: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_B01, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_B01: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_B02, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_B03: {
                    if (objTicket.getTicketDone()) {
                        objTicket.updateTicketStateString(TicketStateStringable.STATE_C01, objTicket);
                        objTicket.getTicketStateObj().setView(this.getView());
                        break;
                    } else {
                        objTicket.updateTicketStateString(TicketStateStringable.STATE_E06, objTicket);
                        objTicket.getTicketStateObj().setView(this.getView());
                        break;
                    }
                }
                case TicketStateStringable.STATE_C01: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_C02, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C02: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_Z00, objTicket);
                    break;
                }
                case TicketStateStringable.STATE_E03: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E02, objTicket);
                    break;
                }
            }*//*


        *//*} else if (view.getId() == dateTimeOther.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("ticketID", ticketID);

            FragmentTransaction fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager()
                    .beginTransaction();
            CalendarView calendarView = new CalendarView();
            calendarView.setArguments(bundle);
            fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentManager.replace(R.id.container_body, calendarView).addToBackStack(CalendarView.FRAGMENT_TRANSACTION).commit();
            (getActivity()).setTitle("יומן");
*//*

        } else if (view.getId() == ticketImg1.getId()) {
            if (!ticketImg1.getDrawable().toString().contains("Vector")) {
                try {
                    Bitmap bitmapOrg = ((BitmapDrawable) ticketImg1.getDrawable()).getBitmap();
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
        } else if (view.getId() == ticketImg2.getId()) {
            if (!ticketImg1.getDrawable().toString().contains("Vector")) {
                try {
                    Bitmap bitmapOrg = ((BitmapDrawable) ticketImg2.getDrawable()).getBitmap();
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
        } else if (view.getId() == btnCancel.getId()) {
            new NiceToast(getContext(), "Clicked cancel", NiceToast.NICETOAST_WARNING, Toast.LENGTH_SHORT).show();
            *//*switch (objTicket.getTicketStateString()) {
                case TicketStateStringable.STATE_A00: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E00, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A01: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E01, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_A03: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E02, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_E03: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E04, objTicket);
                    // objTicket.getTicketCloseDateTime().setTime(objTicket.getTicketCloseDateTime().getTime()+14000);
                    // objTicket.setAlarm(utlAlarmManager.setAlarm(objTicket.getTicketCloseDateTime(),TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION,ticketID));

                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C01: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_E07, objTicket);
                    objTicket.getTicketStateObj().setView(this.getView());
                    break;
                }
                case TicketStateStringable.STATE_C02: {
                    objTicket.updateTicketStateString(TicketStateStringable.STATE_Z00, objTicket);
                    break;
                }
            }*//*
        }
    }
*/

  /*  @Override
    public void onResume() {
        super.onResume();
        initUserDetails();
        Log.d("onResume", "onResume: ");
    }*/


    private String date2String(Date date) {
        SimpleDateFormat format1;
        if (date.getHours() > 0)
            format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        else
            format1 = new SimpleDateFormat("dd/MM/yyyy");

        String timeFormated = format1.format(date);
        return timeFormated;
    }

    public void openPhoneDialog(String phone) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_PHONE_REQUEST_CODE);
            Log.d("openPhoneDialog", ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) + "");
        } else {

            getActivity().startActivity(callIntent);
        }
    }

    private void openGpsDialog(String strAddress) {

        Address formattedAddress = null;
        String title = "בחר תוכנת ניווט";
        formattedAddress = getLocationFromAddress(strAddress);
        if (formattedAddress != null) {
            double longitude = formattedAddress.getLongitude();
            double latitude = formattedAddress.getLatitude();

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

    private void openMailDialog(String strEmail) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?to=" + strEmail);
        intent.setData(data);
        startActivity(intent);
    }

    public Address getLocationFromAddress(String strAddress) {

        if (strAddress.length() == 0) {
            return null;
        }
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(strAddress, 1);
        } catch (IOException e) {
            Log.d("getLocationFromAddress", e.getMessage());
        }
        if (null != addresses && !addresses.isEmpty()) {
            return addresses.get(0);
        } else {
            return null;
        }
    }

    private void alternateRowColor(View view, int rowSetID) {
        try {
            RowSetLayout rowSetLayout = (RowSetLayout) view.findViewById(rowSetID);
            rowSetLayout.alternateRowsBackground(R.color.listRow_alt, RowSetLayout.ALTER_EVEN_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rotateWaitingIcon(ImageView waitingIcon) {
        //Log.d(TAG, "Rotating " + waitingIcon.getId());
        waitingIcon.clearAnimation();
        Animation rotateClockwise = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise);
        waitingIcon.startAnimation(rotateClockwise);
    }

    private void rotateButtonOnToggle(ImageView expandBtn) {
        expandBtn.animate().rotation(expandBtn.getRotation() - 180f).setInterpolator(new LinearInterpolator()).start();
    }
}

