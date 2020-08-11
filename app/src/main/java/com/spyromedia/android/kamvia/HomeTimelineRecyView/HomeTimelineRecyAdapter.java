package com.spyromedia.android.kamvia.HomeTimelineRecyView;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spyromedia.android.kamvia.Globals;
import com.spyromedia.android.kamvia.R;

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
        String stringImage = homeTimelineList.getPostImage();


        try{
            if(stringImage.equals(null)){
                Toast.makeText(context, "Null Image", Toast.LENGTH_SHORT).show();
            }else{
                Bitmap image = StringToBitMap(stringImage);
                int value = 0;
                if (image.getHeight() <= image.getWidth()) {
                    value = image.getHeight();
                } else {
                    value = image.getWidth();
                }

                Bitmap finalBitmap = null;
                finalBitmap = Bitmap.createBitmap(image, 0, 0, value, value);
                holder.postImage.setImageBitmap(finalBitmap);
            }
        }catch (Exception ex){
            ex.getMessage();
        }






    }



    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView user_id;
        public TextView heading;
        public TextView condent;
        public ImageView postImage;

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
            postImage = (ImageView) itemView.findViewById(R.id.postImage);
        }


        @Override
        public void onClick(View v) {
            String verified = Globals.currentUser.VERIFICATION;
            if (verified.equals("verified")) {

                int position = getAdapterPosition();
                HomeTimelineListItem homeTimelineListItem = this.listItems.get(position);
                Intent intent = new Intent(this.context, TimelineViewActivity.class);

                String pdfurl = homeTimelineListItem.getUserid();
                intent.putExtra("pdfurl", pdfurl);
                intent.putExtra("heading", homeTimelineListItem.getHeading());
                intent.putExtra("condent", homeTimelineListItem.getCondent());
                intent.putExtra("image",homeTimelineListItem.getPostImage());

                this.context.startActivity(intent);

            } else {


                int position = getAdapterPosition();
                HomeTimelineListItem homeTimelineListItem = this.listItems.get(position);
                Intent intent = new Intent(this.context, TimelineViewActivity.class);

                String pdfurl = homeTimelineListItem.getUserid();
                intent.putExtra("pdfurl", pdfurl);
                intent.putExtra("heading", homeTimelineListItem.getHeading());
                intent.putExtra("condent", homeTimelineListItem.getCondent());

                this.context.startActivity(intent);


                //  alertDialog();
            }


        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("You are not a Registered member. Please request for membership first.");
        dialog.setTitle("Alert");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
