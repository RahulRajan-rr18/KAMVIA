package com.spyromedia.android.kamvia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mobile_number, password;
    Button btn_login;
    TextView tv_register;
    ProgressDialog progressDialog;
    private static char[] hextable = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        tv_register = findViewById(R.id.NotRegistered);
        mobile_number = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.Login);
        //Check Runtime Permissions
        checkStoragePermission();
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, UserRegistrationActivity.class);
                startActivity(register);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean verification = verify();
                if (verification == true) {
                    UserLogin();
                } else {

                }

            }
        });
        TextView tv_forgotPassword = findViewById(R.id.tv_forgotpassword);
        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpasseord = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotpasseord);
            }
        });

    }

    private boolean checkStoragePermission() {

        Dexter.withContext(LoginActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(LoginActivity.this, "Permission is Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(LoginActivity.this, "Please allow the storage permission in the settings", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
        return true;
    }


    public void UserLogin() {

        String url = "http://18.220.53.162/kamvia/api/user_login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        SharedPreferences preferences = getBaseContext().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = preferences.edit();


                        String uname = jsonObject.getString("user_name");
                        String mobno = jsonObject.getString("mobile_number");
                        String uid = jsonObject.getString("user_id");

                        editor.putString("USER_ID", uid);
                        editor.putString("USER_NAME", uname);
                        editor.putString("MOBILE_NUMBER", mobno);
                        //editor.apply();
                        editor.commit();
                        Globals.currentUser.USER_ID=uid;
                        Globals.currentUser.USER_NAME=uname;
                        Globals.currentUser.MOBILE_NUMBER=mobno;
                        editor.apply();


                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(LoginActivity.this, MainActivity.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(home);
                        finish();

                    } else {

                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Toast.makeText(LoginActivity.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                String hashedPassword = md5(password.getText().toString().trim());
                params.put("username", mobile_number.getText().toString().trim());
                params.put("password",hashedPassword);

                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }

    Boolean verify() {

        if (mobile_number.getText().toString().isEmpty() == true) {
            mobile_number.setError("Enter registered Mobile number");
            return false;
        }

        if (password.getText().toString().isEmpty() == true) {
            password.setError("Please enter password");
            return false;
        }
        return true;
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
