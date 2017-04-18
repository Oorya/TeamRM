package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.SplashScreen;
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

import java.util.List;

import static com.teamrm.teamrm.Type.TicketState.STATELISTENERTAG;


/**
 * Created by Oorya on 28/12/2016.
 */

public class UserSingleton extends Users {

    public static final String LOGINTAG = ":::LOGIN_SEQUENCE:::";
    private static final String TAG = "USER_SINGLETON";

    private static Users userHolder;

    private static class SingletonLoader {
        private static final UserSingleton INSTANCE = new UserSingleton();
    }

    private UserSingleton() {
    }

    public static Users getInstance() {
        return SingletonLoader.INSTANCE;
    }

    public static void init(final Users user) {
        userHolder = user;

        if (isUserLoaded()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    switch (getLoadedUserType()) {
                        case Users.STATUS_PENDING_TECH:
                            UtlFirebase.checkEnrollmentCodes(userHolder.getAssignedCompanyID(), new FireBaseBooleanCallback() {
                                @Override
                                public void booleanCallback(boolean isTrue) {
                                    if (isTrue){
                                        UtlFirebase.enrollmentCodeListener(userHolder.getAssignedCompanyID(), enrollmentCodeObserver);
                                    } else {
                                        //TODO: 1.NOTIFY USER THAT THE CODE WAS REMOVED
                                        //TODO: 2. ROLL BACK USER STATUS = CLIENT
                                        //TODO: 3. LOGOUT
                                    }
                                }
                            });
                            break;

                        case Users.STATUS_ADMIN:
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
        } else if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.e(LOGINTAG, "UserSingleton init failed!");
            new NiceToast(App.getInstance().getApplicationContext(),"Application initialization failed, logging out...\nPlease log in again", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG);
            App.getInstance().signOut();
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


    private static EnrollmentCodesObservable enrollmentCodeObserver = new EnrollmentCodesObservable() {
        @Override
        public void onEnrollmentCodeAdded(EnrollmentCode enrollmentCode) {
            EnrollmentCode.addEnrollmentCodeToList(enrollmentCode);
        }

        @Override
        public void onEnrollmentCodeChanged(EnrollmentCode enrollmentCode) {

        }

        @Override
        public void onEnrollmentCodeRemoved(EnrollmentCode enrollmentCode) {

        }
    };


}



