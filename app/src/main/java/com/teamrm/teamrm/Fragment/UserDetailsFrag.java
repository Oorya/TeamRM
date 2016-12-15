package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.UtlFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFrag extends Fragment {

    EditText address, phone, company;
    RadioButton createCompany;
    Button updateUser;

    public UserDetailsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        address = (EditText)view.findViewById(R.id.txtAddress);
        phone = (EditText)view.findViewById(R.id.txtPhone);
        company = (EditText)view.findViewById(R.id.txtcompany);
        createCompany = (RadioButton)view.findViewById(R.id.adminRadio);
        updateUser = (Button) view.findViewById(R.id.btnUpdateUser);

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createCompany.isChecked())
                {
                    UtlFirebase.removeClient(MainActivity.userId);
                }
                else {
                    UtlFirebase.updateClient(MainActivity.userId, address.getText().toString(), phone.getText().toString());
                }
            }
        });

        return view;
    }

}
