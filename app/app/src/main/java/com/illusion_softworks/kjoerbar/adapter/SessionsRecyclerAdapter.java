package com.illusion_softworks.kjoerbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SessionsRecyclerAdapter extends RecyclerView.Adapter<SessionsRecyclerAdapter.SessionsViewHolder> {
    private final LayoutInflater mInflater;
    private List<Session> dataSet = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public SessionsRecyclerAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    public void addDataSet(List<Session> sessions) {
        dataSet = sessions;
        notifyItemRangeChanged(0, sessions.size() - 1);
    }

    @NonNull
    @Override
    public SessionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.session_card_layout, viewGroup, false);
        return new SessionsRecyclerAdapter.SessionsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsRecyclerAdapter.SessionsViewHolder holder, int position) {
        Session currentData = dataSet.get(position);
        holder.bind(currentData);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class SessionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnItemClickListener onItemClickListener;
        private final TextView mName;
        private final TextView mPerMill;
        private final TextView mDuration;


        public SessionsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mPerMill = itemView.findViewById(R.id.maxPerMill);
            mDuration = itemView.findViewById(R.id.duration);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Session currentData) {
            mName.setText(currentData.getName());
            mPerMill.setText(String.format(Locale.ENGLISH, "%.3f", currentData.getMaxPerMill()));
            mDuration.setText(String.valueOf(((currentData.getEndTime() - currentData.getStartTime()) / 1000)));
            long duration = currentData.getEndTime() - currentData.getStartTime();
            mDuration.setText(String.format(Locale.ENGLISH,
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(duration),
                    TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
                    TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))));
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }


}

