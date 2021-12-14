package com.illusion_softworks.kjoerbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.illusion_softworks.kjoerbar.R;

public class StartNewSessionDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "BottomSheetStartNewSessionFragment";

    private EditText mSessionName;


    public static StartNewSessionDialogFragment newInstance(Context context) {
        return new StartNewSessionDialogFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_new_session, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        Button mStartNewSessionButton = view.findViewById(R.id.button_start_session);
        mSessionName = view.findViewById(R.id.edittext_session_name);

        mStartNewSessionButton.setOnClickListener(v2 -> {
            DrinkListDialogFragment drinkListDialogFragment = DrinkListDialogFragment.newInstance(requireActivity(), mSessionName.getText().toString());
            drinkListDialogFragment.show(getParentFragmentManager(), DrinkListDialogFragment.TAG);
            // Hides bottomSheetView
            this.dismiss();
        });
    }

}
