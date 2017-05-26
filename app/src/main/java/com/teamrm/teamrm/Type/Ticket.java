package com.teamrm.teamrm.Type;

import android.app.PendingIntent;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by אוריה on 18/07/2016.
 */
public class Ticket {

    @Exclude public static final TicketFactory TicketFactory = new TicketFactory();

    @Exclude private static List<Ticket> ticketList = new ArrayList<>();

    private String clientID;
    private String clientEmail;
    private String clientNameString;

    private String ticketPhone;
    private String ticketAddress;

    private String ticketID;
    private String ticketNumber;
    private String companyID;
    private String companyName;

    private String productID;
    private String productName;
    private String categoryID;
    private String categoryName;
    private String regionID;
    private String regionName;
    private String descriptionShort;
    private String descriptionLong;
    @Exclude
    private String ticketImage1; //TODO:use Storage
    @Exclude
    private String ticketImage2; //TODO:use Storage
    private String ticketStateString;

    @Exclude
    private TicketStateAble ticketStateObj;

    private String ticketOpenDateTime;
    private String ticketAssignedDateTime;
    private String ticketAssignedDuration;
    private String ticketCloseDateTime;

    private int alarmID; //TODO:change to String
    private int alarmTechStartWorkOnTicketID; //TODO:change to String

    private String techID;
    private String techNameString;
    private String techColor;

    private long ticketCalendarID;

    private String ticketEventID;
    private Date ttl;
    @Exclude
    private PendingIntent _alarm;
    @Exclude
    private PendingIntent _alarmTechStartWorkOnTicket;
    private int repeatSendCounter;
    private String statusA;
    private int ticketPresentation;
    private boolean isTicketDone;
    private boolean isUserApprove;
    private boolean isTechDone;
    private boolean ticketIsClosed;

    public static final String CLIENT_ID = "clientID";
    public static final String CLIENT_EMAIL = "clientEmail";
    public static final String CLIENT_NAME_STRING = "clientNameString";
    public static final String COMPANY_ID = "companyID";
    public static final String COMPANY_NAME = "companyName";
    public static final String TICKET_STATE_STRING = "ticketStateString";
    public static final String TECH_ID = "techID";
    public static final String TECH_NAME_STRING = "techNameString";
    public static final String TICKET_PRESENTATION = "ticketPresentation";

    public static void clearList()
    {
        ticketList.clear();
    }
    public Ticket() {}  //empty constructor, must have

    public Ticket(String ticketID, String ticketStateString)
    {
        this.ticketID=ticketID;
        this.ticketStateString=ticketStateString;
    }

    public Ticket(String clientID,
                  String ticketPhone, String ticketAddress,
                  String ticketID, String companyID, String companyName,
                  Product product, Category category, Region region, String descriptionShort, String descriptionLong, String ticketImage1, String ticketImage2, String startTime,
                  String techID, String techNameString) {
        this.clientID = clientID;
        this.clientEmail = UserSingleton.getInstance().getUserEmail();
        this.clientNameString = UserSingleton.getInstance().getUserNameString();

        this.ticketPhone = ticketPhone;
        this.ticketAddress = ticketAddress;

        this.ticketID = ticketID;
        this.ticketNumber = ticketID.substring(0, 8);
        this.companyID = companyID;
        this.companyName = companyName;

        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.categoryID = category.getCategoryID();
        this.categoryName = category.getCategoryName();
        this.regionID = region.getRegionID();
        this.regionName = region.getRegionName();
        this.descriptionShort = descriptionShort;
        this.descriptionLong = descriptionLong;
        this.ticketImage1 = ticketImage1;
        this.ticketImage2 = ticketImage2;
        this.ticketStateString = TicketStateStringable.STATE_A00;
        this.ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_URGENT;
        this.ticketOpenDateTime = startTime==null?getCurrentTime():startTime;

        this.ticketAssignedDateTime = "";
        this.ticketAssignedDuration = "";

        this.ticketCloseDateTime = "";
        this.isTicketDone = false;
        this.ticketIsClosed = false;
        this.techID = "";
        this.techNameString = "";
        // this.ticketStateObj = TicketFactory.getNewState(UserSingleton.getInstance().getStatus(),TicketStateStringable.STATE_A00,this);
    }
    public void setTicketCloseDateTime(String ticketCloseDateTime) {
        this.ticketCloseDateTime = ticketCloseDateTime;
    }

    public long getTicketCalendarID() {
        return ticketCalendarID;
    }

    public PendingIntent get_alarm() {
        return _alarm;
    }

    public PendingIntent get_alarmTechStartWorkOnTicket() {
        return _alarmTechStartWorkOnTicket;
    }

    public void setAlarm(PendingIntent alarm) {

        if (_alarm != null) {
            _alarmTechStartWorkOnTicket = alarm;
        } else
            _alarm = alarm;
    }

    public TicketStateAble getTicketStateObj() {
        return ticketStateObj;
    }

    public void setTicketStateObj(TicketStateAble ticketStateObj) {
        this.ticketStateObj = ticketStateObj;
    }

    public void updateTicketStateString(String stateName, Ticket ticket) {
        UtlFirebase.updateTicketStateString(ticket, stateName);
        int ticketPresentation = 0;
        switch (stateName) {
            case TicketStateStringable.STATE_A00:
            case TicketStateStringable.STATE_A01:
            case TicketStateStringable.STATE_A02CN:
                ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_URGENT;
                break;

            case TicketStateStringable.STATE_A03:
            case TicketStateStringable.STATE_B01:
                ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_PENDING;
                break;

            case TicketStateStringable.STATE_B02:
            case TicketStateStringable.STATE_B03:
            case TicketStateStringable.STATE_C01:
            case TicketStateStringable.STATE_C02:
                ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_OK;
                break;

            case TicketStateStringable.STATE_E01:
            case TicketStateStringable.STATE_E02:
            case TicketStateStringable.STATE_E03:
            case TicketStateStringable.STATE_E04:
            case TicketStateStringable.STATE_E05:
            case TicketStateStringable.STATE_E06:
            case TicketStateStringable.STATE_E07:
                ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_ERROR;
                break;

            case TicketStateStringable.STATE_E00:
            case TicketStateStringable.STATE_Z00:
                ticketPresentation = TicketStateAble.TICKET_LIST_PRESENTATION_CLOSED;
                break;
        }
        UtlFirebase.updateTicketPresentation(ticket.ticketID, ticketPresentation);
    }

    @Exclude public Integer getUrgency() {
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
                urgency = 99;
                break;

            default:
                urgency = 0;
        }
        return urgency;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        if (!(this.alarmID <= 0)) {
            this.alarmTechStartWorkOnTicketID = alarmID;
        } else
            this.alarmID = alarmID;
    }

    public boolean getTicketDone() {
        return isTicketDone;
    }

    public void setIsTicketDone(boolean isTicketDone) {
        this.isTicketDone = isTicketDone;
    }

    public boolean isUserApprove() {
        return isUserApprove;
    }

    public void setUserApprove(boolean userApprove) {
        isUserApprove = userApprove;
    }


    public boolean getTechDone() {
        return isTechDone;
    }

    public void setTechDone(boolean techDone) {
        isTechDone = techDone;
    }

    private String getCurrentTime() {
        //Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        //get current date ticketOpenDateTime with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getCompanyCreationTime()));
        return dateFormat.format(date);
    }

    public String toString() {
        String str = "";
        str += "Client clientEmail: " + this.clientEmail + "\n";
        str += "Client name: " + this.clientNameString + "\n";
        str += "Product:" + this.productName + "\n";
        str += "Ticket name: " + this.descriptionShort + "\n";
        str += "Ticket description: " + this.descriptionLong + "\n";
        str += "Phone: " + this.ticketPhone + "\n";
        str += "Status: " + this.ticketPresentation + "\n";
        return str;
    }

    public int getRepeatSendCounter() {
        return repeatSendCounter;
    }

    public void incCounter() {

        //מתודה שמעדקנת את השדה repeatSendCounter בפיירבייס
        this.repeatSendCounter += repeatSendCounter;
    }

    public void incInitialization() {

        //מתודה שמעדקנת את השדה repeatSendCounter בפיירבייס
        this.repeatSendCounter = 0;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getTicketID() {
        return ticketID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientNameString() {
        return clientNameString;
    }

    public String getTicketPhone() {
        return ticketPhone;
    }

    public String getTicketAddress() {
        return ticketAddress;
    }

    public String getCompanyID() {
        return companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getRegionID() {
        return regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public String getTicketImage1() {
        return ticketImage1;
    }

    public String getTicketImage2() {
        return ticketImage2;
    }

    public String getTicketStateString() {
        return ticketStateString;
    }

    public String getTicketOpenDateTime() {
        return ticketOpenDateTime;
    }

    public String getTicketCloseDateTime() {
        return ticketCloseDateTime;
    }

    public int getAlarmTechStartWorkOnTicketID() {
        return alarmTechStartWorkOnTicketID;
    }

    public String getTechID() {
        return techID;
    }

    public String getTechNameString() {
        return techNameString;
    }

    public String getTicketEventID() {
        return ticketEventID;
    }

    public Date getTtl() {
        return ttl;
    }

    public String getStatusA() {
        return statusA;
    }

    public int getTicketPresentation() {
        return ticketPresentation;
    }

    public boolean isTicketIsClosed() {
        return ticketIsClosed;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setClientNameString(String clientNameString) {
        this.clientNameString = clientNameString;
    }

    public void setTicketPhone(String ticketPhone) {
        this.ticketPhone = ticketPhone;
    }

    public void setTicketAddress(String ticketAddress) {
        this.ticketAddress = ticketAddress;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public void setTicketImage1(String ticketImage1) {
        this.ticketImage1 = ticketImage1;
    }

    public void setTicketImage2(String ticketImage2) {
        this.ticketImage2 = ticketImage2;
    }

    public void setTicketStateString(String ticketStateString) {
        this.ticketStateString = ticketStateString;
    }

    public void setTicketOpenDateTime(String ticketOpenDateTime) {
        this.ticketOpenDateTime = ticketOpenDateTime;
    }

    public void setAlarmTechStartWorkOnTicketID(int alarmTechStartWorkOnTicketID) {
        this.alarmTechStartWorkOnTicketID = alarmTechStartWorkOnTicketID;
    }

    public void setTechID(String techID) {
        this.techID = techID;
    }

    public void setTechNameString(String techNameString) {
        this.techNameString = techNameString;
    }

    public void setTechColor(String techColor) {
        this.techColor = techColor;
    }

    public void setTicketCalendarID(long ticketCalendarID) {
        this.ticketCalendarID = ticketCalendarID;
    }

    public void setTicketEventID(String ticketEventID) {
        this.ticketEventID = ticketEventID;
    }

    public String getTechColor() {
        return techColor;
    }

    public void setTicketPresentation(int ticketPresentation) {
        this.ticketPresentation = ticketPresentation;
    }

    public void setTicketDone(boolean ticketDone) {
        isTicketDone = ticketDone;
    }

    public void setTicketIsClosed(boolean ticketIsClosed) {
        this.ticketIsClosed = ticketIsClosed;
    }

    public static List<Ticket> getTicketList() {
        return ticketList;
    }

    public static void setTicketList(List<Ticket> ticketList) {
        Ticket.ticketList = ticketList;
    }

    public static Ticket getTickeyById(String ticketId) {

        for (Ticket item:ticketList)
        {
            if(item.getTicketID().equals(ticketId))
            {
                return item;
            }

        }

        return null;

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

