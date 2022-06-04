package com.example.myfirstregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistrationForm extends AppCompatActivity {
// declaring varialbles ;
    ImageView mcheck1;
    ImageView mcheck2;
    ImageView mcheck3;
    ImageView mx1;
    ImageView mx2;
    ImageView mx3;
    Button msendInfoBtn;
    EditText mtypeEmail;
    EditText mtypePassword;
    EditText mconfirmPassword;

    // declaring and initializing Regular express for the password validation;
    Pattern passwordRegex = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    private static final String SHARED_PREFS = "shared_prefs";
    private static final String TEXT = "text";
    private TextView mcreateLabel;
    // array list to save different email address in the shared preferences.
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
        mcreateLabel = findViewById(R.id.labelCreate);
        mcheck1 = findViewById(R.id.check1);
        mcheck2 = findViewById(R.id.check2);
        mcheck3 = findViewById(R.id.check3);
        mx3= findViewById(R.id.x3);
        mx2 = findViewById(R.id.x2);
        mx1 = findViewById(R.id.x1);


    }
    //the methods and functions are being called in the onResume methods
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
        msendInfoBtn.setOnClickListener(v -> {
        saveData();
        Intent mIntent = new Intent(getBaseContext(), Landing.class);
        startActivity(mIntent);

        });
    }
    public Boolean validateEmail() {
        String emailInput = mtypeEmail.getText().toString().trim();
        boolean emailValidator = Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
        if (emailInput.isEmpty()) {
            mtypeEmail.setError("field cannot be empty");
            return false;
        } else if (!emailValidator) {
            mtypeEmail.setError("please enter a valid email");
            return false;
        } else if (emailList.contains(emailInput)) {
            mtypeEmail.setError("email already in use");
            return false;
        } else {
            mcheck1.setVisibility(View.VISIBLE);
            return true;
        }
    }

    //password validation function
    public Boolean validatePassword(){
        String passInput = mtypePassword.getText().toString().trim();
        if(passInput.isEmpty()){
            mtypePassword.setError("field cannot be empty");
            return false;
        }
        else if(!passwordRegex.matcher(passInput).matches()){
            mtypePassword.setError("password must include at least 8 characters, at least one uppercase, one lower case and one number");
            return false;
        }

        else{
            mcheck2.setVisibility(View.VISIBLE);
            return true;}

    }
        //function to confirm password
    private Boolean validateConfirmPassword(){
        String passInput2 = mconfirmPassword.getText().toString().trim();
        String passInput = mtypePassword.getText().toString().trim();
        if(passInput2.isEmpty()){
            mconfirmPassword.setError("field cannot be empty");
            return false;
        }
        else if(!passwordRegex.matcher(passInput2).matches()){
            mconfirmPassword.setError("password must include at least 8 characters, at least one uppercase, one lower case and one number");
            return false;
        }
        else if(!passInput2.equals(passInput)){
            mconfirmPassword.setError("Passwords must match, please confirm password");
            return false;
        }
        else {
            mcheck3.setVisibility(View.VISIBLE);
            return true;
        }
    }

    private void enableNextButton(){
        if(validateEmail()  && validatePassword()  && validateConfirmPassword()){

            msendInfoBtn.setBackgroundColor(getColor(R.color.teal_700));
        }

    }
        //function called on click to save the email to sharedPreference.
    public void saveData() {
        String emailInput = mtypeEmail.getText().toString().trim();

        emailList.add(emailInput);


        sharedPreferences.edit().putString(TEXT, emailInput ).apply();


        Toast.makeText(this, "email saved", Toast.LENGTH_SHORT).show();
    }
    public void clearData(){
        mtypeEmail.getText().clear();
        mtypePassword.getText().clear();
        mconfirmPassword.getText().clear();
    }
}