package com.spyromedia.android.kamvia.AdminFunctions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spyromedia.android.kamvia.R;

public class MembershipTabActivity extends AppCompatActivity implements View.OnClickListener {
    Button approvalRequests, searchMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_tab);
        getSupportActionBar().hide();

        searchMembers = findViewById(R.id.btnSearchMembers);
        approvalRequests = findViewById(R.id.btn_membership);

        searchMembers.setOnClickListener(this);
        approvalRequests.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSearchMembers:
                Intent findmem = new Intent(MembershipTabActivity.this, AdminSearchMembersActivity.class);
                startActivity(findmem);
                break;

            case R.id.btn_membership:
                Intent approvelist = new Intent(MembershipTabActivity.this, ApprovalListAdminActivity.class);
                startActivity(approvelist);
                break;

            default:
                break;
        }
    }
}