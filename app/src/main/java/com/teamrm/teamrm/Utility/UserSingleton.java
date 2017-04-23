package com.teamrm.teamrm.Utility;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrm.teamrm.Adapter.TicketListAdapter;
import com.teamrm.teamrm.Interfaces.EnrollmentCodesObservable;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.Interfaces.TicketStateObservable;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.TicketState;
import com.teamrm.teamrm.Type.Users;

import java.util.HashMap;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import static com.teamrm.teamrm.Type.TicketState.STATELISTENERTAG;


/**
 * Created by Oorya on 28/12/2016.
 */

public class UserSingleton extends Users {

    public static final String LOGINTAG = ":::LOGIN_SEQUENCE";
    public static final String TE_SEQ = ":::TechEnrollSequence";
    private static final String TAG = ":::USER_SINGLETON";

    private static Users userHolder;

    private static SectionedRecyclerViewAdapter ecAdapter;

    private static class SingletonLoader {
        private static final UserSingleton INSTANCE = new UserSingleton();
    }

    private UserSingleton() {
    }

    public static Users getInstance() {
        return SingletonLoader.INSTANCE;
    }

    public static void init(final Users user) {
        Log.d(LOGINTAG, "called init");
        userHolder = user;

        if (isUserLoaded()) {
            Log.d(TAG, "init with " + user.toString());
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    switch (getLoadedUserType()) { // check which user type is loaded into the Singleton
                        case Users.STATUS_PENDING_TECH:
                            Log.d(TE_SEQ, "Checking enrollmentCodes with companyID = " + userHolder.getAssignedCompanyID());
                            UtlFirebase.checkEnrollmentCodes(userHolder.getAssignedCompanyID(), new FireBaseBooleanCallback() {
                                @Override
                                public void booleanCallback(boolean isTrue) {
                                    if (isTrue) {
                                    Log.d(TE_SEQ, "found enrollmentCodes, attaching listener");
                                        //TODO:set application interface for Pending Technician
                                        UtlFirebase.enrollmentCodeListener(userHolder.getAssignedCompanyID(), enrollmentCodeObserver);

                                    } else {
                                        Log.d(TE_SEQ, "NOT found enrollmentCodes, signing out");
                                        //TODO: 1.NOTIFY USER THAT THE CODE WAS REMOVED
                                        //TODO: 2. ROLL BACK USER STATUS = CLIENT
                                        App.getInstance().signOut();
                                    }
                                }
                            });
                            break;

                        case Users.STATUS_ADMIN:
                            UtlFirebase.enrollmentCodeListener(userHolder.getAssignedCompanyID(), enrollmentCodeObserver);

                        case Users.STATUS_CLIENT:
                        case Users.STATUS_TECH:
                            Log.d(LOGINTAG, "::AsyncTask");
                            UtlFirebase.ticketStateListener(ticketStateObserver);
                            Log.d(LOGINTAG, "Calling getAllTicketLites");
                            UtlFirebase.getAllTicketLites(fbHelper);
                            Log.d(LOGINTAG, "Calling getAllCompanies");
                            UtlFirebase.getAllCompanies(fbHelper);
                            Log.d(LOGINTAG, "Calling getAllTickets");
                            UtlFirebase.getAllTickets(fbHelper);
                            break;

                        case "undefined":
                            Log.e(TAG, ":::UserSingleton undefined");
                            break;

                        default:
                            Log.e(TAG, ":::UserSingleton undefined");
                            break;
                    }

                    return null;
                }
            }.execute();
        } else {
            Log.d(LOGINTAG, "init with null");
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Log.e(LOGINTAG, "UserSingleton init failed!");
                new NiceToast(App.getInstance().getApplicationContext(), "Application initialization failed, logging out...\nPlease log in again", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG);
                App.getInstance().signOut();
            }
        }

    }

    public static boolean isUserLoaded() {
        return userHolder != null;
    }

    public static String getLoadedUserType() {
        if (isUserLoaded()) {
            Log.d(TAG, "userLoaded loaded class: " + userHolder.getClass().getSimpleName());
            return userHolder.getClass().getSimpleName();
        } else return "undefined";
    }

    public static Users getLoadedUserObject() {
        if (isUserLoaded()) {
            return userHolder;
        } else {
            return null;
        }
    }

    public static void refreshTicketLites() {
        UtlFirebase.getAllTicketLites(fbHelper);
    }

    private static FireBaseAble fbHelper = new FireBaseAble() {
        @Override
        public void resultTicket(Ticket ticket) {

        }

        @Override
        public void resultUser(Users user) {

        }

        @Override
        public void ticketListCallback(List<Ticket> tickets) {
            Ticket.setTicketList(tickets);
        }

        @Override
        public void ticketLiteListCallback(List<TicketLite> ticketLites) {
            TicketLite.setTicketLiteList(ticketLites);
            TicketListAdapter.setAdpterList(ticketLites);
            if (TicketListAdapter.getInstance() != null)
                TicketListAdapter.getInstance().notifyDataSetChanged();
            try {
                Log.d(TAG, TicketLite.getTicketLiteList().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void resultBoolean(boolean bool) {

        }

        @Override
        public void companyListCallback(List<Company> companies) {
            Company.setCompanyList(companies);
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
    };

    private static TicketStateObservable ticketStateObserver = new TicketStateObservable() {
        @Override
        public void onTicketAdded(TicketState ticketState) {
            TicketState.ticketStatesAddTicketState(ticketState);
            Log.d(STATELISTENERTAG, "Added state " + ticketState.toString());
        }

        @Override
        public void onTicketStateChanged(TicketState ticketState) {
            TicketState.ticketStatesUpdateTicketState(ticketState);
            Log.d(STATELISTENERTAG, "Changed state " + ticketState.toString());
            TicketFactory ticketFactory = new TicketFactory();

            try {
                ticketFactory.getNewState(UserSingleton.getInstance().getUserStatus() + "States.", ticketState.getTicketStateString() + UserSingleton.getInstance().getUserStatus(),
                        new Ticket(ticketState.getTicketID(), ticketState.getTicketStateString()));

            } catch (Exception e) {
                //test
                DatabaseReference test = FirebaseDatabase.getInstance().getReference("test");
                test.push().setValue(UserSingleton.getInstance().getUserStatus() + "States." + ticketState.getTicketStateString());
                test.push().setValue(e.getMessage() + " " + e.getLocalizedMessage());
                test.push().setValue(e.toString());
            }

        }

        @Override
        public void onTicketRemoved(TicketState ticketState) {
            TicketState.ticketStatesRemoveState(ticketState);
            Log.d(STATELISTENERTAG, "Removed state " + ticketState.toString());
        }
    };


    @Override
    public String getUserID() {
        return userHolder.getUserID();
    }


    @Override
    public String getUserNameString() {
        return userHolder.getUserNameString();
    }

    @Override
    public String getUserEmail() {
        return userHolder.getUserEmail();
    }

    @Override
    public boolean isUserIsAdmin() {
        return userHolder.isUserIsAdmin();
    }

    @Override
    public String getUserStatus() {
        return userHolder.getUserStatus();
    }

    @Override
    public String getUserPhone() {
        return userHolder.getUserPhone();
    }


    @Override
    public String getUserAddress() {
        return userHolder.getUserAddress();
    }


    @Override
    public String getUserImgPath() {
        return userHolder.getUserImgPath();
    }


    @Override
    public String getAssignedCompanyID() {
        return userHolder.getAssignedCompanyID();
    }


    public static void setECAdapter(SectionedRecyclerViewAdapter _ecAdapter) {
        ecAdapter = _ecAdapter;
        if (null != ecAdapter) {
            Log.d(TE_SEQ, "Adapter = " + ecAdapter.toString());
        }
    }

    private static EnrollmentCodesObservable enrollmentCodeObserver = new EnrollmentCodesObservable() {
        @Override
        public void onEnrollmentCodeAdded(EnrollmentCode enrollmentCode) {
            Log.d(TE_SEQ, "entered ecObserver");
            switch (getLoadedUserType()) {
                case Users.STATUS_PENDING_TECH:
                    HashMap<String, Object> update = new HashMap<>();
                    switch (enrollmentCode.getEnrollmentStatus()) {
                        case EnrollmentCode.STATUS_ACCEPTED:
                            Log.e(TE_SEQ, "PENDING_TECH: Found STATUS_ACCEPTED enrollmentCode, finalizing enrollment and setting user as Tech");
                            update.put(EnrollmentCode.ENROLLMENT_STATUS, EnrollmentCode.STATUS_FINALIZED);
                            //TODO: set userStatus = Tech
                            UtlFirebase.updateEnrollmentCode(enrollmentCode.getEnrollmentCodeID(), update);
                            App.getInstance().signOut();
                            break;

                        case EnrollmentCode.STATUS_DECLINED:
                            Log.e(TE_SEQ, "PENDING_TECH; Found STATUS_DECLINED enrollmentCode, finalizing enrollment and rolling back User to Client");
                            update.put(EnrollmentCode.ENROLLMENT_STATUS, EnrollmentCode.STATUS_FINALIZED);
                            //TODO: set userStatus = Client
                            App.getInstance().signOut();
                            break;

                        case EnrollmentCode.STATUS_ISSUED:
                        case EnrollmentCode.STATUS_CANCELLED:
                            //should be impossible
                            //TODO: notify error occurred
                            Log.e(TE_SEQ, "PENDING_TECH: Found STATUS_ISSUED or STATUS_CANCELLED enrollmentCode, shouldn't be there");
                            break;

                        case EnrollmentCode.STATUS_FINALIZED:
                            Log.d(TE_SEQ, "PENDING_TECH: Not adding to list STATUS_FINALIZED enrollmentCode");
                            //ignore
                            break;

                        case EnrollmentCode.STATUS_PENDING:
                            Log.d(TE_SEQ, "PENDING_TECH: Adding to listener STATUS_PENDING enrollmentCode");
                            EnrollmentCode.addEnrollmentCodeToList(enrollmentCode);
                            break;
                    }
                    break;

                case Users.STATUS_ADMIN:
                    switch (enrollmentCode.getEnrollmentStatus()) {

                        case EnrollmentCode.STATUS_DECLINED:
                            Log.d(TE_SEQ, "Admin: ignoring STATUS_DECLINED enrollmentCode");
                            //ignore
                            break;

                        case EnrollmentCode.STATUS_ISSUED:
                        case EnrollmentCode.STATUS_PENDING:
                        case EnrollmentCode.STATUS_ACCEPTED:
                            EnrollmentCode.addEnrollmentCodeToList(enrollmentCode);
                            Log.d(TE_SEQ, "Admin: added enrollmentCode " + enrollmentCode.getEnrollmentCodeString());
                            if (ecAdapter != null) {
                                Log.d(TE_SEQ, "notifying adapter with " + enrollmentCode.getEnrollmentCodeString());
                                ecAdapter.notifyDataSetChanged();
                            }
                            break;

                        case EnrollmentCode.STATUS_CANCELLED:
                            //TODO:notify Admin
                            UtlFirebase.removeEnrollmentCode(enrollmentCode);
                            Log.d(TE_SEQ, "List: removed  CANCELLED enrollmentCode " + enrollmentCode.toString());
                            if (ecAdapter != null) {
                                ecAdapter.notifyDataSetChanged();
                            }
                            break;

                        case EnrollmentCode.STATUS_FINALIZED:
                            //TODO:notify Admin
                            UtlFirebase.removeEnrollmentCode(enrollmentCode);
                            Log.d(TE_SEQ, "List: removed  FINALIZED enrollmentCode " + enrollmentCode.toString());
                            if (ecAdapter != null) {
                                ecAdapter.notifyDataSetChanged();
                            }
                            break;
                    }

                case Users.STATUS_CLIENT:
                case Users.STATUS_TECH:
                    //ignore
                    break;
            }
        }

        @Override
        public void onEnrollmentCodeChanged(EnrollmentCode enrollmentCode) {

        }

        @Override
        public void onEnrollmentCodeRemoved(EnrollmentCode enrollmentCode) {
            EnrollmentCode.removeEnrollmentCodeFromList(enrollmentCode);
            if (ecAdapter != null) {
                ecAdapter.notifyDataSetChanged();
            }
            if (getLoadedUserType().equals(Users.STATUS_PENDING_TECH)) {
                //TODO: notify PendingTech that the enrollmentCode was deleted
            }
        }
    };


}



