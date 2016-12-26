package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Fragment.TicketView;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.List;

/**
 * Created by shalty on 24/10/2016.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {


    private Typeface EXTRA_BOLD;
    private Typeface BOLD;
    private Typeface EXTRA_LIGHT;
    private Typeface LIGHT;
    private Typeface REGULAR;
    private Typeface SEMI_BOLD;
    private List<Ticket> mTicketListItem;
    private Context mContext;

    public MyRecyclerAdapter(Context context) {
        this.mTicketListItem = UtlFirebase.getAllTicket();
        this.mContext = context;
        setFont();
    }

    @Override
    public MyRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomViewHolder viewHolder;
        switch (viewType)
        {
            case TicketStateAble.TICKET_LIST_STATUS_URGENT:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_status_urgent, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_STATUS_URGENT);
                break;
            }
            case TicketStateAble.TICKET_LIST_STATUS_PENDING:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_status_pending, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_STATUS_PENDING);
                break;
            }
            case TicketStateAble.TICKET_LIST_STATUS_OK:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_status_ok, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_STATUS_OK);
                break;
            }
            case TicketStateAble.TICKET_LIST_STATUS_ERROR:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_status_error, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_STATUS_ERROR);
                break;
            }
            default:viewHolder=null;
        }

        return viewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        // super.getItemViewType(position);
        switch (mTicketListItem.get(position).status)
        {
            case TicketStateAble.TICKET_LIST_STATUS_URGENT:
            {
                return TicketStateAble.TICKET_LIST_STATUS_URGENT;
            }
            case TicketStateAble.TICKET_LIST_STATUS_PENDING:
            {
                return TicketStateAble.TICKET_LIST_STATUS_PENDING;
            }
            case TicketStateAble.TICKET_LIST_STATUS_OK:
            {
                return TicketStateAble.TICKET_LIST_STATUS_OK;
            }
            case TicketStateAble.TICKET_LIST_STATUS_ERROR:
            {
                return TicketStateAble.TICKET_LIST_STATUS_ERROR;
            }
            default:return 0;
        }

    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.CustomViewHolder holder, final int position) {
        final Ticket item = mTicketListItem.get(position);
        holder.userName.setText(item.customerName);
        holder.product.setText(item.product);
        holder.address.setText(item.address);
        holder.area.setText(item.area);
        holder.classification.setText(item.classification);
        holder.description.setText(item.desShort);
        holder.ticketNumber.setText(item.ticketNumber);
        holder.time.setText(item.startTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundel = new Bundle();
                bundel.putString("ticketID",item.ticketId);

                FragmentTransaction fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction();
                TicketView ticketView = new TicketView();
                ticketView.setArguments(bundel);
                fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.replace(R.id.container_body,  ticketView).addToBackStack("NEW_TICKET").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mTicketListItem ? mTicketListItem.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected View view;
        protected TextView userName;
        protected TextView product;
        protected TextView classification;
        protected TextView subClassification;
        protected TextView area;
        protected TextView address;
        protected TextView ticketNumber;
        protected TextView description;
        protected TextView time;
        protected TextView endTime;


        public CustomViewHolder(View view, int viewNum) {
            super(view);

            this.view = view;
            switch (viewNum)
            {
                case TicketStateAble.TICKET_LIST_STATUS_URGENT:
                {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_STATUS_PENDING:
                {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_STATUS_OK:
                {
                    setView1();
                    setView2();
                    break;
                }
                case TicketStateAble.TICKET_LIST_STATUS_ERROR:
                {
                    setView1();
                    setView2();
                    break;
                }
            }
        }
        private void setView1()
        {
            this.product = (TextView) view.findViewById(R.id.equipment);
            this.product.setTypeface(BOLD);
            this.ticketNumber = (TextView) view.findViewById(R.id.ticketNum);
            this.ticketNumber.setTypeface(SEMI_BOLD);
            this.userName = (TextView) view.findViewById(R.id.personName);
            this.userName.setTypeface(BOLD);
            this.classification = (TextView) view.findViewById(R.id.Type);
            this.classification.setTypeface(BOLD);
            this.subClassification = (TextView) view.findViewById(R.id.Type2);
            this.subClassification.setTypeface(SEMI_BOLD);
            this.area = (TextView) view.findViewById(R.id.eriaZon);
            this.area.setTypeface(BOLD);
            this.address = (TextView) view.findViewById(R.id.add);
            this.address.setTypeface(SEMI_BOLD);
            this.description = (TextView) view.findViewById(R.id.DescriptionTxt);
            this.description.setTypeface(SEMI_BOLD);
            this.time = (TextView) view.findViewById(R.id.openDate);
            this.time.setTypeface(REGULAR);
            ((TextView)view.findViewById(R.id.DescriptionTitel)).setTypeface(BOLD);
        }
        private void setView2()
        {
            this.endTime = (TextView) view.findViewById(R.id.closeDate);
            this.endTime.setTypeface(REGULAR);
        }

    }

    private void setFont()
    {
        EXTRA_BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraBold.ttf");
        BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Bold.ttf");
        EXTRA_LIGHT = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraLight.ttf");
        LIGHT = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Light.ttf");
        REGULAR = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Regular.ttf");
        SEMI_BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-SemiBold.ttf");
    }
}