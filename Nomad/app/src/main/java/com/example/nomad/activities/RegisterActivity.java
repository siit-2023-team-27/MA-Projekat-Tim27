package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.nomad.R;
import com.example.nomad.dto.AppUser;
import com.example.nomad.dto.UserRegistrationDTO;

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
}