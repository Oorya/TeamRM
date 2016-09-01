package com.teamrm.teamrm.Enums;

/**
 * Created by Oorya on 10/08/2016.
 */
public enum UsersStatus
{
    Admin("Admin"), Technician("Technician"), Client("Client");

    private String status;

    private UsersStatus(String status)
    {
        this.status=status;
    }

    public String toString()
    {
        return status;
    }

}
