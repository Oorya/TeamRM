package com.teamrm.teamrm.Utility;

import android.os.AsyncTask;
import android.util.Log;

import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;

import java.util.List;


/**
 * Created by Oorya on 28/12/2016.
 */

public class UserSingleton extends Users{

    public static final String LOGINTAG = ":::LOGIN_SEQUENCE:::";
    private static Users instance = null;
    private static final String TAG = "USER_SINGLETON";

    private UserSingleton() {}

    public static Users getInstance()
    {
        if(instance == null)
        {
            instance = new UserSingleton();
            Log.w(TAG, instance.getUserEmail()+" if");

            return instance;
        }
        Log.w(TAG, instance.getUserEmail()+" else");
        return instance;
    }

    public static void init(final Users user)
    {
        instance = user;
        Log.d(LOGINTAG, "Stage 7, init the UserSingleton with user fetched from FireBase");
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(LOGINTAG, "::AsyncTask");
                Log.d(LOGINTAG, "Calling getAllTicketLites");
                UtlFirebase.getAllTicketLites(fbHelper);
                Log.d(LOGINTAG, "Calling getAllCompanies");
                UtlFirebase.getAllCompanies(fbHelper);
                Log.d(LOGINTAG, "Calling getAllTickets");
                UtlFirebase.getAllTickets(fbHelper);

                return null;
            }
        }.execute();
                //UtlFirebase.stateListener("l","l","l");//TODO:make it work
            }

    public static void refreshTicketLites(){
        UtlFirebase.getAllTicketLites(fbHelper);
    }

    private static FireBaseAble fbHelper = new FireBaseAble() {
        @Override
        public void resultTicket(Ticket ticket) {

        }

        @Override
        public void resultUser(Users user) {

        }

        @Override
        public void ticketListCallback(List<Ticket> tickets) {
            Ticket.setTicketList(tickets);
        }

        @Override
        public void ticketLiteListCallback(List<TicketLite> ticketLites) {
            TicketLite.setTicketLiteList(ticketLites);
        }

        @Override
        public void resultBoolean(boolean bool) {

        }

        @Override
        public void companyListCallback(List<Company> companies) {
            Company.setCompanyList(companies);
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
    };
    }


