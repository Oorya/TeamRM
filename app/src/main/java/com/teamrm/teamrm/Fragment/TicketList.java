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
import com.teamrm.teamrm.Type.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketList extends Fragment {

    private RecyclerView mRecyclerView; 
    private MyRecyclerAdapter myRecyclerAdapter;
    private static List<Ticket> mTicketListItem;
    private TextView title,Filter,search,order;
    public TicketList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ticket, container, false);

       
       
        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");


        //((TextView)view.findViewById(R.id.titleText)).setTypeface(BOLD);
        ((TextView)view.findViewById(R.id.filtertxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.searchTxt)).setTypeface(SEMI_BOLD);
        ((TextView)view.findViewById(R.id.sortimgTxt)).setTypeface(SEMI_BOLD);

        
        
        
        
        
        mTicketListItem = new ArrayList<>();
        mTicketListItem.add(new Ticket("שאלתיאל",1));
        mTicketListItem.add(new Ticket("אוריה",2));
        mTicketListItem.add(new Ticket("מארק",3));
        mTicketListItem.add(new Ticket("sdfh",4));


        mRecyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerAdapter = new MyRecyclerAdapter(getContext(), mTicketListItem);
        mRecyclerView.setAdapter(myRecyclerAdapter);
        return view;
    }
    public static void stTicketList(List<Ticket> TicketListItem)
    {
        mTicketListItem = TicketListItem;
    }

}
