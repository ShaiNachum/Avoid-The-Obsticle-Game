package com.example.avoid_the_obsticle_game_part2;

import android.app.Application;

import com.example.avoid_the_obsticle_game_part2.Utilities.SharedPreferencesManager;
import com.example.avoid_the_obsticle_game_part2.Utilities.SignalManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
        SignalManager.init(this);
    }
}
