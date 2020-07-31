package com.spyromedia.android.kamvia;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
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

public class UserProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    Spinner home_district, present_rto_district, membership_type;
    ProgressDialog progressDialog, ImageUploadProgressBar;
    TextView errorDist, errorRtoDist;
    RadioGroup member_fee_paid;
    RadioButton radioButton;
    Button pickImageButton, upload_detailsButton, uploadImageButton;
    EditText dateofbirth, dateOfJoiningasamvi;
    EditText name, email, employee_number, add_line1, add_line2, pincode, state, home_station_code, present_station_code;

    public static final String UPLOAD_URL = "http://18.220.53.162/kamvia/api/api.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;

    ImageView profile_image;


    boolean check = true;

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
        //pickImageButton = findViewById(R.id.btn_pickImage);
        uploadImageButton = findViewById(R.id.btn_upload_image);
        upload_detailsButton = findViewById(R.id.btn_upload);
        errorDist = findViewById(R.id.error_dist);
        errorRtoDist = findViewById(R.id.error_rto_dist);
        membership_type = findViewById(R.id.id_memtype);

        profile_image = findViewById(R.id.imagepicked);
        upload_detailsButton.setVisibility(View.INVISIBLE);
        //checking storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }


        uploadImageButton.setOnClickListener(this);


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


        upload_detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean verify = vefifyDetails();
                if (verify == true) {
                    Update_Profile();
                }
//                if(!Globals.isOnline(getApplicationContext())){
//
//
//                }
//                else{
//                    Toast.makeText(UserProfileUpdateActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }


    private Boolean vefifyDetails() {
        if (name.getText().toString().trim().isEmpty() == true) {
            name.setError("Name required");
            return false;
        }
        if (email.getText().toString().trim().isEmpty() == true) {
            email.setError("Email required");
            return false;
        }
        if (employee_number.getText().toString().trim().isEmpty() == true) {
            employee_number.setError("PEN required");
            return false;
        }
        if (add_line1.getText().toString().trim().isEmpty() == true) {
            add_line1.setError("Required");
            return false;
        }
        if (add_line2.getText().toString().trim().isEmpty() == true) {
            add_line2.setError("Required");
            return false;
        }

        if (pincode.getText().toString().trim().isEmpty() == true) {
            pincode.setError("Please enter the pincode");
            return false;
        }
        if (home_station_code.getText().toString().trim().isEmpty() == true) {
            home_station_code.setError("Home station code required");
            return false;
        }
        if (present_station_code.getText().toString().trim().isEmpty() == true) {
            present_station_code.setError("Present station code required");
            return false;
        }
        if (dateofbirth.getText().toString().isEmpty() == true) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Please enter your DOB", Snackbar.LENGTH_LONG);
            snackbar.show();

            return false;
        }
        if (home_district.getSelectedItem().toString().equals("Choose")) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Please choose your district", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        if (present_rto_district.getSelectedItem().toString().equals("Choose")) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Please select your present RTO", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }

        return true;

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
                        Intent home = new Intent(UserProfileUpdateActivity.this, HomeFragment.class);
                        startActivity(home);
                        finish();

                    } else {

                        Toast.makeText(UserProfileUpdateActivity.this, "Profile Updation Failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: Updation Failed");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", Globals.currentUser.USER_ID);
                params.put("name", name.getText().toString().trim());
                params.put("employee_number", employee_number.getText().toString().trim());
                params.put("email", email.getText().toString().trim());
                params.put("mobile_number", Globals.currentUser.MOBILE_NUMBER);
                params.put("date_of_birth", dateofbirth.getText().toString().trim());
                params.put("address", add_line1.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("home_station_code", home_station_code.getText().toString().trim());
                params.put("home_station", home_station_code.getText().toString().trim());
                params.put("date_of_joining", dateOfJoiningasamvi.getText().toString().trim());
                params.put("present_station_code", present_station_code.getText().toString().trim() + add_line2.getText().toString().trim());
                params.put("present_station", present_station_code.getText().toString().trim());
                params.put("state", "Kerala");
                params.put("home_pincode", pincode.getText().toString().trim());
                params.put("home_district", home_district.getSelectedItem().toString().trim());
                params.put("fee_paid", radioButton.getText().toString().trim());
                params.put("member_type", membership_type.getSelectedItem().toString().trim());
                params.put("present_rto_district", present_rto_district.getSelectedItem().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(UserProfileUpdateActivity.this);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v == uploadImageButton) {
            //showFileChooser();

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 100);
        }
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
                //displaying selected image to imageview
                profile_image.setImageBitmap(bitmap);
                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
       // final String tags = "2222222";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, EndPoints.UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            if (obj.get("message").equals("File uploaded successfully")) {
                                upload_detailsButton.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Profile Image Uploading Failed");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", Globals.currentUser.USER_ID.trim());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                //long imagename = System.currentTimeMillis();
                String imagename = Globals.currentUser.USER_ID;
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        progressDialog = new ProgressDialog(UserProfileUpdateActivity.this);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
