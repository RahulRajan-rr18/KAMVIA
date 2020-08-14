package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
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

public class ForgotPasswordActivity extends AppCompatActivity {



MaterialButton sendOTP;
TextInputEditText mobile_number;
String mobile_number_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        sendOTP = findViewById(R.id.otpverify_btn);
        mobile_number = findViewById(R.id.mobilenumber_text);
       mobile_number_text =  mobile_number.getText().toString();
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VerifyNumber();;

                finish();
            }
        });


    }


        public void VerifyNumber()
    {

        String url = "http://18.220.53.162/kamvia/api/CheckNumber.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (!jsonObject.getBoolean("error")) {

                        //Intent to next activity
                        Intent intent = new Intent(ForgotPasswordActivity.this,PasswordResetActivity.class);
                        intent.putExtra("mobile_number",mobile_number_text);
                        startActivity(intent);



                    } else {
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
                params.put("mobile_number",mobile_number.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        }

















//        mobile_number = findViewById(R.id.mobile_number);
//        error = findViewById(R.id.tv_numbernotfound);
//        et_Otp = findViewById(R.id.et_otp);
//        btn_otpVerification = findViewById(R.id.btn_otpverify);
//        btn_otp = findViewById(R.id.et_otp);
//        newpassword = findViewById(R.id.newpasswrod);
//        btn_reset = findViewById(R.id.btn_reset);
//        btn_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Boolean verification = true;
//                    if (verification) {
//                        VerifyNumber();
//
//                    } else {
//
//                    }
//                } catch (Exception ex) {
//                    System.err.println(ex.getMessage());
//                }
//            }
//        });
//
//    }
//
//    private Boolean verify() {
////        if (newpassword.getText().toString().isEmpty() == true) {
////
////            newpassword.setError("Please enter a password");
////            return false;
////        }
////        if (confirmPassword.getText().toString().isEmpty() == true) {
////
////            confirmPassword.setError("Please enter a password");
////            return false;
////        }
////
////        if (newpassword.getText().toString().equals(confirmPassword.getText().toString()) == false) {
////            confirmPassword.setError("Passwords not matching");
////            return false;
////        }
//
//        return true;
//    }
//
//    public void ResetPassword(){
//
//        String url = "http://18.220.53.162/kamvia/api/ResetPassword.php";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                if (error instanceof NetworkError) {
//                } else if (error instanceof ServerError) {
//
//                    Toast.makeText(ForgotPasswordActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();
//
//                } else if (error instanceof AuthFailureError) {
//                } else if (error instanceof ParseError) {
//                } else if (error instanceof NoConnectionError) {
//                } else if (error instanceof TimeoutError) {
//                    Toast.makeText(ForgotPasswordActivity.this,
//                            "Oops. Timeout error!",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("password", newpassword.getText().toString().trim());
//                params.put("mobile_number",mobile_number.getText().toString().trim());
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//
//    public void VerifyNumber(){
//
//        String url = "http://18.220.53.162/kamvia/api/CheckNumber.php";
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                    if (!jsonObject.getBoolean("error")) {
//
//                        ResetPassword();
//
//                    } else {
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                if (error instanceof NetworkError) {
//                } else if (error instanceof ServerError) {
//
//                    Toast.makeText(ForgotPasswordActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();
//
//                } else if (error instanceof AuthFailureError) {
//                } else if (error instanceof ParseError) {
//                } else if (error instanceof NoConnectionError) {
//                } else if (error instanceof TimeoutError) {
//                    Toast.makeText(ForgotPasswordActivity.this,
//                            "Oops. Timeout error!",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("mobile_number",mobile_number.getText().toString().trim());
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
    }


