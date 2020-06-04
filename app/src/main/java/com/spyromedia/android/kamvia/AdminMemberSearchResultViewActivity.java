package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminMemberSearchResultViewActivity extends AppCompatActivity {

    Button btn_promoteAdmin;
    TextView name , employeenumber , mobilenumber , email , date_of_birth, housename ,
            home_location , district , pincode , home_rto_code  , date_of_joining , current_station_dis_wtcode ;
   String user_id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_member_search_result_view);


        Intent intent = getIntent();
        intent.getStringExtra(user_id);

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

        fetchMemberDetails();

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
    private void fetchMemberDetails() {

        String url = "http://18.220.53.162/kamvia/api/list_user_details.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();


                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        name.setText(jsonObject.getString("name"));
                        employeenumber.setText(jsonObject.getString("employee_number"));
                        mobilenumber.setText(jsonObject.getString("whatsapp_number"));
                        date_of_birth.setText(jsonObject.getString("date_of_birth"));
                        housename.setText(jsonObject.getString("address"));
                        home_location.setText(jsonObject.getString("home_location"));
                        district.setText(jsonObject.getString("home_district"));
                        pincode.setText(jsonObject.getString("home_pincode"));
                        home_rto_code.setText(jsonObject.getString("home_station_code"));
                        home_rto_code.setText(jsonObject.getString("home_station_code"));
                        date_of_joining.setText(jsonObject.getString("date_of_joining"));
                        current_station_dis_wtcode.setText(jsonObject.getString("present_rto_district")+
                                jsonObject.getString("  present_station_code"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

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
        progressDialog = new ProgressDialog(AdminMemberSearchResultViewActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


    }


    public void promoteAdmin(final String user_id) {
        String url = "http://18.220.53.162/kamvia/api/promote_admin.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(AdminMemberSearchResultViewActivity.this, "Member Promoted as Admin", Toast.LENGTH_LONG).show();


                    } else {

                        Toast.makeText(AdminMemberSearchResultViewActivity.this, "Something Went Wrong ", Toast.LENGTH_LONG).show();

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

                    Toast.makeText(AdminMemberSearchResultViewActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

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
                params.put("user_id",user_id);
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }
}
