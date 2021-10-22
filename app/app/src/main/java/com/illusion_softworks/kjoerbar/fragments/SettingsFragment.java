package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.activities.SignInActivity;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.LocalFirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {
    EditTextPreference fullNamePreference, usernamePreference, agePreference, heightPreference, weightPreference;
    ListPreference genderPreference;
    User currentUser = UserDataHandler.getUser();
    Map<String, Object> mapUser = new HashMap<>();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        UserDataHandler.updateUserDocumentReference();

        Thread getUserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserDataHandler.getUserData();
                    updateUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getUserThread.start();

        findPreferences();
        clearFields();
        setNumberLimiter();

        //updateUser();

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
            LocalFirebaseUser.getFirebaseUser().updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("Display name: ", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
                }
            });
            return true;
        };

        setOnPreferenceChangeListeners(changeListener, changeListenerFullName, changeListenerNumbers);

    }

    private void updateUI() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUser();
            }
        });
    }

    private void setOnPreferenceChangeListeners(Preference.OnPreferenceChangeListener changeListener, Preference.OnPreferenceChangeListener changeListenerFullName, Preference.OnPreferenceChangeListener changeListenerNumbers) {
        fullNamePreference.setOnPreferenceChangeListener(changeListenerFullName);
        usernamePreference.setOnPreferenceChangeListener(changeListener);
        genderPreference.setOnPreferenceChangeListener(changeListener);
        agePreference.setOnPreferenceChangeListener(changeListenerNumbers);
        heightPreference.setOnPreferenceChangeListener(changeListenerNumbers);
        weightPreference.setOnPreferenceChangeListener(changeListenerNumbers);
    }

    private void updateUser() {
        currentUser = UserDataHandler.getUser();
        if (SignInActivity.getResponse().isNewUser()) {
            fullNamePreference.setText(LocalFirebaseUser.getFirebaseUser().getDisplayName());
            if (currentUser == null) {
                Log.d("USERISNULL", "User is null");
                UserDataHandler.addUserToFirestore(new User(0, 0, 0, "", ""));
                UserDataHandler.getUserData();
            } else {
                Log.d("USERISNULL", "User is not null");
                //Log.d("SettingsUser: ", String.format("Username: %s, Weight: %d, Age: %d, Height: %d, Gender: %s", currentUser.getUsername(), user.getWeight(), user.getAge(), user.getHeight(), user.getGender()));
                Log.d("SettingsCurrentUser: ", String.format("Username: %s, Weight: %d, Age: %d, Height: %d, Gender: %s", currentUser.getUsername(), currentUser.getWeight(), currentUser.getAge(), currentUser.getHeight(), currentUser.getGender()));
                setTextFields();
            }
        } else {
            // Log.d("Current user", currentUser.toString());
            setTextFields();
        }
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
    }

    private void clearFields() {
        usernamePreference.setText("");
        agePreference.setText("");
        heightPreference.setText("");
        weightPreference.setText("");
        genderPreference.setValue("");
    }

    private void setTextFields() {
//        Log.d("CurrentUserSetTextFields", currentUser.getUsername());

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

