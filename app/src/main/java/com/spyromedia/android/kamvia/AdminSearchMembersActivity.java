package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSearchMembersActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<String> itemlistvalues;
    RequestQueue requestQueue;
    AutoCompleteTextView search_field;
    private AdminSearchRecyAdapter adapter;
    private List<AdminSeachResultItem> resultList;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_members);
        search_field = findViewById(R.id.searchMembers);
        recyclerView = findViewById(R.id.recyclerview);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ListNames();

        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        resultList = new ArrayList<>();


        search_field.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               name = search_field.getText().toString();
               FetchDetails();
            }
        });

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

    public void FetchDetails(){

        String url = "http://18.220.53.162/kamvia/api/NameLike.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    resultList.clear();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String location = jsonObject.getString("home_station");
                        String stationcode = jsonObject.getString("home_station_code");
                        String  user_id = jsonObject.getString("user_id");

                        resultList.add(new AdminSeachResultItem(user_id, name, location, stationcode));

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
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(AdminSearchMembersActivity.this, "Server Error"+error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(AdminSearchMembersActivity.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name", name.trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(AdminSearchMembersActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

    }


}
