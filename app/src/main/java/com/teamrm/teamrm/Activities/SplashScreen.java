package com.teamrm.teamrm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.R;

public class SplashScreen extends AppCompatActivity {

    ImageView iconWait;
    TextView loadingState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        iconWait = (ImageView)findViewById(R.id.iconWait);
        iconWait.clearAnimation();
        Animation rotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        iconWait.startAnimation(rotateClockwise);


    }
}
