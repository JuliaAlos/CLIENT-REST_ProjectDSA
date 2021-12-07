package edu.upc.dsa;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoTemplate extends AppCompatActivity {

    VideoView videoBackground;
    MediaPlayer mediaPlayer;
    int currentPositionVideo = 0;

    /**

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangar);

        videoBackground = (VideoView) findViewById(R.id.videoBackgroundID);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hangar_background_video);

        videoBackground.setVideoURI(uri);
        videoBackground.start();

        videoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                if (currentPositionVideo != 0){
                    mediaPlayer.seekTo(currentPositionVideo);
                    mediaPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        currentPositionVideo = mediaPlayer.getCurrentPosition();
        videoBackground.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        videoBackground.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
*/

}
