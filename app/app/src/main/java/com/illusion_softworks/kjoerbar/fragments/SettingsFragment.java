package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static User currentUser = new User();
    Map<String, Object> mapUser = new HashMap<>();
    private EditTextPreference fullNamePreference, usernamePreference, agePreference, heightPreference, weightPreference;
    private ListPreference genderPreference;
    private SwitchPreferenceCompat enableDarkMode;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        findPreferences();
        clearFields();
        setNumberLimiter();

        UserViewModel mViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mViewModel.init();

        mViewModel.getUser().observeForever(user -> currentUser = user);
        mViewModel.getIsUpdating().observeForever(isLoading -> {
            if (!isLoading) {
                setTextFields();
            }
        });

        Preference.OnPreferenceChangeListener changeListenerDarkMode = (preference, newValue) -> {
            boolean isChecked = (boolean) newValue;
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return true;
        };

        Preference.OnPreferenceChangeListener changeListener = (preference, newValue) -> {
            mapUser.put(preference.getKey(), newValue);
            UserDataHandler.updateUserOnFireStore(mapUser);
            return true;
        };

        Preference.OnPreferenceChangeListener changeListenerNumbers = (preference, newValue) -> {
            mapUser.put(preference.getKey(), Integer.parseInt(newValue.toString()));
            UserDataHandler.updateUserOnFireStore(mapUser);
            return true;
        };

        Preference.OnPreferenceChangeListener changeListenerFullName = (preference, newValue) -> {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName((newValue.toString()))
                    .build();
            FirestoreHandler.getFirebaseUser().updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Display name: ", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                }
            });
            return true;
        };

        enableDarkMode.setOnPreferenceChangeListener(changeListenerDarkMode);
        setOnPreferenceChangeListeners(changeListener, changeListenerFullName, changeListenerNumbers);

    }

    private void setOnPreferenceChangeListeners(Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceChangeListener changeListenerFullName, Preference.OnPreferenceChangeListener changeListenerNumbers) {
        fullNamePreference.setOnPreferenceChangeListener(changeListenerFullName);
        usernamePreference.setOnPreferenceChangeListener(changeListener);
        genderPreference.setOnPreferenceChangeListener(changeListener);
        agePreference.setOnPreferenceChangeListener(changeListenerNumbers);
        heightPreference.setOnPreferenceChangeListener(changeListenerNumbers);
        weightPreference.setOnPreferenceChangeListener(changeListenerNumbers);
    }

    private void setNumberLimiter() {
        agePreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        heightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        weightPreference.setOnBindEditTextListener(editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
    }

    private void findPreferences() {
        fullNamePreference = findPreference("full_name");
        usernamePreference = findPreference("username");
        agePreference = findPreference("age");
        heightPreference = findPreference("height");
        weightPreference = findPreference("weight");
        genderPreference = findPreference("gender");
        enableDarkMode = findPreference("dark_mode");
    }

    private void clearFields() {
        usernamePreference.setText("");
        agePreference.setText("");
        heightPreference.setText("");
        weightPreference.setText("");
        genderPreference.setValue("");
    }

    private void setTextFields() {
        // Log.d("CurrentUserSetTextFields", currentUser.getUsername());
        fullNamePreference.setText(FirestoreHandler.getFirebaseUser().getDisplayName());
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