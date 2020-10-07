package com.JForce.hotelreservation.l.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeUser;
    private TextView selected;
    private TextView selected1;
    private String emailid, name, phone, uid, checkInDate = "", Capacity = "", Type = "", getName ;
    private int checkInDay, checkInMonth, checkInYear, currentYear, currentMonth, currentDay;



    private Button findRoomBtn;

    DatePickerDialog picker;
    EditText checkindate;

    private Spinner spinner, spinner1;


    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fStore = FirebaseFirestore.getInstance();

        checkindate = findViewById(R.id.date_field_1);


        findRoomBtn = findViewById(R.id.find_rooms_btn);


        emailid = getIntent().getStringExtra("emailId");




        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate();
            }
        });




        selected = findViewById(R.id.no_of_guest);
        spinner = findViewById(R.id.capacity_spinner);

        final List<String> categories = new ArrayList<>();
       /* categories.add(0, "Select");*/
        categories.add(0, "  1  ");
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

                    HomeActivity.this.Capacity = selected.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(HomeActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();

            }
        });


        selected1 = findViewById(R.id.select_type);
        spinner1 = findViewById(R.id.type_spinner);

        final List<String> categories1 = new ArrayList<>();
        /*categories1.add(0, "Select");*/
        categories1.add(0, "Standard");
        categories1.add("Deluxe");
        categories1.add("Luxury");

        ArrayAdapter<String> dataAdapter1;
        dataAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories1);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Select Type")) {
                } else
                {
                    selected1.setText(adapterView.getSelectedItem().toString());

                    HomeActivity.this.Type = selected1.getText().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(HomeActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
            }
        });





        findRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailid = getIntent().getStringExtra("emailId");


                Calendar c = Calendar.getInstance();
                int Cyear = c.get(Calendar.YEAR);
                int Cmonth = c.get(Calendar.MONTH);
                int Cday = c.get(Calendar.DATE);



                currentYear = Cyear;
                currentMonth = Cmonth + 1;
                currentDay = Cday;

                Log.d("TAG", "onClick: current Year " + currentMonth);
                Log.d("TAG", "onClick: check In year " + checkInMonth);


                if (!checkInDate.equals("")) {
                    if (!Capacity.equals("")) {

                        if (!Type.equals("")) {

                            if ((checkInYear >= currentYear)) {
                                if ((checkInMonth >= currentMonth) && checkInYear == currentYear)
                                {
                                    if ((checkInDay >= currentDay) && (checkInMonth == currentMonth))
                                    {
                                        Intent intent = new Intent(HomeActivity.this, RoomListActivity2.class);
                                        intent.putExtra("checkInDate", checkInDate);
                                        intent.putExtra("Capacity", Capacity);
                                        intent.putExtra("Type", Type);
                                        intent.putExtra("emailId", emailid);
                                        startActivity(intent);
                                    }
                                    else if (checkInDay > currentDay )
                                    {
                                        Intent intent = new Intent(HomeActivity.this, RoomListActivity2.class);
                                        intent.putExtra("checkInDate", checkInDate);
                                        intent.putExtra("Capacity", Capacity);
                                        intent.putExtra("Type", Type);
                                        intent.putExtra("emailId", emailid);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        pickdate();

                                        Toast.makeText(HomeActivity.this, "You can not select Past date", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (checkInYear > currentYear)
                                {
                                    Intent intent = new Intent(HomeActivity.this, RoomListActivity2.class);
                                    intent.putExtra("checkInDate", checkInDate);
                                    intent.putExtra("Capacity", Capacity);
                                    intent.putExtra("Type", Type);
                                    intent.putExtra("emailId", emailid);
                                    startActivity(intent);
                                }
                                else
                                {
                                    pickdate();

                                    Toast.makeText(HomeActivity.this, "You can not select Past date", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                pickdate();

                                Toast.makeText(HomeActivity.this, "You can not select Past date", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "Please Select Room Type", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Select Number of Guests", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Please Select Check in Date", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void pickdate() {

        final Calendar cldr = Calendar.getInstance();
        final int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        final int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(HomeActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        checkindate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        checkindate.setTextColor(Color.rgb(255, 255, 255));


                        checkInYear = year;
                        checkInMonth = monthOfYear + 1;
                        checkInDay = dayOfMonth;

                        HomeActivity.this.checkInDate = checkindate.getText().toString();

                    }
                }, year, month, day);
        picker.show();



    }


    @Override
    protected void onStart() {
        super.onStart();


        emailid = getIntent().getStringExtra("emailId");

        Query query = fStore.collection("Users").whereEqualTo("emailId", emailid);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        name = documentSnapshot.getString("name");
                        phone = documentSnapshot.getString("phone");
                        uid = documentSnapshot.getString("uid");

                        welcomeUser = findViewById(R.id.welcome_user_txt);
                        welcomeUser.setText(name);
                        welcomeUser.setTextColor(Color.rgb(255,255,0));
                    }
                }

            }
        });





    }














    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    }






