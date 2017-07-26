package com.teamrm.teamrm.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.Adapter.PhotoAdapter;
import com.teamrm.teamrm.Broadcast.FirebaseBackgroundService;
import com.teamrm.teamrm.Broadcast.ServiceChecker;
import com.teamrm.teamrm.Fragment.AdminSettingsAdvanced;
import com.teamrm.teamrm.Fragment.AdminSettingsBasic;
import com.teamrm.teamrm.Fragment.AdminSettingsDefineTechs;
import com.teamrm.teamrm.Fragment.CalendarView;
import com.teamrm.teamrm.Fragment.FragmentDrawer;
import com.teamrm.teamrm.Fragment.NewTicket;
import com.teamrm.teamrm.Fragment.TicketList;
import com.teamrm.teamrm.Fragment.TicketView;
import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.InternetAvailable;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlImage;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.io.File;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static com.teamrm.teamrm.Utility.UserSingleton.TE_SEQ;


public class HomeScreen extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private FrameLayout frameLayout;
    private Context context;
    private static final int ACTION_OVERLAY = 300, FROM_PHOTO_ADAPTER = 2;
    private final static String[] TAG_FRAGMENT = {"NEW_TICKET", "CALENDER", "TICKET_LIST", "COMPANY_SEATING", "COMPANY_SEATING_ADVANCED"};
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireBaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        Log.d(UserSingleton.LOGINTAG, "UserSingleton.getLoadedUserType = " + UserSingleton.getLoadedUserType());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int mScreenWidth = displaymetrics.widthPixels;
        int mScreenHeight = displaymetrics.heightPixels;
        View view = getLayoutInflater().inflate(R.layout.activity_home_screen, null);
        setContentView(view, new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight));

        new NiceToast(this, "User " + UserSingleton.getInstance().getUserEmail() + "\n"
                + "logged in as " + UserSingleton.getLoadedUserType(), NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG).show();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        firebaseAuth = FirebaseAuth.getInstance();
        fireBaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (null == firebaseAuth.getCurrentUser()) {
                    Intent splashScreenIntent = new Intent(context, SplashScreen.class);
                    splashScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // for starting Intent from no-Activity context
                    splashScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // for finishing all running activities
                    startActivity(splashScreenIntent);
                }
            }
        };

        frameLayout = (FrameLayout) findViewById(R.id.container_body);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (!ServiceChecker.isServiceStarted(this, FirebaseBackgroundService.class)) {
            App.getInstance().startService();
        }
        TextView appIcon = (TextView) findViewById(R.id.appIcon);
        appIcon.setTypeface(Typeface.createFromAsset(this.getAssets(), "Assistant-Bold.ttf"));

        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.container_body, new TicketList()).addToBackStack(TAG_FRAGMENT[2]);
        fragmentTransaction.replace(R.id.container_body, new TicketList(), TicketList.FRAGMENT_TRANSACTION).addToBackStack(TicketList.FRAGMENT_TRANSACTION);
        fragmentTransaction.commit();
        setTitle(getResources().getStringArray(R.array.nav_list)[0]);

        if(SplashScreen.notifyEnrollmentAdmin)
        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            AdminSettingsDefineTechs settingsDefineTechs = new AdminSettingsDefineTechs();
            ft.replace(R.id.container_body, settingsDefineTechs)
                    .addToBackStack(AdminSettingsDefineTechs.FRAGMENT_TRANSACTION).commit();
            setTitle("הגדרת טכנאים");
            //findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
            SplashScreen.notifyEnrollmentAdmin = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireBaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != fireBaseAuthStateListener) {
            firebaseAuth.removeAuthStateListener(fireBaseAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enrollmentCodeActionSelector(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.w("Permission home screen", "Before if " + requestCode);

        if (requestCode == TicketView.PERMISSION_PHONE_REQUEST_CODE) {
            TicketView ticketListFragment = (TicketView) getSupportFragmentManager().findFragmentByTag(TicketView.FRAGMENT_TRANSACTION);
            //ticketListFragment.openPhoneDialog();
        } else if (requestCode == FROM_PHOTO_ADAPTER) {
            /*PhotoPicker.builder()
                    .setPhotoCount(PhotoAdapter.MAX)
                    .setShowCamera(true)
                    .setPreviewEnabled(false)
                    .setSelected(NewTicket.selectedPhotos)
                    .start(this);*/

            PhotoPicker.builder()
                    .setPhotoCount(PhotoAdapter.MAX)
                    .setShowCamera(true)
                    .setPreviewEnabled(false)
                    .start(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("REQUEST = " + requestCode, "onActivityResult HOME SCREEN");
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {


            List<String> photos = null;
            if (data != null && data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS) != null && requestCode == 233) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                if (NewTicket.imgClick == 1) {
                    NewTicket.imageView1.setImageBitmap(UtlImage.fileToBitmap(new File(photos.get(0))));
                    NewTicket.imgUri1 = UtlImage.fileToUri(new File(photos.get(0)), this);
                    NewTicket.imgClick = 0;
                } else {
                    NewTicket.imageView2.setImageBitmap(UtlImage.fileToBitmap(new File(photos.get(0))));
                    NewTicket.imgUri2 = UtlImage.fileToUri(new File(photos.get(0)), this);
                    NewTicket.imgClick = 0;
                }
            }
            /*else if(data != null && data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS) != null && requestCode == 666)
            {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            if (data != null && data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS) != null && requestCode == 233) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                switch (photos.size()) {
                    case 1:
                        NewTicket.imgUri1 = UtlImage.fileToUri(new File(photos.get(0)), this);
                        break;
                    case 2:
                        NewTicket.imgUri1 = UtlImage.fileToUri(new File(photos.get(0)), this);
                        NewTicket.imgUri2 = UtlImage.fileToUri(new File(photos.get(1)), this);
                        break;
                }
            } else if (data != null && data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS) != null && requestCode == 666) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            NewTicket.selectedPhotos.clear();

            if (photos != null) {
                NewTicket.selectedPhotos.addAll(photos);
            }
            NewTicket.photoAdapter.notifyDataSetChanged();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketList ticketList = new TicketList();
        if (UserSingleton.getLoadedUserType().equals(Users.STATUS_CLIENT)) {
            if (position > 1)
                position += 2;

        } else if (UserSingleton.getLoadedUserType().equals(Users.STATUS_TECH)) {
            Log.d("user tech", "position = " + position);

            if (position >= 3)
                position += 1;

        }

        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.container_body, ticketList)
                        .addToBackStack(TicketList.FRAGMENT_TRANSACTION).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[0]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);



                break;

            case 1:
                //NewTicket ticket = new NewTicket();
                //fragmentTransaction.replace(R.id.container_body, ticket).commit();
                //setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                //findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);



                //fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right_rtl, FragmentTransaction.TRANSIT_NONE, R.anim.slide_in_from_left_rtl, FragmentTransaction.TRANSIT_NONE);
                fragmentTransaction.replace(R.id.container_body, new NewTicket(), NewTicket.FRAGMENT_TRANSACTION);
                fragmentTransaction.addToBackStack(NewTicket.FRAGMENT_TRANSACTION);
                fragmentTransaction.commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

                break;

            case 2:
                Log.d("droeswech", "case2");
                CalendarView calendarView = new CalendarView();
                fragmentTransaction.replace(R.id.container_body, calendarView)
                        .addToBackStack(CalendarView.FRAGMENT_TRANSACTION).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[2]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.GONE);
                break;

            case 3:
                AdminSettingsBasic adminSettingsBasic = new AdminSettingsBasic();
                fragmentTransaction.replace(R.id.container_body, adminSettingsBasic)
                        .addToBackStack(AdminSettingsBasic.FRAGMENT_TRANSACTION).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[3]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 4:
                AdminSettingsAdvanced adminSettingsAdvanced = new AdminSettingsAdvanced();
                fragmentTransaction.replace(R.id.container_body, adminSettingsAdvanced)
                        .addToBackStack(AdminSettingsAdvanced.FRAGMENT_TRANSACTION).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[4]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                break;

            case 5:
                signOut();
                break;

            default:

                fragmentTransaction.replace(R.id.container_body, ticketList)
                        .addToBackStack(TicketList.FRAGMENT_TRANSACTION).commit();
                setTitle(getResources().getStringArray(R.array.nav_list)[0]);
                findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

        }


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {

            if (getSupportFragmentManager().getBackStackEntryCount() > 1&&
                    !getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName()
                            .equals(TicketList.FRAGMENT_TRANSACTION)) {

                 String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName();

                if (getSupportFragmentManager().getBackStackEntryCount() - 2 >= 0&&tag!=null) {

                    Log.d("onBackPressed", "case tag = " + tag);
                    switch (tag) {
                        case CalendarView.FRAGMENT_TRANSACTION:
                            Log.d("onBackPressed", "case CalendarView = " + CalendarView.FRAGMENT_TRANSACTION);
                            setTitle(getResources().getStringArray(R.array.nav_list)[2]);
                            break;
                        case TicketList.FRAGMENT_TRANSACTION:
                            Log.d("onBackPressed", "case TicketList = " + TicketList.FRAGMENT_TRANSACTION);
                            setTitle(getResources().getStringArray(R.array.nav_list)[0]);
                            break;
                        case NewTicket.FRAGMENT_TRANSACTION:
                            Log.d("onBackPressed", "case NewTicket = " + NewTicket.FRAGMENT_TRANSACTION);
                            setTitle(getResources().getStringArray(R.array.nav_list)[1]);
                            //getSupportFragmentManager().popBackStack();
                            break;
                        case AdminSettingsAdvanced.FRAGMENT_TRANSACTION:
                            Log.d("onBackPressed", "case FragmentHelper = " + AdminSettingsAdvanced.FRAGMENT_TRANSACTION);
                            setTitle(getResources().getStringArray(R.array.nav_list)[4]);
                            break;
                        case AdminSettingsBasic.FRAGMENT_TRANSACTION:
                            Log.d("onBackPressed", "case FragmentHelper = " + AdminSettingsBasic.FRAGMENT_TRANSACTION);
                            setTitle(getResources().getStringArray(R.array.nav_list)[3]);

                            break;
                    }

                    findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);
                    super.onBackPressed();
                }
            } else {
                TicketList ticketListFragment = (TicketList) getSupportFragmentManager().findFragmentByTag(TicketList.FRAGMENT_TRANSACTION);
                if (!ticketListFragment.closeSearch()) {
                    new MaterialDialog.Builder(this)
                            .title("האם ברצונך לצאת מהאפליקציה?")
                            .titleColor(Color.BLACK)
                            .positiveText("כן")
                            .negativeText("לא")
                            .backgroundColor(Color.WHITE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    exitApp();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .show();
                }
            }
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void exitApp() {
        finish();
    }

    private void signOut() {
        App.getInstance().signOut();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public Context getContext() {
        return this.context;
    }

    public void enrollmentCodeActionSelector(final int counter) {
        if (counter > 3) {
            if (UserSingleton.getLoadedUserType() == Users.STATUS_PENDING_TECH) {
                new NiceToast(context, "Error getting enrollmentCode", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
            }
        } else if (EnrollmentCode.getEnrollmentCodeList().isEmpty()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enrollmentCodeActionSelector(counter + 1);
                }
            }, 1000);
        } else {
            switch (UserSingleton.getLoadedUserType()) {
                case (Users.STATUS_ADMIN):
                    if (!EnrollmentCode.getEnrollmentCodeList().isEmpty()) {
                        for (EnrollmentCode singleEnrollmentCode : EnrollmentCode.getEnrollmentCodeList()) {
                            switch (singleEnrollmentCode.getEnrollmentStatus()) {

                                case (EnrollmentCode.STATUS_ISSUED):
                                case (EnrollmentCode.STATUS_ACCEPTED):
                                case (EnrollmentCode.STATUS_DECLINED):
                                case (EnrollmentCode.STATUS_FINALIZED):
                                    // do nothing
                                    break;

                                case (EnrollmentCode.STATUS_PENDING):
                                    new NiceToast(context, "PendingTech waiting for approval", NiceToast.NICETOAST_WARNING, Toast.LENGTH_LONG).show();
                                    UtlNotification notificationPending = new UtlNotification("רישום בתהליך", "הרשמת טכנאי מחכה לאישור",true);
                                    notificationPending.sendNotification();
                                    // TODO: TE_SEQ notify admin about PendingTech waiting for approval
                                    break;

                                case (EnrollmentCode.STATUS_CANCELLED):
                                    new NiceToast(context, "PendingTech cancelled the enrollment", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                                    UtlNotification notificationCancel = new UtlNotification("רישום בוטל", "טכנאי ביטל הרשמה",true);
                                    notificationCancel.sendNotification();
                                    //TODO: TE_SEQ notify admin that PendingTech cancelled the enrollment
                                    break;

                                default: //do nothing
                            }
                        }
                    }
                    break;

                case (Users.STATUS_PENDING_TECH):
                    EnrollmentCode pendingTechEnrollmentCode = EnrollmentCode.getEnrollmentCodeList().get(0);
                    switch (pendingTechEnrollmentCode.getEnrollmentStatus()) {

                        case (EnrollmentCode.STATUS_PENDING):
                            UtlNotification notificationPending = new UtlNotification("רישום בתהליך", "הרשמתך כטכנאי מחכה לאישור");
                            notificationPending.sendNotification();
                            // TODO: TE_SEQ display message "waiting for Admin approval of Enrollment"
                            break;

                        case (EnrollmentCode.STATUS_DECLINED):
                            UtlNotification notificationCancel = new UtlNotification("רישום בוטל", "הרשמתך כטכנאי בוטלה");
                            notificationCancel.sendNotification();
                            // TODO: TE_SEQ notify enrollment declined
                            new NiceToast(context, "Enrollment was declined", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG).show();
                            UtlFirebase.rollbackPendingTechToClient(pendingTechEnrollmentCode.getEnrollmentCodeID(), new FireBaseBooleanCallback() {
                                @Override
                                public void booleanCallback(boolean isTrue) {
                                    if (isTrue) {
                                        App.getInstance().signOut();
                                    }
                                }
                            });
                            // TODO: TE_SEQ roll back user to Client
                            // TODO: TE_SEQ Logout
                            break;

                        case (EnrollmentCode.STATUS_ACCEPTED):
                            Log.d(TE_SEQ, "@HomeScreen: found EnrollmentCode -> Accepted");
                            UtlNotification notificationAccepted = new UtlNotification("רישום אושר", "הרשמתך כטכנאי אושרה בהצלחה");
                            notificationAccepted.sendNotification();
                            // TODO: TE_SEQ notify enrollment accepted
                            new NiceToast(context, "Enrollment was accepted", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG).show();
                            UtlFirebase.promotePendingTechToTechnician(pendingTechEnrollmentCode.getEnrollmentCodeID(), new FireBaseBooleanCallback() {
                                @Override
                                public void booleanCallback(boolean isTrue) {
                                    if (isTrue) {
                                        App.getInstance().signOut();
                                    }
                                }
                            });
                            break;

                        default: //do nothing
                    }
                    break;

                case (Users.STATUS_CLIENT):
                case (Users.STATUS_TECH):
                    //do nothing
                    break;

                default: //do nothing
            }
        }
    }

}
