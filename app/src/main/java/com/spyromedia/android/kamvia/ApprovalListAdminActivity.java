package com.spyromedia.android.kamvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class ApprovalListAdminActivity extends AppCompatActivity {

    private ApprovalListAdapter adapter;
    private List<ApprovalMemberListItem> approvallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_list_admin);

        RecyclerView approval_recyclerView = findViewById(R.id.recyclerviewMemberApproval);
        approval_recyclerView.setHasFixedSize(true);

        approval_recyclerView.setLayoutManager(new LinearLayoutManager( this));

        approvallist = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            ApprovalMemberListItem approvalmemberlist = new ApprovalMemberListItem("Govindan"+i, "Chavakkad","KL89");
            approvallist.add(approvalmemberlist);
        }

        adapter = new ApprovalListAdapter(approvallist, this);

        approval_recyclerView.setAdapter(adapter);
    }
}
