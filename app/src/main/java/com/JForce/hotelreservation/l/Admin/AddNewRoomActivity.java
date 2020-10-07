package com.JForce.hotelreservation.l.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.anstrontechnologies.corehelper.AnstronCoreHelper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.grpc.Compressor;

public class AddNewRoomActivity extends AppCompatActivity {

    private EditText RTitle, RCapacity, ROriginalPrice, RDiscountPrice, RDescription;
    private Button AddNewRoomBtn;
    private ImageView InputRoomImage;
    private String RofferString = "", downloadImageUrl, saveCurrentDate, saveCurrentTime, roomRandomKey,  Rdiscountprice = "", Roriginaprice = "";
    private TextView selected, selected1;


    private static final int GalleryPick = 1;
    private Uri ImageUri;

    private FirebaseFirestore fStore;
    private StorageReference RoomImageRef;
    AnstronCoreHelper coreHelper;
    private StorageTask uploadTask;

    private ProgressDialog loadingBar;

    private Compressor compressedImage;
    private Spinner spinner, spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);


        selected = findViewById(R.id.room_capacity);
        spinner = findViewById(R.id.capacity_spinner);

        final List<String> categories = new ArrayList<>();
        categories.add(0, "Capacity");
        categories.add("  1  ");
        categories.add("  2  ");
        categories.add("  3  ");
        categories.add("  4  ");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Select Category")) {
                } else
                {
                    selected.setText(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddNewRoomActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();

            }
        });




        selected1 = findViewById(R.id.room_type);
        spinner1 = findViewById(R.id.type_spinner);

        final List<String> categories1 = new ArrayList<>();
        categories1.add(0, "Type");
        categories1.add("Standard");
        categories1.add("Deluxe");
        categories1.add("Luxury");

        ArrayAdapter<String> dataAdapter1;
        dataAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories1);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Select Category")) {
                } else
                {
                    selected1.setText(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddNewRoomActivity.this, "Please Select type", Toast.LENGTH_SHORT).show();

            }
        });






        RTitle = findViewById(R.id.room_title);
        ROriginalPrice = findViewById(R.id.original_price);
        RDiscountPrice = findViewById(R.id.discount_price);
        RDescription = findViewById(R.id.room_description);
        InputRoomImage =findViewById(R.id.select_room_image);
        AddNewRoomBtn = findViewById(R.id.add_new_room_btn);


        fStore = FirebaseFirestore.getInstance();

        RoomImageRef = FirebaseStorage.getInstance().getReference().child("Room Images");

        coreHelper = new AnstronCoreHelper(this);

        loadingBar = new ProgressDialog(this);



        InputRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity(ImageUri)
                        .setAspectRatio(95, 100)
                        .start(AddNewRoomActivity.this);

            }
        });


        AddNewRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateProductData();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            ImageUri = result.getUri();

            InputRoomImage.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData() {

        Roriginaprice = ROriginalPrice.getText().toString();
        Rdiscountprice = RDiscountPrice.getText().toString();

        if (ImageUri != null) {
            if (!TextUtils.isEmpty(RTitle.getText().toString())) {
                if (!TextUtils.isEmpty(selected.getText().toString()))
                {
                    if (!TextUtils.isEmpty(selected1.getText().toString()))
                    {
                    if (!TextUtils.isEmpty(Roriginaprice))
                    {
                        if (!TextUtils.isEmpty(Rdiscountprice))
                        {
                            if (!TextUtils.isEmpty(RDescription.getText().toString()))
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
                        Toast.makeText(this, "Please select type", Toast.LENGTH_SHORT).show();
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
        }else
        {
            Toast.makeText(this, "Product Image Mandatory...", Toast.LENGTH_SHORT).show();
        }

    }






    private void StoreRoomInformation() {

        loadingBar.setTitle("Adding New Room");
        loadingBar.setMessage("Please wait....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("  hh:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        roomRandomKey = saveCurrentDate + saveCurrentTime ;


        if (ImageUri != null) {

            File actualImage = new File(ImageUri.getPath());

            try {

                final Bitmap compressedImage = new id.zelory.compressor.Compressor(this)
                        .setMaxWidth(320)
                        .setMaxHeight(400)
                        .setQuality(15)
                        .compressToBitmap(actualImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                compressedImage.compress(Bitmap.CompressFormat.JPEG, 15, baos);
                byte[] ImageUri = baos.toByteArray();


                final StorageReference filePath = RoomImageRef.child(ImageUri.getClass() + roomRandomKey + ".jpg");

                uploadTask = filePath.putBytes(ImageUri);


                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUrl = task.getResult();
                                    downloadImageUrl = downloadUrl.toString();


                                    SaveRoomInfoToDatabase();

                                }
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private void SaveRoomInfoToDatabase() {


        HashMap<String, Object> roomMap = new HashMap<>();
        roomMap.put("Rid", roomRandomKey);
        roomMap.put("RTitle", RTitle.getText().toString());
        roomMap.put("RImage", downloadImageUrl);
        roomMap.put("RCapacity", selected.getText().toString());
        roomMap.put("RType", selected1.getText().toString());
        roomMap.put("ROriginalPrice", ROriginalPrice.getText().toString());
        roomMap.put("RDiscountPrice", RDiscountPrice.getText().toString());
        roomMap.put("RDescription", RDescription.getText().toString());
        roomMap.put("Roffer", RofferString);
        roomMap.put("Status", "Available");
        roomMap.put("LastUpdated", saveCurrentDate);



        fStore.collection("All Rooms").document(roomRandomKey).set(roomMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            loadingBar.dismiss();
                            Toast.makeText(AddNewRoomActivity.this, "Room is added Successfully..", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddNewRoomActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            finish();



                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddNewRoomActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        }


                    }
                });






    }


}

