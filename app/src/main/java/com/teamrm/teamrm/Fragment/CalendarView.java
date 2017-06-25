package com.teamrm.teamrm.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.api.services.calendar.model.Event;
import com.teamrm.teamrm.Adapter.GenericPrefListAdapter;
import com.teamrm.teamrm.Adapter.TechAdpter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Technician;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.WeekViewEventCustom;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarView extends android.support.v4.app.Fragment implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener,
        WeekView.EventLongPressListener,
        WeekView.EmptyViewLongPressListener,
        WeekView.EmptyViewClickListener,
        WeekView.ScrollListener,
        WeekView.OnDragListener
{

    public static final String FRAGMENT_TRANSACTION = "CalendarView";
    public static final SimpleDateFormat DATE_FORMAT_WITH_TIME = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT_NO_TIME = new SimpleDateFormat("dd/MM/yyyy");
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static WeekView mWeekView;
    private static List<WeekViewEventCustom> mWeeViewEvent;
    private static List<Ticket> mEvent;
    private static String ticketID;
    private static String[] pikerStartTime = new String[4];
    private static Bundle bundel;
    private GenericPrefListAdapter listTechAdapter;
    private int ticketAssignedDurationHours ,ticketAssignedDurationMin;
    private RecyclerView  techListView;
    //private static
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
        mWeekView.setMonthChangeListener(this);
        mWeekView.goToHour(6.0);

        mWeekView.setOnEventClickListener(this);
        mWeekView.setEventLongPressListener(this);
        mWeekView.setEmptyViewLongPressListener(this);
        mWeekView.setEmptyViewClickListener(this);
        mWeekView.setScrollListener(this);
        mWeekView.setOnDragListener(this);
        // setupDateTimeInterpreter(false);


        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        techListView = (RecyclerView) view.findViewById(R.id.thechList);
        techListView.setLayoutManager(layoutManager);
        TechAdpter techAdpter = new TechAdpter((ArrayList<Technician>) Technician.getTechnicianList(), getContext());
        techListView.setAdapter(techAdpter);



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
                mWeekView.goToHour(6.0);
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
                    mWeekView.goToHour(6.0);
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
                    mWeekView.goToHour(6.0);
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
                    mWeekView.goToHour(6.0);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {




        mWeeViewEvent.clear();
        long id = 0;
        for (Ticket EVENT : Ticket.getTicketList()) {
            if (EVENT.getTicketAssignedDateTime()!=null && EVENT.getTicketAssignedDateTime().length() > 0) {
                WeekViewEventCustom weekViewEventCustom = new WeekViewEventCustom(EVENT.getTicketID()
                        , id++, EVENT.getDescriptionShort(), convertStart(EVENT), convertEnd(EVENT));
                weekViewEventCustom.setColor(EVENT.getTechColor());
                mWeeViewEvent.add(weekViewEventCustom);
            }

        }

        List<WeekViewEventCustom> matchedEvents = new ArrayList<>();
        for (WeekViewEventCustom event : mWeeViewEvent) {
            if (mWeeViewEvent.contains(event) && eventMatches(event, newYear, newMonth)) {
                Log.d("list contains= ", eventMatches(event, newYear, newMonth) + "");
               // Log.d("list contains= ", event.getStartTime() + "");

                matchedEvents.add(event);
            }
        }
      //  Log.d("list  mWeeViewEvent = ", mWeeViewEvent.size() + "");
        Log.d("list  matchedEvents = ", matchedEvents.size() + "");
     //   Log.d("list  matchedEvents = ", matchedEvents.size()!=0?matchedEvents.get(0).getEndTime() + "":"0");
        mWeekView.goToHour(6.0);
        return matchedEvents;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {


        Log.d("list ", "eventMatches MONTH StartTime: "+event.getStartTime().get(Calendar.MONTH)+" month: "+(month-1));
        Log.d("list ", "eventMatches YEAR StartTime: "+event.getStartTime().get(Calendar.YEAR)+" year: "+year);
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1)
                || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    private Calendar convertStart(Ticket event) {
        Calendar calendar = Calendar.getInstance();

        Date start = null;
        try {
                start = DATE_FORMAT_WITH_TIME.parse(event.getTicketAssignedDateTime());
                calendar.setTime(start);
        } catch (ParseException e) {
            Log.d("convertStart Exception", e.getMessage());
            Log.d("convertStart event", event.getTicketAssignedDateTime());
                try {
                    start = DATE_FORMAT_NO_TIME.parse(event.getTicketAssignedDateTime());
                    Log.d("convertStart all day", start.toString());
                } catch (ParseException e1) {
                    Log.d("convertStart all day", e1.getMessage());
                    Log.d("convertStart event ", event.getTicketAssignedDateTime());
                }
                calendar.setTime(start);
        }
        Log.d("convertStart event2 ", event.getTicketAssignedDateTime());
        Log.d("convertStart Start2", calendar.get(Calendar.MINUTE)+"");
        return calendar;
    }

    private Calendar convertEnd(Ticket event) {
        Calendar calendar = Calendar.getInstance();

        //Log.d("getTicketCloseDateTime", event.getTicketCloseDateTime().toString());
        if (event.getTicketCloseDateTime() != null) {
            try {
                calendar.setTime(DATE_FORMAT_WITH_TIME.parse(event.getTicketCloseDateTime()));
                Log.d("convertEnd event1 ", calendar.get(Calendar.HOUR_OF_DAY)+"");
                Log.d("convertEnd event1 ", calendar.get(Calendar.MINUTE)+"");
            } catch (ParseException e) {
               // Log.d("convertEnd1 Exception", e.getMessage());
                try {
                    calendar.setTime(DATE_FORMAT_NO_TIME.parse(event.getTicketCloseDateTime()));
                  //  Log.d("convertEnd event god ", event.getTicketCloseDateTime());
                } catch (ParseException e1) {
                  //  Log.d("convertEnd1 Exception2", e.getMessage());

                }
            }
        }
        Log.d("convertEnd event2 ", event.getTicketCloseDateTime());
        Log.d("convertEnd event2 ", calendar.get(Calendar.MINUTE)+"");
        return calendar;
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {

        Log.d("onDrag", "onEmptyViewLongPress: "+time);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.d("onDrag", "onDrag: "+event);
        return false;
    }

    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        Log.d("onDrag", "onFirstVisibleDayChanged: "+newFirstVisibleDay);
    }

    @Override
    public void onEmptyViewClicked(final Calendar time) {


        Log.d("onEmptyViewClicked", time.toString());
        if (ticketID != null) {
            final int[] thecNum = new int[1];
            final String startTimeString;
            final MaterialDialog setTechEndTimeDialog = new MaterialDialog.Builder(getContext())

                    .customView(R.layout.set_time_date_tech_dialog, false)
                    .positiveText(R.string.label_button_save)
                    .contentColorRes(R.color.textColor_primary)
                    .contentGravity(GravityEnum.CENTER)
                    .negativeText(R.string.label_button_cancel)
                    .titleGravity(GravityEnum.END)
                    .buttonsGravity(GravityEnum.END)
                    .backgroundColorRes(R.color.app_bg)
                    .widgetColorRes(R.color.textColor_primary)
                    .titleColorRes(R.color.textColor_lighter)
                    .positiveColorRes(R.color.colorPrimary)
                    .negativeColorRes(R.color.colorPrimaryDark)
                    .dividerColorRes(R.color.textColor_lighter)
                    .build();

            View btnNegativ =  setTechEndTimeDialog.getActionButton(DialogAction.NEGATIVE);
            View btnPositive = setTechEndTimeDialog.getActionButton(DialogAction.POSITIVE);
            final TextView tehcName = (TextView) setTechEndTimeDialog.findViewById(R.id.techName);
            final TextView date = (TextView) setTechEndTimeDialog.findViewById(R.id.date);
            NumberPicker duration = (NumberPicker) setTechEndTimeDialog.findViewById(R.id.duration);
            NumberPicker startHour = (NumberPicker) setTechEndTimeDialog.findViewById(R.id.startHour);
            NumberPicker startMinit = (NumberPicker) setTechEndTimeDialog.findViewById(R.id.startMinit);

            duration.setMinValue(1);
            duration.setMaxValue(24);
            startHour.setMinValue(0);
            startHour.setMaxValue(24);
            startMinit.setMinValue(0);
            startMinit.setMaxValue(60);


            duration.setWrapSelectorWheel(true);
            startHour.setWrapSelectorWheel(true);
            startMinit.setWrapSelectorWheel(true);


            date.setText(DATE_FORMAT_WITH_TIME.format(time.getTime()));


            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    time.set(Calendar.YEAR,year);
                                    time.set(Calendar.MONTH,monthOfYear);
                                    time.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                    date.setText(DATE_FORMAT_WITH_TIME.format(time.getTime()));


                                }
                            }, time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            });



            Spinner tech = (Spinner) setTechEndTimeDialog.findViewById(R.id.techList);
            listTechAdapter = new GenericPrefListAdapter(getContext(), Technician.getTechnicianList());
            tech.setAdapter(listTechAdapter);
            tech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    thecNum[0] = position;
                    tehcName.setText((Technician.getTechnicianList().get(position).getUserNameString()));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            setTechEndTimeDialog.show();

            startHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    time.set(Calendar.HOUR_OF_DAY,newVal);
                    Log.d("onValueChange", "HOUR_OF_DAY: "+time.get(Calendar.HOUR_OF_DAY));

                    date.setText(DATE_FORMAT_WITH_TIME.format(time.getTime()));

                }
            });
            startMinit.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    time.set(Calendar.MINUTE,newVal);
                    Log.d("onValueChange", "MINUTE: "+time.get(Calendar.MINUTE));
                    date.setText(DATE_FORMAT_WITH_TIME.format(time.getTime()));

                }
            });
            duration.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    ticketAssignedDurationHours = newVal;
                    Log.d("onValueChange", "ticketAssignedDurationHours: "+ticketAssignedDurationHours);
                }
            });


            btnNegativ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTechEndTimeDialog.dismiss();
                }
            });
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String startTimeformatted = DATE_FORMAT_WITH_TIME.format(time.getTime());
                    time.add(Calendar.HOUR_OF_DAY,ticketAssignedDurationHours);
                    Log.d("onValueChange", "time: "+time.get(Calendar.HOUR_OF_DAY));
                    Log.d("onValueChange", "time: "+time.get(Calendar.MINUTE));

                    String endTimeformatted = DATE_FORMAT_WITH_TIME.format(time.getTime());
                    String ticketAssignedDuration = ticketAssignedDurationHours+":"+ticketAssignedDurationMin;

                    Log.d("onValueChange", "startTimeString: "+startTimeformatted);
                    Log.d("onValueChange", "endTimeformatted: "+endTimeformatted);

                        Map<String, String> updates = new HashMap<>();
                        updates.put("ticketAssignedDuration", ticketAssignedDuration);
                        updates.put("ticketAssignedDateTime", startTimeformatted);
                        updates.put("ticketCloseDateTime", endTimeformatted);
                        updates.put("techID",(Technician.getTechnicianList().get(thecNum[0])).getUserID());
                        updates.put("techNameString",(Technician.getTechnicianList().get(thecNum[0])).getUserNameString());
                        UtlFirebase.updateTicket(ticketID, (HashMap<String, String>) updates);
                        getActivity().getSupportFragmentManager().popBackStack();
                    setTechEndTimeDialog.dismiss();
                }
            });
            setTechEndTimeDialog.show();
        } else {
            FragmentTransaction fragmentManager = (getActivity().getSupportFragmentManager())
                    .beginTransaction();
            NewTicket newTicket = new NewTicket();
            String formatted = DATE_FORMAT_WITH_TIME.format(time.getTime());
            bundel.putString("startTime", formatted);
            newTicket.setArguments(bundel);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }



    @Override
    public void onEventClick(final WeekViewEvent event, RectF eventRect) {

         new MaterialDialog.Builder(getContext())
                            .title("האם להוסיף ארוע מקביל?")
                            .titleColor(Color.BLACK)
                            .positiveText("כן")
                            .negativeText("לא")
                            .backgroundColor(Color.WHITE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    onEmptyViewClicked(event.getStartTime());
                                    dialog.dismiss();

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
                                        dialog.dismiss();
                                }
                            })
                            .show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {


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


}
