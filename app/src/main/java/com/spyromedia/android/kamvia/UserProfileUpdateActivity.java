package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.DrawerFragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserProfileUpdateActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    Spinner home_district, present_rto_district,membership_type;
    ProgressDialog progressDialog , ImageUploadProgressBar;
    TextView errorDist,errorRtoDist;
    RadioGroup  member_fee_paid;
    RadioButton radioButton;
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

        getSupportActionBar().hide();


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

        //upload_details.setAlpha(.5f);
      //  upload_details.setClickable(false);
        upload_details.setVisibility(View.INVISIBLE);



        photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }


        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            // startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
//            finish();
//            startActivity(intent);
//            return;
//        }

        int selectedId = member_fee_paid.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

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
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {

        String URL = "http://18.220.53.162/kamvia/api/image_upload.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        ImageUploadProgressBar.dismiss();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            //upload_details.setAlpha(.1f);
                          // upload_details.setClickable(true);
                            upload_details.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ImageUploadProgressBar.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        ImageUploadProgressBar = new ProgressDialog(UserProfileUpdateActivity.this);
        ImageUploadProgressBar.setMessage("Uploading Image......");
        ImageUploadProgressBar.setCancelable(false);
        ImageUploadProgressBar.show();

    }


    private Boolean vefifyDetails() {
        if(name.getText().toString().trim().isEmpty() == true){
            name.setError("Name reqired");
            return  false;
        }
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
        String myFormat = "YYYY/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateofbirth.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel2() {
        String myFormat = "YYYY/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ROOT);
        dateOfJoiningasamvi.setText(sdf.format(myCalendar2.getTime()));

    }
    public void Update_Profile() {

        String url = "http://18.220.53.162/kamvia/api/UserProleUpdate.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    Toast.makeText(UserProfileUpdateActivity.this, message, Toast.LENGTH_LONG).show();

                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(UserProfileUpdateActivity.this, "Profile Updated", Toast.LENGTH_LONG).show();
                        Intent home = new Intent(UserProfileUpdateActivity.this , HomeFragment.class);
                        startActivity(home);
                        finish();

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

                progressDialog.dismiss();

                Toast.makeText(UserProfileUpdateActivity.this, "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id",Globals.currentUser.USER_ID);
                params.put("name",name.getText().toString().trim());
                params.put("employee_number",employee_number.getText().toString().trim());
                params.put("email",email.getText().toString().trim());
                params.put("mobile_number",Globals.currentUser.MOBILE_NUMBER);
                params.put("date_of_birth",dateofbirth.getText().toString().trim());
                params.put("address",add_line1.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("home_station_code",home_station_code.getText().toString().trim());
                params.put("home_station",home_station_code.getText().toString().trim());
                params.put("date_of_joining",dateOfJoiningasamvi.getText().toString().trim());
                params.put("present_station_code",present_station_code.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("present_station",present_station_code.getText().toString().trim());
                params.put("state","Kerala");
                params.put("home_pincode",pincode.getText().toString().trim());
                params.put("home_district",home_district.getSelectedItem().toString().trim());
                params.put("fee_paid",radioButton.getText().toString().trim());
                params.put("member_type",membership_type.getSelectedItem().toString().trim());
                params.put("present_rto_district",present_rto_district.getSelectedItem().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(UserProfileUpdateActivity.this);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }

}
