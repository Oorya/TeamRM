package com.teamrm.teamrm.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Interfaces.CalendarHelper;
import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.CalendarUtil;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsAppPrefs extends Fragment implements CalendarHelper{

    Context context;
    Button button,delitCal,delTickets, registerAsTech;
    CalendarUtil calendarUtil;
    String calId;
    EditText enrollmentCodeInput;

    public AdminSettingsAppPrefs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_admin_settings_app_prefs, container, false);
        delTickets = (Button) V.findViewById(R.id.delTickets);
        delTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllTickets();
            }
        });
        enrollmentCodeInput = (EditText)V.findViewById(R.id.enrollmentCodeInput);
        registerAsTech = (Button) V.findViewById(R.id.registerAsTech);
        registerAsTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClientAsTech();
            }
        });
        return V;

    }

    @Override
    public void getResult(Event event) {

    }

    @Override
    public void getEventList(List<Event> eventUtil) {

    }

    @Override
    public void getCalendar(Calendar calendar) {

        calId = calendar.getId();
    }

    void deleteAllTickets(){
        new MaterialDialog.Builder(getContext())
                .title("delete all tickets and ticket states")
                .content("sure about that?")
                .positiveText("delete all")
                .negativeText("cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UtlFirebase.removeAllTickets();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    void registerClientAsTech(){
        if (UserSingleton.getLoadedUserType().equals(Users.STATUS_CLIENT) && enrollmentCodeInput.length() > 3) {
            UtlFirebase.enrollPendingTech(enrollmentCodeInput.getText().toString(), new FireBaseBooleanCallback() {
                @Override
                public void booleanCallback(boolean isTrue) {
                    if (isTrue) {
                        new NiceToast(App.getInstance().getApplicationContext(), "PendingTech enrolled successfully", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
                        App.getInstance().signOut();
                    } else {
                        new NiceToast(App.getInstance().getApplicationContext(), "Could not enroll PendingTech", NiceToast.NICETOAST_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
