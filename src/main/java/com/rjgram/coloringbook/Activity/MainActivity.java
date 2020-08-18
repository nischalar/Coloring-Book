package com.rjgram.coloringbook.Activity;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.rjgram.coloringbook.Adapter.CategoriesAdapter;
import com.rjgram.coloringbook.Model.CategoriesModel;
import com.rjgram.coloringbook.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ViewPager viewPager;
List<CategoriesModel>models;
CategoriesAdapter adapter;
Integer[] colors = null;
ArgbEvaluator argbEvaluator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        argbEvaluator =  new ArgbEvaluator();
        models = new ArrayList<>();
        models.add(new CategoriesModel(R.drawable.animal,getString(R.string.animals_and_birds)));
        models.add(new CategoriesModel(R.drawable.vegetables,getString(R.string.vegetable_and_fruits)));
        models.add(new CategoriesModel(R.drawable.merry_christmas,getString(R.string.merry_christmas)));
        models.add(new CategoriesModel(R.drawable.prince_and_princess,getString(R.string.disney)));
        models.add(new CategoriesModel(R.drawable.transport2,getString(R.string.transport)));


        adapter = new CategoriesAdapter(models,this);
        viewPager= findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(400,0,400,0);

        colors = new Integer[]{
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5),
                getResources().getColor(R.color.color6)
        };
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount()) && position < (colors.length-1)){
                    viewPager.setBackgroundColor((Integer)argbEvaluator
                            .evaluate(positionOffset,colors[position],colors[position+1]));
                }else{
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}