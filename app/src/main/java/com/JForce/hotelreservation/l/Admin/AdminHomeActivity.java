package com.JForce.hotelreservation.l.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

public class AdminHomeActivity extends AppCompatActivity {

    private Button addNewRoom, RBookings;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);



        addNewRoom = findViewById(R.id.add_new_room_btn);
        RBookings = findViewById(R.id.booking_btn);

        fStore = FirebaseFirestore.getInstance();


        addNewRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AddNewRoomActivity.class);
                startActivity(intent);
            }
        });

        RBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, RoomBookingsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.admin_home_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query = fStore.collection("All Rooms");

        FirestoreRecyclerOptions<Rooms> options =
                new FirestoreRecyclerOptions.Builder<Rooms>()
                        .setQuery(query, Rooms.class)
                        .build();

        FirestoreRecyclerAdapter<Rooms, RoomViewHolder> adapter = new FirestoreRecyclerAdapter<Rooms, RoomViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RoomViewHolder holder, int position, @NonNull final Rooms model) {

                holder.txtRName.setText(model.getRTitle());
                holder.txtROriginalPrice.setText("Rs. " +model.getROriginalPrice());
                holder.txtRDiscountPrice.setText(model.getRDiscountPrice());
                holder.txtRoffer.setText(model.getRoffer()+ " %Off");
                holder.txtRType.setText(model.getRType());
                holder.txtlastUpdated.setText("Last Updated on "+model.getLastUpdated());
                Picasso.get().load(model.getRImage()).placeholder(R.drawable.loading).into(holder.imageView);


                if (model.getRid().equals(model.getRid()))
                {
                    fStore.collection("All Rooms").document(model.getRid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(final DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists())
                                    {
                                        String offerPrice = documentSnapshot.getString("RDiscountPrice");

                                        if (!offerPrice.equals(""))
                                        {
                                            holder.txtRDiscountPrice.setVisibility(View.VISIBLE);
                                            holder.crossLine.setVisibility(View.VISIBLE);
                                            holder.txtRoffer.setVisibility(View.VISIBLE);
                                        }




                                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {


                                                    Intent intent = new Intent(AdminHomeActivity.this, AdminEditRoomActivity.class);
                                                    intent.putExtra("RTitle", model.getRTitle());
                                                    intent.putExtra("RCapacity", model.getRCapacity());
                                                    intent.putExtra("ROriginalPrice", model.getROriginalPrice());
                                                    intent.putExtra("RDiscountPrice", model.getRDiscountPrice());
                                                    intent.putExtra("RDescription", model.getRDescription());
                                                    Log.d("TAG", "onClick: " + model.getRDescription());
                                                    intent.putExtra("RImage", model.getRImage());
                                                    intent.putExtra("Rid", model.getRid());
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

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_recycler_view, parent, false);
                RoomViewHolder holder = new RoomViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }





}