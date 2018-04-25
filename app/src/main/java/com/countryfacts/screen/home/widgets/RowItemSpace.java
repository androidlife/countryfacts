package com.countryfacts.screen.home.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 */

public class RowItemSpace extends RecyclerView.ItemDecoration {

    private final int left, top, right, bottom;

    public RowItemSpace(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION)
            return;
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(left, top, right, bottom);
        } else {
            outRect.set(left, top, right, 0);
        }

    }
}
