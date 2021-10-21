package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.BeverageRecyclerAdapter;

import java.util.Arrays;
import java.util.List;

public class BeverageCatalogFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> dummyData;

    public BeverageCatalogFragment() {
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

        View view = inflater.inflate(R.layout.fragment_beverage_catalog, container, false);

        dummyData = Arrays.asList("Whiskey", "Vodka", "Rum", "Beer");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.beverageRecyclerView);

        BeverageRecyclerAdapter adapter = new BeverageRecyclerAdapter(view.getContext(), dummyData);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }
}