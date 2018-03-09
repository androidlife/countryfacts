package com.countryfacts.screen.home.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 */

public class RowItemSpace extends RecyclerView.ItemDecoration {
    int itemSpace;
    int startEndSpace;

    public RowItemSpace(int itemSpace, int startEndSpace) {
        this.itemSpace = itemSpace;
        this.startEndSpace = startEndSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION)
            return;
        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(itemSpace, 0, startEndSpace, 0);
        } else if (position == 0) {
            outRect.set(startEndSpace, 0, 0, 0);
        } else {
            outRect.set(itemSpace, 0, 0, 0);
        }

    }
}
