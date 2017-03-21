package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

/**
 * Created by r00t on 21/03/2017.
 */

@SuppressWarnings("deprecation")
public class TimePickerCompat extends android.widget.TimePicker
{
    public TimePickerCompat(Context context)
    {
        super(context);
    }

    public TimePickerCompat(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TimePickerCompat(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TimePickerCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHour(int hour)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            super.setHour(hour);
        else
            super.setCurrentHour(hour);
    }

    public void setMinute(int minute)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            super.setMinute(minute);
        else
            super.setCurrentMinute(minute);
    }

    public int getHour()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return super.getHour();
        else
            return super.getCurrentHour();
    }

    public int getMinute()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return super.getMinute();
        else
            return super.getCurrentMinute();
    }

    @Override
    public boolean is24HourView() {
        return true;
    }

}