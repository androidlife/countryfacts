package com.countryfacts.screen.home;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.countryfacts.R;
import com.countryfacts.model.Country;
import com.countryfacts.screen.home.widgets.HomeAdapter;
import com.countryfacts.screen.home.widgets.RowItemSpace;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class HomeView implements HomeInteraction.View {


    @BindView(R.id.swipe_ref_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_info)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error)
    TextView tvError;

    int viewState;
    HomeInteraction.Controller controller;
    HomeAdapter homeAdapter;

    public HomeView(View view, HomeInteraction.Controller controller) {
        ButterKnife.bind(this, view);
        this.controller = controller;
        initViews(view.getContext());
    }

    private void initViews(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RowItemSpace rowItemSpace = new RowItemSpace(context.getResources().getDimensionPixelSize(R.dimen.row_space_top),
                context.getResources().getDimensionPixelSize(R.dimen.row_space_bottom));
        recyclerView.addItemDecoration(rowItemSpace);

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showData(Country country) {
        controller.getActionBar().setTitle(country.name);
        homeAdapter = new HomeAdapter(country.countryInfos);
        recyclerView.setAdapter(homeAdapter);

    }

    @Override
    public int getState() {
        return viewState;
    }

    @Override
    public void setState(int state) {
        viewState = state;
    }
}
