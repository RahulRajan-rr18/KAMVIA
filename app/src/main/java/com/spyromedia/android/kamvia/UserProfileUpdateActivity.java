package com.spyromedia.android.kamvia;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class UserProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    Spinner home_district, present_rto_district, membership_type, PresentStationCode, HomeStationCode , bloodGroup;
    ProgressDialog progressDialog, ImageUploadProgressBar;
    TextView errorDist, errorRtoDist, imageError;
    RadioGroup member_fee_paid;
    RadioButton radioButton;
    ScrollView sv;
    Button upload_detailsButton, uploadImageButton;
    EditText dateofbirth, dateOfJoiningasamvi;
    EditText name, email, employee_number, add_line1, add_line2, pincode, state;
    Boolean ImageUploaded = false;
    public static final String UPLOAD_URL = "http://18.220.53.162/kamvia/api/api.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private final int PICK_IMAGE_REQUEST = 1;
    // private Bitmap bitmap;
    //private Uri filePath;
    ArrayList<String> stationlist = new ArrayList<String>();


    ImageView profile_image;
    RequestQueue requestQueue;


    boolean check = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

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
        home_district = findViewById(R.id.listDistrict);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        HomeStationCode = findViewById(R.id.homeStationCode);
        present_rto_district = findViewById(R.id.id_present_district);
        PresentStationCode = findViewById(R.id.idPreStationCode);
        member_fee_paid = findViewById(R.id.rd_group_feepaid);
        //pickImageButton = findViewById(R.id.btn_pickImage);
        uploadImageButton = findViewById(R.id.btn_upload_image);
        upload_detailsButton = findViewById(R.id.btn_upload);
        errorDist = findViewById(R.id.error_dist);
        errorRtoDist = findViewById(R.id.error_rto_dist);
        membership_type = findViewById(R.id.id_memtype);
        imageError = findViewById(R.id.imageerror);
        sv = findViewById(R.id.scrollView);
        bloodGroup =findViewById(R.id.bloodGroup);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getStationDetails();


        profile_image = findViewById(R.id.imagepicked);
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
                if (verify) {
                    Update_Profile();
                }

            }
        });

    }


    private Boolean vefifyDetails() {
        if (name.getText().toString().trim().isEmpty()) {
            name.setError("Name required");
            Snackbar snackbar = Snackbar
                    .make(name, "Please enter your name", Snackbar.LENGTH_LONG);
            snackbar.show();
            name.requestFocus();
            return false;
        }
        if (email.getText().toString().trim().isEmpty()) {
            email.setError("Email required");
            Snackbar snackbar = Snackbar
                    .make(email, "Please enter your email", Snackbar.LENGTH_LONG);
            email.requestFocus();
            snackbar.show();
            return false;
        }
        if (employee_number.getText().toString().trim().isEmpty()) {
            employee_number.setError("PEN required");
            Snackbar snackbar = Snackbar
                    .make(employee_number, "Please enter your employee number", Snackbar.LENGTH_LONG);
            snackbar.show();
            employee_number.requestFocus();
            return false;
        }
        if (add_line1.getText().toString().trim().isEmpty()) {
            add_line1.setError("Required");
            Snackbar snackbar = Snackbar
                    .make(add_line1, "Please enter address", Snackbar.LENGTH_LONG);
            snackbar.show();
            add_line1.requestFocus();
            return false;
        }
        if (add_line2.getText().toString().trim().isEmpty()) {
            add_line2.setError("Required");
            Snackbar snackbar = Snackbar
                    .make(add_line2, "Please enter your address", Snackbar.LENGTH_LONG);
            snackbar.show();
            add_line2.requestFocus();
            return false;
        }

        if (pincode.getText().toString().trim().isEmpty()) {
            pincode.setError("Please enter the pincode");
            Snackbar snackbar = Snackbar
                    .make(pincode, "Please enter your pincode", Snackbar.LENGTH_LONG);
            snackbar.show();
            pincode.requestFocus();

            return false;
        }
        if (HomeStationCode.getSelectedItem().equals("staion code")) {
           // home_station_code.setError("Home station code required");
            Snackbar snackbar = Snackbar
                    .make(HomeStationCode, "Select you Home Station Code", Snackbar.LENGTH_LONG);
            snackbar.show();
            HomeStationCode.requestFocus();

            return false;
        }
        if (PresentStationCode.getSelectedItem().equals("staion code")) {
           // present_station_code.setError("Present station code required");
            Snackbar snackbar = Snackbar
                    .make(PresentStationCode, "Present Station Code", Snackbar.LENGTH_LONG);
            snackbar.show();
            PresentStationCode.requestFocus();
            return false;
        }
        if (dateofbirth.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(dateofbirth, "Please enter your DOB", Snackbar.LENGTH_LONG);
            snackbar.show();

            return false;
        }
        if (home_district.getSelectedItem().toString().equals("Choose")) {
            Snackbar snackbar = Snackbar
                    .make(home_district, "Please choose your district", Snackbar.LENGTH_LONG);
            snackbar.show();
            home_district.requestFocus();
            return false;
        }

        if (present_rto_district.getSelectedItem().toString().equals("Choose")) {
            Snackbar snackbar = Snackbar
                    .make(present_rto_district, "Please select your present RTO", Snackbar.LENGTH_LONG);
            snackbar.show();
            present_rto_district.requestFocus();
            return false;
        }
        if (dateOfJoiningasamvi.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(dateOfJoiningasamvi, "Date of Joining as AMVI", Snackbar.LENGTH_LONG);
            snackbar.show();
            dateOfJoiningasamvi.requestFocus();
            return false;
        }

        if (!ImageUploaded) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Please select your Image", Snackbar.LENGTH_LONG);
            snackbar.show();
            imageError.setError("Please Upload your Image");
            // imageError.requestFocus();
            sv.scrollTo(5, 10);
            // uploadImageButton.requestFocus();

            return false;
        }

        if (bloodGroup.getSelectedItem().toString().equals("Choose")){
            Snackbar snackbar = Snackbar
                    .make(bloodGroup, "Please Choose your blood group", Snackbar.LENGTH_LONG);
            snackbar.show();
            return  false;
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

        String url = "http://18.220.53.162/kamvia/api/UserProfileUpdate.php";
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

                        try {

                            Toast.makeText(UserProfileUpdateActivity.this, "Profile Updated", Toast.LENGTH_LONG).show();
                            Intent home = new Intent(UserProfileUpdateActivity.this, MainActivity.class);
                            startActivity(home);

                        } catch (Exception ex) {
                            ex.getMessage();

                        }


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
                params.put("address", add_line1.getText().toString().trim());
                params.put("home_location", add_line2.getText().toString().trim());
                params.put("home_station_code", HomeStationCode.getSelectedItem().toString().trim());
                params.put("home_station",HomeStationCode.getSelectedItem().toString());
                params.put("date_of_joining", dateOfJoiningasamvi.getText().toString().trim());
                params.put("present_station", PresentStationCode.getSelectedItem().toString().trim());
                params.put("state", "Kerala");
                params.put("home_pincode", pincode.getText().toString().trim());
                params.put("home_district", home_district.getSelectedItem().toString().trim());
                params.put("fee_paid", radioButton.getText().toString().trim());
                params.put("member_type", membership_type.getSelectedItem().toString().trim());
                params.put("present_rto_district", present_rto_district.getSelectedItem().toString().trim());
                params.put("present_station_code", PresentStationCode.getSelectedItem().toString().trim());
                params.put("bloodgroup",bloodGroup.getSelectedItem().toString().trim());


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
                Bitmap croped = getCircularBitmap(bitmap);

                profile_image.setImageBitmap(croped);
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
                                ImageUploaded = true;

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Profile Image Uploading Failed");
                            Toast.makeText(UserProfileUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private void getStationDetails() {

        String url = "http://18.220.53.162/kamvia/api/getstationdetails.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    stationlist.add("staion code");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String stationcode = jsonObject1.optString("code");


                        stationlist.add(stationcode);
                    }
                    ArrayAdapter<String> spinnerMenu = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stationlist);
                    PresentStationCode.setAdapter(spinnerMenu);
                    HomeStationCode.setAdapter(spinnerMenu);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
