package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.databinding.ActivityHomeBinding;
import com.example.nomad.databinding.ActivitySplashScreenBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000;
    private Button tryAgainButton;
    private Button closeAppButton;

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        closeAppButton = findViewById(R.id.closeAppButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        closeAppButton.setVisibility(View.INVISIBLE);
        tryAgainButton.setVisibility(View.INVISIBLE);

        setButtons();

        if (isConnectedToInternet()) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DELAY);
        } else {
            Toast.makeText(this, "You are not connected to the Internet. Please connect and try again or close app.", Toast.LENGTH_LONG).show();
            tryAgainButton.setVisibility(View.VISIBLE);
            closeAppButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void setButtons() {

        closeAppButton.setOnClickListener(v -> {
            finish();
        });

        tryAgainButton.setOnClickListener(v -> {
            if (isConnectedToInternet() ){
                Intent intent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "You are not connected to the Internet. Please connect and try again or close app.", Toast.LENGTH_LONG).show();
            }
        });
    }
}