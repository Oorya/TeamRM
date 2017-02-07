package com.teamrm.teamrm.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;

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





    return view;
    }

}
