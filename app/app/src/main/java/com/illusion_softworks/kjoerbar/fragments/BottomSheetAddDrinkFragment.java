package com.illusion_softworks.kjoerbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.illusion_softworks.kjoerbar.R;

public class BottomSheetAddDrinkFragment extends BottomSheetDialogFragment {
    public static final String TAG = "BottomSheetAddDrinkFragment";
    public static Context context;
    private Button buttonAddDrink;

    public static BottomSheetAddDrinkFragment newInstance(Context context) {
        return new BottomSheetAddDrinkFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_drink, container, false);

        /*
            Button buttonAddDrink =  bottomSheetView.findViewById(R.id.button_add_drink);
            buttonAddDrink.setOnClickListener(v2 -> {
                Drink testDrink = new Drink("Testdrink", "Wine", 0.3, 0.3);
                mViewModel.addDrinkSimulation(testDrink);
                Log.d(TAG, "Added testdrink");

                // Hides bottomSheetView
                bottomSheetAddDrink.dismiss();
            });
            */

        // Initialize button
        buttonAddDrink = view.findViewById(R.id.button_add_drink);
        return view;
    }
}