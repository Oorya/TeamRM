package com.teamrm.teamrm.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.R;

import java.util.ArrayList;
import java.util.List;

public class NewTicket extends Fragment{

    Spinner selectProduct;
    Spinner selectCategoryA;
    //Spinner selectCategoryB;
    Spinner selectRegion;
TextView tv;

    String[] listProduct = {"בחר מוצר...","מקרר", "מיקרוגל", "תנור"};
    String[] listCategoryA = {"בחר סיווג...", "תקלה", "התקנה", "איסוף"};
    String[] listRegion = {"בחר אזור...", "צפון", "מרכז", "דרום"};

    public NewTicket() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);



        return view;
    }

        }

