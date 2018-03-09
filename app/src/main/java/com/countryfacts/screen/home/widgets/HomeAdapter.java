package com.countryfacts.screen.home.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.countryfacts.model.CountryInfo;

import java.util.List;

/**
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.RowViewHolder> {


    private List<CountryInfo> countryInfos;

    public HomeAdapter(List<CountryInfo> countryInfos) {
        this.countryInfos = countryInfos;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowView rowView = new RowView(parent.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rowView.setLayoutParams(layoutParams);
        return new RowViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        holder.rowView.setData(getItemAt(position));
    }

    @Override
    public int getItemCount() {
        return countryInfos.size();
    }

    private CountryInfo getItemAt(int position) {
        return countryInfos.get(position);
    }

    static class RowViewHolder extends RecyclerView.ViewHolder {

        RowView rowView;

        public RowViewHolder(RowView rowView) {
            super(rowView);
            this.rowView = rowView;
        }
    }
}
