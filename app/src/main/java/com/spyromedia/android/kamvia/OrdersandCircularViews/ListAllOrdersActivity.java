package com.spyromedia.android.kamvia.OrdersandCircularViews;

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
import com.spyromedia.android.kamvia.ListAllStations.ListAllStationsActivity;
import com.spyromedia.android.kamvia.ListAllStations.ListAllStationsRecyAdapter;
import com.spyromedia.android.kamvia.ListAllStations.ListStationsItem;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListAllOrdersActivity extends AppCompatActivity {


    RecyclerView ordersRecyView;
    List<ListAllOrdersListItem> listAllOrdersListItems;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ListAllOrdersRecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_orders);
        getSupportActionBar().hide();


        ordersRecyView  = findViewById(R.id.recyorders);
        ordersRecyView.setHasFixedSize(true);
        ordersRecyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listAllOrdersListItems = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        parseJSON();
        return;

    }

    private void parseJSON() {

        String url = "http://18.220.53.162/kamvia/api/getAllOrders.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //geting pdf url from server
                        String orderId = jsonObject.getString("url");
                        String orderDetails = jsonObject.getString("name");

                        listAllOrdersListItems.add(new ListAllOrdersListItem(orderId, orderDetails));

                    }

                    adapter = new ListAllOrdersRecyAdapter(listAllOrdersListItems, getApplicationContext());
                    ordersRecyView.setAdapter(adapter);
                    ordersRecyView.smoothScrollToPosition(adapter.getItemCount());


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

        progressDialog = new ProgressDialog(ListAllOrdersActivity.this);
        progressDialog.setMessage("Loading List");
        progressDialog.show();
    }

}