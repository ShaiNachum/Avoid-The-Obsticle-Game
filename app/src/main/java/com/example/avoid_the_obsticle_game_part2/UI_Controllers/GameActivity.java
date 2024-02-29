package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.avoid_the_obsticle_game_part2.Interfaces.StepCallback;
import com.example.avoid_the_obsticle_game_part2.Logic.GameManager;
import com.example.avoid_the_obsticle_game_part2.R;
import com.example.avoid_the_obsticle_game_part2.Utilities.CrashSound;
import com.example.avoid_the_obsticle_game_part2.Utilities.SignalManager;
import com.example.avoid_the_obsticle_game_part2.Utilities.StepDetector;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import java.lang.reflect.Field;

public class GameActivity extends AppCompatActivity {
    private static final int EMPTY = 0;
    private static final int ASTEROID = 1;
    private static final int ASTRONAUT = 2;
    private static final int ASTEROIDCOLS = 5;
    private static final int SPACESHIPSROW = 5;
    private static final int ASTEROIDSROWS = 8;
    private static final int NUMOFHEARTS = 3;
    private static final int SMALL_VIBRATE = 50;
    private static final int MEDIUM_VIBRATE = 200;
    private static final int LONG_VIBRATE = 500;
    private ShapeableImageView main_IMG_background;
    private MaterialTextView main_LBL_score;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_spaceship;
    private ShapeableImageView[][] main_IMG_asteroids;
    private ShapeableImageView main_BTN_right;
    private ShapeableImageView main_BTN_left;
    private int main_IMG_astronaut;
    private int main_IMG_astronauts;
    private GameManager gameManager;
    private static final long DELAY = 1000;
    private static final long FAST_DELAY = 700;
    final Handler handler = new Handler();
    private boolean timerOn = false;
    private boolean isFast;
    private boolean tiltMode;
    private String PlayerName;
    private StepDetector stepDetector;
    private CrashSound crashSound;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();

        //this.crashSound = new CrashSound(this);/////////////////////////////////////////////////////////////////

        Intent intent = getIntent();
        this.PlayerName = intent.getStringExtra("playerName");
        this.isFast = intent.getBooleanExtra("gameSpeed", false);
        this.tiltMode = intent.getBooleanExtra("tiltMode", false);
        this.location = getIntent().getParcelableExtra("location");

        if(tiltMode)
            initStepDetector();
        else{
            main_BTN_right.setOnClickListener(View -> rightMove());
            main_BTN_left.setOnClickListener(View -> leftMove());
        }

        gameManager = new GameManager(main_IMG_hearts.length, this.PlayerName, this.location);

        updateSpaceshipsUI();

        Glide
                .with(this)
                .load(R.drawable.space)// can also be url address for the image
                .centerCrop()
                .placeholder(R.drawable.space)
                .into(main_IMG_background);

        startTimer();
    }

    private void initStepDetector() {
        main_BTN_right.setVisibility(View.INVISIBLE);
        main_BTN_left.setVisibility(View.INVISIBLE);
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepXRight() {
                rightMove();
            }
            @Override
            public void stepXLeft() {
                leftMove();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(tiltMode)
            stepDetector.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tiltMode)
            stepDetector.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(isFast){
                handler.postDelayed(this, FAST_DELAY);
            }
            else{
                handler.postDelayed(this, DELAY);
            }
            gameManager.clockTick();
            updateAsteroidUI();
            collisionCheck();
            astronautPick();
            updateScore();
            isGameOnCheck();
        }
    };

    void isGameOnCheck(){
        if(!gameManager.getIsGameOn()){
            stopTimer();
            Intent intent = new Intent(GameActivity.this, RecordsActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    void astronautPick(){
        if(gameManager.isAstronautPicked()) {
            SignalManager.getInstance().vibrate(SMALL_VIBRATE);
            SignalManager.getInstance().toast("+100 points!");
            gameManager.setAstronautPicked(false);
        }
    }

    void collisionCheck() {
        if(gameManager.getCollision()) {
            SignalManager.getInstance().vibrate(LONG_VIBRATE);
            SignalManager.getInstance().toast("COLLISION!!!");
            //crashSound.playSound();/////////////////////////////////////////////////////////////////////////
            updateLives();
        }
    }

    private void updateLives() {
        for (int i = 0; i < NUMOFHEARTS ; i++) {
            if (!gameManager.getHearts()[i])
                main_IMG_hearts[i].setVisibility(View.INVISIBLE);
            else
                main_IMG_hearts[i].setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("SetTextI18n")
    void updateScore(){
        main_LBL_score.setText(gameManager.getScore() + " ");
    }

    private void startTimer() {
        if (!timerOn) {
            handler.postDelayed(runnable, 0);
        }
    }

    private void stopTimer() {
        timerOn = false;
        handler.removeCallbacks(runnable);
    }

    private void rightMove() {
        if (gameManager.getSpaceShipIndex() == SPACESHIPSROW-1) {
            SignalManager.getInstance().vibrate(MEDIUM_VIBRATE);
            SignalManager.getInstance().toast("Cant move anymore");
        } else {
            gameManager.rightMove();
            updateSpaceshipsUI();
            SignalManager.getInstance().vibrate(SMALL_VIBRATE);
        }
    }

    private void leftMove() {
        if (gameManager.getSpaceShipIndex() == 0) {
            SignalManager.getInstance().vibrate(MEDIUM_VIBRATE);
            SignalManager.getInstance().toast("Cant move anymore");
        } else {
            gameManager.leftMove();
            updateSpaceshipsUI();
            SignalManager.getInstance().vibrate(SMALL_VIBRATE);
        }
    }

    private void updateSpaceshipsUI(){
        for (int i = 0; i < SPACESHIPSROW; i++) {
            if(!gameManager.getSpaceships()[i])
                main_IMG_spaceship[i].setVisibility(View.INVISIBLE);
            else
                main_IMG_spaceship[i].setVisibility(View.VISIBLE);
        }
    }

    private void updateAsteroidUI(){
        int[][] asteroids = gameManager.getAsteroids();
        for (int i = 0; i < ASTEROIDSROWS; i++) {
            for (int j = 0; j < ASTEROIDCOLS; j++) {
                if (asteroids[i][j] == EMPTY)
                    main_IMG_asteroids[i][j].setVisibility(View.INVISIBLE);
                else if(asteroids[i][j] == ASTEROID) {
                    main_IMG_asteroids[i][j].setImageResource(main_IMG_astronaut);
                    main_IMG_asteroids[i][j].setVisibility(View.VISIBLE);
                }
                else if(asteroids[i][j] == ASTRONAUT){
                    main_IMG_asteroids[i][j].setImageResource(main_IMG_astronauts);
                    main_IMG_asteroids[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_IMG_spaceship = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_spaceship1),
                findViewById(R.id.main_IMG_spaceship2),
                findViewById(R.id.main_IMG_spaceship3),
                findViewById(R.id.main_IMG_spaceship4),
                findViewById(R.id.main_IMG_spaceship5)
        };
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_IMG_asteroids = new ShapeableImageView[ASTEROIDSROWS][ASTEROIDCOLS];
        String basename = "main_IMG_asteroid";
        for (int i = 0; i < main_IMG_asteroids.length; i++) {
            for (int j = 0; j < main_IMG_asteroids[0].length; j++) {
                try {
                    Field idField = R.id.class.getDeclaredField(basename + i + j);
                    int viewID = idField.getInt(idField);

                    main_IMG_asteroids[i][j] = findViewById(viewID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_IMG_astronauts = R.drawable.astronaut;
        main_IMG_astronaut = R.drawable.asteroid1;
    }
}