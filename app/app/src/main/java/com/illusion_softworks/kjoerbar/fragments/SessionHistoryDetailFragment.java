package com.illusion_softworks.kjoerbar.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.utilities.FormatTime;
import com.illusion_softworks.kjoerbar.viewmodel.DrinkCatalogViewModel;
import com.illusion_softworks.kjoerbar.viewmodel.SessionHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class SessionHistoryDetailFragment extends Fragment implements OnItemClickListener {
    private static Session mSession;
    private static RecyclerView recyclerView;
    private TextView mSessionName, mSessionMaxPerMill, mSessionStartTime, mSessionEndTime, mSessionDuration;
    private SessionHistoryViewModel mSessionHistoryViewModel;
    private DrinkCatalogViewModel mDrinkCatalogViewModel;
    private DrinkRecyclerAdapter mAdapter;
    private View view;
    private int position;

    // Source: https://github.com/PhilJay/MPAndroidChart
    private PieChart pieChart;

    public SessionHistoryDetailFragment() {
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
        view = inflater.inflate(R.layout.fragment_session_history_detail, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSessionHistoryViewModel = new ViewModelProvider(requireActivity()).get(SessionHistoryViewModel.class);
        mSession = mSessionHistoryViewModel.getSelectedSession();

        mDrinkCatalogViewModel = new ViewModelProvider(requireActivity()).get(DrinkCatalogViewModel.class);
        mDrinkCatalogViewModel.init();


        setUpRecyclerView();

        mSessionName = view.findViewById(R.id.textViewSessionName);
        mSessionMaxPerMill = view.findViewById(R.id.textViewSessionMaxPerMill);
        mSessionStartTime = view.findViewById(R.id.textViewSessionStartTime);
        mSessionEndTime = view.findViewById(R.id.textViewSessionEndTime);
        mSessionDuration = view.findViewById(R.id.textViewSessionDuration);
        pieChart = view.findViewById(R.id.categoryPieChart);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yy HH:mm:ss", Locale.ENGLISH);
        formatDate.setTimeZone(TimeZone.getTimeZone("GMT+1"));

        String endtime = formatDate.format(mSession.getEndTime());
        String startTime = formatDate.format(mSession.getStartTime());

        long duration = mSession.getEndTime() - mSession.getStartTime();
        String formattedDuration = FormatTime.getFormattedTime(duration);

        mSessionName.setText(mSession.getName());
        mSessionMaxPerMill.setText(String.valueOf(mSession.getMaxPerMill()));
        mSessionStartTime.setText(startTime);
        mSessionEndTime.setText(endtime);
        mSessionDuration.setText(formattedDuration);
        setUpPieChart();
        loadPieChartData();
    }

    private void setUpPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText(getString(R.string.drinks_by_category));
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(12f);
        legend.setTextColor(Color.rgb(235, 52, 55));
        legend.setDrawInside(true);
        legend.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        HashMap<String, Integer> categoryOccurences = new HashMap<>();
        for (Drink drink : mSession.getBeverages()) {
            categoryOccurences.put(drink.getCategory(), categoryOccurences.getOrDefault(drink.getCategory(), 0) + 1);
        }

        categoryOccurences.forEach((k, v) -> {
            System.out.println("k: " + k + " v: " + v);
            entries.add(new PieEntry(v / 10f, k));
        });

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS)
            colors.add(color);

        for (int color : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(color);

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.drink_categories));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        System.out.println(categoryOccurences);
        System.out.println(entries);
    }

    private void setUpRecyclerView() {
        recyclerView = view.findViewById(R.id.beverageRecyclerView);
        mAdapter = new DrinkRecyclerAdapter(view.getContext(), this);
        mAdapter.addDataSet(mSession.getBeverages());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onItemClick(String view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title", mSession.getBeverages().get(position).getName());
        mDrinkCatalogViewModel.setSelectedDrink(mSession.getBeverages().get(position));
        if (view.equals("beverageDetailFragment")) {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionHistoryDetailFragment_to_drinkDetailFragment, bundle);
        }
    }
}