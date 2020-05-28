package com.spyromedia.android.kamvia.DrawerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.LoginActivity;
import com.spyromedia.android.kamvia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProfileFragment extends Fragment {

    TextView user_name,email,employee_no;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewprofile,container,false);
        user_name = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email);
        employee_no = view.findViewById(R.id.employee_no);
        ProfileDetails();
        return view;
    }
    public void ProfileDetails() {

        String url = "http://18.220.53.162/kamvia/api/list_user_details.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(getContext(), "Details found", Toast.LENGTH_LONG).show();
                        String uname = jsonObject.getString("user_name");
                        user_name.setText(uname);
                        email.setText("");
                        employee_no.setText("");

                    } else {

                        Toast.makeText(getContext(), "No details found", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error:" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userid",Globals.USER_ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
