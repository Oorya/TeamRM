package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;

import java.util.List;

/**
 * Created by shalty on 24/10/2016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    private List<Ticket> mTiketListItem;
    private Context mContext;


    public MyRecyclerAdapter(Context context, List<Ticket> TiketListItem) {
        this.mTiketListItem = TiketListItem;
        this.mContext = context;
    }
    @Override
    public MyRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiket_list_pending_approval__item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
       
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.CustomViewHolder holder, int position) {
        Ticket Item = mTiketListItem.get(position);
        holder.textView.setText(Item.userName);
        setFont(holder);
    }

    @Override
    public int getItemCount() {
        return (null != mTiketListItem ? mTiketListItem.size() : 0);
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
          
            this.textView = (TextView) view.findViewById(R.id.equipment);
        }
    }
    private void setFont(CustomViewHolder holder)
    {
       Typeface EB = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraBold.ttf");
       Typeface B = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Bold.ttf");
       Typeface el = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraLight.ttf");
       Typeface l = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Light.ttf");
       Typeface r = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Regular.ttf");
       Typeface SB = Typeface.createFromAsset(mContext.getAssets(), "Assistant-SemiBold.ttf");
       
        holder.textView.setTypeface(EB);
        
    }
}
