package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spyromedia.android.kamvia.AdminFunctions.AddStationsActivity;
import com.spyromedia.android.kamvia.ListAllStations.ListAllStationsActivity;

public class BasicSettingsActivity extends AppCompatActivity {

    Button addNewStation , EditStations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_settings);

        getSupportActionBar().hide();

        addNewStation = findViewById(R.id.addNewStation);
        addNewStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newStation  = new Intent(BasicSettingsActivity.this , AddStationsActivity.class);
                startActivity(newStation);
            }
        });

        EditStations = findViewById(R.id.EditStation);
        EditStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicSettingsActivity.this , ListAllStationsActivity.class);
                startActivity(intent);
            }
        });
    }
}