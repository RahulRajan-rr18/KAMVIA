package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class AddStationsActivities extends AppCompatActivity {
    Button btnAddStation;
    EditText stationName , stationLocation , stationNumber , stationCode;
    Spinner stationDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stations_activities);
        getSupportActionBar().hide();

        stationName = findViewById(R.id.textStationname);
        stationLocation = findViewById(R.id.textStationLocation);
        stationNumber = findViewById(R.id.textStationNumber);
        stationCode = findViewById(R.id.textStationCode);
        stationDistrict = findViewById(R.id.listDistrict);

        btnAddStation = findViewById(R.id.btnUpload);
        btnAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean verify = verifydata();
                if(verify){
                    uploadData();
                }
            }
        });


    }

    private void uploadData() {

    }

    private Boolean verifydata() {
        if(stationName.getText().toString().equals("")){
            Snackbar snackbar = Snackbar
                    .make(stationName, "Please enter station name", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
        }
        if(stationLocation.getText().toString().equals("")){
            Snackbar snackbar = Snackbar
                    .make(stationLocation, "Enter Location", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
        }
        if(stationCode.getText().toString().equals("")){

            Snackbar snackbar = Snackbar
                    .make(stationCode, "Enter Station Code", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
        }
        if(stationDistrict.getSelectedItem().equals("Select District")){

            Snackbar snackbar = Snackbar
                    .make(stationDistrict, "Choose District", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
        }

        if(stationNumber.getText().toString().equals("")){
            Snackbar snackbar = Snackbar
                    .make(stationNumber, "Enter Station Code", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  true;
        }

      return true;
    }
}