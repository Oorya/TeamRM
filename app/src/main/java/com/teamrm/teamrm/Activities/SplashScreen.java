package com.teamrm.teamrm.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamrm.teamrm.R;

import static com.teamrm.teamrm.R.id.fontX;

public class SplashScreen extends AppCompatActivity {

    ImageView iconWait;
    TextView loadingStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splashscreen);
        rotateWaitingIcon();
        updateLoadingStatus("מכין אפליקציה לשימוש...");
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
}

