package com.countryfacts.screen.home;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.countryfacts.R;
import com.countryfacts.model.Country;
import com.countryfacts.screen.home.widgets.HomeAdapter;
import com.countryfacts.screen.home.widgets.RowItemSpace;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        Resources resources = context.getResources();
        int left = resources.getDimensionPixelSize(R.dimen.row_space_left);
        int right = resources.getDimensionPixelSize(R.dimen.row_space_right);
        int top = resources.getDimensionPixelSize(R.dimen.row_space_top);
        int bottom = resources.getDimensionPixelSize(R.dimen.row_space_bottom);
        RowItemSpace rowItemSpace = new RowItemSpace(left, top, right, bottom);
        recyclerView.addItemDecoration(rowItemSpace);

    }

    @Override
    public void showError(boolean show) {
        if (show) {
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @OnClick({R.id.tv_error})
    public void onClick(View view) {
        controller.onReload();
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(tvError.getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(Country country) {
        controller.getActionBar().setTitle(country.name);
        recyclerView.setVisibility(View.VISIBLE);
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
