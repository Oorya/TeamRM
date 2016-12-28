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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FireBaseAble {


    private static final String PREF_ACCOUNT_NAME = "accountName";

    private Typeface tf;
    private GoogleSignInOptions gso;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";
    private ProgressDialog mProgressDialog;
    public static Context context;
    private UtlAlarmManager utlAlarmManager;
    private TextView fontX;
    private SignInButton signInButton;
    public static GoogleSignInAccount acct;
    public static String userName, email, userImage, userStatus, userId;
    public static boolean resume = false;
    private SharedPreferences prefUser;
    private SharedPreferences.Editor editorUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        tf = Typeface.createFromAsset(getAssets(), "Assistant-ExtraBold.ttf");
        fontX = (TextView)findViewById(R.id.fontX);
        fontX.setTypeface(tf);
        utlAlarmManager = new UtlAlarmManager(this);

        mGoogleApiClient= App.getGoogleApiHelper().getGoogleApiClient();
        gso = App.getGoogleApiHelper().getGso();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setVisibility(View.VISIBLE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        fireBaseListener();

    }

    private void fireBaseListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@Nonnull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Log.w("USER SIGNIN", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out

                    Log.w("USER SIGNOUT", "onAuthStateChanged:signed_out");
                }
            }
        };
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
    protected void onResume() {
        super.onResume();
        if (resume)
        {
            signInButton.setVisibility(View.VISIBLE);
            resume=false;
        }
    }

    @Override
    public void onClick(View v) {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("FIREBASE AUTH GOOGLE: ", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@Nonnull Task<AuthResult> task) {
                        Log.d("ON COMPLETE: ", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        task.getResult().getUser().getUid();
                        if (!task.isSuccessful()) {
                            Log.w("TASK FAILED: ", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
            firebaseAuthWithGoogle(acct);
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, acct.getEmail());
            editor.apply();

            prefUser = getApplicationContext().getSharedPreferences("users", MODE_PRIVATE);
            if(!prefUser.contains(acct.getId()))
            {

            }

            editorUser = prefUser.edit();
            editorUser.putString(acct.getId(), acct.getDisplayName()).commit();


            userId=acct.getId();
            userName=acct.getDisplayName();
            email=acct.getEmail();
            //userStatus="User";
            //UtlFirebase.stateListener(userStatus,email,"NULL");

            UserSingleton.init(acct);

            //UtlFirebase.getUserByKey(userId,this); //fix AsyncTask racing
            Log.w("EMAIL", UserSingleton.getInstance().getEmail()+" ==");

            userImage = acct.getPhotoUrl()==null?"":acct.getPhotoUrl().toString();
            Log.w("IMAGE GOOGLE ACCOUNT", acct.getPhotoUrl()==null?"NULL":"NOT NULL");

            signInButton.setVisibility(View.GONE);
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
        FirebaseAuth.getInstance().signOut();
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

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {
        userStatus=user.getStatus();
        if (userStatus.equals("Admin"))
        {
            UtlFirebase.stateListener(userStatus, email, user.getCompany());
        }
        else if (userStatus.equals("Client"))
        {
            UtlFirebase.stateListener(userStatus, email, "NULL");
        }
        else // TECH
        {
            //UtlFirebase.stateListener(userStatus, email, "NULL");
        }

    }

    @Override
    public void resultList(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }
}