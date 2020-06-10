package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimelineViewActivity extends AppCompatActivity {

    ImageView  backarrow;
    TextView tv_heading , tv_condent,tv_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);
        getSupportActionBar().hide();

        tv_link = findViewById(R.id.tv_link);
        tv_heading = findViewById(R.id.tv_heading);
        tv_condent = findViewById(R.id.tv_condent);

        tv_heading.setText(""+getIntent().getStringExtra("heading"));
        tv_condent.setText(""+getIntent().getStringExtra("condent"));
        tv_link.setText(""+getIntent().getStringExtra(""));


        backarrow = findViewById(R.id.iv_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
