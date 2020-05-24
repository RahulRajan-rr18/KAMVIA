package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserProfileUpdateActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    Spinner home_district, present_rto_district,membership_type;

    TextView errorDist,errorRtoDist;
    RadioGroup  member_fee_paid;
    Button photo_upload , upload_details;
    EditText dateofbirth, dateOfJoiningasamvi;
    EditText name, email, employee_number, add_line1, add_line2, pincode, state, home_station_code, present_station_code;

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    final DatePickerDialog.OnDateSetListener dt = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);

        dateofbirth = findViewById(R.id.id_dob);
        dateOfJoiningasamvi = findViewById(R.id.id_dateofjoining);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        employee_number = findViewById(R.id.employee_number);
        add_line1 = findViewById(R.id.add_line1);
        add_line2 = findViewById(R.id.add_line2);
        home_district = findViewById(R.id.id_district);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        home_station_code = findViewById(R.id.home_station_code);
        present_rto_district = findViewById(R.id.id_present_district);
        present_station_code = findViewById(R.id.present_station_code);
        member_fee_paid = findViewById(R.id.rd_group_feepaid);
        photo_upload = findViewById(R.id.btn_uploadphoto);
        upload_details = findViewById(R.id.btn_upload);
        errorDist = findViewById(R.id.error_dist);
        errorRtoDist = findViewById(R.id.error_rto_dist);
        membership_type = findViewById(R.id.id_memtype);

        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfileUpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateOfJoiningasamvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UserProfileUpdateActivity.this, dt, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        upload_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean verify = vefifyDetails();
                if(verify == true){
                    Update_Profile();
                    Toast.makeText(UserProfileUpdateActivity.this, "Validation Success", Toast.LENGTH_SHORT).show();
                    //database code
                }
            }
        });

    }

    private Boolean vefifyDetails() {
        if(email.getText().toString().trim().isEmpty() == true){
            email.setError("Email reqired");
            return  false;
        }
        if(employee_number.getText().toString().trim().isEmpty() == true){
            employee_number.setError("PEN reqired");
            return  false;
        }
        if(add_line1.getText().toString().trim().isEmpty() == true){
            add_line1.setError("Reqired");
            return  false;
        }
        if(add_line2.getText().toString().trim().isEmpty() == true){
            add_line2.setError("Reqired");
            return  false;
        }

        if(pincode.getText().toString().trim().isEmpty() == true){
            pincode.setError("Please enter the pincode");
            return  false;
        }
        if(home_station_code.getText().toString().trim().isEmpty() == true){
            home_station_code.setError("Home station code reqired");
            return  false;
        }
        if(present_station_code.getText().toString().trim().isEmpty() == true){
            present_station_code.setError("Present station code reqired");
            return  false;
        }
        if(dateofbirth.getText().toString().isEmpty()==true){
            dateofbirth.setText("Reqired");
            return  false;
        }
        if(home_district.getSelectedItem().toString().equals("Choose")){
            errorDist.setError("Please choose your district");
            return  false;
        }

        if(present_rto_district.getSelectedItem().toString().equals("Choose")){
            errorRtoDist.setError("Please choose current district");
            return  false;
        }

        return  true;

    }



    //datepicker
    private void updateLabel() {
        String myFormat = "YYYY/MM/DD"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ROOT);

        dateofbirth.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat = "YYYY/MM/DD"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ROOT);
        dateOfJoiningasamvi.setText(sdf.format(myCalendar2.getTime()));

    }

    public void Update_Profile() {

        String url = "http://192.168.43.132/KAMVIA/user_profile_update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(UserProfileUpdateActivity.this, "Profile Updated Successful", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(UserProfileUpdateActivity.this, "Profile Updation Failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserProfileUpdateActivity.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("member_id",Globals.USER_ID);
                params.put("name",name.getText().toString().trim());
                params.put("mobile_no",Globals.MOBILE_NUMBER);
                params.put("employee_no",employee_number.getText().toString().trim());
                params.put("email",email.getText().toString().trim());
                params.put("date_of_birth",dateofbirth.getText().toString().trim());
                params.put("address",add_line1.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("home_station_code",home_station_code.getText().toString().trim());
                params.put("home_station",home_station_code.getText().toString().trim());
                params.put("date_of_joining",dateOfJoiningasamvi.getText().toString().trim());
                params.put("present_station_code",present_station_code.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("present_station",present_station_code.getText().toString().trim());
                params.put("state","Kerala");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
