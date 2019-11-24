package com.example.retofinaaaaaaaaaal.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.retofinaaaaaaaaaal.R;

public class Shot {
    public static final float INIT_X = 100;
    public static final float INIT_Y = 100;
    public static final int SPRITE_SIZE_WIDTH = 20;
    public static final int SPRITE_SIZE_HEIGTH = 10;
    public static final float GRAVITY_FORCE = 10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;
    private int shooter = 0;
    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteIcecreamCar;
    private boolean isJumping;

    public Shot(Context context, float screenWidth, float screenHeigth, float initialX, float initialY, int shooter) {
        speed = 15;
        positionX = initialX;
        positionY = initialY;
        this.shooter = shooter;
        isJumping = false;
        //assignSprite(context);

        this.maxX = screenWidth - SPRITE_SIZE_WIDTH;
        this.maxY = screenHeigth - SPRITE_SIZE_HEIGTH;
    }

    public void assignSprite(Context context){
        Bitmap originalBitmap;
        if(shooter==0){
            originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_bullet);
        }
        else{
            originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_bullet);
        }
        spriteIcecreamCar = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);
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

    public Bitmap getSpriteIcecreamCar() {
        return spriteIcecreamCar;
    }

    public void setSpriteIcecreamCar(Bitmap spriteIcecreamCar) {
        this.spriteIcecreamCar = Bitmap.createScaledBitmap(spriteIcecreamCar, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }


    public int getShooter() {
        return shooter;
    }

    public void setShooter(int shooter) {
        this.shooter = shooter;
    }

    public void updateInfo() {
        if (shooter == 0) {
            positionX = positionX + speed;
        } else if (shooter == 1) {
            positionX = positionX - 3 * speed / 4;
        }

        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
    }
}