package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.NavDrawerItem;
import com.teamrm.teamrm.Utility.UserSingleton;

import java.util.Collections;
import java.util.List;

/**
 * Created by shalty on 17/09/2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (!UserSingleton.getLoadedUserType().equals(Users.STATUS_ADMIN))
        {
            Log.d("user", UserSingleton.getLoadedUserType());
            if(UserSingleton.getLoadedUserType().equals("Technician"))
            {
                Log.d("user", UserSingleton.getLoadedUserType()+" tech");

                data.remove(3);

            }else if(UserSingleton.getLoadedUserType().equals(Users.STATUS_CLIENT)){
                Log.d("user", UserSingleton.getLoadedUserType()+" tech");

                data.remove(3);
                data.remove(2);
            }

        }
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        Log.d("onBindViewHolder", current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
