package com.teamrm.teamrm.Interfaces;

/**
 * Created by Oorya on 29/11/2016.
 */

public interface TicketStatus {

    String waitForApproval = "ממתין לאישור פתיחה";
    String ticketClosed = "התקלה נסגרה";
    String ticketApproval = "התקלה אושרה";
    String ticketCanceled = "התקלה בוטלה";
    String timeIsOver = "עבר זמן טיפול";
    String pendingOrderedTech = "ממתין לציוות טכנאי";
}
