package com.illusion_softworks.kjoerbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

public class DrinkListDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "BottomSheetAddDrinkFragment";

    public static DrinkListDialogFragment newInstance(Context context) {
        return new DrinkListDialogFragment();
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
        return inflater.inflate(R.layout.fragment_dialog_drink_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DrinkCatalogViewModel mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mViewModel.init();
    }
}