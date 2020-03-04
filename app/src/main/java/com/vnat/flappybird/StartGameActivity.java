package com.vnat.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StartGameActivity extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

    }
}
