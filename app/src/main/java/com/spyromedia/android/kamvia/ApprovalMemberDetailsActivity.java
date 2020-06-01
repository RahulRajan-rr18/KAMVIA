package com.spyromedia.android.kamvia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class ApprovalMemberDetailsActivity extends AppCompatActivity {
     TextView name , email , home_district;
     Button btn_approve,btn_reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_approval);

        btn_approve = findViewById(R.id.btn_approve);
        btn_reject = findViewById(R.id.btn_reject);

        getSupportActionBar().hide();

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
                params.put("user_id","id");
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

}
