package com.example.retofinaaaaaaaaaal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.retofinaaaaaaaaaal.Models.Shot;
import com.example.retofinaaaaaaaaaal.Models.enemy;
import com.example.retofinaaaaaaaaaal.Models.player;

import java.util.ArrayList;
import java.util.Random;


public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isPlaying;
    private player player;
    private Paint paint;
    private Canvas canvas;
    private ArrayList<enemy> enemiesList = new ArrayList<>();
    private ArrayList<Shot> playerShotsList = new ArrayList<>();
    private ArrayList<Shot> enemiesShotsList = new ArrayList<>();
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private Context context;
    private float screenWith;
    private float screenHeight;
    private int points = 0;
    private int velAppearence = 1;
    private int healPoints = 100;
    private int enemiesAppear = 0;
    private int invaders = 0;
    private int levelMultiplier = 1;

    /**
     * Contructor
     *
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        this.context = context;
        this.screenHeight = screenHeight;
        this.screenWith = screenWith;
        player = new player(context, screenWith, screenHeight);
        paint = new Paint();
        holder = getHolder();
        isPlaying = true;

    }

    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
            if (player.isDead) {
                stop();

            }
        }

    }

    private void paintFrame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            shotUpdateInfo();
            validatePlayerShotImpact();
            generateStars();
            createEnemy();
            for (int _i = 0; _i < enemiesList.size(); _i++) {
                enemy enemy = enemiesList.get(_i);
                enemy.updateInfo();
                if (enemy.isDead) {
                    enemiesList.remove(enemy);
                }
                validTouch(enemy);
                if (enemy.getPositionX() == 0) {
                    enemiesList.remove(enemy);
                    if (enemy.typePerson() != 0) {
                        invaders++;
                        if (points > 200) {

                            points -= 200;
                        } else {
                            points = 0;
                        }
                    }
                }
//                paint.setColor(Color.RED);
//                canvas.drawText("yi: " + enemy.getPositionY(), 300, 700, paint);
//                canvas.drawText("yf: " + (enemy.getPositionY() + (float) enemy.getSpritePerson().getHeight()), 350, 750, paint);
//                canvas.drawRect(enemy.getPositionX(), enemy.getPositionY(), enemy.getPositionX() + (float) enemy.getSpritePerson().getWidth(), enemy.getPositionY() + (float) enemy.getSpritePerson().getHeight(), paint);
                canvas.drawBitmap(enemy.getSpritePerson(), enemy.getPositionX(), enemy.getPositionY(), paint);
            }

            Paint paintLetter = new Paint();
            int spSize = 17;
            float scaledSizeInPixels = spSize * getResources().getDisplayMetrics().scaledDensity;
            paintLetter.setColor(Color.YELLOW);
            paintLetter.setTextSize(scaledSizeInPixels);
            canvas.drawText("Points: " + points, 200, 100, paintLetter);
            canvas.drawText("HP: " + healPoints, 200, 150, paintLetter);
            canvas.drawText("Level: " + levelMultiplier, 500, 100, paintLetter);
            canvas.drawText("Invaders: " + invaders, 500, 150, paintLetter);
            //canvas.drawText("yi: " + player.getPositionY(), 300, 400, paintLetter);
            //canvas.drawText("yf: " + (player.getPositionY() + (float) player.getSpriteIcecreamCar().getHeight()), 300, 300, paintLetter);

//            paint.setColor(Color.GREEN);
//            canvas.drawRect(player.getPositionX(), player.getPositionY(), player.getPositionX() + (float) player.getSpriteIcecreamCar().getWidth(), player.getPositionY() + (float) player.getSpriteIcecreamCar().getHeight(), paint);
            canvas.drawBitmap(player.getSpriteIcecreamCar(), player.getPositionX(), player.getPositionY(), paint);
            holder.unlockCanvasAndPost(canvas);
        }

    }

    private void playerShot() {
        if (player.reload == 0) {
            playerShotsList.add(new Shot(context, screenWith, screenHeight, player.getPositionX() + player.getSpriteIcecreamCar().getWidth() * 7 / 8, player.getPositionY() + player.getSpriteIcecreamCar().getHeight() / 2, 0));
        }
    }

    private void enemyShot() {
        for (int _i = 0; _i < enemiesList.size(); _i++) {

            enemy enemy = enemiesList.get(_i);
            if (enemy.typePerson() == 1 && enemy.reload == 0) {
                enemiesShotsList.add(new Shot(context, screenWith, screenHeight, enemy.getPositionX() + enemy.getSpritePerson().getWidth() * 1 / 8, enemy.getPositionY() + enemy.getSpritePerson().getHeight() / 2, 1));
            }
        }
    }

    private void updateInfo() {
        player.updateInfo();
    }

    private void shotUpdateInfo() {
        playerShot();
        for (int _i = 0; _i < playerShotsList.size(); _i++) {
            Shot shot = playerShotsList.get(_i);
            shot.updateInfo();
            if (shot.getPositionX() == screenWith - 20) {
                playerShotsList.remove(shot);
            }
            paint.setColor(Color.GREEN);
            canvas.drawRect(shot.getPositionX(), shot.getPositionY(), shot.getPositionX() + 20, shot.getPositionY() + 10, paint);//.drawBitmap(shot.getSpriteIcecreamCar(), shot.getPositionX(), shot.getPositionY(), paint);
        }

        enemyShot();
        for (int _i = 0; _i < enemiesShotsList.size(); _i++) {
            Shot shot = enemiesShotsList.get(_i);
            shot.updateInfo();
            if (shot.getPositionX() == 0) {
                enemiesShotsList.remove(shot);
            }
            validTouch(shot);

            paint.setColor(Color.RED);
            canvas.drawRect(shot.getPositionX(), shot.getPositionY(), shot.getPositionX() + 20, shot.getPositionY() + 10, paint);//.drawBitmap(shot.getSpriteIcecreamCar(), shot.getPositionX(), shot.getPositionY(), paint);
            //canvas.drawBitmap(shot.getSpriteIcecreamCar(), shot.getPositionX(), shot.getPositionY(), paint);
        }

    }

    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                player.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("TOUCH DOWN - JUMP");
                player.setJumping(true);
                break;
        }
        return true;
    }

    private void createEnemy() {
        //if (enemiesList.size() < 1) {
        Random rand = new Random();
        int randomNum = rand.nextInt((100 - 0) + 1) + 0;

        if (randomNum <= velAppearence) {
            enemiesList.add(new enemy(context, screenWith, screenHeight));
            enemiesAppear++;

            if (enemiesAppear >= levelMultiplier * 10) {
                enemiesAppear = 0;
                levelMultiplier++;
                velAppearence += 1;
            }
        }
        //}
    }

    public void validTouch(enemy enemy) {
        if (validateCrash(enemy)) {
            if (enemy.typePerson() == 0) {
                if (healPoints + 10 >= 100) {
                    healPoints = 100;
                } else {
                    healPoints += 10;
                }
                enemiesList.remove(enemy);
            } else {
                healPoints = 0;
                enemiesList.remove(enemy);
                player.nowIsDead(context);
            }
        }
    }

    public void validTouch(Shot shot) {
        if (validateCrash(shot)) {
            if (healPoints > 15) {
                healPoints = healPoints - 15;
            } else {
                healPoints = 0;
                player.nowIsDead(context);
            }
            enemiesShotsList.remove(shot);
        }
    }


    public void generateStars() {

        Random rand = new Random();
        float randomY = rand.nextFloat() * (screenHeight);
        float randomX = rand.nextFloat() * (screenWith);
        Paint paintStar = new Paint();
        paintStar.setColor(Color.WHITE);
        canvas.drawRect(randomX, randomY, randomX + 25, randomY + 5, paintStar);
    }


    public void validatePlayerShotImpact() {
        for (int _i = 0; _i < enemiesList.size(); _i++) {
            if (!(levelMultiplier >= 5 && enemiesList.get(_i).typePerson() == 0)) {
                for (int _j = 0; _j < playerShotsList.size(); _j++) {
                    if (validateCrashInX(enemiesList.get(_i), playerShotsList.get(_j))) {
                        if (validateCrashInY(enemiesList.get(_i), playerShotsList.get(_j))) {
                            enemiesList.get(_i).nowIsDead(context);
                            playerShotsList.remove(playerShotsList.get(_j));
                            points += 100;
                        }
                    }
                }
            }
        }
    }

    public boolean validateCrash(enemy enemy) {
        if (validateCrashInX(enemy)) {
            if (validateCrashInY(enemy)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateCrash(Shot shot) {
        if (validateCrashInX(shot)) {
            if (validateCrashInY(shot)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateCrashInX(Shot shot) {
        if (player.getPositionX() + player.getSpriteIcecreamCar().getWidth() >= shot.getPositionX()) {
            return true;
        }
        return false;
    }

    public boolean validateCrashInY(Shot shot) {
        float bottomShot = shot.getPositionY() + 10;
        float topShot = shot.getPositionY();
        float bottomPlayer = (player.getPositionY() + (float) player.getSpriteIcecreamCar().getHeight());
        float topPlayer = player.getPositionY();
        if (topPlayer >= topShot) {
            if (topPlayer <= bottomShot) {
                return true;
            }
        }
        if (topPlayer <= topShot) {
            if (bottomPlayer >= bottomShot) {
                return true;
            }
        }
        if (bottomPlayer >= topShot) {
            if (bottomPlayer <= bottomShot) {
                return true;
            }
        }
        return false;
    }


    public boolean validateCrashInX(enemy enemy, Shot shot) {
        if (enemy.getPositionX() <= shot.getPositionX() + 20) {
            return true;
        }
        return false;
    }

    public boolean validateCrashInY(enemy enemy, Shot shot) {
        float bottomEnemy = (enemy.getPositionY() + (float) enemy.getSpritePerson().getHeight());
        float topEnemy = enemy.getPositionY();
        float bottomShot = shot.getPositionY() + 10;
        float topShot = shot.getPositionY();
        if (topShot >= topEnemy) {
            if (topShot <= bottomEnemy) {
                return true;
            }
        }
        if (topShot <= topEnemy) {
            if (bottomShot >= bottomEnemy) {
                return true;
            }
        }
        if (topShot >= topEnemy) {
            if (bottomShot <= bottomEnemy) {
                return true;
            }
        }
        return false;
    }

    public boolean validateCrashInX(enemy enemy) {
        if (enemy.getPositionX() <= player.getPositionX() + player.getSpriteIcecreamCar().getWidth()) {
            return true;
        }
        return false;
    }

    public boolean validateCrashInY(enemy enemy) {
        float bottomEnemy = (enemy.getPositionY() + (float) enemy.getSpritePerson().getHeight());
        float topEnemy = enemy.getPositionY();
        float bottomPlayer = (player.getPositionY() + (float) player.getSpriteIcecreamCar().getHeight());
        float topPlayer = player.getPositionY();

//        Paint paintLetter = new Paint();
//        int spSize = 17;
//        float scaledSizeInPixels = spSize * getResources().getDisplayMetrics().scaledDensity;
//        paintLetter.setColor(Color.YELLOW);
//        paintLetter.setTextSize(scaledSizeInPixels);
//        canvas.drawText("yi: " + topPlayer, 300, 400, paintLetter);
//        canvas.drawText("yf: " + bottomPlayer, 300, 500, paintLetter);
//        canvas.drawText("yi: " + topEnemy, 300, 600, paintLetter);
//        canvas.drawText("yf: " + bottomEnemy, 300, 700, paintLetter);


        if (topPlayer >= topEnemy) {
            if (topPlayer <= bottomEnemy) {
                return true;
            }
        }
        if (topPlayer <= topEnemy) {
            if (bottomPlayer >= bottomEnemy) {
                return true;
            }
        }
        if (bottomPlayer >= topEnemy) {
            if (bottomPlayer <= bottomEnemy) {
                return true;
            }
        }
        return false;
    }

}

