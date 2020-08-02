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

public class HomeTimelineRecyAdapter extends RecyclerView.Adapter<HomeTimelineRecyAdapter.ViewHolder> {
    private List<HomeTimelineListItem> timelineList;
    private Context context;

    public HomeTimelineRecyAdapter(List<HomeTimelineListItem> timelineList, Context context) {
        this.timelineList = timelineList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hometimelineitem, parent, false);
        //routelist_item: layout name of menu
        return new ViewHolder(v, context, timelineList);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HomeTimelineListItem homeTimelineList = timelineList.get(position);
        //routeListItems: list variable
       // holder.user_id.setText(homeTimelineList.getUserid());
        holder.heading.setText(homeTimelineList.getHeading());
        holder.condent.setText(homeTimelineList.getCondent());


    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView user_id;
        public TextView heading;
        public TextView condent;

             List<HomeTimelineListItem> listItems = new ArrayList<HomeTimelineListItem>();
        Context context;


        public ViewHolder(View itemView, Context context, List<HomeTimelineListItem> timelineList) {
            super(itemView);

            this.listItems = timelineList;
            this.context = context;

            itemView.setOnClickListener(this);
            user_id = (TextView) itemView.findViewById(R.id.textview_membername);
            heading = (TextView) itemView.findViewById(R.id.textview_heading);
            condent = (TextView) itemView.findViewById(R.id.tv_condent);
        }


        @Override
        public void onClick(View v) {

             int position = getAdapterPosition();
            HomeTimelineListItem homeTimelineListItem = this.listItems.get(position);
            Intent intent = new Intent(this.context, TimelineViewActivity.class);

            String pdfurl = homeTimelineListItem.getUserid();
            intent.putExtra("pdfurl",pdfurl);
            intent.putExtra("heading",homeTimelineListItem.getHeading());
            intent.putExtra("condent",homeTimelineListItem.getCondent());

            this.context.startActivity(intent);


        }
    }
}
