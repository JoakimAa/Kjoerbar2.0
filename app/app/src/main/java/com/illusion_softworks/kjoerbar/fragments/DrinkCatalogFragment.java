package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;

import java.util.Arrays;
import java.util.List;

public class DrinkCatalogFragment extends Fragment implements OnItemClickListener {

    private final String TAG = "Beverage Catalog";
    private RecyclerView recyclerView;
    private List<Drink> dummyData;
    private DrinkCatalogViewModel mViewModel;
    DrinkRecyclerAdapter mAdapter;

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

        View view = inflater.inflate(R.layout.fragment_drink_catalog, container, false);

        dummyData = Arrays.asList(
                new Drink("Bruh This The Wrong One", "Rum", "cl", 200, 40));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mViewModel.init();

        mAdapter = new DrinkRecyclerAdapter(view.getContext(), mViewModel.getDrinks().getValue(), this);

        mViewModel.getDrinks().observe(requireActivity(), drinks -> mAdapter.notifyDataSetChanged());

        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // Maybe handle what part of the beverage entry was clicked here?
        Log.d(TAG, "onItemClick: " + dummyData.get(position));
    }

    @Override
    public void onItemClick(String view) {

    }
}