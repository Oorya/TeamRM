package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

/**
 * Created by r00t on 26/05/2017.
 */

public class LinearLayoutManagerWithSmoothScroll extends LinearLayoutManager {

    private Context sContext;

    public LinearLayoutManagerWithSmoothScroll(Context context){
        super(context);
        sContext = context;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller scroller = new LinearSmoothScroller(sContext) {
        };
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }
}
