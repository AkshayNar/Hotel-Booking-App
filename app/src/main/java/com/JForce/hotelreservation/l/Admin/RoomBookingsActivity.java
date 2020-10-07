package com.JForce.hotelreservation.l.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.JForce.hotelreservation.R;
import com.JForce.hotelreservation.l.ModelClass.Bookings;
import com.JForce.hotelreservation.l.ModelClass.Rooms;
import com.JForce.hotelreservation.l.Users.HomeActivity;
import com.JForce.hotelreservation.l.ViewHolders.BookingsViewHolder;
import com.JForce.hotelreservation.l.ViewHolders.RoomViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class RoomBookingsActivity extends AppCompatActivity {

    private EditText bookigDate;
    private TextView noBookingsAvailable;

    DatePickerDialog picker;

    private String Bookingdate;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_bookings);


        bookigDate = findViewById(R.id.date_field_1);
        noBookingsAvailable = findViewById(R.id.no_bookings_available);




        fStore = FirebaseFirestore.getInstance();

        bookigDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RoomBookingsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                bookigDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                bookigDate.setTextColor(getColor(R.color.colorPrimary));

                                RoomBookingsActivity.this.Bookingdate = bookigDate.getText().toString();

                                onStart();

                            }
                        }, year, month, day);
                picker.show();
            }
        });



        recyclerView = findViewById(R.id.booking_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart() {
        super.onStart();


        Log.d("TAG", "onStart: " + Bookingdate);

        final Query query = fStore.collection("All Bookings").whereEqualTo("CheckInDate", Bookingdate);

        FirestoreRecyclerOptions<Bookings> options =
                new FirestoreRecyclerOptions.Builder<Bookings>()
                        .setQuery(query, Bookings.class)
                        .build();

        FirestoreRecyclerAdapter<Bookings, BookingsViewHolder> adapter = new FirestoreRecyclerAdapter<Bookings, BookingsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookingsViewHolder holder, int position, @NonNull Bookings model) {

                holder.guestName.setText("Name : "+ model.getNameOfGuest());
                holder.guestPhoneNumber.setText("Phone : "+ model.getGuestPhoneNumber());
                holder.roomNumber.setText("Room : "+ model.getRTitle());
                holder.checkInDate.setText("Check In : "+ model.getCheckInDate());
                holder.numberOfGuests.setText("No. of Guests : "+ model.getNumberOfGuests());
                Picasso.get().load(model.getRImage()).placeholder(R.drawable.loading).into(holder.roomImage);

            }

            @NonNull
            @Override
            public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_layout, parent, false);
                BookingsViewHolder holder = new BookingsViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


        int i = recyclerView.getAdapter().getItemCount();

        if (i == 0)
        {
            noBookingsAvailable.setVisibility(View.VISIBLE);
        }



    }
}