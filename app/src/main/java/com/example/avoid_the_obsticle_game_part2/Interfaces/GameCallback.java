package com.example.avoid_the_obsticle_game_part2.Interfaces;

import com.example.avoid_the_obsticle_game_part2.Models.Game;

public interface GameCallback {
    void gameClicked(Game game, int position);
}
