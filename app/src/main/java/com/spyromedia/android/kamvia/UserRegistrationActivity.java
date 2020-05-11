package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText first_name, last_name, password, confirm_password, mob_no;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        register_btn = (Button) findViewById(R.id.register_btn);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        mob_no = (EditText) findViewById(R.id.mob_no);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean verification = verify();
                    if (verification) {
                        //insert into database
                        Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
                    } else {
                     //   Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }

            private Boolean verify() {
               if(first_name.getText().toString().isEmpty()==true){

                   first_name.setError("Please enter first name");
                   return  false;
               }
               if(last_name.getText().toString().isEmpty()==true){

                   last_name.setError("Please enter last name");
                   return  false;
               }
                if(mob_no.getText().toString().isEmpty() == true){

                    mob_no.setError("Please enter a valid mobile number");
                    return  false;
                }
                if (password.getText().toString().isEmpty()==true){
                    password.setError("Please enter a password");
                    return  false;
                }
                if (confirm_password.getText().toString().isEmpty()==true){
                    confirm_password.setError("Re enter password");
                    return  false;
                }
               if (password.getText().toString().equals(confirm_password.getText().toString())==false ){
                   confirm_password.setError("Passwords not matching");
                   return  false;
               }

                return true;
            }
        });

    }
}
