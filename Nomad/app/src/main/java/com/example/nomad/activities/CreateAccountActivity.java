package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.nomad.R;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.enums.UserType;
import com.example.nomad.services.AuthService;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {
    Button createAccountButton;
    UserRegistrationDTO user;
    EditText userNameBox;
    EditText passwordBox;
    EditText passwordConfirmBox;
    Switch roleSwitch;
    AuthService authService = new AuthService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAccountButton = findViewById(R.id.CreateAccountButton);
        userNameBox = findViewById(R.id.UserNameBox);
        passwordBox = findViewById(R.id.PasswordBox);
        passwordConfirmBox = findViewById(R.id.PasswordConfirmBox);
        roleSwitch = findViewById(R.id.switch1);
        this.user = (UserRegistrationDTO) getIntent().getSerializableExtra("USER");

        setupCreateAccountButton();
    }
    protected void setupCreateAccountButton(){
        createAccountButton.setOnClickListener(v -> {
            constructUser();
            authService.register(user);
            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);

            startActivity(intent);
        });
    }
    private void constructUser(){
        user.setUsername(userNameBox.getText().toString());
        user.setPassword(passwordBox.getText().toString());
        user.setPasswordConfirmation(passwordConfirmBox.getText().toString());
        ArrayList<UserType> roles = new ArrayList<>();
        if (roleSwitch.isChecked()){
            roles.add(UserType.HOST);
        }else{
            roles.add(UserType.GUEST);
        }
        user.setRoles(roles);
    }
}