package com.spyromedia.android.kamvia.ListAllStations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.Globals;

import com.spyromedia.android.kamvia.R;

import java.util.ArrayList;
import java.util.List;

public class ListAllStationsRecyAdapter extends RecyclerView.Adapter<ListAllStationsRecyAdapter.ViewHolder> {
    private List<ListStationsItem> listStationsItems;
    private Context context;

    public ListAllStationsRecyAdapter(List<ListStationsItem> stationsItems, Context context) {
        this.listStationsItems = stationsItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listallstationsitem, parent, false);
        //routelist_item: layout name of menu
        return new ViewHolder(v, context, listStationsItems);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListStationsItem listStationsItem = listStationsItems.get(position);
        holder.stationName.setText(listStationsItem.getStationName());

    }



    @Override
    public int getItemCount() {
        return listStationsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView stationName;


        List<ListStationsItem> listItems = new ArrayList<ListStationsItem>();
        Context context;


        public ViewHolder(View itemView, Context context, List<ListStationsItem> stationsItems) {
            super(itemView);

            this.listItems = stationsItems;
            this.context = context;

            itemView.setOnClickListener(this);
            stationName = (TextView) itemView.findViewById(R.id.textviewStationName);

        }


        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
                ListStationsItem listStationsItem = this.listItems.get(position);
                Intent intent = new Intent(this.context, ListAllStationsActivity.class);

                String pdfurl = listStationsItem.getStationId();
             //   intent.putExtra("StationId", homeTimelineListItem.getHeading());
                intent.putExtra("StationId", listStationsItem.getStationId());

                this.context.startActivity(intent);


        }
    }



}
