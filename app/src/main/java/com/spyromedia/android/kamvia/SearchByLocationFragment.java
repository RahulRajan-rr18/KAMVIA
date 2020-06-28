package com.spyromedia.android.kamvia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class SearchByLocationFragment extends Fragment {

    AutoCompleteTextView searchLocation;
    ArrayList<String> itemlistvalues;
    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_location, container, false);

        searchLocation = view.findViewById(R.id.searchLocation);
        requestQueue = Volley.newRequestQueue(getContext());
        ListNames();

        searchLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String userverified = Globals.currentUser.VERIFICATION;
                if (userverified.equals("verified")) {
                    Intent results = new Intent(getActivity(), SearchByLocationResultActivity.class);
                    results.putExtra("location", searchLocation.getText().toString());
                    startActivity(results);

                } else {
                    alertDialog();
                }




            }
        });

        return view;

    }

    private void ListNames() {

        String url = "http://18.220.53.162/kamvia/api/users.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    itemlistvalues = new ArrayList<String>();

                    String name;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        name = jsonArray.getJSONObject(i).getString("home_station");
                        itemlistvalues.add(name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, itemlistvalues);
                    searchLocation.setAdapter(adapter);

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
    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("You are not a Registered member. Please request for membership.");
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
