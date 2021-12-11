package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.databinding.FragmentDrinkCatalogBinding;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

public class DrinkCatalogFragment extends Fragment implements OnItemClickListener {
    private static final String TAG = "Beverage Catalog";
    private DrinkRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FragmentDrinkCatalogBinding binding;

    public DrinkCatalogFragment() {
        // Required empty public constructor
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.beverageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private void openBottomSheetDialog() {
        AddDrinkDialogFragment bottomSheetAddDrink = AddDrinkDialogFragment.newInstance(requireActivity());
        bottomSheetAddDrink.show(getParentFragmentManager(), AddDrinkDialogFragment.TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // requireActivity().setTitle(getString(R.string.beverage_catalog));
        binding = FragmentDrinkCatalogBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.progress_bar);

        DrinkCatalogViewModel mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
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

        //Button addDrinkBtn = view.findViewById(R.id.add_drink);
        FloatingActionButton openBottomSheetFAB = view.findViewById(R.id.fab_bottom_sheet_add_drink);
        openBottomSheetFAB.setOnClickListener(v -> openBottomSheetDialog());

        initRecyclerView(view);
    }


    @Override
    public void onItemClick(int position) {
        // Maybe handle what part of the beverage entry was clicked here?
        Log.d(TAG, "onItemClick: ");
    }

    @Override
    public void onItemClick(String view, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}