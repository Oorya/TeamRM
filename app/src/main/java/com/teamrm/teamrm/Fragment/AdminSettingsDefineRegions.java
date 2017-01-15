package com.teamrm.teamrm.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.teamrm.teamrm.Adapter.CategoryAdapter;
import com.teamrm.teamrm.Adapter.RegionAdapter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineRegions extends Fragment {

    final static String TAG = ":::Settings:Category:::";
    public RecyclerView regionView;
    protected List<Region> regionList = new ArrayList<>();
    RegionAdapter regionAdapter;
    FloatingActionButton floatBtn;

    public AdminSettingsDefineRegions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);
        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();

        /*regionList = UtlFirebase.getCategories(UserSingleton.getInstance().getUserCompany()); //TODO:fix this
        Log.d(TAG, "initDone true, list="+regionList.toString());*/

        regionList.add(new Region("100", "צפון"));
        regionList.add(new Region("101", "מרכז"));
        regionList.add(new Region("102", "דרום"));
        regionList.add(new Region("103", "חו\"ל"));



        regionView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        regionView.setLayoutManager(new LinearLayoutManager(getContext()));
        regionAdapter = new RegionAdapter(getContext(), regionList);
        regionView.setAdapter(regionAdapter);
        regionView.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 250);

        return view;


    }

}
