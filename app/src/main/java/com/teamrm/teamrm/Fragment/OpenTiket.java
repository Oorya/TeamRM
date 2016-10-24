package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.Adapter.MyRecyclerAdapter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenTiket extends Fragment {

    private RecyclerView mRecyclerView; 
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<Ticket> mTiketListItem;

    public OpenTiket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_tiket, container, false);

        mTiketListItem = new ArrayList<>(); 
        mTiketListItem.add(new Ticket("שאלתיאל"));
        mRecyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerAdapter = new MyRecyclerAdapter(getContext(),mTiketListItem);
        mRecyclerView.setAdapter(myRecyclerAdapter);
        return view;
    }

}
