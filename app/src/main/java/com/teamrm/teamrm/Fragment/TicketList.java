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

import com.teamrm.teamrm.Adapter.TicketListAdapter;
import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketList extends Fragment {

    public RecyclerView mRecyclerView;
    public static TicketListAdapter ticketListAdapter;
    private TextView title,filter,search,order;


    public TicketList() {} // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ticket, container, false);

        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        filter = (TextView)view.findViewById(R.id.filtertxt);
        search = (TextView)view.findViewById(R.id.searchTxt);
        order = (TextView)view.findViewById(R.id.sortimgTxt);


        //((TextView)view.findViewById(R.id.titleText)).setTypeface(BOLD);
        ((TextView)view.findViewById(R.id.filtertxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.searchTxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.sortimgTxt)).setTypeface(SEMI_BOLD);


        mRecyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ticketListAdapter = new TicketListAdapter(getContext());
        mRecyclerView.setAdapter(ticketListAdapter);

        return view;
    }
}
