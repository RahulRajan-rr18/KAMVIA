package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ApprovalMemberDetailsActivity extends AppCompatActivity {
     TextView name , email , home_district;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_approval);

        getSupportActionBar().hide();
        

    }
}
