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
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddDrinkFragment extends Fragment implements OnItemClickListener {
    private static final List<Drink> data = UserDataHandler.getBeverages();

    public AddDrinkFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_drink, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.beverageRecyclerView);

        DrinkRecyclerAdapter adapter = new DrinkRecyclerAdapter(view.getContext(), data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        SessionFragment.startNewSession();
        SessionFragment.addDrink(data.get(position));
        Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.sessionFragment);
        Log.d("TAG", "onItemClick: " + data.get(position));
    }

    @Override
    public void onItemClick(String view) {
        if (view.equals("beverageDetailFragment"))
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_addDrinkFragment_to_drinkDetailFragment);
    }
}