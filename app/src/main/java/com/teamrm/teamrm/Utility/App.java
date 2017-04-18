package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.SplashScreen;
import com.teamrm.teamrm.Broadcast.FirebaseBackgroundService;

/**
 * Created by Oorya on 13/12/2016.
 */

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;

    private Intent serviceIntent;




    @Override
    public void onCreate() {
        super.onCreate();

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
}
