package com.countryfacts.screen.home.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.countryfacts.R;
import com.countryfacts.model.CountryInfo;

import java.util.List;

/**
 * Our RecyclerView Adapter class
 */

public class HomeAdapter extends RecyclerView.Adapter<RowViewHolder> {


    private final List<CountryInfo> countryInfoList;

    public HomeAdapter(List<CountryInfo> countryInfoList) {
        this.countryInfoList = countryInfoList;
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
        return countryInfoList.size();
    }

    private CountryInfo getItemAt(int position) {
        return countryInfoList.get(position);
    }


}
