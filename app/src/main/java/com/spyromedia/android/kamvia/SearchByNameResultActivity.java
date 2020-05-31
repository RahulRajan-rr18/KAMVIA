package com.spyromedia.android.kamvia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchByNameResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchResultRecyItem> resultList;

    private RequestQueue requestQueue;

    //TextView ClassName, SpeakerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name_result);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager ( this));

        resultList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

      //  for (int i = 0; i < 10; i++) {

      //      SearchResultRecyItem searchResultItem = new SearchResultRecyItem("Albert", "Kunnamkulam","KL52");
     //       resultList.add(searchResultItem);
      //  }

    //    adapter = new SearchResultAdapter(resultList, this);

    //    recyclerView.setAdapter(adapter);

    }

    private void parseJSON(){

        String url = "http://18.220.53.162/kamvia/api/user_registration.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String location = jsonObject.getString("home_station");
                        String stationcode = jsonObject.getString("home_station_code");

                        resultList.add(new SearchResultRecyItem(uname,location,stationcode));
                    }
                    adapter = new SearchResultAdapter(resultList,SearchByNameResultActivity.this);
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
        });
        requestQueue.add(jsonObjectRequest);
    }

}
