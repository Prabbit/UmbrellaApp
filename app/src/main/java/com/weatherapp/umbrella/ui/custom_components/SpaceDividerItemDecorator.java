package com.weatherapp.umbrella.ui.custom_components;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weatherapp.umbrella.utils.Utility;


/**
 * Created by CodeWord on 9/12/2017.
 */
public class SpaceDividerItemDecorator extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = Utility.dpToPx(8, parent.getContext());
        }
        outRect.bottom = Utility.dpToPx(8, parent.getContext());
    }
}