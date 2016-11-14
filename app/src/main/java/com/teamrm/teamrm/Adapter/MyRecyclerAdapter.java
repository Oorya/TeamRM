package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;

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



    private List<Ticket> mTiketListItem;
    private Context mContext;
   


    public MyRecyclerAdapter(Context context, List<Ticket> TiketListItem) {
        this.mTiketListItem = TiketListItem;
        this.mContext = context;
        setFont();
        
        
        
        
    }
    @Override
    public MyRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomViewHolder viewHolder;
        switch (viewType)
        {
            case TicketStateAble.TICKET_LIST_PENDIN_APPROVAL:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_pending_approval_item, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_PENDIN_APPROVAL);
            break;
            }
            case TicketStateAble.TICKET_LIST_PENDIN_TECH_ITEM:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_pending_tech_item, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_PENDIN_TECH_ITEM);
                break;
            }
            case TicketStateAble.TICKET_LIST_PENDIN_TREATMENT:
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_pending_tech_treatment_item, null);
                viewHolder = new CustomViewHolder(view,TicketStateAble.TICKET_LIST_PENDIN_TREATMENT);
                break;
            }
            default:viewHolder=null;
        }
        
        return viewHolder;
       
    }

    @Override
    public int getItemViewType(int position) {
       // super.getItemViewType(position);
        switch (mTiketListItem.get(position).status)
        {
            case TicketStateAble.TICKET_LIST_PENDIN_APPROVAL:
            {
               return TicketStateAble.TICKET_LIST_PENDIN_APPROVAL;
            }
            case TicketStateAble.TICKET_LIST_PENDIN_TECH_ITEM:
            { 
                return TicketStateAble.TICKET_LIST_PENDIN_TECH_ITEM;
            }
            case TicketStateAble.TICKET_LIST_PENDIN_TREATMENT:
            {
                return TicketStateAble.TICKET_LIST_PENDIN_TREATMENT;
            }
            default:return 0;
        }
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.CustomViewHolder holder, int position) {
        Ticket Item = mTiketListItem.get(position);
        holder.userName.setText(Item.userName);
       
    }

    @Override
    public int getItemCount() {
        return (null != mTiketListItem ? mTiketListItem.size() : 0);
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
                case TicketStateAble.TICKET_LIST_PENDIN_APPROVAL:
                {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_PENDIN_TECH_ITEM:
                {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_PENDIN_TREATMENT:
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
