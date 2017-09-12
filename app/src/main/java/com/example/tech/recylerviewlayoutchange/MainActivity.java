package com.example.tech.recylerviewlayoutchange;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.ForwardingListener;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;

import com.example.tech.recylerviewlayoutchange.adapter.ItemAdapter;
import com.example.tech.recylerviewlayoutchange.model.ItemModel;
import com.example.tech.recylerviewlayoutchange.view.ItemDecorator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ScaleGestureDetector mScaleDetector;
    private ProgressDialog mProgressDialog;
    String TAG = getClass().getName();
    int no_of_columns = 3;
    int MAX_NO_OFCOL = 5 ;
    static int MAX_NO_OF_PHOTOS = 8;
    private List<ItemModel> mPhotoUris;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ItemDecorator itemDecorator;
    private  ItemAdapter mAdapter;
    public interface adapterinteraction{
        public void changeColumns(boolean shouldChange );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching Photos...");
        mProgressDialog.setCancelable(false);

        ArrayList<ItemModel> items = new ArrayList<>();

        Button inc_btn = (Button) findViewById(R.id.btn_inc);
        Button dec_btn = (Button) findViewById(R.id.btn_dec);
        mPhotoUris = new ArrayList<>();
        for(int i=0 ; i< 10 ;i++){
            mPhotoUris.add(new ItemModel("aoihsaodnasodnasdo "));
//            items.add(itemModel);
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        mAdapter = new ItemAdapter(mPhotoUris, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this , no_of_columns);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        itemDecorator = new ItemDecorator(20);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScaleDetector.onTouchEvent(event);
                return false;
            }
        });
        adapterinteraction check = new adapterinteraction() {
            @Override
            public void changeColumns(boolean shouldChange) {
                if(shouldChange && MAX_NO_OFCOL > no_of_columns)
                    no_of_columns++;
                else if (no_of_columns >1)
                    no_of_columns--;

                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext() , no_of_columns));
            }
        };
        mAdapter.setLayoutChanges(check);
        mAdapter.setLocalDecoratorSpace(itemDecorator.getmSpace());

        recyclerView.setAdapter(mAdapter);


//        requestPermission();




        inc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.incBasePadding(1);
                mAdapter.notifyDataSetChanged();
//                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext() , 6));

            }
        });

        dec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.decBasePadding(1);
                mAdapter.notifyDataSetChanged();
//                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext() , 6));

            }
        });
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
//                Log.i(TAG, "onScaleEnd: ");
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
//                Log.i(TAG, "onScaleBegin: ");
                return true;
            }
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                Log.i( TAG, "zoom ongoing, scale: " + detector.getScaleFactor());

                int val;
                if( detector.getScaleFactor() < 1 && no_of_columns != MAX_NO_OFCOL){
//                    mAdapter.incBasePadding((int) (detector.getScaleFactor()* 3));

                    itemDecorator.setmSpace((int)  Math.ceil( detector.getScaleFactor() ));

                }
                else if(detector.getScaleFactor() >  1 && no_of_columns != 1) {
                    val =- (int) detector.getScaleFactor();
                    itemDecorator.setmSpace(val);
                }
                else if (no_of_columns == MAX_NO_OFCOL)
                    itemDecorator.setmSpace( 0);
                else if (no_of_columns == 1)
                    itemDecorator.setmSpace( 0);
                mAdapter.setLocalDecoratorSpace(itemDecorator.getmSpace());
                mAdapter.notifyDataSetChanged();

                Log.i(TAG, "onScale: no_of_columns "+no_of_columns + "   "+detector.getScaleFactor() + "  "+(int) - Math.ceil( detector.getScaleFactor() ));

                return false;
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
//        Log.i(getClass().getSimpleName(), "onTouchEvent: asdasdas ");
        return true;
    }
//
//
}
