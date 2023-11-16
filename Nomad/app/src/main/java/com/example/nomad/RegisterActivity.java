package com.example.nomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nextButton = findViewById(R.id.RegisterNextButton);
        setupNextButton();
    }
    protected void setupNextButton(){
        nextButton.setOnClickListener(v -> {

            Intent intent = new Intent(RegisterActivity.this, CreateAccountActivity.class);
            intent.putExtra("title", "Cart");
            startActivity(intent);
        });
    }
}