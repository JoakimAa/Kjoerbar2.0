package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.helpers.SetBottomNavigation;

public class MapFragment extends Fragment {
    private BottomNavigationView bottomnavigation;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Log.d("BottomNavigation map", String.valueOf(this.getId()));
        SetBottomNavigation.setBottomNavigation(view, this, bottomnavigation, R.id.navigate_to_map_button);

        return view;
    }
}