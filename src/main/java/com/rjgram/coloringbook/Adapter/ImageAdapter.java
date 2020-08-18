package com.rjgram.coloringbook.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.rjgram.coloringbook.Model.CategoriesModel;
import com.rjgram.coloringbook.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public Integer[] images;

    public ImageAdapter(Context mContext, Integer[] images) {
        this.mContext = mContext;
        this.images = images;
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public int getCount() {
        return images.length;
    }



    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(285, 285));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackgroundColor(Color.WHITE);
           // imageView.setBackgroundResource(R.drawable.border_dots);
        } else {
            imageView = (ImageView) convertView;
        }
        // imageView.setImageResource(mThumbIds[position]);

        imageView.setImageResource(images[position]);

        return imageView;
    }

    // Keep all Images in array
 /*   public Integer[] mThumbIds = {
            R.drawable.cow, R.drawable.camel,
            R.drawable.dog, R.drawable.frog,
            R.drawable.donkey, R.drawable.fish,
            R.drawable.elephant, R.drawable.duck,
            R.drawable.butterfly

    };*/
}

