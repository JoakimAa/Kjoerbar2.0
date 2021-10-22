package com.illusion_softworks.kjoerbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.model.Beverage;

import java.util.List;

public class BeverageRecyclerAdapter extends RecyclerView.Adapter<BeverageRecyclerAdapter.BeverageViewHolder>{

    private LayoutInflater mInflater;
    private List<Beverage> dataSet;

    public BeverageRecyclerAdapter(Context context, List<Beverage> dataSet) {
        this.mInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public BeverageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.fragment_beverage_catalog, viewGroup, false);

        View view = mInflater.inflate(R.layout.fragment_beverage_detail, viewGroup, false);

        return new BeverageViewHolder(view);
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

    class BeverageViewHolder extends RecyclerView.ViewHolder {
        private View.OnClickListener clickListener = new View.MyOnClickListener();
        private TextView textView;

        public BeverageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.beverageNameTextView);
        }

        public void bind(Beverage currentData) {
            textView.setText(currentData.getName());
        }
    }
}
