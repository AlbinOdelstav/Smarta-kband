package com.hallucind.smartaakband;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hallucind.smartaakband.Band.BandHandler;
import com.hallucind.smartaakband.Barcode.BarcodeActivity;
import com.hallucind.smartaakband.Screens.BandInfoActivity;
import com.hallucind.smartaakband.Screens.NewNameActivity;

public class RecyclerListFragment extends Fragment {
    final RecyclerListAdapter adapter;

    public RecyclerListFragment() {
        adapter = new RecyclerListAdapter(BandHandler.bands);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view;

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new RecyclerListAdapter.ClickListener() {

            @Override
            public void onAddNameClick(int position, View v) {
                Intent intent = new Intent(getActivity(), BarcodeActivity.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }

            @Override
            public void onItemClick(int position, View v) {
                if (!(position == BandHandler.bands.size()-1)) {
                    // mer info om valt band
                    Intent intent = new Intent(getActivity(), BandInfoActivity.class);
                    intent.putExtra("name", BandHandler.bands.get(position).getName());
                    intent.putExtra("battery", BandHandler.bands.get(position).getBattery());
                    intent.putExtra("id", position);
                    startActivity(intent);

                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        });
    }
}