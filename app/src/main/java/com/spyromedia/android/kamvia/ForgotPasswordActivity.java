package com.spyromedia.android.kamvia;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
TextView error;
EditText et_Otp , btn_otp , newpassword , confirmPassword , mobile_number ;

Button  btn_reset , btn_otpVerification , send_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

//        getSupportActionBar().hide();
//
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

}
