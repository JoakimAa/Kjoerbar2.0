package com.illusion_softworks.kjoerbar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.activities.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button bntNavToFriends, bntNavToHistory, bntNavToSession, bntNavToUnitCatalog, btnLogOut;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        bntNavToFriends = view.findViewById(R.id.navigate_to_friends_button);
        bntNavToHistory = view.findViewById(R.id.navigate_to_history_button);
        bntNavToSession = view.findViewById(R.id.navigate_to_session_button);
        bntNavToUnitCatalog = view.findViewById(R.id.navigate_to_unit_catalog_button);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        bntNavToFriends.setOnClickListener(viewer -> Navigation.findNavController(viewer).navigate(R.id.action_mainFragment_to_friendsFragment));
        bntNavToHistory.setOnClickListener(viewer -> Navigation.findNavController(viewer).navigate(R.id.action_mainFragment_to_historyFragment));
        bntNavToSession.setOnClickListener(viewer -> Navigation.findNavController(viewer).navigate(R.id.action_mainFragment_to_sessionFragment));
        bntNavToUnitCatalog.setOnClickListener(viewer -> Navigation.findNavController(viewer).navigate(R.id.action_mainFragment_to_unitCatalogFragment));
        btnLogOut.setOnClickListener(this::signOut);

        return view;
    }

    public void signOut(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthUI.getInstance()
                .signOut(view.getContext())
                .addOnCompleteListener(task -> {
                    assert user != null;
                    //Toast.makeText(getApplicationContext(), user.getDisplayName() +" "+ R.string.logged_out, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), SignInActivity.class);
                    startActivity(intent);
                });
    }
}