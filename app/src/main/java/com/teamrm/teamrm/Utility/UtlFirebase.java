package com.teamrm.teamrm.Utility;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Admin;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Type.Client;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Technician;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.teamrm.teamrm.Utility.UserSingleton.LOGINTAG;


public class UtlFirebase { //TODO: make singleton
    //TODO: make abstract factory above DB util classes
    private static Context currentContext;
    private static String ticketStateString;
    private static Ticket returnTicket;
    private static TicketFactory ticketFactory;
    private static List<Ticket> ticketList = new ArrayList<>();
    private static List<TicketLite> ticketLiteList = new ArrayList<>();
    private static Technician techFromFirebase;


    private static final DatabaseReference GLOBAL_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference COMPANY_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Companies");
    private static final DatabaseReference COMPANY_PRODUCTS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyProducts");
    private static final DatabaseReference COMPANY_CATEGORIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyCategories");
    private static final DatabaseReference COMPANY_REGIONS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyRegions");
    private static final DatabaseReference COMPANY_TECHNICIANS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("CompanyTechnicians");
    private static final DatabaseReference TICKET_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Tickets");
    private static final DatabaseReference TICKET_LITE_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("TicketLites");
    private static final DatabaseReference USERS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("Users");
    private static final DatabaseReference CLIENT_COMPANIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("ClientCompanies");

    final static String TAG = ":::UtlFirebase:::";

    public UtlFirebase() {
    }


///////////////////////////// User /////////////////////////////

    public static void loginUser(final FirebaseUser firebaseUser, final FireBaseAble fbHelper) {
        Log.d(LOGINTAG, "Stage 5, check if in firebase exists " + firebaseUser.getEmail());
        final Query queryUserExists = USERS_ROOT_REFERENCE.orderByChild(Users.USER_EMAIL).equalTo(firebaseUser.getEmail());
        queryUserExists.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d(LOGINTAG, "Stage 6, no such user " + firebaseUser.getEmail() + " exists, creating one");
                    Client clientToAdd = new Client(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), "", "");
                    UtlFirebase.addClient(clientToAdd);
                    fbHelper.resultUser(clientToAdd);
                } else {
                    for (final DataSnapshot item : dataSnapshot.getChildren()) {
                        if (item.child(Users.USER_STATUS).exists()) {
                            switch (item.child(Users.USER_STATUS).getValue().toString()) {
                                case Users.STATUS_CLIENT:
                                    Log.d(LOGINTAG, "found Client");
                                    fbHelper.resultUser(item.getValue(Client.class));                              // push current dataSnapshot as Client to the UserSingleton
                                    new NiceToast(currentContext, "You are logged in as Client", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT);
                                    break;

                                case Users.STATUS_TECH:
                                    Query techRef = COMPANY_TECHNICIANS_ROOT_REFERENCE                              //
                                            .child(dataSnapshot.child(Users.ASSIGNED_COMPANY_ID).getValue().toString())   // get Technician from the corresponding path
                                            .child(dataSnapshot.child(Users.USER_ID).getValue().toString());             //
                                    techRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                techFromFirebase = item.getValue(Technician.class);                 // acquire Technician from the new dataSnapshot
                                            } else {
                                                fbHelper.resultUser(item.getValue(Client.class));
                                                new NiceToast(currentContext, "Could not log you in as Tech, you are logged as Client", NiceToast.NICETOAST_WARNING, Toast.LENGTH_LONG);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            toastTheError(databaseError);
                                        }
                                    });
                                    fbHelper.resultUser(techFromFirebase);                                          // push acquired Technician to the UserSingleton
                                    new NiceToast(currentContext, "You are logged in as Technician", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT);
                                    break;
                                case Users.STATUS_ADMIN:
                                    if (item.child(Users.USER_IS_ADMIN).getValue().equals(true)) {                             // check flag isAdmin
                                        fbHelper.resultUser(item.getValue(Admin.class));
                                    } else {
                                        fbHelper.resultUser(item.getValue(Client.class));
                                        new NiceToast(currentContext, "Could not log you in as Admin, you are logged as Client", NiceToast.NICETOAST_WARNING, Toast.LENGTH_LONG);
                                    }
                                    break;
                                default:
                                    fbHelper.resultUser(item.getValue(Client.class));                       // push current dataSnapshot as Client to the UserSingleton
                                    new NiceToast(currentContext, "You are logged in as Client", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT);
                                    break;
                            }
                        } else {
                            fbHelper.resultUser(item.getValue(Client.class));                       // push current dataSnapshot as Client to the UserSingleton
                            new NiceToast(currentContext, "You are logged in as Client", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }


    public static void addClient(final Users user) {
        Log.d(LOGINTAG, "Stage 7, adding user " + user.getUserEmail());
        USERS_ROOT_REFERENCE.child(user.getUserID()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                String addedUser = "Added user:\n";
                addedUser += user.getUserEmail() + "\n";
                addedUser += user.getUserNameString();
                toastSuccessOrError(addedUser, databaseError);
            }

        });
        Log.d("SET USER::", user.toString());
    }

    public static void addTechnician(String companyID, final Technician technician) {
        COMPANY_TECHNICIANS_ROOT_REFERENCE.child(companyID).child(technician.getUserID()).setValue(technician, new
                DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        String addedUser = "Added tech:\n";
                        addedUser += technician.getUserEmail() + "\n";
                        addedUser += technician.getUserNameString();
                        toastSuccessOrError(addedUser, databaseError);
                    }
                });
    }


    public static void getUserByEmail(final String email, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        Query query = USERS_ROOT_REFERENCE.orderByChild(Users.USER_EMAIL).equalTo(email);
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

    public static void updateUser(String userId, final Map<String, Object> updates) {
        USERS_ROOT_REFERENCE.child(userId).updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError(updates.toString(), databaseError);
            }
        });
    }

    public static void changeUserStatus(final String userID, String userStatus) {
        Map updates = new HashMap();
        switch (userStatus) {
            case Users.STATUS_ADMIN:
                updates.clear();
                updates.put(Users.USER_STATUS, Users.STATUS_ADMIN);
                updates.put(Users.USER_IS_ADMIN, true);
                USERS_ROOT_REFERENCE.child(userID).updateChildren(updates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        toastSuccessOrError("User set as admin", databaseError);
                    }
                });
                break;
            case Users.STATUS_TECH:
                updates.clear();
                updates.put(Users.USER_STATUS, Users.STATUS_TECH);
                USERS_ROOT_REFERENCE.child(userID).updateChildren(updates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        toastSuccessOrError("User set as tech", databaseError);
                    }
                });
        }
    }

    public static void setAssignedCompanyID(String userID, String companyID) {
        USERS_ROOT_REFERENCE.child(userID).child(Users.ASSIGNED_COMPANY_ID).setValue(companyID);
    }

///////////////////////////// Ticket /////////////////////////////

    //Listener for state changed
    public static void stateListener(final String statusUser, String email, String company) { //TODO:rebuild method
        ticketFactory = new TicketFactory();
        final String userStatus = UserSingleton.getInstance().getUserStatus();
        final String userEmail = UserSingleton.getInstance().getUserEmail();
        final String userCompanyID = UserSingleton.getInstance().getAssignedCompanyID();
        Log.w("STATE CHANGED", "state listener");

        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.CLIENT_EMAIL).equalTo(userEmail);

        switch (userStatus) {
            case Users.STATUS_ADMIN:
                query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(userCompanyID);
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
                //{state=A00Admin, clientNameString=oorya, company=yes, ticketStateString=0, ticketId=11111};
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

    public static void addTicket(final Ticket ticket) {
        Map updates = new HashMap();
        updates.put("Tickets/" + ticket.getTicketID(), ticket);
        updates.put("TicketLites/" + ticket.getTicketID(), new TicketLite(ticket));
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("Added ticket " + ticket.getTicketNumber(), databaseError);
            }
        });
    }

    public static void updateTicketStateString(String ticketID, String ticketStateString) {
        TICKET_ROOT_REFERENCE.child(ticketID).child(Ticket.TICKET_STATE_STRING).setValue(ticketStateString, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null){
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void updateTicket(String ticketID, String key, Date value) {
        TICKET_ROOT_REFERENCE.child(ticketID).child(key).setValue(value);
        TICKET_LITE_ROOT_REFERENCE.child(ticketID).child(key).setValue(value);
    }

    public static void getAllTickets(final FireBaseAble fbHelper) {
        if (UserSingleton.getInstance() instanceof Client) {
            getAllClientTickets(UserSingleton.getInstance().getUserID(), fbHelper);
        } else if (UserSingleton.getInstance() instanceof Technician) {
            getAllCompanyTickets(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
        } else if ((UserSingleton.getInstance() instanceof Admin)) {
            getAllCompanyTickets(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
        } else {
            Log.e(LOGINTAG, "getAllTickets::Undefined singleton");
        }
    }

    public static void getAllTicketLites(final FireBaseAble fbHelper) {
        if (UserSingleton.getInstance() instanceof Client) {
            getAllClientTicketLites(UserSingleton.getInstance().getUserID(), fbHelper);
        } else if (UserSingleton.getInstance() instanceof Technician) {
            getAllCompanyTicketLites(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
        } else if ((UserSingleton.getInstance() instanceof Admin)) {
            getAllCompanyTicketLites(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
        } else {
            Log.e(LOGINTAG, "getAllTicketlites::Undefined singleton");
        }

    }

    private static void getAllClientTickets(String clientID, final FireBaseAble fbHelper) {
        Log.d("getAllClientTickets", ":::called:::");
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.CLIENT_ID).equalTo(clientID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    private static void getAllClientTicketLites(String clientID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.CLIENT_ID).equalTo(clientID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    private static void getAllCompanyTickets(String companyID, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    private static void getAllCompanyTicketLites(String companyID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void getAllTechTickets(String companyID, String techID, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID).orderByChild(Ticket.TECH_ID).equalTo(techID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void getAllTechTicketLites(String companyID, String techID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID).orderByChild(Ticket.TECH_ID).equalTo(techID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static String getTicketStateString(String ticketID) {
        TICKET_ROOT_REFERENCE.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketStateString = dataSnapshot.child(Ticket.TICKET_STATE_STRING).getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });

        return ticketStateString;
    }

    public static void updateTicketPresentation(String ticketID, int status) {
        Map updates = new HashMap();
        updates.put("Tickets/" + ticketID + "/" + Ticket.TICKET_PRESENTATION, status);
        updates.put("TicketLites/" + ticketID + "/" + Ticket.TICKET_PRESENTATION, status);
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("", databaseError);
            }
        });
    }

    public static void removeTicket(String ticketID) {
        TICKET_ROOT_REFERENCE.child(ticketID).removeValue();
    }

    public static void removeMultipleTickets(List<String> ticketsIDList) {
        for (int counter = 0; counter < ticketsIDList.size(); counter++) {
            TICKET_ROOT_REFERENCE.child(ticketsIDList.get(counter)).removeValue();
        }
    }

    public static void getTicketByKey(final String key, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByKey().equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket ticket = item.getValue(Ticket.class);
                    returnTicket = ticket;
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


    public static void getCurrentCompany(FireBaseAble fbHelper) {
        final List<Company> clientCompanies = new ArrayList<>();
        Query query = COMPANY_ROOT_REFERENCE.child(UserSingleton.getInstance().getAssignedCompanyID());
        //TODO: add method
    }

    public static void getAllClientCompanies(final FireBaseAble fbHelper) {
        final List<Company> clientCompanies = new ArrayList<>();
        Query query = CLIENT_COMPANIES_ROOT_REFERENCE.orderByChild(UserSingleton.getInstance().getUserID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot clientCompany : dataSnapshot.getChildren()) {
                    clientCompanies.add((Company) clientCompany.getValue());
                }
                fbHelper.companyListCallback(clientCompanies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void addCompany(final Company company) {
        COMPANY_ROOT_REFERENCE.child(company.getCompanyId()).setValue(company, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DatabaseReference userRef = USERS_ROOT_REFERENCE.child(UserSingleton.getInstance().getUserID());
                Map update = new HashMap();
                update.put(Users.ASSIGNED_COMPANY_ID, company.getCompanyId());
                userRef.updateChildren(update, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        new NiceToast(currentContext, "Created company " + company.getCompanyName() + "for Admin" + UserSingleton.getInstance().getUserEmail(), NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

///////////////////////////// Product /////////////////////////////

    public static void addProduct(String companyID, String productName) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).push().setValue(productName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.w("Firebase util ", "productName key " + databaseReference.getKey());
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

        final List<Product> productList = new ArrayList<>();
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!productList.isEmpty()) {
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
                Log.d(TAG, "Updated productName " + productUpdatedName);
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

        final List<Category> categoryList = new ArrayList<>();
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!categoryList.isEmpty()) {
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

        final List<Region> regionList = new ArrayList<>();
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!regionList.isEmpty()) {
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

    public static void toastTheError(DatabaseError dbError) {
        new NiceToast(currentContext, "Update failed with error \n" + dbError.getCode() + "\n" + dbError.getDetails(), NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
    }

    public static void toastSuccessOrError(String positiveMessage, @Nullable DatabaseError dbError) {
        if (dbError == null) {
            new NiceToast(currentContext, positiveMessage, NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
        } else {
            new NiceToast(currentContext, "Update failed with error \n" + dbError.getCode() + "\n" + dbError.getDetails(), NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    public static void setCurrentContext(Context currentContext) {
        UtlFirebase.currentContext = currentContext;
        Log.d("setCurrentContext", currentContext.toString());
    }
}