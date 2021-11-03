package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.adapter.SessionsRecyclerAdapter;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionHistoryFragment extends Fragment implements OnItemClickListener {
    private static final List<Session> data = UserDataHandler.getSessions();

    public SessionHistoryFragment() {
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
        requireActivity().setTitle(getString(R.string.session_history));
        View view = inflater.inflate(R.layout.fragment_session_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.sessionsRecyclerView);

        SessionsRecyclerAdapter sessionsRecyclerAdapter = new SessionsRecyclerAdapter(view.getContext(), data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(sessionsRecyclerAdapter);

        return view;

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(String view) {

    }
}