package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class PasswordResetActivity extends AppCompatActivity {

    TextInputEditText password_text, confirm_password_text;
    MaterialButton confirmBtn;
    String password, confirmpassword;
    String mobile_number;
    private static final char[] hextable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        password_text = findViewById(R.id.password_reset_text);
        confirm_password_text = findViewById(R.id.confirm_password_text);
        confirmBtn = findViewById(R.id.password_verify_btn);
        Intent intent = getIntent();
        mobile_number = intent.getStringExtra("mobile_number");
        Log.d("passwordreset", "o" + mobile_number);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verify()) {
                    passwordReset();

                } else {
                    Toast.makeText(PasswordResetActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void passwordReset() {
        String url = "http://18.220.53.162/kamvia/api/ResetPassword.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("PasswordReset", "onResponse: " + jsonObject.getString("message"));
                    if (jsonObject.getString("message").equals("Password changed")) {
                        Toast.makeText(PasswordResetActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PasswordResetActivity.this, LoginActivity.class));
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

                    Toast.makeText(PasswordResetActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(PasswordResetActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String hashedPassword = Globals.md5(password_text.getText().toString().trim());
                Map<String, String> params = new HashMap<>();
                params.put("password", hashedPassword);
                params.put("mobile_number", mobile_number);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private Boolean verify() {
        if (password_text.getText().toString().isEmpty()) {

            password_text.setError("Please enter a password");
            return false;
        }
        if (confirm_password_text.getText().toString().isEmpty()) {
            confirm_password_text.setError("Please enter a password");
            return false;
        }
        if (!password_text.getText().toString().equals(confirm_password_text.getText().toString())) {
            confirm_password_text.setError("Passwords not matching");
            return false;
        }

        return true;
    }


}