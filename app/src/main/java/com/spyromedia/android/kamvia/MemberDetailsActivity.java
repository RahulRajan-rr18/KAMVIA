package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MemberDetailsActivity extends AppCompatActivity {
    TextView tv_name, tv_email, tv_home_district, tv_employee_number, tv_mobile_number,
            tv_present_rto_dist_and_code, tv_house_name, tv_pincode, tv_home_location, tv_homeStationCode,tv_dateOfJoing;
    ProgressDialog progressDialog;
    String user_id;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        getSupportActionBar().hide();

        tv_name = findViewById(R.id.name_tv);
        tv_email = findViewById(R.id.email_tv);
        tv_mobile_number = findViewById(R.id.mobilenumber_tv);
        tv_employee_number = findViewById(R.id.empno_tv);

        tv_house_name = findViewById(R.id.housename_tv);
        tv_home_location = findViewById(R.id.homelocation_tv);
        tv_home_district = findViewById(R.id.district_tv);
        tv_pincode = findViewById(R.id.pincode_tv);
        tv_dateOfJoing = findViewById(R.id.joiningdate_tv);
        tv_homeStationCode = findViewById(R.id.homestationcode_tv);
        tv_present_rto_dist_and_code = findViewById(R.id.presentrtodist_tv);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        FetchDetails();

    }


    public void FetchDetails() {

        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String name = jsonObject1.optString("name");
                        String email = jsonObject1.optString("email");
                        String emp_no = jsonObject1.optString("employee_number");
                        String mob_no = jsonObject1.optString("whatsapp_number");
                        String housename = jsonObject1.optString("address");
                        String location = jsonObject1.optString("home_location");
                        String district = jsonObject1.optString("home_district");
                        String pincode = jsonObject1.optString("home_pincode");
                        String h_rto_code = jsonObject1.optString("home_station_code");
                        String current_station = jsonObject1.optString("present_rto_district");
                        String cu_rto_code = jsonObject1.optString("present_station_code");



                        tv_name.setText(name);
                        tv_email.setText(email);
                        tv_mobile_number.setText(mob_no);
                        tv_employee_number.setText(emp_no);
                        tv_house_name.setText(housename);
                        tv_home_location.setText(location);
                        tv_home_district.setText(district);
                        tv_pincode.setText(pincode);
                        tv_homeStationCode.setText(h_rto_code);
                        tv_present_rto_dist_and_code.setText(current_station + "(" + cu_rto_code +")");


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(MemberDetailsActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(MemberDetailsActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id.trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(MemberDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    private void MemberDetails() {

        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String name = jsonArray.getJSONObject(i).getString("name");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id.trim());
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
