package com.example.Sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.retofinaaaaaaaaaal.R;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;
    private static SoundPool soundPool;
    private static int imperial, gameOver, hitSound,destroy;

    public SoundPlayer(Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(SOUND_POOL_MAX).build();

        }else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }

        imperial = soundPool.load(context, R.raw.imperial,1);
        //gameOver = soundPool.load(context, R.raw.gameOver,1);
        //hitSound = soundPool.load(context, R.raw.hitSound,1);
        //destroy = soundPool.load(context, R.raw.destroy,1);
    }

    public void playImperialSound(){
        soundPool.play(imperial,1.0f,1.0f,1,0,1.0f);
    }
    public void playOverSound(){
        soundPool.play(gameOver,1.0f,1.0f,1,0,1.0f);
    }
    public void playHitSound(){
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playDestroySound(){
        soundPool.play(destroy,1.0f,1.0f,1,0,1.0f);
    }

}