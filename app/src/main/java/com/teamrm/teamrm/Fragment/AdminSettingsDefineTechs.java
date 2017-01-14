package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.Adapter.TechniciansAdapter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineTechs extends Fragment {

    public RecyclerView tRecyclerView;
    TechniciansAdapter tAdapter;
    protected final ArrayList<Technician> techniciansList = new ArrayList<>();

    UUID tech1ID = new UUID(128, 64); //TODO: DEBUG - remove later
    UUID tech2ID = new UUID(128, 64); //TODO: DEBUG - remove later
    Technician tech1 = new Technician(tech1ID.toString(), "טכנאי מוסמך", "tech1@server.com", "0545555555", "רחוב שומשום 666", "cal1", "לוח שנה טכנאי 1", "#ff0000", "משמרת בוקר", "כל האזורים"); //TODO: DEBUG - remove later
    Technician tech2 = new Technician(tech2ID.toString(), "טכנאי מעולה", "tech2@server.com", "0547777777", "סמטה חשוכה 2", "cal2", "לוח שנה טכנאי 2", "#ff0000", "משמרת צהריים", "פתח תקווה"); //TODO: DEBUG - remove later

    public AdminSettingsDefineTechs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_settings_define_techs, container, false);
        techniciansList.add(tech1); //TODO: DEBUG - remove later
        techniciansList.add(tech2); //TODO: DEBUG - remove later
        tRecyclerView = (RecyclerView)view.findViewById(R.id.tRecyclerView);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tAdapter = new TechniciansAdapter(getContext(), techniciansList);
        tRecyclerView.setAdapter(tAdapter);

        return view;


    }

}
