package com.example.campuseventapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.campuseventapp.fragments.HomeFragment;
import com.example.campuseventapp.fragments.EventsFragment;
import com.example.campuseventapp.fragments.RegistrationsFragment;
import com.example.campuseventapp.fragments.VolunteerFragment;
import com.example.campuseventapp.fragments.ProfileFragment;
import com.example.campuseventapp.fragments.AdminFragment;
import com.example.campuseventapp.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    
    private SessionManager sessionManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setContentView(R.layout.activity_main);
        
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        
        // If user is admin, we might want to swap one of the tabs to AdminFragment, or just add logic.
        // For simplicity, let's keep the menu same, but if role is ADMIN, "Volunteer" tab could become "Admin", or we can just navigate admins to AdminFragment for the Volunteer tab.
        // Or we can programmatically change the menu item title for Admins.
        if ("ADMIN".equals(sessionManager.getRole())) {
            bottomNavigationView.getMenu().findItem(R.id.nav_volunteer).setTitle("Admin");
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_events) {
                    selectedFragment = new EventsFragment();
                } else if (itemId == R.id.nav_registrations) {
                    selectedFragment = new RegistrationsFragment();
                } else if (itemId == R.id.nav_volunteer) {
                    if ("ADMIN".equals(sessionManager.getRole())) {
                        selectedFragment = new AdminFragment();
                    } else {
                        selectedFragment = new VolunteerFragment();
                    }
                } else if (itemId == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
        
        // Load default
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}