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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.DrinkRecyclerAdapter;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.utilities.FormatTime;
import com.illusion_softworks.kjoerbar.viewmodel.SessionHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class SessionHistoryDetailFragment extends Fragment implements OnItemClickListener {
    private static Session mSession;
    private static RecyclerView recyclerView;
    private TextView mSessionName, mSessionMaxPerMill, mSessionStartTime, mSessionEndTime, mSessionDuration;
    private SessionHistoryViewModel mSessionHistoryViewModel;
    private DrinkRecyclerAdapter mAdapter;
    private View view;
    private int position;

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

        setUpRecyclerView();

        mSessionName = view.findViewById(R.id.textViewSessionName);
        mSessionMaxPerMill = view.findViewById(R.id.textViewSessionMaxPerMill);
        mSessionStartTime = view.findViewById(R.id.textViewSessionStartTime);
        mSessionEndTime = view.findViewById(R.id.textViewSessionEndTime);
        mSessionDuration = view.findViewById(R.id.textViewSessionDuration);

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
        if (view.equals("beverageDetailFragment")) {
            Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(R.id.action_sessionHistoryDetailFragment_to_drinkDetailFragment, bundle);
        }
    }
}