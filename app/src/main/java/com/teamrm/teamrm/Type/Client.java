package com.teamrm.teamrm.Type;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Client extends Users implements GenericKeyValueTypeable,FireBaseAble
{
    public Client(){}

    @Exclude
    private List<GenericKeyValueTypeable> clientCompanies;

    public Client(String clientID, String userNameString, String userEmail, @Nullable String userPhone, @Nullable String userAddress) {
        super(clientID, userNameString, userEmail);
        //UtlFirebase.getAllClientCompanies(this);
    }

    @Override
    @Exclude
    public String getItemKey() {
        return super.getUserID();
    }

    @Override
    @Exclude
    public String getItemValue() {
        return super.getUserNameString();
    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {

    }

    @Override
    public void ticketListCallback(List<Ticket> tickets) {

    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }

    @Override
    public void companyListCallback(List<GenericKeyValueTypeable> companies) {
        clientCompanies = companies;
    }

    @Override
    public void productListCallback(List<Product> products) {

    }

    @Override
    public void categoryListCallback(List<Category> categories) {

    }

    @Override
    public void regionListCallback(List<Region> regions) {

    }
}
