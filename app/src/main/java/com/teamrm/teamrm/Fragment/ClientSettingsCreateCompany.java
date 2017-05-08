package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientSettingsCreateCompany extends Fragment {

    EditText companyAddressInput, companyPhoneInput, companyNameInput, companyMailInput;
    Button btnCreateCompany;

    public ClientSettingsCreateCompany() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_firm_details, container, false);
        companyAddressInput = (EditText)view.findViewById(R.id.txtAddress);
        companyPhoneInput = (EditText)view.findViewById(R.id.txtPhone);
        companyMailInput = (EditText)view.findViewById(R.id.companyMailText);
        companyNameInput = (EditText)view.findViewById(R.id.txtcompany);
        btnCreateCompany = (Button) view.findViewById(R.id.btnUpdateUser);

        btnCreateCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyNameInput.length() > 0 && companyAddressInput.length() > 0 && companyPhoneInput.length() > 0 && companyMailInput.length() > 0) {
                    Company newCompany = new Company(companyNameInput.getText().toString(), companyAddressInput.getText().toString(), companyPhoneInput.getText().toString(), companyMailInput.getText().toString());
                    UtlFirebase.addCompany(newCompany, new FireBaseBooleanCallback() {
                        @Override
                        public void booleanCallback(boolean isTrue) {
                            if (isTrue) {
                                new NiceToast(getContext(), "נדרש אתחול כדי לעדכן את ההגדרות החדשות", NiceToast.NICETOAST_WARNING, Toast.LENGTH_SHORT).show();
                                App.getInstance().signOut();
                            }
                        }
                    });

                } else {
                    new NiceToast(getContext(), "נא למלא את כל השדות", NiceToast.NICETOAST_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
