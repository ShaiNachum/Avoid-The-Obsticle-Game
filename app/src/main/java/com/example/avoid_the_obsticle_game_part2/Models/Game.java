package com.example.avoid_the_obsticle_game_part2.Models;

import com.example.avoid_the_obsticle_game_part2.Logic.RecordsManager;

import java.util.Comparator;

public class Game {
    private String playerName = "";
    private int score = 0;
    private double lat = 0.0;
    private double lon = 0.0;


    public Game(){
    }

    public int getPlace() {
        return RecordsManager.getGamesArrayList().indexOf(this);
    }


    public String getPlayerName() {
        return playerName;
    }

    public Game setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Game setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Game setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Game setScore(int score) {
        this.score = score;
        return this;
    }

    public int compareTo(Game g1) {
        if(this.getScore() == g1.getScore())
            return 0;
        else if (this.getScore() > g1.getScore())
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "Game{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
