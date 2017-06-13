package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.teamrm.teamrm.Adapter.EnrollmentCodeSection;
import com.teamrm.teamrm.Adapter.TechniciansSection;
import com.teamrm.teamrm.Interfaces.FragmentHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineTechs extends Fragment {


    public RecyclerView tRecyclerView;
    SectionedRecyclerViewAdapter tAdapter;
    private FloatingActionButton floatBtn;
    public static final String FRAGMENT_TRANSACTION = "AdminSettingsDefineTechs";

    public AdminSettingsDefineTechs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);
        getActivity().setTitle(getResources().getStringArray(R.array.setting_title_advance)[1]);
        tAdapter = new SectionedRecyclerViewAdapter();

        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();


        tRecyclerView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final EnrollmentCodeSection ecSection = new EnrollmentCodeSection(getContext());
        final TechniciansSection techSection = new TechniciansSection(getContext());

        tAdapter.addSection(ecSection);
        tAdapter.addSection(techSection);

        tRecyclerView.setAdapter(tAdapter);
        tAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                String sss = Integer.toString(ecSection.getContentItemsTotal());
                String ttt = Integer.toString(ecSection.getSectionItemsTotal());
                String lf = "\n";
                try {
                    Log.d(UserSingleton.TE_SEQ, sss + lf + ttt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (EnrollmentCode.getEnrollmentCodeList().isEmpty() || ecSection.getContentItemsTotal()<1) {
                    ecSection.setVisible(false);
                } else ecSection.setVisible(true);

            }
        });



        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 250);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addTech();
                UtlFirebase.addEnrollmentCode();
            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        UserSingleton.setECAdapter(tAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        UserSingleton.setECAdapter(null);
    }

    void addTech() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
        ft.replace(R.id.container_body, new AdminSettingsAddNewTech(), null);
        ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
        ft.commit();
    }
}
