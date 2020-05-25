package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText first_name, last_name, password, confirm_password, mob_no;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        register_btn = findViewById(R.id.register_btn);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        mob_no = findViewById(R.id.mob_no);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean verification = verify();
                    if (verification) {
                        //insert into database
                        RegisterUser();
                        Toast.makeText(getBaseContext(), "Data validation success", Toast.LENGTH_LONG).show();
                    } else {
                        //   Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }

            private Boolean verify() {
                if (first_name.getText().toString().isEmpty() == true) {

                    first_name.setError("Please enter first name");
                    return false;
                }
                if (last_name.getText().toString().isEmpty() == true) {

                    last_name.setError("Please enter last name");
                    return false;
                }
                if (mob_no.length() < 10) {
                    mob_no.setError("Enter a valid mobile number");
                }
                if (mob_no.getText().toString().isEmpty() == true) {

                    mob_no.setError("Please enter a valid mobile number");
                    return false;
                }
                if (password.getText().toString().isEmpty() == true) {
                    password.setError("Please enter a password");
                    return false;
                }
                if (confirm_password.getText().toString().isEmpty() == true) {
                    confirm_password.setError("Re enter password");
                    return false;
                }
                if (password.getText().toString().equals(confirm_password.getText().toString()) == false) {
                    confirm_password.setError("Passwords not matching");
                    return false;
                }

                return true;
            }
        });

    }

    public void RegisterUser(){

            String url = Globals.URL + "/user_registration.php";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {

                            Toast.makeText(UserRegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            Intent home = new Intent(UserRegistrationActivity.this, LoginActivity.class);
                            startActivity(home);

                        } else {

                            Toast.makeText(UserRegistrationActivity.this, "Registration  Failed", Toast.LENGTH_LONG).show();

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
                    } else if (error instanceof AuthFailureError) {
                    } else if (error instanceof ParseError) {
                    } else if (error instanceof NoConnectionError) {
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(UserRegistrationActivity.this,
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("username", first_name.getText().toString().trim() + last_name.getText().toString().trim());
                    params.put("password", password.getText().toString().trim());
                    params.put("mobno", mob_no.getText().toString().trim());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

}
