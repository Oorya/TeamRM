package com.teamrm.teamrm.Interfaces;

import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.WorkShift;

import java.util.ArrayList;

/**
 * Created by r00t on 17/02/2017.
 */

public interface EnrollmentCodeCallback {

    void enrollmentCodeCallback(ArrayList<EnrollmentCode> enrollmentCodes);
}
