package com.teamrm.teamrm.Utility;

import android.app.Application;
import android.content.Context;

/**
 * Created by Oorya on 13/12/2016.
 */

public class App extends Application {
    private GoogleApiHelper googleApiHelper;
    private static App mInstance;


    public App(Context context) {
       // super.onCreate();

        mInstance = this;
        googleApiHelper = new GoogleApiHelper(context.getApplicationContext());
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
