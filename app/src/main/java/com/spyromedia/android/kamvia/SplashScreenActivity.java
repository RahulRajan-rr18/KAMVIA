package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static int splash_timeout = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Globals.USER_ID.isEmpty()){
                    Intent login=new Intent (SplashScreenActivity.this, LoginActivity.class);
                    startActivity(login);
                }
                else{
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }

                finish();
            }
        },splash_timeout);

    }
}
