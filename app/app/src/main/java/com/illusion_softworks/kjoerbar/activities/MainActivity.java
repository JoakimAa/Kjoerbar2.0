package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Button btnLogout;

    private FragmentManager supportFragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setViews();
        //setDrawerMenu();

        supportFragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment);
        navController = navHostFragment.getNavController();
    }

//    private void setViews() {
//        btnLogout = findViewById(R.id.btnLogout);
//
//        //Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
//        btnLogout.setOnClickListener(this::signOut);
//
//        /*btnNavMap.setOnClickListener(view -> {
//            Intent intent = new Intent(view.getContext(), MapActivity.class);
//            startActivity(intent);
//        });*/
//        /*btnNavFriends.setOnClickListener(view -> {
//            Intent intent = new Intent(view.getContext(), FriendsActivity.class);
//            startActivity(intent);
//        });*/
//
//        drawerLayout = findViewById(R.id.my_drawer_layout);
//    }

//    private void setDrawerMenu() {
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
//
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.nav_alcohol_units_catalog) {
                Intent intent2 = new Intent(this, UnitCatalogActivity.class);
                startActivity(intent2);
                return true;
            }
            else if (id == R.id.nav_settings) {
                Intent intent3 = new Intent(this, SettingsActivity.class);
                startActivity(intent3);
                return true;
            }
            else if (id == R.id.nav_sessions) {
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;
            }
            /*else if (id == R.id.nav_map) {
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
            else if (id == R.id.nav_friends) {
                Intent intent = new Intent(view.getContext(), FriendsActivity.class);
                startActivity(intent);
            }*/

            return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
   // }

    public void signOut(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    assert user != null;
                    //Toast.makeText(getApplicationContext(), user.getDisplayName() +" "+ R.string.logged_out, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(view.getContext(), SignInActivity.class));
                    finish();
                });
    }
}