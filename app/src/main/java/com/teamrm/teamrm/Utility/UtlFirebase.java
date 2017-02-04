package com.teamrm.teamrm.Utility;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UtlFirebase { //TODO: make singleton
    //TODO: make abstract factory above DB util classes
    private static String ticketStateString;
    private static Ticket returnTicket;
    private static TicketFactory ticketFactory;
    private static List<Ticket> ticketList = new ArrayList<>();
    private static List<TicketLite> ticketLiteList = new ArrayList<>();


    private static final DatabaseReference GLOBAL_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference COMPANY_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Companies");
    private static final DatabaseReference COMPANY_PRODUCTS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyProducts");
    private static final DatabaseReference COMPANY_CATEGORIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyCategories");
    private static final DatabaseReference COMPANY_REGIONS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyRegions");
    private static final DatabaseReference COMPANY_TECHNICIANS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyTechnicians");
    private static final DatabaseReference TICKET_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Tickets");
    private static final DatabaseReference TICKET_LITE_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("TicketLites");
    private static final DatabaseReference USERS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Users");
    private static final DatabaseReference USER_COMPANIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("UserCompanies");

    final static String TAG = ":::UtlFirebase:::";

    public UtlFirebase() {
    }


///////////////////////////// User /////////////////////////////

    public static void addUser(Users user) {
        USERS_ROOT_REFERENCE.child(user.getUserID()).setValue(user, new
                DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });
        Log.d("SET USER::", user.toString());
    }

    public static void getUserByEmail(final String email, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        Query query = USERS_ROOT_REFERENCE.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Users user = item.getValue(Users.class);
                    fireBaseAble.resultUser(user);
                    Log.e("ON DATA CHANGE ", user == null ? "NULL" : "NOT NULL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void removeUser(String userID) {
        USERS_ROOT_REFERENCE.child(userID).removeValue();
    }

    public static void updateClient(String userId, String address, String phone) {
        USERS_ROOT_REFERENCE.child(userId).child("userAddress").setValue(address);
        USERS_ROOT_REFERENCE.child(userId).child("userPhone").setValue(phone);
    }

    public static void changeUserStatus(String userID, String status, boolean isAdmin) {
        USERS_ROOT_REFERENCE.child(userID).child("userStatus").setValue(status);
        USERS_ROOT_REFERENCE.child(userID).child("userIsAdmin").setValue(isAdmin);
    }

    public static void setUserCompanyID(String userID, String companyID) {
        USERS_ROOT_REFERENCE.child(userID).child("userCompanyID").setValue(companyID);
    }

///////////////////////////// Ticket /////////////////////////////

    //Listener for state changed
    public static void stateListener(final String statusUser, String email, String company) { //TODO:rebuild method
        ticketFactory = new TicketFactory();
        final String userStatus = UserSingleton.getInstance().getUserStatus();
        final String userEmail = UserSingleton.getInstance().getUserEmail();
        final String userCompanyID = UserSingleton.getInstance().getUserCompanyID();
        Log.w("STATE CHANGED", "state listener");

        Query query = TICKET_ROOT_REFERENCE.orderByChild("userEmail").equalTo(userEmail);

        switch (userStatus) {
            case Users.STATUS_ADMIN:
                query = TICKET_ROOT_REFERENCE.orderByChild("companyID").equalTo(userCompanyID);
                break;
            case Users.STATUS_TECH:
                query = TICKET_ROOT_REFERENCE.orderByChild("techID").equalTo(userEmail);
                break;
        }

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               /*
                String arrData[] = dataSnapshot.getValue().toString().split("[{,]");
                *//*for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    Log.w("STATE FROM LOOP", STATUS_USER + "States." + retrievedTicket.state + STATUS_USER);
                    ticketFactory.getNewState(STATUS_USER + "States.", retrievedTicket.state + STATUS_USER, retrievedTicket);
                }*//*
                Log.w("STATE CHANGED", arrData[1]);
                //{state=A00Admin, userName=oorya, company=yes, ticketStateString=0, ticketId=11111};
                for (int ctr = 0; ctr <= arrData.length; ctr++) {
                    if (arrData[ctr].contains("state")) {
                        Log.w("STATE FROM LOOP", userStatus + "States." + arrData[ctr].substring(7) + userStatus);
                        ticketFactory.getNewState(userStatus + "States.", arrData[ctr].substring(7) + userStatus, new Ticket());
                        return;
                    }
                }*/
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void addTicket(Ticket ticket) {
        Map updates = new HashMap();
        updates.put("Tickets/" + ticket.getTicketID(), ticket);
        updates.put("TicketLites/" + ticket.getTicketID(), new TicketLite(ticket));
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

    public static void updateTicketStateString(String ticketID, String ticketStateString) {
        TICKET_ROOT_REFERENCE.child(ticketID).child("ticketStateString").setValue(ticketStateString, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });
    }

    public static void updateTicket(String ticketID, String key, Date value) {
        TICKET_ROOT_REFERENCE.child(ticketID).child(key).setValue(value);
        TICKET_LITE_ROOT_REFERENCE.child(ticketID).child(key).setValue(value);
    }

    public static void getTicketByKey(final String key, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByKey().equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket ticket = item.getValue(Ticket.class);
                    returnTicket = ticket; //TODO: what for?
                    Log.e("ON DATA CHANGE ", ticket == null ? "NULL" : "NOT NULL");
                    fbHelper.resultTicket(ticket);
                    Log.e("RETURN METHOD ", returnTicket == null ? "NULL" : "NOT NULL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void getAllTickets(final FireBaseAble fbHelper) { //TODO:unusable except for testing, should get by company or by client
        TICKET_ROOT_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);

                }
                Log.e(":::UTLFIREBASE:::", "LIST SIZE: " + ticketList.size() + "");
                fbHelper.ticketListCallback(ticketList);
                //Need to notify for current list adapter
                //TicketList.ticketListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(":::UTLFIREBASE:::", "could not retrieve tickets");
            }
        });
        Log.e(":::UTLFIREBASE::: ALL", "ALL");
    }


    public static void getAllTicketLites(final FireBaseAble fbHelper) { //TODO:unusable except for testing, should get by company or by client
        TICKET_LITE_ROOT_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicketLite = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicketLite);

                }
                Log.e(":::UTLFIREBASE:::", "LIST SIZE: " + ticketList.size() + "");
                fbHelper.ticketLiteListCallback(ticketLiteList);
                //Need to notify for current list adapter
                //TicketList.ticketListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(":::UTLFIREBASE:::", "could not retrieve tickets");
            }
        });
        Log.e(":::UTLFIREBASE::: ALL", "ALL");
    }

    public static void getAllClientTickets(String clientID, final FireBaseAble fbHelper) {
        //TODO:add method
    }

    public static void getAllCompanyTickets(String companyID, final FireBaseAble fbHelper) {
        //TODO:add method
    }

    public static void getAllTechTickets(String companyID, final FireBaseAble fbHelper) {
        //TODO:add method
    }

    public static String getTicketStateString(String ticketID) {
        TICKET_ROOT_REFERENCE.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketStateString = dataSnapshot.child("ticketStateString").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return ticketStateString;
    }

    public static void updateTicketPresentation(String ticketID, int status) {
        TICKET_ROOT_REFERENCE.child(ticketID + "/ticketPresentation").setValue(status);
    }

    public static void removeTicket(String ticketID) {
        TICKET_ROOT_REFERENCE.child(ticketID).removeValue();
    }

    public static void removeMultipleTickets(List<String> ticketsIDList) {
        for (int counter = 0; counter < ticketsIDList.size(); counter++) {
            TICKET_ROOT_REFERENCE.child(ticketsIDList.get(counter)).removeValue();
        }
    }

///////////////////////////// Chat /////////////////////////////

    public static List<Chat> getChatByTicketID(String ticketID) {
        final List<Chat> chatList = new ArrayList<Chat>();
        //creating a reference to the database
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Chat");
        //DataSnapshot data = new DataSnapshot(myRef);
        Query query = myRef.child(ticketID);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Chat retrievedChat = item.getValue(Chat.class);
                    chatList.add(retrievedChat);
                }
                Log.e("LIST SIZE: ", chatList.size() + "");

                //Need to notify for current list adapter
                //ChatTicket.chatAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(MainActivity.context, "Error retrieving data ", Toast.LENGTH_SHORT).show();
            }

        });
        return chatList;
    }

    public static void removeChat(String ticketID) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Chat");

        myRef.child(ticketID).removeValue();
    }

///////////////////////////// Company /////////////////////////////

    public static List<Company> getAllCompanies(final FireBaseAble fbHelper) {

        final List<Company> companyList = new ArrayList<>();

        COMPANY_ROOT_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Company retrieveCompany = item.getValue(Company.class);
                    companyList.add(retrieveCompany);
                }
                fbHelper.companyListCallback(companyList);
                Log.e("LIST SIZE COMPANIES: ", companyList.size() + "");

                //Need to notify for current list adapter
                //NewTicket.listCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(MainActivity.context, "Error retrieving data ", Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("ALL", "COMPANIES LIST");
        return companyList;
    }


    public static List<GenericKeyValueTypeable> getAllClientCompanies(String userID, FireBaseAble fbHelper) {
        final List<GenericKeyValueTypeable> clientCompanies = new ArrayList<>();
        Query query = USER_COMPANIES_ROOT_REFERENCE.orderByChild(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot clientCompany : dataSnapshot.getChildren()) {
                    clientCompanies.add((Company) clientCompany.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return clientCompanies;
    }

    public static void addCompany(Company company) {
        COMPANY_ROOT_REFERENCE.child(company.getCompanyId()).setValue(company, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

///////////////////////////// Product /////////////////////////////

    public static void addProduct(String companyID, String productName) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).push().setValue(productName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.w("Firebase util ", "product key " + databaseReference.getKey());
                //TODO:get key callback
            }
        });
    }

    public static void getProducts(String companyID, final FireBaseAble fbHelper) {
        final List<Product> productList = new ArrayList<>();
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    productList.add(new Product(item.getKey(), (String) item.getValue()));

                }
                fbHelper.productListCallback(productList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void getProductsForEdit(String companyID, final FireBaseAble fbHelper) {

        final List<Product> productList=new ArrayList<>();
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!productList.isEmpty()){
                productList.clear();
            }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    productList.add(new Product(item.getKey(), (String) item.getValue()));
                }
                fbHelper.productListCallback(productList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public static void updateProduct(String companyID, Product product, @NonNull final String productUpdatedName) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).child(product.getItemKey()).setValue(productUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
                Log.d(TAG, "Updated product "+productUpdatedName);
            }
        });
    }

    public static void removeProduct(String companyID, Product product) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).child(product.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

///////////////////////////// Category /////////////////////////////

    public static void addCategory(String companyID, String categoryName) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).push().setValue(categoryName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.w("Firebase util ", "category key " + databaseReference.getKey());
                //TODO:get key callback
            }
        });
    }

    public static List<Category> getCategories(String companyID, final FireBaseAble fbHelper) {
        final List<Category> categoryList = new ArrayList<>();
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    categoryList.add(new Category(item.getKey(), (String) item.getValue()));
                }
                fbHelper.categoryListCallback(categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return categoryList;
    }

    public static void getCategoriesForEdit(String companyID, final FireBaseAble fbHelper) {

        final List<Category> categoryList=new ArrayList<>();
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!categoryList.isEmpty()){
                    categoryList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    categoryList.add(new Category(item.getKey(), (String) item.getValue()));
                }
                fbHelper.categoryListCallback(categoryList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public static void updateCategory(String companyID, Category category, @NonNull String categoryUpdatedName) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).child(category.getItemKey()).setValue(categoryUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

    public static void removeCategory(String companyID, Category category) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).child(category.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

///////////////////////////// Region /////////////////////////////

    public static void addRegion(String companyID, String regionName) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).push().setValue(regionName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.w("Firebase util ", "region key " + databaseReference.getKey());
                //TODO:get key callback
            }
        });
    }

    public static List<Region> getRegions(String companyID, final FireBaseAble fbHelper) {
        final List<Region> regionList = new ArrayList<>();
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regionList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    regionList.add(new Region(item.getKey(), (String) item.getValue()));
                }
                fbHelper.regionListCallback(regionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return regionList;
    }

    public static void getRegionsForEdit(String companyID, final FireBaseAble fbHelper) {

        final List<Region> regionList=new ArrayList<>();
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!regionList.isEmpty()){
                    regionList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    regionList.add(new Region(item.getKey(), (String) item.getValue()));
                }
                fbHelper.regionListCallback(regionList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public static void updateRegion(String companyID, Region region, @NonNull String regionUpdatedName) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).child(region.getItemKey()).setValue(regionUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }

    public static void removeRegion(String companyID, Region region) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).child(region.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //TODO:callback
            }
        });
    }
}