package com.teamrm.teamrm.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Adapter.TicketListAdapter;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.FragmentHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketList extends Fragment implements FireBaseAble,View.OnClickListener{

    public static final String FRAGMENT_TRANSACTION = "TicketList";
    private static boolean isSort;
    public RecyclerView mRecyclerView;
    private SearchView searchView;
    private String searchViewQuery;
    private static List<TicketLite> ticketLiteList = new ArrayList<>();
    private TicketListAdapter ticketListAdapter;
    private LinearLayout title, filter, search, order;
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton floatBtn;
    private AlertDialog chekDialog;
    private TextView ordertext;
    private SwipeRefreshLayout.OnScrollChangeListener swScrollListener;


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

        filter = (LinearLayout) view.findViewById(R.id.filter);//TODO:add visual effects for buttons
        search = (LinearLayout) view.findViewById(R.id.search);
        order = (LinearLayout) view.findViewById(R.id.sort);
        ordertext = (TextView)view.findViewById(R.id.sortimgTxt);
        searchView = (SearchView)view.findViewById(R.id.searchView);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.textColor_primary));

        filter.setOnClickListener(this);
        search.setOnClickListener(this);
        order.setOnClickListener(this);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(),query,Toast.LENGTH_LONG).show();
                sortByQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.GONE);
                filter.setVisibility(View.VISIBLE);
                order.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                ticketListAdapter = null;
                ticketListAdapter = new TicketListAdapter(getContext(), ticketLiteList);
                mRecyclerView.setAdapter(ticketListAdapter);
                orderList(ticketLiteList);

            }
        });

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
        orderList(ticketLiteList);


        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        UserSingleton.refreshTicketLites();
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
                ft.replace(R.id.container_body, new NewTicket(), NewTicket.FRAGMENT_TRANSACTION);
                ft.addToBackStack(NewTicket.FRAGMENT_TRANSACTION);
                ft.commit();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.filter:
            {
                SeekBar urgencyBar = (SeekBar) getView().findViewById(R.id.urgencyBar);
                new MaterialDialog.Builder(getContext())
                        .title(R.string.label_ticket_filter)
                        //.items(R.array.dialog_filter_list)
                        .customView(R.layout.ticket_filter, false)

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
                searchView.setVisibility(View.VISIBLE);
                searchView.onActionViewExpanded();
                filter.setVisibility(View.GONE);
                order.setVisibility(View.GONE);
                search.setVisibility(View.GONE);

                break;
            }
            case R.id.sort:
            {

                Toast.makeText(getContext(),"sort",Toast.LENGTH_LONG).show();
                ticketListAdapter = new TicketListAdapter(getContext(), sortList(ticketLiteList));
                mRecyclerView.setAdapter(ticketListAdapter);


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
    private List<TicketLite> sortList(final List<TicketLite> temp)
    {
        Collections.sort(temp, new Comparator<TicketLite>() {
            @Override
            public int compare(TicketLite o1, TicketLite o2) {
                Date date1= Calendar.getInstance().getTime();
                Date date2 = Calendar.getInstance().getTime();
                String pattern = "HH:mm:ss dd/MM/yyyy";

                DateFormat format = new SimpleDateFormat(pattern);
                try {
                     date1 = format.parse(o1.getTicketOpenDateTime().replace("-",""));
                     date2 = format.parse(o2.getTicketOpenDateTime().replace("-",""));
                } catch (ParseException e) {
                    Log.d("orderList", e.toString());
                }
                if(TicketList.isSort) {
                    return date1.getTime() > date2.getTime() ? 1 : -1;
                }else
                {
                    return date1.getTime() < date2.getTime() ? 1 : -1;
                }
            }
        });
        Log.d("orderList", "orderList: ");

        if(TicketList.isSort) {
            ordertext.setText("סדר מחדש לישן");
            TicketList.isSort = false;
        }
        else {
            ordertext.setText("סדר מישן לחדש");
            TicketList.isSort = true;
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
    public void companyListCallback(List<Company> companies) {

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
    private void sortByQuery(String Query)
    {
        List<TicketLite> tempticketLiteList = new ArrayList<>();
        for (TicketLite ticketLiteItem : ticketLiteList)
        {
            if (ticketLiteItem.getCategoryName()!=null&&ticketLiteItem.getCategoryName().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getTechNameString()!=null&&ticketLiteItem.getTechNameString().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getDescriptionLong()!=null&&ticketLiteItem.getDescriptionLong().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getDescriptionShort()!=null&&ticketLiteItem.getDescriptionShort().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getCategoryName()!=null&&ticketLiteItem.getCategoryName().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getClientNameString()!=null&&ticketLiteItem.getClientNameString().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getProductName()!=null&&ticketLiteItem.getProductName().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getRegionName()!=null&&ticketLiteItem.getRegionName().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getTicketAddress()!=null&&ticketLiteItem.getTicketAddress().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getTicketID()!=null&&ticketLiteItem.getTicketID().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }
            else if (ticketLiteItem.getTicketCloseDateTime()!=null&&ticketLiteItem.getTicketCloseDateTime().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getTicketNumber()!=null&&ticketLiteItem.getTicketNumber().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }
            else if (ticketLiteItem.getTicketOpenDateTime()!=null&&ticketLiteItem.getTicketOpenDateTime().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }
            else if (ticketLiteItem.getCompanyName()!=null&&ticketLiteItem.getCompanyName().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }else if (ticketLiteItem.getTicketStateString()!=null&&ticketLiteItem.getTicketStateString().equals(Query))
            {
                tempticketLiteList.add(ticketLiteItem);
            }
        }
        ticketListAdapter = new TicketListAdapter(getContext(), tempticketLiteList);
        mRecyclerView.setAdapter(ticketListAdapter);
    }

    public boolean closeSearch()
    {
        if(!searchView.isIconified())
        {
            searchView.setVisibility(View.GONE);
            filter.setVisibility(View.VISIBLE);
            order.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
            ticketListAdapter = null;
            ticketListAdapter = new TicketListAdapter(getContext(), ticketLiteList);
            mRecyclerView.setAdapter(ticketListAdapter);
            orderList(ticketLiteList);
            searchView.setIconified(true);
            return true;
        }else {
            return false;
        }
    }
}
