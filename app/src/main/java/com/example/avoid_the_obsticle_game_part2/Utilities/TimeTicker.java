package com.example.avoid_the_obsticle_game_part2.Utilities;

import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_Timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTicker {
    private final int DELAY = 1000;
    private final int FAST_DELAY = 800;
    private Timer timer;
    private Callback_Timer callbackTimer;
    private boolean isOn;

    public TimeTicker(Callback_Timer callbackTimer) {
        this.timer = new Timer();
        this.callbackTimer = callbackTimer;
    }

    public void startTimer(boolean isFast){

        this.isOn = true;

        if(isFast){
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    callbackTimer.tick();
                }
            }, FAST_DELAY, FAST_DELAY);
        }
        else{
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    callbackTimer.tick();
                }
            }, DELAY, DELAY);
        }
    }

    public void stopTimer(){
        this.isOn = false;
        timer.cancel();
    }
}
