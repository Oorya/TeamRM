package com.teamrm.teamrm.Interfaces;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by shalty on 08/08/2016.
 */
public interface CalendarHelper {

    public void getResult(Event  event);
    public void getCalList(List<Event> eventUtil);
}
