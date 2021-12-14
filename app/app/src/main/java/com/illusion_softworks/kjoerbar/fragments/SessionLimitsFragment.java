package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.illusion_softworks.kjoerbar.R;

public class SessionLimitsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_session_preferences, rootKey);
    }
}