package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStationsActivity extends AppCompatActivity {
    Button btnAddStation;
    EditText stationName , stationLocation , stationNumber , stationCode;
    Spinner stationDistrict;
    ProgressDialog progressDialog;

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
                    addStation();
                }
            }
        });


    }

    public void addStation() {

        String url = "http://18.220.53.162/kamvia/api/addStation.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(AddStationsActivity.this, "Station Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AddStationsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(AddStationsActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AddStationsActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("stationname", stationName.getText().toString().trim());
                params.put("location", stationLocation.getText().toString().trim());
                params.put("code", stationCode.getText().toString().trim());
                params.put("district",stationDistrict.getSelectedItem() .toString().trim());
                params.put("number",stationNumber.getText() .toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(AddStationsActivity.this);
        progressDialog.setMessage("Uploading.....");
        progressDialog.show();
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
                    .make(stationNumber, "Enter Phone number", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  true;
        }

      return true;
    }
}