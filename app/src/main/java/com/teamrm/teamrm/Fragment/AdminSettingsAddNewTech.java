package com.teamrm.teamrm.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.RowViewLayout;

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
        rowSet1.AlternateRowsBackground(rowSet1, R.color.listRow_alt, RowSetLayout.ALTER_ODD_ROWS);


    return view;
    }

}
