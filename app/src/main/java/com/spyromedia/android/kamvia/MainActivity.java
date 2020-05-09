package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   Button  userUpdate,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userUpdate = findViewById(R.id.id_userupdate);
        btnLogin = findViewById(R.id.id_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login);
            }
        });
        userUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(MainActivity.this,UserProfileUpdate.class);
                startActivity(user);
            }
        });
    }
}
