package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.illusion_softworks.kjoerbar.R;

public class FriendsFragment extends Fragment {
    private BottomNavigationView bottomnavigation;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle(getString(R.string.friends));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
//        Log.d("BottomNavigation friends", String.valueOf(this.getId()));
//        SetBottomNavigation.setBottomNavigation(view, this, bottomnavigation, R.id.navigate_to_friends_button);
        return view;
    }
}