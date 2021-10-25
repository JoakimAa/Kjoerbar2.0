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
import com.illusion_softworks.kjoerbar.datahandler.BeverageCatalogDataHandler;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.referencehandler.LocalFirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    private static IdpResponse response;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    public static IdpResponse getResponse() {
        return response;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createSignInIntent();
        //DummyData.addBeverageToCatalog();
        BeverageCatalogDataHandler.getAlcoholUnitCatalog();
        Log.d("SIGNIN", "Signin");
    }

    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                //.setEmailLink(link)
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls(
                        "https://example.com/terms.html",
                        "https://example.com/privacy.html")
                .setLogo(R.drawable.ic_baseline_glass_mug_variant)      // Set logo drawable
                .setTheme(R.style.Theme_Kjoerbar_Login)      // Set theme
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(@NonNull FirebaseAuthUIAuthenticationResult result) {
        response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            LocalFirebaseUser.setFirebaseUser(firebaseUser);
            Log.d("New user signin", String.valueOf(firebaseUser));
            assert firebaseUser != null;
            Log.d("USER", firebaseUser.toString());
            assert response != null;
            Log.d("New user signin", String.valueOf(response));
            if (response.isNewUser()) {
                UserDataHandler.addBeverageToCatalog(BeverageCatalogDataHandler.getBeverages());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
            } else {
                //UserDataHandler.getUserData();
                UserDataHandler.addBeverageToCatalog(BeverageCatalogDataHandler.getBeverages());
                Toast.makeText(getApplicationContext(), getString(R.string.logged_in_as) + firebaseUser.getDisplayName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            // Sign in failed
            if (response == null) {
                //Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_LONG).show();
                Log.d("SIGNIN", "Signin failed");
                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                //Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                Log.d("SIGNIN", "No network");
            }
        }
    }

    public void delete() {
        UserDataHandler.removeUserFromFirebase();
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(this, SignInActivity.class);
                    startActivity(intent);
                });
    }
}