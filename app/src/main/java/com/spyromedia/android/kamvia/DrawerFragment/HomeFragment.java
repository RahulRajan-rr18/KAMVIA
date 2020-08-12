package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.OrdersandCircularViews.ListAllOrdersListItem;
import com.spyromedia.android.kamvia.OrdersandCircularViews.ListAllOrdersRecyAdapter;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    HomeTimelineRecyAdapter homeTimelineRecyAdapter;
    ListAllOrdersRecyAdapter listAllOrdersRecyAdapter;
    List<HomeTimelineListItem> timelinelist;
    List<ListAllOrdersListItem> orderlist;
    RequestQueue requestQueuegetTimeline, requestQueuegetQueue;
    RecyclerView news_recyclerView;
    RecyclerView orders_recyclerView;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_frame, container, false);

        news_recyclerView = view.findViewById(R.id.newsrecyclerview);
        news_recyclerView.setHasFixedSize(true);
        news_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orders_recyclerView = view.findViewById(R.id.ordersrecyclerview);
        orders_recyclerView.setHasFixedSize(true);
        news_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderlist = new ArrayList<>();
        timelinelist = new ArrayList<>();

        requestQueuegetTimeline = Volley.newRequestQueue(getContext());
        requestQueuegetQueue = Volley.newRequestQueue(getContext());
        parseJSONOrders();
        parseJSON();

        return view;
    }

    private void parseJSON() {

        String url = "http://18.220.53.162/kamvia/api/timeline.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //geting pdf url from server
                        String pdfurl = jsonObject.getString("pdfurl");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        String image = jsonObject.getString("post_image");
                        timelinelist.add(new HomeTimelineListItem(pdfurl, heading, condent, image));

                    }

                    homeTimelineRecyAdapter = new HomeTimelineRecyAdapter(timelinelist, getContext());
                    news_recyclerView.setAdapter(homeTimelineRecyAdapter);

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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }

    private void parseJSONOrders() {

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
                        String orderId = jsonObject.getString("id");
                        String orderDetails = jsonObject.getString("url");

                        orderlist.add(new ListAllOrdersListItem(orderId, orderDetails));

                    }

                    listAllOrdersRecyAdapter = new ListAllOrdersRecyAdapter(orderlist, getContext());
                    orders_recyclerView.setAdapter(listAllOrdersRecyAdapter);

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
        requestQueuegetQueue.add(jsonObjectRequest);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading List");
        progressDialog.show();
    }


    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("You are not a Registered member. Please request for membership in New Member");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}
