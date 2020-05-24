package com.spyromedia.android.kamvia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApprovalListAdapter extends RecyclerView.Adapter<ApprovalListAdapter.ViewHolder >{
    private  List<ApprovalMemberListItem> aaprovallist;
    private Context context;

    public ApprovalListAdapter(List<ApprovalMemberListItem> approvalmemlist, Context context) {
        this.aaprovallist = approvalmemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.approvalmemberlistitem,parent,false);
          //routelist_item: layout name of menu
        return  new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApprovalMemberListItem approvememberlistitem = aaprovallist.get(position);
        //routeListItems: list variable
        holder.membername.setText(approvememberlistitem.getMemberName());
        holder.memberlocation.setText(approvememberlistitem.getMemberLocation());
        holder.memberstationCode.setText(approvememberlistitem.getMemberStationCode());

//        holder.textViewHead.setText(routeListItem.getHead());
//        holder.textViewDesc.setText(routeListItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return aaprovallist.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder {
        public  TextView membername;
        public TextView memberlocation;
        public TextView memberstationCode;

//        public TextView textViewHead;
//        public TextView textViewDesc;


        public ViewHolder(View itemView) {
            super(itemView);
            membername =(TextView)itemView.findViewById(R.id.textview_membername);
            memberlocation =(TextView)itemView.findViewById(R.id.textview_place);
            memberstationCode = (TextView)itemView.findViewById(R.id.textview_stationcode);
//            textViewHead=(TextView)itemView.findViewById( R.id.textViewHead);
//            textViewDesc=(TextView)itemView.findViewById( R.id.textViewDesc);

        }
    }
}
