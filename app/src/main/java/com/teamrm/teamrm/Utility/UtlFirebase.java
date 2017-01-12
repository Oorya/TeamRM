package com.teamrm.teamrm.Utility;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Fragment.NewTicket;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UtlFirebase { //TODO: make singleton
    //TODO: make abstract factory above DB util classes
    private static String status;
    private static Ticket returnTicket;
    private static TicketFactory ticketFactory;
    private static List<Ticket> ticketList = new ArrayList<Ticket>();
    private static List<String> companyList = new ArrayList<>();

    public UtlFirebase() {}

    //Listener for state changed
    public static void stateListener(final String statusUser, String email, String company) {
        ticketFactory = new TicketFactory();
        final String STATUS_USER = UserSingleton.getInstance().getUserStatus();
        final String MAIL_USER = UserSingleton.getInstance().getUserEmail();
        final String COMPANY = UserSingleton.getInstance().getUserCompany();
        Log.w("STATE CHANGED", "state listener");
        //creating a reference to the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        Query query = myRef.orderByChild("userEmail").equalTo(MAIL_USER);

        switch (STATUS_USER) {
            case Users.STATUS_ADMIN:
                query = myRef.orderByChild("company").equalTo(COMPANY);
                break;
            case Users.STATUS_TECH:
                query = myRef.orderByChild("tech").equalTo(MAIL_USER);
                break;
        }

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String arrData[] = dataSnapshot.getValue().toString().split("[{,]");
                Log.w("STATE CHANGED", arrData[1]);
                //{state=A00Admin, userName=oorya, company=yes, status=0, ticketId=11111};
                for (int ctr = 0; ctr <= arrData.length; ctr++) {
                    if (arrData[ctr].contains("state")) {
                        Log.w("STATE FROM LOOP", STATUS_USER + "States." + arrData[ctr].substring(7) + STATUS_USER);
                        ticketFactory.getNewState(STATUS_USER + "States.", arrData[ctr].substring(7) + STATUS_USER);
                        return;
                    }
                }
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

    public static void changeState(String ticketID, String state) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        myRef.child(ticketID).child("state").setValue(state);
    }

    public static void updateTicket(String ticketID, String key, Date value) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");
        myRef.child(ticketID).child(key).setValue(value);
    }

    public static void updateState(String ticketID, String key, TicketStateAble value) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");
        myRef.child(ticketID).child(key).setValue(value);
    }

    public static void getTicketByKey(final String key, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        Query query = myRef.orderByKey().equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket ticket = item.getValue(Ticket.class);
                    returnTicket = ticket;
                    Log.e("ON DATA CHANGE ", ticket == null ? "NULL" : "NOT NULL");
                    fireBaseAble.resultTicket(ticket);
                    Log.e("RETURN METHOD ", returnTicket == null ? "NULL" : "NOT NULL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static List<Ticket> getAllTicket() {
        //creating a reference to the database
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    /*Ticket retrievedTicket = null;
                    try {
                        Ticket retrievedTicket = item.getValue(Ticket.class);
                    } catch (Exception e) {
                        Log.e(":::UTLFIREBASE:::", "could not retrieve ticket");
                        //Ticket mockTicket = new Ticket("","","", "", "", "", "אין כרטיסים לתצוגה","","","", UUID.randomUUID().toString());
                        //Log.e(":::UTLFIREBASE:::", mockTicket.toString());
                        //ticketList.add(mockTicket);

                    }*/
                    // retrievedTicket.stateObj = (TicketStateAble) item.child("stateObj").getValue(A01Client.class);
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }

                Log.e(":::UTLFIREBASE:::", "LIST SIZE: " + ticketList.size() + "");
                //Need to notify for current list adapter
                TicketList.ticketListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(":::UTLFIREBASE:::", "could not retrieve ticket");
            }
        });
        Log.e(":::UTLFIREBASE::: ALL", "ALL");
        return ticketList;
    }

    public static List<Ticket> getTicketByName(String title, String name, String name2) {
        //creating a reference to the database
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");
        //DataSnapshot data = new DataSnapshot(myRef);
        Query query = myRef.orderByChild(title).equalTo(name);
        //Query query1=myRef.orderByChild("phone").equalTo("3333","4444");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrieve = item.getValue(Ticket.class);
                    ticketList.add(retrieve);
                }
                Log.e("LIST SIZE: ", ticketList.size() + "");

                //Need to notify for current list adapter
                //TicketList.listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return ticketList;
    }

    public static String getStatus(String ticketID) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        myRef.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statusData = dataSnapshot.child("status").getValue() + "";
                status = statusData;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return status;
    }

    public static void changeStatus(String ticketID, String status) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        myRef.child(ticketID).child("status").setValue(status);
    }

    public static void removeTicket(String ticketID) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        myRef.child(ticketID).removeValue();
    }

    public static void removeMultipleTickets(List<String> ticketsList) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        for (int counter = 0; counter < ticketsList.size(); counter++) {
            myRef.child(ticketsList.get(counter)).removeValue();
        }
    }

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

    public static void getUserByKey(final String key, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query = myRef.orderByKey().equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Users users = item.getValue(Users.class);
                    fireBaseAble.resultUser(users);
                    Log.e("ON DATA CHANGE ", users == null ? "NULL" : "NOT NULL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void removeClient(String userID) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        myRef.child("Client").child(userID).removeValue();
    }

    public static void updateClient(String userId, String address, String phone) {
        //creating a reference to Users object
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        //update the user under the UUID
        myRef.child(userId).child("userAddress").setValue(address);
        myRef.child(userId).child("userPhone").setValue(phone);
    }

    public static void changeUserStatus(String userID, String status, boolean isAdmin) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        myRef.child(userID).child("userStatus").setValue(status);
        myRef.child(userID).child("userIsAdmin").setValue(isAdmin);

    }

    public static void setCompany(String userID, String company) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        myRef.child(userID).child("userCompany").setValue(company);
    }

    public static List<String> getAllCompanies() {
        //creating a reference to the database
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Company");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Company retrieveCompany = item.getValue(Company.class);
                    companyList.add(retrieveCompany.name);
                }
                Log.e("LIST SIZE COMPANIES: ", companyList.size() + "");

                //Need to notify for current list adapter
                NewTicket.listCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(MainActivity.context, "Error retrieving data ", Toast.LENGTH_SHORT).show();

            }
        });
        Log.e("ALL", "COMPANIES LIST");
        return companyList;
    }

    public static void saveUser(Users user) {
        //creating a reference to Users object
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

        //saving the user under the UUID
        myRef.child(user.getUserID()).setValue(user, new
                DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });
        Log.d("SET USER::", user.toString());
    }

    public static void saveTicket(Ticket ticket) {
        //creating a reference to Ticket object
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Ticket");

        //saving the user under the UUID
        myRef.child(ticket.ticketId).setValue(ticket, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });
    }

    public static void saveCompany(Company company) {
        //creating a reference to Company object
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Company");

        //saving the user under the UUID
        myRef.child(company.name).setValue(company, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            }
        });
    }
}

