package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineRecyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllTimelineActivity extends AppCompatActivity {
    RecyclerView RecyView;

    HomeTimelineRecyAdapter homeTimelineRecyAdapter;
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueuegetTimeline;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_timeline);
        RecyView = findViewById(R.id.recyclerview);

        RecyView.setHasFixedSize(true);
        RecyView.setLayoutManager(new LinearLayoutManager(this));
        timelinelist = new ArrayList<>();
        requestQueuegetTimeline = Volley.newRequestQueue(this);

        parseJSON();


    }

    private void parseJSON() {

        String url = "http://18.220.53.162/kamvia/api/timeline.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                timelinelist.clear();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //geting pdf url from server
                        String pdfurl = jsonObject.getString("pdfurl");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        String image = null;
                        image = jsonObject.getString("post_image");
                        String postid = jsonObject.getString("post_id");
                        timelinelist.add(new HomeTimelineListItem(postid,pdfurl, heading, condent, image));

                    }

                    homeTimelineRecyAdapter = new HomeTimelineRecyAdapter(timelinelist, getApplicationContext());
                    RecyView.setAdapter(homeTimelineRecyAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AllTimelineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
            }
        });
        requestQueuegetTimeline.add(jsonObjectRequest);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading all Posts , Please wait");
        progressDialog.show();
    }
}