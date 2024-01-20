package com.example.nomad.activities;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.application.isradeleon.notify.Notify;
import com.example.nomad.R;
import com.example.nomad.databinding.ActivityHomeBinding;
import com.example.nomad.dto.UserDTO;
import com.example.nomad.fragments.AccommodationLocationFragment;
import com.example.nomad.fragments.CalendarFragment;
import com.example.nomad.fragments.ProfileFragment;
import com.example.nomad.helper.Consts;
import com.example.nomad.services.AuthService;
import com.example.nomad.services.NotificationService;

import com.example.nomad.services.AuthService;

import com.example.nomad.services.UserService;
import com.google.android.material.navigation.NavigationView;


import org.java_websocket.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import dev.gustavoavila.websocketclient.WebSocketClient;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActivityHomeBinding binding;
    private NavController navController;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Menu menu;
    AccommodationLocationFragment locationFragment;

    UserService userService = new UserService();
    public static NotificationService notificationService;

    UserDTO loggedUser;
    WebSocketClient webSocketClient;

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

        setNavigationMenu();
        navigationView.setNavigationItemSelectedListener(this);

        notificationService = new NotificationService(this);
        notificationService.setUpWebSocket();


    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Log.i("OVDE SAM", "ovde sam");
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//    }

    private void setNavigationMenu() {
        userService.getLoggedUser(AuthService.id);
        userService.getLogged().observe(this, new Observer<UserDTO>() {
            @Override
            public void onChanged(UserDTO userDTO) {
                loggedUser = userDTO;
                int menuResId = getMenuResourceForRole();
                navigationView.inflateMenu(menuResId);
            }
        });
    }

    private int getMenuResourceForRole() {
        switch (loggedUser.getRoles().get(0).toString()) {
            case "ADMIN":
                return R.menu.admin_menu;
            case "HOST":
                return R.menu.host_menu;
            case "GUEST":
                return R.menu.guest_menu;
            default:
                return R.menu.main_menu;
        }
    }


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
    public void click(View view){
        Log.d("AAAA", "onClick: ");
        locationFragment.click(view);

    }
    public void openCalendarFragment(View view){
        locationFragment.nextFragment();

    }

    public void setLocationFragment(AccommodationLocationFragment locationFragment) {
        this.locationFragment = locationFragment;
    }

}