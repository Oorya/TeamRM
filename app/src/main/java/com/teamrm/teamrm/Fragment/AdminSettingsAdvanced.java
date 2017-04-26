package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.RowViewLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAdvanced extends Fragment {

    RowViewLayout userDetails,
            createCompany,
            appPrefs;

    public AdminSettingsAdvanced() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.d("CHECK:::", UserSingleton.getInstance().toString());
        View view = inflater.inflate(R.layout.fragment_admin_settings_advanced, container, false);

        setPointers(view);

        return view;
    }

    private void setPointers(View view) {
        userDetails = (RowViewLayout) view.findViewById(R.id.defineFirm);
        createCompany = (RowViewLayout) view.findViewById(R.id.defineCompany);
        appPrefs = (RowViewLayout) view.findViewById(R.id.appPrefs);

        userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogDetails();
            }
        });

        createCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new ClientSettingsCreateCompany(), null);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        appPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsAppPrefs(), null);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void startDialogDetails() {

    }
}
