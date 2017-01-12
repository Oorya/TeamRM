package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.UserSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAdvanced extends Fragment implements View.OnClickListener {

    RelativeLayout userDetails, createCompany;

    public AdminSettingsAdvanced() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("CHECK:::", UserSingleton.getInstance().toString());
        View view = inflater.inflate(R.layout.fragment_admin_settings_advanced, container, false);

        setPointers(view);

        return view;
    }

    private void setPointers(View view) {
        userDetails = (RelativeLayout)view.findViewById(R.id.defineFirm);
        createCompany = (RelativeLayout)view.findViewById(R.id.defineCompany);
        userDetails.setOnClickListener(this);
        createCompany.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == userDetails.getId())
        {
            startDialogDetails();
        }
        else if (view.getId() == createCompany.getId())
        {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
            ft.replace(R.id.container_body, new FirmDetailsFrag(), null);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void startDialogDetails() {
    }
}
