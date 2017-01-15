package com.teamrm.teamrm.Utility;


import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.Interfaces.CalendarHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CalendarUtil extends Activity implements EasyPermissions.PermissionCallbacks {
    private static final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final int REQUEST_LOCATION = 1004;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};
    private static final String TAG = "CalendarUtil";

    private static com.google.api.services.calendar.Calendar mService;
    private static Context _context;

    private static  GoogleAccountCredential mCredential;
    private static ProgressDialog mProgress;
    private List<CalendarListEntry> items1;
    private static CalendarHelper calendarHelper;
    private static List<CalendarListEntry> calList= new ArrayList<>();
    


    private Exception mLastError = null;

    public CalendarUtil(){}
    
    public CalendarUtil(Context context , Object  result )
    {
        _context = context;
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Calling Google Calendar API ...");
        mProgress.setCanceledOnTouchOutside(false);

        // Initialize credentials and service object.
        this.mCredential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        calendarHelper=(CalendarHelper) result;
    }
    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    public void getResultsFromApi() {

        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (!isDeviceOnline()) {
            Toast.makeText(_context, "isDeviceOnline false", Toast.LENGTH_LONG).show();
        } else if (mCredential.getSelectedAccountName() == null) {
            Log.d("REQUEST", "getSelectedAccountName() == null");

            chooseAccount();
        } else {

            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, mCredential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            Log.d("REQUEST", mService.toString());


            calList.addAll(getCalList());
        }
    }


    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Log.d("REQUEST", "chooseAccount currentapiVersion: "+android.os.Build.VERSION.SDK_INT);

        if (currentapiVersion < Build.VERSION_CODES.M)
        {
            Log.d("REQUEST", "chooseAccount currentapiVersion: "+Build.VERSION_CODES.M);
            if (EasyPermissions.hasPermissions(_context, Manifest.permission.GET_ACCOUNTS))
            {
                String accountName = MainActivity.acct.getEmail();

                if (accountName != null)
                {
                    mCredential.setSelectedAccountName(accountName);

                    Log.d("REQUEST", "chooseAccount getSelectedAccount: "+mCredential.getSelectedAccount());

                    getResultsFromApi();
                } 
                else 
                {
                    // Start a dialog from which the user can choose an account
                    ((Activity)_context).startActivityForResult(
                            mCredential.newChooseAccountIntent(),
                            REQUEST_ACCOUNT_PICKER);
                }
                // Request the GET_ACCOUNTS permission via a user dialog
            } 
            else 
            {
                EasyPermissions.requestPermissions(
                        _context,
                        "This app needs to access your Google account (via Contacts).",
                        REQUEST_PERMISSION_GET_ACCOUNTS,
                        Manifest.permission.GET_ACCOUNTS);
            }

        }
        else if(ActivityCompat.checkSelfPermission(_context, Manifest.permission.GET_ACCOUNTS)
                == _context.getPackageManager().PERMISSION_GRANTED)
        {
            String accountName = UserSingleton.getInstance().getUserEmail();
            Log.d("REQUEST", "chooseAccount 23 accountName: "+accountName);

            if (accountName != null)
            {
                mCredential.setSelectedAccountName(accountName);

                Log.d("REQUEST", "chooseAccount getSelectedAccount: "+mCredential.getSelectedAccount());

                getResultsFromApi();
            }
            else
            {
                // Start a dialog from which the user can choose an account
                ((Activity) _context).startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {

            ActivityCompat.requestPermissions(((Activity) _context),
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_PERMISSION_GET_ACCOUNTS);



        }
    
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("REQUEST = ","onActivityResult CALENDAR");


        switch (requestCode) {

            
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(_context, "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.", Toast.LENGTH_LONG).show();
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {

                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                        mCredential.setSelectedAccountName(accountName);
                    Log.d("REQUEST", "onActivityResult acct.getUserEmail: "+accountName);

                        getResultsFromApi();
                    //}
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {

                    getResultsFromApi();
                }
                break;

        }
    }
    

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("REQUEST = ","onRequestPermissionsResult CALENDAR");


        if (requestCode == REQUEST_PERMISSION_GET_ACCOUNTS)
        {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to

                getResultsFromApi();
            }else
            {
                Toast.makeText(_context, "האפליקציה צריכה הרשאה לגשת לחשבון האימייל שלך"+"\n"+"אנא אשר כדיי שנוכל להתחיל לעבוד ", Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(((Activity) _context),
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        REQUEST_PERMISSION_GET_ACCOUNTS);
            }
        }

    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(_context);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(_context);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */

    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                (Activity) _context,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    /**
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, List<Event>, List<Event>> {

        
       
        public MakeRequestTask() {}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           mProgress.show();
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
               mLastError = e;
                cancel(true);
                return null;
            }
        }
        private List<Event> getDataFromApi() throws IOException {
            
            List<Event>  items = new ArrayList<>();
            for (CalendarListEntry calenders : calList)
           {
                Events events = mService.events().list(calenders.getId())
                        .setMaxResults(100)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();
                 items.addAll(events.getItems());
             
          }
            return items;
        }

        @Override
        protected void onPostExecute(List<Event> output)
        {
            mProgress.hide();
            if (output == null || output.size() == 0)
            {
                Toast.makeText(_context, "no resolt", Toast.LENGTH_LONG).show();
                Log.d("list  mWeeViewEvent = ","");
            }
            else
            {
               // Toast.makeText(_context, "resolt ok ", Toast.LENGTH_LONG).show();

                calendarHelper.getEventList(output);
            }
        }
 
        
        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                        showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                  //  UserRecoverable(mLastError);
                } else {
                  //  mOutputText.setText("The following error occurred:\n"
                        //    + mLastError.getMessage());
                }
            } else {
               // mOutputText.setText("Request cancelled.");
            }
        }
    }
    private void  UserRecoverable(UserRecoverableAuthIOException e)
    {
        ((Activity) _context).startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
        
    }
    public void addNewEvent(final String calendarId ,final String summary,
                            final String description, final String uid, final Date startDate,
                            final Date endDate )
    {

        new AsyncTask<Void, Void, Void>() {

            Event event = new Event()
                    .setSummary(summary)
                    .setDescription(description)
                    .setId(uid);//uuid

            @Override
            protected Void doInBackground(Void... params) {
                SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
                    String startDateFormated = fromUser.format(startDate);
                    String endDateFormated = fromUser.format(endDate);
                    DateTime startDateTime = new DateTime(startDateFormated);
                    DateTime endDateTime = new DateTime(endDateFormated);
                    EventDateTime startEventDateTime = new EventDateTime().setDate(startDateTime);
                    EventDateTime endEventDateTime = new EventDateTime().setDate(endDateTime);
                    event.setStart(startEventDateTime);
                    event.setEnd(endEventDateTime);
                try {
                    event = mService.events().insert(calendarId, event).execute();
                } catch (IOException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(_context, "event add...", Toast.LENGTH_LONG).show();

            }
        }.execute();
    }

    public void delete(String Id) {
        new EventAction(Id, 1,this).execute();
    }

    public void update(String Id, String updateString) {
        Log.d(TAG, "update:in update ");
        new EventAction(Id, 2, updateString, this).execute();
    }

    public Event getEvent(String Id) {
        new EventAction(Id, 3, this).execute();
        return null;
    }

    private class EventAction extends AsyncTask<Void, Event, Event> {
        int task;
        String updateString, Id;
        Event event;
        CalendarHelper CalendarHelper;

        public EventAction(String id, int task,Object  CalendarHelper ) {
            this.CalendarHelper=(CalendarHelper) CalendarHelper;
            this.Id = id;
            this.task = task;
        }

        public EventAction(String id, int task, String updateString,Object  CalendarHelper) {
            this.CalendarHelper=(CalendarHelper) CalendarHelper;
            this.Id = id;
            this.task = task;
            this.updateString = updateString;
        }

        @Override
        protected Event doInBackground(Void... params) {
            try {
                switch (this.task) {
                    case 1:
                        delete(this.Id);
                        return event = null;
                    case 2:
                        return update(this.Id, this.updateString);
                    case 3:
                        return getEvent(this.Id);
                    default:
                        return event = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void delete(String id) throws IOException {
            mService.events().delete("primary", id).execute();
        }

        private Event update(String id, String updateString) throws IOException {
            // Retrieve the event from the API
            Event event;
            Event updatedEvent;
            event = mService.events().get("primary", id).execute();
            // Make a change
            event.setSummary(updateString);
            // Update the event
            updatedEvent = mService.events().update("primary", event.getId(), event).execute();

            return updatedEvent;
        }

        private Event getEvent(String id) throws IOException {
            event = mService.events().get("primary", id).execute();
            return event;
        }


        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute(event);
            Log.d(TAG, "update:in onPostExecute ");

            if (this.task == 1)
            {
                Toast.makeText(_context,"EVENT DELIT",Toast.LENGTH_LONG).show();
            }else if(this.task == 3||this.task == 2) {
                this.CalendarHelper.getResult(event);
            }
        }

    }

    public void calendarView() {
        Date NOW = new Date();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("startTime");
        ContentUris.appendId(builder, NOW.getTime());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        _context.startActivity(intent);
        Log.d(TAG, NOW.toString());

    }

    public String createdCalendar()
    {

        final Uri CAL_URI = CalendarContract.Calendars.CONTENT_URI;

        final ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, "shealtiel84@gmail.com");
        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        cv.put(CalendarContract.Calendars.NAME, "shaltyTest");
        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "shaltyTest");
        cv.put(CalendarContract.Calendars.CALENDAR_COLOR, 0xEA8561);
        //user can only read the calendar
        cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, "shealtiel84@gmail.com");
        cv.put(CalendarContract.Calendars.VISIBLE, 1);
        cv.put(CalendarContract.Calendars.SYNC_EVENTS, 1);


        ContentResolver cr = _context.getContentResolver();


        //insert the calendar into the database
        if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(_context,"NO permission ",Toast.LENGTH_LONG).show();
        }
        Uri newUri = cr.insert(CAL_URI, cv);

        final Long calId = Long.parseLong(newUri.getLastPathSegment());
        return null;
    }
    public void sherCalendar(String scopeValue)
    {
        final AclRule rule = new AclRule();
        final AclRule.Scope scope = new AclRule.Scope();
        scope.setType("user").setValue(scopeValue);
        rule.setScope(scope).setRole("writer");

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                try {
                    AclRule createdRule =mService.acl().insert("primary", rule).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(_context,"ACL INSERT  success...",Toast.LENGTH_LONG).show();
            }
        }.execute();

    }
    public List<CalendarListEntry> getCalList()
    {
        new AsyncTask<Void,Void,Void>()
        {
            List<CalendarListEntry> items;
            String pageToken = null;
            CalendarList calendarList = null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // mProgress.show();
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                do {
                    try {
                        if(mService==null)
                        calendarList = mService.calendarList().list().setPageToken(pageToken).execute();
                    } catch (UserRecoverableAuthIOException e) {
                        UserRecoverable(e);
                    } catch (IOException e) {
                    }
                    if(calendarList != null ) {
                        items = calendarList.getItems();
                        pageToken = calendarList.getNextPageToken();
                    }
                } while (pageToken != null);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(items!=null) {
                    calList = items;
                    new MakeRequestTask().execute();
                }
            }
        }.execute();
        return calList;
    }
}

