package com.teamrm.teamrm.Interfaces;

import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.Technician;

/**
 * Created by root on 19/02/2017.
 */

public interface EnrollmentCodesObservable {
    void onEnrollmentCodeAdded(EnrollmentCode enrollmentCode);
    void onEnrollmentCodeChanged(EnrollmentCode enrollmentCode);
    void onEnrollmentCodeRemoved(EnrollmentCode enrollmentCode);
}
