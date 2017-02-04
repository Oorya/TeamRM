package com.teamrm.teamrm.Utility;

import android.app.Application;

/**
 * Created by Oorya on 13/12/2016.
 */

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        googleApiHelper = new GoogleApiHelper(getApplicationContext());
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
}
