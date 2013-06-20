package com.example.AndEngineTest.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import com.example.AndEngineTest.MyActivity;
import com.example.AndEngineTest.scenes.GameScene;

/**
 * Created with IntelliJ IDEA.
 * User: cyrilthomas
 * Date: 6/19/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensorListener implements SensorEventListener {

    public static SensorListener instance;
    GameScene scene;

    public static SensorListener getSharedInstance() {
        if (instance == null)
            instance = new SensorListener();
        return instance;
    }

    public SensorListener() {
        instance = this;
        scene = (GameScene) MyActivity.getSharedInstance().mCurrentScene;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    scene.accelerometerSpeedX = event.values[1];
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
