package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.helpers.DummyData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    FirebaseUser user;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createSignInIntent();
        Log.d("SIGNIN", "Signin");
    }

    public void createSignInIntent() {
        /*ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
           .setAndroidPackageName(
                       *//* yourPackageName= *//* "com.illusion_softworks.kjoerbar",
                       *//* installIfNotAvailable= *//* true,
                        *//* minimumVersion= *//* null)
           .setHandleCodeInApp(true) // This must be set to true
           .setUrl("https://google.com") // This URL needs to be whitelisted
           .build();*/

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder()
                        //.enableEmailLinkSignIn()
                        //.setActionCodeSettings(actionCodeSettings)
                        .build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        /*
        if (AuthUI.canHandleIntent(getIntent())) {
            if (getIntent().getExtras() == null) {
                return;
            }
            String link = getIntent().getExtras().getString(ExtraConstants.EMAIL_LINK_SIGN_IN);
            if (link != null) {
        */
                // Create and launch sign-in intent
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        //.setEmailLink(link)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        //.setLogo(R.drawable.my_great_logo)      // Set logo drawable
                        //.setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build();
                signInLauncher.launch(signInIntent);
            }
        //}
    //}

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().getCurrentUser();
            DummyData.populateData();
            Toast.makeText(getApplicationContext(), R.string.logged_in_as + user.getDisplayName(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.d("SIGNIN", "crash");

        }
        else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                //showSnackbar(R.string.sign_in_cancelled);
                //Toast.makeText(getApplicationContext(), R.string.sign_in_failed, Toast.LENGTH_LONG).show();
                Log.d("SIGNIN", "Signin failed");
                return;
            }

            if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                //Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                //showSnackbar(R.string.no_internet_connection);
                Log.d("SIGNIN", "No network");
                return;
            }

            //showSnackbar(R.string.unknown_error);
            //Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }

    public void delete() {
        // [START auth_fui_delete]
        UserDataHandler.removeUserFromFirebase();
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(this, SignInActivity.class);
                    startActivity(intent);

                });
        // [END auth_fui_delete]
    }
}