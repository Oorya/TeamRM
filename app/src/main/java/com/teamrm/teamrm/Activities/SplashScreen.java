package com.teamrm.teamrm.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.teamrm.teamrm.Utility.NiceToast;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

import static com.teamrm.teamrm.Utility.UserSingleton.LOGINTAG;

public class SplashScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private ImageView iconWait;
    private TextView loadingStatus;
    private static final String TAG = "SplashScreen";
    public static Context context;

    private static final String PREF_ACCOUNT_NAME = "accountName";

    private static ArrayList<Ticket> tickets = new ArrayList<>();

    private FirebaseAuth firebaseAuth;

    private GoogleSignInOptions gso;
    public static GoogleSignInAccount acct;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;
    public static String userImage;
    private SignInButton signInButton;
    public static boolean resume = false;
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
        UtlFirebase.setCurrentContext(this);

        //Log.d("splash", "onCreate rotateWaitingIcon: ");
        rotateWaitingIcon();
        updateLoadingStatus("מכין אפליקציה לשימוש...");
        //Log.d("splash", "onCreate: ");

        // everything else that doesn't update UI
        permissionToDrawOverlays();
        context = this;
        if(UserSingleton.getInstance() != null)
        {
            //test
            DatabaseReference test = FirebaseDatabase.getInstance().getReference("test");
            test.push().setValue("create");
            Log.w("start app ", "on create service");
            startApp();
        }
        linearLayout = (LinearLayout) findViewById(R.id.load);

        mGoogleApiClient = App.getGoogleApiHelper().getGoogleApiClient();
        gso = App.getGoogleApiHelper().getGso();
        firebaseAuth = FirebaseAuth.getInstance();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        //signInButton.setScopes(gso.getScopeArray()); //deprecated - not needed
        signInButton.setOnClickListener(this);
        signInButton.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w("splash", "onStart: ");

        if(UserSingleton.getInstance() == null)
        {
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
                Log.d("splash", "else Got cached sign-in");

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
        } else if (requestCode == PERM_REQUEST_CODE_DRAW_OVERLAYS) {
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

        Log.d("splash", "handleSignInResult: ");

        if (result.isSuccess()) {
            Log.d(LOGINTAG, "Stage 2, Google login OK");
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct); //LOGIN STAGE 3 ->
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, acct.getEmail());
            editor.apply();

            userImage = acct.getPhotoUrl() == null ? "" : acct.getPhotoUrl().toString();
            Log.w("IMAGE GOOGLE ACCOUNT", acct.getPhotoUrl() == null ? "NULL" : acct.getPhotoUrl().toString());

            Log.d("splash", "handleSignInResult: ");
        }
        else {
            signInButton.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Log.d(LOGINTAG, "Stage 3, signing in to Firebase with user " + acct.getEmail());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        Log.d("ON COMPLETE: ", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        logOnToApp(task.getResult().getUser());

                        if (!task.isSuccessful()) {
                            Log.w(LOGINTAG, "Stage 3 failed with error " + task.getException());
                            new NiceToast(context, "Authentication to app failed!", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });
    }

    private void logOnToApp(FirebaseUser firebaseUser){
        UtlFirebase.loginUser(firebaseUser, new FireBaseAble() {
            @Override
            public void resultTicket(Ticket ticket) {

            }

            @Override
            public void resultUser(Users user) {
                UserSingleton.init(user);       //LOGIN STAGE 7 -> init the UserSingleton with  user fetched from FireBase
                startApp();
            }

            @Override
            public void ticketListCallback(List<Ticket> tickets) {

            }

            @Override
            public void ticketLiteListCallback(List<TicketLite> ticketLites) {

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

    void startApp(){
        //test
        DatabaseReference test = FirebaseDatabase.getInstance().getReference("test");
        test.push().setValue("start");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.w("start app ", "method");
                startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                finish();
            }
        }, 3000);
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

