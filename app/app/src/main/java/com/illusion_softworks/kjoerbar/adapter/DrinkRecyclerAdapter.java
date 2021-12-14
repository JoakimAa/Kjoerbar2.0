package com.illusion_softworks.kjoerbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DrinkRecyclerAdapter extends RecyclerView.Adapter<DrinkRecyclerAdapter.BeverageViewHolder> {
    private final LayoutInflater mInflater;
    private List<Drink> dataSet = new ArrayList<>();
    private final OnItemClickListener onItemClickListener;

    public DrinkRecyclerAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    public void addDataSet(List<Drink> drinks) {
        dataSet = drinks;
        notifyItemRangeChanged(0, drinks.size()-1);
    }

    public Drink getDrink(int position) {
        return dataSet.get(position);
    }

    public void addDrink(Drink drink, int position) {
        // Not in use yet
        dataSet.add(drink);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public BeverageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.card_layout_catalog_drink, viewGroup, false);
        return new BeverageViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BeverageViewHolder holder, int position) {
        Drink currentData = dataSet.get(position);
        holder.bind(currentData);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class BeverageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName, textViewPercent, textViewVolume;
        private final ImageView imageView;
        private final OnItemClickListener onItemClickListener;

        public BeverageViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.beverageNameTextView);
            textViewPercent = itemView.findViewById(R.id.beverageAlcoholPercentageTextView);
            textViewVolume = itemView.findViewById(R.id.beverageVolumeTextView);
            imageView = itemView.findViewById(R.id.imageView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        public void bind(Drink currentDrink) {
            textViewName.setText(currentDrink.getName());
            textViewPercent.setText(String.format(Locale.ENGLISH, "%s %%", currentDrink.getPercent()));
            textViewVolume.setText(String.format(Locale.ENGLISH, "%s dl", currentDrink.getVolume()));
        }

        @Override
        public void onClick(View view) {
            if (view == itemView)
                onItemClickListener.onItemClick(getBindingAdapterPosition());
            else if (view == imageView) {
                onItemClickListener.onItemClick("beverageDetailFragment", getBindingAdapterPosition());
            }
        }
    }
}
