package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.EditTextValidation;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlBitmapUrl;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDetails extends Fragment {

    public static final String FRAGMENT_TRANSACTION = "EditUserDetails";

    public EditUserDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_details_edit, container, false);
        initFields(view);
        return view;
    }

    private void initFields(View view) throws NullPointerException {
        final EditText userName = (EditText) view.findViewById(R.id.userNameCardOpen);
        final EditText address = (EditText) view.findViewById(R.id.txtAddress);
        final EditText phone = (EditText) view.findViewById(R.id.txtPhone);
        TextView mail = (TextView) view.findViewById(R.id.userMailCardOpen);
        ImageView userImage = (ImageView) view.findViewById(R.id.userIcon);
        Spinner region = (Spinner) view.findViewById(R.id.selectRegionSpinner);

        userName.setText(UserSingleton.getInstance().getUserNameString());
        mail.setText(UserSingleton.getInstance().getUserEmail());
        address.setText(UserSingleton.getInstance().getUserAddress() == null ? "" : UserSingleton.getInstance().getUserAddress());
        phone.setText(UserSingleton.getInstance().getUserPhone() == null ? "" : UserSingleton.getInstance().getUserPhone());
        if (null != UserSingleton.getInstance().getUserImgPath()) {
            UtlBitmapUrl bitmapUrl = new UtlBitmapUrl(userImage);
            bitmapUrl.execute(UserSingleton.getInstance().getUserImgPath().toString());
        }

        view.findViewById(R.id.btnUpdateDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EditTextValidation.checkEt(Arrays.asList(userName, phone, address))
                        && EditTextValidation.checkPhoneRegex(phone)) {

                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put(UserSingleton.USER_PHONE,phone.getText().toString() );
                    updates.put(UserSingleton.USER_NAME_STRING,userName.getText().toString() );
                    updates.put(UserSingleton.USER_ADDRESS,address.getText().toString() );
                    updates.put(UserSingleton.USER_LAST_SEEN, Calendar.getInstance().toString());
                    UtlFirebase.updateUser(UserSingleton.getInstance().getUserID(),updates);

                    new NiceToast(getContext(), "הפרטים עודכנו בהצלחה", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
