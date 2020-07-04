package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    @Nullable
    HomeTimelineRecyAdapter adapter;
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueuegetTimeline, requestQueuegetUser;
    RecyclerView home_recyclerview;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_recyclerview = view.findViewById(R.id.home_recyclerview);

        String userverified = Globals.currentUser.VERIFICATION;

        CheckUserVerificationStatus();



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
                        String user_id = jsonObject.getString("user_id");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        timelinelist.add(new HomeTimelineListItem("", heading, condent));

                    }

                    adapter = new HomeTimelineRecyAdapter(timelinelist, getContext());
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading......");
        progressDialog.show();
    }

    private void CheckUserVerificationStatus() {
        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String Username = jsonObject1.optString("name");
                        Log.d("MainActivity", Username);
                        sharedPreferences = getContext().getSharedPreferences("settings", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        String verification_status = jsonObject1.optString("verification_status");
                        if (verification_status.equals("verified")) {



                            SharedPreferences preferences = getContext().getSharedPreferences("settings", 0);
                            SharedPreferences.Editor ed = preferences.edit();

                            ed.putString("VERIFICATION", "verified");
                            ed.putString("USER_NAME",Username);
                            //editor.apply();
                            ed.commit();
                            Globals.currentUser.VERIFICATION="verified";
                            Globals.currentUser.USER_NAME=Username;



                            home_recyclerview.setHasFixedSize(true);
                            home_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                            timelinelist = new ArrayList<>();

                            requestQueuegetTimeline = Volley.newRequestQueue(getContext());
                            parseJSON();

                        } else {

                            if (verification_status.equals(null)) {

                            } else {
                                alertDialog();
                            }


                        }

                        editor.putString("VERIFICATION", verification_status);
                        editor.putString("USER_NAME", Username);
                        editor.apply();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sharedPreferences = getContext().getSharedPreferences("settings", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("VERIFICATION", "notverified");
                    editor.apply();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(getContext(), "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                String user_id = Globals.currentUser.USER_ID;
                params.put("user_id", user_id.trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
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
