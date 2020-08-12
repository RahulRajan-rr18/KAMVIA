package com.spyromedia.android.kamvia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText  password, confirm_password, mob_no,pen;
    Button register_btn;
    ProgressDialog progressDialog;
    private static final char[] hextable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        register_btn = findViewById(R.id.register_btn);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        mob_no = findViewById(R.id.mob_no);
        pen = findViewById(R.id.pen);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean verification = verify();
                    if (verification) {

                            RegisterUser();
                      //Toast.makeText(getBaseContext(), "Data validation success", Toast.LENGTH_LONG).show();
                    } else {
                        //   Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }

            private Boolean verify() {

                if (mob_no.length() < 10 || mob_no.length() > 10 ) {
                    mob_no.setError("Enter a valid mobile number");
                }
                if (mob_no.getText().toString().isEmpty() == true) {
                    mob_no.setError("Please enter a valid mobile number");
                    return false;
                }
                if (pen.getText().toString().isEmpty() == true) {
                    mob_no.setError("Permanent Employee Number");
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

            String url = "http://18.220.53.162/kamvia/api/UserRegistration.php";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {

                            Toast.makeText(UserRegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            Intent home = new Intent(UserRegistrationActivity.this, LoginActivity.class);
                            startActivity(home);
                            finish();

                        }
                        else if(jsonObject.getString("message").equals("User already registered"))
                        {
                            Toast.makeText(UserRegistrationActivity.this, "User Already Registered", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            Toast.makeText(UserRegistrationActivity.this, "Registration  Failed. Try Again", Toast.LENGTH_LONG).show();

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

                        Toast.makeText(UserRegistrationActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

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

                    String hashedPassword = md5(password.getText().toString().trim());

                    params.put("password", hashedPassword);
                    params.put("mobile_number", mob_no.getText().toString().trim());
                    params.put("empno",pen.getText().toString().trim());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(UserRegistrationActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        }

    private static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            byte[] bytes = digest.digest();

            String hash = "";
            for (int i = 0; i < bytes.length; ++i)
            {
                int di = (bytes[i] + 256) & 0xFF;
                hash = hash + hextable[(di >> 4) & 0xF] + hextable[di & 0xF];
            }

            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
        }

        return "";
    }




}
