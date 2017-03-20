package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Interfaces.PrefListable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.WorkShift;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class WorkShiftAdapter extends RecyclerView.Adapter<WorkShiftAdapter.WorkShiftHolder> implements PrefListable {

    List<WorkShift> workShiftList = new ArrayList<>();
    Context cContext;
    private static final String TAG = "WorkShiftAdapter:::";


    public WorkShiftAdapter(Context context, List<WorkShift> workShiftList) {
        this.cContext = context;
        Log.d(TAG, "called constructor");
        this.workShiftList = workShiftList;
    }

    @Override
    public WorkShiftHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_6, null);
        return new WorkShiftHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkShiftHolder holder, final int position) {

        final WorkShift workShiftItem = workShiftList.get(position);
        holder.workShiftName.setText(workShiftItem.getWorkShiftName());
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(cContext);
                editText.setText(workShiftItem.getWorkShiftName());
                new MaterialDialog.Builder(cContext)
                        .title(R.string.label_edit_prefitem_dialog_title)
                        .input("", workShiftItem.getWorkShiftName(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Log.d(TAG, "updating workShift " + workShiftItem.getWorkShiftName());
                                UtlFirebase.updateWorkShift(UserSingleton.getInstance().getAssignedCompanyID(), workShiftItem);
                            }
                        })
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
        });

        holder.iconRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(cContext)
                        .title(R.string.label_remove_text)
                        .content(workShiftList.get(position).getWorkShiftName())
                        .positiveText(R.string.label_button_confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                new MaterialDialog.Builder(cContext)
                                        .title(R.string.label_confirm_remove)
                                        .content(workShiftList.get(position).getWorkShiftName())
                                        .positiveText(R.string.label_button_confirm)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                UtlFirebase.removeWorkShift(UserSingleton.getInstance().getAssignedCompanyID(), workShiftItem);
                                            }
                                        })
                                        .contentColorRes(R.color.textColor_primary)
                                        .contentGravity(GravityEnum.CENTER)
                                        .negativeText(R.string.label_button_cancel)
                                        .titleGravity(GravityEnum.END)
                                        .buttonsGravity(GravityEnum.END)
                                        .backgroundColorRes(R.color.meterial_red_100)
                                        .titleColorRes(R.color.textColor_lighter)
                                        .positiveColorRes(R.color.colorPrimary)
                                        .negativeColorRes(R.color.colorPrimaryDark)
                                        .dividerColorRes(R.color.textColor_lighter)
                                        .show();
                            }
                        })
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
        });
    }

    @Override
    public int getItemCount() {
        return (null != workShiftList ? workShiftList.size() : 0);
    }


    public class WorkShiftHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected TextView workShiftName;
        protected ImageView iconEdit, iconRemove;

        public WorkShiftHolder(View view) {
            super(view);
            this.view = view;
            this.workShiftName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
            this.iconRemove = (ImageView) view.findViewById(R.id.prefIconRemove);
        }

    }

}

