package com.JForce.hotelreservation.l.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminEditRoomActivity extends AppCompatActivity {

    private EditText maintainRTitle, maintainRCapacity, maintainROriginalPrice, maintainRDiscountPrice, maintainRDescription;
    private Button RUpdate, deleteRoom;
    private ImageView maintainRImage;
    private String Rdiscountprice = "", Roriginaprice = "", RofferString = "", Rid, RTitle, RCapacity, ROriginalPrice, RDiscountPrice, RDescription, saveCurrentDate, saveCurrentTime;


    private FirebaseFirestore fStore;
    private StorageReference RoomImageRef;

    private ProgressBar progressBar;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_room);



        maintainRTitle = findViewById(R.id.maintain_room_title);
        maintainRCapacity = findViewById(R.id.maintain_room_capacity);
        maintainROriginalPrice = findViewById(R.id.maintain_original_price);
        maintainRDiscountPrice = findViewById(R.id.maintain_discount_price);
        maintainRDescription = findViewById(R.id.maintain_room_description);
        maintainRImage = findViewById(R.id.maintain_select_room_image);

        RUpdate = findViewById(R.id.update_room_btn);
        deleteRoom = findViewById(R.id.delete_room_btn);


        fStore = FirebaseFirestore.getInstance();
        loadingBar = new ProgressDialog(this);



        RoomImageRef = FirebaseStorage.getInstance().getReference().child("Room Images");


        final String imageref = getIntent().getStringExtra("RImage");
        Rid = getIntent().getStringExtra("Rid");


        RTitle = getIntent().getStringExtra("RTitle");
        RCapacity = getIntent().getStringExtra("RCapacity");
        ROriginalPrice = getIntent().getStringExtra("ROriginalPrice");
        RDiscountPrice = getIntent().getStringExtra("RDiscountPrice");
        RDescription = getIntent().getStringExtra("RDescription");




        displayRoomInfo(RTitle, RCapacity, ROriginalPrice, RDiscountPrice, RDescription, imageref);



        deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomImageRef = FirebaseStorage.getInstance().getReference().child("Product Images").child(imageref);

                CharSequence options[] = new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminEditRoomActivity.this);
                builder.setTitle("Delete Product");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {

                            fStore.collection("All Rooms").document(Rid).delete();
                            RoomImageRef.delete();

                            Toast.makeText(AdminEditRoomActivity.this, "Product deleted successfully...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminEditRoomActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            finish();
                        }
                    }
                });
                builder.show();

            }
        });



        RUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChanges();
            }
        });


    }

   

    private void displayRoomInfo(String rTitle, String rCapacity, String rOriginalPrice, String rDiscountPrice, String rDescription, String rImage) {


        maintainRTitle.setText(rTitle);
        maintainRCapacity.setText(rCapacity);
        maintainROriginalPrice.setText(rOriginalPrice);
        maintainRDiscountPrice.setText(rDiscountPrice);
        maintainRDescription.setText(rDescription);
        Picasso.get().load(rImage).placeholder(R.drawable.loading).into(maintainRImage);



    }



    private void applyChanges() {


        Roriginaprice = maintainROriginalPrice.getText().toString();
        Rdiscountprice = maintainRDiscountPrice.getText().toString();


            if (!TextUtils.isEmpty(maintainRTitle.getText().toString())) {
                if (!TextUtils.isEmpty(maintainRCapacity.getText().toString()))
                {
                    if (!TextUtils.isEmpty(Roriginaprice))
                    {
                        if (!TextUtils.isEmpty(Rdiscountprice))
                        {
                            if (!TextUtils.isEmpty(maintainRDescription.getText().toString()))
                            {
                                if (Integer.parseInt(Rdiscountprice) < (Integer.parseInt(Roriginaprice))) {

                                    float disc = (Integer.parseInt(Rdiscountprice));
                                    float ori = (Integer.parseInt(Roriginaprice));


                                    int Roffer = (int) (100 - ((disc / ori) * 100));


                                    RofferString = String.valueOf(Roffer);

                                    StoreRoomInformation();
                                }
                                else
                                {
                                    Toast.makeText(this, "Discount Price must be less than Original Price", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(this, "Enter Room Description", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            StoreRoomInformation();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Please put Original Price", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(this, "Please Select Capacity", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(this, "Please Put Room Title", Toast.LENGTH_SHORT).show();
            }


    }

    private void StoreRoomInformation() {

        loadingBar.setTitle("Updating Room");
        loadingBar.setMessage("Please wait....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("  hh:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());



        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("Rid", Rid);
        productMap.put("RTitle", maintainRTitle.getText().toString());
        productMap.put("RCapacity", maintainRCapacity.getText().toString());
        productMap.put("ROriginalPrice", Roriginaprice);
        productMap.put("RDiscountPrice", Rdiscountprice);
        productMap.put("RDescription", maintainRDescription.getText().toString());
        productMap.put("Roffer", RofferString);
        productMap.put("Status", "Available");
        productMap.put("LastUpdated", saveCurrentDate);



        fStore.collection("All Rooms").document(Rid).update(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {

                            loadingBar.dismiss();
                            Toast.makeText(AdminEditRoomActivity.this, "Room Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminEditRoomActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            finish();


                        }

                    }
                });



    }


}



