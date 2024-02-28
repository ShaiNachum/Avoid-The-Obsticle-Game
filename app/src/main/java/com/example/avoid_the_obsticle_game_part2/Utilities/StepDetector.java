package com.example.avoid_the_obsticle_game_part2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.avoid_the_obsticle_game_part2.Interfaces.StepCallback;

public class StepDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private long timeStamp = 0l;
    private StepCallback stepCallback;


    public StepDetector(Context context, StepCallback stepCallback){
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateStep(x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }

    private void calculateStep(float x) {
        if(System.currentTimeMillis() - timeStamp > 500){
            timeStamp = System.currentTimeMillis();

            if(x > 3.0){
                if(stepCallback != null)
                    stepCallback.stepXLeft();
            }
            if(x < -3.0){
                if(stepCallback != null)
                    stepCallback.stepXRight();
            }
        }
    }

    public void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}
