package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import androidx.appcompat.app.AppCompatActivity;

public class ApprovalMemberDetailsActivity extends AppCompatActivity {
     Button btn_approve,btn_reject;
     String  user_id;
     ImageView user_photo;
     TextView name , employeenumber , mobilenumber , email , date_of_birth, housename ,
    home_location , district , pincode , home_rto_code  , date_of_joining , current_station_dis_wtcode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_approval);

        name= findViewById(R.id.tv_name);
        employeenumber= findViewById(R.id.tv_employeenumber);
        mobilenumber= findViewById(R.id.mob_no);
        email = findViewById(R.id.tv_email);
        date_of_birth = findViewById(R.id.tv_dateofbirth);
        housename = findViewById(R.id.tv_housename);
        home_location = findViewById(R.id.tv_homelocation);
        district = findViewById(R.id.tv_district);
        pincode = findViewById(R.id.tv_pincode);
        home_rto_code = findViewById(R.id.tv_homelocation);
        date_of_joining = findViewById(R.id.tv_joiningdate);
        current_station_dis_wtcode = findViewById(R.id.tv_presentrtodist);


        Intent intent = getIntent();
          user_id = intent.getStringExtra("user_id");

        btn_approve = findViewById(R.id.btn_approve);
        btn_reject = findViewById(R.id.btn_reject);

        getSupportActionBar().hide();

        fetchMemberDetails();

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

    public void ApproveMember(){

        String url = "http://18.220.53.162/kamvia/api/approve_member.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Member Approved", Toast.LENGTH_LONG).show();


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

                    Toast.makeText(ApprovalMemberDetailsActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

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
                params.put("user_id",user_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void RejectMember(){

        String url = "http://18.220.53.162/kamvia/api/reject_member.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ApprovalMemberDetailsActivity.this, "Rejected", Toast.LENGTH_LONG).show();


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

                    Toast.makeText(ApprovalMemberDetailsActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

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
                params.put("user_id","id");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void fetchMemberDetails() {

        String url = "http://18.220.53.162/kamvia/api/list_user_details.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String location = jsonObject.getString("home_station");
                        String stationcode = jsonObject.getString("home_station_code");
                        String user_id = jsonObject.getString("user_id");

                        Toast.makeText(ApprovalMemberDetailsActivity.this, uname, Toast.LENGTH_SHORT).show();

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
                params.put("userid", user_id);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

}
