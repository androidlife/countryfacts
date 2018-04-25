package com.countryfacts.screen.home.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.countryfacts.R;
import com.countryfacts.model.CountryInfo;

import java.util.List;

/**
 */

public class HomeAdapter extends RecyclerView.Adapter<RowViewHolder> {


    private final List<CountryInfo> countryInfos;

    public HomeAdapter(List<CountryInfo> countryInfos) {
        this.countryInfos = countryInfos;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_view, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        holder.setData(getItemAt(position));
    }

    @Override
    public int getItemCount() {
        return countryInfos.size();
    }

    private CountryInfo getItemAt(int position) {
        return countryInfos.get(position);
    }


}
