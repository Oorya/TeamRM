package com.teamrm.teamrm.Type;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;

/**
 * Created by shalty on 13/01/2017.
 */

public class WeekViewEventCustom extends WeekViewEvent {

    private String eventId;
    public WeekViewEventCustom(String eventId, long id, String description, Calendar startTime, Calendar endTime)
    {
        super(id,description,startTime,endTime);
        this.eventId = eventId;
    }
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
