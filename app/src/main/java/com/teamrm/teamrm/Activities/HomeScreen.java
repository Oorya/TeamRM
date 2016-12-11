package com.teamrm.teamrm.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.teamrm.teamrm.Fragment.CalendarView;
import com.teamrm.teamrm.Fragment.FragmentDrawer;
import com.teamrm.teamrm.Fragment.NewTicket;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.Fragment.TicketView;
import com.teamrm.teamrm.R;


public class HomeScreen extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

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
        setTitle(getResources().getString(R.string.ticket_list));

        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                fragmentTransaction = fragmentManager.beginTransaction();
                NewTicket newTicket = new NewTicket();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.container_body,  newTicket).addToBackStack(TAG_FRAGMENT[0]).commit();
                //fragmentTransaction.commit();
                setTitle(getResources().getString(R.string.new_ticket));
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
                setTitle(getResources().getString(R.string.ticket_list));
                break;
            case 1:
                TicketView ticket = new TicketView();
                fragmentTransaction.replace(R.id.container_body, ticket).addToBackStack(TAG_FRAGMENT[0]).commit();
                addTicket.hide();
                break;
            case 2:
                CalendarView calendarView = new CalendarView();
                fragmentTransaction.replace(R.id.container_body, calendarView).addToBackStack(TAG_FRAGMENT[0]).commit();
                addTicket.hide();
                break;
            case 6:
                NewTicket newTicket = new NewTicket();
                fragmentTransaction.replace(R.id.container_body, newTicket).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getString(R.string.new_ticket));
                addTicket.hide();
                break;

            default:

                fragmentTransaction.replace(R.id.container_body, ticketList).addToBackStack(TAG_FRAGMENT[0]).commit();
                setTitle(getResources().getString(R.string.ticket_list));
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
}
