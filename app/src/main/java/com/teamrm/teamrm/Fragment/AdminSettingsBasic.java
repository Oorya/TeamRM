package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teamrm.teamrm.Interfaces.FragmentHelper;
import com.teamrm.teamrm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsBasic extends Fragment implements View.OnClickListener {

    private RelativeLayout
            defineFirm,
            defineTechnicians,
            defineProducts,
            defineCategory,
            defineRegions,
            defineWorkShifts,
            appPrefs;

    public AdminSettingsBasic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_settings_basic, container, false);


        defineFirm = (RelativeLayout) view.findViewById(R.id.defineFirm);
        defineTechnicians = (RelativeLayout) view.findViewById(R.id.defineTechnicians);
        defineProducts = (RelativeLayout) view.findViewById(R.id.defineProducts);
        defineCategory = (RelativeLayout) view.findViewById(R.id.defineCategoryA);
        defineRegions = (RelativeLayout) view.findViewById(R.id.defineRegion);
        defineWorkShifts = (RelativeLayout) view.findViewById(R.id.defineWorkShift);
        appPrefs = (RelativeLayout) view.findViewById(R.id.appPrefs);

        defineFirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new FirmDetailsFrag(), null);
                ft.addToBackStack("stackSettingsBasic");
                ft.commit();*/
                Toast.makeText(getContext(), "הגדרות חברה", Toast.LENGTH_SHORT).show();
            }
        });
        defineTechnicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineTechs(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        defineProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineProducts(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        defineCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineCategory(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        defineRegions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineRegions(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        appPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsAppPrefs(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        defineWorkShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineWorkShifts(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_BASIC_SETTINGS_NAVIGATION);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }

}
