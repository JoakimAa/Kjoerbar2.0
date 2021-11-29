package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
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

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.SessionsRecyclerAdapter;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.viewmodel.SessionHistoryViewModel;

import java.util.List;

public class SessionHistoryFragment extends Fragment implements OnItemClickListener {
    private static final List<Session> data = UserDataHandler.getSessions();
    private final String TAG = "Session History";
    private RecyclerView recyclerView;
    private SessionHistoryViewModel mViewModel;
    SessionsRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;

    public SessionHistoryFragment() {
        // Required empty public constructor
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.sessionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().setTitle(getString(R.string.session_history));
        View view = inflater.inflate(R.layout.fragment_session_history, container, false);

//        RecyclerView recyclerView = view.findViewById(R.id.sessionsRecyclerView);
//
//        SessionsRecyclerAdapter sessionsRecyclerAdapter = new SessionsRecyclerAdapter(view.getContext(), data, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.setAdapter(sessionsRecyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // NEW STUFF
        mProgressBar = view.findViewById(R.id.progress_bar);

        mViewModel = new ViewModelProvider(requireActivity()).get(SessionHistoryViewModel.class);
        mViewModel.init();

        mAdapter = new SessionsRecyclerAdapter(view.getContext(), mViewModel.getSessions().getValue(), this);
        mViewModel.getSessions().observe(getViewLifecycleOwner(), sessions -> mAdapter.notifyDataSetChanged());
        mViewModel.getIsUpdating().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showProgressBar();
            } else {
                hideProgressBar();
                recyclerView.smoothScrollToPosition(mViewModel.getSessions().getValue().size()-1);
            }
        });

        initRecyclerView(view);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(String view) {

    }
}