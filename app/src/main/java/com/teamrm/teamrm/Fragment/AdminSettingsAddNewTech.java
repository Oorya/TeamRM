package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.RowSetLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAddNewTech extends Fragment {

    public AdminSettingsAddNewTech() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_settings_add_new_tech, container, false);

        RowSetLayout rowSet1 = (RowSetLayout) view.findViewById(R.id.rowSet1);
        rowSet1.alternateRowsBackground(R.color.listRow_alt, RowSetLayout.ALTER_ODD_ROWS);


    return view;
    }

}
