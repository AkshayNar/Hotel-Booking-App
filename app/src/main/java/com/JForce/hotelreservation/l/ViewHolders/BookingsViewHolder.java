package com.JForce.hotelreservation.l.ViewHolders;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.JForce.hotelreservation.R;

public class BookingsViewHolder extends RecyclerView.ViewHolder{


    public TextView guestName, guestPhoneNumber, roomNumber, checkInDate;
    public ImageView roomImage;


    public BookingsViewHolder(@NonNull View itemView) {
        super(itemView);


        guestName = itemView.findViewById(R.id.name_txt);
        guestPhoneNumber = itemView.findViewById(R.id.phone_txt);
        roomNumber = itemView.findViewById(R.id.room_Number_txt);
        checkInDate = itemView.findViewById(R.id.check_in_date_txt);
        roomImage = itemView.findViewById(R.id.room_image);


    }
}
