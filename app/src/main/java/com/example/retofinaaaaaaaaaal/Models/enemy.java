package com.example.retofinaaaaaaaaaal.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.retofinaaaaaaaaaal.R;

import java.util.Random;

public class enemy {
    public static final float INIT_X = 100;
    public static final float INIT_Y = 100;
    public static final int SPRITE_SIZE_WIDTH = 150;
    public static final int SPRITE_SIZE_HEIGTH = 110;
    public static final float GRAVITY_FORCE = 10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private int type = 0; //0 heal,1 ship, 2 rock
    public int reload = 0;
    public static final int CADENCIA = 100;

    private float maxY;
    public float maxX;

    public boolean isDead = false;
    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spritePerson;
    private boolean isJumping;


    public enemy(Context context, float screenWidth, float screenHeigth) {

        speed = 7;
        isJumping = false;
        //Getting bitmap from resource
        Random rand = new Random();
        int randomNum = rand.nextInt((2 - 0) + 2) + 0;
        this.type = randomNum;
        Bitmap originalBitmap;
        if (randomNum == 0) {
            originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_heart);
        } else if (randomNum == 1) {
            originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.galagaship);
        } else {
            originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.e2_rock);
        }
        spritePerson = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);


        float randomY = 0;
        positionX = screenWidth - spritePerson.getWidth();
        positionY = 0 + rand.nextFloat() * (screenHeigth - spritePerson.getHeight());
        this.maxX = screenWidth - (spritePerson.getWidth() / 2);
        this.maxY = screenHeigth - spritePerson.getHeight();

    }

    public void nowIsDead(Context context) {
        isDead = true;
        Bitmap originalBitmap;
        originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_ship_explotion);

        spritePerson = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpritePerson() {
        return spritePerson;
    }

    public void setSpritePerson(Bitmap spritePerson) {
        this.spritePerson = spritePerson;
    }

    public int typePerson() {
        return type;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }


    public void updateInfo() {
        if (!isDead) {
            reload++;
            if (reload == CADENCIA) {
                reload = 0;
            }

            positionX = positionX - speed;

            if (positionX < 0) {
                positionX = 0;
            }
            if (positionX > maxX) {
                positionX = maxX;
            }
        }
    }
}
