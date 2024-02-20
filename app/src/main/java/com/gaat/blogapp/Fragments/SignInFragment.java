package com.gaat.blogapp.Fragments;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gaat.blogapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInFragment extends Fragment {
    private View view;
    private TextInputLayout layoutEmail,layoutPassword;
    private TextInputEditText txtEmail,txtPassword;
    private TextView txtSignUp;
    private Button btnSignIn;

    public SignInFragment(){}

    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstance){
        view = inflater.inflate(R.layout.layout_sign_in,container,false);
        init();
        return view;
    }

    private void init(){
        layoutEmail=view.findViewById(R.id.txtLayoutEmailSignIn);
        layoutPassword=view.findViewById(R.id.txtLayoutPasswordSignIn);
        txtEmail=view.findViewById(R.id.txtEmailSignIn);
        txtPassword=view.findViewById(R.id.txtPasswordSignIn);
        txtSignUp=view.findViewById(R.id.txtSignUp);
        btnSignIn=view.findViewById(R.id.btnSignIn);

        txtSignUp.setOnClickListener(v->{
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignUpFragment()).commit();
        });
        btnSignIn.setOnClickListener(v->{
            //validate  fields  first
            if(validate()){

            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPassword.getText().toString().length()>7){
                  layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validate(){
        if(txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if(txtPassword.getText().toString().length() < 8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characteres");
            return false;
        }
        return true;
    }
}
