package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirmDetailsFrag extends Fragment {

    EditText address, phone, company;
    Button btnCreateCompany;

    public FirmDetailsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firm_details, container, false);
        address = (EditText)view.findViewById(R.id.txtAddress);
        phone = (EditText)view.findViewById(R.id.txtPhone);
        company = (EditText)view.findViewById(R.id.txtcompany);
        btnCreateCompany = (Button) view.findViewById(R.id.btnUpdateUser);
        final String USER_ID = UserSingleton.getInstance().getUserID();

        btnCreateCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = company.getText().toString();
                UtlFirebase.changeUserStatus(USER_ID, Users.STATUS_ADMIN, true);
                UtlFirebase.setCompany(USER_ID, companyName);
                Company company = new Company(companyName, USER_ID, address.getText().toString(),phone.getText().toString());
                UtlFirebase.saveCompany(company);

                Toast.makeText(getContext(), "נדרש אתחול כדי לעדכן את ההגדרות החדשות", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

