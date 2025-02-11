package com.spyromedia.android.kamvia.ListAllStations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListAllStationsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ListStationsItem> listStationsItems;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ListAllStationsRecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_stations);
        getSupportActionBar().hide();

       // progressDialog = new ProgressDialog(getApplicationContext());

        recyclerView = findViewById(R.id.stationsRecyView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listStationsItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        parseJSON();
        return;

    }

    private void parseJSON() {

        String url = "http://18.220.53.162/kamvia/api/getAllStations.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //geting pdf url from server
                        String stationName = jsonObject.getString("station_id");
                        String stationId = jsonObject.getString("stationname");

                        listStationsItems.add(new ListStationsItem(stationName, stationId));

                    }

                    adapter = new ListAllStationsRecyAdapter(listStationsItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);

        progressDialog = new ProgressDialog(ListAllStationsActivity.this);
        progressDialog.setMessage("Loading List");
        progressDialog.show();
    }

}