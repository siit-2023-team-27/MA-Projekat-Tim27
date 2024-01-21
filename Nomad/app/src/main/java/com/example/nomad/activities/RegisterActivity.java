package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.nomad.R;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserRegistrationDTO;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Button nextButton;
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.FirstNameBox);
        lastName = findViewById(R.id.LastNameBox);
        address = findViewById(R.id.AddressBox);
        phoneNumber = findViewById(R.id.PhoneNumberBox);
        nextButton = findViewById(R.id.RegisterNextButton);
        setupNextButton();
        setUpValidation();
    }
    protected void setupNextButton(){
        nextButton.setOnClickListener(v -> {

            Intent intent = new Intent(RegisterActivity.this, CreateAccountActivity.class);
            UserRegistrationDTO user = this.constructUser();
            intent.putExtra("USER", user);
            startActivity(intent);
        });
    }
    private UserRegistrationDTO constructUser(){
        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setPhoneNumber(phoneNumber.getText().toString());
        user.setAddress(address.getText().toString());
        return user;
    }
    private void setUpValidation(){
        nextButton.setEnabled(false);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextButton.setEnabled(validate()); }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private boolean validate(){
        return firstName.getText().toString().length() > 2 && lastName.getText().toString().length() > 2  && address.getText().toString().length() > 2  &&
                phoneNumber.getText().toString().length() > 7 && Pattern.compile("-?\\d+(\\.\\d+)?").matcher(phoneNumber.getText().toString()).matches();
    }
}