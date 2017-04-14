package com.teamrm.teamrm.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by r00t on 08/03/2017.
 */

public class WorkShift {


    private static List<WorkShift> workShiftList = Collections.EMPTY_LIST;

    private String workShiftID;
    private String workShiftName;
    private String workShiftStart;
    private String workShiftEnd;

    public WorkShift(){};

    public WorkShift(String shiftName, String shiftStart, String shiftEnd) {
        this.workShiftName = shiftName;
        this.workShiftStart = shiftStart;
        this.workShiftEnd = shiftEnd;
    }

    public WorkShift(WorkShift workShift){
        this.workShiftID = workShift.getWorkShiftID();
        this.workShiftName = workShift.getWorkShiftName();
        this.workShiftStart = workShift.getWorkShiftStart();
        this.workShiftEnd = workShift.getWorkShiftEnd();
    }

    public String getWorkShiftID() {
        return workShiftID;
    }

    public void setWorkShiftID(String workShiftID) {
        this.workShiftID = workShiftID;
    }

    public String getWorkShiftName() {
        return workShiftName;
    }

    public void setWorkShiftName(String workShiftName) {
        this.workShiftName = workShiftName;
    }

    public String getWorkShiftStart() {
        return workShiftStart;
    }

    public void setWorkShiftStart(String workShiftStart) {
        this.workShiftStart = workShiftStart;
    }

    public String getWorkShiftEnd() {
        return workShiftEnd;
    }

    public void setWorkShiftEnd(String workShiftEnd) {
        this.workShiftEnd = workShiftEnd;
    }
}
