package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spyromedia.android.kamvia.CustomLinearLayoutManager;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyView.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.OrdersandCircularViews.ListAllOrdersActivity;
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
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueuegetTimeline ,orderRequestQueue;
    RecyclerView news_recyclerView , ordersRecyView;
    List<ListAllOrdersListItem> listAllOrdersListItems;
    ListAllOrdersRecyAdapter adapter;

    ProgressDialog progressDialog;



    AppCompatImageView orderArchive;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_frame, container, false);

        SnapHelper snapHelper = new PagerSnapHelper();
        orderArchive = view.findViewById(R.id.appCompatImageView);
        orderArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allorder = new Intent(getActivity() , ListAllOrdersActivity.class);
                startActivity(allorder);
            }
        });
        news_recyclerView = view.findViewById(R.id.newsrecyclerview);
        news_recyclerView.setHasFixedSize(true);
        //news_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        news_recyclerView.setLayoutManager(new CustomLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        timelinelist = new ArrayList<>();
        requestQueuegetTimeline = Volley.newRequestQueue(getContext());


        parseJSON();

        ordersRecyView = view.findViewById(R.id.ordersrecyclerview);
        ordersRecyView.setHasFixedSize(true);
        ordersRecyView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAllOrdersListItems = new ArrayList<>();
        orderRequestQueue = Volley.newRequestQueue(getContext());


        getOrdersList();


        return view;
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
                        timelinelist.add(new HomeTimelineListItem(pdfurl, heading, condent, image));

                    }

                    homeTimelineRecyAdapter = new HomeTimelineRecyAdapter(timelinelist, getContext());
                    news_recyclerView.setAdapter(homeTimelineRecyAdapter);

                    final int speedScroll = 1000;
                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        int count = 0;
                        boolean flag = true;
                        @Override
                        public void run() {
                            //try{
                                if(count < homeTimelineRecyAdapter.getItemCount()){
                                    if(count==homeTimelineRecyAdapter.getItemCount()-1){
                                        flag = false;
                                    }else if(count == 0){
                                        flag = true;
                                    }
                                    if(flag) count++;
                                    else count--;

                                    news_recyclerView.smoothScrollToPosition(count);
                                    handler.postDelayed(this,speedScroll);
                                }
                           // }catch (Exception ex){

                           // }

                        }
                    };

                    handler.postDelayed(runnable,speedScroll);


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
        progressDialog.setMessage("Loading latest news");
        progressDialog.show();
    }

    private void getOrdersList() {

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

                    adapter = new ListAllOrdersRecyAdapter(listAllOrdersListItems, getActivity());
                    ordersRecyView.setAdapter(adapter);


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
        orderRequestQueue.add(jsonObjectRequest);

//        progressDialog = new ProgressDialog(ListAllOrdersActivity.this);
//        progressDialog.setMessage("Loading List");
//        progressDialog.show();
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
