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
import com.illusion_softworks.kjoerbar.model.Beverage;

import java.util.List;

public class BeverageRecyclerAdapter extends RecyclerView.Adapter<BeverageRecyclerAdapter.BeverageViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Beverage> dataSet;
    private final OnItemClickListener onItemClickListener;

    public BeverageRecyclerAdapter(Context context, List<Beverage> dataSet, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BeverageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_beverage_detail, viewGroup, false);
        return new BeverageViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BeverageViewHolder holder, int position) {
        Beverage currentData = dataSet.get(position);
        holder.bind(currentData);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class BeverageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName, textViewPercent, textViewVolume;
        private final OnItemClickListener onItemClickListener;

        public BeverageViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.beverageNameTextView);
            textViewPercent = itemView.findViewById(R.id.beverageAlcoholPercentageTextView);
            textViewVolume = itemView.findViewById(R.id.beverageVolumeTextView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Beverage currentData) {
            textViewName.setText(currentData.getName());
            textViewPercent.setText(String.valueOf(currentData.getPercent()));
            textViewVolume.setText(String.valueOf(currentData.getVolume()));
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
