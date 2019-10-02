package com.ics.easy_ph_repair.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ics.easy_ph_repair.R;

public class EasySplash extends AppCompatActivity {
    public final static int Timeleft = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(EasySplash.this, NavigationActivity.class);
                startActivity(i);
                finish();
            }
        }, Timeleft);
    }
}
