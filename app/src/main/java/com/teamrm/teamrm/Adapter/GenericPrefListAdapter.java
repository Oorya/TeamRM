package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.util.Log;
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

        ViewHolder itemHolder;

        if (convertView == null) {
            itemHolder = new ViewHolder();
           // LayoutInflater vi = LayoutInflater.from(aContext);
           // vi.inflate(R.layout.spinner_view, parent, false);
            convertView = View.inflate(aContext,R.layout.spinner_view,null);
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
        ViewHolder itemHolder = new ViewHolder();

        if (convertView == null)
        {
           // itemHolder = new ViewHolder();
           // LayoutInflater vi = LayoutInflater.from(aContext);
           // vi.inflate(R.layout.spinner_row, parent, false);
            convertView = View.inflate(aContext,R.layout.spinner_row,null);
            try {
                itemHolder.itemName = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(itemHolder);
            } catch (Exception e) {
                Log.d("adpter e1",e.getMessage());
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
        public TextView itemName;
        public String itemID;
    }



}