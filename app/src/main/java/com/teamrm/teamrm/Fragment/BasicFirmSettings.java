package com.teamrm.teamrm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasicFirmSettings extends Fragment {
    private FragmentTabHost settingsTabHost;

    public BasicFirmSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingsTabHost = new FragmentTabHost(getActivity());
        settingsTabHost.clearAllTabs();

        settingsTabHost.setup(getActivity(), getChildFragmentManager());

        settingsTabHost.addTab(settingsTabHost.newTabSpec("firmSettings").setIndicator("הגדרות חברה"), Setting.class, null);

        return settingsTabHost;
    }

}
