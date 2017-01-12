package com.teamrm.teamrm.Utility;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Type.Client;
import com.teamrm.teamrm.Type.Users;

import java.util.UUID;


/**
 * Created by Oorya on 28/12/2016.
 */

public class UserSingleton extends Users{

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

    public static void init(final GoogleSignInAccount account)
    {
        //creating a reference to Users object
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("Users");

        final Query q = myRef.orderByChild("userEmail").equalTo(account.getEmail());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w(TAG, dataSnapshot.exists()+" ok ==LY");
               if(!dataSnapshot.exists())
               {
                   instance = new Client(UUID.randomUUID().toString(),account.getDisplayName(),account.getEmail());
                   Log.w(TAG, instance.getUserEmail()+" cons");
                   UtlFirebase.saveUser(instance);
               }
               else
               {
                   for(DataSnapshot item : dataSnapshot.getChildren())
                   {
                       instance = item.getValue(Client.class);
                       Log.w(TAG, instance.getUserID()+"  FOR");
                   }
               }
                UtlFirebase.stateListener("l","l","l");
                q.removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG," cancel==LY");

            }
        });
    }
}
