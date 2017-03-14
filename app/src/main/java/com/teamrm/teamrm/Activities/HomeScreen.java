package com.teamrm.teamrm.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.Broadcast.FirebaseBackgroundService;
import com.teamrm.teamrm.Fragment.AdminSettingsAdvanced;
import com.teamrm.teamrm.Fragment.AdminSettingsBasic;
import com.teamrm.teamrm.Fragment.CalendarView;
import com.teamrm.teamrm.Fragment.FragmentDrawer;
import com.teamrm.teamrm.Fragment.NewTicket;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;


public class HomeScreen extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private FrameLayout frameLayout;
    public static Context context;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static final int SELECT_FILE = 105;
    private static final int FROM_CAMERA = 205;
    private static final int ACTION_OVERLAY = 300;
    private final static String[] TAG_FRAGMENT = {"NEW_TICKET", "CALENDER","TICKET_LIST"};
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        new NiceToast(this, "User "+UserSingleton.getInstance().getUserEmail() + "\n" + "logged in as " + UserSingleton.getInstance().getClass().getSimpleName(), NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG).show();
        UtlFirebase.setCurrentContext(this);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        context = this;
        frameLayout = (FrameLayout) findViewById(R.id.container_body);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        serviceIntent = new Intent(HomeScreen.this ,FirebaseBackgroundService.class);
        this.startService(serviceIntent);

        TextView appIcon = (TextView) findViewById(R.id.appIcon);
        appIcon.setTypeface(Typeface.createFromAsset(this.getAssets(), "Assistant-Bold.ttf"));

        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.container_body, new TicketList()).addToBackStack(TAG_FRAGMENT[2]);
        fragmentTransaction.add(R.id.container_body, new TicketList()).disallowAddToBackStack();
        fragmentTransaction.commit();
        setTitle(getResources().getStringArray(R.array.nav_list)[0]);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        Log.d("REQUEST = ","API23 HOME_SCREEN");
        Log.w("Permission home screen", "Before if "+requestCode);

        if(requestCode == 108)
        {
            NewTicket newTicket = new NewTicket();
            newTicket.onRequestPermissionsResult(requestCode, permissions, grantResults);
            Log.w("Permission home screen", "Shalty");
        }
        else {
            CalendarView.cal.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("REQUEST = "+requestCode,"onActivityResult HOME SCREEN");
        if(requestCode==FROM_CAMERA || requestCode==SELECT_FILE)
        {
            NewTicket.utlCamera.onActivityResult(requestCode,resultCode,data);
        }
        else
        {
            CalendarView.cal.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketList ticketList = new TicketList();
        switch (position)
        {
            case 0:
                fragmentTransaction.replace(R.id.container_body, ticketList).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[0]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 1:
                NewTicket ticket = new NewTicket();
                fragmentTransaction.replace(R.id.container_body, ticket).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 2:
                Log.d("droeswech","case2");
                CalendarView calendarView = new CalendarView();
                fragmentTransaction.replace(R.id.container_body, calendarView).addToBackStack(TAG_FRAGMENT[1]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[2]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.GONE);
                break;

            case 3:
                AdminSettingsBasic adminSettingsBasic = new AdminSettingsBasic();
                fragmentTransaction.replace(R.id.container_body, adminSettingsBasic).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[3]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 4:
                AdminSettingsAdvanced adminSettingsAdvanced = new AdminSettingsAdvanced();
                fragmentTransaction.replace(R.id.container_body, adminSettingsAdvanced).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[4]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 5:
                signOut();
                break;

            default:

                fragmentTransaction.replace(R.id.container_body, ticketList).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[0]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        //final NewTicket NEW_TICKET_FRAGMENT = (NewTicket)getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT[0]);
        Log.w("ON BACK", getFragmentManager().getBackStackEntryCount()
                + "  SHALTY");
        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            Log.w(" ON BACK","IF");
            //Log.w("ON BACK", getFragmentManager().findFragmentByTag(TAG_FRAGMENT[1]).getTag()+ "  SHALTY");
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else
        {
            Log.w("SUPER ON BACK","ELSE");
            super.onBackPressed();
        }
    }

    private void signOut()
    {
        this.stopService(serviceIntent);
        mGoogleApiClient=App.getGoogleApiHelper().getGoogleApiClient();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status)
                    {
                        //Toast.makeText(context,"logout OK home",Toast.LENGTH_LONG).show();
                        SplashScreen.resume=true;
                        UtlFirebase.removeActiveListeners();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(HomeScreen.this, SplashScreen.class));
                    }
                });
    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {

    }

    public Context getContext(){
        return this.context;
    }



}
