package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Adapter.WorkShiftAdapter;
import com.teamrm.teamrm.Interfaces.WorkShiftCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.WorkShift;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineWorkShifts extends Fragment implements WorkShiftCallback{

    final static String TAG = ":::Settings:Categories:::";
    List<WorkShift> workShiftList = new ArrayList<>();
    public RecyclerView workShiftView;
    WorkShiftAdapter workShiftAdapter;
    FloatingActionButton floatBtn;


    public AdminSettingsDefineWorkShifts() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        UtlFirebase.getWorkShiftsForEdit(UserSingleton.getInstance().getAssignedCompanyID(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);
        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();
        workShiftView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        workShiftView.setLayoutManager(new LinearLayoutManager(getContext()));


        workShiftView.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 250);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkshift();
            }
        });

        return view;
    }

    private void addWorkshift() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.label_add_workshift)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Toast.makeText(getContext(),  input.toString(), Toast.LENGTH_SHORT).show();
                        UtlFirebase.addWorkShift(UserSingleton.getInstance().getAssignedCompanyID(), new WorkShift(input.toString(), "startShift", "endShift"));
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(getContext(), "positive", Toast.LENGTH_SHORT).show();
                    }
                })
                .inputMaxLength(20)
                .positiveText(R.string.label_button_save)
                .contentColorRes(R.color.textColor_primary)
                .contentGravity(GravityEnum.CENTER)
                .negativeText(R.string.label_button_cancel)
                .titleGravity(GravityEnum.END)
                .buttonsGravity(GravityEnum.END)
                .backgroundColorRes(R.color.app_bg)
                .titleColorRes(R.color.textColor_lighter)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .dividerColorRes(R.color.textColor_lighter)
                .show();
    }

    @Override
    public void workShiftFireBaseCallback(ArrayList<WorkShift> workShifts) {
        workShiftList = workShifts;
        workShiftAdapter = new WorkShiftAdapter(getContext(), workShiftList);
        workShiftView.setAdapter(workShiftAdapter);
    }
}
