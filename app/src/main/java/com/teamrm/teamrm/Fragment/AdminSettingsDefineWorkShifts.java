package com.teamrm.teamrm.Fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.NumberPicker;

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
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineWorkShifts extends Fragment implements WorkShiftCallback {

    final static String TAG = "WorkShiftFrag";
    List<WorkShift> workShiftList = new ArrayList<>();
    public RecyclerView workShiftView;
    WorkShiftAdapter workShiftAdapter;
    FloatingActionButton floatBtn;
    String startHour = "", startMinute = "", endHour = "", endMinute = "";
    String[] hoursArray;
    String[] quarterHoursArray;


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

        hoursArray = getActivity().getResources().getStringArray(R.array.hour_array);
        Log.d(TAG, Arrays.toString(hoursArray));
        quarterHoursArray = getActivity().getResources().getStringArray(R.array.quarter_hour);
        Log.d(TAG, Arrays.toString(quarterHoursArray));

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
                showAddWorkShiftDialog();
            }
        });

        return view;
    }

    private void showAddWorkShiftDialog() {

        final MaterialDialog addWorkShiftDialog = new MaterialDialog.Builder(getContext())
                .title(R.string.label_add_workshift)
                .customView(R.layout.time_picker_work_shift, false)
                /*.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(getContext(), "positive", Toast.LENGTH_SHORT).show();
                    }
                })*/
                .inputMaxLength(20)
                .positiveText(R.string.label_button_save)
                .contentColorRes(R.color.textColor_primary)
                .contentGravity(GravityEnum.CENTER)
                .negativeText(R.string.label_button_cancel)
                .titleGravity(GravityEnum.END)
                .buttonsGravity(GravityEnum.END)
                .backgroundColorRes(R.color.app_bg)
                .widgetColorRes(R.color.textColor_primary)
                .titleColorRes(R.color.textColor_lighter)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .dividerColorRes(R.color.textColor_lighter)
                .build();

        View timePickerView = addWorkShiftDialog.getCustomView();
        final EditText workShiftNameInput = (EditText) timePickerView.findViewById(R.id.workShiftName);
        final NumberPicker wsStartHourPicker = (NumberPicker) timePickerView.findViewById(R.id.wsStartHour);
        final NumberPicker wsStartMinutePicker = (NumberPicker) timePickerView.findViewById(R.id.wsStartMinute);
        final NumberPicker wsEndHourPicker = (NumberPicker) timePickerView.findViewById(R.id.wsEndHour);
        final NumberPicker wsEndMinutePicker = (NumberPicker) timePickerView.findViewById(R.id.wsEndMinute);


        pickerSetup(wsStartHourPicker, hoursArray);
        pickerSetup(wsStartMinutePicker, quarterHoursArray);
        pickerSetup(wsEndHourPicker, hoursArray);
        pickerSetup(wsEndMinutePicker, quarterHoursArray);
        startHour = hoursArray[wsStartHourPicker.getValue()];
        startMinute = quarterHoursArray[wsStartMinutePicker.getValue()];
        endHour = hoursArray[wsEndHourPicker.getValue()];
        endMinute = quarterHoursArray[wsEndMinutePicker.getValue()];

        wsStartHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                startHour = hoursArray[i1];
            }
        });

        wsStartMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                startMinute = quarterHoursArray[i1];
            }
        });

        wsEndHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                endHour = hoursArray[i1];
            }
        });

        wsEndMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                endMinute = quarterHoursArray[i1];
            }
        });

        View btnPositive = addWorkShiftDialog.getActionButton(DialogAction.POSITIVE);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wsStartHourPicker.clearFocus();
                wsStartMinutePicker.clearFocus();
                String resolvedWorkShiftNameString = workShiftNameInput.getText().toString();
                String resolvedWorkShiftStartString = startHour + ":" + startMinute;
                String resolvedWorkShiftEndString = endHour + ":" + endMinute;

                UtlFirebase.addWorkShift(UserSingleton.getInstance().getAssignedCompanyID(), new WorkShift(resolvedWorkShiftNameString, resolvedWorkShiftStartString, resolvedWorkShiftEndString));
                addWorkShiftDialog.dismiss();
            }
        });

        addWorkShiftDialog.show();
    }

    @Override
    public void workShiftFireBaseCallback(ArrayList<WorkShift> workShifts) {
        workShiftList = workShifts;
        workShiftAdapter = new WorkShiftAdapter(getContext(), workShiftList);
        workShiftView.setAdapter(workShiftAdapter);
    }

    void pickerSetup(NumberPicker picker, String[] values) {
        picker.setDisplayedValues(values);
        picker.setMinValue(0);
        picker.setMaxValue(values.length - 1);
        picker.setWrapSelectorWheel(true);

    }
}
