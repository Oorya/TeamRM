package com.teamrm.teamrm.Utility;


import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;

import java.util.ArrayList;
import java.util.List;


public class UtlFirebase {
    private static String status;
    private static Ticket returnTicket;
    private static Users returnUser;
    private static boolean isWait = false;
    private static boolean ticketExist;
    private static FirebaseAuth firebaseAuth;
    private static TicketFactory ticketFactory;
    private static List<Ticket> ticketList = new ArrayList<Ticket>();

    public UtlFirebase() {
    }


    public static void signOut() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener autoListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {

            }
        };
    }

    //Listener for state changed
    public static void stateListener(final String statusUser, String email, String company) {
        ticketFactory = new TicketFactory();
        //creating an instance to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //creating a reference to the database
        DatabaseReference myRef = database.getReference("Ticket");

        Query query = myRef.orderByChild("email").equalTo(email);

        switch (statusUser) {
            case "Admin":
                query = myRef.orderByChild("company").equalTo(company);
                break;
            case "Tech":
                query = myRef.orderByChild("tech").equalTo(email);
                break;
        }

        query
                .addChildEventListener(new ChildEventListener() {
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
                        Log.w("STATE FROM LOOP", statusUser + "States." + arrData[ctr].substring(7) + statusUser);
                        ticketFactory.getNewState(statusUser + "States.", arrData[ctr].substring(7) + statusUser);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        myRef.child(ticketID).child("state").setValue(state);
    }

    public static void ticketSaved(final String ticketID, Object object) {
        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        new AsyncTask<Void, Boolean, Boolean>() {


            @Override
            protected Boolean doInBackground(Void... voids) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Ticket");

                ref.child(ticketID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ticketExist = dataSnapshot.exists();
                        isWait = true;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                while (!isWait) {
                    Log.e("WAIT ", "");
                }

                return ticketExist;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                fireBaseAble.resultBoolean(aBoolean);
                isWait = false;
            }
        }.execute();
    }


    public static void getTicketByKey(final String key, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        new AsyncTask<Void, Ticket, Ticket>() {
            @Override
            protected Ticket doInBackground(Void... voids) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Ticket");

                Query query = myRef.orderByKey().equalTo(key);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Ticket ticket = item.getValue(Ticket.class);
                            returnTicket = ticket;
                            Log.e("ON DATA CHANGE ", ticket == null ? "NULL" : "NOT NULL");
                            isWait = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                while (!isWait) {
                    Log.e("WAIT ", "");
                }
                return returnTicket;
            }

            @Override
            protected void onPostExecute(Ticket ticket) {
                fireBaseAble.resultTicket(ticket);
                Log.e("RETURN METHOD ", returnTicket == null ? "NULL" : "NOT NULL");
                isWait = false;
            }
        }.execute();
    }

    public static List<Ticket> getAllTicket() {

        //creating an instance to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //creating a reference to the database
        final DatabaseReference myRef = database.getReference("Ticket");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrive = item.getValue(Ticket.class);
                    ticketList.add(retrive);
                }
                Log.e("LIST SIZE: ", ticketList.size() + "");

                //Need to notify for current list adapter
                TicketList.myRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(MainActivity.context, "Error retrieving data ", Toast.LENGTH_SHORT).show();

            }
        });
        Log.e("ALL", "");
        return ticketList;
    }

    public static List<Ticket> getTicketByName(String title, String name, String name2) {
        final List<Ticket> ticketList = new ArrayList<Ticket>();
        //creating an instance to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //creating a reference to the database
        final DatabaseReference myRef = database.getReference("Ticket");
        //DataSnapshot data = new DataSnapshot(myRef);
        Query query = myRef.orderByChild(title).equalTo(name);
        //Query query1=myRef.orderByChild("phone").equalTo("3333","4444");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrive = item.getValue(Ticket.class);
                    ticketList.add(retrive);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        myRef.child(ticketID).child("status").setValue(status);
    }

    public static void removeTicket(String ticketID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        myRef.child(ticketID).removeValue();
    }

    public static void removeMultipleTickets(List<String> ticketsList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        for (int counter = 0; counter < ticketsList.size(); counter++) {
            myRef.child(ticketsList.get(counter)).removeValue();
        }
    }

    public static void onStatusChanged(String userName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        Query query = myRef.orderByChild("userName").equalTo(userName);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //CREATE INTENT AND MSG ICON FOR NOTIFICATION
                //Intent intent=new Intent(MainActivity.context,TicketList.class);
                //UtlNotification notification = new UtlNotification(R.drawable.new_msg_icon, "Status changed",  " status", intent, MainActivity.context);
                //notification.sendNotification();
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

    public static void onSendTicket() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Ticket");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //CREATE INTENT AND MSG ICON FOR NOTIFICATION
                /*
                if (MainActivity.isAdmin()){
                    Intent intent = new Intent(MainActivity.context, TicketList.class);
                    UtlNotification notification = new UtlNotification(R.drawable.new_msg_icon, "ADDED", " added", intent, MainActivity.context);
                    notification.sendNotification();
                }*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    public static List<Chat> getChatByTicketID(String ticketID) {
        final List<Chat> chatList = new ArrayList<Chat>();
        //creating an instance to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //creating a reference to the database
        final DatabaseReference myRef = database.getReference("Chat");
        //DataSnapshot data = new DataSnapshot(myRef);
        Query query = myRef.child(ticketID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Chat retrive = item.getValue(Chat.class);
                    chatList.add(retrive);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Chat");

        myRef.child(ticketID).removeValue();
    }

    public static void updateClient(String userId, String address, String phone)
    {
        //creating a connection to fire base
        FirebaseDatabase database= FirebaseDatabase.getInstance();

        //creating a reference to Users object
        DatabaseReference myRef=database.getReference("Users");

        //update the user under the UUID
        myRef.child("Client").child(userId).child("address").setValue(address);
        myRef.child("Client").child(userId).child("phone").setValue(phone);
    }

    public static void getUserByKey(final String key, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        new AsyncTask<Void, Users, Users>() {
            @Override
            protected Users doInBackground(Void... voids) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");

                Query query = myRef.orderByKey().equalTo(key);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Users users = item.getValue(Users.class);
                            returnUser = users;
                            Log.e("ON DATA CHANGE ", users == null ? "NULL" : "NOT NULL");
                            isWait = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                while (!isWait) {
                    Log.e("WAIT ", "");
                }
                return returnUser;
            }

            @Override
            protected void onPostExecute(Users users) {
                //fireBaseAble.resultTicket(users);
                Log.e("RETURN METHOD ", returnUser == null ? "NULL" : "NOT NULL");
                isWait = false;
            }
        }.execute();
    }

    public static void removeClient(String userID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.child("Client").child(userID).removeValue();
    }
}
