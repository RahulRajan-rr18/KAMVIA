package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimelineViewActivity extends AppCompatActivity {

    ImageView backarrow;
    TextView tv_heading, tv_condent, tv_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_view);
        getSupportActionBar().hide();

        tv_heading = findViewById(R.id.tv_heading);
        tv_condent = findViewById(R.id.tv_condent);

        tv_heading.setText(""+getIntent().getStringExtra("heading"));
        tv_condent.setText(""+getIntent().getStringExtra("condent"));


        backarrow = findViewById(R.id.iv_backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Log.d("TimelineActivity", "onClick: ");
            }
        });
    }
}
