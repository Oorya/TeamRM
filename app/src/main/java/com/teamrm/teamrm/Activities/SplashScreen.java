package com.teamrm.teamrm.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.teamrm.teamrm.Adapter.TicketListAdapter;
import com.teamrm.teamrm.Broadcast.FirebaseBackgroundService;
import com.teamrm.teamrm.Broadcast.ServiceChecker;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.EditTextValidation;
import com.teamrm.teamrm.Utility.InternetAvailable;
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.teamrm.teamrm.Utility.UserSingleton.LOGINTAG;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = ":::SplashScreen";
    private Context context;

    private static final String PREF_ACCOUNT_NAME = "accountName";

    private FirebaseAuth firebaseAuth;

    private GoogleSignInOptions gso;
    public static GoogleSignInAccount acct;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;
    public static boolean resume = false, notifyEnrollmentAdmin;
    private SharedPreferences prefUser;
    private SharedPreferences.Editor editorUser;
    private LinearLayout linearLayout;
    public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;
    //private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();


        setContentView(R.layout.activity_splashscreen);
        if(!InternetAvailable.isConnectingToInternet(this)){
            NiceToast niceToast = new NiceToast(this,"בדוק חיבור לאינטרנט",NiceToast.NICETOAST_INFORMATION,Toast.LENGTH_LONG);
            niceToast.show();
        }
        mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        gso = App.getGoogleApiHelper().getGso();
        firebaseAuth = FirebaseAuth.getInstance();
        //Log.d("splash", "onCreate rotateWaitingIcon: ");
        rotateWaitingIcon();
        updateLoadingStatus("מכין אפליקציה לשימוש...");
        //Log.d("splash", "onCreate: ");

        // everything else that doesn't update UI
        permissionToDrawOverlays();
        context = this;


        //Toast.makeText(this, UserSingleton.getInstance().getUserNameString() + " user exist", Toast.LENGTH_SHORT).show();


        linearLayout = (LinearLayout) findViewById(R.id.load);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        //signInButton.setScopes(gso.getScopeArray()); //deprecated - not needed
        signInButton.setOnClickListener(this);
        signInButton.setVisibility(View.GONE);
        if (getIntent().getBooleanExtra("enrollmentFrag", false)) {
            notifyEnrollmentAdmin = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");




        if (!UserSingleton.isUserLoaded()) {

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient); //LOGIN STAGE 1
            Log.d(LOGINTAG, "Stage 1, checking Google login");
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(LOGINTAG, "Stage 1a, logging in with cached Google login");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result); //LOGIN STAGE 2 ->
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                // showProgressDialog();
                Log.d(LOGINTAG, "else Got cached sign-in");

                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        // hideProgressDialog();
                        handleSignInResult(googleSignInResult); //LOGIN STAGE 2 ->
                    }
                });
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resume) {
            signInButton.setVisibility(View.VISIBLE);
            //linearLayout.setVisibility(View.GONE);
            resume = false;
        } else if (ServiceChecker.isServiceStarted(this, FirebaseBackgroundService.class) && UserSingleton.isUserLoaded()) {
            Log.d(LOGINTAG, "App is ready = true");
            startApp();
        } else {
            Log.d(LOGINTAG, "App is ready = false");
            String svc = Boolean.toString(ServiceChecker.isServiceStarted(this, FirebaseBackgroundService.class));
            String sngltn = Boolean.toString(UserSingleton.isUserLoaded());
            Log.d(LOGINTAG, "Service = " + svc + "; isUserLoaded = " + sngltn);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
                if (!Settings.canDrawOverlays(this)) {
                    // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
                }
            }
        }
    }

    public void rotateWaitingIcon() {
        ImageView iconWait = (ImageView) findViewById(R.id.iconWait);
        iconWait.clearAnimation();
        Animation rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        iconWait.startAnimation(rotateClockwise);
    }

    public void updateLoadingStatus(String newStatus) {
        TextView loadingStatus = (TextView) findViewById(R.id.loadingStatus);
        loadingStatus.setText(newStatus);
    }

    private void UpdateRecords() {
    }

    private int UserType() {
        return 1;
    }

    private boolean Connected() {
        return true;
    }

    private void stTicketList() {
    }

    @Override
    public void onClick(View v) {
        linearLayout.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d(TAG, "handleSignInResult: " + result.isSuccess());

        if (result.isSuccess()) {
            Log.d(TAG, "Stage 2, Google login OK");
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct); //LOGIN STAGE 3 ->
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, acct.getEmail());
            editor.apply();

            Log.d(TAG, "handleSignInResult: = Success");
        } else {
            Log.d(TAG, "handleSignInResult: = !Success = " + result.getStatus().getStatusCode());

            signInButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Log.d(TAG, "Stage 3, signing in to Firebase with user " + acct.getEmail());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        Log.w("IMAGE GOOGLE ACCOUNT", acct.getPhotoUrl() == null ? "NULL" : acct.getPhotoUrl().toString());
                        logOnToApp(task.getResult().getUser(), acct.getPhotoUrl().toString());

                        if (!task.isSuccessful()) {
                            Log.w(LOGINTAG, "Stage 3 failed with error " + task.getException());
                            new NiceToast(context, "Authentication to app failed!", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });
    }

    private void logOnToApp(FirebaseUser firebaseUser, String userImgPath) {
        UtlFirebase.loginUser(firebaseUser, userImgPath, new FireBaseAble() {
            @Override
            public void resultTicket(Ticket ticket) {

            }

            @Override
            public void resultUser(Users user) {
                if (null != user) {
                    Log.d(LOGINTAG, user.toString());
                    UserSingleton.init(user);       //LOGIN STAGE 7 -> init the UserSingleton with  user fetched from FireBase
                    Log.d(LOGINTAG, "getting TicketLites...");
                    if (UserSingleton.getLoadedUserType().equals(Users.STATUS_PENDING_TECH)) {
                        startApp();
                    } else {
                        UtlFirebase.getAllTicketLites(this);
                    }
                } else {
                    Log.e(LOGINTAG, "Received empty user from callback");
                }

            }

            @Override
            public void ticketListCallback(List<Ticket> tickets) {

            }

            @Override
            public void ticketLiteListCallback(List<TicketLite> ticketLites) {
                Log.d("ticketLiteListCallback", "ticketLiteListCallback: " + ticketLites.size());
                TicketLite.setTicketLiteList(ticketLites);
                TicketListAdapter.setAdpterList(TicketLite.getTicketLiteList());
                if (null != TicketListAdapter.getInstance())
                    TicketListAdapter.getInstance().notifyDataSetChanged();
                startApp();
            }

            @Override
            public void resultBoolean(boolean bool) {

            }

            @Override
            public void companyListCallback(List<Company> companies) {

            }

            @Override
            public void productListCallback(List<Product> products) {

            }

            @Override
            public void categoryListCallback(List<Category> categories) {

            }

            @Override
            public void regionListCallback(List<Region> regions) {

            }
        });
    }

    void startApp() {
        Log.d(LOGINTAG, "startApp "+UserSingleton.getLoadedUserObject().getUserLastSeen()+"");
        if (UserSingleton.isUserLoaded()&&UserSingleton.getLoadedUserObject().getUserLastSeen()==null) {

            setUserInitialDetail();
        }else {
            App.getInstance().startLastSeenRunnable();
            Intent homeScreenIntent = new Intent(this, HomeScreen.class);
            startActivity(homeScreenIntent);
            finish();
        }
    }

    private void setUserInitialDetail() {

        final MaterialDialog userInitialDetail = new MaterialDialog.Builder(this)

                .customView(R.layout.user_information_first_login, false)
                .title("לצורך התחברות ראשונית יש למלא את הפרטים הבאים")
                .positiveText(R.string.label_button_save)
                .contentColorRes(R.color.textColor_primary)
                .contentGravity(GravityEnum.CENTER)
                .negativeText(R.string.label_button_cancel)
                .titleGravity(GravityEnum.START)
                .buttonsGravity(GravityEnum.END)
                .backgroundColorRes(R.color.app_bg)
                .widgetColorRes(R.color.textColor_primary)
                .titleColorRes(R.color.textColor_lighter)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .dividerColorRes(R.color.textColor_lighter)
                .build();

        View btnNegativ = userInitialDetail.getActionButton(DialogAction.NEGATIVE);

            btnNegativ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().signOut();
                    signInButton.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    userInitialDetail.dismiss();
                }
            });
        View btnPositive = userInitialDetail.getActionButton(DialogAction.POSITIVE);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userName = (EditText) userInitialDetail.findViewById(R.id.userName);
                EditText phone = (EditText) userInitialDetail.findViewById(R.id.phone);
                EditText address = (EditText) userInitialDetail.findViewById(R.id.address);

                if(!EditTextValidation.checkEt(Arrays.asList(userName,phone,address))
                        && EditTextValidation.checkPhoneRegex(phone))
                {
                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put(UserSingleton.USER_PHONE,phone.getText().toString() );
                    updates.put(UserSingleton.USER_NAME_STRING,userName.getText().toString() );
                    updates.put(UserSingleton.USER_ADDRESS,address.getText().toString() );
                    updates.put(UserSingleton.USER_LAST_SEEN, Calendar.getInstance().toString());

                    UtlFirebase.updateUser(UserSingleton.getInstance().getUserID(),updates);
                    Intent homeScreenIntent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(homeScreenIntent);
                    userInitialDetail.dismiss();
                    finish();
                    App.getInstance().startLastSeenRunnable();
                }
            }
        });

        userInitialDetail.show();

    }

    public void permissionToDrawOverlays() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("onBackPressed", "onBackPressed");
        finish();
        finishAffinity();
    }
}

