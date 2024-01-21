package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.nomad.R;

public class WelcomeActivity extends AppCompatActivity {

    Button registerButton;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        registerButton = findViewById(R.id.RegisterButton);
        loginButton = findViewById(R.id.LoginButton);

        setupLoginButton();
        setupRegisterButton();
    }

    protected void setupRegisterButton () {
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    protected void setupLoginButton () {
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }


}