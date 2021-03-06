package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Oorya on 13/12/2016.
 */

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();
    private final String SERVER_CLIENT_ID = "840847860575-tcsdcj9r5c4o2243f3janvug66kdclrg.apps.googleusercontent.com";
    Context context;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;

    public GoogleApiHelper(Context context) {

        Log.d("splash", "GoogleApiHelper: ");

        this.context = context;
        buildGoogleApiClient();
        connect();
    }

    public GoogleApiClient getGoogleApiClient() {
        Log.d("splash", "getGoogleApiClient: ");

        return this.mGoogleApiClient;
    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    public boolean isConnected() {
        if (mGoogleApiClient != null) {
            return mGoogleApiClient.isConnected();
        } else {
            return false;
        }
    }

    public GoogleSignInOptions getGso()
    {
        Log.d("splash", "getGso: ");

        return this.gso;
    }

    private void buildGoogleApiClient() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(SERVER_CLIENT_ID)
                .build();
        GoogleApiClient.Builder apiCliBuilder = new GoogleApiClient.Builder(context);
        mGoogleApiClient = apiCliBuilder
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //You are connected do what ever you want
        //Like i get last known location
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult.toString() = " + connectionResult.toString());
    }
}
