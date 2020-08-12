package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String user_id;
    TextView headerText;
    ImageView headerImage;
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    NavController navController;
    NavHostFragment navHostFragment;
    AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);


        //Setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomnavbar);
        drawer = findViewById(R.id.drawer_layout);
        user_id = Globals.currentUser.USER_ID;

        checkUserverificationStatus();

         navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.drawer_name);
        headerImage = headerView.findViewById(R.id.drawer_icon);

        //navigation ui
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        //navigation up
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawer).build();
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);

        //Drawer layout
        NavigationUI.setupWithNavController(navigationView, navController);

        //Custom Drawer Function
        MenuItem menuItem;
        menuItem = navigationView.getMenu().findItem(R.id.adminCornerFragment);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CheckCredentials();
                return true;
            }
        });


        //Applying the data to the header
        String name = Globals.currentUser.USER_NAME;
        if (name.equals("")) {
            headerText.setText("");
        }
        Log.d("MainActivity", "shared" + name);
        headerText.setText(name);
        try {
            fetchImageGlide(this);
        } catch (Exception e) {
            Log.d("ImageCaching", "ImageCatching Exception");
        }


        String status = Globals.currentUser.VERIFICATION;
        if((status.equals("verified") || status.equals("notverified")) ){
            // Toast.makeText(MainActivity.this, "Not Requested for Membership", Toast.LENGTH_SHORT).show();
            Menu menuNav = navigationView.getMenu();
            MenuItem nav_item2 = menuNav.findItem(R.id.userProfileUpdateActivity);
            nav_item2.setEnabled(false);
        }

    }


    private void fetchImageGlide(Activity activity) {
        String url = "http://18.220.53.162/kamvia/api/uploads/" + user_id + ".png";
        Glide.with(activity)
                .load(url)
                .circleCrop()
                .into(headerImage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);

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

                        String user_role = jsonObject1.optString("user_role");
                        if (user_role.equals("Admin")) {
                            //Transaction for admin corner
                            navController.navigate(R.id.action_admin_fragment);
                            drawer.close();

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


    private void checkUserverificationStatus() {
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
                        if((ver_status.equals("verified") || ver_status.equals("notverified")) ){
                            // Toast.makeText(MainActivity.this, "Not Requested for Membership", Toast.LENGTH_SHORT).show();
                            Menu menuNav = navigationView.getMenu();
                            MenuItem nav_item2 = menuNav.findItem(R.id.userProfileUpdateActivity);
                            nav_item2.setEnabled(false);
                        }

                        editor.putString("VERIFICATION", ver_status);
                        editor.putString("USER_NAME", Username);
                        //editor.apply();
                        editor.commit();
                        Globals.currentUser.VERIFICATION = ver_status;
                        Globals.currentUser.USER_NAME = Username;
                        editor.commit();
                        editor.apply();
                        headerText.setText(Username);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sharedPreferences = getBaseContext().getSharedPreferences("settings", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("VERIFICATION", "notrequested");
                    editor.commit();

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