package com.example.avoid_the_obsticle_game_part2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import com.example.avoid_the_obsticle_game_part2.R;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CrashSound {
    private Context context;
    private Executor executor;
    private Handler handler;
    private MediaPlayer mediaPlayer;


    public CrashSound(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void playSound(){
        executor.execute(() ->{
            //mediaPlayer = MediaPlayer.create(context, R.raw.crashSound);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        });
    }

    public void stopSound(){
        if(mediaPlayer != null){
            executor.execute(() -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }
}
