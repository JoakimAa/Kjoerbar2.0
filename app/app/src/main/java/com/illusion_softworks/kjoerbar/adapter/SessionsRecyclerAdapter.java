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
        private final TextView date;
        private final TextView userWeight;
        private final TextView sex;


        public SessionsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            userWeight = itemView.findViewById(R.id.userWeight);
            sex = itemView.findViewById(R.id.sex);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Session currentData) {
            date.setText(currentData.getName());
            userWeight.setText(String.valueOf(currentData.getMaxPerMill()));
            sex.setText(String.valueOf(((currentData.getEndTime() - currentData.getStartTime()) / 1000)));
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }


}

