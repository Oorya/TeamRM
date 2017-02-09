package com.teamrm.teamrm.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Interfaces.CalendarHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.CalendarUtil;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAppPrefs extends Fragment implements CalendarHelper{

    Context context;
    Button button,delitCal;
    CalendarUtil calendarUtil;
    String calId;

    public AdminSettingsAppPrefs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_admin_settings_app_prefs, container, false);

        return V;

    }

    @Override
    public void getResult(Event event) {

    }

    @Override
    public void getEventList(List<Event> eventUtil) {

    }

    @Override
    public void getCalendar(Calendar calendar) {

        calId = calendar.getId();
    }
}
