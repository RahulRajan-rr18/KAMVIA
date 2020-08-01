package com.spyromedia.android.kamvia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApprovalMemberDetailsActivity extends AppCompatActivity {
    Button btn_approve, btn_reject;
    String user_id;
    ProgressDialog progressDialog;

    ImageView user_photo;
    TextView tv_name, tv_employeenumber, tv_mobilenumber, tv_email, tv_date_of_birth, tv_housename,
            tv_home_location, tv_district, tv_pincode, tv_home_rto_code, tv_date_of_joining, tv_current_station_dis_wtcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_approval);
        getSupportActionBar().hide();

        tv_name = findViewById(R.id.textview_name);
        tv_employeenumber = findViewById(R.id.employeenumber);
        tv_mobilenumber = findViewById(R.id.mobilenumber);
        tv_email = findViewById(R.id.email);
        tv_date_of_birth = findViewById(R.id.dateofbirth);
        tv_housename = findViewById(R.id.housename);
        tv_home_location = findViewById(R.id.homelocation);
        tv_district = findViewById(R.id.district);
        tv_pincode = findViewById(R.id.pincode);
        tv_home_rto_code = findViewById(R.id.homestationcode);
        tv_date_of_joining = findViewById(R.id.joiningdate);
        tv_current_station_dis_wtcode = findViewById(R.id.presentrtodist);


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        btn_approve = findViewById(R.id.btn_approve);
        btn_reject = findViewById(R.id.btn_reject);

        FetchDetails();

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveMember();
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectMember();
            }
        });

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

                        String date_of_birth = jsonObject1.optString("date_of_birth");
                        String date_ofjoining = jsonObject1.optString("date_of_joining");


                        tv_name.setText(name);
                        tv_email.setText(email);
                        tv_mobilenumber.setText(mob_no);
                        tv_employeenumber.setText(emp_no);

                        tv_housename.setText(housename);
                        tv_home_location.setText(location);
                        tv_district.setText(district);
                        tv_pincode.setText(pincode);
                        tv_home_rto_code.setText(h_rto_code);
                        tv_date_of_birth.setText(date_of_birth);
                        tv_date_of_joining.setText(date_ofjoining);
                        tv_current_station_dis_wtcode.setText(current_station + "(" + cu_rto_code + ")");


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

                    Toast.makeText(ApprovalMemberDetailsActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ApprovalMemberDetailsActivity.this,
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
        progressDialog = new ProgressDialog(ApprovalMemberDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }


    public void ApproveMember() {

        String url = "http://18.220.53.162/kamvia/api/approve_member.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Member Approved", Toast.LENGTH_LONG).show();
                        finish();

                    } else {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Approval failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(ApprovalMemberDetailsActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ApprovalMemberDetailsActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void RejectMember() {

        String url = "http://18.220.53.162/kamvia/api/reject_member.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Rejected", Toast.LENGTH_LONG).show();
                        finish();

                    } else {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Rejection failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(ApprovalMemberDetailsActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ApprovalMemberDetailsActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", "id");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
