package com.illusion_softworks.kjoerbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

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