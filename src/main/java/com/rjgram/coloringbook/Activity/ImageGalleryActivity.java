package com.rjgram.coloringbook.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.rjgram.coloringbook.Adapter.ImageAdapter;
import com.rjgram.coloringbook.R;
import com.rjgram.coloringbook.common;



public class ImageGalleryActivity extends AppCompatActivity {
    ImageAdapter adapter;
    String categoryName;
    public Integer[] images;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        GridView gridview = findViewById(R.id.grid_view);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {


            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            categoryName = bundle.getString("title");
        Log.d("TAG", "onCreate:  " + categoryName);
        switch (categoryName) {

            case "Animals and Birds":
                images = new Integer[]{
                        R.drawable.dog1, R.drawable.dog2,
                        R.drawable.dog3, R.drawable.fish2,
                        R.drawable.animal1, R.drawable.animal2,
                        R.drawable.animal3, R.drawable.animal4,
                        R.drawable.animal5, R.drawable.animal6,
                        R.drawable.animal7
                };
                break;
            case "Vegetable and Fruits":
                images = new Integer[]{

                        R.drawable.fruit1,
                        R.drawable.fruit2,
                        R.drawable.fruit3,
                        R.drawable.fruit4,
                        R.drawable.fruit5

                };
                break;
            case "Merry Christmas":
                images = new Integer[]{

                        R.drawable.christmas1,
                        R.drawable.christmas2,
                        R.drawable.christmas3,
                        R.drawable.christmas4

                };
                break;
            case "Disney":
                images = new Integer[]{
                        R.drawable.disney1,
                        R.drawable.disney2, R.drawable.disney3
                };
                break;
            case "Transport":
                images = new Integer[]{

                        R.drawable.vehical1,
                        R.drawable.transport4
                        , R.drawable.vehical3
                };
                break;
        }
        if (images != null) {


            adapter = new ImageAdapter(this, images);
            gridview.setAdapter(adapter);

        }


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {


                common.SELECTED_IMAGE = images[position];
                Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
                startActivity(i);
            }
        });
    }



    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}