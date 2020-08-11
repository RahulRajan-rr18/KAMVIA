package com.spyromedia.android.kamvia.DrawerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spyromedia.android.kamvia.AdminFunctions.AddStationsActivity;
import com.spyromedia.android.kamvia.AdminFunctions.AllTypeSearchWindowActivity;
import com.spyromedia.android.kamvia.AdminFunctions.MembershipTabActivity;
import com.spyromedia.android.kamvia.AdminFunctions.PostsActivity;
import com.spyromedia.android.kamvia.BasicSettingsActivity;
import com.spyromedia.android.kamvia.R;

public class AdminCornerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_admin_corner,container,false);


        ImageView exitButton = view.findViewById(R.id.exitbutton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getActivity(),MainActivity.class);
                startActivity(home);

            }
        });

      Button btnNewPost = view.findViewById(R.id.btn_createpost);
      btnNewPost.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent addpost = new Intent(getContext(), PostsActivity.class);
              startActivity(addpost);
          }
      });

      Button btnApprovalList = view.findViewById(R.id.btn_membership);
      btnApprovalList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent membership = new Intent(getContext(), MembershipTabActivity.class);
              startActivity(membership);
          }
      });

      Button btn_findmembers = view.findViewById(R.id.btn_findmembers);
      btn_findmembers.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent findmem = new Intent(getContext(), AllTypeSearchWindowActivity.class);
              startActivity(findmem);
          }
      });
      Button btnAddNewStation = view.findViewById(R.id.basicSettings);
      btnAddNewStation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent newstation = new Intent(getContext(), BasicSettingsActivity.class);
              startActivity(newstation);
          }
      });

        return  view;
    }
}
