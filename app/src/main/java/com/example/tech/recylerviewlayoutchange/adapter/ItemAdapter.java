package com.example.tech.recylerviewlayoutchange.adapter;

import android.content.Context;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tech.recylerviewlayoutchange.MainActivity;
import com.example.tech.recylerviewlayoutchange.R;
import com.example.tech.recylerviewlayoutchange.model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.translationZ;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private final Context mContext;
    private float mScaleFactor = 1.f;

    private List<ItemModel> ItemModelsList;
    private int basePadding = 20;
    private final  int COMMON_PADDING = 20;
    private final  int COMMON_PADDING_MAX = 25;

    MainActivity.adapterinteraction check;

    private int localDecoratorSpace;

    ScaleGestureDetector mScaleDetector;

    String TAG = getClass().getName();

    public void setLayoutChanges(MainActivity.adapterinteraction check) {
        this.check = check;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout ll_each;
        public ImageView iv_each;

        public MyViewHolder(View view) {
            super(view);

            ll_each = (LinearLayout) view.findViewById(R.id.ll_each_component);

            int finalPadding =  basePadding ;

            changeWidth(finalPadding);



            Log.i("adapter ", "MyViewHolder: finalPadding "+finalPadding);

        }

        public void changeWidth(int finalPadding){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(finalPadding, finalPadding, finalPadding, finalPadding);
            ll_each.setLayoutParams(lp);

        }
    }


    public void setLocalDecoratorSpace(int localDecoratorSpace) {
        this.localDecoratorSpace = localDecoratorSpace;
    }

    public void incBasePadding(int basePaddingnew) {
        this.basePadding = this.basePadding + basePaddingnew;
//        Log.i("base ", "incBasePadding: "+basePadding);

        if(basePadding > 40 ){
//            check.changeColumns(true);
            basePadding = 40;
        }
    }

    public void decBasePadding(int basePaddingnew) {
        basePadding = basePadding - basePaddingnew;
        if(basePadding < -10) {
//            check.changeColumns(false);
            basePadding=-10;
        }
    }

    public ItemAdapter(List<ItemModel> ItemModelsList, Context context) {
        this.ItemModelsList = ItemModelsList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_item_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
//        String itemModel = ItemModelsList.get(position);
        int finalPadding;
        if( ItemModelsList.get(position).isSelected()) {
             finalPadding = -10;

            if( localDecoratorSpace < 5){
                holder.ll_each.setBackgroundColor(Color.GREEN);
                holder.ll_each.bringToFront();
                ViewCompat.setTranslationZ( holder.ll_each , 10);
            }
            else {
                holder.ll_each.setBackgroundColor(Color.GRAY);
                ViewCompat.setTranslationZ( holder.ll_each , 0);
            }
        }else{
//            finalPadding = basePadding;
            holder.ll_each.setBackgroundColor(Color.GRAY);
            finalPadding = 0;
            ViewCompat.setTranslationZ( holder.ll_each , 0);
        }


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(finalPadding, finalPadding, finalPadding, finalPadding);
        holder.ll_each.setLayoutParams(lp);

        View itemView = holder.ll_each;
        int test1[] = new int[2];
        Rect val = new Rect();
        itemView.getFocusedRect(val);
        itemView.getLocationOnScreen(test1);
//        Log.i(TAG, "onCreateViewHolder: "+ test1[0]+ " "+ test1[1] + " "+val);
        Log.i(TAG, "onCreateViewHolder: "+ localDecoratorSpace);


        holder.ll_each.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ItemModelsList.get(position).toggleData();
               notifyDataSetChanged();


            }
        });

    }

    @Override
    public int getItemCount() {
        return ItemModelsList.size();
    }

}
