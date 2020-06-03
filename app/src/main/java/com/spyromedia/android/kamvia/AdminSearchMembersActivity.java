package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSearchMembersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> itemlistvalues;
    RequestQueue requestQueue;
    AutoCompleteTextView search_field;
    private AdminSearchRecyAdapter adapter;
    private List<AdminSeachResultItem> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_members);
        search_field = findViewById(R.id.searchMembers);
        recyclerView = findViewById(R.id.recyclerview);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ListNames();

        search_field.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String name = search_field.getText().toString();
               fetchDetails(name);
            }
        });

    }

    private void fetchDetails(String name) {

        String url = "http://18.220.53.162/kamvia/api/users.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String location = jsonObject.getString("home_station");
                        String stationcode = jsonObject.getString("home_station_code");
                        String user_id = jsonObject.getString("user_id");

                        resultList.add(new AdminSeachResultItem(user_id, uname, location, stationcode));
                    }

                    adapter = new AdminSearchRecyAdapter(resultList, AdminSearchMembersActivity.this);
                    recyclerView.setAdapter(adapter);

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
                params.put("name","");

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void ListNames() {

        String url = "http://18.220.53.162/kamvia/api/users.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    itemlistvalues = new ArrayList<String>();

                    String name;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        name = jsonArray.getJSONObject(i).getString("name");
                        itemlistvalues.add(name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, itemlistvalues);
                    search_field.setAdapter(adapter);

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
