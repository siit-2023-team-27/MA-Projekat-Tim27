package com.example.nomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerButton = findViewById(R.id.RegisterButton);
        loginButton = findViewById(R.id.LoginButton);
        setupLoginButton();
        setupRegisterButton();

    }
    protected void setupLoginButton(){
        loginButton.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("title", "Cart");
            startActivity(intent);
        });
    }
    protected void setupRegisterButton(){
        registerButton.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("title", "Cart");
            startActivity(intent);
        });
    }
}