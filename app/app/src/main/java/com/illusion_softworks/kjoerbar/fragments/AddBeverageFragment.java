package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.BeverageRecyclerAdapter;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Beverage;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddBeverageFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private List<Beverage> dummyData;

    public AddBeverageFragment() {
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
        requireActivity().setTitle(getString(R.string.add_beverage));
        View view = inflater.inflate(R.layout.fragment_add_beverage, container, false);

        recyclerView = view.findViewById(R.id.beverageRecyclerView);

        dummyData = Arrays.asList(
                new Beverage("Whiskey", "Rum", "cl", 0.05, 40),
                new Beverage("Wine", "Rum", "cl", 0.5, 14.0),
                new Beverage("Rum", "Rum", "cl", 0.03, 40),
                new Beverage("Beer", "Rum", "cl", 0.5, 6.7),
                new Beverage("Beer", "Rum", "cl", 0.01, 1),
                new Beverage("Øl", "Rum", "Øl", 0.6, 5.0));


        BeverageRecyclerAdapter adapter = new BeverageRecyclerAdapter(view.getContext(), dummyData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        // Maybe handle what part of the beverage entry was clicked here?
        SessionFragment.getUser().getCurrentSession().addAlcoholUnit(new AlcoholUnit(dummyData.get(position)));
        Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.sessionFragment);
        Log.d("TAG", "onItemClick: " + dummyData.get(position));
    }
}