package com.teamrm.teamrm.Activities;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FireBaseAble {

    ImageView iconWait;
    TextView loadingStatus;
    private static final String PREF_ACCOUNT_NAME = "accountName";

    private GoogleSignInOptions gso;
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<TicketLite> ticketsLite = new ArrayList<>();

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "MainActivity";
    private ProgressDialog mProgressDialog;
    public static Context context;
    public static String userImage;
    private SignInButton signInButton;
    public static GoogleSignInAccount acct;
    public static boolean resume = false;
    private SharedPreferences prefUser;
    private SharedPreferences.Editor editorUser;
    private LinearLayout linearLayout;
    public final static int PERM_REQUEST_CODE_DRAW_OVERLAYS = 1234;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();
        setContentView(R.layout.activity_splashscreen);
        Log.d("splash", "onCreate rotateWaitingIcon: ");

        rotateWaitingIcon();
        updateLoadingStatus("מכין אפליקציה לשימוש...");
        Log.d("splash", "onCreate: ");


        // everything else that doesn't update UI
        permissionToDrawOverlays();
        linearLayout = (LinearLayout)findViewById(R.id.load);
        mGoogleApiClient= App.getGoogleApiHelper().getGoogleApiClient();
        gso = App.getGoogleApiHelper().getGso();
        firebaseAuth = FirebaseAuth.getInstance();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);
        signInButton.setVisibility(View.GONE);

        context=this;


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("splash", "onStart: ");

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("splash", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
             // showProgressDialog();
            Log.d("splash", "else Got cached sign-in");

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                  @Override
                 public void onResult(GoogleSignInResult googleSignInResult) {
                     // hideProgressDialog();
                      handleSignInResult(googleSignInResult);
                  }
               });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (resume)
        {
            signInButton.setVisibility(View.VISIBLE);
            //linearLayout.setVisibility(View.GONE);
            resume=false;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("splash", "onActivityResult: ");

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else if(requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS)
        {
            if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
                if (!Settings.canDrawOverlays(this)) {
                    // ADD UI FOR USER TO KNOW THAT UI for SYSTEM_ALERT_WINDOW permission was not granted earlier...
                }
            }
        }
    }

    public void rotateWaitingIcon() {
        iconWait = (ImageView) findViewById(R.id.iconWait);
        iconWait.clearAnimation();
        Animation rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        iconWait.startAnimation(rotateClockwise);
    }

    public void updateLoadingStatus(String newStatus) {
        loadingStatus = (TextView) findViewById(R.id.loadingStatus);
        loadingStatus.setText(newStatus);
    }

    private void UpdateRecords()
    {
    }

    private int UserType()
    {
        return 1;
    }
    private boolean Connected()
    {
        return true;
    }
    private void stTicketList()
    {
    }

    @Override
    public void onClick(View v) {
        linearLayout.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {

    }
    private void handleSignInResult(GoogleSignInResult result) {

        Log.d("splash", "handleSignInResult: ");


        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, acct.getEmail());
            editor.apply();

            userImage = acct.getPhotoUrl()==null?"":acct.getPhotoUrl().toString();
            Log.w("IMAGE GOOGLE ACCOUNT", acct.getPhotoUrl()==null?"NULL":acct.getPhotoUrl().toString());

            Log.d("splash", "handleSignInResult: ");


        } else {
            signInButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("FIREBASE AUTH GOOGLE: ", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        Log.d("ON COMPLETE: ", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        UserSingleton.init(task.getResult().getUser() , SplashScreen.this);
                        Log.w("EMAIL", UserSingleton.getInstance().getUserEmail()+" == ");

                        if (!task.isSuccessful()) {
                            Log.w("TASK FAILED: ", "signInWithCredential", task.getException());
                            Toast.makeText(SplashScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {
        UtlFirebase.getAllTicketLites(this);
    }

    @Override
    public void ticketListCallback(List<Ticket> ticket) {
        tickets.clear();
        tickets.addAll(ticket);
        Ticket.setTicketList(tickets);
        finish();

    }

    @Override
    public void resultBoolean(boolean bool) {

    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {
        ticketsLite.clear();
        ticketsLite.addAll(ticketLites);
        TicketLite.setTicketLiteList(ticketsLite);
        startActivity(new Intent(this,HomeScreen.class));
        UtlFirebase.getAllTickets(this);

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
    public void permissionToDrawOverlays() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERM_REQUEST_CODE_DRAW_OVERLAYS);
            }
        }
    }
}

