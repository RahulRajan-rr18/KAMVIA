package com.spyromedia.android.kamvia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminMemberSearchResultViewActivity extends AppCompatActivity {

    Button btn_promoteAdmin;
    TextView tv_name, tv_employeenumber, tv_mobilenumber, tv_email, tv_date_of_birth, tv_housename,
            tv_home_location, tv_district, tv_pincode, tv_home_rto_code, tv_date_of_joining, tv_current_station_dis_wtcode;
    String user_id;
    ProgressDialog progressDialog;
    Button btn_removeMember;
    ImageView profilePhoto;
    String getUser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_member_search_result_view);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        btn_removeMember = findViewById(R.id.btn_remove_member);

        tv_name = findViewById(R.id.tv_name);
        tv_employeenumber = findViewById(R.id.tv_employeenumber);
        tv_mobilenumber = findViewById(R.id.tv_mobilenumber);
        tv_email = findViewById(R.id.tv_email);
        tv_date_of_birth = findViewById(R.id.tv_dateofbirth);
        tv_housename = findViewById(R.id.tv_housename);
        tv_home_location = findViewById(R.id.tv_homelocation);
        tv_district = findViewById(R.id.tv_district);
        tv_pincode = findViewById(R.id.tv_pincode);
        tv_home_rto_code = findViewById(R.id.tv_homestationcode);
        tv_date_of_joining = findViewById(R.id.tv_joiningdate);
        tv_current_station_dis_wtcode = findViewById(R.id.tv_presentrtodist);
        profilePhoto = findViewById(R.id.imageview_userimage);


        FetchDetails();
        FetchImage(this);

        btn_removeMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMemberSearchResultViewActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Are you sure to forward ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        DeleteMember(user_id);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        btn_promoteAdmin = findViewById(R.id.btn_promoteasadmin);
        btn_promoteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMemberSearchResultViewActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Are you sure to forward ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        promoteAdmin(user_id);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
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
                        String dob = jsonObject1.optString("date_of_birth");
                        String housename = jsonObject1.optString("address");
                        String location = jsonObject1.optString("home_location");
                        String district = jsonObject1.optString("home_district");
                        String pincode = jsonObject1.optString("home_pincode");
                        String h_rto_code = jsonObject1.optString("home_station_code");
                        String home_station = jsonObject1.optString("home_station");
                        String current_station = jsonObject1.optString("present_rto_district");
                        String cu_rto_code = jsonObject1.optString("present_station_code");
                        String date_of_joining = jsonObject1.optString("date_of_joining");

                        tv_name.setText(name);
                        tv_mobilenumber.setText(mob_no);
                        tv_employeenumber.setText(emp_no);
                        tv_email.setText(email);
                        tv_date_of_birth.setText(dob);
                        tv_housename.setText(housename);
                        tv_home_location.setText(location);
                        tv_district.setText(district);
                        tv_pincode.setText(pincode);
                        tv_home_rto_code.setText(h_rto_code);
                        tv_date_of_joining.setText(date_of_joining);
                        tv_current_station_dis_wtcode.setText(current_station + "  " + cu_rto_code);

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

                    Toast.makeText(AdminMemberSearchResultViewActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AdminMemberSearchResultViewActivity.this,
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
        progressDialog = new ProgressDialog(AdminMemberSearchResultViewActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void promoteAdmin(final String user_id) {
        String url = "http://18.220.53.162/kamvia/api/PromoteAsAdmin.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");

                    if (jsonObject.getString("message").equals("Promoted as admin")) {
                        finish();
                    }
                    Toast.makeText(AdminMemberSearchResultViewActivity.this, message, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(AdminMemberSearchResultViewActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AdminMemberSearchResultViewActivity.this,
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

    }

    public void DeleteMember(final String user_id) {
        String url = "http://18.220.53.162/kamvia/api/DeleteMember.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");

                    Toast.makeText(AdminMemberSearchResultViewActivity.this, message, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(AdminMemberSearchResultViewActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AdminMemberSearchResultViewActivity.this,
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

    }


    private void FetchImage(Activity activity) {
        String url = "http://18.220.53.162/kamvia/api/uploads/" + user_id + ".png";
        Glide.with(activity)
                .load(url)
                .circleCrop()
                .into(profilePhoto);
    }

}



