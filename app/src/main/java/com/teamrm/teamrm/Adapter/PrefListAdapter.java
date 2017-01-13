package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.teamrm.teamrm.Interfaces.PrefListable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/01/2017.
 */

public class PrefListAdapter extends RecyclerView.Adapter<PrefListAdapter.prefHolder> implements PrefListable{

    List<?> prefList = new ArrayList<>();
    Context pContext;

    int viewType;

    public PrefListAdapter(Context context, ArrayList<?> prefList, int viewType){
        this.pContext = context;
        this.prefList = prefList;
        this.viewType = viewType;
    }


    @Override
    public PrefListAdapter.prefHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case PrefListable.TEXTVIEW_AND_EDIT:
            {

            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(prefHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return prefList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (this.viewType){
            case PrefListable.TEXTVIEW_AND_EDIT:
            {
                return PrefListable.TEXTVIEW_AND_EDIT;
            }
            default:return 0;
        }

    }

    public class prefHolder extends RecyclerView.ViewHolder{
        protected View view;


        public prefHolder(View view){
            super(view);
        }

    }
}

