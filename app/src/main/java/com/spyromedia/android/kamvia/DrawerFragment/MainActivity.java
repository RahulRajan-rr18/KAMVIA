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
    String currentFragment;


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
        // sharedPreferences = getBaseContext().getSharedPreferences("settings", 0);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.drawer_name);
        headerImage = headerView.findViewById(R.id.drawer_icon);
        //Apply the data to the drawer header.
        String name = Globals.currentUser.USER_NAME;
        if (name.equals(null)) {
            headerText.setText("");
        }
        Log.d("MainActivity", "shared" + name);
        headerText.setText(name);
        try {
            fetchImageGlide(this);
        } catch (Exception e) {
            Log.d("ImageCaching", "ImageCatching Exception");
        }

        viewProfileFragment();
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

    private void viewProfileFragment() {
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewProfileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void fetchImageGlide(Activity activity) {
        String url = "http://18.220.53.162/kamvia/api/uploads/" + user_id + ".png";
        Glide.with(activity)
                .load(url)
                .circleCrop()
                .into(headerImage);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.id_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
                break;
//            case R.id.id_viewprofile:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewProfileFragment()).addToBackStack(null).commit();
//                break;
            case R.id.id_addprofile:
                Intent userprofileup = new Intent(MainActivity.this, UserProfileUpdateActivity.class);
                startActivity(userprofileup);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateProfileOptionFragment()).commit();
                break;
            case R.id.id_changepassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChangePasswordFragment()).addToBackStack(null).commit();
                break;
            case R.id.id_admincorner:
                //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();

                CheckCredentials();
                break;
            case R.id.search_name:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchByNameFragement()).addToBackStack(null).commit();

                break;

            case R.id.search_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchByLocationFragment()).addToBackStack(null).commit();


                //    Toast.makeText(this, "Search by Location", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

        @Override
    public void onBackPressed() {
//        FragmentManager fragmentManager = getFragmentManager();
//        int backCount = fragmentManager.getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

        public void CheckCredentials () {

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

                            String user_role = jsonObject1.optString("user_role");
                            if (user_role.equals("Admin")) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();
                            } else {

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

        private void CheckUserVerificationStatus () {
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

                            String ver_status = jsonObject1.optString("verification_status");

                            editor.putString("VERIFICATION", ver_status);
                            editor.putString("USER_NAME", Username);
                            editor.commit();
                            Globals.currentUser.VERIFICATION = ver_status;
                            Globals.currentUser.USER_NAME = Username;


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


    }
