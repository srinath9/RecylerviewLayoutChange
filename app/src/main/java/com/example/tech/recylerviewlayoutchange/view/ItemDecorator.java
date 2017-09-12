package com.example.tech.recylerviewlayoutchange.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tech on 12/9/17.
 */

public class ItemDecorator extends RecyclerView.ItemDecoration {
    private  int mSpace;

    public ItemDecorator(int space) {
        this.mSpace = space;
    }


    public void setmSpace(int mSpace){
        this.mSpace += mSpace;

        System.out.println("mSpace  "+mSpace);
        if ( this.mSpace < -20 )
            this.mSpace = -20;
        else if ( this.mSpace >40 )
            this.mSpace = 40;
    }

    public int getmSpace() {
        return mSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        outRect.top = mSpace;
    }
}