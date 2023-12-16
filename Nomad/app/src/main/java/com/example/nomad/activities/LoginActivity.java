package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.support.v4.app.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.nomad.R;
import com.example.nomad.services.LoginService;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;
    EditText userName;
    EditText password;
    LoginService loginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            loginService = new LoginService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_login);
        registerButton = findViewById(R.id.RegisterButton);
        loginButton = findViewById(R.id.LoginButton);
        userName = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        setupLoginButton();
        setupRegisterButton();
    }
    protected void setupLoginButton(){
        loginButton.setOnClickListener(v -> {

            try {
                    if(loginService.login(userName.getText().toString(), password.getText().toString())){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("title", "Cart");
                        startActivity(intent);
                    }

            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

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