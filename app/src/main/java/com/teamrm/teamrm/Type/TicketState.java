package com.teamrm.teamrm.Type;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 19/02/2017.
 */

public class TicketState {

    public static final String STATELISTENERTAG = "::: StateListener :::";

    @Exclude private static List<TicketState> ticketStates = new ArrayList<>();
    private String ticketID;
    private String ticketStateString;

    public TicketState(){}

    public TicketState(String ticketID, String ticketStateString){
        this.ticketID = ticketID;
        this.ticketStateString = ticketStateString;
    }

    public TicketState(Ticket ticket){
        this.ticketID = ticket.getTicketID();
        this.ticketStateString = ticket.getTicketStateString();
    }

    @Exclude public static void ticketStatesAddTicketState(TicketState ticketState) {
        ticketStates.add(ticketState);
    }

    @Exclude public static void ticketStatesUpdateTicketState(TicketState ticketState){
        Log.d(STATELISTENERTAG, "Looking for ticket with a hashcode " + ticketState.hashCode());
        for (TicketState ticketStateToUpdate : ticketStates){
            Log.d(STATELISTENERTAG, "Found hashcode " + ticketStateToUpdate.hashCode());
            if (ticketStateToUpdate.hashCode() == ticketState.hashCode()){
                ticketStateToUpdate.setTicketStateString(ticketState.getTicketStateString());
            }
        }
    }

    @Exclude public static void ticketStatesRemoveState(TicketState ticketState){
        ArrayList<TicketState> removeList = new ArrayList<>();
        for (TicketState ticketStateToRemove : ticketStates){
            if (ticketStateToRemove.hashCode() == ticketState.hashCode()){
                removeList.add(ticketStateToRemove);
            }
        }
        ticketStates.removeAll(removeList);
    }

    public static List<TicketState> getTicketStates() {
        return ticketStates;
    }

    public static void setTicketStates(List<TicketState> ticketStates) {
        TicketState.ticketStates = ticketStates;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getTicketStateString() {
        return ticketStateString;
    }

    public void setTicketStateString(String ticketStateString) {
        this.ticketStateString = ticketStateString;
    }

    @Override
    public int hashCode() {
        return this.ticketID.hashCode();
    }

    @Override
    public String toString() {
        return "Ticket ID: " + this.ticketID + ", Ticket state: " + this.ticketStateString;
    }
}
