package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.R;

import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class GenericPrefListAdapter extends BaseAdapter implements SpinnerAdapter {


    private List<GenericKeyValueTypeable> itemList;
    private Context aContext;

    public GenericPrefListAdapter(Context context, List<GenericKeyValueTypeable> itemList) {
        this.aContext = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder itemHolder = null;

        if (convertView == null) {
            itemHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(aContext);
            vi.inflate(R.layout.spinner_view, parent, false);
            itemHolder.itemName = (TextView) convertView.findViewById(R.id.spinnerItem);
            convertView.setTag(itemHolder);

        } else {
            itemHolder = (ViewHolder) convertView.getTag();
        }

        itemHolder.itemName.setText(itemList.get(position).getItemValue());
        itemHolder.itemID = itemList.get(position).getItemKey();
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder itemHolder = null;

        if (convertView == null) {
            itemHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(aContext);
            vi.inflate(R.layout.spinner_row, parent, false);
            try {
                itemHolder.itemName = (TextView) convertView.findViewById(R.id.text1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                convertView.setTag(itemHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            itemHolder = (ViewHolder) convertView.getTag();
        }

        itemHolder.itemName.setText(itemList.get(position).getItemValue());
        itemHolder.itemID = itemList.get(position).getItemKey();
        return convertView;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }



    static class ViewHolder {
        private TextView itemName;
        private String itemID;
    }



}