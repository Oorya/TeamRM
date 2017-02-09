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
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionHolder> implements PrefListable {

    private List<Region> regionList = new ArrayList<>();
    private Context prContext;
    private static final String TAG = "RegionAdapter:::";


    public RegionAdapter(Context context, List<Region> regionList) {
        this.prContext = context;
        Log.d(TAG, "called constructor");
        this.regionList = regionList;
    }

    @Override
    public RegionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_6, null);
        return new RegionHolder(view);
    }

    @Override
    public void onBindViewHolder(final RegionHolder holder, int position) {

        final Region regionItem = regionList.get(position);
        holder.regionName.setText(regionItem.getRegionName());
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(prContext);
                editText.setText(regionItem.getRegionName());
                new MaterialDialog.Builder(prContext)
                        .title(R.string.label_edit_prefitem_dialog_title)
                        .input("", regionItem.getRegionName(), new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                Log.d(TAG, "updating region " + regionItem.getRegionName());
                                UtlFirebase.updateRegion(UserSingleton.getInstance().getAssignedCompanyID(), regionItem, input.toString());
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
                new MaterialDialog.Builder(prContext)
                        .title(R.string.label_remove_text)
                        .content(regionList.get(holder.getAdapterPosition()).getRegionName())
                        .positiveText(R.string.label_button_confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                new MaterialDialog.Builder(prContext)
                                        .title(R.string.label_confirm_remove)
                                        .content(regionList.get(holder.getAdapterPosition()).getRegionName())
                                        .positiveText(R.string.label_button_confirm)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                UtlFirebase.removeRegion(UserSingleton.getInstance().getAssignedCompanyID(), regionItem);
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
        return (null != regionList ? regionList.size() : 0);
    }


    public class RegionHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected TextView regionName;
        protected ImageView iconEdit, iconRemove;

        public RegionHolder(View view) {
            super(view);
            this.view = view;
            this.regionName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
            this.iconRemove = (ImageView) view.findViewById(R.id.prefIconRemove);
        }

    }

}

