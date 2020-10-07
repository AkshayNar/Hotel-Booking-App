package com.JForce.hotelreservation.l.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.JForce.hotelreservation.l.ModelClass.Rooms;
import com.JForce.hotelreservation.l.ViewHolders.RoomViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class RoomListActivity2 extends AppCompatActivity {



    private String emailid, name, phone, uid;
    private String checkInDate, checkOutDate, Capacity, Type;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    private FirebaseFirestore fStore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now2);



        fStore = FirebaseFirestore.getInstance();



        emailid = getIntent().getStringExtra("emailId");


        checkInDate = getIntent().getStringExtra("checkInDate");
      //  checkOutDate = getIntent().getStringExtra("checkOutDate");
        Capacity = getIntent().getStringExtra("Capacity");
        Type = getIntent().getStringExtra("Type");


        Query query = fStore.collection("Users").whereEqualTo("emailId", emailid);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        name = documentSnapshot.getString("name");
                        phone = documentSnapshot.getString("phone");
                        uid = documentSnapshot.getString("uid");
                    }
                }

            }
        });




        recyclerView = findViewById(R.id.user_home_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);



    }



    @Override
    protected void onStart() {
        super.onStart();

        emailid = getIntent().getStringExtra("emailId");
        checkInDate = getIntent().getStringExtra("checkInDate");



        final Query query = fStore.collection("All Rooms").whereEqualTo("RCapacity", Capacity).whereEqualTo("RType", Type);

        FirestoreRecyclerOptions<Rooms> options =
                new FirestoreRecyclerOptions.Builder<Rooms>()
                        .setQuery(query, Rooms.class)
                        .build();

        FirestoreRecyclerAdapter<Rooms, RoomViewHolder> adapter = new FirestoreRecyclerAdapter<Rooms, RoomViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RoomViewHolder holder, int position, @NonNull final Rooms model) {

                holder.txtRName.setText(model.getRTitle());
                holder.txtROriginalPrice.setText(model.getROriginalPrice());
                holder.txtRDiscountPrice.setText(model.getRDiscountPrice());
                holder.txtRoffer.setText(model.getRoffer() + " %Off");
                holder.txtRStatus.setText(model.getStatus());
                Picasso.get().load(model.getRImage()).placeholder(R.drawable.loading).into(holder.imageView);


                if (model.getRid().equals(model.getRid())) {
                    fStore.collection("All Rooms").document(model.getRid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        String offerPrice = documentSnapshot.getString("RDiscountPrice");

                                        if (!offerPrice.equals("")) {
                                            holder.txtRDiscountPrice.setVisibility(View.VISIBLE);
                                            holder.crossLine.setVisibility(View.VISIBLE);
                                            holder.txtRoffer.setVisibility(View.VISIBLE);
                                            holder.txtROriginalPrice.setTextColor(Color.rgb(178, 170, 170));
                                        }

                                        final String status = documentSnapshot.getString("Status");


                                    }

                                }
                            });
                }



                if (model.getRid().equals(model.getRid()))
                {
                   String bookingId = model.getRid() + checkInDate;

                    Query query1 = fStore.collection("All Bookings").whereEqualTo("BookingID", bookingId);

                    query1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {

                                        holder.txtRStatus.setText("Booked");
                                        holder.txtRStatus.setTextColor(Color.rgb(255, 107, 107));



                            }
                            else {
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent intent = new Intent(RoomListActivity2.this, RoomDetailsActivity.class);
                                        intent.putExtra("RTitle", model.getRTitle());
                                        intent.putExtra("RCapacity", model.getRCapacity());
                                        intent.putExtra("ROriginalPrice", model.getROriginalPrice());
                                        intent.putExtra("RDiscountPrice", model.getRDiscountPrice());
                                        intent.putExtra("RDescription", model.getRDescription());
                                        intent.putExtra("RImage", model.getRImage());
                                        intent.putExtra("Status", model.getStatus());
                                        intent.putExtra("Rid", model.getRid());
                                        intent.putExtra("Roff", model.getRoffer());
                                        intent.putExtra("emailId", emailid);
                                        Log.d("TAG", "onCreate: " + emailid);
                                        intent.putExtra("CheckInDate", checkInDate);
                                        startActivity(intent);


                                    }
                                });
                            }
                        }
                    });

                }




            }

            @NonNull
            @Override
            public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_recycler_view, parent, false);
                RoomViewHolder holder = new RoomViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }



}