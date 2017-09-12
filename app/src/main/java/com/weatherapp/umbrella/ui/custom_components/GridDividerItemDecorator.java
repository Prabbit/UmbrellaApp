package com.weatherapp.umbrella.ui.custom_components;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weatherapp.umbrella.utils.Utility;


/**
 * Created by CodeWord on 9/12/2017.
 */
public class GridDividerItemDecorator extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)%4==0) {
            outRect.left = 0;
        }else if (parent.getChildAdapterPosition(view) %4==3){
            outRect.left = Utility.dpToPx(12, parent.getContext());
            outRect.right = 0;
        }else {
            outRect.left=Utility.dpToPx(12, parent.getContext());
        }
    }
}