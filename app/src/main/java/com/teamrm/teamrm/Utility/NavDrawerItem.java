package com.teamrm.teamrm.Utility;

import android.util.Log;

/**
 * Created by shalty on 17/09/2016.
 */
public class NavDrawerItem {

    private boolean showNotify;
    private String title;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        Log.w("title", title  +"  ^^  title get");
        return title;
    }

    public void setTitle(String title) {
        Log.w("title", title  +"  ^^  title set");
        this.title = title;
    }
}
