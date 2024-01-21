package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.dto.UserRegistrationDTO;
import com.example.nomad.enums.UserType;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.IAuthListener;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity implements IAuthListener {
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
        authService.subScribe(this);
        setupCreateAccountButton();
        setUpValidation();
    }
    protected void setupCreateAccountButton(){
        createAccountButton.setOnClickListener(v -> {
            constructUser();
            authService.register(user);
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);

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
    private void setUpValidation(){
        createAccountButton.setEnabled(false);
        userNameBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createAccountButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        passwordBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createAccountButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        passwordConfirmBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createAccountButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private boolean validate(){
        return !userNameBox.getText().toString().isEmpty() && !passwordBox.getText().toString().isEmpty() && !passwordConfirmBox.getText().toString().isEmpty() &&
                passwordBox.getText().toString().equals(passwordConfirmBox.getText().toString()) ;
    }

    @Override
    public void registerFailed() {
        Toast.makeText(getApplicationContext(),"Username already exists",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loginFailed() {

    }
}