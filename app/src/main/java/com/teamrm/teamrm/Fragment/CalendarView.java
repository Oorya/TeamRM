package com.teamrm.teamrm.Fragment;


import android.app.Dialog;
import android.graphics.RectF;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Adapter.GenericPrefListAdapter;
import com.teamrm.teamrm.Interfaces.CalendarHelper;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Type.WeekViewEventCustom;
import com.teamrm.teamrm.Utility.CalendarUtil;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.text.ParseException;
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
        WeekView.EmptyViewClickListener
        //WeekView.ScrollListener
{

    public static final String FRAGMENT_TRANSACTION = "CalendarView";
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static WeekView mWeekView;
    private static List<WeekViewEventCustom> mWeeViewEvent;
    private static List<Ticket> mEvent;
    public static CalendarUtil cal;
    private static String ticketID;
    public static TimePicker timePicker;
    private static Calendar pickerTime;
    private static Bundle bundel;
    private GenericPrefListAdapter listTechAdapter;
    public static final String TAG = ":::CalendarView";


    public CalendarView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
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

        Log.d("list  mEvent = ", "onCreateView");
        bundel = new Bundle();
        mWeekView = (WeekView) view.findViewById(R.id.weekView);
        mWeeViewEvent = new ArrayList<>();
        mEvent = new ArrayList<>();
        mEvent = Ticket.getTicketList();
        // cal = new CalendarUtil(getActivity(),this);
        //cal.getEventList();
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
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


        Log.d("list  mEvent = ", mEvent.size() + "");

        mWeeViewEvent.clear();
        long id = 0;
        for (Ticket EVENT : mEvent) {
            if (EVENT.getTicketCloseDateTime().length() > 0) {
                WeekViewEventCustom weekViewEventCustom = new WeekViewEventCustom(EVENT.getTicketID()
                        , id++, EVENT.getDescriptionShort(), convertStart(EVENT), convertEnd(EVENT));

                mWeeViewEvent.add(weekViewEventCustom);
            }

        }

        List<WeekViewEventCustom> matchedEvents = new ArrayList<>();
        for (WeekViewEventCustom event : mWeeViewEvent) {
            if (mWeeViewEvent.contains(event) && eventMatches(event, newYear, newMonth)) {
                Log.d("list contains= ", eventMatches(event, newYear, newMonth) + "");
                Log.d("list contains= ", event.getStartTime() + "");

                matchedEvents.add(event);
            }
        }
        Log.d("list  mWeeViewEvent = ", mWeeViewEvent.size() + "");
        Log.d("list  matchedEvents = ", matchedEvents.size() + "");

        return matchedEvents;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {

        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1)
                || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private Calendar convertStart(Ticket event) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date start = null;
        try {
            start = dateFormat.parse(event.getTicketOpenDateTime());
            Log.d("convertStart", start.toString());

        } catch (ParseException e) {
            Log.d("convertStart", e.getMessage());
            Log.d("convertStart event", event.getTicketOpenDateTime());
        }
        Calendar calendar = Calendar.getInstance();
        if (start == null) {
            // All-day events don't have start times, so just use
            // the start date.
            //start = start.getDate();
            calendar.setTime(start);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        } else {
            calendar.setTime(start);
        }
        return calendar;
    }

    private Calendar convertEnd(Ticket event) {
        Calendar calendar = Calendar.getInstance();

        //Log.d("getTicketCloseDateTime", event.getTicketCloseDateTime().toString());
        if (event.getTicketCloseDateTime() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");

            try {
                calendar.setTime(dateFormat.parse(event.getTicketCloseDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return calendar;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {


    }

    @Override
    public void onEmptyViewClicked(final Calendar time) {

        Log.d("onEmptyViewClicked", time.toString());

        Log.d("onEmptyViewClicked", time.toString());

        if (ticketID != null) {

            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.timepickerdialog);
            dialog.setTitle("בחר שעה");
            final RadioButton allDay = (RadioButton) dialog.findViewById(R.id.allDayEvent);
            final RadioButton timeRange = (RadioButton) dialog.findViewById(R.id.timeRange);
            // final LinearLayout timeSet = (LinearLayout)dialog.findViewById(R.id.pikDite);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            Button enter = (Button) dialog.findViewById(R.id.enter);
            Spinner tech = (Spinner) dialog.findViewById(R.id.SelectTech);
            listTechAdapter = new GenericPrefListAdapter(getContext(), Technician.getTechnicianList());
            tech.setAdapter(listTechAdapter);
            tech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "selected " + Technician.getTechnicianList().get(position).toString());
                    UtlFirebase.assignTechToTicket(
                            Ticket.getTickeyById(ticketID),
                            (Technician.getTechnicianList().get(position)).getUserID(),
                            Technician.getTechnicianList().get(position).getUserNameString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            final TextView startTime = (TextView) dialog.findViewById(R.id.startTimeTxt);
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String formatted = format1.format(time.getTime());
            startTime.setText(formatted);

            timeRange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // timeSet.setVisibility(View.VISIBLE);
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formatted = format1.format(time.getTime());
                    startTime.setText(formatted);
                }
            });
            allDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //timeSet.setVisibility(View.GONE);
                    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                    String formatted = format1.format(time.getTime());
                    startTime.setText(formatted);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timeRange.isChecked()) {
                        bundel.putLong("ticketOpenDateTime", time.getTime().getTime());
                        bundel.putString("ticketID", ticketID);

                        FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                                .beginTransaction();
                        TicketView ticketView = new TicketView();
                        ticketView.setArguments(bundel);

                        fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        fragmentManager.replace(R.id.container_body, ticketView)
                                .addToBackStack(TicketView.FRAGMENT_TRANSACTION).commit();
                        dialog.dismiss();
                    } else if (allDay.isChecked()) {
                        time.set(Calendar.HOUR_OF_DAY, 0);
                        time.set(Calendar.MINUTE, 0);
                        time.set(Calendar.SECOND, 0);
                        time.set(Calendar.MILLISECOND, 0);
                        bundel.putLong("ticketOpenDateTime", time.getTime().getTime());
                        bundel.putString("ticketID", ticketID);

                        FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                                .beginTransaction();
                        TicketView ticketView = new TicketView();
                        ticketView.setArguments(bundel);

                        fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        fragmentManager.replace(R.id.container_body, ticketView)
                                .addToBackStack(TicketView.FRAGMENT_TRANSACTION).commit();
                        dialog.dismiss();
                    }

                }
            });
            dialog.show();
        } else {
            FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                    .beginTransaction();
            NewTicket newTicket = new NewTicket();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formatted = format1.format(time.getTime());
            bundel.putString("startTime", formatted);
            newTicket.setArguments(bundel);
            fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentManager.replace(R.id.container_body, newTicket)
                    .addToBackStack(TicketView.FRAGMENT_TRANSACTION).commit();
        }
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Bundle bundel = new Bundle();
        bundel.putString("ticketID", ((WeekViewEventCustom) event).getEventId());
        FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                .beginTransaction();
        TicketView ticketView = new TicketView();
        ticketView.setArguments(bundel);
        fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentManager.replace(R.id.container_body, ticketView)
                .addToBackStack(TicketView.FRAGMENT_TRANSACTION)
                .commit();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {


    }

    @Override
    public void getResult(Event event) {

    }

    @Override
    public void getEventList(List<Event> eventUtil) {
        mEvent.clear();

        // mEvent = eventUtil;
        Log.d("list getEventList", mEvent.size() + "");
        mWeekView.notifyDatasetChanged();
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
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
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public void getCalendar(com.google.api.services.calendar.model.Calendar calendar) {

    }
}
