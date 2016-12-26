package com.teamrm.teamrm.Fragment;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.RectF;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Interfaces.CalendarHelper;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.CalendarUtil;

import java.sql.Time;
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
    private static String ticketID;
    public static TimePicker timePicker;
    private static Calendar pickerTime;
    private static Bundle bundel;


    public CalendarView() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            String ticketId = bundle.getString("ticketID", "error");
            Log.w("TICKET Bundle calendar:", ticketId);
            ticketID = ticketId;
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_veiw, container, false);
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.GONE);

        Log.d("list  mEvent = ","onCreateView");
        bundel = new Bundle();
        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeeViewEvent = new ArrayList<>();
        mEvent = new ArrayList<>();
        cal = new CalendarUtil(getActivity(),this);
        cal.getResultsFromApi();
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        mWeekView.setMonthChangeListener(this);

        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);
        mWeekView.setEmptyViewClickListener(this);
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
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        Log.d("list  mEvent = ",mEvent.size()+"");

        mWeeViewEvent.clear();
        long id=0;
        for (Event EVENT : mEvent)
        {
           // WeekViewEvent Wevent = new WeekViewEvent(Long.parseLong(EVENT.getId()),EVENT.getSummary(), convertStart(EVENT),convertEnd(EVENT));
            WeekViewEvent Wevent = new WeekViewEvent(id++,EVENT.getId(),EVENT.getSummary(),convertStart(EVENT),convertEnd(EVENT));
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
    public void onEmptyViewClicked(final Calendar time) {


        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);

       final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.allDay)
                {
                    time.set(Calendar.HOUR_OF_DAY, 0);
                    time.set(Calendar.MINUTE, 0);
                    time.set(Calendar.SECOND, 0);
                    time.set(Calendar.MILLISECOND, 0);
                    //SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                    //String formatted = format1.format(time.getTime());
                    bundel.putLong("time",time.getTime().getTime());
                    bundel.putString("ticketID",ticketID);

                    FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                            .beginTransaction();
                    TicketView ticketView = new TicketView();
                    ticketView.setArguments(bundel);

                    fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    fragmentManager.replace(R.id.container_body,  ticketView).addToBackStack("NEW_TICKET").commit();
                    dialog.dismiss();

                }
                else
                {
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
                   pickerTime = time;
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Bundle bundel = new Bundle();
        bundel.putString("ticketID",event.getName());

        FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                .beginTransaction();
        TicketView ticketView = new TicketView();
        ticketView.setArguments(bundel);
        fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentManager.replace(R.id.container_body,  ticketView).commit();
    }
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {


    }
    @Override
    public void getResult(Event event) {

    }
    @Override
    public void getEventList(List<Event> eventUtil)
    {

        Log.d("list","getEventList");

        mEvent.clear();

        mEvent = eventUtil;
        Log.d("list = ",mEvent.size()+"");
        mWeekView.notifyDatasetChanged();
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


            public static class TimePickerFragment extends DialogFragment
                    implements TimePickerDialog.OnTimeSetListener
            {

                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    // Use the current time as the default values for the picker
                    final Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    // Create a new instance of TimePickerDialog and return it
                    return new TimePickerDialog(getActivity(), this, hour, minute,
                            DateFormat.is24HourFormat(getActivity()));
                }

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Calendar pickerTime = Calendar.getInstance();
                    //pickerTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                   // pickerTime.set(Calendar.MINUTE,minute);

                    pickerTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    pickerTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formatted = format1.format(pickerTime.getTime());
                    bundel.putString("time",formatted);
                    bundel.putString("ticketID",ticketID);

                    FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                            .beginTransaction();
                    TicketView ticketView = new TicketView();
                    ticketView.setArguments(bundel);
                    fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    fragmentManager.replace(R.id.container_body,  ticketView).addToBackStack("CALENDER").commit();
                    this.dismiss();
                }
            }

}
