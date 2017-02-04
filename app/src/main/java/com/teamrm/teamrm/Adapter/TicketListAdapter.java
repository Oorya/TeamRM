package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.Fragment.TicketView;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.TicketLite;

import java.util.List;

/**
 * Created by shalty on 24/10/2016.
 */

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.CustomViewHolder> {


    private Typeface EXTRA_BOLD;
    private Typeface BOLD;
    private Typeface EXTRA_LIGHT;
    private Typeface LIGHT;
    private Typeface REGULAR;
    private Typeface SEMI_BOLD;
    private List<TicketLite> mTicketLiteList;
    private Context mContext;

    public TicketListAdapter(Context context, List<TicketLite> ticketLiteList) {
        this.mTicketLiteList = ticketLiteList;
        this.mContext = context;
        setFont();
    }

    @Override
    public TicketListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                Toast.makeText(mContext, "Something fucked up", Toast.LENGTH_LONG).show();
                //TODO:notify sysadmins
                break;

            case TicketStateAble.TICKET_LIST_PRESENTATION_URGENT: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_lite_list_status_urgent, null);
                viewHolder = new CustomViewHolder(view, TicketStateAble.TICKET_LIST_PRESENTATION_URGENT);
                break;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_PENDING: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_lite_list_status_pending, null);
                viewHolder = new CustomViewHolder(view, TicketStateAble.TICKET_LIST_PRESENTATION_PENDING);
                break;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_OK: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_lite_list_status_ok, null);
                viewHolder = new CustomViewHolder(view, TicketStateAble.TICKET_LIST_PRESENTATION_OK);
                break;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_ERROR: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_lite_list_status_error, null);
                viewHolder = new CustomViewHolder(view, TicketStateAble.TICKET_LIST_PRESENTATION_ERROR);
                break;
            }
            default:
                viewHolder = null;
        }

        return viewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        // super.getItemViewType(position);
        Log.d(":::TICKET ADAPTER:::", mTicketLiteList.size() + " size");
        switch (mTicketLiteList.get(position).getTicketPresentation()) {
            case TicketStateAble.TICKET_LIST_PRESENTATION_URGENT: {
                return TicketStateAble.TICKET_LIST_PRESENTATION_URGENT;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_PENDING: {
                return TicketStateAble.TICKET_LIST_PRESENTATION_PENDING;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_OK: {
                return TicketStateAble.TICKET_LIST_PRESENTATION_OK;
            }
            case TicketStateAble.TICKET_LIST_PRESENTATION_ERROR: {
                return TicketStateAble.TICKET_LIST_PRESENTATION_ERROR;
            }
            default:
                return 0;
        }
    }

    @Override
    public void onBindViewHolder(TicketListAdapter.CustomViewHolder holder, final int position) {
        final TicketLite item = mTicketLiteList.get(position);
        holder.clientNameString.setText(item.getClientNameString());
        holder.productName.setText(item.getProductName());
        holder.ticketAddress.setText(item.getTicketAddress());
        holder.regionName.setText(item.getRegionName());
        holder.categoryName.setText(item.getCategoryName());
        holder.descriptionShort.setText((item.getDescriptionShort().isEmpty() ? "ל\"ת" : item.getDescriptionShort()));
        holder.descriptionLong.setText((item.getDescriptionLong().isEmpty() ? "ל\"ת" : item.getDescriptionLong()));
        holder.ticketNumber.setText(item.getTicketNumber());
        holder.ticketOpenDateTime.setText(item.getTicketOpenDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("ticketID", item.getTicketID());

                FragmentTransaction fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction();
                TicketView ticketView = new TicketView();
                ticketView.setArguments(bundle);
                fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.replace(R.id.container_body, ticketView).addToBackStack("NEW_TICKET").commit();
            }
        });
        /*if (position == mTicketLiteList.size()-1){
            CardView.MarginLayoutParams mlp = (CardView.MarginLayoutParams)holder.cardContainer.getLayoutParams();
            mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, convertDpToPx(120)); //add 120dp margin after last card
        }*/
    }

    @Override
    public int getItemCount() {
        return (null != mTicketLiteList ? mTicketLiteList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected View view;
        protected TextView ticketStatusString;
        protected TextView companyNameString;
        protected TextView clientNameString;
        protected TextView technicianNameString;
        protected TextView productName;
        protected TextView categoryName;
        protected TextView regionName;
        protected TextView ticketAddress;
        protected TextView ticketNumber;
        protected TextView descriptionShort;
        protected TextView descriptionLong;
        protected TextView ticketOpenDateTime;
        protected TextView endTime;
        protected CardView cardContainer;


        public CustomViewHolder(View view, int viewNum) {
            super(view);

            this.view = view;
            switch (viewNum) {
                case TicketStateAble.TICKET_LIST_PRESENTATION_URGENT: {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_PRESENTATION_PENDING: {
                    setView1();
                    break;
                }
                case TicketStateAble.TICKET_LIST_PRESENTATION_OK: {
                    setView1();
                    setView2();
                    break;
                }
                case TicketStateAble.TICKET_LIST_PRESENTATION_ERROR: {
                    setView1();
                    setView2();
                    break;
                }
            }
        }

        private void setView1() {
            this.ticketNumber = (TextView) view.findViewById(R.id.ticketNum);
            this.ticketStatusString = (TextView)view.findViewById(R.id.ticketStatusString);
            this.clientNameString = (TextView) view.findViewById(R.id.clientName);
            this.companyNameString = (TextView) view.findViewById(R.id.companyName);
            this.technicianNameString = (TextView) view.findViewById(R.id.technicianName);
            this.productName = (TextView) view.findViewById(R.id.ticketProduct);
            this.categoryName = (TextView) view.findViewById(R.id.ticketCategory);
            this.regionName = (TextView) view.findViewById(R.id.ticketRegion);
            this.ticketAddress = (TextView) view.findViewById(R.id.ticketAddress);
            this.descriptionShort = (TextView) view.findViewById(R.id.descriptionShort);
            this.descriptionLong = (TextView) view.findViewById(R.id.descriptionLong);
            this.ticketOpenDateTime = (TextView) view.findViewById(R.id.openDate);
            /*this.descriptionShort.setTypeface(BOLD);
            this.ticketAddress.setTypeface(SEMI_BOLD);
            this.regionName.setTypeface(BOLD);
            this.subClassification.setTypeface(SEMI_BOLD);
            this.categoryName.setTypeface(BOLD);
            this.clientNameString.setTypeface(BOLD);
            this.ticketNumber.setTypeface(SEMI_BOLD);
            this.productName.setTypeface(BOLD);
            this.descriptionLong.setTypeface(SEMI_BOLD);
            this.ticketOpenDateTime.setTypeface(REGULAR);
            ((TextView) view.findViewById(R.id.DescriptionTitle)).setTypeface(BOLD);*/
            this.cardContainer = (CardView) view.findViewById(R.id.cardContainer);
        }

        private void setView2() {
            this.endTime = (TextView) view.findViewById(R.id.closeDate);
            this.endTime.setTypeface(REGULAR);
        }

    }

    private void setFont() {
        EXTRA_BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraBold.ttf");
        BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Bold.ttf");
        EXTRA_LIGHT = Typeface.createFromAsset(mContext.getAssets(), "Assistant-ExtraLight.ttf");
        LIGHT = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Light.ttf");
        REGULAR = Typeface.createFromAsset(mContext.getAssets(), "Assistant-Regular.ttf");
        SEMI_BOLD = Typeface.createFromAsset(mContext.getAssets(), "Assistant-SemiBold.ttf");
    }

    private int convertDpToPx(int dp) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
        return Math.round(pixels);
    }

}