package com.teamrm.teamrm.Interfaces;

import com.google.api.services.calendar.model.Event;

import java.util.List;

/**
 * Created by shalty on 08/08/2016.
 */
public interface CalendarHelper {

    void getResult(Event  event);
    void getEventList(List<Event> eventUtil);
}
