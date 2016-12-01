package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragFB extends Fragment {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private TextView txt;

    public LoginFragFB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView= inflater.inflate(R.layout.fragment_login_fb, container, false);
        Toast.makeText(getContext(), "SIGNUP!", Toast.LENGTH_SHORT).show();

        txt=(TextView)returnView.findViewById(R.id.ll);
        txt.setText("SHALOM");
        /*inputEmail = (EditText) returnView.findViewById(R.id.email);
        inputPassword = (EditText) returnView.findViewById(R.id.password);
        progressBar = (ProgressBar) returnView.findViewById(R.id.progressBar);
        btnSignup = (Button) returnView.findViewById(R.id.btn_signup);
        btnLogin = (Button) returnView.findViewById(R.id.btn_login);
        btnReset = (Button) returnView.findViewById(R.id.btn_reset_password);*/


        /*
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //startActivity(new Intent(this.getContext().getAssets(), TicketList.class));
            //finish();
        }







        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);





        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                Toast.makeText(getContext(), "SIGNUP!", Toast.LENGTH_SHORT).show();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(getContext(), TicketList.class);
                                    startActivity(intent);
                                    Toast.makeText(getContext(), "TSET", Toast.LENGTH_SHORT).show();

                                }
                            }

                        });
            }
        });*/
        return returnView;
    }

}
