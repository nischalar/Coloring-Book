package com.rjgram.coloringbook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.rjgram.coloringbook.R;

public class SplashScreenActivity extends AppCompatActivity {
    VideoView splashScreenVideo;
    private boolean ispaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashScreenVideo = findViewById(R.id.splash_screen);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_screen_video);
        if (video != null) {
            splashScreenVideo.setVideoURI(video);

            splashScreenVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0f, 0f);
                   // mediaPlayer.setLooping(true);
                }
            });
            splashScreenVideo.start();

            splashScreenVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {

                    startNextActivity();
                }
            });

        } else {
            startNextActivity();
        }

    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ispaused) {
            startNextActivity();
        }

    }
}
