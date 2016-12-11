package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.Adapter.MyRecyclerAdapter;
import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketList extends Fragment {

    public RecyclerView mRecyclerView;
    public static MyRecyclerAdapter myRecyclerAdapter;
    private TextView title,Filter,search,order;


    public TicketList() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ticket, container, false);

        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");


        //((TextView)view.findViewById(R.id.titleText)).setTypeface(BOLD);
        ((TextView)view.findViewById(R.id.filtertxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.searchTxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.sortimgTxt)).setTypeface(SEMI_BOLD);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerAdapter = new MyRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(myRecyclerAdapter);

        return view;
    }
}
