package com.JForce.hotelreservation.l.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RoomDetailsActivity extends AppCompatActivity {


    private Button bookRoom;
    private ImageView roomImage;
    private TextView roomTitle, roomDescription, roomOriginalPrice, roomofferPrice, crossline, Roff;
    private String Rid = "", RImage = "", Rstate = "", RTitle = "", RCapacity, ROriginalPrice, RDiscountPrice, RDescription,
            roff = "", emailID, checkInDate;
    private String name, phone, uid;

    private ProgressDialog loadingBar;


    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        roomImage = findViewById(R.id.room_image_details);
        roomTitle = findViewById(R.id.room_name_details);
        roomOriginalPrice = findViewById(R.id.room_price_details);
        roomofferPrice = findViewById(R.id.room_offer_price_details);
        roomDescription = findViewById(R.id.Room_description_details);
        crossline = findViewById(R.id.room_price_line_details);
        Roff = findViewById(R.id.percent_off_double);
        bookRoom = findViewById(R.id.book_room);

        loadingBar = new ProgressDialog(this);


        fStore = FirebaseFirestore.getInstance();


        RTitle = getIntent().getStringExtra("RTitle");
        RCapacity = getIntent().getStringExtra("RCapacity");
        ROriginalPrice = getIntent().getStringExtra("ROriginalPrice");
        RDiscountPrice = getIntent().getStringExtra("RDiscountPrice");
        RDescription = getIntent().getStringExtra("RDescription");
        RImage = getIntent().getStringExtra("RImage");
        roff = getIntent().getStringExtra("Roff");
        emailID = getIntent().getStringExtra("emailId");


        displayRoomInfo(RTitle, RCapacity, ROriginalPrice, RDiscountPrice, RDescription);


    }

    private void displayRoomInfo(String rTitle, String rCapacity, String rOriginalPrice, String rDiscountPrice, String rDescription) {


        roomTitle.setText(rTitle);
        // maintainRCapacity.setText(rCapacity);
        roomOriginalPrice.setText(rOriginalPrice);
        roomofferPrice.setText(rDiscountPrice);
        roomDescription.setText(rDescription);
        Roff.setText(roff + " %off");
        Picasso.get().load(String.valueOf(RImage)).placeholder(R.drawable.loading).into(roomImage);

        if (!roff.equals("")) {
            Roff.setVisibility(View.VISIBLE);
            roomofferPrice.setVisibility(View.VISIBLE);
            crossline.setVisibility(View.VISIBLE);
            roomofferPrice.setTextColor(getColor(R.color.colorPrimary));
            roomOriginalPrice.setTextColor(Color.rgb(178, 170, 170));

        }


        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rstate = getIntent().getStringExtra("Status");

                if (!Rstate.equals("Booked")) {
                    BookRoom();
                } else {
                    Toast.makeText(RoomDetailsActivity.this, "Room is Already Booked on selected date... Please select other date", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void BookRoom() {

        loadingBar.setTitle("Booking about to done");
        loadingBar.setMessage("Please wait....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        emailID = getIntent().getStringExtra("emailId");
        checkInDate = getIntent().getStringExtra("CheckInDate");
        Rid = getIntent().getStringExtra("Rid");
        RTitle = getIntent().getStringExtra("RTitle");
        RCapacity = getIntent().getStringExtra("RCapacity");
        ROriginalPrice = getIntent().getStringExtra("ROriginalPrice");
        RDiscountPrice = getIntent().getStringExtra("RDiscountPrice");
        RDescription = getIntent().getStringExtra("RDescription");
        RImage = getIntent().getStringExtra("RImage");
        roff = getIntent().getStringExtra("Roff");
        emailID = getIntent().getStringExtra("emailId");


        Query query = fStore.collection("Users").whereEqualTo("emailId", emailID);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        name = documentSnapshot.getString("name");
                        phone = documentSnapshot.getString("phone");
                        uid = documentSnapshot.getString("uid");




                        Calendar calendarForDate = Calendar.getInstance();

                        String saveCurrentDate, saveCurrenttime;

                        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
                        saveCurrentDate = currentDate.format(calendarForDate.getTime());

                        SimpleDateFormat currenttime = new SimpleDateFormat("hh : mm ");
                        saveCurrenttime = currentDate.format(calendarForDate.getTime());


                        String bookingID = Rid + checkInDate;

                        fStore = FirebaseFirestore.getInstance();


                        HashMap<String, Object> roomMap = new HashMap<>();
                        roomMap.put("Rid", Rid);
                        roomMap.put("RTitle", RTitle);
                        roomMap.put("RImage", RImage);
                        roomMap.put("ROriginalPrice", ROriginalPrice);
                        roomMap.put("RDiscountPrice", RDiscountPrice);
                        roomMap.put("Roffer", roff);
                        roomMap.put("nameOfGuest", name);
                        roomMap.put("CheckInDate", checkInDate);
                        roomMap.put("GuestPhoneNumber", phone);
                        roomMap.put("BookingID", bookingID);
                        roomMap.put("BookingDate", saveCurrentDate);
                        roomMap.put("Status", "Booked");

                        fStore.collection("All Bookings").document(bookingID).set(roomMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            loadingBar.dismiss();

                                            Toast.makeText(RoomDetailsActivity.this, "Room Booked Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RoomDetailsActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                });




                    }
                }

            }
        });





    }
}