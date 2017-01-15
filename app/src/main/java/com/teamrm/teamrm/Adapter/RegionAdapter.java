package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.Interfaces.PrefListable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionHolder> implements PrefListable {

    List<Region> regionList = new ArrayList<>();
    Context rContext;


    public RegionAdapter(Context context, List<Region> regionList) {
        this.rContext = context;
        this.regionList = regionList;
    }

    @Override
    public RegionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_1, null);
        RegionHolder cHolder = new RegionHolder(view);
        return cHolder;
    }

    @Override
    public void onBindViewHolder(final RegionHolder holder, final int position) {

        final Region regionItem = regionList.get(position);
        holder.regionName.setText(regionItem.getRegionName());
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(rContext);
                editText.setText(regionItem.getRegionName());
                new AlertDialog.Builder(rContext)
                        .setCancelable(true)
                        .setView(editText)
                        .setTitle(R.string.label_edit_prefitem_dialog_title)
                        .setPositiveButton(R.string.label_button_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RegionAdapter.super.notifyItemChanged(position);
                            }
                        })
                        .setNegativeButton(R.string.label_button_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
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
        protected String regionID;
        protected TextView regionName;
        protected ImageView iconEdit;

        public RegionHolder(View view) {
            super(view);

            this.view = view;
            this.regionName = (TextView) view.findViewById(R.id.prefText);
            this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
        }

    }
}

