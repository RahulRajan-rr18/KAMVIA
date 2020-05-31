package com.spyromedia.android.kamvia.DrawerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

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
import com.spyromedia.android.kamvia.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.LoginActivity;
import com.spyromedia.android.kamvia.R;
import com.spyromedia.android.kamvia.UserRegistrationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    HomeTimelineRecyAdapter adapter;
    List<HomeTimelineListItem> timelinelist;
    RequestQueue requestQueue;
    RecyclerView home_recyclerview;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

         home_recyclerview = view.findViewById(R.id.home_recyclerview);
        home_recyclerview.setHasFixedSize(true);
        home_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        timelinelist = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(getContext());
        parseJSON();


//        for (int i = 0; i < 10; i++) {
//
//            HomeTimelineListItem timelineitem = new HomeTimelineListItem("Dasan"+i, "Thrikkaderi"+i,"KL-51");
//            timelinelist.add(timelineitem);
//        }
//
//        adapter = new HomeTimelineRecyAdapter(timelinelist,getContext());
//
//        home_recyclerview.setAdapter(adapter);


        Button login = view.findViewById(R.id.id_login);
        Button btn_register = view.findViewById(R.id.id_userupdate);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getContext(), UserRegistrationActivity.class);
                startActivity(reg);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void parseJSON() {

        String url = "http://18.220.53.162/kamvia/api/timeline.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i =0; i <jsonArray.length() ; i++ ){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user_id = jsonObject.getString("user_id");
                        String heading = jsonObject.getString("heading");
                        String condent = jsonObject.getString("condent");
                        timelinelist.add(new HomeTimelineListItem(user_id,heading,condent));

                    }

                    adapter = new HomeTimelineRecyAdapter(timelinelist,getContext());
                    home_recyclerview.setAdapter(adapter);

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
