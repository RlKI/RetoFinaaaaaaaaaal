package com.example.retofinaaaaaaaaaal.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.retofinaaaaaaaaaal.R;

public class player {


    public static final float INIT_X = 100;
    public static final float INIT_Y = 100;
    public static final int CADENCIA = 10;
    public static final int SPRITE_SIZE_WIDTH = 300;
    public static final int SPRITE_SIZE_HEIGTH = 200;
    public static final float GRAVITY_FORCE = 10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteIcecreamCar;
    private boolean isJumping;
    public boolean hasShield = false;
    public int reload = 0;
    public boolean isDead = false;

    public player(Context context, float screenWidth, float screenHeigth) {
        speed = 1;
        positionX = this.INIT_X;
        positionY = this.INIT_Y;
        isJumping = false;
        //Getting bitmap from resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.e1_milleniumfalcon);
        spriteIcecreamCar = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteIcecreamCar.getWidth() / 2);
        this.maxY = screenHeigth - spriteIcecreamCar.getHeight();
    }

    public void nowIsDead(Context context) {
        isDead = true;
        Bitmap originalBitmap;
        originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_ship_explotion);

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

    public void updateInfo() {

        reload++;
        if (reload == CADENCIA) {
            reload = 0;
        }
        if (isJumping) {
            speed += 5;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        this.positionY -= speed - GRAVITY_FORCE;

        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }


    }
}
