package com.teamrm.teamrm.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.RowViewLayout;
import com.teamrm.teamrm.Utility.UtlFirebase;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by r00t on 02/04/2017.
 */

public class EnrollmentCodeSection extends StatelessSection {

    public static final String TAG = "enrollmentCodeSection";

    static final int MAIL_SEND_SUCCESS = 1000;
    static final int PHONE_SEND_SUCCESS = 2000;

    private Context eContext;


    public EnrollmentCodeSection(Context context) {
        super(R.layout.technician_enrollment_section_header, R.layout.enrollment_code_card);
        Log.d(TAG, "called constructor");
        Log.d(TAG, "items: " + EnrollmentCode.getEnrollmentCodeList().size());
        eContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        //Log.d(TAG, "items: " + EnrollmentCode.getEnrollmentCodeList().size());
        return EnrollmentCode.getEnrollmentCodeList().size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EnrollmentCodeHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final EnrollmentCodeHolder ecHolder = (EnrollmentCodeHolder) holder;
        final EnrollmentCode ecListItem = EnrollmentCode.getEnrollmentCodeList().get(position);
        Log.d(TAG, ecListItem.toString());

        ecHolder.cardHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ecHolder.expandableLayout.toggle();
                rotateButtonOnToggle(ecHolder.expandableLayout, ecHolder.expandBtn);
            }
        });

        ecHolder.ecString.setText(ecListItem.getEnrollmentCodeString());


        ecHolder.ecStatus.setText(getStatusString(ecListItem));

        if (ecListItem.isSentToMail()) {
            ecHolder.ecMailNotSentRow.setVisibility(View.GONE);
            ecHolder.ecMailSentRow.setVisibility(View.VISIBLE);
            ecHolder.ecSentToMail.setText(ecListItem.getEnrollmentCodeSentToMail());
        } else {
            ecHolder.ecMailSentRow.setVisibility(View.GONE);
            ecHolder.ecMailNotSentRow.setVisibility(View.VISIBLE);
            ecHolder.ecSendMailAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int resultCode = 0;
                    //TODO: send mail and check if success
                    //resultCode = ...
                    if (resultCode == MAIL_SEND_SUCCESS) {
                        HashMap<String, Object> ecItemUpdates = new HashMap<>();
                        ecItemUpdates.put(EnrollmentCode.IS_SENT_TO_MAIL, true);
                        ecItemUpdates.put(EnrollmentCode.ENROLLMENT_CODE_SENT_TO_MAIL, ecHolder.ecMailInput.getText().toString());
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), ecItemUpdates);
                    } else {
                        new NiceToast(eContext, "Could not send mail", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if (ecListItem.isSentToPhone()) {
            ecHolder.ecPhoneNotSentRow.setVisibility(View.GONE);
            ecHolder.ecPhoneSentRow.setVisibility(View.VISIBLE);
            ecHolder.ecSentToPhone.setText(ecListItem.getEnrollmentCodeSentToPhone());
        } else {
            ecHolder.ecPhoneSentRow.setVisibility(View.GONE);
            ecHolder.ecPhoneNotSentRow.setVisibility(View.VISIBLE);
            ecHolder.ecSendPhoneAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int resultCode = 0;
                    //TODO: send SMS and check if success
                    //resultCode = ...
                    if (resultCode == PHONE_SEND_SUCCESS) {
                        HashMap<String, Object> ecItemUpdates = new HashMap<>();
                        ecItemUpdates.put(EnrollmentCode.IS_SENT_TO_PHONE, true);
                        ecItemUpdates.put(EnrollmentCode.ENROLLMENT_CODE_SENT_TO_PHONE, ecHolder.ecPhoneInput.getText().toString());
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), ecItemUpdates);
                    } else {
                        new NiceToast(eContext, "Could not send SMS", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        switch (ecListItem.getEnrollmentStatus()){
            case (EnrollmentCode.STATUS_CANCELLED):
                //
                break;

            case (EnrollmentCode.STATUS_PENDING):
                ecHolder.btnRemoveRow.setVisibility(View.GONE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.VISIBLE);
                ecHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> updates = new HashMap<>();
                        updates.put(EnrollmentCode.ENROLLMENT_STATUS, EnrollmentCode.STATUS_ACCEPTED);
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), updates);
                    }
                });
                ecHolder.btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> updates = new HashMap<>();
                        updates.put(EnrollmentCode.ENROLLMENT_STATUS, EnrollmentCode.STATUS_DECLINED);
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), updates);
                    }
                });
        }
        ecHolder.btnRemoveEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ecListItem.isSentToMail() || ecListItem.isSentToPhone()) {
                    new AlertDialog.Builder(eContext)
                            .setCancelable(true)
                            .setMessage("Code already sent \n still delete?")
                            .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UtlFirebase.removeEnrollmentCode(ecListItem);
                                }
                            })
                            .show();
                } else {
                    UtlFirebase.removeEnrollmentCode(ecListItem);
                }
            }
        });

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder headerHolder) {
        EnrollmentCodeHeaderHolder enrollmentCodeHeaderHolder = (EnrollmentCodeHeaderHolder) headerHolder;
        enrollmentCodeHeaderHolder.headerString.setText("רשימת קודי הרשמה");

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new EnrollmentCodeHeaderHolder(view);
    }


    private class EnrollmentCodeHeaderHolder extends RecyclerView.ViewHolder {
        TextView headerString;

        EnrollmentCodeHeaderHolder(View view) {
            super(view);
            headerString = (TextView) view.findViewById(R.id.headerTitle);
        }
    }

    private class EnrollmentCodeHolder extends RecyclerView.ViewHolder {

        private ExpandableLayout expandableLayout;
        private View cardHeaderView;

        private TextView ecString;

        private TextView ecStatus;

        private RowViewLayout ecMailSentRow;
        private RowViewLayout ecMailNotSentRow;
        private EditText ecMailInput;
        private TextView ecSentToMail;
        private TextView ecSendMailAction;

        private RowViewLayout ecPhoneSentRow;
        private RowViewLayout ecPhoneNotSentRow;
        private EditText ecPhoneInput;
        private TextView ecSentToPhone;
        private TextView ecSendPhoneAction;

        private RowViewLayout btnRemoveRow;
        private View btnRemoveEC;

        private RowViewLayout btnAcceptDeclineRow;
        private View btnAccept, btnDecline;

        private ImageView expandBtn;


        EnrollmentCodeHolder(View view) {
            super(view);
            Log.d(TAG, "::: called viewholder");

            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandableLayout);
            expandBtn = (ImageView) view.findViewById(R.id.expandButton);
            cardHeaderView = view.findViewById(R.id.enrollmentCodeRow);

            ecString = (TextView) view.findViewById(R.id.enrollmentCodeString);

            ecStatus = (TextView) view.findViewById(R.id.enrollmentStatusText);

            ecMailNotSentRow = (RowViewLayout) view.findViewById(R.id.enrollmentSendMailRow);
            ecMailSentRow = (RowViewLayout) view.findViewById(R.id.enrollmentMailSentRow);
            ecMailInput = (EditText) view.findViewById(R.id.enrollmentSendMailInput);
            ecSentToMail = (TextView) view.findViewById(R.id.enrollmentMailSentText);
            ecSendMailAction = (TextView) view.findViewById(R.id.enrollmentSendMailAction);

            ecPhoneNotSentRow = (RowViewLayout) view.findViewById(R.id.enrollmentSendPhoneRow);
            ecPhoneSentRow = (RowViewLayout) view.findViewById(R.id.enrollmentPhoneSentRow);
            ecPhoneInput = (EditText) view.findViewById(R.id.enrollmentSendPhoneInput);
            ecSentToPhone = (TextView) view.findViewById(R.id.enrollmentPhoneSentText);
            ecSendPhoneAction = (TextView) view.findViewById(R.id.enrollmentSendPhoneAction);

            btnRemoveRow = (RowViewLayout)view.findViewById(R.id.btnRemoveRow);
            btnRemoveEC = view.findViewById(R.id.btnRemove);

            btnAcceptDeclineRow = (RowViewLayout)view.findViewById(R.id.acceptOrDeclineRow);
            btnAccept = view.findViewById(R.id.btnAccept);
            btnDecline = view.findViewById(R.id.btnDecline);

        }
    }

    void rotateButtonOnToggle(ExpandableLayout expandableLayout, ImageView expandBtn) {
        expandBtn.animate().rotation(expandBtn.getRotation() - 180f).setInterpolator(new LinearInterpolator()).start();
    }

    private String getStatusString(EnrollmentCode ec){
        switch (ec.getEnrollmentStatus()){
            case EnrollmentCode.STATUS_ISSUED:
                return "Issued";

            case EnrollmentCode.STATUS_PENDING:
                return "Pending";

            case EnrollmentCode.STATUS_CANCELLED:
                return "Cancelled";

            default:
                return ec.getEnrollmentStatus() + "";

        }
    }

}
