package com.teamrm.teamrm.Utility;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Client;
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
    public static boolean initDone = false;

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

    public static void init(final FirebaseUser firebaseUser)
    {
        Log.d(LOGINTAG, "Stage 4, init singleton with user from credentials "+firebaseUser.toString());
        FireBaseAble fbHelper = new FireBaseAble() {
            @Override
            public void resultTicket(Ticket ticket) {

            }

            @Override
            public void resultUser(Users user) {
                instance = user;
                Log.d(LOGINTAG, "Final stage, instance initialized as "+user.getUserEmail()+", "+user.getUserStatus());
                UtlFirebase.getAllTicketLites(this);
            }

            @Override
            public void ticketListCallback(List<Ticket> tickets) {

            }

            @Override
            public void ticketLiteListCallback(List<TicketLite> ticketLites) {
                TicketLite.setTicketLiteList(ticketLites);
            }

            @Override
            public void resultBoolean(boolean bool) {

            }

            @Override
            public void companyListCallback(List<GenericKeyValueTypeable> companies) {

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
        UtlFirebase.loginUser(firebaseUser, fbHelper);
        //UtlFirebase.stateListener("l","l","l");//TODO:make it work
            }

    }


