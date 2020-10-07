package com.JForce.hotelreservation.l.Users;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.JForce.hotelreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }



    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;

    private EditText name;
    private EditText phone;
    private EditText emailId;
    private EditText password;

    private Button signUpBtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";





    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        alreadyHaveAccount = view.findViewById(R.id.already_have_an_account);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);

        name = view.findViewById(R.id.name_field);
        phone = view.findViewById(R.id.phone_field);
        emailId = view.findViewById(R.id.emailId_field);
        password = view.findViewById(R.id.password_field);
        signUpBtn = view.findViewById(R.id.sign_up_btn);
        progressBar = view.findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SigninFragment());
            }
        });


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
                

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }

    private void checkInputs() {

        if (!TextUtils.isEmpty(name.getText().toString()))
        {
            if (!TextUtils.isEmpty(phone.getText().toString()))
            {
                if (!TextUtils.isEmpty(emailId.getText().toString()))
                {
                    if(!TextUtils.isEmpty(password.getText().toString()))
                    {
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                        signUpBtn.setBackgroundColor(Color.rgb(123, 0, 176));
                        
                    }
                    else
                    {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.rgb(204, 204, 204));
                        signUpBtn.setBackgroundColor(Color.rgb(242, 241, 241));
                    }
                    
                }
                else
                {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.rgb(204, 204, 204));
                    signUpBtn.setBackgroundColor(Color.rgb(242, 241, 241));
                }
            }
            else
            {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.rgb(204, 204, 204));
                signUpBtn.setBackgroundColor(Color.rgb(242, 241, 241));
            }
        }
        else 
        {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.rgb(204, 204, 204));
            signUpBtn.setBackgroundColor(Color.rgb(242, 241, 241));
        }

    }

    private void checkEmailAndPassword() {
        if (emailId.getText().toString().matches(emailPattern))
        {
            if (phone.getText().length() == 10) {

                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.rgb(204, 204, 204));
                signUpBtn.setBackgroundColor(Color.rgb(242, 241, 241));

                firebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    final String uid = firebaseAuth.getUid();

                                    HashMap<String, Object> userdataMap = new HashMap<>();
                                    userdataMap.put("name", name.getText().toString());
                                    userdataMap.put("phone", phone.getText().toString());
                                    userdataMap.put("uid", uid);
                                    userdataMap.put("emailId", emailId.getText().toString());
                                    userdataMap.put("password", password.getText().toString());


                                    fStore.collection("Users").document(uid).set(userdataMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                                        intent.putExtra("Uid", uid);
                                                        intent.putExtra("emailId", emailId.getText().toString());
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }
                                                }
                                            });
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signUpBtn.setEnabled(true);
                                    signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "Error" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else
            {
                phone.setError("Invalid phone");
            }
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            signUpBtn.setEnabled(true);
            signUpBtn.setTextColor(Color.rgb(255, 255, 255));
            emailId.setError("Invalid Email");
        }

    }


}