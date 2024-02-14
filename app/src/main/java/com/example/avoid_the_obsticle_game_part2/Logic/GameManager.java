package com.example.avoid_the_obsticle_game_part2.Logic;

import java.util.Random;

public class GameManager {
    private static final int POINTS = 10;
    private static final int EMPTY = 0;
    private static final int ASTEROID = 1;
    private static final int ASTRONAUT = 2;
    private static final int COLLISIONFINE = 50;
    private static final int ASTEROIDCOLS = 5;
    private static final int ASTEROIDSROWS = 9;//it's bigger by one from the actual size in order
    //                                           to create an option to avoid the asteroid
    private static final int SPACESHIPSROW = 5;
    private static final int NUMOFHEARTS = 3;
    private int spaceShipIndex;
    private boolean isCollision;
    private int score = 0;
    private int life;
    private int[][] asteroids;
    private boolean[] spaceships;
    private boolean[] hearts;

    public GameManager(int life) {
        this.life = life;

        this.spaceShipIndex = 1;

        this.isCollision = false;

        this.spaceships = new boolean[SPACESHIPSROW];
        spaceships[1] = true;

        this.hearts = new boolean[NUMOFHEARTS];
        for(int i = 0 ; i < NUMOFHEARTS ; i++)
            hearts[i] = true;

        this.asteroids = new int[ASTEROIDSROWS][ASTEROIDCOLS];
        for(int i = 0 ; i < ASTEROIDSROWS ; i++)
            for(int j = 0 ; j < ASTEROIDCOLS ; j++)
                asteroids[i][j] = EMPTY;
    }


    public boolean[] getSpaceships() {
        return spaceships;
    }

    public int[][] getAsteroids() {
        return asteroids;
    }

    public boolean[] getHearts(){return hearts;}

    public int getRandom() {
        Random rnd = new Random();
        return rnd.nextInt(ASTEROIDCOLS);
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

        int rnd = getRandom();
        for (int i = 0; i < ASTEROIDCOLS; i++) {
            if (i == rnd)
                asteroids[0][i] = ASTEROID;
            else
                asteroids[0][i] = EMPTY;
        }
        checkCollision();
        checkScore();
    }

    private void checkCollision(){
        if (asteroids[ASTEROIDSROWS-1][spaceShipIndex] == ASTEROID){
            //from here, for unlimited life:
            if(this.life == 0){
                this.life = 3;
                for (int i = 0; i < NUMOFHEARTS; i++) {
                    hearts[i] = true;
                }
            }//till here
            this.isCollision = true;
            this.life--;
            hearts[NUMOFHEARTS - this.life - 1] = false;
        }
        else
            this.isCollision = false;
    }

    private void checkScore(){
        if (asteroids[ASTEROIDSROWS-1][spaceShipIndex] == EMPTY) {
            this.score += POINTS;
        }
        else{
            if(this.score >= 50)
                this.score -= COLLISIONFINE;
            else
                this.score = 0;
        }
    }

    public int getScore(){
        return score;
    }

    public int getLife() {
        return life;
    }

    public boolean getCollision(){
        return this.isCollision;
    }
}