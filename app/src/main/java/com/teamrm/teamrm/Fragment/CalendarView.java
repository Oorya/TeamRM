package com.teamrm.teamrm.Fragment;


import android.graphics.RectF;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Interfaces.CalendarHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.CalendarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarView extends android.support.v4.app.Fragment implements WeekView.EventClickListener,
    MonthLoader.MonthChangeListener,
    CalendarHelper,
    WeekView.EventLongPressListener,
    WeekView.EmptyViewLongPressListener,
    WeekView.EmptyViewClickListener//WeekView.ScrollListener
        {
     
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static   WeekView mWeekView;
    private static List<WeekViewEvent> mWeeViewEvent;
    private static List<Event> mEvent;
    public static CalendarUtil cal;
    
    public CalendarView() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_veiw, container, false);

        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeeViewEvent = new ArrayList<>();
        mEvent = new ArrayList<>();
        cal = new CalendarUtil(getActivity(),this);
        cal.getResultsFromApi();
        mWeekView.setMonthChangeListener(this);

        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);
        // setupDateTimeInterpreter(false);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {



    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        Log.d("list  mEvent = ",mEvent.size()+"");

        mWeeViewEvent.clear();
        int id=0;
        for (Event EVENT : mEvent)
        {
            id++;

            WeekViewEvent Wevent = new WeekViewEvent(id,EVENT.getSummary(), convertStart(EVENT),convertEnd(EVENT));
            mWeeViewEvent.add(Wevent);

        }

        List<WeekViewEvent> matchedEvents = new ArrayList<>();
        for (WeekViewEvent event : mWeeViewEvent)
        {
            if (mWeeViewEvent.contains(event) && eventMatches(event, newYear, newMonth))
            {
                matchedEvents.add(event);
            }
        }
        Log.d("list  mWeeViewEvent = ",mWeeViewEvent.size()+"");
        Log.d("list  matchedEvents = ",matchedEvents.size()+"");

        return matchedEvents;
    }
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
       
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) ==  month-1 )
                || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month-1);
    }
    private Calendar convertStart(Event event)
    {
        Calendar calendar = Calendar.getInstance();
        DateTime start = event.getStart().getDateTime();
        if (start == null) {
            // All-day events don't have start times, so just use
            // the start date.
            start = event.getStart().getDate();
            calendar.setTime(new Date(start.getValue()));
            calendar.set(Calendar.HOUR_OF_DAY,0);
        }else {
            calendar.setTime(new Date(start.getValue()));
        }
        return calendar;
    }
    private Calendar convertEnd(Event event)
    {
        Calendar calendar = Calendar.getInstance();
        DateTime end = event.getEnd().getDateTime();
        if (end == null) {
            // All-day events don't have start times, so just use
            // the start date.
            end = event.getStart().getDate();
            calendar.setTime(new Date(end.getValue()));
            calendar.set(Calendar.HOUR_OF_DAY,23);
        }else {
            calendar.setTime(new Date(end.getValue()));
        }
        return calendar;
    }


    @Override
    public void onEmptyViewLongPress(Calendar time) {



    }
    @Override
    public void onEmptyViewClicked(Calendar time) {


    }

    @Override
    public void getResult(Event event) {

    }




    @Override
    public void getEventList(List<Event> calList)
    {
        Log.d("list get resolt","start");

        mEvent.clear();
        mEvent = calList;
        Log.d("list = ",mEvent.size()+"");
        mWeekView.notifyDatasetChanged();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {


    }

    private void setupDateTimeInterpreter(final boolean shortDate)
    {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter()
        {
            @Override
            public String interpretDate(Calendar date)
            {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour)
            {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }


}
