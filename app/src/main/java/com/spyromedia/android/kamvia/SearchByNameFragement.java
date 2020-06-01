package com.spyromedia.android.kamvia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class SearchByNameFragement extends Fragment {
    RequestQueue requestQueue;
    AutoCompleteTextView SearchByname;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<SearchResultRecyItem> resultList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_name, container, false);

        String[] itemlistvalues = {"Raju", "Balu", "Raman", "KrishnanKutty", "Gopalan", "Ashok",
                "Anjas", "Muhammed", "Chandran", "Anoop", "Aravind"};

        SearchByname = view.findViewById(R.id.searchbyname);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.select_dialog_item, itemlistvalues);
        SearchByname.setAdapter(arrayAdapter);

        resultList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        ListNames();

        SearchByname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getContext(), "Hola...", Toast.LENGTH_SHORT).show();
                Intent results = new Intent(getActivity(), SearchByNameResultActivity.class);
                startActivity(results);

            }
        });
        return view;
    }

    private void ListNames(){

        String url = "http://18.220.53.162/kamvia/api/users.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String uname = jsonObject.getString("name");
                        String location = jsonObject.getString("home_station");
                        String stationcode = jsonObject.getString("home_station_code");

                        resultList.add(new SearchResultRecyItem(uname,location,stationcode));

                    }
                    adapter = new SearchResultAdapter(resultList,getActivity());
                    recyclerView.setAdapter(adapter);


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
