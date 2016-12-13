package com.teamrm.teamrm.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.teamrm.teamrm.Fragment.CalendarView;
import com.teamrm.teamrm.Fragment.CompanySettingFrag;
import com.teamrm.teamrm.Fragment.FragmentDrawer;
import com.teamrm.teamrm.Fragment.NewTicket;
import com.teamrm.teamrm.Fragment.Setting;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.App;


public class HomeScreen extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private FrameLayout frameLayout;
    public static Context context;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FloatingActionButton addTicket;
    private static final int SELECT_FILE = 105;
    private static final int FROM_CAMERA = 205;
    private static final int ACTION_OVERLAY = 300;
    private final static String[] TAG_FRAGMENT = {"NEW_TICKET"};
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        context = this;
        frameLayout = (FrameLayout) findViewById(R.id.container_body);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        addTicket = (FloatingActionButton) findViewById(R.id.fab);

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
        fragmentTransaction.add(R.id.container_body, new TicketList());
        fragmentTransaction.commit();
        setTitle(getResources().getStringArray(R.array.nav_list)[0]);

        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                fragmentTransaction = fragmentManager.beginTransaction();
                NewTicket newTicket = new NewTicket();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.container_body,  newTicket).addToBackStack(TAG_FRAGMENT[0]).commit();
                //fragmentTransaction.commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                addTicket.hide();
            }
        });

        //if(Settings)

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        Log.d("REQUEST = ","API23 HOMSCREEN");
        Log.w("Permission home screen", "Befor if "+requestCode);

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
        Toast.makeText(this, "HOME SCREEN  "+requestCode, Toast.LENGTH_SHORT).show();
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
                break;
            case 1:
                NewTicket ticket = new NewTicket();
                fragmentTransaction.replace(R.id.container_body, ticket).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                addTicket.hide();
                break;
            case 2:
                CalendarView calendarView = new CalendarView();
                fragmentTransaction.replace(R.id.container_body, calendarView).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[2]);
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                addTicket.hide();
                break;
            case 3:
                CompanySettingFrag companySettingFrag = new CompanySettingFrag();
                fragmentTransaction.replace(R.id.container_body, companySettingFrag).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[3]);
                addTicket.hide();
                break;
            case 4:
                Setting setting = new Setting();
                fragmentTransaction.replace(R.id.container_body, setting).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[4]);
                addTicket.hide();
                break;
            case 5:
                signOut();
                finish();
                //startActivity(new Intent(this, MainActivity.class));
                break;

            default:

                fragmentTransaction.replace(R.id.container_body, ticketList).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[0]);
        }
    }

    @Override
    public void onBackPressed() {
        //final NewTicket NEW_TICKET_FRAGMENT = (NewTicket)getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT[0]);
        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void signOut()
    {


        mGoogleApiClient=App.getGoogleApiHelper().getGoogleApiClient();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        Toast.makeText(context,"logout OK hom",Toast.LENGTH_LONG).show();

                        // [START_EXCLUDE]
                        //  updateUI(false);
                        // [END_EXCLUDE]
                    }
                });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
