package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.handler.BeverageCatalogDataHandler;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private static IdpResponse response;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public static IdpResponse getResponse() {
        return response;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        createAuthStateListener();
        setContentView(R.layout.activity_login);
        // createSignInIntent();

        BeverageCatalogDataHandler.getAlcoholUnitCatalog();
        Log.d("SIGNIN", "Signin");
    }

    private void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        signInLauncher.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls(
                        "https://example.com/terms.html",
                        "https://example.com/privacy.html")
                .setLogo(R.drawable.ic_baseline_glass_mug_variant)      // Set logo drawable
                .setTheme(R.style.Theme_Kjoerbar_Login)      // Set theme
                .build());
    }

    private void onSignInResult(@NonNull FirebaseAuthUIAuthenticationResult result) {
        response = result.getIdpResponse();

        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in

            FirebaseUser firebaseUser = auth.getCurrentUser();
            FirestoreHandler.setFirebaseUser(firebaseUser);

            Log.d("New user signin", String.valueOf(firebaseUser));
            Log.d("USER", firebaseUser.toString());
            Log.d("New user signin", String.valueOf(response));

            UserDataHandler.addBeverageToCatalog(BeverageCatalogDataHandler.getBeverages());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // Sign in failed
            if (response == null) {
                // Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_LONG).show();
                Log.d("SIGNIN", "Signin failed");
                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                //Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                Log.d("SIGNIN", "No network");
            }
        }
    }

    private void createAuthStateListener() {
        authStateListener = firebaseAuth -> {
            FirebaseUser currentUser = auth.getCurrentUser();

            if (currentUser == null) {
                createSignInIntent();
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Inlogget som " + currentUser.getDisplayName(),
                        Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(authStateListener);
    }
}