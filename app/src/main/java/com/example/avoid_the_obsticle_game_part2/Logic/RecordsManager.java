package com.example.avoid_the_obsticle_game_part2.Logic;

import com.example.avoid_the_obsticle_game_part2.Models.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordsManager {
    private static final int ARRAY_SIZE = 10;
    private ArrayList<Game> gamesArrayList = new ArrayList<>();


    public RecordsManager() {
    }

    public ArrayList<Game> getGamesArrayList() {
        return gamesArrayList;
    }

    public RecordsManager setGamesArrayList(ArrayList<Game> gamesArrayList) {
        this.gamesArrayList = gamesArrayList;
        return this;
    }

    public RecordsManager addGame(Game game){
        this.gamesArrayList.add(game);

        gamesArrayList.sort((g1,g2) -> g2.compareTo(g1));

        if (gamesArrayList.size() > ARRAY_SIZE + 1)
            gamesArrayList.remove(ARRAY_SIZE);

        return this;
    }
}
