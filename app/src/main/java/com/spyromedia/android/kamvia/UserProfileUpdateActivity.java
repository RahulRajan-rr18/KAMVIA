package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.android.volley.AuthFailureError;
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

    public static final String UPLOAD_URL = "http://18.220.53.162/kamvia/api/img.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private int PICK_IMAGE_REQUEST = 1;
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
        pickImageButton = findViewById(R.id.btn_pickImage);
        uploadImageButton = findViewById(R.id.btn_upload_image);
        upload_detailsButton = findViewById(R.id.btn_upload);
        errorDist = findViewById(R.id.error_dist);
        errorRtoDist = findViewById(R.id.error_rto_dist);
        membership_type = findViewById(R.id.id_memtype);

        profile_image = findViewById(R.id.imagepicked);
        upload_detailsButton.setVisibility(View.INVISIBLE);


        pickImageButton.setOnClickListener(this);
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
            }
        });

    }


    private Boolean vefifyDetails() {
        if (name.getText().toString().trim().isEmpty() == true) {
            name.setError("Name reqired");
            return false;
        }
        if (email.getText().toString().trim().isEmpty() == true) {
            email.setError("Email reqired");
            return false;
        }
        if (employee_number.getText().toString().trim().isEmpty() == true) {
            employee_number.setError("PEN reqired");
            return false;
        }
        if (add_line1.getText().toString().trim().isEmpty() == true) {
            add_line1.setError("Reqired");
            return false;
        }
        if (add_line2.getText().toString().trim().isEmpty() == true) {
            add_line2.setError("Reqired");
            return false;
        }

        if (pincode.getText().toString().trim().isEmpty() == true) {
            pincode.setError("Please enter the pincode");
            return false;
        }
        if (home_station_code.getText().toString().trim().isEmpty() == true) {
            home_station_code.setError("Home station code reqired");
            return false;
        }
        if (present_station_code.getText().toString().trim().isEmpty() == true) {
            present_station_code.setError("Present station code reqired");
            return false;
        }
        if (dateofbirth.getText().toString().isEmpty() == true) {
            dateofbirth.setText("Reqired");
            return false;
        }
        if (home_district.getSelectedItem().toString().equals("Choose")) {
            errorDist.setError("Please choose your district");
            return false;
        }

        if (present_rto_district.getSelectedItem().toString().equals("Choose")) {
            errorRtoDist.setError("Please choose current district");
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
        if (v == pickImageButton) {
            showFileChooser();
        }
        if (v == uploadImageButton) {
            uploadImage();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UserProfileUpdateActivity.this, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if(s.equals("Image Uploaded Successfully")){
                    upload_detailsButton.setVisibility(View.VISIBLE);

                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }

        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
}
