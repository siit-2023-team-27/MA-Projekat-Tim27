package com.example.nomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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

            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
            intent.putExtra("title", "Cart");
            startActivity(intent);
        });
    }
}