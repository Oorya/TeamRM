package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Client extends Users
{
    public Client(){}

    public Client(String userID, String userNameString, String userEmail) {
        super(userID, userNameString, userEmail);
    }

    /*public void saveClient()
    {
        //create an instance of User class
        Client client=new Client(userName,userEmail,address,phone,userId);

        //creating a connection to fire base
        FirebaseDatabase database= FirebaseDatabase.getInstance();

        //creating a reference to Users object
        DatabaseReference myRef=database.getReference("Users");

        //saving the user under the UUID
        myRef.child("Client").child(userId).setValue(client);
    }*/
}
