package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.databinding.FragmentDrinkCatalogBinding;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

import java.util.Arrays;
import java.util.List;

public class DrinkCatalogFragment extends Fragment implements OnItemClickListener {

    private final String TAG = "Beverage Catalog";
    private RecyclerView recyclerView;
    private DrinkCatalogViewModel mViewModel;
    DrinkRecyclerAdapter mAdapter;
    private FragmentDrinkCatalogBinding binding;
    private ProgressBar mProgressBar;

    public DrinkCatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().setTitle(getString(R.string.beverage_catalog));
        binding = FragmentDrinkCatalogBinding.inflate(inflater, container, false);
//        View view = inflater.inflate(R.layout.fragment_drink_catalog, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mViewModel.init();

        mAdapter = new DrinkRecyclerAdapter(view.getContext(), mViewModel.getDrinks().getValue(), this);

        mViewModel.getDrinks().observe(getViewLifecycleOwner(), drinks -> mAdapter.notifyDataSetChanged());
//        mViewModel.getDrinks().observe(requireActivity(), new Observer<List<Drink>>() {
//            @Override
//            public void onChanged(List<Drink> drinks) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        mViewModel.getIsUpdating().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showProgressBar();
            } else {
                hideProgressBar();
                recyclerView.smoothScrollToPosition(mViewModel.getDrinks().getValue().size()-1);
            }
        });

        Button addDrinkBtn = view.findViewById(R.id.add_drink);
        addDrinkBtn.setOnClickListener(view1 -> {
            Drink testDrink = new Drink("Testdrink", "Wine", 0.3, 0.3);
            mViewModel.addDrinkSimulation(testDrink);
            Log.d(TAG, "Added testdrink");
        });

        initRecyclerView(view);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // Maybe handle what part of the beverage entry was clicked here?
        Log.d(TAG, "onItemClick: ");
    }

    @Override
    public void onItemClick(String view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}