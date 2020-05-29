package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TimelineViewActivity extends AppCompatActivity {

    ImageView  backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);

        getSupportActionBar().hide();

        backarrow = findViewById(R.id.iv_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
