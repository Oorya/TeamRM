package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingTechHome extends Fragment {

    public static final String FRAGMENT_TRANSACTION = "PendingTechHome";


    public PendingTechHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_tech_home, container, false);
    }

}
