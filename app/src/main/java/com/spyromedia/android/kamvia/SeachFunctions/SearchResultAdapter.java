package com.spyromedia.android.kamvia.SeachFunctions;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.MemberDetailsActivity;
import com.spyromedia.android.kamvia.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder >{
    private  List<SearchResultRecyItem> resultList;
    private Context context;

    public SearchResultAdapter(List<SearchResultRecyItem> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresultitem,parent,false);
          //routelist_item: layout name of menu
        return  new ViewHolder(v,context,resultList);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SearchResultRecyItem searchresultitem = resultList.get(position);
        //routeListItems: list variable
        holder.membername.setText(searchresultitem.getMemberName());
        holder.memberlocation.setText(searchresultitem.getMemberLocation());
        holder.memberstationCode.setText(searchresultitem.getMemberStationCode());
    }
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public  TextView membername;
        public TextView memberlocation;
        public TextView memberstationCode;

        List<SearchResultRecyItem> listItems = new ArrayList<SearchResultRecyItem>();
        Context context;
        public ViewHolder(View itemView, Context context, List<SearchResultRecyItem> resultList) {
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
            SearchResultRecyItem listItem = this.listItems.get(position);
            Intent intent = new Intent(this.context, MemberDetailsActivity.class);
            String user_id = listItem.getUser_id();
            intent.putExtra("user_id",user_id);

            this.context.startActivity(intent);
        }
    }
}
