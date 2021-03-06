package com.teamrm.teamrm.Type;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 27/01/2017.
 */

public class TicketLite implements Serializable{

    private static List<TicketLite> ticketLiteList = new ArrayList<>();

    private String clientID;
    private String clientNameString;

    private String ticketAddress;

    private String ticketID;
    private String ticketNumber;
    private String companyID;
    private String companyName;

    private String productName;
    private String categoryName;
    private String regionName;
    private String descriptionShort;
    private String descriptionLong;
    private String ticketStateString;

    private String ticketOpenDateTime;
    private String ticketAssignedDateTime;
    private String ticketAssignedDuration;
    private String ticketCloseDateTime;

    private String techID;
    private String techNameString;
    private String techColor;

    //private Image techAvatar;

    private int ticketPresentation;

    public static void clearList()
    {
        ticketLiteList.clear();
    }

    public static List<TicketLite> getTicketLiteList() {
        return ticketLiteList;
    }

    public static void setTicketLiteList(List<TicketLite> ticketLiteList) {
        TicketLite.ticketLiteList = ticketLiteList;
    }


    public TicketLite() {
    }

    public TicketLite(Ticket ticket) {
        this.clientID = ticket.getClientID();
        this.clientNameString = ticket.getClientNameString();

        this.ticketAddress = ticket.getTicketAddress();

        this.ticketID = ticket.getTicketID();
        this.ticketNumber = ticket.getTicketNumber();
        this.companyID=ticket.getCompanyID();
        this.companyName = ticket.getCompanyName();

        this.productName = ticket.getProductName();
        this.categoryName = ticket.getCategoryName();
        this.regionName = ticket.getRegionName();
        this.descriptionShort = ticket.getDescriptionShort();
        this.descriptionLong = ticket.getDescriptionLong();
        this.ticketStateString = ticket.getTicketStateString();

        this.ticketOpenDateTime = ticket.getTicketOpenDateTime();
        this.ticketAssignedDateTime = ticket.getTicketAssignedDateTime();
        this.ticketAssignedDuration = ticket.getTicketAssignedDuration();
        this.ticketCloseDateTime = ticket.getTicketCloseDateTime();

        this.techID = ticket.getTechID();
        this.techNameString = ticket.getTechNameString();
        this.techColor = ticket.getTechColor();
        //this.techAvatar=ticket.

        this.ticketPresentation = ticket.getTicketPresentation();
    }

    @Exclude
    public Integer getUrgency() {
        Integer urgency;

        switch (this.ticketStateString) {
            case TicketStateStringable.STATE_A00:
            case TicketStateStringable.STATE_A01:
            case TicketStateStringable.STATE_E03:
            case TicketStateStringable.STATE_E05:
            case TicketStateStringable.STATE_E07:
                urgency = 0;
                break;

            case TicketStateStringable.STATE_E06:
            case TicketStateStringable.STATE_E02:
            case TicketStateStringable.STATE_A02CN:
                urgency = 1;
                break;

            case TicketStateStringable.STATE_E04:
                urgency = 2;
                break;

            case TicketStateStringable.STATE_B01:
            case TicketStateStringable.STATE_A03:
            case TicketStateStringable.STATE_B02:
            case TicketStateStringable.STATE_B03:
            case TicketStateStringable.STATE_C01:
            case TicketStateStringable.STATE_C02:
                urgency = 3;
                break;

            case TicketStateStringable.STATE_E00:
                urgency = 4;
                break;

            case TicketStateStringable.STATE_E01:
            case TicketStateStringable.STATE_Z00:
                urgency = 5;
                break;

            default:
                urgency = 0;
        }
        return urgency;
    }

    public String getClientNameString() {
        return clientNameString;
    }

    public void setClientNameString(String clientNameString) {
        this.clientNameString = clientNameString;
    }

    public String getTicketAddress() {
        return ticketAddress;
    }

    public void setTicketAddress(String ticketAddress) {
        this.ticketAddress = ticketAddress;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getTicketStateString() {
        return ticketStateString;
    }

    public void setTicketStateString(String ticketStateString) {
        this.ticketStateString = ticketStateString;
    }

    public String getTicketOpenDateTime() {

        return ticketOpenDateTime;
    }

    public void setTicketOpenDateTime(String ticketOpenDateTime) {
        this.ticketOpenDateTime = ticketOpenDateTime;
    }

    public String getTicketCloseDateTime() {
        return ticketCloseDateTime;
    }

    public void setTicketCloseDateTime(String ticketCloseDateTime) {
        this.ticketCloseDateTime = ticketCloseDateTime;
    }

    public String getTechNameString() {
        return techNameString;
    }

    public void setTechNameString(String techNameString) {
        this.techNameString = techNameString;
    }

    public int getTicketPresentation() {
        return ticketPresentation;
    }

    public void setTicketPresentation(int ticketPresentation) {
        this.ticketPresentation = ticketPresentation;
    }

    public String getTechColor() {
        return techColor;
    }

    public void setTechColor(String techColor) {
        this.techColor = techColor;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getTechID() {
        return techID;
    }

    public void setTechID(String techID) {
        this.techID = techID;
    }

    public String getTicketAssignedDateTime() {
        return ticketAssignedDateTime;
    }

    public void setTicketAssignedDateTime(String ticketAssignedDateTime) {
        this.ticketAssignedDateTime = ticketAssignedDateTime;
    }

    public String getTicketAssignedDuration() {
        return ticketAssignedDuration;
    }

    public void setTicketAssignedDuration(String ticketAssignedDuration) {
        this.ticketAssignedDuration = ticketAssignedDuration;
    }

    @Override
    public int hashCode() {
        return this.ticketID.hashCode();
    }
}
