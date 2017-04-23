package com.teamrm.teamrm.Broadcast;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Oorya on 23/04/2017.
 */

public class ServiceChecker
{
    public static boolean isServiceStarted(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) { // iterate through running services to check if the relevant service is present
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
