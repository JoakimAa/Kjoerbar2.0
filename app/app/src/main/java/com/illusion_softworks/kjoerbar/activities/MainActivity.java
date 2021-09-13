package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        Button btnLogout = findViewById(R.id.btnLogout);

        //Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
        btnLogout.setOnClickListener(this::signOut);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut(View view) {
        // [START auth_fui_signout]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    assert user != null;
                    Toast.makeText(getApplicationContext(), user.getDisplayName() +" "+ R.string.logged_out, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(view.getContext(), SignInActivity.class));
                    finish();
                });
        // [END auth_fui_signout]
    }
}