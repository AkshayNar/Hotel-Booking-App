package com.JForce.hotelreservation.l.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.JForce.hotelreservation.R;
import com.JForce.hotelreservation.l.Interface.ItemClickListener;

public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtRName, txtRDescription, txtROriginalPrice, txtRCapacity, txtRDiscountPrice, txtRoffer, txtRType, txtRStatus, crossLine, txtlastUpdated;
    public ImageView imageView ;
    public ItemClickListener listner;


    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.admin_room_image);
        txtRName = itemView.findViewById(R.id.room_title_txt);
        txtROriginalPrice = itemView.findViewById(R.id.price_txt);
        txtRCapacity = itemView.findViewById(R.id.discount_price_txt);
        txtRDiscountPrice = itemView.findViewById(R.id.discount_price_txt);
        txtRoffer = itemView.findViewById(R.id.off_txt);
        crossLine = itemView.findViewById(R.id.crossline);
        txtRType = itemView.findViewById(R.id.type_txt);
        txtRStatus = itemView.findViewById(R.id.status_txt);
        txtlastUpdated = itemView.findViewById(R.id.last_updated_txt);

    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(), false);

    }
}
