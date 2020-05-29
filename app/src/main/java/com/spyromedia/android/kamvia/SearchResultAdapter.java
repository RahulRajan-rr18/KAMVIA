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

        SearchResultRecyItem speakerListItem = resultList.get(position);
        //routeListItems: list variable
        holder.membername.setText(speakerListItem.getMemberName());
        holder.memberlocation.setText(speakerListItem.getMemberLocation());
        holder.memberstationCode.setText(speakerListItem.getMemberStationCode());

//        holder.textViewHead.setText(routeListItem.getHead());
//        holder.textViewDesc.setText(routeListItem.getDesc());

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
            memberlocation =(TextView)itemView.findViewById(R.id.textview_place);
            memberstationCode = (TextView)itemView.findViewById(R.id.textview_stationcode);
//            textViewHead=(TextView)itemView.findViewById( R.id.textViewHead);
//            textViewDesc=(TextView)itemView.findViewById( R.id.textViewDesc);

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(this.context,MemberDetailsActivity.class);
            this.context.startActivity(intent);

        }
    }
}
