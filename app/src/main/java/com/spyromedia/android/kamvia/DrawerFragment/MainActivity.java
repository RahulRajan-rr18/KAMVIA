package com.spyromedia.android.kamvia.DrawerFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
    Button userUpdate, btnLogin;
    private DrawerLayout drawer;
     String[] user = new String[1];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

               checkCredentials();
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

    public void checkCredentials() {

        String url = "http://18.220.53.162/kamvia/api/userinfo.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");


                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String user_role = jsonObject.getString("user_role");

                    if (user_role.equals("Admin")) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "Only Admin can Access this session", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name", Globals.currentUser.USER_ID);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
