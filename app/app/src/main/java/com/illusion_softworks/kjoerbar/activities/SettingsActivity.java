package com.illusion_softworks.kjoerbar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.LocalFirebaseUser;

import java.util.HashMap;
import java.util.Map;

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
        User user;
        User currentUser;
        Map<String, Object> mapUser = new HashMap<String, Object>();

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            UserDataHandler.getUserData();

            fullNamePreference = findPreference("full_name");
            usernamePreference = findPreference("username");
            agePreference = findPreference("age");
            heightPreference = findPreference("height");
            weightPreference = findPreference("weight");
            genderPreference = findPreference("gender");

            assert agePreference != null;
            agePreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            assert heightPreference != null;
            heightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            assert weightPreference != null;
            weightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            currentUser = UserDataHandler.getUser();
            Log.d("Current user", currentUser.toString());

            if (SignInActivity.getResponse().isNewUser()) {
                UserDataHandler.addUserToFirestore(currentUser);
            }

            setTextFields();

            Preference.OnPreferenceChangeListener changeListener = new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    /*if (SignInActivity.getResponse().isNewUser()) {
                        createUser();
                        UserDataHandler.addUserToFirestore(user);
                    }
                    else {*/
                    mapUser.put(preference.getKey(), newValue);
                    UserDataHandler.updateUserOnFireStore(mapUser);
                    //}
                    return true;
                }
            };

            Preference.OnPreferenceChangeListener changeListenerFullName = new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName((newValue.toString()))
                            .build();
                    LocalFirebaseUser.getFirebaseUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Display name: ", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            }
                        }
                    });
                    return true;
                }
            };

            fullNamePreference.setOnPreferenceChangeListener(changeListenerFullName);
            usernamePreference.setOnPreferenceChangeListener(changeListener);
            agePreference.setOnPreferenceChangeListener(changeListener);
            heightPreference.setOnPreferenceChangeListener(changeListener);
            weightPreference.setOnPreferenceChangeListener(changeListener);
            genderPreference.setOnPreferenceChangeListener(changeListener);
        }

        private void setTextFields() {
            /*fullNamePreference.setText("");
            usernamePreference.setText("");
            agePreference.setText("");
            heightPreference.setText("");
            weightPreference.setText("");
            genderPreference.setValue("");*/
            Log.d("CurrentUserSetTextFields", currentUser.getUsername().toString());

            fullNamePreference.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName() == null ? "" : LocalFirebaseUser.getFirebaseUser().getDisplayName());
            usernamePreference.setText(currentUser.getUsername());
            agePreference.setText(String.valueOf(currentUser.getAge()));
            heightPreference.setText(String.valueOf(currentUser.getHeight()));
            weightPreference.setText(String.valueOf(currentUser.getWeight()));
            genderPreference.setValue(currentUser.getGender());
        }

        private void createUser() {
            if (isRequiredFieldsValid()) {
                user = new User(Integer.parseInt(weightPreference.getText().toString()),
                        Integer.parseInt(heightPreference.getText().toString()),
                        Integer.parseInt(agePreference.getText().toString()),
                        genderPreference.getValue().toString(),
                        usernamePreference.getText().toString());
            }
        }

        private boolean isRequiredFieldsValid() {
            return !usernamePreference.getText().equals("") &&
                    !genderPreference.getValue().equals("") &&
                    !agePreference.getText().equals("") &&
                    !heightPreference.getText().equals("") &&
                    !weightPreference.getText().equals("");
        }
    }
}