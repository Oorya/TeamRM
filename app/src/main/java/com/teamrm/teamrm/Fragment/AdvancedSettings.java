package com.teamrm.teamrm.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvancedSettings extends Fragment {

    private   final String CLASS_NAME = "AdvancedSettings"; 
    
    public  String getName(){
        return CLASS_NAME;
    }

    public AdvancedSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced_settings, container, false);
    }

}
