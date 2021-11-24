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

import java.util.List;

public class SessionsRecyclerAdapter extends RecyclerView.Adapter<SessionsRecyclerAdapter.SessionsViewHolder> {
    private final LayoutInflater mInflater;
    private final List<Session> dataSet;
    private final OnItemClickListener onItemClickListener;

    public SessionsRecyclerAdapter(Context context, List<Session> dataSet, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SessionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.session_in_history, viewGroup, false);
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
        private final TextView textViewName;

        public SessionsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.beverageNameTextView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Session currentData) {
            textViewName.setText(currentData.getName());
        }

        @Override
        public void onClick(View view) {
            if (view == itemView)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}

