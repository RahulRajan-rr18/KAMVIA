package com.spyromedia.android.kamvia.AdminFunctions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.R;

import java.util.ArrayList;
import java.util.List;

public class AdminSearchRecyAdapter extends RecyclerView.Adapter<AdminSearchRecyAdapter.ViewHolder >{
    private  List<AdminSeachResultItem> resultList;
    private Context context;

    public AdminSearchRecyAdapter(List<AdminSeachResultItem> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_search_result_item,parent,false);
          //routelist_item: layout name of menu
        return  new ViewHolder(v,context,resultList);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AdminSeachResultItem adminSeachResultItem = resultList.get(position);
        //routeListItems: list variable
        holder.membername.setText(adminSeachResultItem.getMemberName());
        holder.memberlocation.setText(adminSeachResultItem.getMemberLocation());
        holder.memberstationCode.setText(adminSeachResultItem.getMemberStationCode());
    }
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public  TextView membername;
        public TextView memberlocation;
        public TextView memberstationCode;

        List<AdminSeachResultItem> listItems = new ArrayList<AdminSeachResultItem>();
        Context context;
        public ViewHolder(View itemView, Context context, List<AdminSeachResultItem> resultList) {
            super(itemView);

            this.listItems = resultList;
            this.context = context;

            itemView.setOnClickListener(this);

            membername =(TextView)itemView.findViewById(R.id.textview_membername);
            memberlocation =(TextView)itemView.findViewById(R.id.textview_heading);
            memberstationCode = (TextView)itemView.findViewById(R.id.tv_condent);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            AdminSeachResultItem listItem = this.listItems.get(position);
            Intent intent = new Intent(this.context,AdminMemberSearchResultViewActivity.class);
            String user_id = listItem.getUser_id();
            intent.putExtra("user_id",user_id);

            this.context.startActivity(intent);
        }
    }
}
