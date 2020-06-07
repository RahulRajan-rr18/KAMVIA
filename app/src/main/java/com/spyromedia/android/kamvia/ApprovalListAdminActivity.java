package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

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
import java.util.List;

public class ApprovalListAdminActivity extends AppCompatActivity {

    private ApprovalListAdapter adapter;
    private List<ApprovalMemberListItem> approvallist;
    RequestQueue requestQueue;
    RecyclerView approval_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_list_admin);

         approval_recyclerView = findViewById(R.id.recyclerviewMemberApproval);
        approval_recyclerView.setHasFixedSize(true);

        approval_recyclerView.setLayoutManager(new LinearLayoutManager( this));

        approvallist = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }

    private void parseJSON() {
        String url = "http://18.220.53.162/kamvia/api/approvallist.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String home_location_code = jsonObject.getString("home_station_code");
                        String home_station = jsonObject.getString("home_station");
                        String member_id = jsonObject.getString("user_id");

                        approvallist.add(new ApprovalMemberListItem(uname,home_station,home_location_code,member_id));
                    }
                    adapter = new ApprovalListAdapter(approvallist,ApprovalListAdminActivity.this);
                    approval_recyclerView.setAdapter(adapter);

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
