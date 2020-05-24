package com.spyromedia.android.kamvia.DrawerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.HomeTimelineListItem;
import com.spyromedia.android.kamvia.HomeTimelineRecyAdapter;
import com.spyromedia.android.kamvia.LoginActivity;
import com.spyromedia.android.kamvia.R;
import com.spyromedia.android.kamvia.UserRegistrationActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

         HomeTimelineRecyAdapter adapter;
         List<HomeTimelineListItem> timelinelist;

        RecyclerView home_recyclerview = view.findViewById(R.id.home_recyclerview);
        home_recyclerview.setHasFixedSize(true);

        home_recyclerview.setLayoutManager(new LinearLayoutManager( getContext()));

        timelinelist = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            HomeTimelineListItem timelineitem = new HomeTimelineListItem("Dasan"+i, "Thrikkaderi"+i,"KL-51");
            timelinelist.add(timelineitem);
        }

        adapter = new HomeTimelineRecyAdapter(timelinelist,getContext());

        home_recyclerview.setAdapter(adapter);



        Button login = view.findViewById(R.id.id_login);
        Button btn_register = view.findViewById(R.id.id_userupdate);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg  =new Intent(getContext(), UserRegistrationActivity.class);
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
}
