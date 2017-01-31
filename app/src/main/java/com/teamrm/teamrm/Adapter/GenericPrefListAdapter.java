package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.R;

import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class GenericPrefListAdapter extends BaseAdapter{


    private List<GenericKeyValueTypeable> itemList;
    private Context aContext;

    GenericPrefListAdapter(Context context, List<GenericKeyValueTypeable> itemList){
        this.aContext = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = View.inflate(aContext, R.layout.spinner_row, null);
        TextView itemName = (TextView)view.findViewById(R.id.text1);
        itemName.setText(itemList.get(position).getItemValue());
        TextView itemID = (TextView) view.findViewById(R.id.itemID);
        itemID.setText(itemList.get(position).getItemKey());
        return view;
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
}