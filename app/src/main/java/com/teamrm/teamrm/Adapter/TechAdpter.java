package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Shealtiel on 26/05/2017.
 */

public class TechAdpter extends RecyclerView.Adapter<TechAdpter.ViewHolder> {
    private ArrayList<Technician> techList;
    private Context Context;
    private LinearLayout linearLayout;
    private static final AtomicInteger GeneratedId = new AtomicInteger(1);
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            this.mTextView =(TextView) v.findViewById(R.id.techItemNime);
            this.linearLayout = (LinearLayout) v.findViewById(R.id.techItem);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TechAdpter(ArrayList<Technician> myDataset ,Context Context) {
        techList = myDataset;
        this.Context = Context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TechAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tech_list_item, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(techList.get(position).getUserNameString());
        holder.linearLayout.setBackgroundColor(Color.BLUE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return techList.size();
    }
}



