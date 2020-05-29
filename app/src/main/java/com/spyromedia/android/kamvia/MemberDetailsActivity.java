package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MemberDetailsActivity extends AppCompatActivity {
     TextView name , email , home_district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        getSupportActionBar().hide();
        
        name  =findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        home_district = findViewById(R.id.tv_district);

        // TODO: 22-05-2020 just retrive 2 one or two details from db and put it into the fields 
    }
}
