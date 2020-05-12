package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText first_name,last_name,password,confirm_password,mob_no;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirm_password);
        mob_no = (EditText)findViewById(R.id.mob_no);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
