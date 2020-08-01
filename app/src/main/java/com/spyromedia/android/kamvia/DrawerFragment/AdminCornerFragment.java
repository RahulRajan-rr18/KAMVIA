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

import com.spyromedia.android.kamvia.AddNewPostActivity;
import com.spyromedia.android.kamvia.AddStationsActivity;
import com.spyromedia.android.kamvia.AdminSearchMembersActivity;
import com.spyromedia.android.kamvia.ApprovalListAdminActivity;
import com.spyromedia.android.kamvia.R;

public class AdminCornerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_admin_corner,container,false);


      Button btnNewPost = view.findViewById(R.id.btn_createpost);
      btnNewPost.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent addpost = new Intent(getContext(),AddNewPostActivity.class);
              startActivity(addpost);
          }
      });

      Button btnApprovalList = view.findViewById(R.id.btn_approvalRequests);
      btnApprovalList.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent approvelist = new Intent(getContext(), ApprovalListAdminActivity.class);
              startActivity(approvelist);
          }
      });

      Button btn_findmembers = view.findViewById(R.id.btn_findmembers);
      btn_findmembers.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent findmem = new Intent(getContext(), AdminSearchMembersActivity.class);
              startActivity(findmem);
          }
      });
      Button btnAddNewStation = view.findViewById(R.id.btnddnewstation);
      btnAddNewStation.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent newstation = new Intent(getContext(), AddStationsActivity.class);
              startActivity(newstation);
          }
      });

        return  view;
    }
}
