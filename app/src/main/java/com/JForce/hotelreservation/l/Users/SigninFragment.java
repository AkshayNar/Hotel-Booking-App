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
import com.JForce.hotelreservation.l.Admin.AdminHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SigninFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAccount;
    private FrameLayout parentFrameLayout;
    private Button LoginBtn;
    private EditText emailId, password;

    private TextView iamAdmin;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
        View view =  inflater.inflate(R.layout.fragment_signin, container, false);
        dontHaveAccount = view.findViewById(R.id.donthaveaccount_txt);
        parentFrameLayout = getActivity().findViewById(R.id.register_frame_layout);


        emailId = view.findViewById(R.id.emailId_field);
        password = view.findViewById(R.id.password_field);

        LoginBtn = view.findViewById(R.id.log_in_btn);

        progressBar = view.findViewById(R.id.progressBar);


        iamAdmin = view.findViewById(R.id.i_am_admin);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignupFragment());
            }
        });

        iamAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AdminHomeActivity.class);
                startActivity(intent);
                getActivity().finish();
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




        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginWithEmailandPassword();

            }
        });

    }


    private void checkInputs() {


                if (!TextUtils.isEmpty(emailId.getText().toString()))
                {
                    if(!TextUtils.isEmpty(password.getText().toString()))
                    {
                        LoginBtn.setEnabled(true);
                        LoginBtn.setTextColor(Color.rgb(255, 255, 255));
                        LoginBtn.setBackgroundColor(Color.rgb(123, 0, 176));

                    }
                    else
                    {
                        LoginBtn.setEnabled(false);
                        LoginBtn.setTextColor(Color.rgb(204, 204, 204));
                        LoginBtn.setBackgroundColor(Color.rgb(242, 241, 241));
                    }

                }
                else
                {
                    LoginBtn.setEnabled(false);
                    LoginBtn.setTextColor(Color.rgb(204, 204, 204));
                    LoginBtn.setBackgroundColor(Color.rgb(242, 241, 241));
                }


    }




    private void loginWithEmailandPassword() {
        if (emailId.getText().toString().matches(emailPattern))
        {
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(emailId.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.putExtra("emailId", emailId.getText().toString());
                                startActivity(intent);
                                getActivity().finish();

                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), ""+ error , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
        else
        {

            LoginBtn.setEnabled(true);
            LoginBtn.setTextColor(Color.rgb(204, 204, 204));
            LoginBtn.setBackgroundColor(Color.rgb(123, 0, 176));

            Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_SHORT).show();
        }



    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();

    }






}