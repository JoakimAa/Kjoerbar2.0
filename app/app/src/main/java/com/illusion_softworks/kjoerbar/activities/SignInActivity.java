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
    private final String TAG = "SIGN_IN";
    private boolean isNewUser = false;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Signing in...");

        auth = FirebaseAuth.getInstance();
        createAuthStateListener();
        setContentView(R.layout.activity_login);
        BeverageCatalogDataHandler.getAlcoholUnitCatalog();
    }

    private void createSignInIntent() {
        Log.d(TAG, "I Visited createSignInIntent()");
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
                .setLogo(R.drawable.ic_lille_promille_logo)      // Set logo drawable
                .setTheme(R.style.Theme_Kjoerbar_Login)      // Set theme
                .build());
    }

    private void onSignInResult(@NonNull FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();

        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            assert response != null;
            isNewUser = response.isNewUser();

            FirebaseUser firebaseUser = auth.getCurrentUser();
            FirestoreHandler.setFirebaseUser(firebaseUser);

            Log.d(TAG, "New user signin" + firebaseUser);
            assert firebaseUser != null;
            Log.d(TAG, "USER" + firebaseUser.toString());
            Log.d(TAG, "New user signin" + response);

            UserDataHandler.addBeverageToCatalog(BeverageCatalogDataHandler.getBeverages());
            //navigateToMain();
        } else {
            // Sign in failed
            if (response == null) {
                // Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_LONG).show();
                Log.d(TAG, "Signin failed");
                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                //Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                Log.d(TAG, "No network");
            }
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isNewUser", isNewUser);
        startActivity(intent);

        isNewUser = false;
    }

    private void createAuthStateListener() {
        authStateListener = firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();

            if (user == null) {
                createSignInIntent();
            } else {
                String text = "Inlogget som " + user.getDisplayName();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                navigateToMain();
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