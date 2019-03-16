package com.softec.moviehub;

import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;
    Button playBtn, stopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.watchVideo);
        playBtn = findViewById(R.id.playBtn);
        stopBtn = findViewById(R.id.stopBtn);

        String str = "https://firebasestorage.googleapis.com/v0/b/moviehub-1c165.appspot.com/o/nature%201080p%20-%20Bing%20Videos_3.appleuniversal.mp4?alt=media&token=bbb0af9a-f417-433e-9dec-cee93d89e7c9";
        Uri uri = Uri.parse(str);

        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 videoView.stopPlayback();
            }
        });
    }
}
