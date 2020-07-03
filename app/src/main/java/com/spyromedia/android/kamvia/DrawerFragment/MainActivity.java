package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
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
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.R;
import com.spyromedia.android.kamvia.SearchByLocationFragment;
import com.spyromedia.android.kamvia.SearchByNameFragement;
import com.spyromedia.android.kamvia.UserProfileUpdateActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ProgressDialog progressDialog;
    String user_id;
    TextView headerText;
    ImageView headerImage;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fetching current verification status for restricting operations
        CheckUserVerificationStatus();
        //Check Runtime Permissions
        user_id = Globals.currentUser.USER_ID;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getBaseContext().getSharedPreferences("settings", 0);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.drawer_name);
        headerImage = headerView.findViewById(R.id.drawer_icon);
        //Apply the data to the drawer header.
        String name = sharedPreferences.getString("USER_NAME", null);
        Log.d("MainActivity", "shared" + name);
        headerText.setText(name);
        try {
            /*fetchImage();*/
            //Glide code
            fetchImageGlide(this);
        } catch (Exception e) {

        }

        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.id_home);
        }

    }

    private void fetchImageGlide(Activity activity) {
        String url = "http://18.220.53.162/kamvia/api/fetch_image.php?id=" + user_id;
        Glide.with(activity)
                .load(url)
                .circleCrop()
                .into(headerImage);

    }

    /*private void fetchImage() {
        String id = "12336";
        class GetImage extends AsyncTask<String, Void, Bitmap> {
            // ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //     loading = ProgressDialog.show(MemberDetailsActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                // Bitmap result = GetBitmapClippedCircle(b);
                if (b != null) {
                   *//* Bitmap result = getCircularBitmap(b);*//*
     *//* headerImage.setImageBitmap(result);*//*
                }
            }


            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = "http://18.220.53.162/kamvia/api/fetch_image.php?id=" + user_id;
                Log.d("DoinBackground", "doInBackground: "+user_id);
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.id_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.id_viewprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewProfileFragment()).commit();
                break;
            case R.id.id_addprofile:
                Intent userprofileup = new Intent(MainActivity.this, UserProfileUpdateActivity.class);
                startActivity(userprofileup);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateProfileOptionFragment()).commit();
                break;
            case R.id.id_changepassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChangePasswordFragment()).commit();
                break;
            case R.id.id_admincorner:
              //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();

                CheckCredentials();
                break;
            case R.id.search_name:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchByNameFragement()).commit();

                break;

            case R.id.search_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchByLocationFragment()).commit();


                //    Toast.makeText(this, "Search by Location", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    public void CheckCredentials() {

        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String 	user_role = jsonObject1.optString("member_type");
                        if(user_role.equals("Admin")){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();
                        }
                        else {

                            Toast.makeText(MainActivity.this, "This is Restricted to Admins Only", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Only Admin can access to this session", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(MainActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id.trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    private void CheckUserVerificationStatus() {
        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String Username = jsonObject1.optString("name");
                        Log.d("MainActivity", Username);
                        sharedPreferences = getBaseContext().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        String user_role = jsonObject1.optString("verification_status");

                        editor.putString("VERIFICATION", user_role);
                        editor.putString("USER_NAME", Username);
                        editor.apply();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sharedPreferences = getBaseContext().getSharedPreferences("settings", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("VERIFICATION", "notverified");
                    editor.apply();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(MainActivity.this, "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(MainActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id.trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

  /*  public static Bitmap getCircularBitmap(Bitmap bitmap) {
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
*/
}
