package com.teamrm.teamrm.Interfaces;

import com.teamrm.teamrm.Type.Technician;

/**
 * Created by root on 19/02/2017.
 */

public interface TechniciansObservable {
    void onTechnicianAdded(Technician technician);
    void onTechnicianChanged(Technician technician);
    void onTechnicianRemoved(Technician technician);
}
