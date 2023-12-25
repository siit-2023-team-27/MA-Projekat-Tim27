package com.example.nomad.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.nomad.R;
import com.example.nomad.databinding.ActivityHomeBinding;
import com.example.nomad.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;



public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActivityHomeBinding binding;
    private NavController navController;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = binding.activityHomeBase.toolbar;
        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        menu = navigationView.getMenu();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navigationView.setNavigationItemSelectedListener(this);

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.i("OVDE SAM", "ovde sam");
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        drawerLayout.close();
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}