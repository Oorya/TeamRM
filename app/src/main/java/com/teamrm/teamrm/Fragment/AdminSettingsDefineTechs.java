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
import com.teamrm.teamrm.Interfaces.EnrollmentCodeCallback;
import com.teamrm.teamrm.Interfaces.FragmentHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.UUID;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineTechs extends Fragment {

    public RecyclerView tRecyclerView;
    SectionedRecyclerViewAdapter tAdapter;
    private FloatingActionButton floatBtn;


    public AdminSettingsDefineTechs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);

        tAdapter = new SectionedRecyclerViewAdapter();

        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();


        tRecyclerView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //final EnrollmentCodeSection ecSection = new EnrollmentCodeSection(getContext(), EnrollmentCode.enrollmentCodeList);
        final EnrollmentCodeSection ecSection = new EnrollmentCodeSection(getContext());
        //final TechniciansSection techSection = new TechniciansSection(getContext(), Technician.getTechnicianList());

        tAdapter.addSection(ecSection);
        ecSection.setVisible(false);
        //tAdapter.addSection(techSection);

        //ecSection.setVisible(false);
        //ecSection.setState(Section.State.LOADING);
        //techSection.setVisible(false);

        UtlFirebase.getEnrollmentCodesForEdit(UserSingleton.getInstance().getAssignedCompanyID(), new EnrollmentCodeCallback() {
            @Override
            public void enrollmentCodeCallback(ArrayList<EnrollmentCode> enrollmentCodes) {
                Log.d(":::ec callback", "BOOM");
                if (!enrollmentCodes.isEmpty()) {
                    EnrollmentCode.enrollmentCodeList = enrollmentCodes;
                    ecSection.setVisible(true);
                    tAdapter.notifyDataSetChanged();
               /* if (!enrollmentCodes.isEmpty()) {
                    //ecSection.setVisible(true);
                }*/
                } else {
                    tAdapter.notifyDataSetChanged();
                    ecSection.setVisible(false);
                }
            }
        });

       /* UtlFirebase.getCompanyTechniciansForEdit(UserSingleton.getInstance().getAssignedCompanyID(), new TechnicianCallback() {
            @Override
            public void technicianCallback(ArrayList<Technician> technicianList) {
                Log.d(":::tech callback", "BOOM");
                Technician.setTechnicianList(technicianList);
                if (!technicianList.isEmpty()) {
                    techSection.setVisible(true);
                }
            }
        });*/


        tRecyclerView.setAdapter(tAdapter);

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
                UtlFirebase.addEnrollmentCode(new EnrollmentCode(UserSingleton.getInstance().getAssignedCompanyID(), UUID.randomUUID().toString().substring(0, 7).toUpperCase()));
            }
        });
        return view;

    }

    void addTech() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
        ft.replace(R.id.container_body, new AdminSettingsAddNewTech(), null);
        ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
        ft.commit();
    }

}
