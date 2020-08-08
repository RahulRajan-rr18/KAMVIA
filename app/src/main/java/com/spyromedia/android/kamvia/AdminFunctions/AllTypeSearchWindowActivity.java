package com.spyromedia.android.kamvia.AdminFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.spyromedia.android.kamvia.R;

public class AllTypeSearchWindowActivity extends AppCompatActivity {

    EditText searchBar;
    Spinner SearchType;
    ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_type_search_window);
        getSupportActionBar().hide();

        searchBar = findViewById(R.id.searchBar);
        SearchType = findViewById(R.id.typeSpinner);
        searchButton = findViewById(R.id.searchImage);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean verify = Verify();
                if (verify) {
                 String searchData = searchBar.getText().toString().trim();
                    Intent results = new Intent(AllTypeSearchWindowActivity.this,AllTypeSeachResultsActivity.class );
                    results.putExtra("data",searchData);
                    results.putExtra("searchtype",SearchType.getSelectedItem().toString());
                    startActivity(results);
                }

            }
        });


    }

    Boolean Verify() {
        if (SearchType.getSelectedItem().equals("Choose")) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Please Choose Search Type", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        if (searchBar.getText().toString().equals("")) {
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Type Keyword", Snackbar.LENGTH_LONG);
            snackbar.show();
            searchBar.setError("Type Keyword");
            return false;
        }

        return true;
    }
}