package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPasswordActivity extends AppCompatActivity {
TextView error;
EditText et_Otp , btn_otp , newpassword , confirmPassword ;

Button  btn_reset , btn_otpVerification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        error = findViewById(R.id.tv_numbernotfound);
        error.setVisibility(View.INVISIBLE);

        et_Otp = findViewById(R.id.et_otp);
        et_Otp.setVisibility(View.INVISIBLE);

        btn_otpVerification = findViewById(R.id.btn_otpverify);
        btn_otpVerification.setVisibility(View.INVISIBLE);

        btn_otp = findViewById(R.id.et_otp);
        btn_otp.setVisibility(View.INVISIBLE);

        newpassword = findViewById(R.id.newpasswrod);
        confirmPassword = findViewById(R.id.confirmpassword);

        newpassword.setVisibility(View.INVISIBLE);
        confirmPassword.setVisibility(View.INVISIBLE);

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setVisibility(View.INVISIBLE);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean verification = verify();
                    if (verification) {
                        ResetPassword();
                        //Toast.makeText(getBaseContext(), "Data validation success", Toast.LENGTH_LONG).show();
                    } else {
                        //   Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

    }

    private Boolean verify() {
        if (newpassword.getText().toString().isEmpty() == true) {

            newpassword.setError("Please enter a password");
            return false;
        }
        if (confirmPassword.getText().toString().isEmpty() == true) {

            confirmPassword.setError("Please enter a password");
            return false;
        }

        if (newpassword.getText().toString().equals(confirmPassword.getText().toString()) == false) {
            confirmPassword.setError("Passwords not matching");
            return false;
        }

        return true;
    }

    public void ResetPassword(){

        String url = "http://18.220.53.162/kamvia/api/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ForgotPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_LONG).show();


                    } else {

                        Toast.makeText(ForgotPasswordActivity.this, "Password Reset  Failed", Toast.LENGTH_LONG).show();

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

                    Toast.makeText(ForgotPasswordActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("password", newpassword.getText().toString().trim());
                params.put("user_id",Globals.USER_ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
