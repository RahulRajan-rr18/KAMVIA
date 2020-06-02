package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btn_login;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        tv_register = findViewById(R.id.NotRegistered);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.Login);

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


    public void UserLogin() {

        String url = "http://18.220.53.162/kamvia/api/user_login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        // Globals.USER_NAME = jsonObject.getString("user_name");
                        // Globals.MOBILE_NUMBER = jsonObject.getString("mobile_number");
                        // Globals.USER_ID = jsonObject.getString("user_id");

                        SharedPreferences preferences = getBaseContext().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = preferences.edit();


                        String uname = jsonObject.getString("user_name");
                        String mobno = jsonObject.getString("mobile_number");
                        String uid = jsonObject.getString("user_id");

                        editor.putString("USER_ID", uid);
                        editor.putString("USER_NAME", uname);
                        editor.putString("MOBILE_NUMBER", mobno);
                        editor.apply();



                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(home);

                    } else {

                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    Boolean verify() {

        if (username.getText().toString().isEmpty() == true) {
            username.setError("Enter registered Mobile number");
            return false;
        }

        if (password.getText().toString().isEmpty() == true) {
            password.setError("Please enter password");
            return false;
        }
        return true;
    }
}
