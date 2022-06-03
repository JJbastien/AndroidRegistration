package com.example.myfirstregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mRegistration = findViewById(R.id.btnRegister);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getBaseContext(), RegistrationForm.class);
                startActivity(mIntent);
                Log.d("TAG", "onResume: onresume");
            }
        });

    }
}