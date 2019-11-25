package com.example.retofinaaaaaaaaaal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Sound.SoundPlayer;

public class GamePlay extends AppCompatActivity {

    private GameSurfaceView gameSurfaceView;
    private SoundPlayer soundPlayer;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_paly);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getRealSize(screenSize);

        gameSurfaceView = new GameSurfaceView(this, screenSize.x, screenSize.y);
        setContentView(gameSurfaceView);

        soundPlayer = new SoundPlayer(this);

    }
    @Override
    protected void onPause() {
        super.onPause();
        gameSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.resume();
    }

}