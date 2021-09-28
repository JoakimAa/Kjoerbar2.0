package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;

public class MainActivity extends AppCompatActivity {
    private static final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private FragmentManager supportFragmentManager;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supportFragmentManager = getSupportFragmentManager();
        navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        menu.findItem(R.id.username).setTitle(firebaseUser.getDisplayName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            navController.navigate(R.id.action_global_mainFragment);
            return true;
        } else if (id == R.id.nav_alcohol_units_catalog) {
            navController.navigate(R.id.action_global_unitCatalogFragment);
            return true;
        } else if (id == R.id.nav_history) {
            navController.navigate(R.id.action_global_historyFragment);
            return true;
        } else if (id == R.id.log_out) {
            signOut();
            return true;
        }
            return super.onOptionsItemSelected(item);
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
