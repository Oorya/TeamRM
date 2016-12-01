package com.teamrm.teamrm.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.teamrm.teamrm.Fragment.LoginFragFB;
import com.teamrm.teamrm.R;

public class FireBaseLogin extends AppCompatActivity {

    LinearLayout container;
    FragmentTransaction ft;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_login);
        container=(LinearLayout)findViewById(R.id.fContainer);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fContainer, new LoginFragFB());
        ft.commit();
    }
}
