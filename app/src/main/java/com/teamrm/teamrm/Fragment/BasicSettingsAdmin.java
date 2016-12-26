package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.teamrm.teamrm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicSettingsAdmin extends Fragment implements View.OnClickListener {

    private RelativeLayout
                    defineFirm,
                    defineTechnicians,
                    defineProducts,
                    defineCategoryA,
                    defineCategoryB,
                    defineRegion,
                    appSettings;

    public BasicSettingsAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_settings_admin, container, false);



        defineFirm = (RelativeLayout)view.findViewById(R.id.defineFirm);
        defineTechnicians = (RelativeLayout)view.findViewById(R.id.defineTechnicians);
        defineProducts = (RelativeLayout)view.findViewById(R.id.defineProducts);
        defineCategoryA = (RelativeLayout)view.findViewById(R.id.defineCategoryA);
        defineCategoryB = (RelativeLayout)view.findViewById(R.id.defineCategoryB);
        defineRegion = (RelativeLayout)view.findViewById(R.id.defineRegion);
        appSettings = (RelativeLayout)view.findViewById(R.id.appSettings);

        defineFirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "::: clicked defineFirm :::", Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new FirmDetailsFrag(), null);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        defineTechnicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        defineProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        defineCategoryA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        defineCategoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        defineRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        appSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    return view;
    }

    @Override
    public void onClick(View view) {

    }
}
