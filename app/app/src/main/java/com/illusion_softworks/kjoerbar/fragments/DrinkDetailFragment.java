package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

public class DrinkDetailFragment extends Fragment {
    private static Drink mDrink;
    private TextView mBeverageName, mBeverageAlcoholPercent, mBeverageVolume, mBeverageProducer, mBeverageCategory;
    private DrinkCatalogViewModel mDrinkCatalogViewModel;

    public DrinkDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrinkCatalogViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mDrink = mDrinkCatalogViewModel.getSelectedDrink();

        mBeverageName = view.findViewById(R.id.beverageNameTextView);
        mBeverageAlcoholPercent = view.findViewById(R.id.beverageAlcoholPercentageTextView);
        mBeverageVolume = view.findViewById(R.id.beverageVolumeTextView);
        mBeverageProducer = view.findViewById(R.id.beverageProducerTextView);
        mBeverageCategory = view.findViewById(R.id.beverageCategoryTextView);

        mBeverageName.setText(mDrink.getName());
        mBeverageAlcoholPercent.setText(String.valueOf(mDrink.getPercent()));
        mBeverageVolume.setText(String.valueOf(mDrink.getVolume()));
        mBeverageProducer.setText(mDrink.getProducer());
        mBeverageCategory.setText(mDrink.getCategory());
    }
}
