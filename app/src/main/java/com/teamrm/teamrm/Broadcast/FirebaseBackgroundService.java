package com.teamrm.teamrm.Broadcast;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;

/**
 * Created by Oorya on 12/02/2017.
 */

public class FirebaseBackgroundService extends Service {

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "FROM SERVICE ", Toast.LENGTH_SHORT).show();

        context = this;
        Log.w("from service", "create ");
        if (!UserSingleton.isUserLoaded()) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Toast.makeText(this, "FROM SERVICE USER " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                UtlFirebase.loginUser(FirebaseAuth.getInstance().getCurrentUser(), new FireBaseAble() {
                    @Override
                    public void resultTicket(Ticket ticket) {

                    }

                    @Override
                    public void resultUser(Users user) {
                        Toast.makeText(FirebaseBackgroundService.this, "INIT SERVICE ", Toast.LENGTH_SHORT).show();
                        UserSingleton.init(user);
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
                    public void companyListCallback(List<Company> companies) {

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
                });
            }
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "stop service", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
