package com.teamrm.teamrm.Fragment;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Adapter.TicketListAdapter;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.FragmentHelper;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Interfaces.TicketStatus;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketList extends Fragment implements FireBaseAble,View.OnClickListener{

    public RecyclerView mRecyclerView;
    private List<TicketLite> ticketLiteList = new ArrayList<>();
    private TicketListAdapter ticketListAdapter;
    private TextView title, filter, search, order;
    private SwipeRefreshLayout swipeContainer;
    FloatingActionButton floatBtn;
    private AlertDialog chekDialog;

    public TicketList() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ticketLiteList!=null)
        ticketLiteList.clear();
        ticketLiteList.addAll(TicketLite.getTicketLiteList());
        orderList(ticketLiteList);
        Log.d("tiket", "onStart: "+TicketLite.getTicketLiteList().size());
        if(Ticket.getTicketList()!=null)
        Log.d("tiket", "onStart: "+Ticket.getTicketList().size());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_ticket, container, false);

        Typeface SEMI_BOLD = Typeface.createFromAsset(this.getContext().getAssets(), "Assistant-SemiBold.ttf");

        LinearLayout filter = (LinearLayout) view.findViewById(R.id.filter);
        search = (TextView) view.findViewById(R.id.searchTxt);
        order = (TextView) view.findViewById(R.id.sortimgTxt);
        filter.setOnClickListener(this);

        //((TextView)view.findViewById(R.id.titleText)).setTypeface(BOLD);
        ((TextView) view.findViewById(R.id.filtertxt)).setTypeface(SEMI_BOLD);
        ((TextView) view.findViewById(R.id.searchTxt)).setTypeface(SEMI_BOLD);
        ((TextView) view.findViewById(R.id.sortimgTxt)).setTypeface(SEMI_BOLD);


        LinearLayoutManager recyclerViewManager = new LinearLayoutManager(getContext());
        recyclerViewManager.setInitialPrefetchItemCount(4); //initial number of tickets prefetched

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(recyclerViewManager);


        ticketListAdapter = new TicketListAdapter(getContext(), ticketLiteList);
        mRecyclerView.setAdapter(ticketListAdapter);




        //mRecyclerView.setAdapter(ticketListAdapter);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        UtlFirebase.getAllTicketLites(TicketList.this);
                        swipeContainer.setRefreshing(false);
                    }
                });
            }
        });

        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 500);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                ft.replace(R.id.container_body, new NewTicket(), null);
                ft.addToBackStack(FragmentHelper.STACK_FOR_GENERAL_NAVIGATION);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.filter:
            {

                new MaterialDialog.Builder(getContext())
                        .title(R.string.label_ticket_filter)
                        .items(R.array.dialog_filter_list)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {

                                ArrayList<TicketLite> temp = new ArrayList<>();
                                for (Integer item : which)
                                {
                                    temp.addAll(filterList(item));


                                }
                                ticketListAdapter = new TicketListAdapter(getContext(), orderList(temp));
                                mRecyclerView.setAdapter(ticketListAdapter);
                                return true;
                            }
                        })
                        .positiveText("OK")
                        .contentColorRes(R.color.textColor_primary)
                        .contentGravity(GravityEnum.CENTER)
                        .negativeText(R.string.label_button_cancel)
                        .titleGravity(GravityEnum.CENTER)
                        .buttonsGravity(GravityEnum.END)
                        .backgroundColorRes(R.color.app_bg)
                        .titleColorRes(R.color.textColor_lighter)
                        .positiveColorRes(R.color.colorPrimary)
                        .negativeColorRes(R.color.colorPrimaryDark)
                        .dividerColorRes(R.color.textColor_lighter)
                        .show();
                break;
            }
            case R.id.search:
            {
                break;
            }
            case R.id.sort:
            {
                break;
            }
        }
    }
    private List<TicketLite> filterList(Integer which)
    {
        ArrayList<TicketLite> temp = new ArrayList<>();
        for (TicketLite item:ticketLiteList)
        {
            if (item.getUrgency()==which){
                temp.add(item);
            }
        }
        return temp;

    }
    private List<TicketLite> orderList(List<TicketLite> temp)
    {
        Collections.sort(temp, new Comparator<TicketLite>() {
            @Override
            public int compare(TicketLite o1, TicketLite o2) {
                Integer iInteger1 = new Integer(o1.getTicketPresentation());
                Integer iInteger2 = new Integer(o2.getTicketPresentation());


                return iInteger1.compareTo(iInteger2);
            }
        });
        Log.d("orderList", "orderList: ");

        return temp;
    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {

    }

    @Override
    public void ticketListCallback(List<Ticket> tickets) {

    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {


    }

    @Override
    public void resultBoolean(boolean bool) {

    }

    @Override
    public void companyListCallback(List<GenericKeyValueTypeable> companies) {

    }

    @Override
    public void productListCallback(List<Product> products) {

    }

    @Override
    public void categoryListCallback(List<Category> categories) {

    }

    @Override
    public void regionListCallback(List<Region> regions) {

    }
}
