package com.hallucind.smartaakband;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hallucind.smartaakband.Band.Band;
import com.hallucind.smartaakband.Band.BandHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RecyclerListAdapterHorizontal extends RecyclerView.Adapter {
    private ArrayList<Band> list = new ArrayList<>();

    public RecyclerListAdapterHorizontal(ArrayList<Band> list) {
        this.list.addAll(list);

        // För att kunna visa enhetens position
        Band band = new Band("Du", 0);
        this.list.remove(this.list.size()-1);
        this.list.add(0, band);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_horizontal, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int safePosition = holder.getAdapterPosition();

        ((ItemViewHolder) holder).nameView.setText(list.get(safePosition).getName());
        ((ItemViewHolder) holder).bandColor.setBackground(BandHandler.getGradientDrawable(list, safePosition));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Initialiserar ett band
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView nameView;
        public final View bandColor;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameView = itemView.findViewById(R.id.bandName);
            bandColor = itemView.findViewById(R.id.bandColor);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
