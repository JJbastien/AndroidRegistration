package com.example.myfirstregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class RegistrationForm extends AppCompatActivity {

    Button msendInfoBtn;
    EditText mtypeEmail;
    EditText mtypePassword;
    EditText mconfirmPassword;
    Pattern passwordRegex = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    private static final String SHARED_PREFS = "shared_prefs";
    private static final String TEXT = "text";
    SharedPreferences sharedPreferences;
    ArrayList <String> emailList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        msendInfoBtn = findViewById(R.id.btnNext);
        mtypeEmail = findViewById(R.id.email);
        mtypePassword = findViewById(R.id.password);
        mconfirmPassword = findViewById(R.id.confirmPassword);
        emailList = new ArrayList<>();
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        emailList.add(mtypeEmail.getText().toString().trim());
        mtypeEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mtypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mconfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateConfirmPassword();
                enableNextButton();
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        msendInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private Boolean validateEmail(){
        String emailInput = mtypeEmail.getText().toString().trim();
        Boolean emailValidator = Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
        if(emailInput.isEmpty()){
            mtypeEmail.setError("field cannot be empty");
            return false;
        }
        else if(!emailValidator){
            mtypeEmail.setError("please enter a valid email");
            return false;
        }
        else if (emailList.contains(emailInput)){
            mtypeEmail.setError("email already in use");
            return false;
        }

        else {
            Toast.makeText(this,"email validated", Toast.LENGTH_SHORT);
            return true;}


    }
    private Boolean validatePassword(){
        String passInput = mtypePassword.getText().toString().trim();
        if(passInput.isEmpty()){
            mtypePassword.setError("field cannot be empty");
            return false;
        }
        else if(!passwordRegex.matcher(passInput).matches()){
            mtypePassword.setError("password must include at least 8 characters, at least one uppercase, one lower case and one number");
            return false;
        }

        else
            return true;


    }

    private Boolean validateConfirmPassword(){
        String passInput2 = mconfirmPassword.getText().toString().trim();
        if(passInput2.isEmpty()){
            mconfirmPassword.setError("field cannot be empty");
            return false;
        }
        else if(!passwordRegex.matcher(passInput2).matches()){
            mconfirmPassword.setError("password must include at least 8 characters, at least one uppercase, one lower case and one number");
            return false;
        }
        else return true;
    }

    private void enableNextButton(){
        if(validateEmail()  && validatePassword()  && validateConfirmPassword()){

            msendInfoBtn.setBackgroundColor(getColor(R.color.teal_700));
        }

    }

    public void saveData() {
        String emailInput = mtypeEmail.getText().toString().trim();

        emailList.add(emailInput);


        sharedPreferences.edit().putString(TEXT, emailInput ).apply();


        Toast.makeText(this, "email saved", Toast.LENGTH_SHORT);
    }

}