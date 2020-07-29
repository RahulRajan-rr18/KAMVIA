package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProfileFragment extends Fragment {

    TextView user_name, email, employee_no;
    ProgressDialog progressDialog;
    ImageView profileimage;
    String TAG = "ViewProfie";
    String user_id;
    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewprofile, container, false);
        user_name = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        employee_no = view.findViewById(R.id.employee_no);
        profileimage = view.findViewById(R.id.profileimage);
        user_id = Globals.currentUser.USER_ID;
        Log.d(TAG, "Current User" + user_id);
        ProfileDetails();
        fetchimage();
        return view;
    }


    private void fetchimage() {
        String url = "http://18.220.53.162/kamvia/api/uploads/retrieveimage.php?file=" + user_id;
        Glide.with(getActivity())
                .load(url)
                .circleCrop()
                .into(profileimage);
    }

    public void ProfileDetails() {

        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php?user_id=" + user_id;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        username = jsonObject1.optString("name");
                        Log.d(TAG, "onResponse: " + user_name);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: Error Occured" + e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                Log.d(TAG, "onErrorResponse: ERoorr");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userid",Globals.currentUser.USER_ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading......");
        progressDialog.show();

    }
}
