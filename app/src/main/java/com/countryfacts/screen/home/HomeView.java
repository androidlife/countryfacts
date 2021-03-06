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
 * View separation layer implementing View contracts
 */

public class HomeView implements HomeContracts.View {


    @BindView(R.id.swipe_ref_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_info)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error)
    TextView tvError;

    private int viewState;
    private final HomeContracts.Controller controller;

    public HomeView(View view, HomeContracts.Controller controller) {
        ButterKnife.bind(this, view);
        this.controller = controller;
        initViews(view.getContext());
    }

    //initializing the view based upon R.layout.activity_home
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
        swipeRefreshLayout.setOnRefreshListener(controller::onRefresh);

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
    public void onClick() {
        controller.onRetry();
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(tvError.getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateList(Country country) {
        controller.getActionBar().setTitle(country.name);
        recyclerView.setVisibility(View.VISIBLE);
        HomeAdapter homeAdapter = new HomeAdapter(country.countryInfoList);
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

    @Override
    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }
}
