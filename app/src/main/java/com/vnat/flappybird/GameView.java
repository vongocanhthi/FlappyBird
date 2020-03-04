package com.vnat.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
    Handler handler;
    Runnable runnable;
    final int TIME = 30;
    Bitmap background;
    Bitmap topTube, bottomTube;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect;
    Bitmap[] birds;
    int birdFrame = 0;
    int velocity = 0, gravity = 3;
    int birdX, birdY;
    boolean gameState = false;
    int gap = 400;
    int minTubeOffSet, maxTubeOffSet;
    int numberOffTubes = 4;
    int distanceBetweenTubes;
    int tubeX;
    int topTubeY;
    Random random;

    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        mapping();
        init();

    }

    private void init() {
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;

        rect = new Rect(0,0,dWidth,dHeight);

        birds = new Bitmap[4];
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        birds[2] = BitmapFactory.decodeResource(getResources(), R.drawable.bird3);
        birds[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bird4);

        birdX = dWidth/2 - birds[0].getWidth()/2;
        birdY = dHeight/2 - birds[0].getHeight()/2;

        distanceBetweenTubes = dWidth*3/4;
        minTubeOffSet = gap/2;
        maxTubeOffSet = dHeight - minTubeOffSet - gap;

        tubeX = dWidth/2 - topTube.getWidth()/2;
        topTubeY = minTubeOffSet + random.nextInt(maxTubeOffSet - minTubeOffSet + 1);
    }

    private void mapping() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_day);
        topTube = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);
        bottomTube = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rect,null);
        if (birdFrame == 0){
            birdFrame = 1;
        }else if (birdFrame == 1){
            birdFrame = 2;
        }else if (birdFrame == 2){
            birdFrame = 3;
        }else if (birdFrame == 3){
            birdFrame = 0;
        }

        if (gameState){
            if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity;
                birdY += velocity;
            }
            canvas.drawBitmap(topTube, tubeX, topTubeY - topTube.getHeight(), null);
            canvas.drawBitmap(bottomTube, tubeX, topTubeY + gap, null);
        }

        canvas.drawBitmap(birds[birdFrame], birdX, birdY, null);

        handler.postDelayed(runnable, TIME);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            velocity = -30;
            gameState = true;
            topTubeY = minTubeOffSet + random.nextInt(maxTubeOffSet - minTubeOffSet + 1);
        }
        return true;
    }
}
