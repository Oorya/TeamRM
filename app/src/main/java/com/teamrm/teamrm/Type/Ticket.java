package com.teamrm.teamrm.Type;

import android.app.PendingIntent;
import android.util.Log;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStatus;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Utility.UserSingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by אוריה on 18/07/2016.
 */
public class Ticket {

    public static final TicketFactory TicketFactory = new TicketFactory();

    public String clientName;
    public String email;
    public String tech;
    public String product;
    public String classification;
    public String desShort;
    public String desLong; //Description
    public String area;
    public String address;
    public String ticketImage1;
    public String ticketImage2;
    public String phone;
    public String ticketId;
    public String state;
    public String startTime;
    public TicketStateAble stateObj;
    public Date endTime;
    public Date ttl;
    public int alarmID;
    public int techStartWorkOnTicketId;
    public PendingIntent _alarm;
    public PendingIntent _alarmTechStartWorkOnTicket;
    public int repeatSendCounter;
    public String ticketNumber;
    public String calenderID;
    public String eventID;
    public String company;
    public String statusA;
    public int status;
    public long calendarTicketId;
    public boolean isTicketDone;
    public boolean isUserApprove;
    public boolean isTechDone;

    public Ticket(){}  //empty constructor, must have

    public Ticket(String company, String product, String classification, String area, String address, String phone, String desShort, String desLong
                 ,String ticketImage1, String ticketImage2, String ticketId)
    {
        this.clientName = UserSingleton.getInstance().getUserNameString();
        this.email = UserSingleton.getInstance().getUserEmail();
        this.company = company;
        this.product = product;
        this.classification = classification;
        this.area = area;
        this.address = address;
        this.phone = phone;
        this.desShort = desShort;
        this.desLong = desLong;
        this.ticketImage1 = ticketImage1;
        this.ticketImage2 = ticketImage2;
        this.ticketId = ticketId;
        this.startTime = getCurrentTime();
        this.state = ProductID.STATE_A00;
        this.statusA = TicketStatus.waitForApproval;
        this.tech ="אין טכנאי מצוות" ;
        this.ticketNumber = ticketId.substring(0,8);
        this.status = 1;
        this.calendarTicketId = (new Date()).getTime();
       // this.stateObj = TicketFactory.getNewState(UserSingleton.getInstance().getStatus(),ProductID.STATE_A00,this);
    }

    public long getCalendarTicketId() {
        return calendarTicketId;
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

    public TicketStateAble getStateObj() {
        return stateObj;
    }

    public void setStateObj(TicketStateAble stateObj) {
        this.stateObj = stateObj;
    }

    public TicketStateAble changeState(String stateName,Ticket ticket)
    {
        this.state = stateName;

        //Log.d("FactorystateType = ", UserSingleton.getInstance().getUserStatus()==null?"null":UserSingleton.getInstance().getUserStatus());
        Log.d("FactorstateName = ", stateName);

        this.stateObj = TicketFactory.getNewState("Client",stateName);
       // UtlFirebase.updateState(ticket.ticketId,"stateObj",this.stateObj);
        return this.stateObj;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        if(!(this.alarmID<=0))
        {
            this.techStartWorkOnTicketId = alarmID;
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
        //get current date startTime with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getTime()));
        return dateFormat.format(date);
    }

    public String toString()
    {
        String str="";
        str+="Client email: "+this.email +"\n";
        str+="Client name: "+this.clientName +"\n";
        str+="Product:" +this.product+"\n";
        str+="Ticket name: "+this.desShort+"\n";
        str+="Ticket description: "+this.desLong+"\n";
        str+="Phone: "+this.phone+"\n";
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
