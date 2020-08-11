package com.spyromedia.android.kamvia.AdminFunctions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
import com.spyromedia.android.kamvia.R;
import com.spyromedia.android.kamvia.SeachFunctions.SearchResultAdapter;
import com.spyromedia.android.kamvia.SeachFunctions.SearchResultRecyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTypeSeachResultsActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView recyView;
    private List<SearchResultRecyItem> resultList;
    private SearchResultAdapter adapter;
    RequestQueue requestQueue;
    String searchdata, searchType;
    TextView Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_type_seach_results);
        recyView = findViewById(R.id.result_recyview);
        Info = findViewById(R.id.infoTextview);

        recyView.setHasFixedSize(true);
        recyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        resultList = new ArrayList<>();

        Intent data = getIntent();
         searchdata = data.getStringExtra("data");
        searchType = data.getStringExtra("searchtype");
        Info.setText(searchType +": "+searchdata);
        FetchDetails();

    }
    public void FetchDetails() {

        String url = "http://18.220.53.162/kamvia/api/universalsearch.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

           //     progressDialog.dismiss();
                if(response.equals("00")){
                    Toast.makeText(getApplicationContext(), "No Members Found", Toast.LENGTH_SHORT).show();
                }
                try {
                    resultList.clear();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String location = jsonObject.getString("home_location");
                        String stationcode = jsonObject.getString("home_station_code");
                        String user_id = jsonObject.getString("user_id");

                        resultList.add(new SearchResultRecyItem(user_id, name, location, stationcode));
                    }

                    adapter = new SearchResultAdapter(resultList, getApplicationContext());
                    recyView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(getApplicationContext(), "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
               // params.put("searchdata", searchdata);
                params.put("searchdata",searchdata);
                return params;
            }
        };
        requestQueue.add(stringRequest);
//        progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();
    }
}