package com.spyromedia.android.kamvia.OrdersandCircularViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.DrawerFragment.MainActivity;
import com.spyromedia.android.kamvia.R;

import java.util.ArrayList;
import java.util.List;

public class ListAllOrdersRecyAdapter extends RecyclerView.Adapter<ListAllOrdersRecyAdapter.ViewHolder> {
    private final List<ListAllOrdersListItem> listOrders;
    private final Context context;

    public ListAllOrdersRecyAdapter(List<ListAllOrdersListItem> stationsItems, Context context) {
        this.listOrders = stationsItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listallorderslistitem, parent, false);
        //routelist_item: layout name of menu
        return new ViewHolder(v, context, listOrders);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListAllOrdersListItem listAllOrdersListItem = listOrders.get(position);
        holder.orderDetails.setText(listAllOrdersListItem.getOrderDetails());

    }



    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView orderDetails;


        List<ListAllOrdersListItem> listItems = new ArrayList<ListAllOrdersListItem>();
        Context context;


        public ViewHolder(View itemView, Context context, List<ListAllOrdersListItem> listAllOrdersListItems) {
            super(itemView);

            this.listItems = listAllOrdersListItems;
            this.context = context;

            itemView.setOnClickListener(this);
            orderDetails = (TextView) itemView.findViewById(R.id.tvOrders);

        }


        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
                ListAllOrdersListItem listStationsItem = this.listItems.get(position);
                Intent intent = new Intent(this.context, MainActivity.class);

                String pdfurl = listStationsItem.getOrderId();
             //   intent.putExtra("StationId", homeTimelineListItem.getHeading());
                intent.putExtra("StationId", listStationsItem.getOrderId());

                this.context.startActivity(intent);


        }
    }



}
