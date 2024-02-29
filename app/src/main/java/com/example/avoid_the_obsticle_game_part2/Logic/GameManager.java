package com.example.avoid_the_obsticle_game_part2.Logic;

import android.location.Location;
import android.util.Log;
import com.example.avoid_the_obsticle_game_part2.Models.Game;
import com.example.avoid_the_obsticle_game_part2.Utilities.SharedPreferencesManager;
import com.google.gson.Gson;
import java.util.Random;

public class GameManager {
    public static final String RECORDSMANAGER = "RECORDSMANAGER";
    private static final int POINTS = 10;
    private static final int EMPTY = 0;
    private static final int ASTEROID = 1;
    private static final int ASTRONAUT = 2;
    private static final int COLLISIONFINE = 50;
    private static final int ASTRONAUTPICK = 100;
    private static final int ASTEROIDCOLS = 5;
    private static final int ASTEROIDSROWS = 9;//it's bigger by one from the actual size in order
    //                                           to create an option to avoid the asteroid
    private static final int SPACESHIPSROW = 5;
    private static final int NUMOFHEARTS = 3;
    private int spaceShipIndex;
    private boolean isCollision;
    private boolean isAstronautPicked;
    private int score = 0;
    private int life;
    private int[][] asteroids;
    private boolean[] spaceships;
    private boolean[] hearts;
    private String playerName;
    private Game game;
    private boolean isGameOn;
    private RecordsManager fromSP;
    private Location location;


    public GameManager(int life, String name, Location location) {
        this.location = location;

        this.life = life;

        this.playerName = name;

        this.spaceShipIndex = 1;

        this.isCollision = false;

        this.isGameOn = true;

        this.isAstronautPicked = false;

        this.spaceships = new boolean[SPACESHIPSROW];
        spaceships[1] = true;

        this.hearts = new boolean[NUMOFHEARTS];
        for(int i = 0 ; i < NUMOFHEARTS ; i++)
            hearts[i] = true;

        this.asteroids = new int[ASTEROIDSROWS][ASTEROIDCOLS];
        for(int i = 0 ; i < ASTEROIDSROWS ; i++)
            for(int j = 0 ; j < ASTEROIDCOLS ; j++)
                asteroids[i][j] = EMPTY;

        this.game = new Game();

        this.fromSP = new Gson().fromJson(SharedPreferencesManager.getInstance().getString(RECORDSMANAGER, ""), RecordsManager.class);
    }


    public boolean isAstronautPicked() {
        return isAstronautPicked;
    }

    public void setAstronautPicked(boolean astronautPicked) {
        isAstronautPicked = astronautPicked;
    }

    public boolean[] getSpaceships() {
        return spaceships;
    }

    public int[][] getAsteroids() {
        return asteroids;
    }

    public boolean[] getHearts(){return hearts;}

    private int getRandom() {
        Random rnd = new Random();
        return rnd.nextInt(ASTEROIDCOLS);
    }

    private boolean getBooleanRandom() {
        Random rnd = new Random();
        return rnd.nextBoolean();
    }

    public int getSpaceShipIndex() {
        return spaceShipIndex;
    }

    public void rightMove(){
        spaceships[spaceShipIndex] = false;
        spaceShipIndex++;
        spaceships[spaceShipIndex] = true;
    }

    public void leftMove(){
        spaceships[spaceShipIndex] = false;
        spaceShipIndex--;
        spaceships[spaceShipIndex] = true;
    }

    public void clockTick() {
        for (int i = ASTEROIDSROWS - 1 ; i > 0 ; i--) {
            for (int j = 0 ; j < ASTEROIDCOLS ; j++) {
                asteroids[i][j] = asteroids[i-1][j];
            }
        }

        int rnd1 = getRandom();
        int rnd2 = getRandom();
        boolean boolRnd = getBooleanRandom();

        for (int i = 0; i < ASTEROIDCOLS; i++) {
            if (i == rnd1)
                asteroids[0][i] = ASTEROID;
            else if(i == rnd2 && boolRnd)
                asteroids[0][i] = ASTRONAUT;
            else
                asteroids[0][i] = EMPTY;
        }

        checkAstronautPick();
        checkScore();
        checkCollision();
    }

    private void checkCollision(){
        if (asteroids[ASTEROIDSROWS-1][spaceShipIndex] == ASTEROID){
//            //from here, for unlimited life:
//            if(this.life == 0){
//                this.life = 3;
//                for (int i = 0; i < NUMOFHEARTS; i++) {
//                    hearts[i] = true;
//                }
//            }//till here

            this.isCollision = true;
            this.life--;

            if(this.life == 0)
                endGame();
            else
                hearts[NUMOFHEARTS - this.life - 1] = false;
        }
        else
            this.isCollision = false;
    }

    private void checkAstronautPick(){
        if (asteroids[ASTEROIDSROWS-1][spaceShipIndex] == ASTRONAUT)
            this.isAstronautPicked = true;
        else
            this.isAstronautPicked = false;
    }

    private void checkScore(){
        if (asteroids[ASTEROIDSROWS-1][spaceShipIndex] == EMPTY) {
            this.score += POINTS;
        }
        else if(this.isAstronautPicked)
            this.score += ASTRONAUTPICK;
        else {
            if(this.score >= 50)
                this.score -= COLLISIONFINE;
            else
                this.score = 0;
        }

    }

    public int getScore(){
        return score;
    }

    public boolean getCollision(){
        return this.isCollision;
    }

    public boolean getIsGameOn(){
        return this.isGameOn;
    }

    private void endGame(){
        game.setScore(this.score);
        game.setPlayerName(this.playerName);
        game.setLat(this.location.getLatitude());
        game.setLon(this.location.getLongitude());

        if(fromSP == null)
            fromSP = new RecordsManager();

        fromSP.addGame(this.game);
        Gson gson = new Gson();
        String recordsManagerAsJson = gson.toJson(fromSP);
        SharedPreferencesManager.getInstance().putString(RECORDSMANAGER,recordsManagerAsJson);
        this.isGameOn = false;
    }
}
