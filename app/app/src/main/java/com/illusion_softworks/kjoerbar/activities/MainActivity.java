package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.referencehandler.LocalFirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//    private ActionBarDrawerToggle toggle;
//    private DrawerLayout drawer;
//    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomNavigation();
        setNavigationDrawer();
        //toolBarAndDrawer();
    }

    public void setBottomNavigation() {
        NavController controller = Navigation.findNavController(this, R.id.nav_host);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, controller);
    }

    private void setNavigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_friends)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController controller = Navigation.findNavController(this, R.id.nav_host);

        NavigationView navView = findViewById(R.id.nav_view);
        //setDrawerInfo(navView);

        NavigationUI.setupWithNavController(navView, controller);
    }

    private void setDrawerInfo(NavigationView navView) {
        View headerView = navView.getHeaderView(0);
        username = headerView.findViewById(R.id.username);
        username.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName());
    }

//    private void toolBarAndDrawer() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        drawer = findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        //setNavigationView();
//        setToolBarAndDrawer();
//    }

//    private void setFragmentNavigation() {
//        FragmentManager supportFragmentManager;
//        NavHostFragment navHostFragment;
//        NavController navController;
//
//        supportFragmentManager = getSupportFragmentManager();
//        navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host);
//        assert navHostFragment != null;
//        navController = navHostFragment.getNavController();
//    }
//    private void setNavigationView() {
//        NavigationView navigationView = findViewById(R.id.nav_viewer);
//        View headerView = navigationView.getHeaderView(0);
//        username = headerView.findViewById(R.id.username);
//        username.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName());
//
//        navigationView.setNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//            Log.d("ITEM", String.valueOf(id));
//            if (id == R.id.nav_home) {
//                navController.navigate(R.id.action_global_mainFragment);
//            } else if (id == R.id.nav_alcohol_units_catalog) {
//                navController.navigate(R.id.action_global_unitCatalogFragment);
//            } else if (id == R.id.nav_history) {
//                navController.navigate(R.id.action_global_historyFragment);
//            } else if (id == R.id.nav_settings) {
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//            } else if (id == R.id.log_out) {
//                signOut();
//            }
//            drawer.closeDrawer(GravityCompat.START);
//            return true;
//        });
//    }

//    private void setToolBarAndDrawer() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        drawer = findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this, R.id.nav_host);
        return NavigationUI.navigateUp(controller, appBarConfiguration) || super.onSupportNavigateUp();
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START))
//            drawer.closeDrawer(GravityCompat.START);
//        else super.onBackPressed();
//    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    startActivity(new Intent(this, SignInActivity.class));
                    finish();
                });
    }
}
