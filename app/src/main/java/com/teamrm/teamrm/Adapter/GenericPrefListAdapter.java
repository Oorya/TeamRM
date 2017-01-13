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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class GenericPrefListAdapter extends RecyclerView.Adapter<GenericPrefListAdapter.PrefHolder> implements PrefListable {

    List<Object> prefList = new ArrayList<>();
    Context pContext;

    int viewType;

    public GenericPrefListAdapter(Context context, List<Object> prefList, int viewType) { //TODO: pass reference to DB as a parameter or use some other binding
        this.pContext = context;
        this.prefList = prefList;
        this.viewType = viewType;
    }

    @Override
    public PrefHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case PrefListable.TEXTVIEW_AND_EDIT_ICON: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pref_adapter_row_1, null);
                PrefHolder prefHolder = new PrefHolder(view, PrefListable.TEXTVIEW_AND_EDIT_ICON);
                break;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final GenericPrefListAdapter.PrefHolder holder, final int position) {

        switch (viewType) {
            case PrefListable.TEXTVIEW_AND_EDIT_ICON: {
                final String[] prefItem = (String[])prefList.get(position);
                holder.itemID = prefItem[0];
                holder.itemString.setText(prefItem[1]);
                holder.iconEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final EditText editText = new EditText(pContext);
                        editText.setText(prefItem[1]);
                        new AlertDialog.Builder(pContext)
                                .setCancelable(true)
                                .setView(editText)
                                .setTitle(R.string.label_edittext_dialog_title)
                                .setPositiveButton(R.string.label_button_save, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //TODO: save to firebase
                                        GenericPrefListAdapter.super.notifyItemChanged(position);
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
        }
    }

    @Override
    public int getItemCount() {
        return (null != prefList ? prefList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        switch (this.viewType) {
            case PrefListable.TEXTVIEW_AND_EDIT_ICON: {
                return PrefListable.TEXTVIEW_AND_EDIT_ICON;
            }
            default:
                return 0;
        }
    }

    public class PrefHolder extends RecyclerView.ViewHolder {
        protected View view;
        protected String itemID;
        protected TextView itemString;
        protected ImageView iconEdit;

        public PrefHolder(View view, int viewType) {
            super(view);

            this.view = view;
            switch (viewType) {
                case PrefListable.TEXTVIEW_AND_EDIT_ICON: {
                   this.itemString = (TextView) view.findViewById(R.id.prefText);
                   this.iconEdit = (ImageView) view.findViewById(R.id.prefIconEdit);
                }
            }
        }

    }
}

