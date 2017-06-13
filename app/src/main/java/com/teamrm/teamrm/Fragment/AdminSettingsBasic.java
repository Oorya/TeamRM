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
import com.teamrm.teamrm.Utility.RowViewLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsBasic extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_TRANSACTION = "AdminSettingsBasic";

    private RowViewLayout
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


        defineFirm = (RowViewLayout) view.findViewById(R.id.defineFirm);
        defineTechnicians = (RowViewLayout) view.findViewById(R.id.defineTechnicians);
        defineProducts = (RowViewLayout) view.findViewById(R.id.defineProducts);
        defineCategory = (RowViewLayout) view.findViewById(R.id.defineCategoryA);
        defineRegions = (RowViewLayout) view.findViewById(R.id.defineRegion);
        defineWorkShifts = (RowViewLayout) view.findViewById(R.id.defineWorkShift);
        appPrefs = (RowViewLayout) view.findViewById(R.id.appPrefs);

        defineFirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new ClientSettingsCreateCompany(), null);
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
                ft.replace(R.id.container_body, new AdminSettingsDefineTechs());
                ft.addToBackStack(AdminSettingsDefineTechs.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        defineProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineProducts());
                ft.addToBackStack(AdminSettingsDefineProducts.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        defineCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineCategory());
                ft.addToBackStack(AdminSettingsDefineCategory.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        defineRegions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineRegions());
                ft.addToBackStack(AdminSettingsDefineRegions.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        appPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsAppPrefs());
                ft.addToBackStack(AdminSettingsAppPrefs.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        defineWorkShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new AdminSettingsDefineWorkShifts());
                ft.addToBackStack(AdminSettingsDefineWorkShifts.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }

}
