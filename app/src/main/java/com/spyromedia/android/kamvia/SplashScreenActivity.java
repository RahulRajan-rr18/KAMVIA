package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;


public class SplashScreenActivity extends AppCompatActivity {
    private static final int splash_timeout = 2500;
    public int RC_STORAGE_PREM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Globals.currentUser.USER_ID.equals("")) {
                    Intent I=new Intent (SplashScreenActivity.this, LoginActivity.class);
                    startActivity(I);
                }
                else{
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }

                finish();
            }
        },splash_timeout);
        Globals.currentUser = Globals.loadLoginInfo(getBaseContext());

    }


}
