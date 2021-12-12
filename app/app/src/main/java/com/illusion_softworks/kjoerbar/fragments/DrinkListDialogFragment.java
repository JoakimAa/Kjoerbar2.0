package com.illusion_softworks.kjoerbar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

import java.util.List;

public class DrinkListDialogFragment extends BottomSheetDialogFragment implements OnItemClickListener {
    public static final String TAG = "BottomSheetAddDrinkFragment";
    private static List<Drink> data;
    private DrinkRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private static String mName;
    private DrinkCatalogViewModel mViewModel;


    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    public static DrinkListDialogFragment newInstance(Context context, String name) {
        DrinkListDialogFragment.mName = name;
        return new DrinkListDialogFragment();
    }

    public static DrinkListDialogFragment newInstance(Context context) {
        return new DrinkListDialogFragment();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.beverageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
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

        mProgressBar = view.findViewById(R.id.progress_bar);

        mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mViewModel.init();

        mAdapter = new DrinkRecyclerAdapter(view.getContext(), this);
        mViewModel.getDrinks().observe(getViewLifecycleOwner(), drinks -> mAdapter.addDataSet(drinks));
        mViewModel.getIsUpdating().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                showProgressBar();
            } else {
                hideProgressBar();
            }
        });

        initRecyclerView(view);
    }

    @Override
    public void onItemClick(int position) {
        SessionFragment.startNewSession(mName);
        SessionFragment.addDrink(mAdapter.getDrink(position));
        Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.sessionFragment);
        Log.d("TAG", "onItemClick: " + mAdapter.getDrink(position));
        // Close bottom dialog
        this.dismiss();
    }

    @Override
    public void onItemClick(String view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title", mAdapter.getDrink(position).getName());
        mViewModel.setSelectedDrink(mAdapter.getDrink(position));
        if (view.equals("beverageDetailFragment")) {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionFragment_to_drinkDetailFragment, bundle);
            this.dismiss();
        }
    }
}