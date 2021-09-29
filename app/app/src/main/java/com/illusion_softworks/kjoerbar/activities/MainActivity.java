package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;

public class MainActivity extends AppCompatActivity {
    private static final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView username;

    private FragmentManager supportFragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragmentNavigation();
        setNavigationView();
        toolBarAndDrawer();
    }

    private void toolBarAndDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setFragmentNavigation() {
        supportFragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }

    private void setNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_viewer);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.username);
        username.setText(SignInActivity.getUser().getDisplayName());

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.d("ITEM", String.valueOf(id));
            if (id == R.id.nav_home) {
                navController.navigate(R.id.action_global_mainFragment);
            } else if (id == R.id.nav_alcohol_units_catalog) {
                navController.navigate(R.id.action_global_unitCatalogFragment);
            } else if (id == R.id.nav_history) {
                navController.navigate(R.id.action_global_historyFragment);
            } else if (id == R.id.log_out) {
                signOut();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    startActivity(new Intent(this, SignInActivity.class));
                    finish();
                });
    }
}
