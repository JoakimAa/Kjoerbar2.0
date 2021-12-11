package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;
import com.illusion_softworks.kjoerbar.utilities.Notifications;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDataHandler.getUserBeverageCatalog();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                // All fragments where hamburger is visible
                R.id.sessionFragment,
                R.id.mapFragment,
                R.id.friendsFragment,
                R.id.drinkCatalogFragment,
                R.id.sessionHistoryFragment,
                R.id.settingsFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(toolbar, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(bottomNav, navController);

        Notifications.init(this);
        setSignOut(navView);
        setDrawerInfo(navView);

        if (getIntent().getBooleanExtra("isNewUser", false)) {
            navController.navigate(R.id.setEssentialSettingsFragment);
        }
    }

    private void setDrawerInfo(NavigationView navView) {
        View headerView = navView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        username.setText(FirestoreHandler.getFirebaseUser().getDisplayName());
    }

    public void setSignOut(NavigationView navView) {
        navView.getMenu().findItem(R.id.log_out).setOnMenuItemClickListener(menuItem -> {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(task -> {
                        startActivity(new Intent(this, SignInActivity.class));
                        finish();
                    });
            return true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Navigation.findNavController(this, R.id.nav_host);
        return NavigationUI.navigateUp(controller, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void proceed(View view) {
        Navigation.findNavController(this, R.id.nav_host).navigate(R.id.sessionFragment);
    }
}
