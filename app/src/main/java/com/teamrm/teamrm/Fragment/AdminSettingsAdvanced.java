package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.UserSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAdvanced extends Fragment {


    public AdminSettingsAdvanced() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("CHECK:::", UserSingleton.getInstance().toString());
        return inflater.inflate(R.layout.fragment_admin_settings_advanced, container, false);
    }

}
