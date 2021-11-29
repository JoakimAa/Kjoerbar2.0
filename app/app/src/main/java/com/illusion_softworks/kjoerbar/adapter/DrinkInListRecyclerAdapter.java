package com.illusion_softworks.kjoerbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apachat.swipereveallayout.core.SwipeLayout;
import com.apachat.swipereveallayout.core.ViewBinder;
import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.interfaces.OnItemClickListener;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.ArrayList;
import java.util.Locale;

public class DrinkInListRecyclerAdapter extends RecyclerView.Adapter<DrinkInListRecyclerAdapter.BeverageViewHolder> {

    private final ViewBinder viewBinder = new ViewBinder();
    private final LayoutInflater mInflater;
    private final ArrayList<AlcoholUnit> dataSet;
    private final OnItemClickListener onItemClickListener;

    public DrinkInListRecyclerAdapter(Context context, ArrayList<AlcoholUnit> dataSet, OnItemClickListener onItemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
        viewBinder.setOpenOnlyOne(true);
    }

    @NonNull
    @Override
    public BeverageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.swipe_reveal_layout, viewGroup, false);
        return new BeverageViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BeverageViewHolder holder, int position) {
        AlcoholUnit currentData = dataSet.get(position);
        holder.bind(currentData);
        viewBinder.bind(holder.swipelayout, currentData.getDrink().getName());
        viewBinder.setOpenOnlyOne(true);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class BeverageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName, textViewPercent, textViewVolume;
        private final ImageView imageViewInfo, imageViewDelete;
        private final SwipeLayout swipelayout;
        private final OnItemClickListener onItemClickListener;

        public BeverageViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.beverageNameTextView);
            textViewPercent = itemView.findViewById(R.id.beverageAlcoholPercentageTextView);
            textViewVolume = itemView.findViewById(R.id.beverageVolumeTextView);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            imageViewInfo = itemView.findViewById(R.id.imageViewInfo);
            swipelayout = itemView.findViewById(R.id.swipeLayout);
            this.onItemClickListener = onItemClickListener;
            imageViewInfo.setOnClickListener(this);
            imageViewDelete.setOnClickListener(this);
        }

        public void bind(AlcoholUnit currentData) {
            Drink currentDrink = currentData.getDrink();

            textViewName.setText(currentDrink.getName());
            textViewPercent.setText(String.format(Locale.ENGLISH, "%s %%", String.valueOf(currentDrink.getPercent())));
            textViewVolume.setText(String.format(Locale.ENGLISH, "%s dl", String.valueOf(currentDrink.getVolume())));
        }

        @Override
        public void onClick(View view) {
            if (view == imageViewDelete)
                onItemClickListener.onItemClick(getAdapterPosition());
            else if (view == imageViewInfo) {
                String beverageDetailFragment = "beverageDetailFragment";
                onItemClickListener.onItemClick(beverageDetailFragment);
            }
        }
    }
}
