package com.teamrm.teamrm.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by shalty on 30/08/2016.
 */
public class BootReceiver extends BroadcastReceiver implements FireBaseAble {
    
   
    List<Ticket> tickets;

    @Override
    public void onReceive(Context context, Intent intent) 
    {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) 
        {
            Log.e("MESSEGE","BootReceiver is activate");
           
            tickets = UtlFirebase.getAllTicket();
            
        }
        
        
    }

    @Override
    public void result(Ticket ticket) {
        
    }

    @Override
    public void resultList(List<Ticket> ticket) {


        Calendar cal = Calendar.getInstance();

        for (Ticket tickets : ticket)
        {
            if(tickets.endTime.getTime()-cal.getTime().getTime()<0)
            {
                //send a notification eventTime/ttl/wait for tech.
                // pass deadline notification start activities display all pass deadline  event
            }
        }
    }


}
