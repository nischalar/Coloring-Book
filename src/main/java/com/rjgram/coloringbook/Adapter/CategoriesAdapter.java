package com.rjgram.coloringbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.rjgram.coloringbook.Activity.ImageGalleryActivity;
import com.rjgram.coloringbook.Model.CategoriesModel;
import com.rjgram.coloringbook.R;

import java.util.List;

public class CategoriesAdapter extends PagerAdapter {
    private List<CategoriesModel> models;
    private Context context;

    public CategoriesAdapter(List<CategoriesModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return models.get(position).getTitle();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView;
        TextView textView;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.items, container, false);

        imageView = view.findViewById(R.id.img_categories);
        textView = view.findViewById(R.id.tx_title);
        imageView.setImageResource(models.get(position).getImgCategories());
        textView.setText(models.get(position).getTitle());
        container.addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = (String) getPageTitle(position);
                Intent i = new Intent(context, ImageGalleryActivity.class);
                i.putExtra("id", position);
                i.putExtra("title",title);
                context.startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }


}





