package com.gaat.blogapp.Fragments;

import static com.gaat.blogapp.Constant.LOGIN;
import static com.gaat.blogapp.Constant.REGISTER;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gaat.blogapp.AuthActivity;
import com.gaat.blogapp.HomeActivity;
import com.gaat.blogapp.R;
import com.gaat.blogapp.UserInfoActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    private View view;
    private TextInputLayout layoutEmail,layoutPassword,layoutConfirmPassword;
    private TextInputEditText txtEmail,txtPassword,txtConfirmPassword;
    private TextView txtSignIn;
    private Button btnSignUp;
    private ProgressDialog dialog;

    public SignUpFragment(){}

    //criando view e atribuindo a ela o cadastro
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstance){
        view = inflater.inflate(R.layout.layout_sign_up,container,false);
        init();
        return view;
    }

    //iniciando componentes
    private void init(){
        layoutEmail=view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutPassword=view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutConfirmPassword=view.findViewById(R.id.txtLayoutPasswordSignUp);
        txtEmail=view.findViewById(R.id.txtEmailSignUp);
        txtPassword=view.findViewById(R.id.txtPasswordSignUp);
        txtConfirmPassword=view.findViewById(R.id.txtConfirmPasswordSignUp);
        txtSignIn=view.findViewById(R.id.txtSignIn);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        dialog= new ProgressDialog(getContext());
        dialog.setCancelable(false);

        //evento de ir para o login
        txtSignIn.setOnClickListener(v->{
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new SignInFragment()).commit();
        });
        //evento para validação e se passa se cadastrar
        btnSignUp.setOnClickListener(v->{
            //validate  fields  first
            if(validate()){
                register();
            }
        });

        //validação de campo de email a partir do TextWatcher
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

        //validação de campo de password a partir do TextWatcher
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txtPassword.getText().toString().length()>3){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //validação de campo de confirmPassword a partir do TextWatcher
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
        //seta mensagem e mostra,assim que começa a validar
        dialog.setMessage("Registering");
        dialog.show();
        if(txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if(txtPassword.getText().toString().length() < 3){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 3 characteres");
            return false;
        }
        if(!txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())){
            layoutConfirmPassword.setErrorEnabled(true);
            layoutConfirmPassword.setError("Password does not match");
            dialog.dismiss();
            return false;
        }
        return true;
    }

    private void register(){
        //variavel de request que vai enviar parametros do tipo post para url register
        StringRequest request=new StringRequest(Request.Method.POST,REGISTER, response->{
            //ve got response if connection success

            try {
                //iniciando objeto json
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){
                    //iniciando objeto json user se a conexão com api for um sucesso
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    //putString é para adicionar valores ao objeto
                    editor.putString("token",object.getString("token"));
                    editor.putString("name",user.getString("name"));
                    editor.putString("lastName",user.getString("lastName"));
                    editor.putString("photo",user.getString("photo"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    //if success,show short message
                    startActivity(new Intent(((AuthActivity)getContext()), UserInfoActivity.class));
                    ((AuthActivity)getContext()).finish();
                    Toast.makeText(getContext(),"Register success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Register failed",Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            dialog.dismiss();
        },error->{
            //error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }){
            //add parameters
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> map =new HashMap<>();
                map.put("email",txtEmail.getText().toString().trim());
                map.put("password",txtPassword.getText().toString());
                return map;
            }
        };
        // add this  request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
