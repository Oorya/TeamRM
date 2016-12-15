package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFrag extends Fragment implements FireBaseAble {

    EditText address, phone, company;
    RadioButton createCompany;
    Button updateUser;
    Users user;

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
        UtlFirebase.getUserByKey(MainActivity.userId,this);

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createCompany.isChecked())
                {
                    String companyName = company.getText().toString();
                    UtlFirebase.changeUserStatus(MainActivity.userId, "Admin");
                    UtlFirebase.setCompany(MainActivity.userId, companyName);
                    Company company = new Company(companyName, MainActivity.userId);
                    company.saveCompany("Company");

                    Toast.makeText(getContext(), "נדרש אתחול כדי לעדכן את ההגדרות החדשות", Toast.LENGTH_SHORT).show();
                }
                else {
                    UtlFirebase.updateClient(MainActivity.userId, address.getText().toString(), phone.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {
        this.user=user;
    }

    @Override
    public void resultList(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }
}
