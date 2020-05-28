package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {
TextView error;
EditText et_Otp , btn_otp , new_password , confirmPassword ;

Button  btn_reset , btn_otpVerification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        error = findViewById(R.id.tv_numbernotfound);
        error.setVisibility(View.INVISIBLE);

        et_Otp = findViewById(R.id.et_otp);
        et_Otp.setVisibility(View.INVISIBLE);

        btn_otpVerification = findViewById(R.id.btn_otpverify);
        btn_otpVerification.setVisibility(View.INVISIBLE);

        btn_otp = findViewById(R.id.et_otp);
        btn_otp.setVisibility(View.INVISIBLE);

        new_password = findViewById(R.id.newpasswrod);
        confirmPassword = findViewById(R.id.confirmpassword);

        new_password.setVisibility(View.INVISIBLE);
        confirmPassword.setVisibility(View.INVISIBLE);

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setVisibility(View.INVISIBLE);





    }
}
