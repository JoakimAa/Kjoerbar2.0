package com.illusion_softworks.kjoerbar.helpers;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.illusion_softworks.kjoerbar.R;

public class SetBottomNavigation {
    public static void setBottomNavigation(View view, Fragment fragment, BottomNavigationView bottomnavigation, int itemId) {
//        bottomnavigation = view.findViewById(R.id.bottom_navigation);
//        bottomnavigation.setSelectedItemId(itemId == -1 ? R.id.invisible : itemId);
//        bottomnavigation.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//            if (id == R.id.navigate_to_session_button) {
//                item.setChecked(true);
//                NavHostFragment.findNavController(fragment).navigate(R.id.action_global_sessionFragment);
//            }
//            if (id == R.id.navigate_to_friends_button) {
//                item.setChecked(true);
//                NavHostFragment.findNavController(fragment).navigate(R.id.action_global_friendsFragment);
//            }
//            if (id == R.id.navigate_to_map_button) {
//                item.setChecked(true);
//                NavHostFragment.findNavController(fragment).navigate(R.id.action_global_mapFragment);
//            }
//
//            return true;
//        });
    }
}
