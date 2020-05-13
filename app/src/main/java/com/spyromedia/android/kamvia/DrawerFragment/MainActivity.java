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

import com.google.android.material.navigation.NavigationView;
import com.spyromedia.android.kamvia.R;
import com.spyromedia.android.kamvia.SearchByLocationFragment;
import com.spyromedia.android.kamvia.SearchByNameFragement;
import com.spyromedia.android.kamvia.UserProfileUpdate;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button userUpdate, btnLogin;
    private DrawerLayout drawer;


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
if(savedInstanceState == null ) {
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    navigationView.setCheckedItem(R.id.id_home);
}
//        userUpdate = findViewById(R.id.id_userupdate);
//        btnLogin = findViewById(R.id.id_login);
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent login = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(login);
//            }
//        });
//        userUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent user = new Intent(MainActivity.this,UserProfileUpdate.class);
//                startActivity(user);
//            }
//        });
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
                Intent userprofileup  =new Intent(MainActivity.this, UserProfileUpdate.class);
                startActivity(userprofileup);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateProfileOptionFragment()).commit();
                 break;
            case R.id.id_changepassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChangePasswordFragment()).commit();
                break;
            case R.id.id_admincorner:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdminCornerFragment()).commit();
                break;
            case R.id.search_name:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchByNameFragement()).commit();

                //Toast.makeText(this, "Search by name", Toast.LENGTH_SHORT).show();
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
}
