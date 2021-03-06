package com.teamrm.teamrm.Utility;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.Activities.SplashScreen;
import com.teamrm.teamrm.Broadcast.FirebaseBackgroundService;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Technician;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Oorya on 13/12/2016.
 */

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;
    private Handler handler;
    private Intent serviceIntent;
    private boolean isHandlerRun = true;



    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        mInstance = this;

        googleApiHelper = new GoogleApiHelper(getApplicationContext());

        serviceIntent = new Intent(this, FirebaseBackgroundService.class);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public GoogleApiHelper getGoogleApiHelperInstance() {
        return this.googleApiHelper;
    }
    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

    public void signOut() {
        this.stopService(serviceIntent);

        UtlFirebase.removeActiveListeners();

        FirebaseAuth.getInstance().signOut();

        GoogleApiClient mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //Toast.makeText(context,"logout OK home",Toast.LENGTH_LONG).show();
                        Ticket.clearList();
                        TicketLite.clearList();
                        EnrollmentCode.clearEnrollmentCodeList();
                        Technician.clearTechnicianList();
                        Company.clearCompanyList();

                        SplashScreen.resume = true;
                    }
                });
        UserSingleton.init(null);
    }

    public void startService(){

        this.startService(serviceIntent);
    }

    public void stopService(){
        this.stopService(serviceIntent);
    }
    public void startLastSeenRunnable(){
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(UserSingleton.isUserLoaded() && InternetAvailable.isConnectingToInternet(getApplicationContext())) {
                    Calendar calendar = Calendar.getInstance();
                    String LastSeen = calendar.toString();
                    UserSingleton.getInstance().setUserLastSeen(LastSeen);

                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put(UserSingleton.getInstance().USER_LAST_SEEN,LastSeen );

                    UtlFirebase.updateUser(UserSingleton.getInstance().getUserID(),updates);

                    startLastSeenRunnable();

                }
            }
        }, 60000);



    }

}
