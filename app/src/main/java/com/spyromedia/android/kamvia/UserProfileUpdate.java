package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserProfileUpdate extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    EditText birthday,dateOfJoining;
    EditText name,email,phone_no,add_line1,add_line2,pincode,state,home_station,id_dateofjoining,present_station_code;

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    final DatePickerDialog.OnDateSetListener dt = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);
        birthday = findViewById(R.id.id_dob);
        dateOfJoining = findViewById(R.id.id_dateofjoining);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone_no = findViewById(R.id.phone_no);
        add_line1 = findViewById(R.id.add_line1);
        add_line2 = findViewById(R.id.add_line2);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        home_station = findViewById(R.id.home_station);
        id_dateofjoining = findViewById(R.id.id_dateofjoining);
        present_station_code = findViewById(R.id.present_station_code);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfileUpdate.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateOfJoining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfileUpdate.this, dt, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }
//datepicker
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ROOT);

        birthday.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ROOT);
        dateOfJoining.setText(sdf.format(myCalendar2.getTime()));

    }

    public void Update_Profile() {

        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(UserProfileUpdate.this, "Profile Updated Successful", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(UserProfileUpdate.this, "Profile Updation Failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserProfileUpdate.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name", name.getText().toString().trim());
                params.put("email", email.getText().toString().trim());
                params.put("phone_no", phone_no.getText().toString().trim());
                params.put("add_line1", add_line1.getText().toString().trim());
                params.put("add_line2", add_line2.getText().toString().trim());
                params.put("pincode", pincode.getText().toString().trim());
                params.put("state", state.getText().toString().trim());
                params.put("home_station", home_station.getText().toString().trim());
                params.put("present_station_code", present_station_code.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
