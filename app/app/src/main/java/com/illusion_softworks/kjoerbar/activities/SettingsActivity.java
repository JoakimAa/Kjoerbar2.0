package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.LocalFirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        EditTextPreference fullNamePreference, usernamePreference, agePreference, heightPreference, weightPreference;
        ListPreference genderPreference;
        User currentUser;
        Map<String, Object> mapUser = new HashMap<>();

        @Override
        public void onStop() {
            super.onStop();
            if (isRequiredFieldsValid())
                UserDataHandler.getUserData();
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            UserDataHandler.updateUserDocumentReference();

            fullNamePreference = findPreference("full_name");
            usernamePreference = findPreference("username");
            agePreference = findPreference("age");
            heightPreference = findPreference("height");
            weightPreference = findPreference("weight");
            genderPreference = findPreference("gender");

            clearFields();

            agePreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            heightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            weightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));

            if (SignInActivity.getResponse().isNewUser()) {
                fullNamePreference.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName());
                if (UserDataHandler.getUser() == null) {
                    Log.d("USERISNULL", "User is null");
                    UserDataHandler.addUserToFirestore(new User(0, 0, 0, "", ""));
                } else {
                    Log.d("USERISNULL", "User is not null");
                    currentUser = UserDataHandler.getUser();
                    Log.d("SettingsUser: ", String.format("Username: %s, Weight: %d, Age: %d, Height: %d, Gender: %s", currentUser.getUsername(), currentUser.getWeight(), currentUser.getAge(), currentUser.getHeight(), currentUser.getGender()));
                    setTextFields();
                }
            } else {
                currentUser = UserDataHandler.getUser();
                Log.d("Current user", currentUser.toString());
                setTextFields();
            }

            Preference.OnPreferenceChangeListener changeListener = (preference, newValue) -> {
                mapUser.put(preference.getKey(), newValue);
                UserDataHandler.updateUserOnFireStore(mapUser);
                return true;
            };

            Preference.OnPreferenceChangeListener changeListenerFullName = (preference, newValue) -> {
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName((newValue.toString()))
                        .build();
                LocalFirebaseUser.getFirebaseUser().updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Display name: ", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                    }
                });
                return true;
            };

            fullNamePreference.setOnPreferenceChangeListener(changeListenerFullName);
            usernamePreference.setOnPreferenceChangeListener(changeListener);
            agePreference.setOnPreferenceChangeListener(changeListener);
            heightPreference.setOnPreferenceChangeListener(changeListener);
            weightPreference.setOnPreferenceChangeListener(changeListener);
            genderPreference.setOnPreferenceChangeListener(changeListener);
        }

        private void clearFields() {
            usernamePreference.setText("");
            agePreference.setText("");
            heightPreference.setText("");
            weightPreference.setText("");
            genderPreference.setValue("");
        }

        private void setTextFields() {
            Log.d("CurrentUserSetTextFields", currentUser.getUsername());

            fullNamePreference.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName());
            usernamePreference.setText(currentUser.getUsername());
            genderPreference.setValue(currentUser.getGender());
            agePreference.setText(String.valueOf(currentUser.getAge() != 0 ? currentUser.getAge() : ""));
            heightPreference.setText(String.valueOf(currentUser.getHeight() != 0 ? currentUser.getHeight() : ""));
            weightPreference.setText(String.valueOf(currentUser.getWeight() != 0 ? currentUser.getWeight() : ""));
        }

        private boolean isRequiredFieldsValid() {
            return usernamePreference.getText() != null &&
                    genderPreference.getValue() != null &&
                    agePreference.getText() != null &&
                    heightPreference.getText() != null &&
                    weightPreference.getText() != null;
        }
    }
}