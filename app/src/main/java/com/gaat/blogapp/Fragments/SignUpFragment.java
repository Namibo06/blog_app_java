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

public class SignUpFragment extends Fragment {
    private View view;
    private TextInputLayout layoutEmail,layoutPassword,layoutConfirmPassword;
    private TextInputEditText txtEmail,txtPassword,txtConfirmPassword;
    private TextView txtSignIn;
    private Button btnSignUp;

    public SignUpFragment(){}

    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstance){
        view = inflater.inflate(R.layout.layout_sign_up,container,false);
        init();
        return view;
    }

    private void init(){
        layoutEmail=view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutPassword=view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutConfirmPassword=view.findViewById(R.id.txtLayoutPasswordSignUp);
        txtEmail=view.findViewById(R.id.txtEmailSignUp);
        txtPassword=view.findViewById(R.id.txtPasswordSignUp);
        txtConfirmPassword=view.findViewById(R.id.txtConfirmPasswordSignUp);
        txtSignIn=view.findViewById(R.id.txtSignIn);
        btnSignUp=view.findViewById(R.id.btnSignUp);

        txtSignIn.setOnClickListener(v->{
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
        });
        btnSignUp.setOnClickListener(v->{
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

        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())){
                    layoutConfirmPassword.setErrorEnabled(false);
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
        if(!txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())){
            layoutConfirmPassword.setErrorEnabled(true);
            layoutConfirmPassword.setError("Password does not match");
            return false;
        }
        return true;
    }
}
