package com.teamrm.teamrm.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.GoogleApiHelper;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    private static final String PREF_ACCOUNT_NAME = "accountName";

    private Typeface tf;
    GoogleSignInOptions gso;
    public GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";
    private ProgressDialog mProgressDialog;
    public static Context context;
    private UtlAlarmManager utlAlarmManager;
    private TextView fontX;
    public static GoogleSignInAccount acct;
    public static String userName;
    public static String email;
    public static String userImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        tf = Typeface.createFromAsset(getAssets(), "Assistant-ExtraBold.ttf");
        fontX = (TextView)findViewById(R.id.fontX);
        fontX.setTypeface(tf);
        utlAlarmManager = new UtlAlarmManager(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        GoogleApiHelper googleApiHelper = new GoogleApiHelper(this);
        googleApiHelper.setApiClient(mGoogleApiClient);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        UtlFirebase.stateListener("User",MainActivity.email,"NULL");
    }



    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //  showProgressDialog();
            //  opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
            //      @Override
            //     public void onResult(GoogleSignInResult googleSignInResult) {
            //          hideProgressDialog();
            //          handleSignInResult(googleSignInResult);
            //      }
            //   });
        }
    }
    @Override
    public void onClick(View v) {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();

            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, acct.getEmail());
            editor.apply();
            userName=acct.getDisplayName();
            email=acct.getEmail();
            if(acct.getPhotoUrl().toString()==null)
            {

            }
            userImage=acct.getPhotoUrl().toString();

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        } else {
            Toast.makeText(this,"Incorrect Username or Password ",Toast.LENGTH_LONG).show();
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    public void signOut(View V) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        Toast.makeText(context,"logout OK",Toast.LENGTH_LONG).show();

                        // [START_EXCLUDE]
                        //  updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }


    public void alert10Sec(View view) {

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.SECOND,180);//(Calendar.DATE)
        utlAlarmManager.setAlarm(calendar.getTime(), TicketStateAble.TICKET_LIST_STATUS_OK);
        Log.d("MESSEGE","alert10Sec");


    }
    public void alert1Sec(View view) {

        // Calendar calendar = GregorianCalendar.getInstance();
        // calendar.add(Calendar.SECOND, 10);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,10);//(Calendar.DATE)
        utlAlarmManager.setAlarm(calendar.getTime(), TicketStateAble.TICKET_LIST_STATUS_OK);
        Log.d("MESSEGE","alert1Sec");
    }

    public void stopAlert(View view) {
        utlAlarmManager.cancelAlarm(this.utlAlarmManager);
        Log.d("MESSEGE","stopAlert");
    }

    public void nev(View view) {
        Intent nav = new Intent(this,HomeScreen.class);
        startActivity(nav);
    }



    public void btnTestStates(View view) {
        startActivity(new Intent(this,TestStates.class));
    }

    public void goToSplashScreen(View view) {startActivity(new Intent(this,SplashScreen.class));}
}