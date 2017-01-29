package com.teamrm.teamrm.Type;

import android.media.Image;

import com.teamrm.teamrm.Interfaces.TicketStateAble;

import java.util.Date;

/**
 * Created by root on 27/01/2017.
 */

public class TicketLite {

    public String clientNameString;

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

    public Date ticketOpenDateTime;
    public Date ticketCloseDateTime;

    public String techNameString;
    public Image techAvatar;

    public int ticketPresentation;

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

    public Date getTicketOpenDateTime() {
        return ticketOpenDateTime;
    }

    public void setTicketOpenDateTime(Date ticketOpenDateTime) {
        this.ticketOpenDateTime = ticketOpenDateTime;
    }

    public Date getTicketCloseDateTime() {
        return ticketCloseDateTime;
    }

    public void setTicketCloseDateTime(Date ticketCloseDateTime) {
        this.ticketCloseDateTime = ticketCloseDateTime;
    }

    public String getTechNameString() {
        return techNameString;
    }

    public void setTechNameString(String techNameString) {
        this.techNameString = techNameString;
    }

    public Image getTechAvatar() {
        return techAvatar;
    }

    public void setTechAvatar(Image techAvatar) {
        this.techAvatar = techAvatar;
    }

    public int getTicketPresentation() {
        return ticketPresentation;
    }

    public void setTicketPresentation(int ticketPresentation) {
        this.ticketPresentation = ticketPresentation;
    }
}
