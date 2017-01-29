package com.teamrm.teamrm.Type;

import android.app.PendingIntent;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by אוריה on 18/07/2016.
 */
public class Ticket {

    public static final TicketFactory TicketFactory = new TicketFactory();

    public String clientID;
    public String clientEmail;
    public String clientNameString;

    public String ticketPhone;
    public String ticketAddress;

    public String ticketID;
    public String ticketNumber;
    public String companyID;
    public String companyName;

    public String productID;
    public String productName;
    public String categoryID;
    public String categoryName;
    public String regionID;
    public String regionName;
    public String descriptionShort;
    public String descriptionLong;
    public String ticketImage1; //TODO:use Storage
    public String ticketImage2; //TODO:use Storage
    public String ticketStateString;
    public TicketStateAble ticketStateObj;

    public String ticketOpenDateTime;
    public Date ticketCloseDateTime; //TODO:change to String

    public int alarmID; //TODO:change to String
    public int alarmTechStartWorkOnTicketID; //TODO:change to String

    public String techID;

    public String techNameString;
    public long ticketCalendarID;

    public String ticketEventID;
    public Date ttl;
    public PendingIntent _alarm;
    public PendingIntent _alarmTechStartWorkOnTicket;
    public int repeatSendCounter;
    public String statusA;
    public int status;
    public boolean isTicketDone;
    public boolean isUserApprove;
    public boolean isTechDone;
    public boolean ticketIsClosed;

    public Ticket(){}  //empty constructor, must have

    public Ticket(String clientID,
                  String ticketPhone, String ticketAddress,
                  String ticketID, String companyID,
                  String productID, String categoryID, String regionID, String descriptionShort, String descriptionLong, String ticketImage1, String ticketImage2)
    {
        this.clientID = clientID;

        this.ticketPhone = ticketPhone;
        this.ticketAddress = ticketAddress;

        this.ticketID = ticketID;
        this.ticketNumber = ticketID.substring(0,8);
        this.companyID = companyID;

        this.productID = productID;
        this.categoryID = categoryID;
        this.regionID = regionID;
        this.descriptionShort = descriptionShort;
        this.descriptionLong = descriptionLong;
        this.ticketImage1 = ticketImage1;
        this.ticketImage2 = ticketImage2;
        this.ticketStateString = ProductID.STATE_A00;
        this.ticketOpenDateTime = getCurrentTime(); //TODO: change to Firebase timestamp

        this.status = 1; //TODO:???
        this.ticketCalendarID = (new Date()).getTime();
        this.ticketIsClosed = false;
        // this.ticketStateObj = TicketFactory.getNewState(UserSingleton.getInstance().getStatus(),ProductID.STATE_A00,this);
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

        if(_alarm!=null)
        {
            _alarmTechStartWorkOnTicket = alarm;
        }else
        _alarm = alarm;
    }

    public TicketStateAble getTicketStateObj() {
        return ticketStateObj;
    }

    public void setTicketStateObj(TicketStateAble ticketStateObj) {
        this.ticketStateObj = ticketStateObj;
    }

    public void changeState(String stateName, Ticket ticket)
    {
        UtlFirebase.changeState(ticket.ticketID, stateName);
        /*this.ticketStateString = stateName;

        //Log.d("FactorystateType = ", UserSingleton.getInstance().getUserStatus()==null?"null":UserSingleton.getInstance().getUserStatus());
        Log.d("FactorstateName = ", stateName);

        this.ticketStateObj = TicketFactory.getNewState("Client",stateName);
       // UtlFirebase.updateState(ticket.ticketID,"ticketStateObj",this.ticketStateObj);
        return this.ticketStateObj;*/
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        if(!(this.alarmID<=0))
        {
            this.alarmTechStartWorkOnTicketID = alarmID;
        }else
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

    private String getCurrentTime()
    {
        //Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        //get current date ticketOpenDateTime with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getTime()));
        return dateFormat.format(date);
    }

    public String toString()
    {
        String str="";
        str+="Client clientEmail: "+this.clientEmail +"\n";
        str+="Client name: "+this.clientNameString +"\n";
        str+="Product:" +this.productName +"\n";
        str+="Ticket name: "+this.descriptionShort +"\n";
        str+="Ticket description: "+this.descriptionLong +"\n";
        str+="Phone: "+this.ticketPhone +"\n";
        str+="Status: "+this.status+"\n";
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
}
