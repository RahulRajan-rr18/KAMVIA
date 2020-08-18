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

public class ModifyPostListActivity extends AppCompatActivity {
    RecyclerView recyView;
    ModifyPostRecyAdapter adapter;
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueuegetTimeline;
    ProgressDialog progressDialog;
    private static final int VERTICAL_ITEM_SPACE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_list);
        getSupportActionBar().hide();

        recyView = findViewById(R.id.recyclerView);

        recyView.setHasFixedSize(true);
        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        timelinelist = new ArrayList<>();

        requestQueuegetTimeline = Volley.newRequestQueue(this);
        FetchPosts();


    }

    private void FetchPosts() {

        String url = "http://18.220.53.162/kamvia/api/timeline.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                   progressDialog.dismiss();

                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String pdfurl = jsonObject.getString("pdfurl");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        String image = jsonObject.getString("post_image");
                        String post_id = jsonObject.getString("post_id");
                        timelinelist.add(new HomeTimelineListItem(post_id,pdfurl, heading, condent,image));

                    }

                    adapter = new ModifyPostRecyAdapter(timelinelist, getApplicationContext());
                    recyView.setAdapter(adapter);

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Posts");
        progressDialog.show();
    }
}