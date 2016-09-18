package com.teamrm.teamrm.Activities;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teamrm.teamrm.Fragment.FragmentDrawer;
import com.teamrm.teamrm.R;

public class navBar  extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

private Toolbar mToolbar;
private FragmentDrawer drawerFragment;

@Override
            protected void onCreate(Bundle savedInstanceState)
                {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_nev_bar);
        
                mToolbar=(Toolbar)findViewById(R.id.toolbar);
        
                setSupportActionBar(mToolbar);
                    
                    ActionBar ab =getSupportActionBar();
                    ab.setDisplayShowHomeEnabled(true);
        
                drawerFragment=(FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
                drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),mToolbar);
                drawerFragment.setDrawerListener(this);
               }


@Override
public boolean onCreateOptionsMenu(Menu menu)
        {
        // Inflate the menu; this adds items to the action bar if it is present.
           // getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       

        return super.onOptionsItemSelected(item);
        }

@Override
public void onDrawerItemSelected(View view, int position){

        }
        
}
