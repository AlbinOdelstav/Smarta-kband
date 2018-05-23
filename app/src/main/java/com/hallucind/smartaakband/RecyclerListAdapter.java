package com.hallucind.smartaakband;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hallucind.smartaakband.Band.Band;
import com.hallucind.smartaakband.Band.BandHandler;

import java.util.ArrayList;

public class RecyclerListAdapter extends RecyclerView.Adapter {

    private ArrayList<Band> list;
    private static ClickListener clickListener;

    public RecyclerListAdapter(ArrayList<Band> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
                return new ItemViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add, parent, false);
                return new addItemViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int safePosition = holder.getAdapterPosition();

        if (!(safePosition == list.size()-1)) {
            ((ItemViewHolder) holder).nameView.setText(list.get(safePosition).getName());
            ((ItemViewHolder) holder).bandColor.setBackground(BandHandler.getGradientDrawable(list, safePosition));
        }
    }

    // Hanterar om det är ett band som ska visas i listan, eller "Lägg till ett band"-knappen
    @Override
    public int getItemViewType(int position) {
        if (position == list.size()-1) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerListAdapter.clickListener = clickListener;
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
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    // Initialiserar "Lägg till ett band"-knappen
    public static class addItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final RelativeLayout bandItem;

        public addItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            bandItem = itemView.findViewById(R.id.bandItem);

            bandItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onAddBandClick(getAdapterPosition(), view);
                }
            });
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onAddBandClick(int position, View v);
        void onItemClick(int position, View v);
    }
}
