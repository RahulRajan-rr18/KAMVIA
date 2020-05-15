package com.spyromedia.android.kamvia;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder >{
    private  List<SearchResultItem> resultList;
    private Context context;

    public SearchResultAdapter(List<SearchResultItem> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.searchresultitem,parent,false);
          //routelist_item: layout name of menu
        return  new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SearchResultItem speakerListItem = resultList.get(position);
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
