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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

public class AddDrinkDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "BottomSheetAddDrinkFragment";
    private EditText mName;
    private EditText mCategory;
    private EditText mVolume;
    private EditText mPercent;
    private Button buttonAddDrink;

    public static AddDrinkDialogFragment newInstance(Context context) {
        return new AddDrinkDialogFragment();
    }

    @Override
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
        View view = inflater.inflate(R.layout.fragment_dialog_add_drink, container, false);

        // Initialize views
        buttonAddDrink = view.findViewById(R.id.button_add_drink);
        mName = view.findViewById(R.id.edittext_drink_name);
        mCategory = view.findViewById(R.id.edittext_drink_category);
        mVolume = view.findViewById(R.id.edittext_drink_volume);
        mPercent = view.findViewById(R.id.edittext_drink_percent);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrinkCatalogViewModel mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mViewModel.init();

        buttonAddDrink.setOnClickListener(v2 -> {
            Drink drink = new Drink(
                    mName.getText().toString(),
                    mCategory.getText().toString(),
                    Double.parseDouble(mVolume.getText().toString()),
                    Double.parseDouble(mPercent.getText().toString()));

            //mViewModel.addDrinkSimulation(drink);

            mViewModel.addDrink(drink);

            // Hides bottomSheetView
            this.dismiss();
        });
    }
}