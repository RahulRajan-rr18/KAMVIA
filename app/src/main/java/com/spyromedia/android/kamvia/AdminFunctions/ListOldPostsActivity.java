package com.spyromedia.android.kamvia.AdminFunctions;

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
import com.spyromedia.android.kamvia.VerticalSpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOldPostsActivity extends AppCompatActivity {

    HomeTimelineRecyAdapter adapter;
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueuegetTimeline;
    RecyclerView home_recyclerview;
    ProgressDialog progressDialog;
    private static final int VERTICAL_ITEM_SPACE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_old_posts);

        home_recyclerview = findViewById(R.id.oldpostRecyview);
        home_recyclerview.setHasFixedSize(true);
        home_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        home_recyclerview.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        timelinelist = new ArrayList<>();

        requestQueuegetTimeline = Volley.newRequestQueue(this);
        FetchPosts();
    }

    private void FetchPosts() {

        String url = "http://18.220.53.162/kamvia/api/timeline.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

             //   progressDialog.dismiss();

                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String pdfurl = jsonObject.getString("pdfurl");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        timelinelist.add(new HomeTimelineListItem(pdfurl, heading, condent));

                    }

                    adapter = new HomeTimelineRecyAdapter(timelinelist, getApplicationContext());
                    home_recyclerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
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
//        progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.setMessage("Loading......");
//        progressDialog.show();
    }

}