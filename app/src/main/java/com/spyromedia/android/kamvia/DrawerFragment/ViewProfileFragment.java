package com.spyromedia.android.kamvia.DrawerFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.spyromedia.android.kamvia.EditProfileActivity;
import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.LoginActivity;
import com.spyromedia.android.kamvia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewProfileFragment extends Fragment {

    TextView username, useremail, userphone, userrole;
    ProgressDialog progressDialog;
    ImageView profileimage;
    SharedPreferences sharedPreferences;
    String TAG = "ViewProfie";
    String user_id;
    MaterialButton logoutbtn;
    Button editprofile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewprofilenew, container, false);
        username = view.findViewById(R.id.username);
        useremail = view.findViewById(R.id.useremailtext);
        userphone = view.findViewById(R.id.userphonetext);
        userrole = view.findViewById(R.id.userroletext);
        profileimage = view.findViewById(R.id.profileimage);
        logoutbtn = view.findViewById(R.id.logout_btn);
        user_id = Globals.currentUser.USER_ID;

        editprofile = view.findViewById(R.id.EditProfile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(editprofile);
            }
        });
        Log.d(TAG, "Current User" + user_id);
        FetchDetails();
        fetchimage();
        logOutsession();
        return view;
    }

    private void logOutsession() {
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("settings", 0);
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });
    }


    private void fetchimage() {
        try {
            String url = "http://18.220.53.162/kamvia/api/uploads/" + user_id + ".png";
            Glide.with(getActivity())
                    .load(url)
                    .circleCrop()
                    .into(profileimage);
        }
        catch (Exception ex){
            ex.getMessage();
        }

    }

    public void FetchDetails() {

        String url = "http://18.220.53.162/kamvia/api/LoadDetails.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    Log.d(TAG, "onResponse: " + response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String name = jsonObject1.optString("name");
                        String email = jsonObject1.optString("email");
                        String phone = jsonObject1.optString("mobile_number");
                        String role = jsonObject1.optString("user_role");
                        username.setText(name);
                        useremail.setText(email);
                        userphone.setText(phone);
                        userrole.setText(role);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {

                    Toast.makeText(getActivity(), "Server Error" + error, Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getActivity(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                 Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id.trim());


                return params;
            }
        };
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

}
