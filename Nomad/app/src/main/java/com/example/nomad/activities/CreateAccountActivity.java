package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.nomad.R;

public class CreateAccountActivity extends AppCompatActivity {
    Button createAccountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAccountButton = findViewById(R.id.CreateAccountButton);
        setupCreateAccountButton();
    }
    protected void setupCreateAccountButton(){
        createAccountButton.setOnClickListener(v -> {

            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
            intent.putExtra("title", "Cart");
            startActivity(intent);
        });
    }
}