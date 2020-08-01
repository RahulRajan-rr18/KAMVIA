package com.spyromedia.android.kamvia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApprovalListAdapter extends RecyclerView.Adapter<ApprovalListAdapter.ViewHolder> {
    private final List<ApprovalMemberListItem> aprovallist;
    private final Context context;

    public ApprovalListAdapter(List<ApprovalMemberListItem> approvalmemlist, Context context) {
        this.aprovallist = approvalmemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.approvalmemberlistitem, parent, false);
        //routelist_item: layout name of menu
        return new ViewHolder(v, context, aprovallist);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApprovalMemberListItem approvememberlistitem = aprovallist.get(position);
        //routeListItems: list variable
        holder.membername.setText(approvememberlistitem.getMemberName());
        holder.mobilenumber.setText(approvememberlistitem.getMemberLocation());
        holder.homestation.setText(approvememberlistitem.getMemberStationCode());


    }

    @Override
    public int getItemCount() {
        return aprovallist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView membername;
        public TextView mobilenumber;
        public TextView homestation;

        List<ApprovalMemberListItem> listItems = new ArrayList<ApprovalMemberListItem>();
        Context context;


        public ViewHolder(View itemView, Context context, List<ApprovalMemberListItem> aprovallist) {
            super(itemView);

            this.listItems = aprovallist;
            this.context = context;

            itemView.setOnClickListener(this);
            membername = itemView.findViewById(R.id.textview_membername);
            homestation = itemView.findViewById(R.id.tv_condent);
            mobilenumber = itemView.findViewById(R.id.textview_heading);


        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            ApprovalMemberListItem approvalMemberListItem = this.listItems.get(position);

            try {

                Intent intent = new Intent(this.context, ApprovalMemberDetailsActivity.class);
                intent.putExtra("user_id", approvalMemberListItem.getMember_id());

                this.context.startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }
}
