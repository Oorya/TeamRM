package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamrm.teamrm.R;

/**
 * Created by r00t on 08/02/2017.
 */

public class NiceToast extends Toast {
    final public static int NICETOAST_INFORMATION = 1;
    final public static int NICETOAST_WARNING = 2;
    final public static int NICETOAST_ERROR = 3;

    public NiceToast(Context context, String message, int toastType, int duration) {
        super(context);
        this.setDuration(duration);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.nice_toast, null);
        CardView toastCard = (CardView) layout.findViewById(R.id.toastCard);
        RelativeLayout toastBG = (RelativeLayout) layout.findViewById(R.id.toastBG);
        TextView messageView = (TextView) layout.findViewById(R.id.toastMessage);
        messageView.setText(message);
        ImageView icon = (ImageView) layout.findViewById(R.id.toastIcon);
        switch (toastType) {
            case NICETOAST_INFORMATION:
                toastCard.setAlpha(0.9f);
                toastBG.setBackgroundResource(R.color.status_ok);
                icon.setBackgroundResource(R.drawable.ic_info_outline);
                break;

            case NICETOAST_WARNING:
                toastCard.setAlpha(0.95f);
                toastBG.setBackgroundResource(R.color.status_pending);
                icon.setBackgroundResource(R.drawable.ic_icon_alert2);
                break;

            case NICETOAST_ERROR:
                toastBG.setBackgroundResource(R.color.status_error);
                icon.setBackgroundResource(R.drawable.ic_icon_error);
        }
        setView(layout);
    }

}
