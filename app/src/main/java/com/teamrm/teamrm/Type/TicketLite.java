package com.teamrm.teamrm.Type;

import android.media.Image;

import com.teamrm.teamrm.Interfaces.TicketStateAble;

import java.util.Date;

/**
 * Created by root on 27/01/2017.
 */

public class TicketLite {

    public String clientID;
    public String clientNameString;

    public String ticketPhone;
    public String ticketAddress;

    public String ticketID;
    public String ticketNumber;
    public String companyName;

    public String productName;
    public String categoryName;
    public String regionName;
    public String descriptionShort;
    public String descriptionLong;
    public String ticketStateString;

    public String ticketOpenDateTime;
    public Date ticketCloseDateTime; //TODO:change to String

    public String techID;
    public String techNameString;
    public Image techAvatar;
}
