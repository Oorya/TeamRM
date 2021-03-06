package com.teamrm.teamrm.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.Interfaces.PendingTechSingleCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.PendingTech;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.RowViewLayout;
import com.teamrm.teamrm.Utility.UserSingleton;
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
                rotateButtonOnToggle(ecHolder.expandBtn);
            }
        });

        ecHolder.ecString.setText(ecListItem.getEnrollmentCodeString());

        switch (ecListItem.getEnrollmentStatus()) {

            case (EnrollmentCode.STATUS_ISSUED):
                ecHolder.ecStatusRow.setBackgroundResource(R.color.listRow_alt);
                ecHolder.ecStatusLabel.setTextColor(ContextCompat.getColor(eContext, R.color.textColor_lighter));
                ecHolder.ecStatusString.setTextColor(ContextCompat.getColor(eContext, R.color.textColor_primary));
                ecHolder.ecSendCodeRow.setVisibility(View.VISIBLE);
                ecHolder.pendingTechNameRow.setVisibility(View.GONE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.GONE);
                ecHolder.rowSetPendingTechDetails.setVisibility(View.GONE);
                ecHolder.rowSetIssuedCode.setVisibility(View.VISIBLE);
                ecHolder.btnRemoveRow.setVisibility(View.VISIBLE);

                String ecStatusString;
                if (ecListItem.isSentToMail() && ecListItem.isSentToPhone()) {
                    ecStatusString = "קוד נשלח בדואל וב-SMS";
                } else if (ecListItem.isSentToMail()) {
                    ecStatusString = "קוד נשלח בדואל";
                } else if (ecListItem.isSentToPhone()) {
                    ecStatusString = "קוד נשלח ב-SMS";
                } else {
                    ecStatusString = "קוד טרם נשלח";
                }
                ecHolder.ecStatusString.setText(ecStatusString);

                ecHolder.ecSendMailAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse("mailto:?to=" + "");
                        intent.putExtra(Intent.EXTRA_TEXT, ecListItem.getEnrollmentCodeString());
                        intent.setData(data);
                        eContext.startActivity(intent);
                        HashMap<String, Object> ecItemUpdates = new HashMap<>();
                        ecItemUpdates.put(EnrollmentCode.IS_SENT_TO_MAIL, true);
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), ecItemUpdates);

                    }
                });


                ecHolder.ecSendPhoneAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
                        {
                            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(eContext); // Need to change the build to API 19

                            Intent sendIntent = new Intent(Intent.ACTION_SEND);
                            sendIntent.setType("text/plain");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, ecListItem.getEnrollmentCodeString());
                            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
                            // any app that support this intent.
                            {
                                sendIntent.setPackage(defaultSmsPackageName);
                            }
                            eContext.startActivity(sendIntent);

                        } else // For early versions, do what worked for you before.
                        {

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            //Uri data = Uri.parse("sms:" + ecHolder.ecPhoneInput.getText().toString());
                            Uri data = Uri.parse("sms:" + "");
                            intent.setData(data);
                            intent.putExtra("sms_body", ecListItem.getEnrollmentCodeString());
                            eContext.startActivity(intent);
                        }

                        HashMap<String, Object> ecItemUpdates = new HashMap<>();
                        ecItemUpdates.put(EnrollmentCode.IS_SENT_TO_PHONE, true);
                        UtlFirebase.updateEnrollmentCode(ecListItem.getEnrollmentCodeID(), ecItemUpdates);

                    }
                });


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
                break;

            case (EnrollmentCode.STATUS_PENDING):
                ecHolder.ecStatusRow.setBackgroundResource(R.color.status_urgent);
                ecHolder.ecStatusLabel.setTextColor(Color.parseColor("#AAFFFFFF"));
                ecHolder.ecStatusString.setTextColor(Color.WHITE);
                ecHolder.ecStatusString.setText("ממתין לאישור");
                UtlFirebase.getPendingTechByID(ecListItem.getEnrolledTechUserID(), new PendingTechSingleCallback() {
                    @Override
                    public void pendingTechCallback(PendingTech _pendingTech) {
                        ecHolder.pendingTechUserNameString.setText(_pendingTech.getUserNameString());
                        ecHolder.pendingTechMailText.setText(_pendingTech.getUserEmail());
                        ecHolder.pendingTechPhoneText.setText(_pendingTech.getUserPhone());
                    }
                });
                ecHolder.rowSetPendingTechDetails.setVisibility(View.VISIBLE);
                ecHolder.pendingTechNameRow.setVisibility(View.VISIBLE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.VISIBLE);
                ecHolder.ecSendCodeRow.setVisibility(View.GONE);
                ecHolder.rowSetIssuedCode.setVisibility(View.GONE);
                ecHolder.btnRemoveRow.setVisibility(View.GONE);

                ecHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtlFirebase.getPendingTechByID(ecListItem.getEnrolledTechUserID(), new PendingTechSingleCallback() {
                            @Override
                            public void pendingTechCallback(PendingTech _pendingTech) {
                                Log.d(UserSingleton.TE_SEQ, "registering Technician from PendingTech " + _pendingTech.toString());
                                UtlFirebase.acceptNewTechnician(ecListItem, _pendingTech, new FireBaseBooleanCallback() {
                                    @Override
                                    public void booleanCallback(boolean isTrue) {
                                        if (!isTrue) {
                                            new NiceToast(eContext, "Could not add Technician, try again later", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        });
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
                break;

            case (EnrollmentCode.STATUS_CANCELLED):
                ecHolder.ecStatusRow.setBackgroundResource(R.color.status_error);
                ecHolder.ecStatusLabel.setTextColor(Color.parseColor("#AAFFFFFF"));
                ecHolder.ecStatusString.setTextColor(Color.WHITE);
                ecHolder.ecStatusString.setText("הרשמה בוטלה עי הטכנאי");
                UtlFirebase.getPendingTechByID(ecListItem.getEnrolledTechUserID(), new PendingTechSingleCallback() {
                    @Override
                    public void pendingTechCallback(PendingTech _pendingTech) {
                        ecHolder.pendingTechUserNameString.setText(_pendingTech.getUserNameString());
                        ecHolder.pendingTechMailText.setText(_pendingTech.getUserEmail());
                        ecHolder.pendingTechPhoneText.setText(_pendingTech.getUserPhone());
                    }
                });
                ecHolder.pendingTechNameRow.setVisibility(View.VISIBLE);
                ecHolder.rowSetPendingTechDetails.setVisibility(View.VISIBLE);
                ecHolder.btnRemoveRow.setVisibility(View.VISIBLE);
                ecHolder.rowSetIssuedCode.setVisibility(View.GONE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.GONE);

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
                break;

            case (EnrollmentCode.STATUS_ACCEPTED):
                ecHolder.ecStatusRow.setBackgroundResource(R.color.status_ok);
                ecHolder.ecStatusLabel.setTextColor(Color.parseColor("#AAFFFFFF"));
                ecHolder.ecStatusString.setTextColor(Color.WHITE);
                ecHolder.pendingTechNameRow.setVisibility(View.GONE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.GONE);
                ecHolder.rowSetPendingTechDetails.setVisibility(View.GONE);
                ecHolder.rowSetIssuedCode.setVisibility(View.GONE);
                ecHolder.btnRemoveRow.setVisibility(View.GONE);
                ecHolder.ecStatusString.setText("נרשם כטכנאי");
                break;

            case (EnrollmentCode.STATUS_DECLINED):
                ecHolder.ecStatusRow.setBackgroundResource(R.color.icon_gray);
                ecHolder.ecStatusLabel.setTextColor(Color.parseColor("#AAFFFFFF"));
                ecHolder.ecStatusString.setTextColor(Color.WHITE);
                ecHolder.pendingTechNameRow.setVisibility(View.GONE);
                ecHolder.btnAcceptDeclineRow.setVisibility(View.GONE);
                ecHolder.rowSetPendingTechDetails.setVisibility(View.GONE);
                ecHolder.rowSetIssuedCode.setVisibility(View.GONE);
                ecHolder.btnRemoveRow.setVisibility(View.GONE);
                ecHolder.ecStatusString.setText("נדחה");
                break;
        }


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

        private RowSetLayout rowSetIssuedCode;

        private ExpandableLayout expandableLayout;
        private View cardHeaderView;

        private TextView ecString;

        private RowViewLayout ecStatusRow;
        private TextView ecStatusLabel, ecStatusString;

        private RowViewLayout ecSendCodeRow;
        private Button ecSendMailAction;
        private Button ecSendPhoneAction;

        private View btnRemoveEC;

        private RowViewLayout pendingTechNameRow, btnRemoveRow, btnAcceptDeclineRow;

        private RowSetLayout rowSetPendingTechDetails;
        private TextView pendingTechUserNameString, pendingTechMailText, pendingTechPhoneText;
        private View btnAccept, btnDecline;

        private ImageView expandBtn;


        EnrollmentCodeHolder(View view) {
            super(view);
            Log.d(TAG, "::: called viewholder");

            rowSetIssuedCode = (RowSetLayout) view.findViewById(R.id.rowSetIssuedCode);

            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandableLayout);
            expandBtn = (ImageView) view.findViewById(R.id.expandButton);
            cardHeaderView = view.findViewById(R.id.enrollmentCodeRow);

            ecString = (TextView) view.findViewById(R.id.enrollmentCodeString);

            ecStatusRow = (RowViewLayout) view.findViewById(R.id.enrollmentStatusRow);
            ecStatusLabel = (TextView) view.findViewById(R.id.enrollmentStatusLabel);
            ecStatusString = (TextView) view.findViewById(R.id.enrollmentStatusText);

            ecSendCodeRow = (RowViewLayout) view.findViewById(R.id.ecSendCodeRow);
            ecSendMailAction = (Button) view.findViewById(R.id.btnSendMail);
            ecSendPhoneAction = (Button) view.findViewById(R.id.btnSendPhone);


            btnRemoveEC = view.findViewById(R.id.btnRemove);

            pendingTechNameRow = (RowViewLayout) view.findViewById(R.id.pendingTechNameRow);
            btnRemoveRow = (RowViewLayout) view.findViewById(R.id.btnRemoveRow);
            btnAcceptDeclineRow = (RowViewLayout) view.findViewById(R.id.acceptOrDeclineRow);

            rowSetPendingTechDetails = (RowSetLayout) view.findViewById(R.id.rowSetPendingTechDetails);

            pendingTechUserNameString = (TextView) view.findViewById(R.id.pendingTechUserNameString);
            pendingTechMailText = (TextView) view.findViewById(R.id.pendingTechMailText);
            pendingTechPhoneText = (TextView) view.findViewById(R.id.pendingTechPhoneText);

            btnAccept = view.findViewById(R.id.btnAccept);
            btnDecline = view.findViewById(R.id.btnDecline);

        }
    }

    void rotateButtonOnToggle(ImageView expandBtn) {
        expandBtn.animate().rotation(expandBtn.getRotation() - 180f).setInterpolator(new LinearInterpolator()).start();
    }

   /* private String getStatusString(EnrollmentCode ec) {
        switch (ec.getEnrollmentStatus()) {
            case EnrollmentCode.STATUS_ISSUED:
                return "Issued";

            case EnrollmentCode.STATUS_PENDING:
                return "Pending";

            case EnrollmentCode.STATUS_CANCELLED:
                return "Cancelled";

            case EnrollmentCode.STATUS_DECLINED:
                return "Declined";

            case EnrollmentCode.STATUS_ACCEPTED:
                return "Accepted";

            case EnrollmentCode.STATUS_FINALIZED:
                return "Finalized";

            default:
                return ec.getEnrollmentStatus() + "";

        }
    }*/

}
