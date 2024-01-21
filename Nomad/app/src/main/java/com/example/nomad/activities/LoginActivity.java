package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.support.v4.app.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.LoginDTO;
import com.example.nomad.services.AccommodationService;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.IAuthListener;


import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity implements IAuthListener {
    Button registerButton;
    Button loginButton;
    EditText userName;
    EditText password;
    AuthService authService = new AuthService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        registerButton = findViewById(R.id.RegisterButton);
        loginButton = findViewById(R.id.LoginButton);
        userName = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        setupLoginButton();
        setupRegisterButton();
        authService.subScribe(this);

    }
    protected void setupLoginButton(){
        loginButton.setOnClickListener(v -> {

            authService.login(new LoginDTO(userName.getText().toString(), password.getText().toString()));


        });
    }
    public void loginSuccess(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    protected void setupRegisterButton(){
        registerButton.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void registerFailed() {

    }

    @Override
    public void loginFailed() {
        Toast.makeText(getApplicationContext(), "Pogresno korisnicko ime i lozinka", Toast.LENGTH_SHORT).show();
    }
}