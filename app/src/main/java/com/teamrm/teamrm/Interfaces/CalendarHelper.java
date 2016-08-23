package com.teamrm.teamrm.Interfaces;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;

import java.util.List;

/**
 * Created by shalty on 08/08/2016.
 */
public interface CalendarHelper {

    public void getResolt(List<Event> eventUtil);
    public void getCalLst(List<CalendarListEntry> eventUtil);
}
