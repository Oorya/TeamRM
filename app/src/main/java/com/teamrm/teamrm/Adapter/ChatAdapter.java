package com.teamrm.teamrm.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oorya on 22/07/2016.
 */
public class ChatAdapter extends BaseAdapter {
    List<Chat> chatList;
    Context context;

    public ChatAdapter(Context context, String ticketID)
    {
        this.context=context;
        chatList=new ArrayList<>();
        chatList= UtlFirebase.getChatByTicketID(ticketID);
    }
    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView=new TextView(context);
        textView.setText(chatList.get(i).msg);
        return textView;
    }
}
