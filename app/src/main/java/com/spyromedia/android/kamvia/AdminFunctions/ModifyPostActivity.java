package com.spyromedia.android.kamvia.AdminFunctions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModifyPostActivity extends AppCompatActivity {
    EditText postHead, postContent;
    ProgressDialog progressDialog;
    ImageView postImage, deletePOst;
    private String upload_URL = "http://18.220.53.162/kamvia/api/modifyPost.php";
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    //Pdf request code
    private final int PICK_PDF_REQUEST = 1;
    private int PICK_IMAGE_REQUEST = 2;

    TextView tvPdfurl;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Uri filePath;
    Uri uri;
    String displayName = null, postId, stringImage, pdfUrl;
    private Bitmap bitmap ;
    Boolean pdfpicked = false;
    Boolean imagePicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_post);

        deletePOst = findViewById(R.id.deletePost);

        Button pdfChoose, uploadPost, btnChooseImage;
        pdfChoose = findViewById(R.id.choose_file);
        btnChooseImage = findViewById(R.id.btnPickImage);
        postHead = findViewById(R.id.postheading);
        postContent = findViewById(R.id.postcontent);

        tvPdfurl = findViewById(R.id.tvpdfurl);
        getSupportActionBar().hide();
        requestStoragePermission();

        Intent list = getIntent();
        postHead.setText(list.getStringExtra("heading"));
        postContent.setText(list.getStringExtra("condent"));
        postId = list.getStringExtra("postid");
        stringImage = list.getStringExtra("image");
        pdfUrl = list.getStringExtra("pdfurl");


        postImage = findViewById(R.id.postImage);
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        if (!stringImage.isEmpty()){
            bitmap = StringToBitMap(stringImage);
        }


        postImage.setImageBitmap(bitmap);
        tvPdfurl.setText(pdfUrl);

        uploadPost = findViewById(R.id.buttonuploadpost);
        uploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean verify = verifyPost();
                if (verify == true) {

                    if (!pdfpicked) {
                        modifyPostWithoutPdf();
                    } else {
                        uploadPDF(displayName, uri);
                    }

                }
            }
        });

        pdfChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        deletePOst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPostActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure to delete this post?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        deletePost();

                        // dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    private void deletePost() {

        String url = "http://18.220.53.162/kamvia/api/addnewpost.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Boolean res = jsonObject.getBoolean("error");
                    if (!jsonObject.getBoolean("error")) {

                        Toast.makeText(ModifyPostActivity.this, "Post Deleted Successfully", Toast.LENGTH_LONG).show();
                        // finish();

                        Intent post = new Intent(ModifyPostActivity.this, PostsActivity.class);
                        startActivity(post);
                        finish();

                    } else {

                        Toast.makeText(ModifyPostActivity.this, "Something went wrong. Try Again", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ModifyPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(ModifyPostActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ModifyPostActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("post_id", postId);


                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(ModifyPostActivity.this);
        progressDialog.setMessage("Requesting.....");
        progressDialog.show();
        // TODO: 17-08-2020  create api for deleting post, fetch postid along with datadata for paassing deleting a particular data


    }

    Boolean verifyPost() {

        if (postHead.getText().toString().isEmpty()) {
            postHead.setError("Add a heading");
            return false;
        }

        if (postContent.getText().toString().isEmpty()) {
            postContent.setError("Type the content");
            return false;
        }

//        if(!pdfpicked){
//
//            Toast.makeText(this, "Please Choose Pdf", Toast.LENGTH_SHORT).show();
//            return  false;
//        }

        return true;
    }

    public void modifyPostWithoutPdf() {

        String url = "http://18.220.53.162/kamvia/api/modifyPost.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                   progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Boolean res = jsonObject.getBoolean("error");
                    if (jsonObject.getBoolean("status")) {

                        Toast.makeText(ModifyPostActivity.this, "Post Modified Successfully", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(ModifyPostActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ModifyPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(ModifyPostActivity.this, "Server Error" + error,
                            Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ModifyPostActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
              //  try {


                    params.put("user_id", Globals.currentUser.USER_ID.trim());
                    params.put("heading", postHead.getText().toString().trim());
                    params.put("content", postContent.getText().toString().trim());

                    if(bitmap != null){
                        params.put("image", getStringImage(bitmap));
                    }
                    else{
                        params.put("image", "no image");

                    }
                    params.put("post_id", postId);




//                } catch (Exception ex) {
//                    ex.getMessage();
//                }

                return params;


            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(ModifyPostActivity.this);
        progressDialog.setMessage("Uploading.....");
        progressDialog.show();
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        pdfpicked = true;
                        Log.d("nameeeee>>>>  ", displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ", displayName);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                postImage.setImageBitmap(bitmap);
                imagePicked = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void uploadPDF(final String pdfname, Uri pdffile) {

        InputStream iStream = null;
        try {


            iStream = getContentResolver().openInputStream(pdffile);


            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest =
                    new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                            new Response.Listener<NetworkResponse>() {
                                @Override
                                public void onResponse(NetworkResponse response) {
                                    progressDialog.dismiss();

                                    Log.d("Uploading ....", new String(response.data));
                                    rQueue.getCache().clear();
                                    try {
                                        JSONObject jsonObject = new JSONObject(new String(response.data));
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                        jsonObject.toString().replace("\\\\", "");

                                        if (jsonObject.getString("status").equals("true")) {

//                                    Log.d("come::: >>>  ","yessssss");
//                                    arraylist = new ArrayList<HashMap<String, String>>();
//                                    JSONArray dataArray = jsonObject.getJSONArray("data");
                                            Toast.makeText(ModifyPostActivity.this, jsonObject.getString("message"),
                                                    Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(ModifyPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
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
                            // params.put("tags", "ccccc");  add string parameters
                            params.put("user_id", Globals.currentUser.USER_ID);
                            params.put("heading", postHead.getText().toString());
                            params.put("content", postContent.getText().toString());
                            String uploadImage = getStringImage(bitmap);
                            params.put("image", uploadImage);
                            params.put("post_id", postId);

                            return params;
                        }

                        /*
                         *pass files using below method
                         * */
                        @Override
                        protected Map<String, DataPart> getByteData() {
                            Map<String, DataPart> params = new HashMap<>();
                            params.put("filename", new DataPart(pdfname, inputData));
                            return params;
                        }
                    };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(ModifyPostActivity.this);
            rQueue.add(volleyMultipartRequest);

            progressDialog = new ProgressDialog(ModifyPostActivity.this);
            progressDialog.setMessage("Uploading......");
            progressDialog.setCancelable(false);
            progressDialog.show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}