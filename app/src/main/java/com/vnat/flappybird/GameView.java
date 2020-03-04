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
import android.view.View;

public class GameView extends View {
    Handler handler;
    Runnable runnable;
    final int TIME = 30;
    Bitmap background;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect;
    Bitmap[] birds;
    int birdFrame = 0;

    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_day);

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();

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
        canvas.drawBitmap(birds[birdFrame], dWidth/2 - birds[0].getWidth()/2, dHeight/2 - birds[0].getHeight()/2, null);

        handler.postDelayed(runnable, TIME);
    }
}
