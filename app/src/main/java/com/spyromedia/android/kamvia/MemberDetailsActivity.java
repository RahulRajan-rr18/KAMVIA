package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MemberDetailsActivity extends AppCompatActivity {
    TextView name, email, home_district, employee_number, mobile_number, dateofjoing_amvi,
            present_rto_dist_and_code, house_name, pincode, home_location;
    ProgressDialog progressDialog;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        getSupportActionBar().hide();

        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        home_district = findViewById(R.id.tv_district);
        employee_number = findViewById(R.id.tv_employeenumber);
        mobile_number = findViewById(R.id.tv_mobilenumber);
        dateofjoing_amvi = findViewById(R.id.tv_joiningdate);
        present_rto_dist_and_code = findViewById(R.id.tv_presentrtodist);
        house_name = findViewById(R.id.tv_housename);
        pincode = findViewById(R.id.tv_pincode);
        home_location = findViewById(R.id.tv_homelocation);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        fetchDetails();

    }

    private void fetchDetails() {

        String url = "http://18.220.53.162/kamvia/api/MemberDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String user_id = jsonObject.getString("user_id");
                        String email = jsonObject.getString("email");
                        Toast.makeText(MemberDetailsActivity.this, uname, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MemberDetailsActivity.this, user_id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MemberDetailsActivity.this, email, Toast.LENGTH_SHORT).show();

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
                params.put("user_id", user_id);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
        progressDialog = new ProgressDialog(MemberDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

}
