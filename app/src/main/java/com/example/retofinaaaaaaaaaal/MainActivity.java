package com.example.retofinaaaaaaaaaal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Sound.SoundPlayer;


public class MainActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);
        soundPlayer.playImperialSound();

     }

    public void playGame (View view){
        startActivity(new Intent(this, GamePlay.class));

    }

}
